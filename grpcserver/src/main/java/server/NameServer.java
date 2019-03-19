package server;

import grpc.NameServiceImplBaseImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class NameServer {

    private Logger logger=Logger.getLogger(NameServer.class.getName());
    private  static  final  int DEFAULT_PORT=8088;
    private int port;//服务端口号
    private Server server;

    public  NameServer(int port){
        this(port,ServerBuilder.forPort(port));
    }
    public NameServer(int port, ServerBuilder<?> serverBuilder){
       this.port=port;
       server=serverBuilder.addService(new NameServiceImplBaseImpl()).build();
    }

    private void start() throws IOException {
        server.start();
        logger.info("server has started ,listening on" +port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                logger.warning("*** server shut down");
               NameServer.this.stop();
            }
        });

    }

    private void stop() {
        if(server!=null){
            server.shutdown();
        }
    }

    //让server阻塞到程序退出为止
    private void  blockUntilShutdown() throws InterruptedException {
        if (server!=null){
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        NameServer nameServer;
        if(args.length>0){
            nameServer=new NameServer(Integer.parseInt(args[0]));
        }else {
            nameServer=new NameServer(DEFAULT_PORT);
        }
        nameServer.start();
        nameServer.blockUntilShutdown();
    }
}
