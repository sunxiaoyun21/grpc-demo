package service;

import entity.User;

public interface AccountService {
    User getUserByName(String name);
}
