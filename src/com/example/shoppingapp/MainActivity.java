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
import com.example.shoppingapp.JSONobjParser.SubCat;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView txtv;
	ListView listV;
	AdapterForThis comboAdap;
	AdapterForThisSubC comboAdapSubC;
	OnItemClickListener onClk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtv=(TextView) findViewById(R.id.info);
		listV=(ListView) findViewById(R.id.list);
		final JsonParser jsop=new JsonParser();
		new ConnectionTask().execute("a");
		
		onClk=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String identifier=((TextView) arg1.findViewById(R.id.identifier)).getText().toString();
				//Toast.makeText(getApplicationContext(), "Clicked item is"+identifier, Toast.LENGTH_LONG).show();
				new ConnectionTaskSubc().execute(identifier);
				// TODO Auto-generated method stub
				
			}
		};
		
	}
	private class AdapterForThisSubC extends ArrayAdapter<SubCat>{
		private List<SubCat> objects;
		public AdapterForThisSubC(Context context, int resource, List<SubCat> objects) {
			super(context, resource, objects);
			this.objects=objects;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.customlistitemsubc, null);
			}
			SubCat data=objects.get(position);

			TextView text=(TextView) v.findViewById(R.id.text1subc);
			TextView ident=(TextView) v.findViewById(R.id.identifiersubc);
			TextView level=(TextView) v.findViewById(R.id.levelsubc);
			ident.setText(data.id);
			level.setText("subcat");
			text.setText(data.title);
			
			
			return v;
		}

	}
	private class AdapterForThis extends ArrayAdapter<Catagory>{
		private List<Catagory> objects;
		public AdapterForThis(Context context, int resource, List<Catagory> objects) {
			super(context, resource, objects);
			this.objects=objects;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.customlistitem, null);
			}
			Catagory data=objects.get(position);

			TextView text=(TextView) v.findViewById(R.id.text1);
			ImageView img=(ImageView) v.findViewById(R.id.image1);
			TextView ident=(TextView) v.findViewById(R.id.identifier);
			TextView level=(TextView) v.findViewById(R.id.level);
			ident.setText(data.id);
			level.setText("cate");
			text.setText(data.title);
			img.setImageDrawable(data.img);
			
			return v;
		}

	}	
	
	private class ConnectionTask extends AsyncTask<String, Void, String> {
		String stri;
		ArrayList<Catagory> re;
		final JsonParser jsop=new JsonParser();
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject job=jsop.JsonParse("http://bb.apiary.io/");
			re=new JSONobjParser().JSONobjParseCat(job);
			return stri;
		}
		@Override
		protected void onPostExecute(String result) {
			comboAdap=new AdapterForThis(MainActivity.this, R.layout.customlistitem, re);
			listV.setAdapter(comboAdap);
			listV.setOnItemClickListener(onClk);
			txtv.setText(stri);
		}

	}
	
	private class ConnectionTaskSubc extends AsyncTask<String, Void, String> {
		String stri;
		ArrayList<SubCat> resp;
		final JsonParser jsop=new JsonParser();
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject job=jsop.JsonParse("http://bb.apiary.io/categories/"+arg0[0]);
			resp=new JSONobjParser().JSONobjParseSubCat(job);
			return arg0[0];
		}
		@Override
		protected void onPostExecute(String result) {
			if(resp==null) { 
				Toast.makeText(getApplicationContext(), "broke"+result, Toast.LENGTH_LONG).show();
				return;
			}
			Toast.makeText(getApplicationContext(), resp.get(0).toString(), Toast.LENGTH_LONG).show();
			comboAdapSubC=new AdapterForThisSubC(MainActivity.this, R.layout.customlistitemsubc, resp);
			listV.setAdapter(comboAdapSubC);
		//	listV.setOnItemClickListener(onClk);
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

