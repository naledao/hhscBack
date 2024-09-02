package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GetAllMineGoods {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private WareHouseDao wareHouseDao;
    public LastResult get(String rate, int id,String openid)
    {
        if(rate.equals("s"))
        {
            String nowopenid=stringRedisTemplate.opsForValue().get("start");
            if(nowopenid!=null && nowopenid.equals(openid))
            {
                List<SstoreLast> end=new ArrayList<>();
                List<SstoreLast> all=new ArrayList<>();
                List<SstoreLast> ing=new ArrayList<>();
                Sstore sstore=sstoreDao.get();
                if(sstore!=null && sstore.getPurchase_people()!=null && !sstore.getPurchase_people().equals("") && sstore.getStatus()==0)
                {
                    User user = userDao.get(sstore.getPurchase_people());
                    if(user!=null && user.getName()!=null)
                    {
                        SstoreLast sstoreLast=new SstoreLast(sstore.getId(),sstore.getName(),sstore.getPic(),sstore.getDescription(),sstore.getImage(),sstore.getPrice(),sstore.getPurchase_price(),sstore.getBusiness(),sstore.getArea(),sstore.getStatus(),sstore.getOpenid(),sstore.getPurchase_people(), user.getName());
                        end.add(sstoreLast);
                        all.add(sstoreLast);
                        return new LastResult(1,all,ing,end);
                    }
                    else
                    {
                        return new LastResult(0,"");
                    }
                }
                else
                {
                    if(sstore!=null && sstore.getPurchase_people()!=null && !sstore.getPurchase_people().equals(""))
                    {
                        User user = userDao.get(sstore.getPurchase_people());
                        if(user!=null  && user.getName()!=null)
                        {
                            SstoreLast sstoreLast=new SstoreLast(sstore.getId(),sstore.getName(),sstore.getPic(),sstore.getDescription(),sstore.getImage(),sstore.getPrice(),sstore.getPurchase_price(),sstore.getBusiness(),sstore.getArea(),sstore.getStatus(),sstore.getOpenid(),sstore.getPurchase_people(), user.getName());
                            ing.add(sstoreLast);
                            all.add(sstoreLast);
                            return new LastResult(1,all,ing,end);
                        }
                        else
                        {
                            return new LastResult(0,"");
                        }
                    }
                    else
                    {
                        if(sstore!=null)
                        {
                            SstoreLast sstoreLast=new SstoreLast(sstore.getId(),sstore.getName(),sstore.getPic(),sstore.getDescription(),sstore.getImage(),sstore.getPrice(),sstore.getPurchase_price(),sstore.getBusiness(),sstore.getArea(),sstore.getStatus(),sstore.getOpenid(),sstore.getPurchase_people(), "暂无求购者");
                            all.add(sstoreLast);
                            return new LastResult(1,all,ing,end);
                        }
                        else
                        {
                            return new LastResult(0,"");
                        }
                    }
                }
            }
            else
            {
                return new LastResult(0,"");
            }
        }
        else if(rate.equals("a"))
        {
            String nowopenid=stringRedisTemplate.opsForValue().get("roll");
            if(nowopenid!=null && nowopenid.equals(openid))
            {
                List<SstoreLast> end=new ArrayList<>();
                List<SstoreLast> all=new ArrayList<>();
                List<SstoreLast> ing=new ArrayList<>();
                Sstore sstore= astoreDao.get();
                if(sstore!=null && sstore.getPurchase_people()!=null && !sstore.getPurchase_people().equals("") && sstore.getStatus()==0)
                {
                    User user = userDao.get(sstore.getPurchase_people());
                    if(user!=null && user.getName()!=null)
                    {
                        SstoreLast sstoreLast=new SstoreLast(sstore.getId(),sstore.getName(),sstore.getPic(),sstore.getDescription(),sstore.getImage(),sstore.getPrice(),sstore.getPurchase_price(),sstore.getBusiness(),sstore.getArea(),sstore.getStatus(),sstore.getOpenid(),sstore.getPurchase_people(), user.getName());
                        end.add(sstoreLast);
                        all.add(sstoreLast);
                        return new LastResult(1,all,ing,end);
                    }
                    else
                    {
                        return new LastResult(0,"");
                    }
                }
                else
                {
                    if(sstore!=null && sstore.getPurchase_people()!=null && !sstore.getPurchase_people().equals(""))
                    {
                        User user = userDao.get(sstore.getPurchase_people());
                        if(user!=null  && user.getName()!=null)
                        {
                            SstoreLast sstoreLast=new SstoreLast(sstore.getId(),sstore.getName(),sstore.getPic(),sstore.getDescription(),sstore.getImage(),sstore.getPrice(),sstore.getPurchase_price(),sstore.getBusiness(),sstore.getArea(),sstore.getStatus(),sstore.getOpenid(),sstore.getPurchase_people(), user.getName());
                            ing.add(sstoreLast);
                            all.add(sstoreLast);
                            return new LastResult(1,all,ing,end);
                        }
                        else
                        {
                            return new LastResult(0,"");
                        }
                    }
                    else
                    {
                        if(sstore!=null)
                        {
                            SstoreLast sstoreLast=new SstoreLast(sstore.getId(),sstore.getName(),sstore.getPic(),sstore.getDescription(),sstore.getImage(),sstore.getPrice(),sstore.getPurchase_price(),sstore.getBusiness(),sstore.getArea(),sstore.getStatus(),sstore.getOpenid(),sstore.getPurchase_people(), "暂无求购者");
                            all.add(sstoreLast);
                            return new LastResult(1,all,ing,end);
                        }
                        else
                        {
                            return new LastResult(0,"");
                        }
                    }
                }
            }
            else
            {
                return new LastResult(0,"");
            }
        }
        else if(rate.equals("b"))
        {
            List<String> list=stringRedisTemplate.opsForList().range("slide",0,-1);
            if(list==null || list.size()==0)
            {
                return new LastResult(0,"");
            }
            else
            {
                List<BstoreLast> all=new ArrayList<>();
                List<BstoreLast> ing=new ArrayList<>();
                List<BstoreLast> end=new ArrayList<>();
                for(String i:list)
                {
                    String key=i.split(":")[0];
                    String nowopenid=i.split(":")[1];
                    if(nowopenid.equals(openid))
                    {
                        Bstore bstore= bstoreDao.getbykey(key);
                        if(bstore!=null)
                        {
                            if(bstore.getPurchase_people()!=null && !bstore.getPurchase_people().equals("")  && bstore.getStatus()==0)
                            {
                                User user= userDao.get(bstore.getPurchase_people());
                                if(user!=null && user.getName()!=null)
                                {
                                    BstoreLast bstoreLast=new BstoreLast(bstore.getId(),bstore.getName(),bstore.getPic(),bstore.getDescription(),bstore.getImage(),bstore.getPrice(),bstore.getPurchase_price(),bstore.getBusiness(),bstore.getArea(),bstore.getStatus(),bstore.getOpenid(),bstore.getPurchase_people(),bstore.getKeynm(), user.getName());
                                    all.add(bstoreLast);
                                    end.add(bstoreLast);
                                }
                            }
                            else
                            {
                                if(bstore.getPurchase_people()!=null && !bstore.getPurchase_people().equals(""))
                                {
                                    User user= userDao.get(bstore.getPurchase_people());
                                    if(user!=null && user.getName()!=null)
                                    {
                                        BstoreLast bstoreLast=new BstoreLast(bstore.getId(),bstore.getName(),bstore.getPic(),bstore.getDescription(),bstore.getImage(),bstore.getPrice(),bstore.getPurchase_price(),bstore.getBusiness(),bstore.getArea(),bstore.getStatus(),bstore.getOpenid(),bstore.getPurchase_people(),bstore.getKeynm(), user.getName());
                                        all.add(bstoreLast);
                                        ing.add(bstoreLast);
                                    }
                                }
                                else
                                {
                                    BstoreLast bstoreLast=new BstoreLast(bstore.getId(),bstore.getName(),bstore.getPic(),bstore.getDescription(),bstore.getImage(),bstore.getPrice(),bstore.getPurchase_price(),bstore.getBusiness(),bstore.getArea(),bstore.getStatus(),bstore.getOpenid(),bstore.getPurchase_people(),bstore.getKeynm(), "暂无求购者");
                                    all.add(bstoreLast);
                                }
                            }
                        }
                    }
                }
                return new LastResult(1,all,ing,end);
            }
        }
        else if(rate.equals("c"))
        {
            WareHouseC wareHousec= wareHouseDao.getcw(id);
            if(wareHousec==null || wareHousec.getCid()==null || wareHousec.getCid().equals(""))
            {
                return new LastResult(0,"");
            }
            else
            {
                List<CstoreLast> all=new LinkedList<>();
                List<CstoreLast> ing=new LinkedList<>();
                List<CstoreLast> end=new LinkedList<>();
                String[] x=wareHousec.getCid().split("[*]");
                int sum=0;
                StringBuilder stringBuilder=new StringBuilder();
                for(String i:x)
                {
                    Cstore cstore= cstoreDao.getc(i);
                    if(cstore!=null && cstore.getActivation()==1)
                    {
                        if(cstore.getPurchase_people()!=null && !cstore.getPurchase_people().equals("") && cstore.getStatus()==0)
                        {
                            User user= userDao.get(cstore.getPurchase_people());
                            if(user!=null && user.getName()!=null)
                            {
                                CstoreLast cstoreLast=new CstoreLast(cstore.getId(),cstore.getName(),cstore.getPic(),cstore.getDescription(),cstore.getImage(),cstore.getPrice(),cstore.getPurchase_price(),cstore.getBusiness(),cstore.getArea(),cstore.getStatus(),cstore.getOpenid(),cstore.getPurchase_people(),cstore.getActivation(),cstore.getTime(), user.getName());
                                all.add(cstoreLast);
                                end.add(cstoreLast);
                                sum=sum+1;
                                stringBuilder.append(i).append("*");
                            }
                        }
                        else
                        {
                            if(cstore.getPurchase_people()!=null && !cstore.getPurchase_people().equals(""))
                            {
                                User user= userDao.get(cstore.getPurchase_people());
                                if(user!=null && user.getName()!=null)
                                {
                                    CstoreLast cstoreLast=new CstoreLast(cstore.getId(),cstore.getName(),cstore.getPic(),cstore.getDescription(),cstore.getImage(),cstore.getPrice(),cstore.getPurchase_price(),cstore.getBusiness(),cstore.getArea(),cstore.getStatus(),cstore.getOpenid(),cstore.getPurchase_people(),cstore.getActivation(),cstore.getTime(), user.getName());
                                    all.add(cstoreLast);
                                    ing.add(cstoreLast);
                                    sum=sum+1;
                                    stringBuilder.append(i).append("*");
                                }
                            }
                            else
                            {
                                CstoreLast cstoreLast=new CstoreLast(cstore.getId(),cstore.getName(),cstore.getPic(),cstore.getDescription(),cstore.getImage(),cstore.getPrice(),cstore.getPurchase_price(),cstore.getBusiness(),cstore.getArea(),cstore.getStatus(),cstore.getOpenid(),cstore.getPurchase_people(),cstore.getActivation(),cstore.getTime(), "暂无求购者");
                                all.add(cstoreLast);
                                sum=sum+1;
                                stringBuilder.append(i).append("*");
                            }
                        }
                    }
                }
                if(sum!= x.length)
                {
                    wareHouseDao.upcw(new String(stringBuilder),wareHousec.getId());
                }
                return new LastResult(1,all,ing,end);
            }
        }
        else
        {
            return new LastResult(0,"");
        }
    }
}
