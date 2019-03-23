import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQMessage;
import com.github.brainlag.nsq.callbacks.NSQMessageCallback;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;
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

    public void greet(String name,int score){
        logger.info("Will try to greet " + name + " ...");
        HelloRequest request=HelloRequest.newBuilder().setName(name).setScore(score).build();
        HelloReply reply = null;
        try {
            reply=blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
             e.printStackTrace();
        }
        logger.info("Greeting: " + reply.getMessage());
    }

    //从nsq中取数据
    public  void nsqConsumer(String topic) {
        NSQLookup lookup = new DefaultNSQLookup();
        //外网ip地址。lockup端口号
        lookup.addLookupAddress("localhost", 4161);


        // lookup ,topic名称 ，订阅的消息
        NSQConsumer nsqConsumer=new NSQConsumer(lookup,topic,"nsq_to_file",new NSQMessageCallback(){

            public void message(NSQMessage nsqMessage) {
                //获取订阅消息的内容
                byte[] b = nsqMessage.getMessage();
                String s= new String(b);
                JSON jsonpObject=JSON.parseObject(s);
                String account=((JSONObject) jsonpObject).getString("account");
                Integer score=((JSONObject) jsonpObject).getInteger("score");
                greet(account,score);
                System.out.println(s);

                nsqMessage.finished();

            }
        });

        nsqConsumer.start();
        //线程睡眠，让程序执行完
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient("localhost",8081);




        try {

            client.nsqConsumer("grpc-test");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }

    }
}
