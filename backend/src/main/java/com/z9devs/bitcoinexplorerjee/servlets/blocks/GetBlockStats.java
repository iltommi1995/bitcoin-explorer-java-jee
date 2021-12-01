package com.z9devs.bitcoinexplorerjee.servlets.blocks;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.z9devs.bitcoinexplorerjee.utils.http.HttpCalls;

@WebServlet("/getblockstats")
public class GetBlockStats extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Metodo per Bitcoin core
	private static String METHOD = "getblockstats";

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
		String stats = request.getParameter("stats");
		System.out.println(stats);
		// Controllo se ci sono i parametri necessari
		if(stats != null && !stats.isEmpty() && blockhash != null && !blockhash.isEmpty()) {
				
			ArrayList<String> params = new ArrayList<>();
			params.add(blockhash);
			
			for(String stat : stats.split("\\|"))
				params.add(stat);
			
			
			res = HttpCalls.callBtc(METHOD, params);
		} else {
			res = "\"Error\":\"You need to insert the 'blockhash' param and the 'stats' param with all the stats required, separated by '|'.\"";
		}
			
		out.print(res);
		out.flush();
	}
}
