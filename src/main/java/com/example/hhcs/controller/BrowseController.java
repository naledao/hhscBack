package com.example.hhcs.controller;

import com.example.hhcs.domain.Result;
import com.example.hhcs.util.GetBrowseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/browsehistory")
public class BrowseController {
    @Autowired
    private GetBrowseHistory getBrowseHistory;
    @GetMapping("/{openid}/{choice}")
    private Result get(@PathVariable String openid,@PathVariable String choice){
        return getBrowseHistory.get(openid,choice);
    }
}
