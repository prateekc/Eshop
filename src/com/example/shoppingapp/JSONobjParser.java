package com.example.shoppingapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
			
		}
		return null;

	}
	
	public class Catagory{
		String title;
		String id;
		String href;
		String image;
		public Catagory(JSONObject inputObj){
			try {
				this.title=inputObj.getString("title");
				this.id=inputObj.getString("id");
				this.href=inputObj.getString("href");
				this.image=inputObj.getString("image");
			} catch (JSONException e) {
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
				JSONObject promo=inputObj.getJSONObject("promos");
				this.promoDesc=promo.getString("description");
				this.promoHref=promo.getString("href");
				this.price=new Price(inputObj.getJSONObject("price"));
				this.images=new imageUrl(inputObj.getJSONObject("image"),inputObj.getJSONObject("images"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		String full;
		String thumb;
		String thumbSmall;
		String thumbLarge;
		public imageUrl(JSONObject inputObj, JSONObject inputObj1){
			try {
				this.full=inputObj.getString("full");
				this.alterText=inputObj1.getString("alt");
				this.thumb=inputObj1.getString("thumb");
				this.thumbSmall=inputObj1.getString("1x");
				this.thumbLarge=inputObj1.getString("2x");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
