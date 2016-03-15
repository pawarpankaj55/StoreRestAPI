package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Geocoder {
	
	public static String getLatLng(String zipCode){
		Client client = Client.create();

		WebResource webResource = client.resource("https://maps.googleapis.com/maps/api/geocode/xml?address="+zipCode+"&key=AIzaSyBV9hJ2Xe56RBd_Nt7Z9yZuZdey22EM-PU");

		ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);

		if (response.getStatus() != 200) {
		   throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		}

		String output = response.getEntity(String.class);
        return output;
	}
	
	public static String getLatitude(String response){
		String lat = "000.000";
		Pattern Latitude = Pattern.compile("<location>\\s*<lat>(.*?)</lat>");
		Matcher m = Latitude.matcher(response);
		if(m.find()){
			lat = m.group(1);
		}
		return lat;
	}
	
	public static String getLongitude(String response){
		Pattern Longitude = Pattern.compile("<lng>(.*?)</lng>\\s*</location>");
		String lng = "000.000";
		Matcher m = Longitude.matcher(response);
		if(m.find()){
			lng = m.group(1);
		}
		return lng;
	}
}

