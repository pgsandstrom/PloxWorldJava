package se.persandstrom.ploxworld.main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;

import se.persandstrom.ploxworld.common.Rand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

//TODO: This is truly crappy code and very insecure. Plox change it someday :-)
public class Main {

	static World world;

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
			String path = uri.getPath();

			if ("/".equals(path)) {
				path = "/index.html";
			}

			File file = new File("." + path);

//			System.out.println(uri.getPath());

			Headers headers = httpExchange.getResponseHeaders();
			if (path.endsWith(".css")) {
				headers.set("Content-Type", "text/css");
			}

			httpExchange.sendResponseHeaders(200, file.length());
			try (OutputStream out = httpExchange.getResponseBody()) {
				Files.copy(file.toPath(), out);
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

				URI uri = httpExchange.getRequestURI();
				String path = uri.getPath();

//				System.out.println(path);

				//TODO: Make some mechanism so the server can hold many worlds for different games :-)
				String response;
				if ("/backend".equals(path)) {
					Rand.reset();
					world = new World();
					world.progressTurn();
				} else if ("/backend/progressTurn".equals(path)) {
					world.progressTurn();
				}

				Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				response = gson.toJson(world);


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
