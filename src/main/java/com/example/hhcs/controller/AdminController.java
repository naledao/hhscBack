package com.example.hhcs.controller;

import com.example.hhcs.dao.AdminDao;
import com.example.hhcs.domain.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AdminDao adminDao;




//    管理员登录
    @PostMapping()
    @CrossOrigin
    private Result login(@RequestBody Admin admin)
    {
        if(admin!=null && admin.getName()!=null && admin.getPassword()!=null)
        {
            Admin admin1=adminDao.selectAdmin(admin.getName());
            if(admin1==null || !admin1.getPassword().equals(admin.getPassword()))
            {
                return new Result(0,"");
            }
            else
            {
                String[] res=new String[]{"1","2","3","4","5","6","7","8","9","q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
                StringBuilder stringBuilder=new StringBuilder();
                Random random=new Random(System.currentTimeMillis());
                while (true)
                {
                    while (true)
                    {
                        stringBuilder.append(res[random.nextInt(res.length)]);
                        if(stringBuilder.length()==13)
                        {
                            break;
                        }
                    }
                    String key=stringRedisTemplate.opsForValue().get("admin:"+admin1.getName());
                    if(key==null)
                    {
                        break;
                    }
                    else
                    {
                        if(!key.equals(new String(stringBuilder)))
                        {
                            break;
                        }
                        else
                        {
                            stringBuilder.delete(0,stringBuilder.length());
                        }
                    }
                }
                String kk= String.valueOf(stringBuilder);
                stringRedisTemplate.opsForValue().set("admin:"+admin1.getName(), kk,30, TimeUnit.MINUTES);
                return new Result(1,new String(stringBuilder));
            }
        }
        else
        {
            return new Result(0,"");
        }
    }



//    获取用户
    @PostMapping("/getusers/{page}")
    @CrossOrigin
    private Result getusers(@RequestBody Jurisdiction jurisdiction,@PathVariable int page)
    {
        if(jurisdiction!=null && jurisdiction.getName()!=null && jurisdiction.getMessage()!=null)
        {
            String key=stringRedisTemplate.opsForValue().get("admin:"+jurisdiction.getName());
            if(key==null)
            {
                return new Result(0,"");
            }
            else
            {
                if(!jurisdiction.getMessage().equals(key))
                {
                    return new Result(0,"");
                }
                else
                {
                    if(page>=1)
                    {
                        int csum= adminDao.getcountcstore();
                        int sum=adminDao.getalluser();
                        if(sum%5==0)
                        {
                            int pagesum=sum/5;
                            if(page>pagesum)
                            {
                                return new Result(0,"");
                            }
                            else
                            {
                                PageHelper.startPage(page,5);
                                List<User> list= adminDao.getusers();
                                return new Result(1,sum,csum,list);
                            }
                        }
                        else
                        {
                            int pagesum=sum/5+1;
                            if(page>pagesum)
                            {
                                return new Result(0,"");
                            }
                            else
                            {
                                PageHelper.startPage(page,5);
                                List<User> list= adminDao.getusers();
                                return new Result(1,sum,csum,list);
                            }
                        }
                    }
                    else
                    {
                        return new Result(0,"");
                    }
                }
            }
        }
        else
        {
            return new Result(0,"");
        }
    }




//    获取c商品信息
    @PostMapping("/getcstore/{page}")
    @CrossOrigin
    private Result getcstore(@RequestBody Jurisdiction jurisdiction,@PathVariable int page)
    {
        if(jurisdiction!=null && jurisdiction.getName()!=null && jurisdiction.getMessage()!=null)
        {
            String key=stringRedisTemplate.opsForValue().get("admin:"+jurisdiction.getName());
            if(key==null)
            {
                return new Result(0,"");
            }
            else
            {
                if(!jurisdiction.getMessage().equals(key))
                {
                    return new Result(0,"");
                }
                else
                {
                    if(page>=1)
                    {
                        int sum= adminDao.getcountcstore();
                        if(sum%6==0)
                        {
                            int pagesum=sum/6;
                            if(page>pagesum)
                            {
                                return new Result(0,"");
                            }
                            else
                            {
                                PageHelper.startPage(page,6);
                                List<Cstore> list=adminDao.getcstore();
                                return new Result(1,sum,sum,list);
                            }
                        }
                        else
                        {
                            int pagesum=sum/6+1;
                            if(page>pagesum)
                            {
                                return new Result(0,"");
                            }
                            else
                            {
                                PageHelper.startPage(page,6);
                                List<Cstore> list= adminDao.getcstore();
                                return new Result(1,sum,sum,list);
                            }
                        }
                    }
                    else
                    {
                        return new Result(0,"");
                    }
                }
            }
        }
        else
        {
            return new Result(0,"");
        }
    }

}
