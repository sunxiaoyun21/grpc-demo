import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import nsq.NsqConsumer;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GrpcClient {

    private static final Logger logger = Logger.getLogger(GrpcClient.class.getName());
    private final ManagedChannel managedChannel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public GrpcClient(String host,int port){
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build());
    }

    public GrpcClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        this.blockingStub = GreeterGrpc.newBlockingStub(managedChannel);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name){
        logger.info("Will try to greet " + name + " ...");
        HelloRequest request=HelloRequest.newBuilder().setName(name).build();
        HelloReply reply = null;
        try {
            reply=blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
             e.printStackTrace();
        }
        logger.info("Greeting: " + reply.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient("localhost",8081);
        NsqConsumer nsqConsumer=new NsqConsumer();

        String user = null;
        try {
            user = "grpc";
            if (args.length > 0) {
                user = args[0];
            }
            client.greet(user);
            nsqConsumer.nsqConsumer();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }

    }
}
