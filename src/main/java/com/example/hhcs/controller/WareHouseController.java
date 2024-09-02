package com.example.hhcs.controller;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/warehouse")
public class WareHouseController {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WareHouseDao wareHouseDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SstoreDao sstoreDao;
    @GetMapping("/{openid}/{rate}")
    private Result get(@PathVariable String openid,@PathVariable String rate)
    {
        User user= userDao.get(openid);
        if(user==null)
        {
            return new Result(0,"");
        }
        else
        {
            if(rate.equals("s"))
            {
                String nowopenid=stringRedisTemplate.opsForValue().get("start");
                if(nowopenid==null)
                {
                    return new Result(0,"");
                }
                else
                {
                    WareHouse wareHouse= wareHouseDao.get();
                    if(wareHouse==null)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        int sid=wareHouse.getSid();
                        Sstore sstore=sstoreDao.getbyid(sid);
                        if(sstore==null)
                        {
                            return new Result(0,"");
                        }
                        else
                        {
                            if(sstore.getOpenid().equals(openid))
                            {
                                List<Sstore> list=new ArrayList<>();
                                list.add(sstore);
                                return new Result(1, Collections.singletonList(list));
                            }
                            else
                            {
                                return new Result(0,"");
                            }
                        }
                    }
                }
            }
            else if(rate.equals("a"))
            {
                String nowopenid=stringRedisTemplate.opsForValue().get("roll");
                if(nowopenid==null)
                {
                    return new Result(0,"");
                }
                else
                {
                    WareHouseA wareHouse=wareHouseDao.getA();
                    if(wareHouse==null)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        int aid=wareHouse.getAid();
                        Sstore sstore= astoreDao.getnyid(aid);
                        if(sstore==null || sstore.getOpenid()==null)
                        {
                            return new Result(0,"");
                        }
                        else
                        {
                            if(sstore.getOpenid().equals(openid))
                            {
                                List<Sstore> list=new ArrayList<>();
                                list.add(sstore);
                                return new Result(1, Collections.singletonList(list));
                            }
                            else
                            {
                                return new Result(0,"");
                            }
                        }
                    }
                }
            }
            else if(rate.equals("b"))
            {
                List<String> list=stringRedisTemplate.opsForList().range("slide",0,-1);
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
                        if(bstore!=null && bstore.getOpenid().equals(openid))
                        {
                            list1.add(bstore);
                        }
                    }
                    return new Result(1,Collections.singletonList(list1));
                }
            }
            else if(rate.equals("c"))
            {
                WareHouseC wareHouseC= wareHouseDao.getc(user.getWarehouse());
                if(wareHouseC==null || wareHouseC.getCid()==null || wareHouseC.getCid().equals(""))
                {
                    return new Result(0,"");
                }
                else
                {
                    String[] shuzu=wareHouseC.getCid().split("[*]");
                    List<Cstore> list=new LinkedList<>();
                    for(String i:shuzu)
                    {
                        Cstore cstore= cstoreDao.getc(i);
                        if(cstore!=null)
                        {
                            list.add(cstore);
                        }
                    }
                    return new Result(1,Collections.singletonList(list));
                }
            }
            else
            {
                return new Result(0,"");
            }
        }
    }
}
