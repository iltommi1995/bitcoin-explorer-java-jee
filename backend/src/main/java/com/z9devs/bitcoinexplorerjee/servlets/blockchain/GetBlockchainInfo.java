package com.z9devs.bitcoinexplorerjee.servlets.blockchain;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.z9devs.bitcoinexplorerjee.utils.http.HttpCalls;

@WebServlet("/getblockchaininfo")
public class GetBlockchainInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Metodo per Bitcoin core
	private static String METHOD = "getblockchaininfo";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Setto il content type
		response.setContentType("applitaction/json");
		// Dichiaro e inizializzo il writer
		PrintWriter out = response.getWriter();
			
		// Stampo in console chi effettua la richiesta
		System.out.println(String.format("%s requested the method: %s.", request.getRemoteAddr(), METHOD));
		
		out.print(HttpCalls.callBtc(METHOD, null));
		out.flush();
	}
}
