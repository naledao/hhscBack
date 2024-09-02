package com.example.hhcs.util;

import com.example.hhcs.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SlideZhanYou {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    查看是否有该用户的订单存在
    public Result getOrder(String openid)
    {
        Set<String> set=stringRedisTemplate.keys("slidezhifu:*");
        if(set==null || set.size()==0)
        {
//            没有该用户的订单
            return new Result(0,"");
        }
        else
        {
            Map<String,List<String>> map=new HashMap<>();
            for(String i:set)
            {
                String key=i.split(":")[1];
                if(map.containsKey(key))
                {
                    map.get(key).add(stringRedisTemplate.opsForValue().get(i));
                }
                else
                {
                    List<String> list=new ArrayList<>();
                    list.add(stringRedisTemplate.opsForValue().get(i));
                    map.put(key,list);
                }
            }
            for(Map.Entry<String,List<String>> m: map.entrySet())
            {
                List<String> list=m.getValue();
                if(list.get(0).equals(openid))
                {
                    return new Result(1,list.get(1));
                }
                if(list.get(1).equals(openid))
                {
                    return new Result(1,list.get(0));
                }
            }
//            没有该用户的订单
            return new Result(0,"");
        }
    }



//    查看该用户是否已经占有过位置
    public Result getLocation(String openid)
    {
        Set<String> set=stringRedisTemplate.keys("slideweixin:*");
        if(set==null || set.size()==0)
        {
//            该用户没有占有过位置
            return new Result(0,"");
        }
        else
        {
//            寻找是否有该用户的openid
            for(String i:set)
            {
                String nowopenid=stringRedisTemplate.opsForValue().get(i);
                if(nowopenid!=null && nowopenid.equals(openid))
                {
//                    该用户可以进行抢占
                    return new Result(1,"");
                }
            }
//            该用户没有占有过位置
            return new Result(0,"");
        }
    }



//    计算当前未使用的bkey和还未过期b商品的总数
    public Result Sum()
    {
        int sum=getSumOfBkey()+getSumOfBstore();
        if(sum>=0 && sum<=2)
        {
//            可以进行抢占
            return new Result(0,"");
        }
        else
        {
//            不可进行抢占
            return new Result(1,"");
        }
    }

//    计算当前未使用的bkey
    private int getSumOfBkey()
    {
        Set<String> set=stringRedisTemplate.keys("bkey:*");
        if(set==null || set.size()==0)
        {
            return 0;
        }
        else
        {
            return set.size();
        }
    }


//    计算当前未过期b商品的数量
    private int getSumOfBstore()
    {
        List<String> list=stringRedisTemplate.opsForList().range("slide",0,-1);
        if(list==null || list.size()==0)
        {
            return 0;
        }
        else
        {
            return list.size();
        }

    }


//    计算当前是否可以操作
    public Result yn()
    {
//        计算当前可以操作的人数
        Set<String> order=stringRedisTemplate.keys("slidezhifu:*");
        Set<String> zhanyou=stringRedisTemplate.keys("slideweixin:*");
        Set<String> room=stringRedisTemplate.keys("slideroom:*");
        Set<String> renshuu=new HashSet<>();
        assert order != null;
        for(String i:order)
        {
            if(i.contains("openid"))
            {
                String openid=stringRedisTemplate.opsForValue().get(i);
                renshuu.add(openid);
            }
        }
        assert zhanyou != null;
        for(String i:zhanyou)
        {
            String openid=stringRedisTemplate.opsForValue().get(i);
            if(openid!=null && !openid.equals(""))
            {
                renshuu.add(openid);
            }
        }
        assert room != null;
        for(String i:room)
        {
            String openid=stringRedisTemplate.opsForValue().get(i);
            if(openid!=null && !openid.equals(""))
            {
                renshuu.add(openid);
            }
        }
        int cunzai=getSumOfBkey()+getSumOfBstore()+renshuu.size();
        int shengyu=3-cunzai;
        if(shengyu>0)
        {
//            可以操作
            return new Result(0,"");
        }
        else
        {
            if(renshuu.size()==0)
            {
//                席位占满
                return new Result(1,"");
            }
            else
            {
//                操作人数达到上限
                return new Result(2,"");
            }
        }
    }

}
