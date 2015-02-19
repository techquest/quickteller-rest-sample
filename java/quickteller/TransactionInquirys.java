package com.interswitchng.techquest.interswitch.api.sample.java.quickteller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.interswitchng.techquest.interswitch.api.sample.java.quickteller.util.InterswitchAuth;

public class TransactionInquirys {

	public static final String QUICKTELLER_BASE_URL = "https://172.35.2.5:9080/api/v1/quickteller";
	public static final String QUICKTELLER_BASE_URL2 = "http://172.35.2.5:9080/api/v1/quickteller";

	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String NONCE = "NONCE";
	private static final String SIGNATURE_METHOD = "SIGNATURE_METHOD";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String AUTHORIZATION = "AUTHORIZATION";
	
	private static final String CLIENT_ID = "IKIAD4A4E150C002732AF042E28BD28332DED7C87000"; 
	private static final String CLIENT_SECRET = "ml0q1pCzo1ulgu7QyirH8RpH8K1WRjbl0hu3FBFNfkM=";
	

	public static void main(String args[]) throws NoSuchAlgorithmException,
			JSONException, ClientProtocolException, IOException {
		transactionInquirys();
	}

	public static void transactionInquirys() throws JSONException,
			NoSuchAlgorithmException, ClientProtocolException, IOException {
		// user's details

		String paymentCode = "70001";
		String customerId = "0040556842";
		String cardPan = "";
		String customerMobile = "";
		String pageFlowValues = "DestinationAccountNumber:0040556842|Amount:50000|BankId:31|DestinationAccountType:20|";
		String hashedPIN = "";
		String requestReference = "";
		String siteDomainName = "";
		String deviceTerminalId = "";
		String amount = "";
		String bankCbnCode = "";
		String customerEmail = "";

		String httpMethod = "POST";
		String resourceUrl = QUICKTELLER_BASE_URL + "/transactions/inquirys";
		String resourceUrl2 = QUICKTELLER_BASE_URL2 + "/transactions/inquirys";
		String clientId = CLIENT_ID;
		String clientSecretKey = CLIENT_SECRET;
		String signatureMethod = "SHA-256";

		JSONObject obj = new JSONObject();

		obj.put("paymentCode", paymentCode);
		obj.put("customerId", customerId);
		obj.put("cardPan", cardPan);
		obj.put("customerMobile", customerMobile);
		obj.put("pageFlowValues", pageFlowValues);
		obj.put("hashedPIN", hashedPIN);
		obj.put("requestReference", requestReference);
		obj.put("siteDomainName", siteDomainName);
		obj.put("deviceTerminalId", deviceTerminalId);
		obj.put("amount", amount);
		obj.put("bankCbnCode", bankCbnCode);
		obj.put("customerEmail", customerEmail);

		StringWriter out = new StringWriter();
		obj.write(out);

		String jsonText = out.toString();

		HashMap<String, String> interswitchAuth = InterswitchAuth
				.generateInterswitchAuth(httpMethod, resourceUrl, clientId,
						clientSecretKey, "", signatureMethod);

		// Write HTTP request to post
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(resourceUrl2);

		post.setHeader("Authorization", interswitchAuth.get(AUTHORIZATION));
		post.setHeader("Timestamp", interswitchAuth.get(TIMESTAMP));
		post.setHeader("Nonce", interswitchAuth.get(NONCE));
		post.setHeader("Signature", interswitchAuth.get(SIGNATURE));
		post.setHeader("SignatureMethod", interswitchAuth.get(SIGNATURE_METHOD));
		post.setHeader("TerminalId", "3IWP0001");
		StringEntity entity = new StringEntity(jsonText);

		entity.setContentType("application/json");
		post.setEntity(entity);

		HttpResponse response = client.execute(post);

		int responseCode = response.getStatusLine().getStatusCode();

		HttpEntity httpEntity = response.getEntity();
		InputStream inputStream = httpEntity.getContent();
		StringBuffer resposeString = new StringBuffer();

		int c;
		while ((c = inputStream.read()) != -1) {
			resposeString.append((char) c);
		}

		// Printout responseCode
		System.out.println(responseCode);

		// Printout responseString
		System.out.println(resposeString);
	}
}
