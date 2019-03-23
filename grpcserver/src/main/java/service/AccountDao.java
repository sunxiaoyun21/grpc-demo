package service;

import entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface  AccountDao {

    @Select("SELECT * FROM `user` WHERE account=#{account} LIMIT 1")
    User fetchByAccount(String account);

    @Update("update user set score=#{score} where account=#{account}")
    void updateAccount(@Param("account") String account,@Param("score") int score);

}
