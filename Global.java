import play.*;
import controllers.Application;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

public class Global  extends GlobalSettings {
    
    @Override
    public void onStart(play.Application app) {
        try {
            AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
                new PlainCallbackHandler(System.getenv("MEMCACHEDCLOUD_USERNAME"), System.getenv("MEMCACHEDCLOUD_PASSWORD")));
                
            controllers.Application.mc = new MemcachedClient(
                new ConnectionFactoryBuilder()
                    .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                    .setAuthDescriptor(ad).build(),
                AddrUtil.getAddresses(System.getenv("MEMCACHEDCLOUD_SERVERS"))); 
           
            Logger.info("Memcached Client initialized sucessfully.");				
	} catch (Exception e) {
	    Logger.error("Memcached Client could not be initialized.", e);
	    controllers.Application.mc = null;
	}
    }
}

