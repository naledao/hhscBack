package com.example.hhcs.util;

import com.example.hhcs.domain.Hero;
import org.springframework.web.client.RestTemplate;

public class HeroPower {
    public Hero get(String name)
    {
        RestTemplate restTemplate=new RestTemplate();
        Hero hero=restTemplate.getForObject("https://www.somekey.cn/mini/hero/getHeroInfo.php?hero="+name+"&type=aqq",Hero.class);
        return hero;
    }
}
