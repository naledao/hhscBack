package com.example.hhcs.util;

import com.example.hhcs.dao.BstoreDao;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SelectorBgoods {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BstoreDao bstoreDao;
    public Result get(String area, int page)
    {
        if(area.equals("all"))
        {
            List<String> list = stringRedisTemplate.opsForList().range("slide", 0, -1);
            if(list==null || list.size()==0)
            {
                return new Result(0,"");
            }
            else
            {
                List<Bstore> list1=new ArrayList<>();
                for(String i:list)
                {
                    String key=i.split(":")[0];
                    Bstore bstore= bstoreDao.getbykey(key);
                    if(bstore!=null)
                    {
                        list1.add(bstore);
                    }
                }
                return new Result(1, Collections.singletonList(list1));
            }
        }
        else if(area.equals("hospital"))
        {
            List<String> list = stringRedisTemplate.opsForList().range("slide", 0, -1);
            if(list==null || list.size()==0)
            {
                return new Result(0,"");
            }
            else
            {
                List<Bstore> list1=new ArrayList<>();
                for(String i:list)
                {
                    String key=i.split(":")[0];
                    Bstore bstore= bstoreDao.getbykey(key);
                    if(bstore!=null && bstore.getArea().equals("医学院区"))
                    {
                        list1.add(bstore);
                    }
                }
                return new Result(1, Collections.singletonList(list1));
            }
        }
        else if(area.equals("north"))
        {
            List<String> list = stringRedisTemplate.opsForList().range("slide", 0, -1);
            if(list==null || list.size()==0)
            {
                return new Result(0,"");
            }
            else
            {
                List<Bstore> list1=new ArrayList<>();
                for(String i:list)
                {
                    String key=i.split(":")[0];
                    Bstore bstore= bstoreDao.getbykey(key);
                    if(bstore!=null && bstore.getArea().equals("北区"))
                    {
                        list1.add(bstore);
                    }
                }
                return new Result(1, Collections.singletonList(list1));
            }
        }
        else if(area.equals("south"))
        {
            List<String> list = stringRedisTemplate.opsForList().range("slide", 0, -1);
            if(list==null || list.size()==0)
            {
                return new Result(0,"");
            }
            else
            {
                List<Bstore> list1=new ArrayList<>();
                for(String i:list)
                {
                    String key=i.split(":")[0];
                    Bstore bstore= bstoreDao.getbykey(key);
                    if(bstore!=null && bstore.getArea().equals("南区"))
                    {
                        list1.add(bstore);
                    }
                }
                return new Result(1, Collections.singletonList(list1));
            }
        }
        else
        {
            return new Result(0,"");
        }
    }
}
