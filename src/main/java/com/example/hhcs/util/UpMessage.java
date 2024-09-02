package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Transactional
@Component
public class UpMessage {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WareHouseDao wareHouseDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String rootpath="/root/tomcat/webapps/";
    public int addmessage(Sstore sstore,String choice,int imageid,int warehouseid,String key,String openid,String Simageid)
    {
        if(choice.equals("c"))
        {
//            完善商品信息
            User user=userDao.get(openid);
            if(user==null)
            {
                System.out.println(0);
                return 0;
            }
            Date date = new Date(); // this object contains the current date value
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=formatter.format(date);
            Cstore cstore=new Cstore();
            cstore.setTime(time);
            cstore.setId(Simageid);
            cstore.setName(sstore.getName());
            cstore.setDescription(sstore.getDescription());
            cstore.setImage(Simageid);
            cstore.setPrice(sstore.getPrice());
            cstore.setPurchase_price(-1);
            cstore.setBusiness(user.getName());
            cstore.setArea(sstore.getArea());
            cstore.setStatus(sstore.getStatus());
            cstore.setOpenid(openid);
//            更新pic
            String path=rootpath+"hhsc/common/"+openid+"/"+Simageid+"/pic/";
            File file=new File(path);
            if(file.exists())
            {
                File[] list=file.listFiles();
                if(list==null || list.length==0)
                {
                    System.out.println(1);
                    return 0;
                }
                else
                {
                    String name=list[0].getName();
                    String pic="http://47.100.9.232:8080/hhsc/common/"+openid+"/"+Simageid+"/pic/"+name;
                    cstore.setPic(pic);
                    int res= cstoreDao.upc(cstore);
                    if(res==1)
                    {
//                        完善图片信息
                        String path1=rootpath+"hhsc/common/"+openid+"/"+Simageid+"/image/";
                        File file1=new File(path1);
                        if(file1.exists())
                        {
                            File[] files=file1.listFiles();
                            if(files==null || files.length==0)
                            {
                                System.out.println(2);
                                return 0;
                            }
                            else
                            {
                                ImageC imageC=new ImageC();
                                imageC.setId(Simageid);
                                int index=0;
                                for(File file11:files)
                                {
                                    index=index+1;
                                    String img="http://47.100.9.232:8080/hhsc/common/"+openid+"/"+Simageid+"/image/"+file11.getName();
                                    if(index==1)
                                    {
                                        imageC.setImg1(img);
                                        continue;
                                    }
                                    if(index==2)
                                    {
                                        imageC.setImg2(img);
                                        continue;
                                    }
                                    if(index==3)
                                    {
                                        imageC.setImg3(img);
                                        continue;
                                    }
                                    if(index==4)
                                    {
                                        imageC.setImg4(img);
                                        continue;
                                    }
                                    if(index==5)
                                    {
                                        imageC.setImg5(img);
                                        continue;
                                    }
                                    if(index==6)
                                    {
                                        imageC.setImg6(img);
                                    }
                                }
                                int res2=imageDao.addc(imageC);
                                if(res2==1)
                                {
                                    int res3=cstoreDao.upac(1,Simageid);
                                    if(res3==1)
                                    {
                                        WareHouseC wareHouseC=wareHouseDao.getcw(warehouseid);
                                        if(wareHouseC==null)
                                        {
                                            System.out.println(3);
                                            return 0;
                                        }
                                        else
                                        {
                                            String cid=wareHouseC.getCid();
                                            if(cid==null)
                                            {
                                                cid="";
                                            }
                                            cid=cid+Simageid+"*";
                                            wareHouseC.setCid(cid);
                                            int res4=wareHouseDao.upcw(cid,warehouseid);
                                            if(res4==1)
                                            {
                                                return 1;
                                            }
                                            else
                                            {
                                                System.out.println(4);
                                                return 0;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        System.out.println(5);
                                        return 0;
                                    }
                                }
                                else
                                {
                                    System.out.println(6);
                                    return 0;
                                }
                            }
                        }
                        else
                        {
                            System.out.println(7);
                            return 0;
                        }
                    }
                    else
                    {
                        System.out.println(8);
                        return 0;
                    }
                }
            }
            else
            {
                System.out.println(9);
                return 0;
            }
        }



        if(choice.equals("s"))
        {
            //        完善对象信息添加到s级商品库
            String cv=stringRedisTemplate.opsForValue().get("startname");
            int gh=Integer.parseInt(cv)-1;
            String pic="http://47.100.9.232:8080/hhsc/start/pic/"+gh+".jpg";
            String path=rootpath+"hhsc/start/image/";
            sstore.setPurchase_price(-1);
            sstore.setPic(pic);
            sstore.setImage(imageid);
            sstore.setId(imageid);
            sstore.setOpenid(openid);
            User user= userDao.get(openid);
            if(user==null)
            {
                return 0;
            }
            sstore.setBusiness(user.getName());
            int res2=sstoreDao.upall(sstore);
            if(res2==1)
            {
                //            添加商品图片仓库
                ImageS imageS=new ImageS();
                File file=new File(path);
                File[] list = file.listFiles();
                String rooturl="http://47.100.9.232:8080/hhsc/start/image/";
                for(File file1:list)
                {
                    String url=rooturl+file1.getName();
                    if(imageS.getImg1()==null)
                    {
                        imageS.setImg1(url);
                    }
                    else if(imageS.getImg2()==null)
                    {
                        imageS.setImg2(url);
                    }
                    else if(imageS.getImg3()==null)
                    {
                        imageS.setImg3(url);
                    }
                    else if(imageS.getImg4()==null)
                    {
                        imageS.setImg4(url);
                    }
                    else if(imageS.getImg5()==null)
                    {
                        imageS.setImg5(url);
                    }
                    else
                    {
                        imageS.setImg6(url);
                    }
                }
                imageS.setId(imageid);
                int res=imageDao.adds(imageS);
                if(res==1)
                {
//                添加用户发布s级商品记录
                    if(wareHouseDao.get()!=null)
                    {
                        int res6=wareHouseDao.dels();
                        if(res6!=1)
                        {
                            System.out.println(1);
                            return 0;
                        }
                    }
                    int res3=wareHouseDao.adds(warehouseid,imageid);
                    if(res3==1)
                    {
//                        删除key
                        String time=stringRedisTemplate.opsForValue().get(key);
                        Boolean res4 = stringRedisTemplate.delete(key);
                        try {
                            if(res4)
                            {
//                                添加时间限制
                                int day=Integer.parseInt(time);
                                stringRedisTemplate.opsForValue().set("start", openid, day, TimeUnit.DAYS);
                                String start=stringRedisTemplate.opsForValue().get("start");
                                if(start==null)
                                {
                                    System.out.println(2);
                                    return 0;
                                }
                                else
                                {
                                    return 1;
                                }
                            }
                            else
                            {
                                System.out.println(3);
                                return 0;
                            }
                        }catch (Exception e){
                            System.out.println(4);
                            return 0;
                        }
                    }
                    else
                    {
                        System.out.println(5);
                        return 0;
                    }
                }
                else
                {
                    System.out.println(6);
                    return 0;
                }
            }
            else
            {
                System.out.println(7);
                return 0;
            }
        }

        if(choice.equals("a"))
        {
            //        完善对象信息添加到a级商品库
            String cv=stringRedisTemplate.opsForValue().get("rollname");
            int gh=Integer.parseInt(cv)-1;
            String pic="http://47.100.9.232:8080/hhsc/roll/pic/"+gh+".jpg";
            String path=rootpath+"hhsc/roll/image/";
            sstore.setPic(pic);
            sstore.setPurchase_price(-1);
            sstore.setImage(imageid);
            sstore.setId(imageid);
            sstore.setOpenid(openid);
            User user= userDao.get(openid);
            if(user==null)
            {
                return 0;
            }
            sstore.setBusiness(user.getName());
            int res2=astoreDao.upall(sstore);
            if(res2==1)
            {
                //            添加商品图片仓库
                ImageS imageS=new ImageS();
                File file=new File(path);
                File[] list = file.listFiles();
                String rooturl="http://47.100.9.232:8080/hhsc/roll/image/";
                for(File file1:list)
                {
                    String url=rooturl+file1.getName();
                    if(imageS.getImg1()==null)
                    {
                        imageS.setImg1(url);
                    }
                    else if(imageS.getImg2()==null)
                    {
                        imageS.setImg2(url);
                    }
                    else if(imageS.getImg3()==null)
                    {
                        imageS.setImg3(url);
                    }
                    else if(imageS.getImg4()==null)
                    {
                        imageS.setImg4(url);
                    }
                    else if(imageS.getImg5()==null)
                    {
                        imageS.setImg5(url);
                    }
                    else
                    {
                        imageS.setImg6(url);
                    }
                }
                imageS.setId(imageid);
                int res=imageDao.adda(imageS);
                if(res==1)
                {
//                添加用户发布a级商品记录
                    if(wareHouseDao.geta()!=null)
                    {
                        int res6=wareHouseDao.dela();
                        if(res6!=1)
                        {
                            System.out.println(1);
                            return 0;
                        }
                    }
                    int res3=wareHouseDao.adda(warehouseid,imageid);
                    if(res3==1)
                    {
//                        删除key
                        String time=stringRedisTemplate.opsForValue().get(key);
                        Boolean res4 = stringRedisTemplate.delete(key);
                        try {
                            if(res4)
                            {
//                                添加时间限制
                                int day=Integer.parseInt(time);
                                stringRedisTemplate.opsForValue().set("roll", openid, day, TimeUnit.DAYS);
                                String start=stringRedisTemplate.opsForValue().get("roll");
                                if(start==null)
                                {
                                    System.out.println(2);
                                    return 0;
                                }
                                else
                                {
                                    return 1;
                                }
                            }
                            else
                            {
                                System.out.println(3);
                                return 0;
                            }
                        }catch (Exception e){
                            System.out.println(4);
                            return 0;
                        }
                    }
                    else
                    {
                        System.out.println(5);
                        return 0;
                    }
                }
                else
                {
                    System.out.println(6);
                    return 0;
                }
            }
            else
            {
                System.out.println(7);
                return 0;
            }
        }

        if(choice.equals("b"))
        {
            //        完善对象信息添加到a级商品库
            key=key.split(":")[1];
            String pic="http://47.100.9.232:8080/hhsc/slide/"+key+"/pic/"+key+".jpg";
            String path=rootpath+"hhsc/slide/"+key+"/image/";
            sstore.setPic(pic);
            sstore.setPurchase_price(-1);
            sstore.setImage(imageid);
            sstore.setId(imageid);
            sstore.setOpenid(openid);
            User user= userDao.get(openid);
            if(user==null)
            {
                return 0;
            }
            sstore.setBusiness(user.getName());
            int res2=bstoreDao.upb(sstore);
            if(res2==1)
            {
                //            添加商品图片仓库
                ImageB imageS=new ImageB();
                File file=new File(path);
                File[] list = file.listFiles();
                String rooturl="http://47.100.9.232:8080/hhsc/slide/"+key+"/image/";
                for(File file1:list)
                {
                    String url=rooturl+file1.getName();
                    if(imageS.getImg1()==null)
                    {
                        imageS.setImg1(url);
                    }
                    else if(imageS.getImg2()==null)
                    {
                        imageS.setImg2(url);
                    }
                    else if(imageS.getImg3()==null)
                    {
                        imageS.setImg3(url);
                    }
                    else if(imageS.getImg4()==null)
                    {
                        imageS.setImg4(url);
                    }
                    else if(imageS.getImg5()==null)
                    {
                        imageS.setImg5(url);
                    }
                    else
                    {
                        imageS.setImg6(url);
                    }
                }
                imageS.setId(imageid);
                imageS.setKeynm(key);
                int res=imageDao.addb(imageS);
                if(res==1)
                {
                    WareHouseB wareHouseB= wareHouseDao.getb(warehouseid);
                    if(wareHouseB==null)
                    {
                        wareHouseB=new WareHouseB();
                        wareHouseB.setId(warehouseid);
                        wareHouseB.setBid1(imageid);
                        int resv=wareHouseDao.addb(wareHouseB);
                        if(resv==1)
                        {
                            String time=stringRedisTemplate.opsForValue().get("bkey:"+key);
                            Long len=stringRedisTemplate.opsForList().size("slide");
                            if(len==null || len==0)
                            {
                                String v=key+":"+openid;
                                stringRedisTemplate.opsForList().leftPush("slide",v);
                                Long ex=stringRedisTemplate.opsForList().indexOf("slide",v);
                                if(ex==null || ex==-1)
                                {
                                    return 0;
                                }
                                else
                                {
                                    stringRedisTemplate.delete("bkey:"+key);
                                    v=v.split(":")[0];
                                    stringRedisTemplate.opsForValue().set("timeslide:"+v,"",Integer.parseInt(time),TimeUnit.DAYS);
                                    return 1;
                                }
                            }
                            else
                            {
                                String v=key+":"+openid;
                                stringRedisTemplate.opsForList().leftPush("slide",v);
                                Long ex=stringRedisTemplate.opsForList().indexOf("slide",v);
                                if(ex==null || ex==-1)
                                {
                                    return 0;
                                }
                                else
                                {
                                    stringRedisTemplate.delete("bkey:"+key);
                                    v=v.split(":")[0];
                                    stringRedisTemplate.opsForValue().set("timeslide:"+v,"",Integer.parseInt(time),TimeUnit.DAYS);
                                    return 1;
                                }
                            }
                        }
                        else
                        {
                            return 0;
                        }
                    }
                    else
                    {
                        if(wareHouseB.getBid1()==0)
                        {
                            wareHouseB.setBid1(imageid);
                        }
                        else
                        {
                            if(wareHouseB.getBid2()==0)
                            {
                                wareHouseB.setBid2(imageid);
                            }
                            else
                            {
                                if(wareHouseB.getBid3()==0)
                                {
                                    wareHouseB.setBid3(imageid);
                                }
                                else
                                {
                                    wareHouseB.setBid1(imageid);
                                }
                            }
                        }
                        int b=wareHouseDao.upb(wareHouseB);
                        if(b==1)
                        {
                            String time=stringRedisTemplate.opsForValue().get("bkey:"+key);
                            Long len=stringRedisTemplate.opsForList().size("slide");
                            if(len==null || len==0)
                            {
                                String v=key+":"+openid;
                                stringRedisTemplate.opsForList().leftPush("slide",v);
                                Long ex=stringRedisTemplate.opsForList().indexOf("slide",v);
                                if(ex==null || ex==-1)
                                {
                                    return 0;
                                }
                                else
                                {
                                    stringRedisTemplate.delete("bkey:"+key);
                                    v=v.split(":")[0];
                                    stringRedisTemplate.opsForValue().set("timeslide:"+v,"",Integer.parseInt(time),TimeUnit.DAYS);
                                    return 1;
                                }
                            }
                            else
                            {
                                String v=key+":"+openid;
                                stringRedisTemplate.opsForList().leftPush("slide",v);
                                Long ex=stringRedisTemplate.opsForList().indexOf("slide",v);
                                if(ex==null || ex==-1)
                                {
                                    return 0;
                                }
                                else
                                {
                                    stringRedisTemplate.delete("bkey:"+key);
                                    v=v.split(":")[0];
                                    stringRedisTemplate.opsForValue().set("timeslide:"+v,"",Integer.parseInt(time),TimeUnit.DAYS);
                                    return 1;
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
                    System.out.println(6);
                    return 0;
                }
            }
            else
            {
                System.out.println(7);
                return 0;
            }
        }

        else
        {
            System.out.println(8);
            return 0;
        }
    }
}
