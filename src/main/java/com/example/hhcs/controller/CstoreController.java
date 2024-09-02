package com.example.hhcs.controller;

import com.example.hhcs.dao.BrowseHistoryDao;
import com.example.hhcs.dao.CstoreDao;
import com.example.hhcs.dao.ImageDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.*;
import com.example.hhcs.util.ToBrowseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cstore")
public class CstoreController {
    @Autowired
    private BrowseHistoryDao browseHistoryDao;
    @Autowired
    private ToBrowseHistory browseHistory;
    @Autowired
    private CstoreDao cstoreDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;

//    获取图片
    @GetMapping("/image")
    private ImageC getimage(@RequestParam("id") String id)
    {
        ImageC imageC=imageDao.getimgc(id);
        if(imageC==null)
        {
            imageC=new ImageC();
            imageC.setImg1("http://47.100.9.232:8080/startimage.jpg");
            return imageC;
        }
        else
        {
            return imageC;
        }
    }




//    添加浏览记录
    @PostMapping("/history/{openid}/{id}")
    private void addhis(@PathVariable String openid,@PathVariable String id)
    {
        User user=userDao.get(openid);
        Cstore cstore=cstoreDao.getc(id);
        if(user!=null && cstore!=null)
        {
//            判断是否有该用户的浏览id
            BrowseHistory browseHistory1= browseHistoryDao.get(user.getWarehouse());
            if(browseHistory1==null)
            {
                browseHistory1=new BrowseHistory();
                browseHistory1.setC(id+"*");
                browseHistory1.setId(user.getWarehouse());
                browseHistoryDao.inithistory(browseHistory1);
            }
            else
            {
                String chis=browseHistory1.getC();
                if(chis==null || chis.equals(""))
                {
                    browseHistory1.setC(id+"*");
                    browseHistoryDao.updatehistory(browseHistory1);
                }
                else
                {
                    String[] x=browseHistory1.getC().split("[*]");
                    for(String i:x)
                    {
                        if(i.equals(id))
                        {
                            return;
                        }
                    }
                    String his=browseHistory1.getC()+id+"*";
                    browseHistory1.setC(his);
                    browseHistoryDao.updatehistory(browseHistory1);
                }
            }
        }
    }
}
