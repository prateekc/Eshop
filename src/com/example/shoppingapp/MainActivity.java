package com.example.shoppingapp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
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

import com.example.shoppingapp.JSONobjParser.Catagory;
import com.example.shoppingapp.JSONobjParser.Product;
import com.example.shoppingapp.JSONobjParser.SubCat;

public class MainActivity extends Activity {
	TextView txtv;
	ListView listV;
	AdapterForThis comboAdap;
	AdapterForThisSubC comboAdapSubC;
	AdapterForThisProduct comboAdapProd;
	OnItemClickListener onClk;
	OnItemClickListener onClkSubC;
	OnItemClickListener onClkProduct;
	ArrayList<Product> respProd; //arraylists of processed json objects to be shown in list
	ArrayList<Catagory> respCate;
	ArrayList<SubCat> respSubCat;
	String level="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtv=(TextView) findViewById(R.id.info);
		listV=(ListView) findViewById(R.id.list);
		
		//checking connection to Internet
		if(connectionValidatro()){
		new ConnectionTask().execute("a");
		}else{
			Toast.makeText(getApplicationContext(), "please connect to internet", Toast.LENGTH_SHORT).show();
		}
		//Listner for catagory list
		onClk=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				level="subCat";
				String identifier=((TextView) arg1.findViewById(R.id.identifier)).getText().toString();
				new ConnectionTaskSubc().execute(identifier);
				// TODO Auto-generated method stub

			}
		};
		//Listener for subcatagory list
		onClkSubC=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				level="product";
				String identifier=((TextView) arg1.findViewById(R.id.identifiersubc)).getText().toString();
				new ConnectionTaskProduct().execute(identifier);
				// TODO Auto-generated method stub

			}
		};
		//listner for product list
		onClkProduct=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String identifier=((TextView) arg1.findViewById(R.id.identifierprod)).getText().toString();
				Bundle bundle=new Bundle();
				bundle.putString("prodID", identifier);//sending product identifier to new list
				Intent newIntent = new Intent(MainActivity.this, ProductDetailActivity.class);
				newIntent.putExtras(bundle);
				startActivity(newIntent);
				// TODO Auto-generated method stub

			}
		};

	}
	//Arrayadapter for SubCatagory List
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
	//Array Adapter for Catagory List
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
	//ArrayAdapter for Products list
	private class AdapterForThisProduct extends ArrayAdapter<Product>{
		private List<Product> objects;
		public AdapterForThisProduct(Context context, int resource, List<Product> objects) {
			super(context, resource, objects);
			this.objects=objects;
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.customlistitemproduct, null);
			}
			Product data=objects.get(position);
			TextView text=(TextView) v.findViewById(R.id.text1prod);
			ImageView img=(ImageView) v.findViewById(R.id.image1prod);
			TextView ident=(TextView) v.findViewById(R.id.identifierprod);
			TextView level=(TextView) v.findViewById(R.id.levelprod);
			ident.setText(data.id);
			level.setText("subcat");
			text.setText(data.toString());
			img.setImageDrawable(data.images.thumbSmall);
			return v;
		}

	}
	//Async connection tasks for each eccess defined below these send a request to the JsonParser to get json objects and JSONobjParser for
	//converting JSON objects into processed custom data objects. This helps keep things simple and also if i wanted to use breadcrums to show the user path he 
	//took to current state
	private class ConnectionTask extends AsyncTask<String, Void, String> {
		String stri;

		final JsonParser jsop=new JsonParser();
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject job=jsop.JsonParse("http://bb.apiary.io/");
			respCate=new JSONobjParser().JSONobjParseCat(job);
			return stri;
		}
		@Override
		protected void onPostExecute(String result) {
			comboAdap=new AdapterForThis(MainActivity.this, R.layout.customlistitem, respCate);
			listV.setAdapter(comboAdap);
			listV.setOnItemClickListener(onClk);
			txtv.setText(stri);
		}

	}

	private class ConnectionTaskSubc extends AsyncTask<String, Void, String> {
		String stri;

		final JsonParser jsop=new JsonParser();
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject job=jsop.JsonParse("http://bb.apiary.io/categories/"+arg0[0]);
			respSubCat=new JSONobjParser().JSONobjParseSubCat(job);
			return arg0[0];
		}
		@Override
		protected void onPostExecute(String result) {
			comboAdapSubC=new AdapterForThisSubC(MainActivity.this, R.layout.customlistitemsubc, respSubCat);
			listV.setAdapter(comboAdapSubC);
			listV.setOnItemClickListener(onClkSubC);
			txtv.setText(stri);
		}

	}
	private class ConnectionTaskProduct extends AsyncTask<String, Void, String> {
		String stri;

		final JsonParser jsop=new JsonParser();
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject job=jsop.JsonParse("http://bb.apiary.io/categories/"+arg0[0]+"/products");
			respProd=new JSONobjParser().JSONobjParseProduct(job);
			return arg0[0];
		}
		@Override
		protected void onPostExecute(String result) {
			comboAdapProd=new AdapterForThisProduct(MainActivity.this, R.layout.customlistitemproduct, respProd);
			listV.setAdapter(comboAdapProd);
			listV.setOnItemClickListener(onClkProduct);
			txtv.setText(stri);
		}

	}
	//back button functionality implementation, uses level to know current location
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (level.equals("")){
				finish();
				System.exit(0);
			} else if(level.equals("subCat")){
				level="";
				listV.setAdapter(comboAdap);
				listV.setOnItemClickListener(onClk);
			} else if(level.equals("product")){
				level="subCat";
				listV.setAdapter(comboAdapSubC);
				listV.setOnItemClickListener(onClkSubC);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 
	 * @return true if network access exists
	 */
	public boolean connectionValidatro(){
		return	((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

