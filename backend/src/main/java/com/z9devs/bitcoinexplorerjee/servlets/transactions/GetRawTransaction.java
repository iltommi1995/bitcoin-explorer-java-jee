package com.z9devs.bitcoinexplorerjee.servlets.transactions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.z9devs.bitcoinexplorerjee.utils.http.HttpCalls;

@WebServlet("/getrawtransaction")
public class GetRawTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Metodo per Bitcoin core
	private static String METHOD = "getrawtransaction";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Setto il content type
		response.setContentType("applitaction/json");
		// Dichiaro e inizializzo il writer
		PrintWriter out = response.getWriter();
					
		// Stampo in console chi effettua la richiesta
		System.out.println(String.format("%s requested the method: %s.", request.getRemoteAddr(), METHOD));
				
		// Preparo risposta
		String res = "";
		String blockhash = request.getParameter("transactionid");
		// Controllo se ci sono i parametri necessari
		if(blockhash != null && !blockhash.isEmpty()) {
						
			ArrayList<String> params = new ArrayList<String>();
			params.add(request.getParameter("transactionid"));
					
			res = HttpCalls.callBtc(METHOD, params);
		} else {
			res = "\"Error\":\"You need to insert the 'blockhash' param.\"";
		}
					
		out.print(res);
		out.flush();
	}
}
