package com.example.hhcs.dao;

import com.example.hhcs.domain.PurchaseHistory;
import com.example.hhcs.domain.Test1;
import com.example.hhcs.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {




    @Select("select warehouse from user")
    List<Integer> getss();
    @Select("select * from user where openid=#{id}")
    User get(String id);

    @Insert("insert into user values(#{warehouse},#{openid},#{head},#{name},#{email})")
    int adduser(User user);

//    获取求购历史表
    @Select("select * from purchasehistory where openid=#{openid}")
    PurchaseHistory gethis(@Param("openid") String openid);

//    更新求购历史表a
    @Update("update purchasehistory set a=#{a} where openid=#{openid}")
    int upAhis(@Param("a") int a,@Param("openid") String openid);

//    新建求购历史表
    @Insert("insert into purchasehistory values(#{openid},#{s},#{a},#{b},#{c})")
    int xinhis(PurchaseHistory purchaseHistory);

    @Update("update user set name=#{name} where openid=#{openid}")
    int changename(@Param("name") String name,@Param("openid") String openid);

    @Update("update user set head=#{head} where openid=#{openid}")
    int changehead(@Param("head") String head,@Param("openid") String openid);

}
