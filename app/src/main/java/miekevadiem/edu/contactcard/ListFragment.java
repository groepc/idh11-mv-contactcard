package miekevadiem.edu.contactcard;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView contactListView;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private ContactListAdapter arrayAdapter;

    private ContactDBHandler dbh;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbh = new ContactDBHandler(getActivity().getApplicationContext());
        contactList = dbh.getAllContacts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("onCreateView()", "");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contact_list_fragment, container, false);

        // set array list adapter
        arrayAdapter = new ContactListAdapter(getActivity().getApplicationContext(), inflater, contactList);

        contactListView = (ListView) view.findViewById(R.id.contact_list);
        contactListView.setOnItemClickListener(this);
        contactListView.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener ...");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = arrayAdapter.getItem(position);
        mListener.onFragmentInteraction(contact.getEmail());
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String msg);
    }
}
