package se.kth.guancio.SampleService;

import static spark.Spark.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	staticFiles.location("/static"); // Static files

    	get("/login/:login_name", "application/json", new Route(){
			public Object handle(Request req, Response res) throws Exception {
    			req.session(true);                      // create and return session
    			
    			if (!req.headers("pwd").equals("123456"))
    				halt(401, "Not authorized");
    			
    			User roberto = new User("rob", "Roberto", "Guanciale", 42, 10);
    			
				return roberto;
			}
    	}, new JsonTransformer());

    	post("/report/:login_name", new Route() {
    		public Object handle(Request req, Response res) throws Exception {
    			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

    			if (!req.headers("pwd").equals("123456"))
    				halt(401, "Not authorized");

    			HttpServletRequest raw = req.raw();
    			//InputStream is = raw.getPart("uploaded_file").getInputStream();
    			InputStream is = raw.getInputStream();
    			
    			Gson gb = new GsonBuilder().create();
    			MatchResult input = gb.fromJson(new InputStreamReader(is), MatchResult.class);
    			System.out.println(input.duration);
    			User roberto = new User("rob", "Roberto", "Guanciale", 55, 10);
    			return roberto; 
    		}
    	}, new JsonTransformer());
 	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	get("/hello", new Route(){
			public Object handle(Request req, Response res) throws Exception {
    			req.session(true);                      // create and return session
				return "Hello world your name is " + req.session().attribute("user");
			}
    	});
    	
    	// matches "GET /hello/foo" and "GET /hello/bar"
    	// request.params(":name") is 'foo' or 'bar'
    	get("/test/:name", new Route() {
    		public Object handle(Request req, Response res) throws Exception {
    			req.session(true);                      // create and return session
    			req.session().attribute("user", req.params(":name"));
    			return "Set name to " + req.params(":name");
    		}
		});
    	
    	before("/admin/*", new Filter() {
			public void handle(Request req, Response res) throws Exception {
	    	    boolean authenticated;
	    	    authenticated = (req.session(true).attribute("user") != null); 
	    	    if (!authenticated) {
	    	        halt(401, "You are not welcome here");
	    	    }
			}
		});

    	get("/admin/:name", new Route() {
    		public Object handle(Request req, Response res) throws Exception {
    			return "Admin page";
    		}
		});
    	
    	get("/hello-json", "application/json", new Route() {
    		public Object handle(Request req, Response res) throws Exception {
    			return new MyMessage("Hello World");
    		}
    	}, new JsonTransformer());

    	get("/halt", new Route() {
    		public Object handle(Request req, Response res) throws Exception {
    			stop();
    			halt();
    			return null;
    		}
    	});
    	
    	post("/yourUploadPath", new Route() {
    		public Object handle(Request req, Response res) throws Exception {
    			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
    			InputStream is = req.raw().getPart("uploaded_file").getInputStream();
    			
    			/*
    			StringWriter writer = new StringWriter();
    			IOUtils.copy(is, writer);
    			String theString = writer.toString();
    			
    			System.out.println(theString);
    			*/
    			/*
    			JsonStreamParser ps = new JsonStreamParser(new InputStreamReader(is));
    			JsonElement el = ps.next();
    			JsonObject jo = el.getAsJsonObject();
    			System.out.println(jo.get("message").getAsString());
    			System.out.println(jo.get("name").getAsString());
    			*/
    			Gson gb = new GsonBuilder().create();
    			MyMessage m = gb.fromJson(new InputStreamReader(is), MyMessage.class);
    			System.out.println(m.message);
    			System.out.println(m.name);
    			return "File uploaded";
    			//http://www.nilzorblog.com/2014/06/building-solid-json-rest-client-in.html
    			//http://square.github.io/retrofit/
    		}
    	});
    }
}
