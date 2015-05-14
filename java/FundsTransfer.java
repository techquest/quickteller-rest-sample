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

public class FundTransfer {

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
		fundTransfer();
	}

		public static void transfers() throws Exception {
		
		String initiatingEntityCode = "PBL";
		String transferCode = "9898448142366";

		// sender
		String senderLastname = "Amina";
		String senderOtherNames = "Joseph";
		String senderEmail = "Joseph@yahoo.com";
		String senderPhone = "080xxxxxxxx";

		// beneficiary
		String beneficiaryLastname = "Ade";
		String beneficiaryOthernames = "Chuks";

		// initiation
		String initiationAmount = "100000";
		String initiationChannel = "7";
		String initiationPaymentMethodCode = "CA";
		String initiationCurrencyCode = "566";

		// termination
		String terminationPaymentMethodCode = "AC";
		String terminationAmount = "100000";
		String terminationCurrencyCode = "566";
		String terminationCountryCode = "NG";
		String terminationEntityCode = "058";

		// accountRecievable
		String accountNumber = "534233";
		String accountType = "20";
		
		String MAC = MACKey.generatemacKey(initiationAmount, initiationCurrencyCode, initiationPaymentMethodCode, 
				terminationAmount, terminationCurrencyCode, terminationPaymentMethodCode, terminationCountryCode);

		JSONObject json = new JSONObject();
		json.put("transferCode", transferCode);
		json.put("mac", MAC);
		json.put("initiatingEntityCode", initiatingEntityCode);

		JSONObject sender = new JSONObject();
		sender.put("lastname", senderLastname);
		sender.put("othernames", senderOtherNames);
		sender.put("email", senderEmail);
		sender.put("phone", senderPhone);

		JSONObject beneficiary = new JSONObject();
		beneficiary.put("lastname", beneficiaryLastname);
		beneficiary.put("othernames", beneficiaryOthernames);

		JSONObject initiation = new JSONObject();
		initiation.put("amount", initiationAmount);
		initiation.put("channel", initiationChannel);
		initiation.put("paymentMethodCode", initiationPaymentMethodCode);
		initiation.put("currencyCode", initiationCurrencyCode);

		JSONObject accountRecievable = new JSONObject();
		accountRecievable.put("accountNumber", accountNumber);
		accountRecievable.put("accountType", accountType);

		JSONObject termination = new JSONObject();
		termination.put("paymentMethodCode", terminationPaymentMethodCode);
		termination.put("amount", terminationAmount);
		termination.put("currencyCode", terminationCurrencyCode);
		termination.put("countryCode", terminationCountryCode);
		termination.put("entityCode", terminationEntityCode);
		termination.put("accountReceivable", accountRecievable);

		json.put("sender", sender);
		json.put("beneficiary", beneficiary);
		json.put("initiation", initiation);
		json.put("termination", termination);
		json.put("terminalId","3GTI0001");

		String httpMethod = "POST";
		String resourceUrl = QUICKTELLER_BASE_URL + "/payments/transfers";
		String resourceUrl2 = QUICKTELLER_BASE_URL2 + "/payments/transfers";
		String clientId = CLIENT_ID;
		String clientSecretKey = CLIENT_SECRET;
		String signatureMethod = "SHA-256";

		StringWriter out = new StringWriter();
		json.write(out);

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
		StringEntity entity = new StringEntity(jsonText);

		entity.setContentType("application/json");
		post.setEntity(entity);

		HttpResponse response = client.execute(post);
		int responseCode = response.getStatusLine().getStatusCode();
		HttpEntity httpEntity = response.getEntity();
		InputStream inputStream = httpEntity.getContent();
		StringBuffer stringBuffer = new StringBuffer();

		int c;
		while ((c = inputStream.read()) != -1) {
			stringBuffer.append((char) c);
		}

		// Printout response
		System.out.println(responseCode);
		System.out.println(stringBuffer);
	}
}
