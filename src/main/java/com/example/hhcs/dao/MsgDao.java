package com.example.hhcs.dao;

import com.example.hhcs.domain.Comments;
import com.example.hhcs.domain.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MsgDao {

    @Select("select * from ${table}")
    List<Message> get(@Param("table") String table);

    @Select("select * from ${table} ORDER BY time DESC")
    List<Comments> getCom(@Param("table") String table);


    @Select("select count(*) from ${table}")
    int sum(@Param("table") String table);

    @Select("select * from ${table} ORDER BY time DESC limit 1")
    Message getmsg(@Param("table") String table);

    @Insert("insert into ${table} values(#{id},#{openid},#{name},#{msg},#{time},#{pic},#{warehouse})")
    int addCom(@Param("table") String table,@Param("id") Integer id ,@Param("openid") String openid,@Param("name") String name,@Param("msg") String msg,@Param("time") String time,@Param("pic") String pic,@Param("warehouse") Integer warehouse);

    @Insert("insert into ${table} values(#{id},#{openid},#{msg},#{time},#{pic})")
    int add(@Param("table") String table,@Param("id") Integer id ,@Param("openid") String openid,@Param("msg") String msg,@Param("time") String time,@Param("pic") String pic);
}
