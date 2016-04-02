package miekevadiem.edu.contactcard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment implements View.OnClickListener {

    private ContactDBHandler dbh;
    private Contact contact;

    private TextView contactNameText;
    private TextView contactEmailText;

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        Log.i("DetailFragment()", "Constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("onCreateView()", "");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.contact_detail_fragment, container, false);
        contactNameText = (TextView) view.findViewById(R.id.contactName);
        contactEmailText = (TextView) view.findViewById(R.id.contactEmail);

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void setContactView(String contactEmail) {
        Log.i("setContactView()", contactEmail);

        dbh = new ContactDBHandler(getActivity().getApplicationContext());
        contact = dbh.getContactByEmail(contactEmail);

        contactNameText.setText(contact.getFullName());
        contactEmailText.setText(contact.getEmail());
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String msg);
    }
}
