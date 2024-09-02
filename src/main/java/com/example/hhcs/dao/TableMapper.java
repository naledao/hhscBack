package com.example.hhcs.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TableMapper {
//    创建聊天表
    void createTable(String tableName);



//    创建评论表
    void createComments(String tableName);


//    创建与chatgpt聊天表
    void createchattogpt(String tableName);
}
