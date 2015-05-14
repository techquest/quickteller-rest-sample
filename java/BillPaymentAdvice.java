package com.interswitchng.techquest.quickteller.sample.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.interswitchng.techquest.quickteller.sample.rest.util.InterswitchAuth;

public class BillPaymentAdvice {

	public static final String QUICKTELLER_BASE_URL = "http://sandbox.interswitchng.com/api/v1/quickteller";

	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String NONCE = "NONCE";
	private static final String SIGNATURE_METHOD = "SIGNATURE_METHOD";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String AUTHORIZATION = "AUTHORIZATION";
	
	private static final String CLIENT_ID = "your client ID"; 
	private static final String CLIENT_SECRET = "your client secret";

	public static void main(String args[]) throws NoSuchAlgorithmException,
			IllegalStateException, JSONException, IOException {
		billPaymentAdvice();
	}

		public static void billPaymentAdvice() throws JSONException,
			NoSuchAlgorithmException, IllegalStateException, IOException {

		String transactionRef = "IWP|T|Web|3IWP0001|QTFT|290115090524|00000030";
		String deviceTerminalId = "3IWP0076";
		String bankCbnCode = "058";

		String httpMethod = "POST";
		String resourceUrl = QUICKTELLER_BASE_URL + "/payments/advices";
		String clientId = CLIENT_ID;
		String clientSecretKey = CLIENT_SECRET;
		String signatureMethod = "SHA-256";

		JSONObject obj = new JSONObject();

		obj.put("terminalId", deviceTerminalId);
		obj.put("bankCbnCode", bankCbnCode);

		obj.put("transactionRef", transactionRef);

		StringWriter out = new StringWriter();
		obj.write(out);

		String jsonText = out.toString();

		HashMap<String, String> interswitchAuth = InterswitchAuth
				.generateInterswitchAuth(httpMethod, resourceUrl, clientId,
						clientSecretKey, "", signatureMethod);

		// Write HTTP request to post
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(resourceUrl);

		post.setHeader("Authorization", interswitchAuth.get(AUTHORIZATION));
		post.setHeader("Timestamp", interswitchAuth.get(TIMESTAMP));
		post.setHeader("Nonce", interswitchAuth.get(NONCE));
		post.setHeader("Signature", interswitchAuth.get(SIGNATURE));
		post.setHeader("SignatureMethod", interswitchAuth.get(SIGNATURE_METHOD));
		// post.setHeader("TerminalId", "3VRV0001");
		StringEntity entity = new StringEntity(jsonText);

		entity.setContentType("application/json");
		post.setEntity(entity);
		
		// get responseCode
		HttpResponse response = client.execute(post);
		int responseCode1 = response.getStatusLine().getStatusCode();
		
		HttpEntity httpEntity = response.getEntity();
		if(httpEntity != null){
			InputStream inputStream = httpEntity.getContent();
			StringBuffer resposeString = new StringBuffer();

			int c;
			while ((c = inputStream.read()) != -1) {
				resposeString.append((char) c);
			}
			// Printout responseString
			System.out.println(resposeString);

		}
		
		// Printout responseCode
		System.out.println(responseCode1);
		
		
	}
}
