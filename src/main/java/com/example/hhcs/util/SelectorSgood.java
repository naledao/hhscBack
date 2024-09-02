package com.example.hhcs.util;

import com.example.hhcs.dao.SstoreDao;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.Sstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SelectorSgood {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SstoreDao sstoreDao;
    public Result get(String area, int page)
    {
        if(area.equals("all"))
        {
            String openid=stringRedisTemplate.opsForValue().get("start");
            if(openid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= sstoreDao.get();
                if(sstore==null)
                {
                    return new Result(0,"");
                }
                else
                {
                    List<Sstore> list=new ArrayList<>();
                    list.add(sstore);
                    return new Result(1, Collections.singletonList(list));
                }
            }
        }
        else if (area.equals("hospital"))
        {
            String openid=stringRedisTemplate.opsForValue().get("start");
            if(openid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= sstoreDao.get();
                if(sstore==null || !sstore.getArea().equals("医学院区"))
                {
                    return new Result(0,"");
                }
                else
                {
                    List<Sstore> list=new ArrayList<>();
                    list.add(sstore);
                    return new Result(1, Collections.singletonList(list));
                }
            }
        }
        else if(area.equals("north"))
        {
            String openid=stringRedisTemplate.opsForValue().get("start");
            if(openid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= sstoreDao.get();
                if(sstore==null || !sstore.getArea().equals("北区"))
                {
                    return new Result(0,"");
                }
                else
                {
                    List<Sstore> list=new ArrayList<>();
                    list.add(sstore);
                    return new Result(1, Collections.singletonList(list));
                }
            }
        }
        else if(area.equals("south"))
        {
            String openid=stringRedisTemplate.opsForValue().get("start");
            if(openid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= sstoreDao.get();
                if(sstore==null || !sstore.getArea().equals("南区"))
                {
                    return new Result(0,"");
                }
                else
                {
                    List<Sstore> list=new ArrayList<>();
                    list.add(sstore);
                    return new Result(1, Collections.singletonList(list));
                }
            }
        }
        else
        {
            return new Result(0,"");
        }
    }

}
