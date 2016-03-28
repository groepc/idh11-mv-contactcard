package miekevadiem.edu.contactcard;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener {

    private ContactDBHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(String contactEmail) {
        Log.i("onFragmentInteraction", contactEmail);

        // check if detail view exists
        DetailFragment contactDetailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.contact_detail_fragment);

        if (contactDetailFragment != null ) {
            contactDetailFragment.setContactView(contactEmail);
        } else {
            DetailFragment newContactDetailFragment = new DetailFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contact_list_fragment, newContactDetailFragment, "details");
            transaction.addToBackStack(null);

            // Commit
            transaction.commit();
        }
    }
}
