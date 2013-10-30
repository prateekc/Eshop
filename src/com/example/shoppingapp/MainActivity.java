package com.example.shoppingapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.shoppingapp.JSONobjParser.Catagory;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView txtv;
	String stri="";
	ListView listV;
	AdapterForThis comboAdap;
	Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	c=this;
    //	listV.setSystemUiVisibility(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtv=(TextView) findViewById(R.id.info);
        listV=(ListView) findViewById(R.id.list);
        final JsonParser jsop=new JsonParser();

        new ConnectionTask().execute("a");	
    }
    private class AdapterForThis extends ArrayAdapter<Catagory>{
    	private List<Catagory> objects;
		public AdapterForThis(Context context, int resource, List<Catagory> objects) {
			super(context, resource, objects);
			this.objects=objects;
			// TODO Auto-generated constructor stub
		}
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent){
////			 View v = convertView;
////		        if (v == null) {
////		            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////		            v = vi.inflate(R.layout.customlistitem, null);
////		        }
//		Catagory data=objects.get(position);
//		
//		TextView text=(TextView) findViewById(R.id.text11);
//		//ImageView img=(ImageView) findViewById(R.id.image1);
//		Toast.makeText(getApplicationContext(), "Clicked item is"+position, Toast.LENGTH_LONG).show();
//		//text.setText("here sir");
//		//img.setImageURI(Uri.parse(data.image));
//		return parent;
//		}
    	
    }
    private class ConnectionTask extends AsyncTask<String, Void, String> {
    	String stri;
    	ArrayList<Catagory> re;
    	final JsonParser jsop=new JsonParser();
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject job=jsop.JsonParse();
			try {
				re=new JSONobjParser().JSONobjParseCat(job);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return stri;
		}
		@Override
		protected void onPostExecute(String result) {
			comboAdap=new AdapterForThis(c, R.layout.customlistitem1, re);
			stri=re.get(1).toString();
			listV.setAdapter(comboAdap);
			txtv.setText(stri);
		}
    	
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}

