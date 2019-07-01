package com.example.imenikapp.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

public class AboutDialog extends AlertDialog.Builder {
    public AboutDialog(@NonNull Context context) {
        super(context);

        setTitle("O aplikaciji");
        setMessage("Stevan Grujic");
        setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            setCancelable(true);

        }

        public AlertDialog prepareDialog () {
            AlertDialog dialog = create();
            dialog.setCanceledOnTouchOutside(false);

            return dialog;
        }
    }
