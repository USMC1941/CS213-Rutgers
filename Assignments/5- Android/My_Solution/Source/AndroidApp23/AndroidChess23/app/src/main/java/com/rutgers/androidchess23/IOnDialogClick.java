package com.rutgers.androidchess23;


import android.app.DialogFragment;


/**
 * Interface for Dialog to callback to activity with game title
 *
 * @author William Chen
 * @author Chijun Sha
 */
public interface IOnDialogClick {

    void onDialogPositiveClick(DialogFragment dialog);
    void onDialogNegativeClick(DialogFragment dialog);

}
