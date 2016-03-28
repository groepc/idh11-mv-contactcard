package miekevadiem.edu.contactcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ContactDBHandler extends SQLiteOpenHelper {

    private static ContactDBHandler sInstance;

    private static final String TAG = "ContactDBHandler";

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "contact.db";
    private static final String DB_TABLE_NAME = "contacts";

    private static final String COLOMN_ID = "_id";  // primary key, auto increment
    private static final String COLOMN_FIRSTNAME = "firstName";
    private static final String COLOMN_LASTNAME = "lastName";
    private static final String COLOMN_EMAIL = "email";
    private static final String COLOMN_IMAGEURL = "imageUrl";

    public static synchronized ContactDBHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ContactDBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private ContactDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + DB_TABLE_NAME +
                "(" +
                COLOMN_ID + " INTEGER PRIMARY KEY," +
                COLOMN_FIRSTNAME + " TEXT," +
                COLOMN_LASTNAME + " TEXT," +
                COLOMN_EMAIL + " TEXT," +
                COLOMN_IMAGEURL + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactList = new ArrayList<Contact>();

        Cursor cursor = fetchAllContacts();
        cursor.moveToFirst();
        while( cursor.moveToNext() ) {
            Contact contact = new Contact();

            contact.setFirstName(cursor.getString(cursor.getColumnIndex(COLOMN_FIRSTNAME)));
            contact.setLastName(cursor.getString(cursor.getColumnIndex(COLOMN_LASTNAME)));
            contact.setEmail(cursor.getString(cursor.getColumnIndex(COLOMN_EMAIL)));
            contact.setImageUrl(cursor.getString(cursor.getColumnIndex(COLOMN_IMAGEURL)));

            contactList.add(contact);
        }

        return contactList;
    }

    private Cursor fetchAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DB_TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    public void addContacts() {
        Contact contact1 = new Contact("Vadiem", "Janssens", "vadiem@webcolors.nl");
        Contact contact2 = new Contact("Mieke", "van Doorneveld", "mieke@avans.nl");
        this.addContact(contact1);
        this.addContact(contact2);
    }

    public void addContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(COLOMN_FIRSTNAME, contact.getFirstName());
        values.put(COLOMN_LASTNAME, contact.getLastName());
        values.put(COLOMN_EMAIL, contact.getEmail());
        values.put(COLOMN_IMAGEURL, contact.getImageUrl());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DB_TABLE_NAME, null, values);
        db.close();
    }
}
