package com.example.hhcs.dao;

import com.example.hhcs.domain.Instruction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InstructionDao {
    @Select("select * from instruction")
    List<Instruction> get();
}
