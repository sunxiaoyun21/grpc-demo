package grpc;

import io.grpc.examples.nameserver.Ip;
import io.grpc.examples.nameserver.Name;
import io.grpc.examples.nameserver.NameServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NameServiceImplBaseImpl extends NameServiceGrpc.NameServiceImplBase {

    private Map<String ,String> map=new HashMap<String, String>();
    private Logger logger=Logger.getLogger(NameServiceImplBaseImpl.class.getName());
    public  NameServiceImplBaseImpl(){
        map.put("sunny","123.456.789");
        map.put("allison","456.189.7853");
    }



    @Override
    public void getIpByName(Name request, StreamObserver<Ip> responseObserver) {
        logger.log(Level.INFO,"request is coming.args=" +request.getName());
        Ip ip=Ip.newBuilder().setIp(getName(request.getName())).build();
        responseObserver.onNext(ip);
        responseObserver.onCompleted();

    }

    private String getName(String name) {
        String ip=map.get(name);
        if(ip==null){
            return "0.0.0.0";
        }
        return ip;
    }
}
