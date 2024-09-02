package com.example.hhcs.controller;

import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.Sstore;
import com.example.hhcs.util.GetTwoId;
import com.example.hhcs.util.UpLoadImage;
import com.example.hhcs.util.UpMessage;
import com.example.hhcs.util.UploadPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
@RestController
@RequestMapping("/upload")
public class UploadstoreController {
    @Autowired
    private GetTwoId getTwoId;
    @Autowired
    private UploadPic uploadPic;
    @Autowired
    private UpLoadImage upLoadImage;
    @Autowired
    private UpMessage upMessage;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
//    获取用户的仓库id和图片的仓库id
    @GetMapping("/{openid}/{choice}/{key}")
    private Result gettwoid(@PathVariable String openid,@PathVariable String choice,@PathVariable String key)
    {
        if(choice.equals("s"))
        {
            String time=stringRedisTemplate.opsForValue().get("key:"+key);
            if(time==null)
            {
                return new Result(0,"");
            }
            else
            {
                return getTwoId.gettwoid(openid,choice,key);
            }
        }



        if(choice.equals("a"))
        {
            String time=stringRedisTemplate.opsForValue().get("akey:"+key);
            if(time==null)
            {
                return new Result(0,"");
            }
            else
            {
                return getTwoId.gettwoid(openid,choice,key);
            }
        }

        if(choice.equals("b"))
        {
            String time=stringRedisTemplate.opsForValue().get("bkey:"+key);
            if(time==null)
            {
                return new Result(0,"");
            }
            else
            {
                return getTwoId.gettwoid(openid,choice,key);
            }
        }

        if(choice.equals("c"))
        {
            return getTwoId.gettwoid(openid,choice,key);
        }


        else
        {
            return new Result(0,"");
        }
    }



//    上传封面
    @PostMapping("/pic/{choice}/{openid}/{key}")
    private int uppic(@RequestParam("Pic") MultipartFile Pic,@PathVariable String choice,@PathVariable String openid,@PathVariable String key,@RequestParam("warehouseid") int warehouseid,@RequestParam("imageid") int imageid,@RequestParam("simageid") String simageid)
    {
        if(choice.equals("s"))
        {
            String time=stringRedisTemplate.opsForValue().get("key:"+key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return uploadPic.addpic(choice,Pic,openid,key,warehouseid,imageid,simageid);
            }
        }


        if(choice.equals("a"))
        {
            String time=stringRedisTemplate.opsForValue().get("akey:"+key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return uploadPic.addpic(choice,Pic,openid,key,warehouseid,imageid,simageid);
            }
        }

        if(choice.equals("b"))
        {
            String time=stringRedisTemplate.opsForValue().get("bkey:"+key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return uploadPic.addpic(choice,Pic,openid,key,warehouseid,imageid,simageid);
            }
        }

        if(choice.equals("c"))
        {
            return uploadPic.addpic(choice,Pic,openid,key,warehouseid,imageid,simageid);
        }


        else
        {
            return 0;
        }
    }





//    上传图片
    @PostMapping("/image/{choice}/{openid}/{key}")
    private int upimage(@RequestParam("image") MultipartFile image,@PathVariable String choice,@PathVariable String openid,@PathVariable String key,@RequestParam("warehouseid") int warehouseid,@RequestParam("imageid") int imageid,@RequestParam("simageid") String simageid)
    {
        if(choice.equals("s"))
        {
            String time=stringRedisTemplate.opsForValue().get("key:"+key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return upLoadImage.addimage(openid,choice,image,key,warehouseid,imageid,simageid);
            }
        }

        if(choice.equals("a"))
        {
            String time=stringRedisTemplate.opsForValue().get("akey:"+key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return upLoadImage.addimage(openid,choice,image,key,warehouseid,imageid,simageid);
            }
        }

        if(choice.equals("b"))
        {
            String time=stringRedisTemplate.opsForValue().get("bkey:"+key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return upLoadImage.addimage(openid,choice,image,key,warehouseid,imageid,simageid);
            }
        }

        if(choice.equals("c"))
        {
            return upLoadImage.addimage(openid,choice,image,key,warehouseid,imageid,simageid);
        }


        else
        {
            return 0;
        }
    }


//    上传文字信息
    @PostMapping("/message/{choice}/{warehouseid}/{key}/{openid}")
    private int upmessage(@RequestBody Sstore sstore,@PathVariable String choice,@PathVariable String openid,@PathVariable int warehouseid,@PathVariable String key,@RequestParam("imageid") int imageid,@RequestParam("simageid") String simageid)
    {
        if(choice.equals("s"))
        {
            key="key:"+key;
            String time=stringRedisTemplate.opsForValue().get(key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return upMessage.addmessage(sstore,choice, imageid,warehouseid,key,openid,simageid);
            }
        }


        if(choice.equals("a"))
        {
            key="akey:"+key;
            String time=stringRedisTemplate.opsForValue().get(key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return upMessage.addmessage(sstore,choice, imageid,warehouseid,key,openid,simageid);
            }
        }

        if(choice.equals("b"))
        {
            key="bkey:"+key;
            String time=stringRedisTemplate.opsForValue().get(key);
            if(time==null)
            {
                return 0;
            }
            else
            {
                return upMessage.addmessage(sstore,choice, imageid,warehouseid,key,openid,simageid);
            }
        }

        if(choice.equals("c"))
        {
            return upMessage.addmessage(sstore,choice,1,warehouseid,key,openid,simageid);
        }

        else
        {
            return 0;
        }
    }

}
