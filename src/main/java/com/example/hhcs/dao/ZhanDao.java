package com.example.hhcs.dao;

import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.Zhan;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ZhanDao {
    @Select("select * from Zhan where id=#{id}")
    Zhan get(@Param("id") String id);


    @Insert("insert into Zhan values(#{id},#{history})")
    int init(Zhan zhan);


    @Update("update Zhan set history=#{zhan} where id=#{id}")
    int update(@Param("id") String id,@Param("zhan") String zhan);
}
