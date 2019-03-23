import nsq.NsqProducer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CostScore {
    //消费积分放入nsq中
    public static void main(String[] args) {
       Map<String,Object> map=new HashMap<String, Object>();
       Map<String,Integer> userAndScore= new HashMap<String,Integer>();
       userAndScore.put("grcc",25);
       map.put("score",userAndScore);
       CostScore costScore=new CostScore();
       costScore.costScore(map);

    }

    public  void  costScore(Map<String,Object> map){
        NsqProducer nsqProducer=new NsqProducer();

        nsqProducer.nsqProducer(map);
    }
}
