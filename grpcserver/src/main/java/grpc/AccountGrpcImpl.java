package grpc;

import nsq.NsqProducer;
import util.MybatisUtil;
import entity.User;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import org.apache.ibatis.session.SqlSession;
import service.AccountDao;

import java.util.HashMap;
import java.util.Map;

public class AccountGrpcImpl extends GreeterGrpc.GreeterImplBase {
      SqlSession session= MybatisUtil.getSqlSession(true);
       NsqProducer nsqProducer=new NsqProducer();


    public  void  sayHello(HelloRequest req , StreamObserver<HelloReply> responseObserver){
        AccountDao accountDao=session.getMapper(AccountDao.class);
        User user=accountDao.fetchByAccount(req.getName());
        HelloReply reply=(user==null)? HelloReply.newBuilder().setMessage("hello Null").build():
                HelloReply.newBuilder().setMessage("hello-------->" +user.getAccount()).build();
        Map<String,Object> map=new HashMap<>();

        map.put("topic-grpc","今天开始学习grpc了，好不错啊");

        nsqProducer.nsqProducer(map);
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }



}
