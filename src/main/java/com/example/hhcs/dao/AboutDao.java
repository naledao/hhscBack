package com.example.hhcs.dao;

import com.example.hhcs.domain.About;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AboutDao {
    @Select("select * from about")
    List<About> get();
}
