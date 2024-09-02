package com.example.hhcs.service.impl;

import com.example.hhcs.dao.ImageDao;
import com.example.hhcs.domain.ImageS;
import com.example.hhcs.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
//    s级商品图片获取
    public ImageS get(int id) {
        String start=stringRedisTemplate.opsForValue().get("start");
        if(start==null)
        {
            ImageS imageS=new ImageS();
            imageS.setImg1("http://47.100.9.232:8080/startimage2.jpg");
            return imageS;
        }
        else
        {
            return imageDao.get();
        }
    }

    @Override
    public ImageS getaimage() {
        String roll=stringRedisTemplate.opsForValue().get("roll");
        if(roll==null)
        {
            ImageS imageS=new ImageS();
            imageS.setImg1("http://47.100.9.232:8080/startimage2.jpg");
            return imageS;
        }
        else
        {
            ImageS imageS=imageDao.getaimage();
            if(imageS==null)
            {
                imageS=new ImageS();
                imageS.setImg1("http://47.100.9.232:8080/startimage2.jpg");
                return imageS;
            }
            else
            {
                return imageS;
            }
        }
    }
}
