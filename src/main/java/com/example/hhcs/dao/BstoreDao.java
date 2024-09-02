package com.example.hhcs.dao;

import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.Sstore;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BstoreDao {
    @Update("update bstore set status=#{status} where id=#{id}")
    int upstatus(@Param("status") int status,@Param("id") int id);
    @Select("select * from bstore where keynm=#{key}")
    Bstore getbykey(@Param("key") String key);

    @Select("select * from bstore where id=#{id}")
    Bstore getbyid(@Param("id") int id);


    @Insert("insert into bstore values(#{id},#{name},#{pic},#{description},#{image},#{price},#{Purchase_price},#{Purchase_people},#{business},#{area},#{status},#{openid},#{keynm})")
    int addb(Bstore bstore);

    @Delete("delete from bstore where keynm=#{key}")
    int delb(@Param("key") String key);

    @Update("update bstore set name=#{name},pic=#{pic},description=#{description},image=#{image},price=#{price},Purchase_price=#{Purchase_price},Purchase_people=#{Purchase_people},business=#{business},area=#{area},status=#{status},openid=#{openid} where id=#{id}")
    int upb(Sstore sstore);

//    更新求购信息
    @Update("update bstore set Purchase_price=#{price},Purchase_people=#{people} where id=#{id}")
    int uppur(@Param("price") double price,@Param("people") String people,@Param("id") int id);
}
