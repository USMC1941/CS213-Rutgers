package com.example.sesh.movies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by seshv on 4/10/17.
 */

public class MovieDialogFragment extends DialogFragment {

    public static final String MESSAGE_KEY = "message_key";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create the dialog

        // Use the Builder class for convenient dialog construction
        Bundle bundle = getArguments();

        AlertDialog.Builder builder =
            new AlertDialog.Builder(getActivity());

        builder.setMessage(bundle.getString(MESSAGE_KEY))
                .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                        int id) {
                        // NOTHING TO DO
                        }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
