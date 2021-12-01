package com.z9devs.bitcoinexplorerjee.utils.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpCalls 
{
	// Metodo per fare chiamata a Bitcoin Core
	public static String callBtc(String method, ArrayList<String> params) {
		
		// Leggo il file delle props per recuperare user e pw 
		final Properties prop = new Properties();
		try {
		    prop.load(new FileInputStream("C:\\Users\\tomas\\Documents\\apache-tomee-plus-8.0.8\\webapps\\BitcoinExplorerJEE\\WEB-INF\\classes\\application.properties"));  
		} catch (IOException e) {
			e.printStackTrace();
			return "Errore di lettura props";
		}
		
		// Dichiaro e inizializzo il client
		ExecutorService executor = Executors.newSingleThreadExecutor();
		HttpClient cli = HttpClient.newHttpClient();
		
		String reqBody = ReqBodyFormatter.formatReqBody(method, params);
		System.out.println(reqBody);
        
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(prop.getProperty("btc.host")))
					.POST(HttpRequest.BodyPublishers.ofString(reqBody))
					.header("Content-Type", "application/json")
					.build();
			HttpResponse<String> res = cli.newBuilder()
					.authenticator(new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									prop.getProperty("btc.user"),
									prop.getProperty("btc.password").toCharArray());
						}
					})
					.connectTimeout(Duration.ofSeconds(10))
					.executor(executor)
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());
			
			// Stampo in console i risultati della richiesta
			System.out.println("Res: " + res.statusCode());
			System.out.println("Res: " + res.toString());
			return res.body();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "Error sending the request.";
		} finally {
			// Chiudo la connessione
			executor.shutdownNow();
			cli = null;
			System.gc();
		}
	}

}