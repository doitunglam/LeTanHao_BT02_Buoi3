package com.example.letanhao_bt02_buoi3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> implements Filterable {

    Context context;
    ArrayList<Contact> contacts;
    ArrayList<Contact> contactsFilter;
    Listener listener;

    public ContactAdapter(Listener listener, ArrayList<Contact> contacts) {
        this.listener = listener;
        this.contacts = contacts;
        this.contactsFilter = contacts;
    }

    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row, parent,false);
        return new ContactVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = contactsFilter.get(position);
        holder.txName.setText(contact.getFname().concat(" ").concat(contact.getLname()));
        holder.txPhone.setText(contact.getPhone());
        holder.txEmail.setText(contact.getMail());
        if(contact.getImageID() == 0){
            holder.imgProfile.setImageResource(R.drawable.ic_baseline_circle_24);
        }else {
            holder.imgProfile.setImageResource(contact.getImageID());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemListener(position,contact);
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new ContactFilter();
    }

    class ContactVH extends RecyclerView.ViewHolder{
        TextView txName, txPhone, txEmail;
        ImageView imgProfile;

        public ContactVH(@NonNull View itemView) {
            super(itemView);
            txName = itemView.findViewById(R.id.txName);
            txPhone = itemView.findViewById(R.id.txPhone);
            txEmail = itemView.findViewById(R.id.txMail);
            imgProfile = itemView.findViewById(R.id.imgContact);
        }
    }

    class ContactFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                contactsFilter = contacts;
            } else {
                List<Contact> filteredList = new ArrayList<>();
                for (Contact row : contacts) {
                    if (row.getFname().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence) || row.getLname().contains(charSequence)) {
                        filteredList.add(row);
                    }
                }

                contactsFilter = (ArrayList<Contact>) filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = contactsFilter;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactsFilter= (ArrayList<Contact>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void addContact(Contact contact){
        contactsFilter.add(contact);
        notifyDataSetChanged();
    }

    public void editContact(Contact contact, int pos){
        contactsFilter.set(pos, contact);
        notifyDataSetChanged();
    }

    public void deleteContact(int pos){
        contactsFilter.remove(pos);
        notifyDataSetChanged();
    }

    public void deleteContact(Contact contact){
        contactsFilter.remove(contact);
        notifyDataSetChanged();
    }

    interface Listener{
        void OnItemListener(int pos, Contact contact);
    }
}
