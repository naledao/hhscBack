package com.example.hhcs.controller;

import com.example.hhcs.dao.InstructionDao;
import com.example.hhcs.domain.Instruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/instruction")
public class InstructionController {
    @Autowired
    private InstructionDao instructionDao;
    @GetMapping
    private List<Instruction> get()
    {
        List<Instruction> list=instructionDao.get();
        if(list==null || list.size()==0)
        {
            return new ArrayList<>();
        }
        else
        {
            return list;
        }
    }
}
