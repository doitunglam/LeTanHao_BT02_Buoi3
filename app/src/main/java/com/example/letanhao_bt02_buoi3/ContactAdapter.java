package com.example.letanhao_bt02_buoi3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> {
    ArrayList<Contact> contacts;
    Listener listener;

    public ContactAdapter(Listener listener, ArrayList<Contact> countries) {
        this.listener = listener;
        this.contacts = countries;
    }

    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent, false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, int position) {
        Contact contact = contacts.get(position);
        holder.imgContact.setImageResource(contact.getImageID());
        holder.txName.setText(String.format("%s%s", contact.getFname(), contact.getLname()));
        holder.txPhone.setText(contact.getPhone());
        holder.txMail.setText(contact.getMail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemListener(contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ContactVH extends RecyclerView.ViewHolder {
        CircleImageView imgContact;
        TextView txName, txPhone, txMail;

        public ContactVH(@NonNull View itemView) {
            super(itemView);
            imgContact = itemView.findViewById(R.id.imgContact);
            txName = itemView.findViewById(R.id.txName);
            txPhone = itemView.findViewById(R.id.txPhone);
            txMail = itemView.findViewById(R.id.txMail);
        }
    }

    interface Listener {
        void onItemListener(Contact contact);
    }
}

    
