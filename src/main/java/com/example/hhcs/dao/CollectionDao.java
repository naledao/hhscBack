package com.example.hhcs.dao;

import com.example.hhcs.domain.LikeGoods;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CollectionDao {
    @Select("select * from likegoods where id=#{id}")
    LikeGoods get(@Param("id") int id);


    @Insert("insert into likegoods values(#{id},#{likeid})")
    int add(LikeGoods likeGoods);



    @Update("update likegoods set likeid=#{likeid} where id=#{id}")
    int up(LikeGoods likeGoods);
}
