package com.example.hhcs.controller;

import com.example.hhcs.dao.UserDao;
import com.example.hhcs.dao.WareHouseDao;
import com.example.hhcs.domain.*;
import com.example.hhcs.service.UserService;
import com.example.hhcs.util.*;
import jakarta.servlet.http.HttpServletRequest;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private EndStore endStore;
    @Autowired
    private DelUserGood delUserGood;
    @Autowired
    private GetAllMineGoods getAllMineGoods;
    @Autowired
    private WareHouseDao wareHouseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WxOpenid wxOpenid;
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("{id}")
    private User get(@PathVariable String id){
        return userService.get(id);
    }


    @GetMapping ("/login")
    private Result login(@Param("key") String key)
    {
        String openid=stringRedisTemplate.opsForValue().get("user:"+key);
        if(openid==null)
        {
//            登录密匙无效，返回0，删除客户端的key
            return new Result(0,"");
        }
        else
        {
            return new Result(1,openid);
        }
    }

//    用户立即登录
    @PostMapping("/login")
    private Result loginnow(@RequestBody UserMessage userMessage){
//        调用微信接口获取用户id以及key
        Result result=wxOpenid.getcode(userMessage.getCode());
        if(result.getCode()==0){
//            获取openid和key成功
//            将用户信息存储到数据库中
            String id=stringRedisTemplate.opsForValue().get(result.getWxReturn().getSession_key());
            if(id==null){
                User user1= userService.get(result.getWxReturn().getOpenid());
                if(user1==null)
                {
//                    用户为新用户
                    stringRedisTemplate.opsForValue().set("user:" + result.getWxReturn().getSession_key(), result.getWxReturn().getOpenid());
                    User user=new User();
                    user.setOpenid(result.getWxReturn().getOpenid());
                    user.setHead(userMessage.getHead());
                    user.setName(userMessage.getName());
                    int s=userService.adduser(user);
                    if(s==1){
                        return new Result(0,result.getWxReturn(),user);
                    }
                    else{
                        return new Result(1,"");
                    }
                }
                else
                {
                    User user=new User();
                    user.setOpenid(result.getWxReturn().getOpenid());
                    user.setHead(userMessage.getHead());
                    user.setName(userMessage.getName());
                    stringRedisTemplate.opsForValue().set("user:" + result.getWxReturn().getSession_key(), result.getWxReturn().getOpenid());
                    return new Result(0,result.getWxReturn(),user);
                }
            }
            else {
                return new Result(1,"");
            }
        }
        else
        {
//            登录失败
            return new Result(1,"");
        }
    }

    @PostMapping("/loginout")
    private void loginout(@RequestParam("key") String key){
        stringRedisTemplate.delete("user:"+key);
    }


//   获取用户发布的商品
    @GetMapping("/getallminegoods/{openid}/{rate}")
    private LastResult getall(@PathVariable String openid, @PathVariable String rate){
        User user= userDao.get(openid);
        if(user==null || user.getWarehouse()==0)
        {
            return new LastResult(0,"");
        }
        else
        {
            return getAllMineGoods.get(rate,user.getWarehouse(),openid);
        }
    }


//    删除用户发布的商品
    @DeleteMapping("/{rate}/{goodid}/{openid}")
    private int del(@PathVariable String rate,@PathVariable String goodid,@PathVariable String openid)
    {
        return delUserGood.del(rate,goodid,openid);
    }

//    终止求购
    @PostMapping("/end/{rate}/{goodid}/{openid}/{purid}")
    private int end(@PathVariable String rate,@PathVariable String goodid,@PathVariable String openid,@PathVariable String purid)
    {
        return endStore.end(rate,goodid,openid,purid);
    }



//    获取用户头像和名称
    @GetMapping("/getmessage")
    private Result getmessage(@Param("openid") String openid)
    {
        if(openid==null)
        {
            return new Result(0,"");
        }
        else
        {
            User user= userDao.get(openid);
            if(user==null)
            {
                return new Result(0,"");
            }
            else
            {
                return new Result(1,user);
            }
        }
    }


//    修改用户昵称
    @PostMapping("/changename")
    private int changename(@Param("name") String name,@Param("openid") String openid)
    {
        if(name!=null && !name.equals("") && openid!=null)
        {
            if(name.contains(" "))
            {
                return 2;
            }
            if(name.length()>16)
            {
                return 3;
            }
            return userDao.changename(name,openid);
        }
        else
        {
            return 0;
        }
    }

//    修改用户头像
    @PostMapping("/changehead")
    private int changehead(@RequestParam("head")MultipartFile head,@Param("openid") String openid)
    {
        if(openid==null)
        {
            return 0;
        }
        else
        {
            User user= userDao.get(openid);
            if(user==null)
            {
                return 0;
            }
            else
            {
                String rootpath="/root/tomcat/webapps/hhsc/user/";
                File file=new File(rootpath+openid);
                if(file.exists())
                {
                    long time=System.currentTimeMillis();
                    File file1=new File(rootpath+openid,time+".jpg");
                    try {
                        head.transferTo(file1);
//                        压缩图片
                        CompressPicture.cpmpress(rootpath+openid+"/"+time+".jpg");
                        String finalurl="http://47.100.9.232:8080/"+"hhsc/user/"+openid+"/"+time+".jpg";
                        return userDao.changehead(finalurl,openid);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        return 0;
                    }
                }
                else
                {
                    if(file.mkdirs())
                    {
                        long time=System.currentTimeMillis();
                        File file1=new File(rootpath+openid,time+".jpg");
                        try {
                            head.transferTo(file1);
//                            压缩图片
                            CompressPicture.cpmpress(rootpath+openid+"/"+time+".jpg");
                            String finalurl="http://47.100.9.232:8080/"+"hhsc/user/"+openid+"/"+time+".jpg";
                            return userDao.changehead(finalurl,openid);
                        } catch (IOException e) {
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
    }

}
