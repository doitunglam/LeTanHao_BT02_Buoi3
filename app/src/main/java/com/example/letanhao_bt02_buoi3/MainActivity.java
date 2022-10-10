package com.example.letanhao_bt02_buoi3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  implements ContactAdapter.Listener{

    RecyclerView rvContact;
    Contact[] contacts;
    ContactAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvContact = findViewById(R.id.rvContacts);
        StringBuilder stringBuilder= new StringBuilder();
        InputStream s = this.getResources().openRawResource(R.raw.bai02_1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(s));
        String string = "";
        while (true) {
            try {
                if ((string = reader.readLine()) == null) break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            stringBuilder.append(string).append("\n");
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            rvContact = findViewById(R.id.rvContacts);
            contacts = objectMapper.readValue(stringBuilder.toString(), Contact [].class);
            for (Contact ct:contacts)
                ct.setImageID(this.getResources().getIdentifier(ct.getImage().substring(0,ct.getImage().length()-4),"drawable",this.getPackageName()));
            contactAdapter = new ContactAdapter(MainActivity.this ,new ArrayList<>(Arrays.asList(contacts)));
            rvContact.setAdapter(contactAdapter);
            rvContact.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            rvContact.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemListener(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Contacts");
        builder.setIcon(getDrawable(contact.getImageID()));
        builder.setMessage(contact.getFname() +" " + contact.getLname() + "\n" + contact.getPhone());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}