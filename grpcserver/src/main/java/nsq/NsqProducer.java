package nsq;


import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;

import java.util.concurrent.TimeoutException;

//生产者发布方法
public class NsqProducer {


    public  void  nsqProducer(){
        NSQProducer producer=new NSQProducer();
        //ip地址和端口号
        producer.addAddress("localhost",4150).start();

        try {
            //名称，发布的消息
            producer.produce("test","message".getBytes());
        } catch (NSQException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       NsqProducer nsqProducer=new NsqProducer();
       nsqProducer.nsqProducer();
    }



}
