package com.example.hhcs.controller;

import com.example.hhcs.dao.ChatGptDao;
import com.example.hhcs.dao.TableMapper;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.ChatGptMessageYoN;
import com.example.hhcs.domain.ChatGptMessages;
import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.User;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/chatgpt")
public class ChatGptController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private ChatGptDao chatGptDao;

    //    判断服务是否开启
    @PostMapping("/yon")
    private int serviceyon()
    {
        String yon=stringRedisTemplate.opsForValue().get("chatgpt");
        if(yon==null || yon.equals(""))
        {
            return 0;
//            服务未开启
        }
        else
        {
            return 1;
//            服务开启
        }
    }


    @PostMapping("/emailyon")
//    判断当前用户是否绑定邮箱
    private int emailyon(@RequestParam("openid") String openid)
    {
        if(openid!=null)
        {
            User user= userDao.get(openid);
            if(user!=null)
            {
                if(user.getEmail()==null || user.getEmail().equals(""))
                {
                    return 0;
//                    没有绑定邮箱
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -1;
        }
    }


    @PostMapping("/setusermessage")
//    存用户发送的消息
    private Result getmessage(@RequestParam("message") String message,@RequestParam("openid") String openid)
    {
        //        code:
//        -1:服务未开启
        // 0:不具备使用权利
        // 1:消息不合法
//        2:建表失败
//        3:进入消息队列成功
//        4:进入消息队列失败
//        5:历史会话过长
        String yon=stringRedisTemplate.opsForValue().get("chatgpt");
//        服务开启中
        if(yon!=null && yon.equals("1"))
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
                    if(message==null || message.equals("") || message.length()>600)
                    {
                        return new Result(1,"");
                    }
                    else
                    {
                        openid=openid.replace("-","");
                        try {
                            int count= chatGptDao.getcount("usertochatgpt"+openid);
                            if(count>=30)
                            {
                                return new Result(5,"");
                            }
                        }catch (Exception ignored)
                        {
                        }
//                        进入消息队列
                        tableMapper.createchattogpt("usertochatgpt"+openid);
                        int t=0;
                        try {
                            int d=chatGptDao.getcount("usertochatgpt"+openid);
                        }catch (Exception e)
                        {
//                            没有创建好表
                            t=1;
                        }
                        if(t==0)
                        {
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time=formatter.format(date);
                            int y=chatGptDao.addmessage("usertochatgpt"+openid,null,"user",message,openid,time,0);
                            if(y==1)
                            {
                                int y2= chatGptDao.addmessageQueue(null,"usertochatgpt"+openid);
                                if(y2==1)
                                {
                                    return new Result(3,"");
                                }
                                else
                                {
                                    List<Integer> list=chatGptDao.getlastqueueid("usertochatgpt"+openid);
                                    for(int i:list)
                                    {
                                        ChatGptMessageYoN chatGptMessageYoN= chatGptDao.check("usertochatgpt"+openid,i);
                                        if(chatGptMessageYoN.getRole().equals("user") && chatGptMessageYoN.getYorn()==0)
                                        {
                                            chatGptDao.delmsg("usertochatgpt"+openid,i);
                                            break;
                                        }
                                    }
                                    return new Result(4,"");
                                }
                            }
                            else
                            {
                                return new Result(4,"");
                            }
                        }
                        else
                        {
                            return new Result(2,"");
                        }
                    }
                }
            }
        }
//        服务未开启
        else
        {
            return new Result(-1,"");
        }
    }



    @GetMapping("/getallmessge")
//    获取当前用户所有消息
    private List<ChatGptMessages> getallmesssge(@Param("openid") String openid)
    {
        if(openid==null)
        {
            return new ArrayList<>();
        }
        else
        {
            User user= userDao.get(openid);
            if(user==null)
            {
                return new ArrayList<>();
            }
            else
            {
                openid=openid.replace("-","");
                String tablename="usertochatgpt"+openid;
                try {
                    List<ChatGptMessages> list= chatGptDao.getallmessages(tablename);
                    if(list==null)
                    {
                        return new ArrayList<>();
                    }
                    else
                    {
                        return list;
                    }
                }catch (Exception e)
                {
                    return new ArrayList<>();
                }
            }
        }
    }


    @GetMapping("/reset")
//    重置会话
    private int reset(@Param("openid") String openid)
    {
        if(openid==null)
        {
            return 0;
        }
        else
        {
            openid=openid.replace("-","");
            try {
                int t=chatGptDao.delallmessages("usertochatgpt"+openid);
                if(t>0)
                {
                    return 1;
                }
                else
                {
                    return 0;
                }
            }catch (Exception e)
            {
                return 0;
            }
        }
    }


    @GetMapping("/getallhuihua")
    private int get(@RequestParam("openid") String openid)
    {
        if(openid!=null)
        {
            try {
                List<ChatGptMessages> list= chatGptDao.getallmessages("usertochatgpt"+openid);
                if(list.size()>=30)
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            }catch (Exception e)
            {
                return 1;
            }
        }
        else
        {
            return 1;
        }
    }


//    获取前面还有多少条消息要处理
    @GetMapping("/getwait")
    private Integer getwait()
    {
        Integer sum=chatGptDao.allqueue();
        if(sum==null)
        {
            return 3;
        }
        else
        {
            return sum;
        }
    }
}
