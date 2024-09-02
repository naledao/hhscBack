package com.example.hhcs.dao;


import com.example.hhcs.domain.ChatGptMessageYoN;
import com.example.hhcs.domain.ChatGptMessages;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatGptDao {

//    用户消息进入队列
    @Insert("insert into ${tablename} values(#{id},#{role},#{message},#{openid},#{time},#{yorn})")
    int addmessage(@Param("tablename") String tablename,@Param("id") Integer id,@Param("role") String role,@Param("message") String message,@Param("openid") String openid,@Param("time") String time,@Param("yorn") int yorn);


//    进入标记消息队列
    @Insert("insert into ChatGptQueue values(#{id},#{tablename})")
    int addmessageQueue(@Param("id") Integer id,@Param("tablename") String tablename);

    @Select("select id from ${tablename} order by id desc limit 1")
    List<Integer> getlastqueueid(@Param("tablename") String tablename);


//    删除用户消息
    @Delete("delete from ${tablename} where id=#{id}")
    void delmsg(@Param("tablename") String tablename,@Param("id") int id);


//    取标记队列中的第一个
    @Select("select tablename from ChatGptQueue order by id limit 1")
    String getfirstqueue();

    @Select("select id from ChatGptQueue order by id limit 1")
    Integer getfirstid();


    @Select("select count(*) from ${tablename}")
    int getcount(@Param("tablename") String tablename);

    @Select("select id from ${tablename}")
    List<Integer> getqueue(@Param("tablename") String tablename);


    @Select("select role,yorn,message,openid from ${tablename} where id=#{id}")
    ChatGptMessageYoN check(@Param("tablename") String tablename,@Param("id") int id);


    @Update("update ${tablename} set yorn=#{y} where id=#{id}")
    int upmessage(@Param("tablename") String tablename,@Param("y") int y,@Param("id") int id);

    @Select("select role,time,message from ${tablename}")
    List<ChatGptMessages> getallmessages(@Param("tablename") String tablename);

    @Delete("delete from ${tablename}")
    int delallmessages(@Param("tablename") String tablename);

    @Delete("delete from ${tablename} where id=#{id}")
    int delqueue(@Param("tablename") String table,@Param("id") int id);

    @Select("select count(*) from ${tablename}")
    int all(@Param("tablename") String tablename);

    @Select("select count(*) from ChatGptQueue")
    Integer allqueue();

}
