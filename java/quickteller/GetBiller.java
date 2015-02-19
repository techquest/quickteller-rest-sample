package com.interswitchng.techquest.interswitch.api.sample.java.quickteller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import com.interswitchng.techquest.interswitch.api.sample.java.quickteller.util.InterswitchAuth;





public class GetBiller {
	
	public static final String QUICKTELLER_BASE_URL = "https://172.35.2.5:9080/api/v1/quickteller";
	public static final String QUICKTELLER_BASE_URL2 = "http://172.35.2.5:9080/api/v1/quickteller";

	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String NONCE = "NONCE";
	private static final String SIGNATURE_METHOD = "SIGNATURE_METHOD";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String AUTHORIZATION = "AUTHORIZATION";

	private static final String CLIENT_ID = "IKIAD4A4E150C002732AF042E28BD28332DED7C87000"; 
	private static final String CLIENT_SECRET = "ml0q1pCzo1ulgu7QyirH8RpH8K1WRjbl0hu3FBFNfkM=";
	
	public static void main (String args[]) throws NoSuchAlgorithmException, IOException{
		getBiller();
	}

	public static void getBiller() throws NoSuchAlgorithmException, IOException {
		String billerName = "Intensitech";

		String httpMethod = "GET";
		String resourceUrl = QUICKTELLER_BASE_URL + "/billers?billerName=" + billerName;
		String resourceUrl2 = QUICKTELLER_BASE_URL2 + "/billers?billerName=" + billerName;
		
		String clientId = CLIENT_ID;
		String clientSecretKey = CLIENT_SECRET;
		String signatureMethod = "SHA-256";

		HashMap<String, String> interswitchAuth = InterswitchAuth.generateInterswitchAuth(httpMethod, resourceUrl, clientId,
						clientSecretKey, "", signatureMethod);

		URL obj = new URL(resourceUrl2);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", interswitchAuth.get(AUTHORIZATION));
		con.setRequestProperty("Timestamp", interswitchAuth.get(TIMESTAMP));
		con.setRequestProperty("Nonce", interswitchAuth.get(NONCE));
		con.setRequestProperty("Signature", interswitchAuth.get(SIGNATURE));
		con.setRequestProperty("SignatureMethod", interswitchAuth.get(SIGNATURE_METHOD));
		
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		InputStream inputStream = con.getInputStream();

		StringBuffer response = new StringBuffer();

		int c;
		while ((c = inputStream.read()) != -1) {
			response.append((char) c);
		}

		// Printout response
		System.out.println(response);
	}
}
