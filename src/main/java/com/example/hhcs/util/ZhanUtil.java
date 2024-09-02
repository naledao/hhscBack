package com.example.hhcs.util;

import com.example.hhcs.dao.SstoreDao;
import com.example.hhcs.dao.ZhanDao;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.domain.Zhan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZhanUtil {
    @Autowired
    private SstoreDao sstoreDao;
    @Autowired
    private ZhanDao zhanDao;
    public void zhan(String rate,String goodid,String openid,double price)
    {
            Zhan zhan=zhanDao.get(goodid);
            if(zhan==null)
            {
                zhan=new Zhan();
                zhan.setId(goodid);
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(openid).append("+").append(price).append("*");
                zhan.setHistory(new String(stringBuilder));
                zhanDao.init(zhan);
            }
            else
            {
                if(zhan.getHistory()==null || zhan.getHistory().equals(""))
                {
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append(openid).append("+").append(price).append("*");
                    zhan.setHistory(new String(stringBuilder));
                    zhanDao.update(zhan.getId(), new String(stringBuilder));
                }
                else
                {
                    String[] x=zhan.getHistory().split("[*]");
                    int flag=0;
                    for(int i=0;i<x.length;i++)
                    {
                        String nowopenid=x[i].substring(0,x[i].indexOf("+"));
                        if(nowopenid.equals(openid))
                        {

                            String[] zan=x[i].split("[+]");
                            zan[1]=String.valueOf(price);
                            String zui=zan[0]+"+"+zan[1];
                            x[i]=zui;
                            flag=1;
                            break;
                        }
                    }
                    if(flag==0)
                    {
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder.append(zhan.getHistory()).append(openid).append("+").append(price).append("*");
                        zhanDao.update(zhan.getId(),new String(stringBuilder));
                    }
                    if(flag==1)
                    {
                        StringBuilder stringBuilder=new StringBuilder();
                        for(String i:x)
                        {
                            stringBuilder.append(i).append("*");
                        }
                        zhanDao.update(zhan.getId(),new String(stringBuilder));
                    }
                }
            }
    }
}
