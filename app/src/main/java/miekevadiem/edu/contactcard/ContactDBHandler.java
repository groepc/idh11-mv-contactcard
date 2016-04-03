package miekevadiem.edu.contactcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Iterator;

public class ContactDBHandler extends SQLiteAssetHelper {

    private static ContactDBHandler sInstance;

    private static final String TAG = "ContactDBHandler";

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "contacts.db";
    private static final String DB_TABLE_NAME = "contacts";

    private static final String COLOMN_ID = "_id";
    private static final String COLOMN_FIRSTNAME = "firstName";
    private static final String COLOMN_LASTNAME = "lastName";
    private static final String COLOMN_EMAIL = "email";
    private static final String COLOMN_IMAGEURL = "imageUrl";

    public ContactDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        setForcedUpgrade(2);
    }

    /**
     * READ
     */
    public ArrayList<Contact> getAllContacts() {
        Cursor cursor = fetchAllContacts();
        return mapToContactList(cursor);
    }

    public ArrayList<Contact> findContacts(String query) {
        Cursor cursor = fetchContactsByQuery(query);

        return mapToContactList(cursor);
    }

    public Contact getContactByEmail(String contactEmail) {
        Cursor cursor = fetchSingleContactByEmail(contactEmail);
        return mapToContact(cursor);
    }

    /**
     * CREATE
     */
    public void addContacts(ArrayList<Contact> contacts) {
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            addContact(iterator.next());
        }
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


    /**
     * Map a DB cursor to a contact object
     */
    private Contact mapToContact(Cursor cursor) {
        Contact contact = new Contact();

        contact.setFirstName(cursor.getString(cursor.getColumnIndex(COLOMN_FIRSTNAME)));
        contact.setLastName(cursor.getString(cursor.getColumnIndex(COLOMN_LASTNAME)));
        contact.setEmail(cursor.getString(cursor.getColumnIndex(COLOMN_EMAIL)));
        contact.setImageUrl(cursor.getString(cursor.getColumnIndex(COLOMN_IMAGEURL)));

        return contact;
    }

    private ArrayList<Contact> mapToContactList(Cursor cursor) {
        ArrayList<Contact> contactList = new ArrayList<>();

        cursor.moveToFirst();

        if(cursor.getCount() > 1) {
            while( cursor.moveToNext() ) {
                contactList.add(mapToContact(cursor));
            }
        }

        if(cursor.getCount() == 1) {
            contactList.add(mapToContact(cursor));
        }

        return contactList;
    }

    /**
     * Query SQLite DB to find a user by it's e-mail
     * @param email
     * @return Cursor
     */
    private Cursor fetchSingleContactByEmail(String email) {
        String sqlQuery = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + COLOMN_EMAIL + "='" + email + "'";
        return queryDB(sqlQuery);
    }

    /**
     * Query SQLite DB for all contacts
     * @return
     */
    private Cursor fetchAllContacts() {
        String sqlQuery = "SELECT * FROM " + DB_TABLE_NAME;
        return queryDB(sqlQuery);
    }

    /**
     * Find user where NAME LIKE
     * @param query
     * @return
     */
    private Cursor fetchContactsByQuery(String query) {
        String sqlQuery = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + COLOMN_FIRSTNAME + " LIKE '%" + query + "%'";
        System.out.println(sqlQuery);
        return queryDB(sqlQuery);
    }

    private Cursor queryDB(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        db.close();
        return c;
    }
}
