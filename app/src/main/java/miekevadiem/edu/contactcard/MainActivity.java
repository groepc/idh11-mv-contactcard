package miekevadiem.edu.contactcard;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener {

    private ContactDBHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(String msg) {
        Log.i("onFragmentInteraction", msg);

        // check if detail view exists
        DetailFragment contactDetailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.contact_detail_fragment);

        if (contactDetailFragment != null ) {
            contactDetailFragment.setContactView("Hallo wereld");
        } else {

//            DetailFragment newContactDetailFragment = new DetailFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.contact_list_fragment, newContactDetailFragment);
//            transaction.addToBackStack(null);

            // Commit
//            transaction.commit();
        }




    }
}
