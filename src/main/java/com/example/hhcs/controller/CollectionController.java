package com.example.hhcs.controller;

import com.example.hhcs.dao.CollectionDao;
import com.example.hhcs.dao.CstoreDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.LikeGoods;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.soap.Addressing;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectionController {
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CstoreDao cstoreDao;
    @PostMapping("/{openid}/{id}")
    private int tocollection(@PathVariable String id, @PathVariable String openid)
    {
        User user= userDao.get(openid);
        Cstore cstore = cstoreDao.getc(id);
        if(user==null || cstore==null)
        {
//            收藏失败
            return 0;
        }
        else
        {
//            判断是否要初始化收藏夹
            LikeGoods likeGoods= collectionDao.get(user.getWarehouse());
            if(likeGoods==null)
            {
                likeGoods=new LikeGoods();
                likeGoods.setLikeid(id+"*");
                likeGoods.setId(user.getWarehouse());
                int res=collectionDao.add(likeGoods);
                if(res==1)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
            else
            {
                String likestring= likeGoods.getLikeid();
                if(likestring==null || likestring.equals(""))
                {
                    likeGoods.setLikeid(id+"*");
                    int res=collectionDao.up(likeGoods);
                    if(res==1)
                    {
                        return 1;
                    }
                    else
                    {
                        return 0;
                    }
                }
                else
                {
                    String[] x=likestring.split("[*]");
                    if(x.length>30)
                    {
                        return 2;
                    }
                    else
                    {
                        for(String i:x)
                        {
                            if(i.equals(id))
                            {
                                return 4;
                            }
                        }
                        likestring=likestring+id+"*";
                        likeGoods.setLikeid(likestring);
                        int res=collectionDao.up(likeGoods);
                        if(res==1)
                        {
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                }
            }
        }
    }


    @GetMapping("/{openid}")
    private Result get(@PathVariable String openid)
    {
        User user= userDao.get(openid);
        if(user==null)
        {
            return new Result(0,"");
        }
        else
        {
            LikeGoods likeGoods= collectionDao.get(user.getWarehouse());
            if(likeGoods==null)
            {
                return new Result(0,"");
            }
            else
            {
                String x= likeGoods.getLikeid();
                if(x==null || x.equals(""))
                {
                    return new Result(0,"");
                }
                else
                {
                    List<Cstore> list=new LinkedList<>();
                    String[] shuzu=x.split("[*]");
                    int len=shuzu.length;
                    int num=0;
                    for(String i:shuzu)
                    {
                        Cstore cstore= cstoreDao.getc(i);
                        if(cstore!=null)
                        {
                            num=num+1;
                            list.add(cstore);
                        }
                    }
                    if(num!=len)
                    {
                        StringBuilder stringBuilder=new StringBuilder();
                        for(Cstore cstore:list)
                        {
                            stringBuilder.append(cstore.getOpenid()).append("*");
                        }
                        likeGoods.setLikeid(new String(stringBuilder));
                        collectionDao.up(likeGoods);
                    }
                    return new Result(1, Collections.singletonList(list));
                }
            }
        }
    }


    @DeleteMapping("/{openid}/{id}")
    private int delete(@PathVariable String openid,@PathVariable String id)
    {
        User user= userDao.get(openid);
        Cstore cstore= cstoreDao.getc(id);
        if(user==null || cstore==null)
        {

            return 0;
        }
        else
        {
            LikeGoods likeGoods= collectionDao.get(user.getWarehouse());
            if(likeGoods!=null)
            {
                String x= likeGoods.getLikeid();
                if(x!=null && !x.equals(""))
                {
                    String[] shuzu=x.split("[*]");
                    StringBuilder stringBuilder=new StringBuilder();
                    for(String i:shuzu)
                    {
                        if(!i.equals(id))
                        {
                            stringBuilder.append(i).append("*");
                        }
                    }
                    likeGoods.setLikeid(new String(stringBuilder));
                    int res=collectionDao.up(likeGoods);
                    if(res==1)
                    {
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

                return 0;
            }
        }
    }
}
