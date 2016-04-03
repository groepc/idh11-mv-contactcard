package miekevadiem.edu.contactcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener {

    public final static String CONTACT_EMAIL = "miekevadiem.edu.contactcard.CONTACT_EMAIL";

    private ContactDBHandler contactDbHandler;
    private RandomUserApi randomUserApi;

    private ArrayList<Contact> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contact Card");

        contactDbHandler = new ContactDBHandler(getApplicationContext());
        contactList = contactDbHandler.getAllContacts();

        if(contactList.isEmpty()) {
            // do ajax call
            randomUserApi = RandomUserApi.getInstance(this);
            randomUserApi.getResults(new RandomUserApi.ApiResponseListener() {
                @Override
                public void getResult(ArrayList contacts) {
                    contactDbHandler.addContacts(contacts);
                    contactList = contactDbHandler.getAllContacts();
                    addListItems();
                }
            });

        } else {
            // we had contacts in DB
            addListItems();
        }
    }

    /**
     * Push contacts to contact list fragment
     */
    public void addListItems() {
        ListFragment contactListFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.contact_list_fragment);
        contactListFragment.addItems(this.contactList);
    }

    /**
     * Intercept a click on a contact
     * Decides if the contact info should be send to
     * the contact detail fragment (landscape mode) or to
     * start a new DetailActivity (portrait mode)
     * @param contactEmail
     */
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu_item, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                contactList = contactDbHandler.findContacts(query);
                addListItems();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
                contactList = contactDbHandler.findContacts(query);
                addListItems();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
}
