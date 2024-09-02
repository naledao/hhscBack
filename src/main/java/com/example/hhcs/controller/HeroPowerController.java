package com.example.hhcs.controller;

import com.example.hhcs.domain.Hero;
import com.example.hhcs.util.HeroPower;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hero")
public class HeroPowerController {
    @PostMapping
    @CrossOrigin
    private Hero get(@Param("name") String name)
    {
        if(name==null)
        {
            Hero hero=new Hero();
            hero.setCode(400);
            return hero;
        }
        else
        {
            HeroPower heroPower=new HeroPower();
            return heroPower.get(name);
        }
    }

    @PostMapping("/power")
    @CrossOrigin
    private Hero getpower(@Param("name") String name,@Param("type") String type)
    {
        if(name==null || type==null)
        {
            Hero hero=new Hero();
            hero.setCode(400);
            return hero;
        }
        else
        {
            RestTemplate restTemplate=new RestTemplate();
            Hero hero=restTemplate.getForObject("https://www.somekey.cn/mini/hero/getHeroInfo.php?hero="+name+"&type="+type,Hero.class);
            return hero;
        }
    }
}
