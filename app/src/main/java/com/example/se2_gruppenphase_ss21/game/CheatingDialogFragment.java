package com.example.se2_gruppenphase_ss21.game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.se2_gruppenphase_ss21.R;

public class CheatingDialogFragment extends DialogFragment {

    public interface CheatingDialogListener {
        public void onCheatingPositiveClick(DialogFragment dialog);
        public void onCheatingCancelClick(DialogFragment dialog);
    }

    CheatingDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the CheatingDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the CheatingDialogListener so we can send events to the host
            listener = (CheatingDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement CheatingDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.cheating_dialog_fragment)
                .setPositiveButton(R.string.cheating_dialog_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        listener.onCheatingPositiveClick(CheatingDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cheating_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        listener.onCheatingCancelClick(CheatingDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
