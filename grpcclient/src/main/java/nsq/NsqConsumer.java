package nsq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.github.brainlag.nsq.NSQConsumer;
import com.github.brainlag.nsq.NSQMessage;
import com.github.brainlag.nsq.callbacks.NSQMessageCallback;
import com.github.brainlag.nsq.lookup.DefaultNSQLookup;
import com.github.brainlag.nsq.lookup.NSQLookup;

public class NsqConsumer {




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
                System.out.println(jsonpObject);
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


    public static void main(String[] args) {
        NsqConsumer nsqConsumer=new NsqConsumer();
        nsqConsumer.nsqConsumer("grpc-test");
//        String s="{grpc=25}";
//        JSONObject jsonpObject=JSON.parseObject(s);
//        System.out.println(jsonpObject);



    }
}
