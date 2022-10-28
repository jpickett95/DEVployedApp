package com.example.devployedapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MySkillsDialog extends AppCompatDialogFragment {
    private EditText editText;
    private MySkillsDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_profile_skills, null);

        editText = view.findViewById(R.id.editText);

        builder.setView(view)
                .setTitle("Enter a new skill:")
                .setNegativeButton("Cancel", (DialogInterface dialogInterface, int i) -> {})
                .setPositiveButton("Apply", (DialogInterface dialogInterface, int i) -> {
                        String skill = editText.getText().toString();
                        listener.applySkillText(skill);
                    });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MySkillsDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + "must implement MySkillsDialogListener");
        }

    }

    public interface MySkillsDialogListener{
        void applySkillText(String text);
    }
}
