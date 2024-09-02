package com.example.hhcs.controller;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.*;
import com.example.hhcs.service.SstoreService;
import com.example.hhcs.service.impl.PurchaseHistoryServiveImpl;
import com.example.hhcs.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private FinishUtil finishUtil;
    @Autowired
    private GiveUpUtil giveUpUtil;
    @Autowired
    private PurchaseHistoryDao purchaseHistoryDao;
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private AstoreDao astoreDao;
    @Autowired
    private ZhanDao zhanDao;
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PurchaseC purchaseC;
    @Autowired
    private PurchaseB purchaseB;
    @Autowired
    private PurchaseA purchaseA;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SstoreService sstoreService;
    @Autowired
    private PurchaseHistoryServiveImpl purchaseHistoryServive;


//    C级商品求购记录
    @GetMapping("/purChis/{openid}")
    private Result getc(@PathVariable String openid)
    {
        return purchaseC.getc(openid);
    }


    //    C级商品确认求购
    @PostMapping("/purchaseC/{openid}/{goodid}")
    private int confirmC(@PathVariable String openid,@PathVariable String goodid)
    {
        return purchaseC.confirmA(openid,goodid);
    }


    //    C级商品的求购占有席位
    @GetMapping("/purchaseC/{openid}/{goodid}")
    private synchronized Result getpurchaseC(@PathVariable String openid,@PathVariable String goodid)
    {
        return purchaseC.getweizhi(openid,goodid);

    }


    //    B级商品确认求购
    @PostMapping("/purchaseB/{openid}/{goodid}")
    private int confirmB(@PathVariable String openid,@PathVariable int goodid)
    {
        return purchaseB.confirmA(openid,goodid);
    }


    //    B级商品的求购占有席位
    @GetMapping("/purchaseB/{openid}/{goodid}")
    private synchronized Result getpurchaseB(@PathVariable String openid,@PathVariable int goodid)
    {
        return purchaseB.getweizhi(openid,goodid);
    }

//    B级商品求购记录
    @GetMapping("/purBhis/{openid}")
    private Result getb(@PathVariable String openid)
    {
        return purchaseB.getb(openid);
    }

    //    A级商品的求购占有席位
    @GetMapping("/purchaseA/{openid}")
    private synchronized Result getpurchaseA(@PathVariable String openid)
    {
        return purchaseA.getweizhi(openid);
    }

    //    A级商品确认求购
    @PostMapping("/purchaseA/{openid}")
    private int confirmA(@PathVariable String openid)
    {
        return purchaseA.confirmA(openid);
    }

//    A级商品求购记录
    @GetMapping("/purAhis/{openid}")
    private Result geta(@PathVariable String openid)
    {
        return purchaseA.geta(openid);
    }

//    S级商品的求购占有席位
    @GetMapping("/purchaseS/{openid}")
    private synchronized Result getpurchaseS(@PathVariable String openid){
        String a=stringRedisTemplate.opsForValue().get("start");
        User user= userDao.get(openid);
        if(a==null || user==null)
        {
            return new Result(0,"");
        }
        String storeid=sstoreService.getopenid();
        if(storeid.equals(openid))
        {
            //卖家无法购买自己的商品
            return new Result(3,"");
        }
        else
        {
            int status= sstoreService.getstatus();
            if(status==0)
            {
//            该商品不可求购
                return new Result(0,"");
            }
            else
            {
                String people=stringRedisTemplate.opsForValue().get("Sstore:flag");
                if(people==null)
                {
//                可以求购
                    double price=sstoreService.getpurchase();
                    stringRedisTemplate.opsForValue().set("Sstore:flag","1",30, TimeUnit.SECONDS);
                    stringRedisTemplate.opsForValue().set("Sstore:openid",openid,30, TimeUnit.SECONDS);
                    return new Result(1,String.valueOf(price+1));
                }
                else if(people.equals("0")){
//                可以求购
                    double price=sstoreService.getpurchase();
                    stringRedisTemplate.opsForValue().set("Sstore:flag","1",30, TimeUnit.SECONDS);
                    stringRedisTemplate.opsForValue().set("Sstore:openid",openid,30, TimeUnit.SECONDS);
                    return new Result(1,String.valueOf(price+1));
                }
                else
                {
                    String opid=stringRedisTemplate.opsForValue().get("Sstore:openid");
                    if(openid.equals(opid))
                    {
//                    可以求购
                        double price=sstoreService.getpurchase();
                        return new Result(1,String.valueOf(price+1));
                    }
                    else
                    {
//                    不可以求购
                        return new Result(2,"");
                    }
                }
            }
        }
    }

//    S级商品确认求购
    @PostMapping("/purchaseS/{openid}")
    private int confirmS(@PathVariable String openid){
        String people=stringRedisTemplate.opsForValue().get("Sstore:openid");
        if(people==null)
        {
//            求购席位超市，重新求购
            return 0;
        }
        else if(!people.equals(openid))
        {
//            求购席位超市，重新求购
            return 0;
        }
        else
        {
//            可以求购，修改求购价格
            double price=sstoreService.getpurchase();
            int res=sstoreService.updatepur(price+1,openid);
            if(res==3)
            {
//                求购成功,更新求购价以及求购者,添加到求购历史，删除求购参标量
                Sstore sstore=sstoreService.get();
                String seller=sstore.getOpenid();
                if(seller!=null && !seller.equals(""))
                {
                    User user= userDao.get(seller);
                    if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                    {
                        User buyer=userDao.get(openid);
                        if(buyer!=null && buyer.getName()!=null && !buyer.getName().equals(""))
                        {
                            StringBuilder stringBuilder=new StringBuilder();
                            stringBuilder.append("你的商品")
                                    .append("【")
                                    .append(sstore.getName())
                                    .append("】")
                                    .append("得到了用户")
                                    .append("【")
                                    .append(buyer.getName())
                                    .append("】")
                                    .append("的求购,求购价为")
                                    .append("【")
                                    .append(price+1)
                                    .append("】")
                                    .append("元,你可以上线选择结束求购或者继续等待");
                            MailUtils.sendMail(user.getEmail(),new String(stringBuilder),"【黄淮市场】你的商品得到了求购");
                        }
                    }
                }
                return 1;
            }
            else
            {
//                求购失败，请重试
                return 2;
            }
        }
    }


//    获取s商品求购记录
    @GetMapping("/purShis/{openid}")
    private Result gets(@PathVariable String openid)
    {
        System.out.println(openid);
        PurchaseHistory purchaseHistory=purchaseHistoryDao.gethis(openid);
        if(purchaseHistory==null || purchaseHistory.getS()==0)
        {
            return new Result(0,"");
        }
        else
        {
            int sid=purchaseHistory.getS();
            String nowopenid=stringRedisTemplate.opsForValue().get("start");
            if(nowopenid==null)
            {
                return new Result(0,"");
            }
            else
            {
                Sstore sstore= sstoreDao.get();
                if(sstore!=null && sstore.getId()==sid && sstore.getPurchase_people()!=null && sstore.getPurchase_people().equals(openid))
                {
                    List<Sstore> ing=new ArrayList<>();
                    List<Sstore> end=new ArrayList<>();
                    if(sstore.getStatus()==0)
                    {
//                        交易中
                        end.add(sstore);
                    }
                    else
                    {
                        ing.add(sstore);
                    }
                    return new Result(1,Collections.singletonList(ing),Collections.singletonList(end));
                }
                else
                {
                    return new Result(0,"");
                }
            }
        }
    }


//    放弃求购
    @DeleteMapping("/{goodid}/{openid}/{rate}")
    public int del(@PathVariable String goodid,@PathVariable String openid,@RequestParam("name") String name,@PathVariable String rate)
    {
        return giveUpUtil.giveup(goodid,openid,name,rate);
    }


//    确认交易完成
    @PostMapping("/finish/{rate}/{goodid}/{openid}")
    private int finish(@PathVariable String rate,@PathVariable String goodid,@PathVariable String openid)
    {
        return finishUtil.finish(rate,goodid,openid);
    }
}
