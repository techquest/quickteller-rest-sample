package com.interswitchng.techquest.quickteller.sample.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.interswitchng.techquest.quickteller.sample.rest.util.InterswitchAuth;

public class CreateUser {
	public static final String QUICKTELLER_BASE_URL = "https://sandbox.interswitchng.com/api/v1/quickteller";
	public static final String QUICKTELLER_BASE_URL2 = "http://sandbox.interswitchng.com/api/v1/quickteller";

	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String NONCE = "NONCE";
	private static final String SIGNATURE_METHOD = "SIGNATURE_METHOD";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String AUTHORIZATION = "AUTHORIZATION";
	
	private static final String CLIENT_ID = "your client ID"; 
	private static final String CLIENT_SECRET = "your client secret";

	public static void main(String args[]) throws NoSuchAlgorithmException,
			ClientProtocolException, JSONException, IOException {
		createUser();
	}

	public static void createUser() throws NoSuchAlgorithmException,
			JSONException, ClientProtocolException, IOException {

		// user's details
		String username = "interswitch";
		String password = "password";
		String address1 = "opp EKO Hotel";
		String addressCity = "Victoria Island";
		String addressState = "Lagos";
		String countryCode = "NG";
		String otherNames = "techquest";
		String lastname = "Limited";
		String phone = "234803XXXXXXX";
		String userEmail = "apidoc@yahoo.com";
		String userMobilePhone = "234803XXXXXXX";
		String title = "Mr";

		String httpMethod = "POST";
		String resourceUrl = QUICKTELLER_BASE_URL + "/users";
		String resourceUrl2 = QUICKTELLER_BASE_URL2 + "/users";
		String clientId = CLIENT_ID;
		String clientSecretKey = CLIENT_SECRET;
		String signatureMethod = "SHA-256";

		JSONObject obj = new JSONObject();

		obj.put("username", username);
		obj.put("password", password);
		obj.put("address1", address1);
		obj.put("addressCity", addressCity);
		obj.put("addressState", addressState);
		obj.put("countryCode", countryCode);
		obj.put("othernames", otherNames);
		obj.put("lastname", lastname);
		obj.put("phone", phone);
		obj.put("email", userEmail);
		obj.put("mobilePhone", userMobilePhone);
		obj.put("title", title);

		StringWriter out = new StringWriter();
		obj.write(out);

		String jsonText = out.toString();

		HashMap<String, String> interswitchAuth = InterswitchAuth
				.generateInterswitchAuth(httpMethod, resourceUrl, clientId,
						clientSecretKey, "", signatureMethod);

		// Write HTTP request to post
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(resourceUrl2);

		// set Headers
		post.setHeader("Authorization", interswitchAuth.get(AUTHORIZATION));
		post.setHeader("Timestamp", interswitchAuth.get(TIMESTAMP));
		post.setHeader("Nonce", interswitchAuth.get(NONCE));
		post.setHeader("Signature", interswitchAuth.get(SIGNATURE));
		post.setHeader("SignatureMethod", interswitchAuth.get(SIGNATURE_METHOD));
		StringEntity entity = new StringEntity(jsonText);

		// set content-type to json
		entity.setContentType("application/json");
		post.setEntity(entity);

		// get responseCode
		HttpResponse response = client.execute(post);
		int responseCode = response.getStatusLine().getStatusCode();

		// Printout responseCode
		System.out.println(responseCode);
	}

}
