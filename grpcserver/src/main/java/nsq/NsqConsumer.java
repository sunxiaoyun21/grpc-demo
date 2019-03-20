package nsq;

import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQMessage;
import com.github.brainlag.nsq.callbacks.NSQMessageCallback;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

//消费者
public class NsqConsumer {


    public  void nsqConsumer(){
        NSQLookup lookup = new DefaultNSQLookup();
        //外网ip地址。lockup端口号
        lookup.addLookupAddress("localhost",4161);
        // lookup ,topic名称 ，订阅的消息
        NSQConsumer consumer=new NSQConsumer(lookup, "test", "nsq_to_file", new NSQMessageCallback() {
            @Override
            public void message(NSQMessage nsqMessage) {
                //获取订阅消息的内容
                byte b[] = nsqMessage.getMessage();
                String s= new String(b);
                System.out.println(s);
                nsqMessage.finished();
            }
        });
        consumer.start();
        //线程睡眠，让程序执行完
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





    public static void main(String[] args) {
        NsqConsumer nsqConsumer=new NsqConsumer();

        nsqConsumer.nsqConsumer();
    }


}
