package nsq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.brainlag.nsq.NSQProducer;
import com.github.brainlag.nsq.exceptions.NSQException;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeoutException;

//生产者发布方法
public class NsqProducer {


    public  void  nsqProducer(Map<String,Object> message){

        NSQProducer producer=new NSQProducer();
        //ip地址和端口号
        producer.addAddress("localhost",4150).start();

        try {
            Iterator<Map.Entry<String,Object>> iter = message.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String,Object> entry = iter.next();
                String topic = entry.getKey();
                String mess = entry.getValue().toString();
                System.out.println(topic+" "+mess);
                //名称，发布的消息
                producer.produce(topic,mess.getBytes());
            }



        } catch (NSQException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) {
       NsqProducer nsqProducer=new NsqProducer();
       Map<String,Object> map=new HashMap<String, Object>();
       Map<String,Object> score=new HashMap<String, Object>();
       score.put("account","grpc");
       score.put("score",25);
       JSONObject paramsObj = new JSONObject(score);
       map.put("grpc-test",paramsObj);
       nsqProducer.nsqProducer(map);

    }



}
