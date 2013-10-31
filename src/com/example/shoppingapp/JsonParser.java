package com.example.shoppingapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class JsonParser {
	
	public JSONObject JsonParse(String url){
		try{
			JSONObject job;
	        DefaultHttpClient client=new DefaultHttpClient();
	        HttpGet getReq=new HttpGet();
	        URI web=new URI(url);
	        getReq.setURI(web);
	        getReq.setHeader("accept", "application/json");
	        HttpResponse resp=client.execute(getReq);
	        InputStream inps=resp.getEntity().getContent();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                inps, "iso-8859-1"));
	        
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	        inps.close();
	        
	        String jsonO=sb.toString();
	   //     System.out.println(jsonO);
	        job=new JSONObject(jsonO);
	        return job;
	        }
	        catch(Exception e){
	        	System.out.println(e.toString());
	        	//return e.toString();
	        	
	        }
		return null;
	}
}
