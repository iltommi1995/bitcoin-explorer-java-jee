package com.z9devs.bitcoinexplorerjee.utils.http;

import java.util.ArrayList;

public class ReqBodyFormatter 
{
	public static String formatReqBody(String method, ArrayList<String> params) {
		// Aggiungo il metodo
		String reqBody = String.format("{\"jsonrpc\": \"1.0\", \"id\": \"curltest\", \"method\": \"%s\", \"params\": [", method);
		
		switch(method) {
			case "getblockchaininfo":
				break;
			case "getblock":
				reqBody += String.format("\"%s\"", params.get(0));
				break;
			case "getblockstats":
				reqBody += String.format("\"%s\", [", params.get(0));
				System.out.println(params.size());
				System.out.println(params.get(0));
				for(int i = 1; i < params.size(); i++) {
					System.out.println(params.get(i));
					reqBody += String.format("\"%s\",", params.get(i));
				}
				reqBody = reqBody.substring(0, reqBody.length() -1) + "]";
				break;
			case "getrawtransaction":
				reqBody += String.format("\"%s\"", params.get(0));
				break;
		}
		
		reqBody += "]}";
		System.out.println(reqBody);
		return reqBody;
	}
}