package com.interswitchng.techquest.quickteller.sample.rest.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class InterswitchAuth {

	private static final String TIMESTAMP = "TIMESTAMP";
	private static final String NONCE = "NONCE";
	private static final String SIGNATURE_METHOD = "SIGNATURE_METHOD";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String AUTHORIZATION = "AUTHORIZATION";

	private static final String AUTHORIZATION_REALM = "InterswitchAuth";
	private static final String ISO_8859_1 = "ISO-8859-1";

	public static HashMap<String, String> generateInterswitchAuth(
			String httpMethod, String resourceUrl, String clientId,
			String clientSecretKey, String additionalParameters,
			String signatureMethod) throws UnsupportedEncodingException,
			NoSuchAlgorithmException {
		HashMap<String, String> interswitchAuth = new HashMap<String, String>();
		
		//Timezone MUST be Africa/Lagos.
		TimeZone lagosTimeZone = TimeZone.getTimeZone("Africa/Lagos");

		Calendar calendar = Calendar.getInstance(lagosTimeZone);
		
		// Timestamp must be in seconds.
		long timestamp = calendar.getTimeInMillis() / 1000;

		UUID uuid = UUID.randomUUID();
		String nonce = uuid.toString().replaceAll("-", "");

		String clientIdBase64 = new String(Base64.encodeBase64(clientId
				.getBytes()));
		String authorization = AUTHORIZATION_REALM + " " + clientIdBase64;

		String encodedResourceUrl = URLEncoder.encode(resourceUrl, ISO_8859_1);
		String signatureCipher = httpMethod + "&" + encodedResourceUrl + "&"
				+ timestamp + "&" + nonce + "&" + clientId + "&"
				+ clientSecretKey;
		if (additionalParameters != null && !"".equals(additionalParameters))
			signatureCipher = signatureCipher + "&" + additionalParameters;

		MessageDigest messageDigest = MessageDigest
				.getInstance(signatureMethod);
		byte[] signatureBytes = messageDigest
				.digest(signatureCipher.getBytes());
		
		// encode signature as base 64 
		String signature = new String(Base64.encodeBase64(signatureBytes));

		interswitchAuth.put(AUTHORIZATION, authorization);
		interswitchAuth.put(TIMESTAMP, String.valueOf(timestamp));
		interswitchAuth.put(NONCE, nonce);
		interswitchAuth.put(SIGNATURE_METHOD, signatureMethod);
		interswitchAuth.put(SIGNATURE, signature);

		return interswitchAuth;
	}
}
