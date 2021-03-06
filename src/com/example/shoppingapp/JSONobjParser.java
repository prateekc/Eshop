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

public class JSONobjParser {
	/**
	 * 
	 * @param job: accepts a JSONObject that is created by JsonParser. converts the object into a custom datatype specific to show category information
	 * @return an arraylist of custom class that contains information to show in the catagories list
	 */

	public ArrayList<Catagory> JSONobjParseCat(JSONObject job){


		try{
			ArrayList<Catagory> response=new ArrayList<Catagory>();
			JSONArray categories=job.getJSONArray("categories");
			for (int i=0; i<categories.length(); i++){
				response.add(new Catagory(categories.getJSONObject(i)));
			}
			return response;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * 
	 * @param job: accepts a JSONObject that is created by JsonParser. converts the object into a custom datatype specific to show subcatagory information
	 * @return  an arraylist of custom class that contains information to show in the subcatagory list
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
	/**
	 * 
	 * @param job: accepts a JSONObject that is created by JsonParser. converts the object into a custom datatype specific to show product information in the list
	 * @return  an arraylist of custom class that contains information to show in the product list
	 */
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

	/**
	 * 
	 * @param job: accepts a JSONObject that is created by JsonParser. converts the object into a custom datatype specific to show product information of proj-
	 * ect page
	 * @return  a custom class that contains information to show in the product informaion on the project page
	 */

	public ProductForDisp JSONobjParserSingProduct(JSONObject job){
		ProductForDisp response=new ProductForDisp(job);
		return response;

	}
	//custom objects for different uses
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
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			String price="Retail price"+this.retail+"\n";

			if(!sale.equals(null)){
				price=price+"Sale:"+this.sale+"\n";
			}
			price=price+"Value: "+this.currency+": "+this.value;

			return price;
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
		Promo promotions;
		imageUrl images;
		public ProductForDisp(JSONObject inputObj){
			try {
				this.title=inputObj.getString("title");
				this.id=new Id(inputObj.getJSONObject("identifiers"));
				this.Brand=inputObj.getString("brand");
				this.description=new Description(inputObj.getJSONObject("description"));
				//this.price=new Price(inputObj.getJSONObject("price"));
				this.images=new imageUrl(inputObj.getJSONObject("image"),inputObj.getJSONArray("images").getJSONObject(0));
				price=new Price(inputObj.getJSONArray("variations").getJSONObject(0).getJSONObject("price"));
				promotions=new Promo(inputObj.getJSONArray("promos"));
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
				this.upc=input.getString("upc");
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
					bullets=bullets+"- "+tempUse.getJSONObject(i)+"\n";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class Promo{
		String promo="";
		public Promo(JSONArray input){
			try{
				for(int i=0; i<input.length();i++){
					promo=promo+input.getJSONObject(i).getString("description");
				}

			}catch(JSONException e){
				e.printStackTrace();
			}
		}
	}


}
