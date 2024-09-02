package com.example.hhcs.dao;

import com.example.hhcs.domain.Cstore;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CstoreDao {
    @Update("update cstore set status=#{status} where id=#{id}")
    int upstatus(@Param("status") int status,@Param("id") String id);
    @Delete("delete from cstore where id=#{id}")
    int del(@Param("id") String id);
    @Insert("insert into cstore values(#{id},#{name},#{pic},#{description},#{image},#{price},#{Purchase_price},#{Purchase_people},#{business},#{area},#{status},#{openid},#{activation},#{time})")
    int addc(Cstore cstore);

    @Update("update cstore set name=#{name},pic=#{pic},description=#{description},image=#{image},price=#{price},Purchase_price=#{Purchase_price},Purchase_people=#{Purchase_people},business=#{business},area=#{area},status=#{status},openid=#{openid},time=#{time} where id=#{id}")
    int upc(Cstore cstore);

    @Update("update cstore set activation=#{acti} where id=#{id}")
    int upac(@Param("acti") int acti,@Param("id") String id);

//    普通商品全部
    @Select("select * from cstore where activation=#{acti} ORDER BY time DESC")
    List<Cstore> getareaalllist(@Param("acti") int acti);

    @Select("select count(*) from cstore where activation=#{acti}")
    int getallall(@Param("acti") int acti);


    @Select("select count(*) from cstore where activation=#{acti} and area=#{area}")
    int getalldisarea(@Param("acti") int acti,@Param("area") String area);

    @Select("select * from cstore where activation=#{acti} and area=#{area} ORDER BY time DESC")
    List<Cstore> getarealist(@Param("acti") int acti,@Param("area") String area);


    @Select("select count(*) from cstore where name like CONCAT('%', #{text} ,'%')")
    int getfree(@Param("text") String text);

    @Select("select * from cstore where name like CONCAT('%', #{text} ,'%')")
    List<Cstore> getfreesearch(@Param("text") String text);

    @Select("select * from cstore where id=#{id}")
    Cstore getc(@Param("id") String id);


    @Update("update cstore set Purchase_price=#{price},Purchase_people=#{people} where id=#{id}")
    int uppur(@Param("price") double price,@Param("people") String people,@Param("id") String id);
}
