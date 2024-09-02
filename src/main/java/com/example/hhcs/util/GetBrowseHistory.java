package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import com.example.hhcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class GetBrowseHistory {
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private UserService userService;
    @Autowired
    private BrowseHistoryDao browseHistoryDao;
    @Autowired
    private SstoreDao sstoreDao;
    public Result get(String openid, String choice){
        User user= userService.get(openid);
        if(user==null)
        {
            return new Result(0,"");
        }
        int historyid= user.getWarehouse();
        BrowseHistory browseHistory= browseHistoryDao.get(historyid);
        if(browseHistory == null){
//            该用户没有浏览过商品,记录为空
            return new Result(0,"");
        }
        else {
            if(choice.equals("s")){
                String time=stringRedisTemplate.opsForValue().get("start");
                if(browseHistory.getS()==0 || time==null){
//                    该用户没有浏览过s商品,记录为空
                    return new Result(0,"");
                }
                List<Sstore> list=new ArrayList<>();
                Sstore sstore=sstoreDao.getbyid(browseHistory.getS());
                if(sstore!=null)
                {
                    list.add(sstore);
                    return new Result(1, Collections.singletonList(list));
                }
                else
                {
                    return new Result(0,"");
                }
            }
            if(choice.equals("a"))
            {
                String time=stringRedisTemplate.opsForValue().get("roll");
                if(browseHistory.getA()==0 || time==null){
//                    该用户没有浏览过a商品,记录为空
                    return new Result(0,"");
                }
                else
                {
                    List<Sstore> list=new ArrayList<>();
                    Sstore sstore=astoreDao.getnyid(browseHistory.getA());
                    if(sstore!=null)
                    {
                        list.add(sstore);
                        return new Result(1, Collections.singletonList(list));
                    }
                    else
                    {
                        return new Result(0,"");
                    }
                }
            }
            if(choice.equals("b"))
            {
                String b=browseHistory.getB();
                if(b==null || b.equals(""))
                {
                    return new Result(0,"");
                }
                else
                {
                    List<Bstore> list=new ArrayList<>();
                    String[] x=b.split("-");
                    for(String i:x)
                    {
//                        判断商品是否合理
                        Bstore bstore=bstoreDao.getbyid(Integer.parseInt(i));
                        if(bstore!=null)
                        {
                            String key="timeslide:"+bstore.getKeynm();
                            String y=stringRedisTemplate.opsForValue().get(key);
                            if(y!=null)
                            {
                                list.add(bstore);
                            }
                        }
                    }
                    return new Result(1, Collections.singletonList(list));
                }
            }
            if(choice.equals("c"))
            {
                String c=browseHistory.getC();
                if(c==null || c.equals(""))
                {
                    return new Result(0,"");
                }
                else
                {
                    List<Cstore> list=new LinkedList<>();
                    String[] x=c.split("[*]");
                    List<String> zan=new ArrayList<>();
                    StringBuilder stringBuilder=new StringBuilder();
                    if(x.length>30)
                    {
                        for(int i=x.length-1;i>=0;i--)
                        {
                            Cstore cstore=cstoreDao.getc(x[i]);
                            if(cstore!=null)
                            {
                                list.add(cstore);
                                zan.add(x[i]);
                                if(zan.size()==30)
                                {
                                    break;
                                }
                            }
                        }
                        for(int i= zan.size()-1;i>=0;i--)
                        {
                            stringBuilder.append(zan.get(i)).append("*");
                        }
                        browseHistory.setC(new String(stringBuilder));
                        browseHistoryDao.updatehistory(browseHistory);
                        return new Result(1,Collections.singletonList(list));
                    }
                    else
                    {
                        for(int i=x.length-1;i>=0;i--)
                        {
                            Cstore cstore=cstoreDao.getc(x[i]);
                            if(cstore!=null)
                            {
                                list.add(cstore);
                            }
                        }
                        return new Result(1,Collections.singletonList(list));
                    }
                }
            }
            else {
//                查询失败
                return new Result(0,"");
            }
        }
    }
}
