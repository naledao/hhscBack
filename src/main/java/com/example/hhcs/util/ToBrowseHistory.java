package com.example.hhcs.util;

import com.example.hhcs.dao.BrowseHistoryDao;
import com.example.hhcs.dao.BstoreDao;
import com.example.hhcs.domain.BrowseHistory;
import com.example.hhcs.domain.Bstore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToBrowseHistory {
    @Autowired
    private BstoreDao bstoreDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BrowseHistoryDao browseHistoryDao;
    public void addhistory(String option,int id,int history){
        BrowseHistory browseHistory= browseHistoryDao.get(id);
        if(browseHistory==null){
//            添加id，添加浏览记录
            BrowseHistory browseHistory1=new BrowseHistory();
            browseHistory1.setId(id);
            if(option.equals("s")){
                browseHistory1.setS(history);
            }
            if(option.equals("a")){
                browseHistory1.setA(history);
            }
            if(option.equals("b")){
                browseHistory1.setB(history+"-");
            }
            browseHistoryDao.inithistory(browseHistory1);
        }


        else {
            if(option.equals("s")){
                browseHistory.setS(history);
                browseHistoryDao.updatehistory(browseHistory);
            }
            if(option.equals("a")){
                browseHistory.setA(history);
                browseHistoryDao.updatehistory(browseHistory);
            }
            if(option.equals("b")){
                String bhis=browseHistory.getB();
                if(bhis==null || bhis.equals(""))
                {
                    browseHistory.setB(history+"-");
                    browseHistoryDao.updatehistory(browseHistory);
                }
                else
                {
                    String[] x=bhis.split("-");
                    for(String m:x)
                    {
                        if(m.equals(String.valueOf(history)))
                        {
                            return;
                        }
                    }
                    if(x.length>=3)
                    {
                        for(int i=0;i< x.length;i++)
                        {
                            Bstore bstore =bstoreDao.getbyid(Integer.parseInt(x[i]));
                            if(bstore==null)
                            {
                                x[i]=String.valueOf(history);
                                break;
                            }
                            else
                            {
                                if(stringRedisTemplate.opsForValue().get("timeslide:"+bstore.getKeynm())==null)
                                {
                                    x[i]=String.valueOf(history);
                                    break;
                                }
                            }
                        }
                        StringBuilder stringBuilder=new StringBuilder();
                        for(String m:x)
                        {
                            stringBuilder.append(m).append("-");
                        }
                        browseHistory.setB(new String(stringBuilder));
                        browseHistoryDao.updatehistory(browseHistory);
                    }
                    else
                    {
                        StringBuilder stringBuilder=new StringBuilder();
                        for(String i:x)
                        {
                            stringBuilder.append(i).append("-");
                        }
                        stringBuilder.append(history).append("-");
                        browseHistory.setB(new String(stringBuilder));
                        browseHistoryDao.updatehistory(browseHistory);
                    }
                }
            }
        }
    }
}
