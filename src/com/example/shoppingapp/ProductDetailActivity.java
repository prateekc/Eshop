package com.example.shoppingapp;

import org.json.JSONObject;

import com.example.shoppingapp.JSONobjParser.ProductForDisp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends Activity {
	TextView title;
	ImageView prodImg;
	TextView price;
	TextView otherDetails;
	TextView description;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_detail);
		Bundle bundle = this.getIntent().getExtras();
        String productID = bundle.getString("prodID");
        title=(TextView) findViewById(R.id.producttitle);
        prodImg=(ImageView) findViewById(R.id.productimage);
        price=(TextView) findViewById(R.id.productprice);
        otherDetails=(TextView) findViewById(R.id.productotherdetails);
        description=(TextView) findViewById(R.id.productdescription);
        new ConnectionTaskProduct().execute(productID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_detail, menu);
		return true;
	}
	private class ConnectionTaskProduct extends AsyncTask<String, Void, String> {
		String stri;
		ProductForDisp displayabelProd;
		final JsonParser jsop=new JsonParser();
		@Override
		protected String doInBackground(String... arg0) {
			JSONObject job=jsop.JsonParse("http://bb.apiary.io/products/"+arg0[0]);
			displayabelProd=new JSONobjParser().JSONobjParserSingProduct(job);
			return arg0[0];
		}
		@Override
		protected void onPostExecute(String result) {
		title.setText(displayabelProd.title);
		prodImg.setImageDrawable(displayabelProd.images.thumb);
		}

	}
}
