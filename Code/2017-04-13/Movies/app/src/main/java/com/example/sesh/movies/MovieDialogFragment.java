package com.example.sesh.movies;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class MovieDialogFragment extends DialogFragment {
    public static final String MESSAGE_KEY = "message_key";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create the dialog

        // Use the Builder class for convenient dialog construction
        Bundle bundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(Objects.requireNonNull(bundle).getString(MESSAGE_KEY)).setPositiveButton("OK", (dialog, id) -> {
            // NOTHING TO DO
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
