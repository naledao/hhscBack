package com.example.hhcs.util;

import com.example.hhcs.dao.AstoreDao;
import com.example.hhcs.dao.BstoreDao;
import com.example.hhcs.dao.CstoreDao;
import com.example.hhcs.dao.SstoreDao;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.Sstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class DelUserGood {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SstoreDao sstoreDao;
    @Transactional
    public int del(String rate,String goodid,String openid)
    {
        if(rate.equals("s"))
        {
            Sstore sstore= sstoreDao.getbyid(Integer.parseInt(goodid));
            if(sstore==null )
            {
                return 0;
            }
            else if(sstore.getStatus()==0 && sstore.getPurchase_price()!=-1)
            {
                return 1;
            }
            else if(!sstore.getOpenid().equals(openid))
            {
                return 0;
            }
            else
            {
                if(stringRedisTemplate.delete("start"))
                {
                    return 2;
                }
                else
                {
                    return 0;
                }
            }
        }
        else if(rate.equals("a"))
        {
            Sstore sstore= astoreDao.getnyid(Integer.parseInt(goodid));
            if(sstore==null)
            {
                return 0;
            }
            else if(sstore.getStatus()==0 && sstore.getPurchase_price()!=-1)
            {
                return 1;
            }
            else if(!sstore.getOpenid().equals(openid))
            {
                return 0;
            }
            else
            {
                if(stringRedisTemplate.delete("roll"))
                {
                    return 2;
                }
                else
                {
                    return 0;
                }
            }
        }
        else if(rate.equals("b"))
        {
            Bstore bstore= bstoreDao.getbyid(Integer.parseInt(goodid));
            if(bstore==null)
            {
                return 0;
            }
            else if(bstore.getStatus()==0 && bstore.getPurchase_price()!=-1)
            {
                return 1;
            }
            else if(!bstore.getOpenid().equals(openid) || bstore.getKeynm()==null || bstore.getKeynm().equals(""))
            {
                return 0;
            }
            else
            {
                List<String> list=stringRedisTemplate.opsForList().range("slide",0,-1);
                if(list==null || list.size()==0)
                {
                    return 0;
                }
                else
                {
                    for(int i=0;i<list.size();i++)
                    {
                        String key=list.get(i).split(":")[0];
                        if(key.equals(bstore.getKeynm()))
                        {
                            stringRedisTemplate.opsForValue().set("slidezhifu:1","1",6, TimeUnit.SECONDS);
                            stringRedisTemplate.opsForValue().set("slidezhifu:2","2",6, TimeUnit.SECONDS);
                            stringRedisTemplate.opsForValue().set("slidezhifu:3","3",6, TimeUnit.SECONDS);
                            stringRedisTemplate.delete("slide");
                            for(int k=0;k<list.size();k++)
                            {
                                if(k!=i)
                                {
                                    stringRedisTemplate.opsForList().rightPush("slide",list.get(k));
                                }
                            }
                            return 2;
                        }
                    }
                    return 0;
                }
            }
        }
        else if(rate.equals("c"))
        {
            Cstore cstore= cstoreDao.getc(goodid);
            if(cstore==null)
            {
                return 0;
            }
            else if(cstore.getStatus()==0 && cstore.getPurchase_price()!=-1)
            {
                return 1;
            }
            else if(!cstore.getOpenid().equals(openid))
            {
                return 0;
            }
            else
            {
                int res=cstoreDao.del(goodid);
                if(res==1)
                {
                    return 2;
                }
                else
                {
                    return 0;
                }
            }
        }
        else
        {
            return 0;
        }
    }
}
