package com.example.hhcs.controller;

import com.example.hhcs.dao.FeedBackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedBackController {
    @Autowired
    private FeedBackDao feedBackDao;
    @PostMapping
    private int add(@RequestParam("feedback") String feedback){
        if(feedback!=null && feedback.length()!=0 && feedback.length()<=200)
        {
            int res= feedBackDao.add(null,feedback);
            if(res==1)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else
        {
            return 0;
        }
    }
}
