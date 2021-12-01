package com.z9devs.bitcoinexplorerjee.elasticsearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

@RequestScoped
public class ElasticService 
{
	@Inject private ElasticClient client;
	
	// Check if index exists
	public boolean checkIndex() 
	{
		try {
			return client.highLevelElasticClient().indices().exists(new GetIndexRequest("blocks"), RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Create new index
	public void createIndex() throws IOException 
	{
		client.highLevelElasticClient().indices().create(new CreateIndexRequest("blocks"), RequestOptions.DEFAULT);
	}
	
	// Search if block exists (by id)
	public SearchResponse searchBlock(String blockhash) throws IOException 
	{
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); 
		sourceBuilder.query(QueryBuilders.termQuery("hash", blockhash));
		
		SearchRequest sr = new SearchRequest()
				.indices("blocks")
				.source(sourceBuilder);
				
		SearchResponse searchResponse = client.highLevelElasticClient()
			.search(sr, RequestOptions.DEFAULT);
		
		return searchResponse;
	}
	
	// Add new block to index
	public IndexResponse addBlock(Map<String, Object> newBlock) throws IOException {
		IndexRequest req = new IndexRequest("blocks"); 
		req.id(newBlock.get("height")+""); 
		req.source(newBlock, XContentType.JSON);
		req.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
		return client.highLevelElasticClient().index(req, RequestOptions.DEFAULT);
	}
}
