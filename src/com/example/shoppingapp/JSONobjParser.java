package com.example.shoppingapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.provider.MediaStore.Images;
import android.widget.Toast;

import com.example.shoppingapp.JSONobjParser.Catagory;

public class JSONobjParser {

	public ArrayList<Catagory> JSONobjParseCat(JSONObject job){


		try{
			ArrayList<Catagory> response=new ArrayList<Catagory>();
			JSONArray categories=job.getJSONArray("categories");
			for (int i=0; i<categories.length(); i++){
				response.add(new Catagory(categories.getJSONObject(i)));
			}
			return response;
		}catch (Exception e){

		}
		return null;

	}
	/**
	 * 
	 * @param 
	 * @return
	 */
	public ArrayList<SubCat> JSONobjParseSubCat(JSONObject job){


		try{
			ArrayList<SubCat> response=new ArrayList<SubCat>();
			JSONArray categories=job.getJSONArray("categories");
			for (int i=0; i<categories.length(); i++){
				response.add(new SubCat(categories.getJSONObject(i)));
			}
			return response;
		}catch (Exception e){

		}
		return null;

	}

	public ArrayList<Product> JSONobjParseProduct(JSONObject job){


		try{
			ArrayList<Product> response=new ArrayList<Product>();
			JSONArray products=job.getJSONArray("products");
			for (int i=0; i<products.length(); i++){
				response.add(new Product(products.getJSONObject(i)));
			}
			return response;
		}catch (Exception e){
			e.printStackTrace();

		}
		return null;

	}
	public ProductForDisp JSONobjParserSingProduct(JSONObject job){
		ProductForDisp response=new ProductForDisp(job);
		return response;
		
	}
	public class Catagory{
		String title;
		String id;
		String href;
		String image;
		Drawable img;
		public Catagory(JSONObject inputObj){
			try {
				this.title=inputObj.getString("title");
				this.id=inputObj.getString("id");
				this.href=inputObj.getString("href");
				this.image=inputObj.getString("image");
				img=Drawable.createFromStream((InputStream) new URL(image).getContent(), "src");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return title+id+href+image;
		}
	}
	public class SubCat{
		String title;
		String id;
		String href;
		public SubCat(JSONObject inputObj){
			try {
				this.title=inputObj.getString("title");
				this.id=inputObj.getString("id");
				this.href=inputObj.getString("href");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return title+"   "+id+"  "+href;
		}

	}
	public class Product{
		String id;
		String title;
		Price price;
		String promoDesc;
		String promoHref;
		String href;
		String condition;
		imageUrl images;
		public Product(JSONObject inputObj){
			try {
				this.title=inputObj.getString("title");
				this.id=inputObj.getString("id");
				this.href=inputObj.getString("href");
				this.condition=inputObj.getString("condition");
				this.price=new Price(inputObj.getJSONObject("price"));
				this.images=new imageUrl(inputObj.getJSONObject("image"),inputObj.getJSONArray("images").getJSONObject(0));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return title+"\n"+price.currency+":"+price.value;
		}
	}
	public class Price{
		String retail;
		String sale;
		String value;
		String currency;
		public Price(JSONObject inputObj){
			try {
				this.retail=inputObj.getString("retail");
				this.sale=inputObj.getString("sale");
				this.value=inputObj.getString("value");
				this.currency=inputObj.getString("currency");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class imageUrl{
		String alterText;
		//String full;
		Drawable full;
		Drawable thumb;
		Drawable thumbSmall;
		Drawable thumbLarge;
		public imageUrl(JSONObject inputObj, JSONObject inputObj1){
			try {
				this.full=getDrawable(inputObj.getString("full"));
				this.alterText=inputObj1.getString("alt");
				this.thumb=getDrawable(inputObj1.getString("thumb"));
				this.thumbSmall=getDrawable(inputObj1.getString("1x"));
				this.thumbLarge=getDrawable(inputObj1.getString("2x"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public Drawable getDrawable(String url) throws MalformedURLException, IOException{
			return Drawable.createFromStream((InputStream) new URL(url).getContent(), "src");

		}
	}
	public class ProductForDisp{
		Id id;
		String title;
		Price price;
		Description description;
		String Brand;
		String promoDesc;
		String promoHref;
		imageUrl images;
		public ProductForDisp(JSONObject inputObj){
			try {
				this.title=inputObj.getString("title");
				this.id=new Id(inputObj.getJSONObject("identifiers"));
				this.Brand=inputObj.getString("brand");
				this.description=new Description(inputObj.getJSONObject("description"));
				//this.price=new Price(inputObj.getJSONObject("price"));
				this.images=new imageUrl(inputObj.getJSONObject("image"),inputObj.getJSONArray("images").getJSONObject(0));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return title+"\n"+price.currency+":"+price.value;
		}
	}
	public class Id{
		String id;
		String sku;
		String upc;
		public Id(JSONObject input){
			try {
				this.id=input.getString("id");
				this.sku=input.getString("sku");
				this.upc=input.getString(upc);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class Description{
		String desc;
		String bullets="";
		public Description(JSONObject input){
			try {
				this.desc=input.getString("leadingEquity");
				JSONArray tempUse=input.getJSONArray("bullets");
				for(int i=0; i<tempUse.length();i++){
					bullets=bullets+tempUse.getJSONObject(i)+"\n";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
