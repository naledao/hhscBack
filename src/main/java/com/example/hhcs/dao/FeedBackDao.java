package com.example.hhcs.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FeedBackDao {
    @Insert("insert into feedback values(#{id},#{feedback})")
    int add(@Param("id") Integer id,@Param("feedback") String feedback);
}
