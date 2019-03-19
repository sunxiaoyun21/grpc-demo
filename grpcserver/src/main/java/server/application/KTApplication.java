package server.application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import server.NettyServer;

public class KTApplication {


    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(
                new ApplicationModule()
        );
        final NettyServer server = injector.getInstance(NettyServer.class);
        server.run();
    }
}
