package com.z9devs.bitcoinexplorerjee.elasticsearch;

import javax.enterprise.context.ApplicationScoped;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

@ApplicationScoped
public class ElasticClient {
	
	final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	

	public RestClient lowLevelElastiClient() {
		
		
		
		RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

		builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
			public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
				return requestConfigBuilder.setSocketTimeout(10000);
			}
		});
		return builder.build();
	}

	public RestHighLevelClient highLevelElasticClient() {
		credentialsProvider.setCredentials(AuthScope.ANY,
		        new UsernamePasswordCredentials("elastic", "elastic"));

		RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200))
		        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
		            @Override
		            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
		                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
		            }
		        });


		RestHighLevelClient client = new RestHighLevelClient(builder);

		return client;
	}
}
