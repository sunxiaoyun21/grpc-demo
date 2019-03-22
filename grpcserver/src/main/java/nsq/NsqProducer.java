package nsq;


import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

//生产者发布方法
public class NsqProducer {


    public  void  nsqProducer(Map<String, Object> map){
        NSQProducer producer=new NSQProducer();
        //ip地址和端口号
        producer.addAddress("localhost",4150).start();

        try {

            Iterator<String> iter = map.keySet().iterator();
            while(iter.hasNext()){
                String key=iter.next();
                String value = map.get(key).toString();
                System.out.println(key+" "+value);
                //名称，发布的消息
                producer.produce(key,value.getBytes());
            }
        } catch (NSQException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       NsqProducer nsqProducer=new NsqProducer();
       Map<String,Object> map=new HashMap<>();
       map.put("ceshi","就看对不对");
        map.put("grpc","就看对不对");
       nsqProducer.nsqProducer(map);
    }



}
