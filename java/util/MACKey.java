package com.interswitchng.techquest.interswitch.api.sample.quickteller.apis;

import java.security.MessageDigest;

public class MACKey {

	public static String generatemacKey(String InitiatingAmount,
			String InitiatingCurrencyCode, String InitiatingPaymentMethodCode,
			String TerminatingAmount, String TerminatingCurrencyCode,
			String TerminatingPaymentMethodCode, String TerminatingCountryCode)
			throws Exception {

		String InitAmt = InitiatingAmount == null ? "" : InitiatingAmount;
		String InitCCode = InitiatingCurrencyCode == null ? "": InitiatingCurrencyCode;
		String InitPmc = InitiatingPaymentMethodCode == null ? "": InitiatingPaymentMethodCode;
		String TerAmt = TerminatingAmount == null ? "" : TerminatingAmount;
		String TerCurCode = TerminatingCurrencyCode == null ? "": TerminatingCurrencyCode;
		String TerPmc = TerminatingPaymentMethodCode == null ? "": TerminatingPaymentMethodCode;
		String TerCCode = TerminatingCountryCode == null ? "": TerminatingCountryCode;

		String data = InitAmt + InitCCode + InitPmc + TerAmt + TerCurCode + TerPmc + TerCCode;

		 return hashText(data);
	}

	public static String hashText(String textToHash) throws Exception {
		final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
		byte[] dataBytes = sha512.digest(textToHash.getBytes());

		return convertByteToHex(dataBytes);
	}
	
	public static String convertByteToHex(byte data[]) {
		StringBuffer hexData = new StringBuffer();
		for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
			hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100,
					16).substring(1));

		return hexData.toString();
	}
}
