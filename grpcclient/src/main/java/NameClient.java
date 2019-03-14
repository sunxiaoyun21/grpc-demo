import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.nameserver.Ip;
import io.grpc.examples.nameserver.Name;
import io.grpc.examples.nameserver.NameServiceGrpc;

import java.util.concurrent.TimeUnit;

public class NameClient {

    private static final String DEFAULT_HOST="localhost";
    private static final int DEFAULT_PORT=8088;
    private ManagedChannel managedChannel;
    private NameServiceGrpc.NameServiceBlockingStub nameServiceBlockingStub;

    public NameClient(String host,int port){
        this(ManagedChannelBuilder.forAddress(host,port).usePlaintext(true).build());
    }

    public NameClient(ManagedChannel managedChannel) {
        this.managedChannel=managedChannel;
        this.nameServiceBlockingStub=NameServiceGrpc.newBlockingStub(managedChannel);
    }

    public void shutdown() throws InterruptedException {
        managedChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public String getIpByName(String n){
        Name name=Name.newBuilder().setName(n).build();
        Ip ip=nameServiceBlockingStub.getIpByName(name);
        return ip.getIp();
    }

    public static void main(String[] args) throws InterruptedException {
        NameClient nameClient=new NameClient(DEFAULT_HOST,DEFAULT_PORT);

            String res=nameClient.getIpByName("allison");
            System.out.println("get result from server :" +res );

            nameClient.shutdown();
    }
}
