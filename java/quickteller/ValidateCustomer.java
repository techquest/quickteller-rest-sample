package com.interswitchng.techquest.interswitch.api.sample.java.quickteller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.interswitchng.techquest.interswitch.api.sample.java.quickteller.util.InterswitchAuth;

public class ValidateCustomer {
	public static final String QUICKTELLER_BASE_URL = "http://172.35.2.5:9080/api/v1/quickteller";
	public static final String QUICKTELLER_BASE_URL2 = "https://172.35.2.5:9080/api/v1/quickteller";

	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String NONCE = "NONCE";
	private static final String SIGNATURE_METHOD = "SIGNATURE_METHOD";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String AUTHORIZATION = "AUTHORIZATION";

	private static final String CLIENT_ID = "IKIAD4A4E150C002732AF042E28BD28332DED7C87000"; 
	private static final String CLIENT_SECRET = "ml0q1pCzo1ulgu7QyirH8RpH8K1WRjbl0hu3FBFNfkM=";
	
	
	public static void main (String args[]) throws NoSuchAlgorithmException, IOException, JSONException{
		validations();
	}

public static void validations() throws JSONException, NoSuchAlgorithmException, IOException  {
		
	String paymentCode = "10803";
	String customerId = "iswtester2@yahoo.com";
	String customerEmail = "iswtester2@yahoo.com";

		JSONArray array = new JSONArray();

		JSONObject firstEntry = new JSONObject();
		firstEntry.put("paymentCode", paymentCode);
		firstEntry.put("customerId", customerId);
		firstEntry.put("customerEmail", customerEmail);

		JSONObject secondEntry = new JSONObject();
		secondEntry.put("paymentCode", paymentCode);
		secondEntry.put("customerId", customerId);

		array.put(firstEntry);
		//array.put(secondEntry);

		JSONObject json = new JSONObject();
		json.put("customers", array);

		StringWriter out = new StringWriter();
		json.write(out);

		String jsonText = out.toString();

		String httpMethod = "POST";
		String resourceUrl = QUICKTELLER_BASE_URL + "/customers/validations";
		String resourceUrl2 = QUICKTELLER_BASE_URL2 + "/customers/validations";
		String clientId = CLIENT_ID;
		String clientSecretKey = CLIENT_SECRET;
		String signatureMethod = "SHA-256";

		HashMap<String, String> interswitchAuth = InterswitchAuth
				.generateInterswitchAuth(httpMethod, resourceUrl2, clientId,
						clientSecretKey, "", signatureMethod);

		// Write HTTP request to post
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(resourceUrl);

		post.setHeader("Authorization", interswitchAuth.get(AUTHORIZATION));
		post.setHeader("Timestamp", interswitchAuth.get(TIMESTAMP));
		post.setHeader("Nonce", interswitchAuth.get(NONCE));
		post.setHeader("Signature", interswitchAuth.get(SIGNATURE));
		post.setHeader("SignatureMethod", interswitchAuth.get(SIGNATURE_METHOD));
		post.setHeader("TerminalId", "3IWP0076");
		StringEntity entity = new StringEntity(jsonText);

		entity.setContentType("application/json");
		post.setEntity(entity);

		HttpResponse response = client.execute(post);
		
		int responseCode = response.getStatusLine().getStatusCode(); 
		System.out.println("Response Code : " + responseCode);
		
		HttpEntity httpEntity = response.getEntity();
		InputStream inputStream = httpEntity.getContent();
		StringBuffer stringBuffer = new StringBuffer();
		

		int c;
		while ((c = inputStream.read()) != -1) {
			stringBuffer.append((char) c);
		}

		// Printout response

		System.out.println(stringBuffer);
	}
}
