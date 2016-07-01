package se.persandstrom.ploxworld.main;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.Executors;

import se.persandstrom.ploxworld.action.Action;
import se.persandstrom.ploxworld.ai.Ai;
import se.persandstrom.ploxworld.ai.MinerAi;
import se.persandstrom.ploxworld.ai.PirateAi;
import se.persandstrom.ploxworld.ai.TraderAi;
import se.persandstrom.ploxworld.common.Rand;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

//TODO: This is truly crappy code and very insecure. Plox change it someday :-)
public class Main implements PlayerInterface {

	static World world;

	public static void main(String[] args) throws Exception {
		new Main().main2();
	}

	public void main2() throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//		HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 8000),0);	//For security lol
		server.createContext("/", new FrontendHandler());
		server.createContext("/backend", new BackendHandler(this));
//		server.setExecutor(null); // creates a default executor
		server.setExecutor(Executors.newCachedThreadPool());    //Allow multithread
		server.start();
	}

	private class FrontendHandler implements HttpHandler {

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

	private HttpExchange currentHttpExchange;
	private Action currentAction;

	private class BackendHandler implements HttpHandler {

		private final PlayerInterface playerInterface;

		public BackendHandler(PlayerInterface playerInterface) {
			this.playerInterface = playerInterface;
		}

		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			currentHttpExchange = httpExchange;

			try {

				URI uri = httpExchange.getRequestURI();
				String path = uri.getPath();

//				System.out.println(path);

				//TODO: Make some mechanism so the server can hold many worlds for different games :-)
				String response = null;
				if ("/backend".equals(path)) {
					Rand.reset();
					world = new World(playerInterface);
					world.progressTurn();

					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Ai.class, new AiSerializer()).create();
					response = gson.toJson(world);
				} else if ("/backend/progressTurn".equals(path)) {
					int turnToProgress = 1;
					String query = uri.getQuery();
					String[] arguments = query.split("&");
					for (String argument : arguments) {
						String[] split = argument.split("=");
						if (split.length != 2) {
							throw new IllegalArgumentException();
						}
						if ("turns".equals(split[0])) {
							turnToProgress = Integer.valueOf(split[1]);
						}
					}
					world.progressTurn(turnToProgress);

					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Ai.class, new AiSerializer()).create();
					response = gson.toJson(world);
				} else if ("/backend/log".equals(path)) {
					String personName = null;
					String query = uri.getQuery();
					String[] arguments = query.split("&");
					for (String argument : arguments) {
						String[] split = argument.split("=");
						if (split.length != 2) {
							throw new IllegalArgumentException();
						}
						if ("name".equals(split[0])) {
							personName = split[1];
						}
					}
					personName = personName.replace('+', ' ');    //fulfix på jquery skit
					List<String> personLogs = world.getPersonLogs(personName);
					Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Ai.class, new AiSerializer()).create();
					response = gson.toJson(personLogs);
				} else if ("/backend/action".equals(path)) {
					String query = uri.getQuery();
					String[] arguments = query.split("&");
					for (String argument : arguments) {
						String[] split = argument.split("=");
						if (split.length != 2) {
							throw new IllegalArgumentException();
						}
						if ("decision".equals(split[0])) {
							String decision = split[1];
//							System.out.println("action received: " + decision + ", " + currentAction);
							currentAction.setDecision(decision);
						}
					}

					// amazing hacks! :)
					currentHttpExchange = httpExchange;
					return;
				} else {
					System.out.println("errro!!!");
				}

				// switch back to the current http before responding:
				httpExchange = currentHttpExchange;

				Headers headers = httpExchange.getResponseHeaders();
				headers.set("Content-Type", "application/json");
				headers.set("Access-Control-Allow-Origin", "http://localhost:8080");
				httpExchange.sendResponseHeaders(200, response.length());

				OutputStream os = httpExchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void completeAction(Action action) {
		try {
			System.out.println("starting to wait for decision");
			currentAction = action;

			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Ai.class, new AiSerializer()).create();
			String response = "{\"action\":\"" + action.getClass().getSimpleName() + "\", \"info\":"+gson.toJson(action)+"}";
//			String response = "{\"action\":\"" + action.getClass().getSimpleName() + "\"}";

			HttpExchange httpExchange = currentHttpExchange;
			Headers headers = httpExchange.getResponseHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Access-Control-Allow-Origin", "http://localhost:8080");
			httpExchange.sendResponseHeaders(200, response.length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(response.getBytes());
			os.close();

			while (action.isDecided() == false) {
//				System.out.println("waiting for decision...");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Received decision! Continuing!!!");

			currentAction = null;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class AiSerializer implements JsonSerializer<Ai> {
		@Override
		public JsonElement serialize(Ai ai, Type type, JsonSerializationContext jsonSerializationContext) {
			if (ai instanceof TraderAi) {
				return new JsonPrimitive("Trader");
			} else if (ai instanceof MinerAi) {
				return new JsonPrimitive("Miner");
			} else if (ai instanceof PirateAi) {
				return new JsonPrimitive("Pirate");
			} else {
				throw new IllegalStateException();
			}
		}
	}
}