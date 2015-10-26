package com.example.geek.tipstat;

import dmax.dialog.SpotsDialog;
import android.app.AlertDialog;
import android.graphics.Canvas;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import dmax.dialog.SpotsDialog;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.ApiUtils;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
public class MainActivity extends AppCompatActivity {
  Context ctx;
  String str;
  ListView lv=null;
  Context context;
  //private ProgressDialog pDialog;
 private AlertDialog dialog;
  ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

  // URL to get contacts JSON
  private static String url = "http://tipstat.0x10.info/api/tipstat?type=json&query=list_status";
  private static String url2="http://tipstat.0x10.info/api/tipstat?type=json&query=api_hits";
  EditText inputSearch;
  // contacts JSONArray
  JSONArray contacts = null;
  JSONArray contact1=null;

  // Hashmap for ListView
  ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ctx = this;
    setContentView(R.layout.activity_main);
//Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//setSupportActionBar(toolbar);
   // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
       // WindowManager.LayoutParams.FLAG_FULLSCREEN);
    new GetContacts().execute();

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //Target viewTarget = new ViewTarget(R.id.button, this);
    new ShowcaseView.Builder(this)
        .setTarget(new ViewTarget(R.id.imageView, this))
        .setContentTitle(R.string.custom_text_painting_title)
        .setContentText(R.string.custom_text_painting_text)
        .setStyle(R.style.ShowcaseView_Light)
        .build();

  }

  public void welcome(View view){
    TextView t1 = (TextView)findViewById(R.id.str);
   // t1.setText(str);
    Snackbar.make(view,"API HITS: "+str,Snackbar.LENGTH_LONG).setAction("Action",null).show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
  {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.setHeaderTitle("Sort By:-");
    menu.add(0, v.getId(), 0, "Height");//groupId, itemId, order, title
    //menu.add(0, v.getId(), 0, "Price");
    menu.add(0, v.getId(), 0, "Weight");
  }
  @Override
  public boolean onContextItemSelected(MenuItem item){
    if(item.getTitle()=="Height"){
      Collections.sort(contactList, new Comparator<HashMap<String, String>>() {

        @Override
        public int compare(HashMap<String, String> lhs,
                           HashMap<String, String> rhs) {
          // Do your comparison logic here and retrn accordingly.
          return lhs.get("height").compareTo(rhs.get("height"));
        }
      });
      lv.setAdapter(new CustomAdapter2(MainActivity.this, contactList));

    }
    else if(item.getTitle()=="Weight"){
      Collections.sort(contactList, new Comparator<HashMap< String,String >>() {

        @Override
        public int compare(HashMap<String, String> lhs,
                           HashMap<String, String> rhs) {
          // Do your comparison logic here and retrn accordingly.
          return lhs.get("weight").compareTo(rhs.get("weight"));
        }
      });
      lv.setAdapter(new CustomAdapter2(MainActivity.this, contactList));
    }
    else{
      return false;
    }
    return true;
  }




  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
  private class GetContacts extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      // Showing progress dialog
      dialog = new SpotsDialog(MainActivity.this);
      dialog.show();
      //dialog.show();

     // pDialog = new ProgressDialog(MainActivity.this);
      //pDialog.setMessage("Please wait...");
      //pDialog.setCancelable(false);
      //pDialog.show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
      // Creating service handler class instance
      ServiceHandler sh = new ServiceHandler();

      // Making a request to url and getting response
      String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
      String aa = sh.makeServiceCall(url2,ServiceHandler.GET);
      Log.d("Response: ", "> " + jsonStr);

      if (jsonStr != null) {
        try {
          // JSONObject jsonObj = new JSONObject(jsonStr);

          // Getting JSON Array node
          JSONObject p=(new JSONObject(jsonStr));
          JSONObject q = (new JSONObject(aa));
          str =  q.getString("api_hits");
          contacts = new JSONArray(p.getString("members"));

          // looping through All Contacts
          for (int i = 0; i < contacts.length(); i++) {
            JSONObject c = contacts.getJSONObject(i);

            String id = c.getString("id");
            String imagee = c.getString("image");
            String urldisplay = imagee;

            String dob = c.getString("dob");
            String status = c.getString("status");
            String ethnicity = c.getString("ethnicity");
            String weight = c.getString("weight");
            String height = c.getString("height");
            String is_veg = c.getString("is_veg");
            String drink = c.getString("drink");

           // JSONObject contact1 = c.getJSONObject("location");

            //String latitude=contact1.getString("latitude");
            //String longitude=contact1.getString("longitude");
                    /*
                    // looping through All Contacts
                    for (int j = 1; j < contact1.length(); j++) {
                        JSONObject c1 = contact1.getJSONObject(j);

                        city = city +","+ c1.getString("city");
                         users= users +","+ c1.getString("users");
                    }
                    */
            // tmp hashmap for single contact
            HashMap<String, String> contact = new HashMap<String, String>();

            // adding each child node to HashMap key => value
            contact.put("id", id);
            contact.put("imagee", imagee);
            contact.put("status", status);
            contact.put("weight", weight);
            contact.put("height", height);
            contact.put("is_veg", is_veg);
            contact.put("drink", drink);
            contact.put("dob", dob);
            contact.put("ethnicity", ethnicity);
           // contact.put("longitude", longitude);
            //contact.put("date", date);
            //contact.put("link", link);

            // adding contact to contact list
            contactList.add(contact);
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      } else {
        Log.e("ServiceHandler", "Couldn't get any data from the url");
      }

      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      super.onPostExecute(result);
      // Dismiss the progress dialog
      if (dialog.isShowing())
        dialog.dismiss();

    //  ImageView img=(ImageView)findViewById(R.id.imageView1);
    //  img.setImageResource(R.drawable.ic_launcher);
      lv=(ListView) findViewById(R.id.list);
      lv.setAdapter(new CustomAdapter2(MainActivity.this, contactList));
      registerForContextMenu(lv);
      // lv.setAdapter(new CustomAdapter1(MainActivity.this, contactList,bitmapArray));
     // TextView t1 = (TextView)findViewById(R.id.str);
     // t1.setText(str);



    }

  }


}
