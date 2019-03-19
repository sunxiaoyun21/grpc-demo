package server;

import com.google.inject.Inject;
import com.google.inject.Injector;
import grpc.AccountGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import server.application.ApplicationProperties;

import java.io.IOException;
import java.util.logging.Logger;

public class NettyServer {
    private final static Logger logger  =Logger.getLogger(NettyServer.class.getName());

    private ApplicationProperties applicationProperties = new ApplicationProperties();

    @Inject
    private Injector injector;

    private Server server;

    public NettyServer() throws IOException {
    }

    private void start() throws IOException {

        int port=applicationProperties.i("server.port");
        server= ServerBuilder.forPort(port).addService(new  AccountGrpcImpl())
                .build().start();

        logger.info("Server started, listening on " + port);

       Runtime.getRuntime().addShutdownHook(new Thread(){
           @Override
           public void run() {
               logger.warning("*** shutting down gRPC server since JVM is shutting down");
               NettyServer.this.stop();
               logger.warning("*** server shut down");
           }
       });

    }

    private void stop() {
        if (server!=null){
            server.shutdown();
        }
    }

    public void run() throws IOException, InterruptedException {
        this.start();
        this.blockUntilShutdown();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        NettyServer nettyServer;
        nettyServer=new NettyServer();
        nettyServer.start();
       nettyServer.blockUntilShutdown();
    }
}
