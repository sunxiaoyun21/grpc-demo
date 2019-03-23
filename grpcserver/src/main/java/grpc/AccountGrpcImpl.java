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


    public  void  sayHello(HelloRequest req , StreamObserver<HelloReply> responseObserver){
        AccountDao accountDao=session.getMapper(AccountDao.class);
        User user=accountDao.fetchByAccount(req.getName());
        if(null!=user){
            accountDao.updateAccount(req.getName(),req.getScore());
            HelloReply reply= HelloReply.newBuilder().setMessage("正在处理中----").build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }


    }
    
}
