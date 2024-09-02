package com.example.hhcs.dao;


import com.example.hhcs.domain.Sstore;
import org.apache.ibatis.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

@Mapper
public interface SstoreDao {
//    创建表



    @Update("update sstore set status=#{status} where id=#{id}")
    int upstatus(@Param("status") int status,@Param("id") int id);
    @Select("select * from sstore where id=#{id}")
    Sstore getbyid(@Param("id") int id);

    @Select("select * from sstore")
    Sstore get();

    @Select("select status from sstore")
    int getstatus();

    @Select("select Purchase_price from sstore")
    double getpurchase();

    @Select("select openid from sstore")
    String getopenid();

    @Update("update sstore set Purchase_price=#{Purchase_price}")
    int updatepur(double Purchase_price);

    @Update("update sstore set Purchase_people=#{Purchase_people}")
    int updateppeop(String Purchase_people);

    @Update("update sstore set Purchase_people=#{Purchase_people},Purchase_price=#{Purchase_price} where id=#{id}")
    int uplast(@Param("Purchase_people") String people,@Param("Purchase_price") double price,@Param("id") int id);
    @Delete("delete from sstore")
    int del();

    @Insert("insert into sstore values(#{id},#{name},#{pic},#{description},#{image},#{price},#{Purchase_price},#{Purchase_people},#{business},#{area},#{status},#{openid})")
    int addstore(Sstore sstore);


    @Update("update sstore set name=#{name},pic=#{pic},description=#{description},image=#{image},price=#{price},Purchase_price=#{Purchase_price},Purchase_people=#{Purchase_people},business=#{business},area=#{area},status=#{status},openid=#{openid} where id=#{id}")
    int upall(Sstore sstore);

}
