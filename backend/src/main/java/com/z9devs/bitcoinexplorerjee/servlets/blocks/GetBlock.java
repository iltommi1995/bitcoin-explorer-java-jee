package com.z9devs.bitcoinexplorerjee.servlets.blocks;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.search.SearchHit;

import com.z9devs.bitcoinexplorerjee.elasticsearch.ElasticDocuments;
import com.z9devs.bitcoinexplorerjee.elasticsearch.ElasticService;
import com.z9devs.bitcoinexplorerjee.utils.http.HttpCalls;


@WebServlet("/getblock")
public class GetBlock extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// Metodo per Bitcoin core
	private static String METHOD = "getblock";
	
	@Inject private ElasticService elastic;
	@Inject private ElasticDocuments doc;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Setto il content type
		response.setContentType("applitaction/json");
		// Dichiaro e inizializzo il writer
		PrintWriter out = response.getWriter();
			
		// Stampo in console chi effettua la richiesta
		System.out.println(String.format("%s requested the method: %s.", request.getRemoteAddr(), METHOD));

		// Preparo risposta
		String res = "";
		String blockhash = request.getParameter("blockhash");
		
		// Controllo se ci sono i parametri necessari
		if(blockhash != null && !blockhash.isEmpty()) {
			System.out.println("Checking if index 'blocks' exists...");
			if(!elastic.checkIndex()) {
				System.out.println("Creating index 'blocks'...");
				elastic.createIndex();
			}
			
			System.out.println("Searching requested block on index 'blocks'...");
			
			SearchHit[] sh = elastic.searchBlock(blockhash).getHits().getHits();
			
			if(sh.length > 0) {
				System.out.println("The block exists on elasticsearch index.");
				res = sh[0].getSourceAsString();
			}
			else {
				System.out.println("The block doasn't exist on elasticsearch index.");
				System.out.println("Retrieving the block from the full node...");
				
				ArrayList<String> params = new ArrayList<String>();
				params.add(request.getParameter("blockhash"));
				res = HttpCalls.callBtc(METHOD, params);
				
				// Aggiungo il nuovo blocco ad elasticsearch
				System.out.println("Adding the new block to index 'blocks'...");
				System.out.println(elastic.addBlock(doc.toMap(res)).getResult().toString());
			}
		} else {
			res = "\"Error\":\"You need to insert the 'blockhash' param.\"";
		}
		
		out.print(res);
		out.flush();
	}
}
