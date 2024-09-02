package com.example.hhcs.util;

import com.example.hhcs.dao.AstoreDao;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.Sstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SelectorAgods {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AstoreDao astoreDao;
    public Result get(String area, int page)
    {
        if(area.equals("all"))
        {
            String opneid=stringRedisTemplate.opsForValue().get("roll");
            if(opneid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= astoreDao.get();
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
        else if(area.equals("hospital"))
        {
            String opneid=stringRedisTemplate.opsForValue().get("roll");
            if(opneid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= astoreDao.get();
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
            String opneid=stringRedisTemplate.opsForValue().get("roll");
            if(opneid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= astoreDao.get();
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
            String opneid=stringRedisTemplate.opsForValue().get("roll");
            if(opneid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= astoreDao.get();
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
