package grpc;

import util.MybatisUtil;
import entity.User;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

import org.apache.ibatis.session.SqlSession;
import service.AccountDao;

public class AccountGrpcImpl extends GreeterGrpc.GreeterImplBase {
      SqlSession session= MybatisUtil.getSqlSession(true);


    public  void  sayHello(HelloRequest req , StreamObserver<HelloReply> responseObserver){
        AccountDao accountDao=session.getMapper(AccountDao.class);
        User user=accountDao.fetchByAccount(req.getName());
        HelloReply reply=(user==null)? HelloReply.newBuilder().setMessage("hello Null").build():
                HelloReply.newBuilder().setMessage("hello-------->" +user.getAccount()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }



}
