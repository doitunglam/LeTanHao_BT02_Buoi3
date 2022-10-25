package com.example.letanhao_bt02_buoi3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

public class AddEditActivity extends AppCompatActivity {
    TextInputLayout tifName, tilName, tiEmail, tiPhone, tiBirthday;
    TextInputEditText edfName, edlName, edEmail, edPhone, edBirthday;
    int mYear, mMonth, mDay;
    int flag;
    Contact contactEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tifName = findViewById(R.id.tilFname);
        tilName = findViewById(R.id.tilLname);
        tiEmail = findViewById(R.id.tilEmail);
        tiPhone = findViewById(R.id.tilPhone);
        tiBirthday = findViewById(R.id.tilBirthday);

        edfName = findViewById(R.id.edFirstName);
        edlName = findViewById(R.id.edLastName);
        edEmail = findViewById(R.id.edEmail);
        edPhone = findViewById(R.id.edPhone);
        edBirthday = findViewById(R.id.edBirthday);

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        if(flag != 1){
            getSupportActionBar().setTitle(R.string.add);
        }else {
            getSupportActionBar().setTitle(R.string.edit);
            contactEdit = (Contact) intent.getSerializableExtra("contact");
            edfName.setText(contactEdit.getFname());
            edlName.setText(contactEdit.getLname());
            edEmail.setText(contactEdit.getMail());
            edPhone.setText(contactEdit.getPhone());
            edBirthday.setText(contactEdit.getBirthday());
        }


        edBirthday.setOnClickListener(view -> {
            if (view == edBirthday) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( AddEditActivity.this, new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        edBirthday.setText ( dayOfMonth + "/" + String.format("%02d",month+1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_mnu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.editSave){
            if(edfName.getText().toString().isEmpty()
                    ||edlName.getText().toString().isEmpty()
                    ||edEmail.getText().toString().isEmpty()
                    ||edPhone.getText().toString().isEmpty()
                    ||edBirthday.getText().toString().isEmpty()){
                tifName.setError("Not null");
                tilName.setError("Not null");
                tiEmail.setError("Not null");
                tiPhone.setError("Not null");
                tiBirthday.setError("Not null");
                return false;
            }else {
                Contact contact;
                Intent intent = new Intent();
                if(flag == 1){
                    contact = new Contact(contactEdit.getId(),
                            edfName.getText().toString(),
                            edlName.getText().toString(),
                            contactEdit.getImageID(),
                            edPhone.getText().toString(),
                            edEmail.getText().toString(),
                            edBirthday.getText().toString());

                    intent.putExtra("contact", contact);
                    intent.putExtra("flag", 1);
                }else {
                    contact = new Contact(new Random().nextInt(9999),
                            edfName.getText().toString(),
                            edlName.getText().toString(),
                            0,
                            edPhone.getText().toString(),
                            edEmail.getText().toString(),
                            edBirthday.getText().toString());

                    intent.putExtra("contact", contact);
                    intent.putExtra("flag", 2);
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

