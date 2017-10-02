package com.rutgers.androidchess23;


import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Base Activity for playing chess and replay
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class ChessBoardActivity extends ActivityBase {
    public static final String PIECE_BR     = "BR";
    public static final String PIECE_BN     = "BN";
    public static final String PIECE_BB     = "BB";
    public static final String PIECE_BQ     = "BQ";
    public static final String PIECE_BK     = "BK";
    public static final String PIECE_BP     = "BP";
    public static final String PIECE_WR     = "WR";
    public static final String PIECE_WN     = "WN";
    public static final String PIECE_WB     = "WB";
    public static final String PIECE_WQ     = "WQ";
    public static final String PIECE_WK     = "WK";
    public static final String PIECE_WP     = "WP";
    public static final String PIECE_NO     = "--";


    ImageView   player;
    TextView    message;
    CheckBox    checkDraw;


    private ImageView[][] iviews = new ImageView[8][8];         //file - rank
    private String szSelectedOne = null;


    protected void setBackground(int file, int rank, boolean isSelected) {
        if (isSelected) {
            iviews[file][rank].setBackgroundColor(Color.BLUE);
        }
        else {
            if (((file + rank) % 2) == 0) {
                iviews[file][rank].setBackgroundColor(getResources().getColor(R.color.boarddark)/*Color.GRAY*/);
            } else {
                iviews[file][rank].setBackgroundColor(getResources().getColor(R.color.boardlight)/*Color.WHITE*/);
            }
        }
    }


    protected String getSelected() {
        return szSelectedOne;
    }


    protected boolean isSelected(String szTag) {
        if (szSelectedOne==null) {
            return false;
        }
        else {
            return szSelectedOne.charAt(0)==szTag.charAt(0) && szSelectedOne.charAt(1)==szTag.charAt(1);
        }
    }


    protected void doSelectPiece(String szTag) {
        int file        = szTag.charAt(0)-'0';
        int rank        = szTag.charAt(1)-'0';
        //
        doDeselectPiece();
        //
        szSelectedOne = szTag;
        setBackground(file, rank, true);
    }
    protected void doDeselectPiece() {
        if (szSelectedOne!=null) {
            int file        = szSelectedOne.charAt(0)-'0';
            int rank        = szSelectedOne.charAt(1)-'0';
            //
            szSelectedOne = null;
            setBackground(file, rank, false);
        }
    }


    protected void setPlayer(boolean isWhite, boolean isProposedDraw, Button btnDraw) {
        if (isWhite) {
            player.setImageResource(R.drawable.whiteking);
        }
        else {
            player.setImageResource(R.drawable.blackking);
        }
        //
        checkDraw.setChecked(false);
        //
        if (btnDraw!=null) {
            if (isProposedDraw) {
                btnDraw.setEnabled(true);
                btnDraw.setBackgroundColor(Color.GREEN);
            }
            else {
                btnDraw.setEnabled(false);
                btnDraw.setBackgroundColor(Color.WHITE);
            }
        }
    }

    protected void goGUI(String instruction) {
        String[] temps = instruction.split("\\;");
        for (int i=0; i<temps.length; i++) {
            String szOne = temps[i].trim();
            //
            doDeselectPiece();
            //
            if (szOne.length()>=5 && szOne.charAt(0)=='M') {                    //move
                movePiece(szOne.charAt(3)-'0', szOne.charAt(4)-'0', szOne.charAt(1)-'0', szOne.charAt(2)-'0');
            }
            else if (szOne.length()>=5 && szOne.charAt(0)=='A') {               //add
                addPiece(szOne.substring(1,3), szOne.charAt(3)-'0', szOne.charAt(4)-'0');
            }
            else if (szOne.length()>=3 && szOne.charAt(0)=='R') {               //remove
                removePiece(szOne.charAt(1)-'0', szOne.charAt(2)-'0');
            }
        }
    }
    protected void removePiece(int file, int rank) {
        String szTag = "" + file + "" + rank + PIECE_NO;
        //
        iviews[file][rank].setImageResource(R.drawable.transparent);
        iviews[file][rank].setTag(szTag);
    }
    protected void movePiece(int fileOld, int rankOld, int fileNew, int rankNew) {
        String szOldTag = (String) iviews[fileOld][rankOld].getTag();
        String szNewTag = (String)iviews[fileNew][rankNew].getTag();
        //
        if (szOldTag.indexOf(PIECE_BR)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.blackrook);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_BN)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.blackknight);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_BB)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.blackbishop);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_BQ)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.blackqueen);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_BK)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.blackking);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_BP)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.blackpawn);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_WR)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.whiterook);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_WN)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.whiteknight);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_WB)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.whitebishop);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_WQ)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.whitequeen);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_WK)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.whiteking);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_WP)>=0) {
            iviews[fileOld][rankOld].setImageResource(R.drawable.transparent);
            iviews[fileOld][rankOld].setTag(szOldTag.substring(0,2) + PIECE_NO);
            //
            iviews[fileNew][rankNew].setImageResource(R.drawable.whitepawn);
            iviews[fileNew][rankNew].setTag(szNewTag.substring(0,2) + szOldTag.substring(2));
        }
        else if (szOldTag.indexOf(PIECE_NO)>=0) {
        }
        else {
        }
    }
    protected void addPiece(String type, int file, int rank) {
        String szTag = "" + file + "" + rank + type.toUpperCase();
        //
        if (type.equalsIgnoreCase(PIECE_BR)) {
            iviews[file][rank].setImageResource(R.drawable.blackrook);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_BN)) {
            iviews[file][rank].setImageResource(R.drawable.blackknight);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_BB)) {
            iviews[file][rank].setImageResource(R.drawable.blackbishop);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_BQ)) {
            iviews[file][rank].setImageResource(R.drawable.blackqueen);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_BK)) {
            iviews[file][rank].setImageResource(R.drawable.blackking);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_BP)) {
            iviews[file][rank].setImageResource(R.drawable.blackpawn);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_WR)) {
            iviews[file][rank].setImageResource(R.drawable.whiterook);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_WN)) {
            iviews[file][rank].setImageResource(R.drawable.whiteknight);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_WB)) {
            iviews[file][rank].setImageResource(R.drawable.whitebishop);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_WQ)) {
            iviews[file][rank].setImageResource(R.drawable.whitequeen);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_WK)) {
            iviews[file][rank].setImageResource(R.drawable.whiteking);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_WP)) {
            iviews[file][rank].setImageResource(R.drawable.whitepawn);
            iviews[file][rank].setTag(szTag);
        }
        else if (type.equalsIgnoreCase(PIECE_NO)) {
        }
        else {
        }
    }


    protected void doInitChessBoard(View.OnClickListener lister, Button btnDraw) {
        if (message!=null) {
            message.setText("");
        }
        //
        if (btnDraw!=null) {
            btnDraw.setEnabled(false);
            btnDraw.setBackgroundColor(Color.WHITE);
        }
        //
        iviews[0][7] = (ImageView) findViewById(R.id.iv07);	iviews[0][7].setTag("07"+PIECE_BR);
        iviews[1][7] = (ImageView) findViewById(R.id.iv17);	iviews[1][7].setTag("17"+PIECE_BN);
        iviews[2][7] = (ImageView) findViewById(R.id.iv27);	iviews[2][7].setTag("27"+PIECE_BB);
        iviews[3][7] = (ImageView) findViewById(R.id.iv37);	iviews[3][7].setTag("37"+PIECE_BQ);
        iviews[4][7] = (ImageView) findViewById(R.id.iv47);	iviews[4][7].setTag("47"+PIECE_BK);
        iviews[5][7] = (ImageView) findViewById(R.id.iv57);	iviews[5][7].setTag("57"+PIECE_BB);
        iviews[6][7] = (ImageView) findViewById(R.id.iv67);	iviews[6][7].setTag("67"+PIECE_BN);
        iviews[7][7] = (ImageView) findViewById(R.id.iv77);	iviews[7][7].setTag("77"+PIECE_BR);
        //
        iviews[0][6] = (ImageView) findViewById(R.id.iv06);	iviews[0][6].setTag("06"+PIECE_BP);
        iviews[1][6] = (ImageView) findViewById(R.id.iv16);	iviews[1][6].setTag("16"+PIECE_BP);
        iviews[2][6] = (ImageView) findViewById(R.id.iv26);	iviews[2][6].setTag("26"+PIECE_BP);
        iviews[3][6] = (ImageView) findViewById(R.id.iv36);	iviews[3][6].setTag("36"+PIECE_BP);
        iviews[4][6] = (ImageView) findViewById(R.id.iv46);	iviews[4][6].setTag("46"+PIECE_BP);
        iviews[5][6] = (ImageView) findViewById(R.id.iv56);	iviews[5][6].setTag("56"+PIECE_BP);
        iviews[6][6] = (ImageView) findViewById(R.id.iv66);	iviews[6][6].setTag("66"+PIECE_BP);
        iviews[7][6] = (ImageView) findViewById(R.id.iv76);	iviews[7][6].setTag("76"+PIECE_BP);
        //
        iviews[0][5] = (ImageView) findViewById(R.id.iv05);	iviews[0][5].setTag("05"+PIECE_NO);
        iviews[1][5] = (ImageView) findViewById(R.id.iv15);	iviews[1][5].setTag("15"+PIECE_NO);
        iviews[2][5] = (ImageView) findViewById(R.id.iv25);	iviews[2][5].setTag("25"+PIECE_NO);
        iviews[3][5] = (ImageView) findViewById(R.id.iv35);	iviews[3][5].setTag("35"+PIECE_NO);
        iviews[4][5] = (ImageView) findViewById(R.id.iv45);	iviews[4][5].setTag("45"+PIECE_NO);
        iviews[5][5] = (ImageView) findViewById(R.id.iv55);	iviews[5][5].setTag("55"+PIECE_NO);
        iviews[6][5] = (ImageView) findViewById(R.id.iv65);	iviews[6][5].setTag("65"+PIECE_NO);
        iviews[7][5] = (ImageView) findViewById(R.id.iv75);	iviews[7][5].setTag("75"+PIECE_NO);
        //
        iviews[0][4] = (ImageView) findViewById(R.id.iv04);	iviews[0][4].setTag("04"+PIECE_NO);
        iviews[1][4] = (ImageView) findViewById(R.id.iv14);	iviews[1][4].setTag("14"+PIECE_NO);
        iviews[2][4] = (ImageView) findViewById(R.id.iv24);	iviews[2][4].setTag("24"+PIECE_NO);
        iviews[3][4] = (ImageView) findViewById(R.id.iv34);	iviews[3][4].setTag("34"+PIECE_NO);
        iviews[4][4] = (ImageView) findViewById(R.id.iv44);	iviews[4][4].setTag("44"+PIECE_NO);
        iviews[5][4] = (ImageView) findViewById(R.id.iv54);	iviews[5][4].setTag("54"+PIECE_NO);
        iviews[6][4] = (ImageView) findViewById(R.id.iv64);	iviews[6][4].setTag("64"+PIECE_NO);
        iviews[7][4] = (ImageView) findViewById(R.id.iv74);	iviews[7][4].setTag("74"+PIECE_NO);
        //
        iviews[0][3] = (ImageView) findViewById(R.id.iv03);	iviews[0][3].setTag("03"+PIECE_NO);
        iviews[1][3] = (ImageView) findViewById(R.id.iv13);	iviews[1][3].setTag("13"+PIECE_NO);
        iviews[2][3] = (ImageView) findViewById(R.id.iv23);	iviews[2][3].setTag("23"+PIECE_NO);
        iviews[3][3] = (ImageView) findViewById(R.id.iv33);	iviews[3][3].setTag("33"+PIECE_NO);
        iviews[4][3] = (ImageView) findViewById(R.id.iv43);	iviews[4][3].setTag("43"+PIECE_NO);
        iviews[5][3] = (ImageView) findViewById(R.id.iv53);	iviews[5][3].setTag("53"+PIECE_NO);
        iviews[6][3] = (ImageView) findViewById(R.id.iv63);	iviews[6][3].setTag("63"+PIECE_NO);
        iviews[7][3] = (ImageView) findViewById(R.id.iv73);	iviews[7][3].setTag("73"+PIECE_NO);
        //
        iviews[0][2] = (ImageView) findViewById(R.id.iv02);	iviews[0][2].setTag("02"+PIECE_NO);
        iviews[1][2] = (ImageView) findViewById(R.id.iv12);	iviews[1][2].setTag("12"+PIECE_NO);
        iviews[2][2] = (ImageView) findViewById(R.id.iv22);	iviews[2][2].setTag("22"+PIECE_NO);
        iviews[3][2] = (ImageView) findViewById(R.id.iv32);	iviews[3][2].setTag("32"+PIECE_NO);
        iviews[4][2] = (ImageView) findViewById(R.id.iv42);	iviews[4][2].setTag("42"+PIECE_NO);
        iviews[5][2] = (ImageView) findViewById(R.id.iv52);	iviews[5][2].setTag("52"+PIECE_NO);
        iviews[6][2] = (ImageView) findViewById(R.id.iv62);	iviews[6][2].setTag("62"+PIECE_NO);
        iviews[7][2] = (ImageView) findViewById(R.id.iv72);	iviews[7][2].setTag("72"+PIECE_NO);
        //
        iviews[0][1] = (ImageView) findViewById(R.id.iv01);	iviews[0][1].setTag("01"+PIECE_WP);
        iviews[1][1] = (ImageView) findViewById(R.id.iv11);	iviews[1][1].setTag("11"+PIECE_WP);
        iviews[2][1] = (ImageView) findViewById(R.id.iv21);	iviews[2][1].setTag("21"+PIECE_WP);
        iviews[3][1] = (ImageView) findViewById(R.id.iv31);	iviews[3][1].setTag("31"+PIECE_WP);
        iviews[4][1] = (ImageView) findViewById(R.id.iv41);	iviews[4][1].setTag("41"+PIECE_WP);
        iviews[5][1] = (ImageView) findViewById(R.id.iv51);	iviews[5][1].setTag("51"+PIECE_WP);
        iviews[6][1] = (ImageView) findViewById(R.id.iv61);	iviews[6][1].setTag("61"+PIECE_WP);
        iviews[7][1] = (ImageView) findViewById(R.id.iv71);	iviews[7][1].setTag("71"+PIECE_WP);
        //
        iviews[0][0] = (ImageView) findViewById(R.id.iv00);	iviews[0][0].setTag("00"+PIECE_WR);
        iviews[1][0] = (ImageView) findViewById(R.id.iv10);	iviews[1][0].setTag("10"+PIECE_WN);
        iviews[2][0] = (ImageView) findViewById(R.id.iv20);	iviews[2][0].setTag("20"+PIECE_WB);
        iviews[3][0] = (ImageView) findViewById(R.id.iv30);	iviews[3][0].setTag("30"+PIECE_WQ);
        iviews[4][0] = (ImageView) findViewById(R.id.iv40);	iviews[4][0].setTag("40"+PIECE_WK);
        iviews[5][0] = (ImageView) findViewById(R.id.iv50);	iviews[5][0].setTag("50"+PIECE_WB);
        iviews[6][0] = (ImageView) findViewById(R.id.iv60);	iviews[6][0].setTag("60"+PIECE_WN);
        iviews[7][0] = (ImageView) findViewById(R.id.iv70);	iviews[7][0].setTag("70"+PIECE_WR);
        //
        for (int file = 0; file <= 7; file++) {
            for (int rank = 0; rank <= 7; rank++) {
                setBackground(file, rank, false);
                //
                if (lister!=null) {
                    iviews[file][rank].setOnClickListener(lister);
                }
            }
        }
    }


    protected void doCleanupChessBoard(Button btnDraw) {
        setPlayer(true, false, btnDraw);
        //
        if (message!=null) {
            message.setText("");
        }
        //
        iviews[0][7].setImageResource(R.drawable.blackrook);	iviews[0][7].setTag("07"+PIECE_BR);
        iviews[1][7].setImageResource(R.drawable.blackknight);	iviews[1][7].setTag("17"+PIECE_BN);
        iviews[2][7].setImageResource(R.drawable.blackbishop);	iviews[2][7].setTag("27"+PIECE_BB);
        iviews[3][7].setImageResource(R.drawable.blackqueen);	iviews[3][7].setTag("37"+PIECE_BQ);
        iviews[4][7].setImageResource(R.drawable.blackking);	iviews[4][7].setTag("47"+PIECE_BK);
        iviews[5][7].setImageResource(R.drawable.blackbishop);	iviews[5][7].setTag("57"+PIECE_BB);
        iviews[6][7].setImageResource(R.drawable.blackknight);	iviews[6][7].setTag("67"+PIECE_BN);
        iviews[7][7].setImageResource(R.drawable.blackrook);	iviews[7][7].setTag("77"+PIECE_BR);
        //
        iviews[0][6].setImageResource(R.drawable.blackpawn);	iviews[0][6].setTag("06"+PIECE_BP);
        iviews[1][6].setImageResource(R.drawable.blackpawn);	iviews[1][6].setTag("16"+PIECE_BP);
        iviews[2][6].setImageResource(R.drawable.blackpawn);	iviews[2][6].setTag("26"+PIECE_BP);
        iviews[3][6].setImageResource(R.drawable.blackpawn);	iviews[3][6].setTag("36"+PIECE_BP);
        iviews[4][6].setImageResource(R.drawable.blackpawn);	iviews[4][6].setTag("46"+PIECE_BP);
        iviews[5][6].setImageResource(R.drawable.blackpawn);	iviews[5][6].setTag("56"+PIECE_BP);
        iviews[6][6].setImageResource(R.drawable.blackpawn);	iviews[6][6].setTag("66"+PIECE_BP);
        iviews[7][6].setImageResource(R.drawable.blackpawn);	iviews[7][6].setTag("76"+PIECE_BP);
        //
        iviews[0][5].setImageResource(R.drawable.transparent);	iviews[0][5].setTag("05"+PIECE_NO);
        iviews[1][5].setImageResource(R.drawable.transparent);	iviews[1][5].setTag("15"+PIECE_NO);
        iviews[2][5].setImageResource(R.drawable.transparent);	iviews[2][5].setTag("25"+PIECE_NO);
        iviews[3][5].setImageResource(R.drawable.transparent);	iviews[3][5].setTag("35"+PIECE_NO);
        iviews[4][5].setImageResource(R.drawable.transparent);	iviews[4][5].setTag("45"+PIECE_NO);
        iviews[5][5].setImageResource(R.drawable.transparent);	iviews[5][5].setTag("55"+PIECE_NO);
        iviews[6][5].setImageResource(R.drawable.transparent);	iviews[6][5].setTag("65"+PIECE_NO);
        iviews[7][5].setImageResource(R.drawable.transparent);	iviews[7][5].setTag("75"+PIECE_NO);
        //
        iviews[0][4].setImageResource(R.drawable.transparent);	iviews[0][4].setTag("04"+PIECE_NO);
        iviews[1][4].setImageResource(R.drawable.transparent);	iviews[1][4].setTag("14"+PIECE_NO);
        iviews[2][4].setImageResource(R.drawable.transparent);	iviews[2][4].setTag("24"+PIECE_NO);
        iviews[3][4].setImageResource(R.drawable.transparent);	iviews[3][4].setTag("34"+PIECE_NO);
        iviews[4][4].setImageResource(R.drawable.transparent);	iviews[4][4].setTag("44"+PIECE_NO);
        iviews[5][4].setImageResource(R.drawable.transparent);	iviews[5][4].setTag("54"+PIECE_NO);
        iviews[6][4].setImageResource(R.drawable.transparent);	iviews[6][4].setTag("64"+PIECE_NO);
        iviews[7][4].setImageResource(R.drawable.transparent);	iviews[7][4].setTag("74"+PIECE_NO);
        //
        iviews[0][3].setImageResource(R.drawable.transparent);	iviews[0][3].setTag("03"+PIECE_NO);
        iviews[1][3].setImageResource(R.drawable.transparent);	iviews[1][3].setTag("13"+PIECE_NO);
        iviews[2][3].setImageResource(R.drawable.transparent);	iviews[2][3].setTag("23"+PIECE_NO);
        iviews[3][3].setImageResource(R.drawable.transparent);	iviews[3][3].setTag("33"+PIECE_NO);
        iviews[4][3].setImageResource(R.drawable.transparent);	iviews[4][3].setTag("43"+PIECE_NO);
        iviews[5][3].setImageResource(R.drawable.transparent);	iviews[5][3].setTag("53"+PIECE_NO);
        iviews[6][3].setImageResource(R.drawable.transparent);	iviews[6][3].setTag("63"+PIECE_NO);
        iviews[7][3].setImageResource(R.drawable.transparent);	iviews[7][3].setTag("73"+PIECE_NO);
        //
        iviews[0][2].setImageResource(R.drawable.transparent);	iviews[0][2].setTag("02"+PIECE_NO);
        iviews[1][2].setImageResource(R.drawable.transparent);	iviews[1][2].setTag("12"+PIECE_NO);
        iviews[2][2].setImageResource(R.drawable.transparent);	iviews[2][2].setTag("22"+PIECE_NO);
        iviews[3][2].setImageResource(R.drawable.transparent);	iviews[3][2].setTag("32"+PIECE_NO);
        iviews[4][2].setImageResource(R.drawable.transparent);	iviews[4][2].setTag("42"+PIECE_NO);
        iviews[5][2].setImageResource(R.drawable.transparent);	iviews[5][2].setTag("52"+PIECE_NO);
        iviews[6][2].setImageResource(R.drawable.transparent);	iviews[6][2].setTag("62"+PIECE_NO);
        iviews[7][2].setImageResource(R.drawable.transparent);	iviews[7][2].setTag("72"+PIECE_NO);
        //
        iviews[0][1].setImageResource(R.drawable.whitepawn);	iviews[0][1].setTag("01"+PIECE_WP);
        iviews[1][1].setImageResource(R.drawable.whitepawn);	iviews[1][1].setTag("11"+PIECE_WP);
        iviews[2][1].setImageResource(R.drawable.whitepawn);	iviews[2][1].setTag("21"+PIECE_WP);
        iviews[3][1].setImageResource(R.drawable.whitepawn);	iviews[3][1].setTag("31"+PIECE_WP);
        iviews[4][1].setImageResource(R.drawable.whitepawn);	iviews[4][1].setTag("41"+PIECE_WP);
        iviews[5][1].setImageResource(R.drawable.whitepawn);	iviews[5][1].setTag("51"+PIECE_WP);
        iviews[6][1].setImageResource(R.drawable.whitepawn);	iviews[6][1].setTag("61"+PIECE_WP);
        iviews[7][1].setImageResource(R.drawable.whitepawn);	iviews[7][1].setTag("71"+PIECE_WP);
        //
        iviews[0][0].setImageResource(R.drawable.whiterook);	iviews[0][0].setTag("00"+PIECE_WR);
        iviews[1][0].setImageResource(R.drawable.whiteknight);	iviews[1][0].setTag("10"+PIECE_WN);
        iviews[2][0].setImageResource(R.drawable.whitebishop);	iviews[2][0].setTag("20"+PIECE_WB);
        iviews[3][0].setImageResource(R.drawable.whitequeen);	iviews[3][0].setTag("30"+PIECE_WQ);
        iviews[4][0].setImageResource(R.drawable.whiteking);	iviews[4][0].setTag("40"+PIECE_WK);
        iviews[5][0].setImageResource(R.drawable.whitebishop);	iviews[5][0].setTag("50"+PIECE_WB);
        iviews[6][0].setImageResource(R.drawable.whiteknight);	iviews[6][0].setTag("60"+PIECE_WN);
        iviews[7][0].setImageResource(R.drawable.whiterook);	iviews[7][0].setTag("70"+PIECE_WR);
        //
        for (int file = 0; file <= 7; file++) {
            for (int rank = 0; rank <= 7; rank++) {
                setBackground(file, rank, false);
            }
        }
    }
}
