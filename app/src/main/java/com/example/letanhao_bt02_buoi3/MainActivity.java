package com.example.letanhao_bt02_buoi3;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity  implements ContactAdapter.Listener{

    RecyclerView rvContact;
    Contact[] contacts;
    Contact currentContact;
    ContactAdapter contactAdapter;
    View contactDetailView;
    static boolean isInDetailView = false;
    Menu currentMenu;
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
        System.out.println(s);
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
    public void OnItemListener(int pos, Contact contact) {
        if (isInDetailView) return;
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        contactDetailView = layoutInflater.inflate(R.layout.contact_detail, null);
        ViewGroup container = findViewById(R.id.contactDetail);
        container.addView(contactDetailView);
        TextView txBirthday = findViewById(R.id.cdBirthday);
        TextView cdName = findViewById(R.id.cdFullName);
        TextView txMail = findViewById(R.id.cdMmail);
        TextView txPhone = findViewById(R.id.cdPhone);
        ImageView userImage = findViewById(R.id.userImage);
        cdName.setText(contact.getFullName());
        txBirthday.setText(contact.getBirthday());
        txMail.setText(contact.getMail());
        txPhone.setText(contact.getPhone());
        userImage.setImageResource(contact.getImageID());
        isInDetailView = true;
        currentContact = contact;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.main_mnu, menu);
            this.currentMenu = menu;
            SearchView searchView = (SearchView) menu.findItem(R.id.mnuSearch).getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    contactAdapter.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    contactAdapter.getFilter().filter(newText);
                    FloatingActionButton fabAddContact = findViewById(R.id.fabAddContact);
                    if (newText.isEmpty()) {
                        fabAddContact.setVisibility(View.VISIBLE);
                    } else {
                        fabAddContact.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
            return true;
        }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.mnuSort){
//            Collections.sort(Arrays.asList(contacts));
//            contactAdapter.notifyDataSetChanged();
//        }
//        if(item.getItemId() == R.id.mnuSearch){
//            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void onAddButtonClick(View view) {
        Intent it = new Intent(MainActivity.this,AddEditActivity.class);
        it.putExtra("flag",1);
        startActivityForResult(it,1);
    }

    public void onCancelBtnClick(View view) {
        ((ViewGroup) contactDetailView.getParent()).removeView(contactDetailView);
        isInDetailView = false;
    }

    public void onEditBtnClick(View view) {
        Intent it = new Intent(MainActivity.this,AddEditActivity.class);
        it.putExtra("contact",currentContact);
        it.putExtra("flag",0);
        startActivityForResult(it,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Contact newContact = (Contact) data.getSerializableExtra("contact");
            for (int i = 0 ; i <contacts.length; i++)
            {
                Contact ct = contacts[i];
                if (ct.Id == newContact.getId()) {
                    contacts[i] = newContact;
                    break;
                }
            }
            contactAdapter = new ContactAdapter(MainActivity.this ,new ArrayList<>(Arrays.asList(contacts)));
            rvContact.setAdapter(contactAdapter);
            rvContact.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            rvContact.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        }
    }
}