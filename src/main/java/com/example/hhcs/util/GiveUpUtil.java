package com.example.hhcs.util;

import com.example.hhcs.dao.*;
import com.example.hhcs.domain.PurchaseHistory;
import com.example.hhcs.domain.User;
import com.example.hhcs.domain.Zhan;
import com.example.hhcs.service.SstoreService;
import com.example.hhcs.service.impl.PurchaseHistoryServiveImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class GiveUpUtil {
    @Autowired
    private ImportMail importMail;
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


    @Transactional
    public int giveup(String goodid, String openid,  String name, String rate)
    {
        Zhan zhan= zhanDao.get(goodid);
        User user= userDao.get(openid);
        if(zhan==null || zhan.getHistory()==null || zhan.getHistory().equals("") || user==null)
        {
            return 0;
        }
        else
        {
            String[] x=zhan.getHistory().split("[*]");
            int index=-1;
            String nowopnied="";
            double price1=-1;
            for(int i=0;i<x.length;i++)
            {
                double pri= Double.parseDouble(x[i].split("[+]")[1]);
                if(pri>price1)
                {
                    price1=Double.parseDouble(x[i].split("[+]")[1]);
                    nowopnied=x[i].split("[+]")[0];
                    index=i;
                }
            }
            if(!nowopnied.equals(openid))
            {
                return 0;
            }
            else
            {
                StringBuilder stringBuilder=new StringBuilder();
                String next="";
                double price=-1;
                for(int i=0;i<x.length;i++)
                {
                    if(i!=index)
                    {
                        stringBuilder.append(x[i]).append("*");
                        if(Double.parseDouble(x[i].split("[+]")[1])>price)
                        {
                            next=x[i].split("[+]")[0];
                            price=Double.parseDouble(x[i].split("[+]")[1]);
                        }
                    }
                }
//                更新该商品的求购数据
                if(rate.equals("s"))
                {
                    if(price==-1)
                    {
                        int res=sstoreDao.uplast("",-1,Integer.parseInt(goodid.substring(1)));
                        int res3= zhanDao.update(goodid,"");
                        if(res+res3==2)
                        {
                            if(user.getName()!=null)
                            {
                                importMail.reallygiveup("s",goodid.substring(1),name,user.getName());
                            }
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                    else
                    {
                        PurchaseHistory purchaseHistory=purchaseHistoryDao.gethis(next);
                        if(purchaseHistory==null)
                        {
                            int res=sstoreDao.uplast("",-1,Integer.parseInt(goodid.substring(1)));
                            int res3= zhanDao.update(goodid,"");
                            if(res+res3==2)
                            {
                                if(user.getName()!=null)
                                {
                                    importMail.reallygiveup("s",goodid.substring(1),name,user.getName());
                                }
                                return 1;
                            }
                            else
                            {
                                System.out.println(3);
                                return 0;
                            }
                        }
                        else
                        {
                            int ress=purchaseHistoryDao.ups(Integer.parseInt(goodid.substring(1)),next);
                            int res=sstoreDao.uplast(next,price,Integer.parseInt(goodid.substring(1)));
                            int res2= zhanDao.update(goodid,new String(stringBuilder));
                            if(ress+res+res2==3)
                            {
                                User user1=userDao.get(next);
                                if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                {
                                    importMail.giveup("s",goodid.substring(1),name,user.getName(),user1.getName(),price,next);
                                }
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                    }
                }

                else if(rate.equals("a"))
                {
                    if(price==-1)
                    {
                        int res=astoreDao.uplast(-1,"",Integer.parseInt(goodid.substring(1)));
                        int res3= zhanDao.update(goodid,"");
                        if(res+res3==2)
                        {
                            if(user.getName()!=null)
                            {
                                importMail.reallygiveup("a",goodid.substring(1),name,user.getName());
                            }
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                    else
                    {
                        PurchaseHistory purchaseHistory=purchaseHistoryDao.gethis(next);
                        if(purchaseHistory==null)
                        {
                            int res=astoreDao.uplast(-1,"",Integer.parseInt(goodid.substring(1)));
                            int res3= zhanDao.update(goodid,"");
                            if(res+res3==2)
                            {
                                if(user.getName()!=null)
                                {
                                    importMail.reallygiveup("a",goodid.substring(1),name,user.getName());
                                }
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                        else
                        {
                            int res=userDao.upAhis(Integer.parseInt(goodid.substring(1)),next);
                            int res1=astoreDao.uplast(price,next,Integer.parseInt(goodid.substring(1)));
                            int res2= zhanDao.update(goodid,new String(stringBuilder));
                            if(res+res1+res2==3)
                            {
                                User user1=userDao.get(next);
                                if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                {
                                    importMail.giveup("a",goodid.substring(1),name,user.getName(),user1.getName(),price,next);
                                }
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                    }
                }

                else if(rate.equals("b"))
                {
                    if(price==-1)
                    {
                        int res=bstoreDao.uppur(-1,"",Integer.parseInt(goodid.substring(1)));
                        int res3= zhanDao.update(goodid,"");
                        if(res+res3==2)
                        {
                            if(user.getName()!=null)
                            {
                                importMail.reallygiveup("b",goodid.substring(1),name,user.getName());
                            }
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                    else
                    {
                        PurchaseHistory purchaseHistory=purchaseHistoryDao.gethis(next);
                        if(purchaseHistory==null)
                        {
                            int res=bstoreDao.uppur(-1,"",Integer.parseInt(goodid.substring(1)));
                            int res3= zhanDao.update(goodid,"");
                            if(res+res3==2)
                            {
                                if(user.getName()!=null)
                                {
                                    importMail.reallygiveup("b",goodid.substring(1),name,user.getName());
                                }
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                        else
                        {
                            String bhis=purchaseHistory.getB();
                            if(bhis==null || bhis.equals(""))
                            {
                                String id=goodid.substring(1)+"-";
                                int res=purchaseHistoryDao.upb(id,next);
                                int res1=bstoreDao.uppur(price,next,Integer.parseInt(goodid.substring(1)));
                                int res2= zhanDao.update(goodid,new String(stringBuilder));
                                if(res+res1+res2==3)
                                {
                                    User user1=userDao.get(next);
                                    if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                    {
                                        importMail.giveup("b",goodid.substring(1),name,user.getName(),user1.getName(),price,next);
                                    }
                                    return 1;
                                }
                                else
                                {
                                    return 0;
                                }
                            }
                            else
                            {
                                String id=goodid.substring(1);
                                String[] shuzu=bhis.split("-");
                                int flag=0;
                                for(int m=0;m<shuzu.length;m++)
                                {
                                    if(shuzu[m].equals(id))
                                    {
                                        flag=1;
                                        break;
                                    }
                                }
                                if(flag==0)
                                {
                                    StringBuilder stringBuilder1=new StringBuilder();
                                    stringBuilder1.append(bhis).append(id).append("-");
                                    int res=purchaseHistoryDao.upb(new String(stringBuilder1),next);
                                    int res1=bstoreDao.uppur(price,next,Integer.parseInt(goodid.substring(1)));
                                    int res2= zhanDao.update(goodid,new String(stringBuilder));
                                    if(res+res1+res2==3)
                                    {
                                        User user1=userDao.get(next);
                                        if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                        {
                                            importMail.giveup("b",goodid.substring(1),name,user.getName(),user1.getName(),price,next);
                                        }
                                        return 1;
                                    }
                                    else
                                    {
                                        return 0;
                                    }
                                }
                                else
                                {
                                    int res1=bstoreDao.uppur(price,next,Integer.parseInt(goodid.substring(1)));
                                    int res2= zhanDao.update(goodid,new String(stringBuilder));
                                    if(res1+res2==2)
                                    {
                                        User user1=userDao.get(next);
                                        if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                        {
                                            importMail.giveup("b",goodid.substring(1),name,user.getName(),user1.getName(),price,next);
                                        }
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

                else if(rate.equals("c"))
                {
                    if(price==-1)
                    {
                        int res=cstoreDao.uppur(-1,"",goodid);
                        int res3= zhanDao.update(goodid,"");
                        if(res+res3==2)
                        {
                            if(user.getName()!=null)
                            {
                                importMail.reallygiveup("c",goodid,name,user.getName());
                            }
                            return 1;
                        }
                        else
                        {
                            return 0;
                        }
                    }
                    else
                    {
                        PurchaseHistory purchaseHistory=purchaseHistoryDao.gethis(next);
                        if(purchaseHistory==null)
                        {

                            int res=cstoreDao.uppur(-1,"",goodid);
                            int res3= zhanDao.update(goodid,"");
                            if(res+res3==2)
                            {
                                if(user.getName()!=null)
                                {
                                    importMail.reallygiveup("c",goodid,name,user.getName());
                                }
                                return 1;
                            }
                            else
                            {
                                return 0;
                            }
                        }
                        else
                        {
                            String bhis=purchaseHistory.getC();
                            if(bhis==null || bhis.equals(""))
                            {
                                String id=goodid+"*";
                                int res=purchaseHistoryDao.upc(id,next);
                                int res1=cstoreDao.uppur(price,next,goodid);
                                int res2= zhanDao.update(goodid,new String(stringBuilder));
                                if(res+res1+res2==3)
                                {
                                    User user1=userDao.get(next);
                                    if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                    {
                                        importMail.giveup("c",goodid,name,user.getName(),user1.getName(),price,next);
                                    }
                                    return 1;
                                }
                                else
                                {
                                    return 0;
                                }
                            }
                            else
                            {
                                String[] shuzu=bhis.split("[*]");
                                int flag=0;
                                for(int m=0;m<shuzu.length;m++)
                                {
                                    if(shuzu[m].equals(goodid))
                                    {
                                        flag=1;
                                        break;
                                    }
                                }
                                if(flag==0)
                                {
                                    StringBuilder stringBuilder1=new StringBuilder();
                                    stringBuilder1.append(bhis).append(goodid).append("*");
                                    int res=purchaseHistoryDao.upc(new String(stringBuilder1),next);
                                    int res1=cstoreDao.uppur(price,next,goodid);
                                    int res2= zhanDao.update(goodid,new String(stringBuilder));
                                    if(res+res1+res2==3)
                                    {
                                        User user1=userDao.get(next);
                                        if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                        {
                                            importMail.giveup("c",goodid,name,user.getName(),user1.getName(),price,next);
                                        }
                                        return 1;
                                    }
                                    else
                                    {
                                        return 0;
                                    }
                                }
                                else
                                {
                                    int res1=cstoreDao.uppur(price,next,goodid);
                                    int res2= zhanDao.update(goodid,new String(stringBuilder));
                                    if(res1+res2==2)
                                    {
                                        User user1=userDao.get(next);
                                        if(user1!=null && user1.getName()!=null && user.getName()!=null)
                                        {
                                            importMail.giveup("c",goodid,name,user.getName(),user1.getName(),price,next);
                                        }
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
                else
                {
                    return 0;
                }
            }
        }
    }
}
