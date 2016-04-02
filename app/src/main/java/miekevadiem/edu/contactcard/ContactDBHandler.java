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
        ArrayList<Contact> contactList = new ArrayList<Contact>();

        Cursor cursor = fetchAllContacts();
        cursor.moveToFirst();
        while( cursor.moveToNext() ) {
            contactList.add(mapToContact(cursor));
        }

        return contactList;
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

    private Cursor fetchSingleContactByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + COLOMN_EMAIL + "='" + email + "';";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        db.close();
        return c;
    }

    private Cursor fetchAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DB_TABLE_NAME;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        db.close();
        return c;
    }
}
