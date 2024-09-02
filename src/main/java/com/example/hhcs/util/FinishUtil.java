package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.Bstore;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class FinishUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private UserDao userDao;
    @Transactional
    public int finish(String rate,String goodid,String openid)
    {
        if(rate.equals("s"))
        {
            List<String> list=stringRedisTemplate.opsForList().range("Fs"+goodid,0,-1);
            if(list==null || list.size()==0)
            {
                Sstore sstore= sstoreDao.getbyid(Integer.parseInt(goodid));
                User user= userDao.get(openid);
                if(sstore!=null && user!=null && sstore.getPurchase_price()!=-1 && sstore.getOpenid()!=null && sstore.getPurchase_people()!=null)
                {
                    if(sstore.getOpenid().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fs"+goodid,openid);
                        return 1;
                    }
                    else if(sstore.getPurchase_people().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fs"+goodid,openid);
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                Sstore sstore= sstoreDao.getbyid(Integer.parseInt(goodid));
                User user= userDao.get(openid);
                if(sstore!=null && user!=null && sstore.getPurchase_price()!=-1 && sstore.getOpenid()!=null && sstore.getPurchase_people()!=null)
                {
                    String now= list.get(0);
                    if(now.equals(openid))
                    {
                        return 2;
                    }
                    else
                    {
                        if(now.equals(sstore.getOpenid()))
                        {
                            if(openid.equals(sstore.getPurchase_people()))
                            {
//                                进行最终删除
                                int res=sstoreDao.del();
                                if(res==1)
                                {
                                    stringRedisTemplate.delete("start");
                                    stringRedisTemplate.delete("Fs"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
                        }
                        else if(now.equals(sstore.getPurchase_people()))
                        {
                            if(openid.equals(sstore.getOpenid()))
                            {
//                                进行最终删除
                                int res=sstoreDao.del();
                                if(res==1)
                                {
                                    stringRedisTemplate.delete("start");
                                    stringRedisTemplate.delete("Fs"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
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
        else if(rate.equals("a"))
        {
            List<String> list=stringRedisTemplate.opsForList().range("Fa"+goodid,0,-1);
            if(list==null || list.size()==0)
            {
                Sstore sstore= astoreDao.getnyid(Integer.parseInt(goodid));
                User user= userDao.get(openid);
                if(sstore!=null && user!=null && sstore.getPurchase_price()!=-1 && sstore.getOpenid()!=null && sstore.getPurchase_people()!=null)
                {
                    if(sstore.getOpenid().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fa"+goodid,openid);
                        return 1;
                    }
                    else if(sstore.getPurchase_people().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fa"+goodid,openid);
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                Sstore sstore= astoreDao.getnyid(Integer.parseInt(goodid));
                User user= userDao.get(openid);
                if(sstore!=null && user!=null && sstore.getPurchase_price()!=-1 && sstore.getOpenid()!=null && sstore.getPurchase_people()!=null)
                {
                    String now= list.get(0);
                    if(now.equals(openid))
                    {
                        return 2;
                    }
                    else
                    {
                        if(now.equals(sstore.getOpenid()))
                        {
                            if(openid.equals(sstore.getPurchase_people()))
                            {
//                                进行最终删除
                                int res=astoreDao.del();
                                if(res==1)
                                {
                                    stringRedisTemplate.delete("roll");
                                    stringRedisTemplate.delete("Fa"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
                        }
                        else if(now.equals(sstore.getPurchase_people()))
                        {
                            if(openid.equals(sstore.getOpenid()))
                            {
//                                进行最终删除
                                int res=astoreDao.del();
                                if(res==1)
                                {
                                    stringRedisTemplate.delete("roll");
                                    stringRedisTemplate.delete("Fa"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
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
        else if(rate.equals("b"))
        {
            List<String> list=stringRedisTemplate.opsForList().range("Fb"+goodid,0,-1);
            if(list==null || list.size()==0)
            {
                Bstore bstore= bstoreDao.getbyid(Integer.parseInt(goodid));
                User user= userDao.get(openid);
                if(bstore!=null && user!=null && bstore.getPurchase_price()!=-1 && bstore.getOpenid()!=null && bstore.getPurchase_people()!=null && bstore.getKeynm()!=null)
                {
                    if(bstore.getOpenid().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fb"+goodid,openid);
                        return 1;
                    }
                    else if(bstore.getPurchase_people().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fb"+goodid,openid);
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                Bstore bstore= bstoreDao.getbyid(Integer.parseInt(goodid));
                User user= userDao.get(openid);
                if(bstore!=null && user!=null && bstore.getPurchase_price()!=-1 && bstore.getOpenid()!=null && bstore.getPurchase_people()!=null && bstore.getKeynm()!=null)
                {
                    String now= list.get(0);
                    if(now.equals(openid))
                    {
                        return 2;
                    }
                    else
                    {
                        if(now.equals(bstore.getOpenid()))
                        {
                            if(openid.equals(bstore.getPurchase_people()))
                            {
//                                进行最终删除
                                int res=bstoreDao.delb(bstore.getKeynm());
                                if(res==1)
                                {
                                    stringRedisTemplate.opsForValue().set("slideweixin:"+3,"3",6, TimeUnit.SECONDS);
                                    stringRedisTemplate.opsForValue().set("slideweixin:"+2,"2",6, TimeUnit.SECONDS);
                                    stringRedisTemplate.opsForValue().set("slideweixin:"+1,"1",6, TimeUnit.SECONDS);
                                    List<String> list1=stringRedisTemplate.opsForList().range("slide",0,-1);
                                    if(list1!=null)
                                    {
                                        stringRedisTemplate.delete("slide");
                                        for(String i:list1)
                                        {
                                            if(!bstore.getKeynm().equals(i.split(":")[0]))
                                            {
                                                stringRedisTemplate.opsForList().rightPush("slide",i);
                                            }
                                        }
                                    }
                                    stringRedisTemplate.delete("Fb"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
                        }
                        else if(now.equals(bstore.getPurchase_people()))
                        {
                            if(openid.equals(bstore.getOpenid()))
                            {
//                                进行最终删除
                                int res=bstoreDao.delb(bstore.getKeynm());
                                if(res==1)
                                {
                                    stringRedisTemplate.opsForValue().set("slideweixin:"+3,"3",6, TimeUnit.SECONDS);
                                    stringRedisTemplate.opsForValue().set("slideweixin:"+2,"2",6, TimeUnit.SECONDS);
                                    stringRedisTemplate.opsForValue().set("slideweixin:"+1,"1",6, TimeUnit.SECONDS);
                                    List<String> list1=stringRedisTemplate.opsForList().range("slide",0,-1);
                                    if(list1!=null)
                                    {
                                        stringRedisTemplate.delete("slide");
                                        for(String i:list1)
                                        {
                                            if(!bstore.getKeynm().equals(i.split(":")[0]))
                                            {
                                                stringRedisTemplate.opsForList().rightPush("slide",i);
                                            }
                                        }
                                    }
                                    stringRedisTemplate.delete("Fb"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
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
        else if(rate.equals("c"))
        {
            List<String> list=stringRedisTemplate.opsForList().range("Fc"+goodid,0,-1);
            if(list==null || list.size()==0)
            {
                Cstore cstore= cstoreDao.getc(goodid);
                User user= userDao.get(openid);
                if(cstore!=null && user!=null && cstore.getPurchase_price()!=-1 && cstore.getOpenid()!=null && cstore.getPurchase_people()!=null)
                {
                    if(cstore.getOpenid().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fc"+goodid,openid);
                        return 1;
                    }
                    else if(cstore.getPurchase_people().equals(openid))
                    {
                        stringRedisTemplate.opsForList().rightPush("Fc"+goodid,openid);
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                Cstore cstore= cstoreDao.getc(goodid);
                User user= userDao.get(openid);
                if(cstore!=null && user!=null && cstore.getPurchase_price()!=-1 && cstore.getOpenid()!=null && cstore.getPurchase_people()!=null)
                {
                    String now= list.get(0);
                    if(now.equals(openid))
                    {
                        return 2;
                    }
                    else
                    {
                        if(now.equals(cstore.getOpenid()))
                        {
                            if(openid.equals(cstore.getPurchase_people()))
                            {
//                                进行最终删除
                                int res=cstoreDao.del(goodid);
                                if(res==1)
                                {
                                    stringRedisTemplate.delete("Fc"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
                        }
                        else if(now.equals(cstore.getPurchase_people()))
                        {
                            if(openid.equals(cstore.getOpenid()))
                            {
//                                进行最终删除
                                int res=cstoreDao.del(goodid);
                                if(res==1)
                                {
                                    stringRedisTemplate.delete("Fc"+goodid);
                                    return 3;
                                }
                                else
                                {
                                    return 0;

                                }
                            }
                            else
                            {
                                return 0;
                            }
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
        else
        {
            return 0;
        }
    }
}
