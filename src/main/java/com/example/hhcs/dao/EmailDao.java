package com.example.hhcs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmailDao {
    @Select("select email from user where openid=#{openid}")
    String getemail(@Param("openid") String openid);


    @Update("update user set email=#{email} where openid=#{openid}")
    int upemail(@Param("email") String email,@Param("openid") String openid);

    @Update("update user set email=#{email} where openid=#{openid}")
    int delemail(@Param("email") String email,@Param("openid") String openid);
}
