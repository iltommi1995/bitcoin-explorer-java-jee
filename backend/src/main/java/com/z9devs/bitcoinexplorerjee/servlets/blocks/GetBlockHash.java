package com.z9devs.bitcoinexplorerjee.servlets.blocks;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import com.z9devs.bitcoinexplorerjee.elasticsearch.ElasticClient;

/**
 * Servlet implementation class GetBlockHash
 */
@WebServlet("/getblockhash")
public class GetBlockHash extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    @Inject ElasticClient elasticClient;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Dichiaro e inizializzo il writer
		PrintWriter out = response.getWriter();
		
		System.out.println("1");
				
		Request elasticRequest = new Request(
			    "GET",  
			    "/prova-indeice/_search");   
		System.out.println("2");
		elasticRequest.setJsonEntity("{\r\n"
				+ "  \"query\": {\r\n"
				+ "    \"match_all\": {}\r\n"
				+ "  }\r\n"
				+ "}");
		System.out.println("3 - Prima di entrare");
		Response elasticResponse = elasticClient.lowLevelElastiClient().performRequest(elasticRequest);
		System.out.println("4");
		
		String responseBody = EntityUtils.toString(elasticResponse.getEntity()); 
		System.out.println("5");
		out.print(responseBody);
	}

}
