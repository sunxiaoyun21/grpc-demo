package service.impl;

import com.google.inject.Inject;
import dao.UserDao;
import entity.User;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import service.AccountService;

public class AccountServiceImpl implements AccountService {

    @Inject
    private UserDao userDao;

    public User getUserByName(String name) {
        System.out.println(userDao);
        return userDao.fetchByAccount(name);
    }
}
