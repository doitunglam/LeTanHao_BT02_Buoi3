package com.example.letanhao_bt02_buoi3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class InfoDialogBottomSheet extends BottomSheetDialog {
    TextView txFullName, txPhone, txEmail,txBirthday;
    ImageView imImage;
    ImageButton btnClose, btnEdit, btnDelete;
    Contact contact;
    ActivityResultLauncher mLauncher;
    ContactAdapter contactAdapter;
    public InfoDialogBottomSheet(@NonNull Context context, Contact contact, ActivityResultLauncher mLauncher, ContactAdapter contactAdapter) {
        super(context);
        this.contact = contact;
        this.mLauncher = mLauncher;
        this.contactAdapter = contactAdapter;
    }


    public void findView(){
        View view = getLayoutInflater().inflate(R.layout.contact_detail, null);

        txFullName = view.findViewById(R.id.cdFullName);
        txPhone = view.findViewById(R.id.cdPhone);
        txEmail = view.findViewById(R.id.cdMmail);
        txBirthday = view.findViewById(R.id.cdBirthday);
        imImage = view.findViewById(R.id.userImage);
        btnClose = view.findViewById(R.id.cancelBtn);
        btnClose.setOnClickListener( v -> {
            this.dismiss();
        });
        btnEdit = view.findViewById(R.id.editBtn);
        btnEdit.setOnClickListener( v -> {
            Intent intent = new Intent(getContext(), AddEditActivity.class);
            intent.putExtra("contact", (CharSequence) contact);
            intent.putExtra("flag", 2);
            mLauncher.launch(intent);
            dismiss();
        });

        btnDelete = view.findViewById(R.id.deleteBtn);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Contacts");
                builder.setMessage("Delete ".concat(contact.getFullName()).concat(" ?"));
                builder.setNegativeButton("No", (dialogInterface, i) -> {
                    dialogInterface.cancel();

                });
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    contactAdapter.deleteContact(contact);
                    dialogInterface.dismiss();
                    dismiss();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        txFullName.setText(contact.getFullName());
        txEmail.setText(contact.getMail());
        txPhone.setText(contact.getPhone());
        txBirthday.setText(contact.getBirthday());
        imImage.setImageResource(contact.getImageID());

        setContentView(view);
    }

}

