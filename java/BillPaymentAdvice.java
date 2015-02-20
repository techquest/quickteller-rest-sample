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

	public static final String QUICKTELLER_BASE_URL = "https://sandbox.interswitchng.com/api/v1/quickteller";
	public static final String QUICKTELLER_BASE_URL2 = "http://sandbox.interswitchng.com/api/v1/quickteller";

	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String NONCE = "NONCE";
	private static final String SIGNATURE_METHOD = "SIGNATURE_METHOD";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String AUTHORIZATION = "AUTHORIZATION";
	
	private static final String CLIENT_ID = "IKIAD4A4E150C002732AF042E28BD28332DED7C87000"; 
	private static final String CLIENT_SECRET = "ml0q1pCzo1ulgu7QyirH8RpH8K1WRjbl0hu3FBFNfkM=";

	public static void main(String args[]) throws NoSuchAlgorithmException,
			IllegalStateException, JSONException, IOException {
		billPaymentAdvice();
	}

	public static void billPaymentAdvice() throws JSONException,
			NoSuchAlgorithmException, IllegalStateException, IOException {
		// user's details

		String amount = "";
		String paymentCode = "";
		String customerId = "";
		String requestReference = "";
		String pageFlowValues = "";
		String transactionRef = "IWP|T|Web|3IWP0001|QTFT|190215100042|00000001";
		String isThirdPartyAdvice = "";
		String responseCode = "";
		String customerName = "";
		String rechargePin = "";
		String wpdRef = "";
		String beneficiaryName = "";
		String identificationType = "";
		String identificationDescription = "";
		String phcnTokenDetails = "";
		String isInjectedTransaction = "";
		String cardPan = "";
		String customerMobile = "";
		String customerEmail = "";
		String hashedPIN = "";
		String siteDomainName = "";
		String deviceTerminalId = "3IWP0001";
		String bankCbnCode = "058";

		String httpMethod = "POST";
		String resourceUrl = QUICKTELLER_BASE_URL + "/sendAdviceRequest";
		String resourceUrl2 = QUICKTELLER_BASE_URL2 + "/sendAdviceRequest";
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
		obj.put("terminalId", deviceTerminalId);
		obj.put("amount", amount);
		obj.put("bankCbnCode", bankCbnCode);
		obj.put("customerEmail", customerEmail);

		obj.put("transactionRef", transactionRef);
		obj.put("isThirdPartyAdvice", isThirdPartyAdvice);
		obj.put("responseCode", responseCode);
		obj.put("customerName", customerName);
		obj.put("rechargePin", rechargePin);
		obj.put("wpdRef", wpdRef);
		obj.put("beneficiaryName", beneficiaryName);
		obj.put("identificationType", identificationType);
		obj.put("identificationDescription", identificationDescription);
		obj.put("phcnTokenDetails", phcnTokenDetails);
		obj.put("isInjectedTransaction", isInjectedTransaction);

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
		// post.setHeader("TerminalId", "3VRV0001");
		StringEntity entity = new StringEntity(jsonText);

		entity.setContentType("application/json");
		post.setEntity(entity);
		
		// get responseCode
		HttpResponse response = client.execute(post);
		int responseCode1 = response.getStatusLine().getStatusCode();

		// Printout responseCode
		System.out.println(responseCode1);

	}
}
