package service;

import entity.User;
import org.apache.ibatis.annotations.Select;

public interface  AccountDao {

    @Select("SELECT * FROM `user` WHERE account=#{account} LIMIT 1")
    User fetchByAccount(String account);

}
