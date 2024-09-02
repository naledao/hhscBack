package com.example.hhcs.dao;

import com.example.hhcs.domain.Sstore;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AstoreDao {
    @Update("update astore set status=#{status} where id=#{id}")
    int upstatus(@Param("status") int status,@Param("id") int id);
    @Select("select * from astore")
    Sstore get();

    @Select("select * from astore where id=#{id}")
    Sstore getnyid(@Param("id") int id);

    @Insert("insert into astore values(#{id},#{name},#{pic},#{description},#{image},#{price},#{Purchase_price},#{Purchase_people},#{business},#{area},#{status},#{openid})")
    int addstore(Sstore sstore);

    @Delete("delete from astore")
    int del();

    @Update("update astore set name=#{name},pic=#{pic},description=#{description},image=#{image},price=#{price},Purchase_price=#{Purchase_price},Purchase_people=#{Purchase_people},business=#{business},area=#{area},status=#{status},openid=#{openid} where id=#{id}")
    int upall(Sstore sstore);

//    修改求购价格和求购人
    @Update("update astore set Purchase_price=#{price},Purchase_people=#{openid}")
    int uppurch(@Param("price") double price,@Param("openid") String openid);


    @Update("update astore set Purchase_price=#{price},Purchase_people=#{openid} where id=#{id}")
    int uplast(@Param("price") double price,@Param("openid") String openid,@Param("id") int id);
}
