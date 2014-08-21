package controllers;

import java.net.SocketAddress;
import java.util.Map;

import play.*;
import play.mvc.*;

import views.html.*;

import net.spy.memcached.MemcachedClient;

public class Application extends Controller {
    public static MemcachedClient mc;

    public static Result command(String a) {
        try {
            if (mc != null) {
                Object res = null;

                switch (a) {
                    case "set":
                        res = mc.set("welcome_msg", 0, "Hello from Redis!").get();
                        break;
                    case "get":
                        res = mc.get("welcome_msg");
                        break;
                    case "stats":
                        String stats = "";
                        Map<SocketAddress, Map<String, String>> map = mc.getStats();
                        for (Map.Entry<SocketAddress, Map<String, String>>  entry : map.entrySet()) {
                            stats += entry.getKey().toString() + "\r\n";
                            for (Map.Entry<String, String> statsEntry :  entry.getValue().entrySet())
                                stats += statsEntry.getKey() + ": " + statsEntry.getValue() + "\r\n";
                        }
                        res = stats;
                        break;
                    case "delete":
            	        res = mc.delete("welcome_msg").get();
                        break;
                }

                return ok(res != null ? res.toString() : "N/A");
            } else {
                return ok("Error");
            }
        } catch (Exception e) {
            Logger.error("An error ocurred", e); 
            return ok("Error");   
        }
    }
}
