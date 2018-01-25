package com.rutgers.androidchess23;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import controller.AndroidChessController;
import controller.Move;
import controller.Outcome;
import model.Player;

/**
 * Activity for playing chess
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class ChessActivity extends ChessBoardActivity implements View.OnClickListener, IOnDialogClick, DialogInterface.OnClickListener {
    //message.setText(input);
    //
    CharSequence pieces[] = new CharSequence[] {"Queen", "Rook", "Bishop", "Knight"};
    //
    Button btnRollback;
    Button btnAI;
    Button btnResign;
    Button btnSave;
    Button btnNew;
    Button btnBack;
    Button btnDraw;
    Button btnPlayBack;

    //getText from a EditText in a DialogFragment
    private DialogGetGameTitle diaglogGetName;
    //
    //Dialog to pick a piece to promote to
    private AlertDialog.Builder piecePickerBuilder;

    //Controller
    AndroidChessController controller;


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        DialogGetGameTitle diag = (DialogGetGameTitle)dialog;
        String szTemp = diag.getGameTitle();
        //
        szTemp = szTemp==null ? "" : szTemp;
        //
        if (szTemp!=null) {
            szTemp = szTemp.trim();
            if (szTemp.length()==0) {
                szTemp = "Not_Set";
            }
            //
            String out = serializeAddress(getFilesDir(), szTemp, guiGame);
        }
    };


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_chess);
        //
        player      = (ImageView) findViewById(R.id.player);
        message     = (TextView) findViewById(R.id.message);
        checkDraw   = (CheckBox) findViewById(R.id.checkDraw);
        //
        btnRollback = (Button) findViewById(R.id.btnRollback);
        btnAI       = (Button) findViewById(R.id.btnAI);
        btnResign   = (Button) findViewById(R.id.btnResign);
        btnSave     = (Button) findViewById(R.id.btnSave);
        btnNew      = (Button) findViewById(R.id.btnNew);
        btnBack     = (Button) findViewById(R.id.btnBack);
        btnDraw     = (Button) findViewById(R.id.btnDraw);
        btnPlayBack = (Button) findViewById(R.id.btnPlayBack);
        //
        btnRollback.setOnClickListener(this);
        btnAI.setOnClickListener(this);
        btnResign.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnDraw.setOnClickListener(this);
        btnPlayBack.setOnClickListener(this);
        //
        diaglogGetName = new DialogGetGameTitle();
        piecePickerBuilder = new AlertDialog.Builder(this);
        //
        controller = new AndroidChessController();
        doInitChessBoard(this, btnDraw);
        btnSave.setEnabled(false);
        btnPlayBack.setEnabled(false);
    }


    @Override
    public void onClick(View v) {
        if (v instanceof ImageView) {
            if (controller.gameEnded()) {
                //do nothibng
            }
            else {
                ImageView iv = (ImageView)v;
                String szTag = (String)iv.getTag();
                int file            = szTag.charAt(0)-'0';
                int rank            = szTag.charAt(1)-'0';
                //
                String tagSelected = getSelected();
                //
                if (tagSelected!=null) {
                    if (isSelected(szTag)) {
                        doDeselectPiece();
                    }
                    else {
                        int fileSelected    = tagSelected.charAt(0)-'0';
                        int rankSelected    = tagSelected.charAt(1)-'0';
                        char pieceSelected  = tagSelected.charAt(3);
                        //
                        boolean isProposingDraw = checkDraw.isChecked();
                        //
                        Move move = new Move(file, rank, fileSelected, rankSelected, isProposingDraw);
                        doMove(move, pieceSelected=='P' && ((rank==0) || (rank==7)) );
                    }
                }
                else {
                    char colorPlayer    = szTag.charAt(2);
                    //
                    Player currentPlayer = controller.getCurrentPlayer();
                    if (colorPlayer=='W' && currentPlayer.isWhitePlayer()) {
                        doSelectPiece(szTag);
                    }
                    else if (colorPlayer=='B' && !currentPlayer.isWhitePlayer()) {
                        doSelectPiece(szTag);
                    }
                }
            }
        }
        else if (v instanceof Button) {
            if (!controller.gameEnded() && v==btnRollback) {
                Move move = new Move("Rollback");
                doMove(move, false);
            }
            else if (!controller.gameEnded() && v==btnAI) {
                Move move = new Move("AI");
                doMove(move, false);
            }
            else if (!controller.gameEnded() && v==btnDraw) {
                Move move = new Move("draw");
                doMove(move, false);
            }
            else if (!controller.gameEnded() && v==btnResign) {
                Move move = new Move("resign");
                doMove(move, false);
            }
            else if (v==btnSave) {
                diaglogGetName.show(getFragmentManager(), "Get Game Title");
            }
            else if (v==btnNew) {
                message.setText("New");
                //
                controller = new AndroidChessController();
                doCleanupChessBoard(btnDraw);
                btnSave.setEnabled(false);
                btnPlayBack.setEnabled(false);
            }
            else if (v==btnBack) {
                Intent intent = new Intent(ChessActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else if (v==btnPlayBack) {
                Intent intent = new Intent(ChessActivity.this, PlaybackActivity.class);
                intent.putExtra(INTENT_DATA_KEY_FILENAME, INTENT_DATA_FILENAME_LAST);
                startActivity(intent);
            }
            else {
               //"draw"
            }
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Move.Promotion promotion = Move.Promotion.NONE;
        if (which==0) {
            promotion = Move.Promotion.Queen;
        }
        else if (which==1) {
            promotion = Move.Promotion.Rook;
        }
        else if (which==2) {
            promotion = Move.Promotion.Bishop;
        }
        else if (which==3) {
            promotion = Move.Promotion.Knight;
        }
        else {
            promotion = Move.Promotion.Rook;
        }
        //
        mmove.setPromotion(promotion);
        //
        _doMove(mmove);
    }


    Move mmove;
    private void doMove(Move move, boolean isPromotion) {
        if (isPromotion) {
            mmove = move;
            piecePickerBuilder.setTitle("Promote the pawn to:");
            piecePickerBuilder.setItems(pieces, this);
            piecePickerBuilder.show();
        }
        else {
            _doMove(move);
        }
    }
    private void _doMove(Move move) {
        Outcome outcome = controller.doMove(move);
        //
        if (outcome.isOK()) {
            if (outcome.getGuiInstruction()!=null) {
                goGUI(outcome.getGuiInstruction());
                //
                Player currentPlayer = controller.getCurrentPlayer();
                setPlayer(currentPlayer.isWhitePlayer(), outcome.isProposingDraw(), btnDraw);
            }
            //
            message.setText(outcome.getReason());
            //
            if (controller.gameEnded()) {
                guiGame = controller.guiGame;
                controller.guiGame = null;
                //
                diaglogGetName.show(getFragmentManager(), "Get Game Title");
                //
                btnSave.setEnabled(true);
                btnPlayBack.setEnabled(true);
            }
        }
        else {
            message.setText(outcome.getReason());
        }
    }
}
