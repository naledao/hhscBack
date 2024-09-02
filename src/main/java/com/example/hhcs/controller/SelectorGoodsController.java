package com.example.hhcs.controller;

import com.example.hhcs.domain.Result;
import com.example.hhcs.util.SelectorAgods;
import com.example.hhcs.util.SelectorBgoods;
import com.example.hhcs.util.SelectorCgoods;
import com.example.hhcs.util.SelectorSgood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selector")
public class SelectorGoodsController {
    @Autowired
    private SelectorSgood selectorSgood;
    @Autowired
    private SelectorAgods selectorAgods;
    @Autowired
    private SelectorBgoods selectorBgoods;
    @Autowired
    private SelectorCgoods selectorCgoods;
    @GetMapping("/{rate}/{area}/{page}")
    private Result get(@PathVariable String rate,@PathVariable String area,@PathVariable int page)
    {
        if(rate.equals("c"))
        {
            return selectorCgoods.get(area,page);
        }
        else if(rate.equals("b"))
        {
            return selectorBgoods.get(area,page);
        }
        else if(rate.equals("a"))
        {
            return selectorAgods.get(area,page);
        }
        else if(rate.equals("s"))
        {
            return selectorSgood.get(area,page);
        }
        else
        {
            return new Result(0,"");
        }
    }

    @GetMapping("/search/{page}")
    private Result getfree(@RequestParam("text") String text,@PathVariable int page)
    {
        if(text.length()>0 && text.length()<=16)
        {
            return selectorCgoods.getfree(text,page);
        }
        else
        {
            return new Result(0,"");
        }
    }
}
