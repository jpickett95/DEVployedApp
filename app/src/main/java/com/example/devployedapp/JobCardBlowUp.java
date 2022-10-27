package com.example.devployedapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devployedapp.databinding.JobcardBlowupItemBinding;
import com.example.devployedapp.databinding.ListPagesRejectedBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JobCardBlowUp extends AppCompatDialogFragment {
    JobcardBlowupItemBinding jobcardBlowupItemBinding;
    FloatingActionButton closeExpandedJobCardButton;

    /*@Override
    protected void OnCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

    }*/

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        jobcardBlowupItemBinding = JobcardBlowupItemBinding.inflate(getLayoutInflater());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.jobcard_blowup_item, jobcardBlowupItemBinding.getRoot());

        closeExpandedJobCardButton = view.findViewById(R.id.floatingActionButton);

        builder.setView(view)
                .setOnDismissListener((DialogInterface.OnDismissListener) closeExpandedJobCardButton);

        return builder.create();
    }

}
