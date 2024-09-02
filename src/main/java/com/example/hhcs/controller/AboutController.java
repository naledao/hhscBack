package com.example.hhcs.controller;

import com.example.hhcs.dao.AboutDao;
import com.example.hhcs.domain.About;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutController {
    @Autowired
    private AboutDao aboutDao;
    @GetMapping
    private List<About> get(){
        List<About> list= aboutDao.get();
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
