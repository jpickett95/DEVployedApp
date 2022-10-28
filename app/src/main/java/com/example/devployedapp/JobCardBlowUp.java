package com.example.devployedapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.devployedapp.databinding.JobcardBlowupItemBinding;
import com.example.webparser.data.JobListing;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class JobCardBlowUp extends DialogFragment {
    /*JobcardBlowupItemBinding jobcardBlowupItemBinding;
    FloatingActionButton closeExpandedJobCardButton;
    ArrayList<JobListing> jobPostings = new ArrayList<>();
    Context context;*/

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.jobcard_blowup_item, container, false);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        /*Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;*/
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        //View view = layoutInflater.inflate(R.layout.jobcard_blowup_item, null);

        builder.setView(layoutInflater.inflate(R.layout.jobcard_blowup_item, null));

        return builder.create();
    }
}
