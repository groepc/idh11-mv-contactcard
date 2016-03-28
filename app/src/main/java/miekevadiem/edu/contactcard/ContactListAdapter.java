package miekevadiem.edu.contactcard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater mInflator;
    ArrayList<Contact> contactArrayList;

    public ContactListAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Contact> contacts) {
        this.context = context;
        this.mInflator = layoutInflater;
        this.contactArrayList = contacts;
    }

    @Override
    public int getCount() {
        int size = contactArrayList.size();
        return size;
    }

    @Override
    public Contact getItem(int position) {
        return contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null) {
            convertView = mInflator.inflate(R.layout.contact_list_item, null);

            viewHolder = new ViewHolder();
            viewHolder.contactAvatar = (ImageView) convertView.findViewById(R.id.contactAvatar);
            viewHolder.contactName = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.contactEmail = (TextView) convertView.findViewById(R.id.contactEmail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contact = contactArrayList.get(position);

        viewHolder.contactName.setText(contact.getFirstName());
        viewHolder.contactEmail.setText(contact.getEmail());
        viewHolder.contactAvatar = null;

        return convertView;
    }

    private static class ViewHolder {
        public ImageView contactAvatar;
        public TextView contactName;
        public TextView contactEmail;
    }
}
