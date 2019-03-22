package nsq;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQMessage;
import com.github.brainlag.nsq.callbacks.NSQMessageCallback;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

public class NsqConsumer {

    public  void nsqConsumer() {
        NSQLookup lookup = new DefaultNSQLookup();
        //外网ip地址。lockup端口号
        lookup.addLookupAddress("localhost", 4161);
        // lookup ,topic名称 ，订阅的消息

        NSQConsumer nsqConsumer=new NSQConsumer(lookup,"topic-grpc","nsq_to_file",new NSQMessageCallback(){

            public void message(NSQMessage nsqMessage) {
                //获取订阅消息的内容
                byte b[] = nsqMessage.getMessage();
                String s= new String(b);
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
}
