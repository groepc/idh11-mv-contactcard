package miekevadiem.edu.contactcard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {

    // views
    private ListView contactListView;
    private RelativeLayout loadingLayout;

    // variables
    private ContactListAdapter arrayAdapter;

    // listeners
    private OnFragmentInteractionListener mListener;

    // view
    LayoutInflater inflater;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;

        // Inflate the layout for this fragment
        this.view = this.inflater.inflate(R.layout.contact_list_fragment, container, false);

        return this.view;
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
        void onFragmentInteraction(String msg);
    }

    public void addItems(ArrayList<Contact> contactList) {
        // set array list adapter
        arrayAdapter = new ContactListAdapter(getActivity().getApplicationContext(), inflater, contactList);

        contactListView = (ListView) view.findViewById(R.id.contact_list);
        contactListView.setOnItemClickListener(this);
        contactListView.setAdapter(arrayAdapter);

//        if(arrayAdapter.getCount() == 0) {
//            loadingLayout = (RelativeLayout) view.findViewById(R.id.loadingLayout);
//            loadingLayout.setVisibility(View.VISIBLE);
//        }
    }
}
