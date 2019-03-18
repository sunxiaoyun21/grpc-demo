package dao;

import entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {

    @Select("SELECT * FROM `user` WHERE account=#{account,jdbcType=VARCHAR} LIMIT 1")
    User fetchByAccount(String account);

}
