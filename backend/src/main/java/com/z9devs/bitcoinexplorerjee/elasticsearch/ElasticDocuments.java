package com.z9devs.bitcoinexplorerjee.elasticsearch;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RequestScoped
public class ElasticDocuments 
{
	// From response build Map with document data
	public Map<String, Object> toMap(String res)
	{
		JsonObject blockJson = JsonParser.parseString(res).getAsJsonObject().get("result").getAsJsonObject();
		
		Map<String, Object> docMap = new HashMap<String, Object>();
		docMap.put("hash", blockJson.get("hash").getAsString());
		docMap.put("height", blockJson.get("height").getAsLong());
		docMap.put("version", blockJson.get("version").getAsLong());
		docMap.put("merkleroot", blockJson.get("merkleroot").getAsString());
		docMap.put("time", blockJson.get("time").getAsLong());
		docMap.put("nonce", blockJson.get("nonce").getAsLong());
		docMap.put("difficulty", blockJson.get("difficulty").getAsLong());
		docMap.put("previousblockhash", blockJson.get("previousblockhash").getAsString());
		docMap.put("size", blockJson.get("size").getAsInt());
		docMap.put("weight", blockJson.get("weight").getAsInt());
		docMap.put("nTx", blockJson.get("nTx").getAsInt());
		
		
		return docMap;
	}
	
}
