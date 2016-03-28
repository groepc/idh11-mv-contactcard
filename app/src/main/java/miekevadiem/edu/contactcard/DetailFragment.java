package miekevadiem.edu.contactcard;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment implements View.OnClickListener {

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
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

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void setContactView(String contact) {
        Log.i("setContactView()", contact);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String msg);
    }
}
