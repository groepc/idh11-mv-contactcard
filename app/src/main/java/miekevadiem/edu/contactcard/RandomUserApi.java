package miekevadiem.edu.contactcard;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RandomUserApi {

    private static RandomUserApi instance = null;
    private static final String endpoint = "http://api.randomuser.me/";

    public RequestQueue requestQueue;

    private RandomUserApi(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized RandomUserApi getInstance(Context context)
    {
        if (null == instance) {
            instance = new RandomUserApi(context);
        }

        return instance;
    }

    //this is so we don't need to pass context each time
    public static synchronized RandomUserApi getInstance()
    {
        if (null == instance)
        {
            throw new IllegalStateException(RandomUserApi.class.getSimpleName() + " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void getResults(final ApiResponseListener<String> listener) {
        String path = "?results=25&nat=nl";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint + path, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Contact> contacts = new ArrayList<Contact>();

                        try {
                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonContact = results.getJSONObject(i);
                                Contact contact = JSONtoContact(jsonContact);
                                contacts.add(contact);
                            }

                            listener.getResult(contacts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        throw new RuntimeException("Connection to RandomUserAPI failed. Try again");
                    }
                });

        requestQueue.add(jsObjRequest);
    }



    public Contact JSONtoContact(JSONObject jsonContact) throws JSONException {
        String first = jsonContact.getJSONObject("user").getJSONObject("name").getString("first");
        String last = jsonContact.getJSONObject("user").getJSONObject("name").getString("last");
        String email = jsonContact.getJSONObject("user").getString("email");
        String imageUrl = jsonContact.getJSONObject("user").getJSONObject("picture").getString("medium");

        Contact contact = new Contact(first, last, email, imageUrl);
        return contact;
    }


    public void getImage(String url, final ApiImageResponseListener listener) {
        ImageRequest request = new ImageRequest(url,
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    listener.getResult(bitmap);
                }
            }, 0, 0, Bitmap.Config.RGB_565,
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });

        requestQueue.add(request);
    }

    public interface ApiResponseListener<T>
    {
        void getResult(ArrayList<Contact> contacts);
    }

    public interface ApiImageResponseListener<T>
    {
        void getResult(Bitmap bitmap);
    }


}
