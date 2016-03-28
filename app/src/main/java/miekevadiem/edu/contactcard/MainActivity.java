package miekevadiem.edu.contactcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener {

    public final static String CONTACT_EMAIL = "miekevadiem.edu.contactcard.CONTACT_EMAIL";


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

        if (contactDetailFragment != null && contactDetailFragment.isVisible()) {
            contactDetailFragment.setContactView(contactEmail);
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(CONTACT_EMAIL, contactEmail);
            startActivity(intent);
            overridePendingTransition(R.anim.animation_right_to_center, R.anim.animation_center_to_left);

//            Starting new activity was easier than replacing fragments
//            DetailFragment newContactDetailFragment = new DetailFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.contact_list_fragment, newContactDetailFragment, "details");
//            transaction.addToBackStack(null);
//            transaction.commit();
        }
    }
}
