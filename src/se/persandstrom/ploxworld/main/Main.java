package se.persandstrom.ploxworld.main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {

	//TODO: This is truly crappy code and very insecure. Plox change it someday :-)

	static World world = new World();

	public static void main(String[] args) throws Exception {

		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//		HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8000),0);	//For security lol
		server.createContext("/", new FrontendHandler());
		server.createContext("/backend", new BackendHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}
	private static class FrontendHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange httpExchange) throws IOException {

			URI uri = httpExchange.getRequestURI();

			File file = new File("." + uri.getPath());

//			System.out.println(file.getAbsoluteFile());

			httpExchange.sendResponseHeaders(200, file.length());
			try (OutputStream out = httpExchange.getResponseBody()) {
				Path path = file.toPath();
				Files.copy(path, out);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class BackendHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange httpExchange) throws IOException {

			try {

				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				String response = gson.toJson(world);


				Headers headers = httpExchange.getResponseHeaders();
				headers.set("Content-Type", "application/json");
				httpExchange.sendResponseHeaders(200, response.length());

				OutputStream os = httpExchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
