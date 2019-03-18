package grpc;

import com.google.inject.Inject;
import entity.User;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import service.AccountService;

public class AccountGrpcImpl extends GreeterGrpc.GreeterImplBase {

    @Inject
    private AccountService accountService ;

    public  void  sayHello(HelloRequest req , StreamObserver<HelloReply> responseObserver){
        User user=accountService.getUserByName(req.getName());
        HelloReply reply=(user==null)? HelloReply.newBuilder().setMessage("hello Null").build():
                HelloReply.newBuilder().setMessage("hello" +user.getAccount()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }



}
