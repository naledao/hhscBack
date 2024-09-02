package com.example.hhcs.dao;

import com.example.hhcs.domain.Admin;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminDao {
//    获取用户总数
    @Select("select count(*) from user")
    int getalluser();

//    查看管理员的信息
    @Select("select * from admin_users where name=#{name}")
    Admin selectAdmin(@Param("name") String name);

//    获取用户信息
    @Select("select * from user")
    List<User> getusers();


//    获取c商品数量
    @Select("select count(*) from cstore")
    int getcountcstore();

//    获取c商品信息
    @Select("select * from cstore")
    List<Cstore> getcstore();
}
