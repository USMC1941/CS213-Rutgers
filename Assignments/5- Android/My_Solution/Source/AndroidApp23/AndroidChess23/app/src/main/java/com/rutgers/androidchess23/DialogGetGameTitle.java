package com.rutgers.androidchess23;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


/**
 * Dialog for game title to save to file
 *
 * @author William Chen
 * @author Chijun Sha
 */
public class DialogGetGameTitle extends DialogFragment {
    private IOnDialogClick iOnDialogClick;
    private EditText gameTitle;


    public String getGameTitle() {
        if (gameTitle==null) {
            return "Unknown";
        }
        return gameTitle.getText().toString();
    }

    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(getActivity());
        //
        inputDialog.setTitle("Please enter title and hit OK if want To save the game, Or Cancel if not:");
        //
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_getgametitle, null);
        gameTitle = (EditText) view.findViewById(R.id.gameTitle);
        //
        inputDialog.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        iOnDialogClick.onDialogPositiveClick(DialogGetGameTitle.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        iOnDialogClick.onDialogNegativeClick(DialogGetGameTitle.this);
                    }
                });
        //
        return inputDialog.create();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        iOnDialogClick = (IOnDialogClick) activity;
    }
}