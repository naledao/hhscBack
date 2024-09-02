package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class GetTwoId {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private WareHouseDao wareHouseDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private AstoreDao astoreDao;
    public Result gettwoid(String openid,String choice,String key)
    {
//        获取用户的仓库id
        int warehouseid;
        User user=userDao.get(openid);
        if(user!=null)
        {
            warehouseid=user.getWarehouse();
//            判断上传商品的等级
            if (choice.equals("c"))
            {
                if(openid.contains("-"))
                {
                    openid = openid.replaceAll("-", "");
                }
//                判断用户c商品是否超过40个
                WareHouseC wareHouseC= wareHouseDao.getc(warehouseid);
                if(wareHouseC==null)
                {
//                    该用户第一次上传商品
                    Cstore cstore=new Cstore();
                    cstore.setActivation(0);
                    String imageid=openid+System.currentTimeMillis();
                    cstore.setId(imageid);
                    int res=cstoreDao.addc(cstore);
                    if(res==1)
                    {
                        wareHouseC=new WareHouseC();
                        wareHouseC.setId(warehouseid);
                        int res1=wareHouseDao.addc(wareHouseC);
                        if(res1==1)
                        {
                            return new Result(1,warehouseid,imageid);
                        }
                        else
                        {
                            return new Result(0,"");
                        }
                    }
                    else
                    {
//                        上传失败
                        return new Result(0,"");
                    }
                }
                else
                {
//                    判断用户c商品是否超过40个
                    String listcstore=wareHouseC.getCid();
                    if(listcstore==null || listcstore.length()==0)
                    {
                        Cstore cstore=new Cstore();
                        cstore.setActivation(0);
                        long time=System.currentTimeMillis();
                        String imageid=openid+time;
                        cstore.setId(imageid);
                        int res=cstoreDao.addc(cstore);
                        if(res==1)
                        {
                            return new Result(1,warehouseid, imageid);
                        }
                        else
                        {
                            return new Result(0,"");
                        }
                    }
                    else
                    {
                        String[] x=listcstore.split("[*]");
                        if(x.length>=30)
                        {
                            return new Result(2,"");
                        }
                        else
                        {
                            Cstore cstore=new Cstore();
                            cstore.setActivation(0);
                            long time=System.currentTimeMillis();
                            String imageid=openid+time;
                            cstore.setId(imageid);
                            int res=cstoreDao.addc(cstore);
                            if(res==1)
                            {
                                return new Result(1,warehouseid, imageid);
                            }
                            else
                            {
                                return new Result(0,"");
                            }
                        }
                    }
                }
            }
            if(choice.equals("s"))
            {
                String start=stringRedisTemplate.opsForValue().get("start");
                if(start!=null)
                {
                    return new Result(0,"");
                }
//                删除s级商品和s级商品的图片仓库
                ImageS imageS=imageDao.get();
                Sstore sstore= sstoreDao.get();
                if(imageS==null && sstore==null)
                {
                    //                        为新商品分配id和imageid
                    sstoreDao.addstore(new Sstore());
                    Sstore sstore1=sstoreDao.get();
                    if(sstore1!=null)
                    {
                        int imageid=sstore1.getId();
                        return new Result(1,warehouseid,imageid);
                    }
                    else
                    {
                        return new Result(0,"");
                    }
                }
                else
                {
                    if(imageS!=null && sstore!=null)
                    {
                        int res=imageDao.dels();
                        int res2=sstoreDao.del();
//                    删除完毕
                        if(res+res2==2)
                        {
//                        为新商品分配id和imageid
                            sstoreDao.addstore(new Sstore());
                            Sstore sstore1=sstoreDao.get();
                            if(sstore1!=null)
                            {
                                int imageid=sstore1.getId();
                                return new Result(1,warehouseid,imageid);
                            }
                            else
                            {
                                return new Result(0,"");
                            }
                        }
                        else
                        {
                            return new Result(0,"");
                        }
                    }
                    else
                    {
                        int res=imageDao.dels();
                        int res2=sstoreDao.del();
                        return new Result(0,"");
                    }
                }

            }


            if(choice.equals("a"))
            {
                String roll=stringRedisTemplate.opsForValue().get("roll");
                if(roll!=null)
                {
                    return new Result(0,"");
                }
//                判断图片仓库和商品是否存在
                ImageS imageS=imageDao.geta();
                Sstore sstore= astoreDao.get();
//                判断图片仓库和商品不存在
                if(imageS==null && sstore==null)
                {
                    //                        为新商品分配id和imageid
                    astoreDao.addstore(new Sstore());
                    Sstore sstore1=astoreDao.get();
                    if(sstore1!=null)
                    {
                        int imageid=sstore1.getId();
                        return new Result(1,warehouseid,imageid);
                    }
                    else
                    {
                        System.out.println(0);
                        return new Result(0,"");
                    }
                }
//                判断图片仓库和商品存在
                else
                {
                    if(imageS!=null && sstore!=null)
                    {
                        int res=imageDao.dela();
                        int res2=astoreDao.del();
//                    删除完毕
                        if(res+res2==2)
                        {
//                        为新商品分配id和imageid
                            astoreDao.addstore(new Sstore());
                            Sstore sstore1=astoreDao.get();
                            if(sstore1!=null)
                            {
                                int imageid=sstore1.getId();
                                return new Result(1,warehouseid,imageid);
                            }
                            else
                            {
                                System.out.println(1);
                                return new Result(0,"");
                            }
                        }
                        else
                        {
                            System.out.println(2);
                            return new Result(0,"");
                        }
                    }
                    else
                    {
                        System.out.println(3);
                        int res=imageDao.dela();
                        int res2=astoreDao.del();
                        return new Result(0,"");
                    }
                }
            }

            if(choice.equals("b"))
            {
                Long len = stringRedisTemplate.opsForList().size("slide");
                if(len!=null && len>=3)
                {
                    return new Result(0,"");
                }
//                判断图片仓库和商品是否存在
                ImageB imageS = imageDao.getb(key);
                Bstore bstore= bstoreDao.getbykey(key);
//                判断图片仓库和商品不存在
                if(imageS==null && bstore==null)
                {
                    //                        为新商品分配id和imageid
                    Bstore bstore1=new Bstore();
                    bstore1.setKeynm(key);
                    bstoreDao.addb(bstore1);
                    Bstore bstore2=bstoreDao.getbykey(key);
                    if(bstore2!=null)
                    {
                        int imageid=bstore2.getId();
                        return new Result(1,warehouseid,imageid);
                    }
                    else
                    {
                        System.out.println(0);
                        return new Result(0,"");
                    }
                }
//                判断图片仓库和商品存在
                else
                {
                    if(imageS!=null && bstore!=null)
                    {
                        int imageid=bstore.getId();
                        return new Result(1,warehouseid,imageid);
                    }
                    else
                    {
                        imageDao.delb(key);
                        bstoreDao.delb(key);
                        return new Result(0,"");
                    }
                }
            }


            else
            {
                System.out.println(4);
                return new Result(0,"");
            }
        }
        else
        {
            System.out.println(5);
//            上传失败
            return new Result(0,"");
        }
    }
}
