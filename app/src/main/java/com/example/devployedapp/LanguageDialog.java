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

public class LanguageDialog extends AppCompatDialogFragment {
    private EditText editTextLanguage;
    private LanguageDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_profile_languages, null);

        editTextLanguage = view.findViewById(R.id.editText);

        builder.setView(view)
                .setTitle("Enter Programming Language:")
                .setNegativeButton("Cancel", (DialogInterface dialogInterface, int i) -> {})
                .setPositiveButton("Apply", (DialogInterface dialogInterface, int i) -> {
                        String language = editTextLanguage.getText().toString();
                        listener.applyText(language);
                    });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (LanguageDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + "must implement LanguageDialogListener");
        }

    }

    public interface LanguageDialogListener{
        void applyText(String text);
    }
}
