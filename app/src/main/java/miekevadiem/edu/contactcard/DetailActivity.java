package miekevadiem.edu.contactcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public final static String CONTACT_EMAIL = "miekevadiem.edu.contactcard.CONTACT_EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.contact_detail_fragment);
        detailFragment.setContactView(intent.getStringExtra(CONTACT_EMAIL));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.animation_left_to_center, R.anim.animation_center_to_right);
    }
}
