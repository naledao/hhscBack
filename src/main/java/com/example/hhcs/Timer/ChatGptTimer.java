package com.example.hhcs.Timer;


import com.example.hhcs.dao.ChatGptDao;
import com.example.hhcs.dao.UserDao;
import com.example.hhcs.domain.*;
import com.example.hhcs.util.MailUtils;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ChatGptTimer {
    @Autowired
    UserDao userDao;
    @Autowired
    private ChatGptDao chatGptDao;
//    处理消息队列中的消息
    @Scheduled(cron = "0 0/1 * * * ?")
    private void handlemessage()
    {
        String tablename= chatGptDao.getfirstqueue();
        Integer q_id= chatGptDao.getfirstid();
        if(tablename!=null && q_id!=null)
        {
            int flag=0;
            List<ChatMessage> list1=new ArrayList<>();
            List<Integer> list= chatGptDao.getqueue(tablename);
            for(int id:list)
            {
                ChatGptMessageYoN chatGptMessageYoN= chatGptDao.check(tablename,id);
                list1.add(new ChatMessage(chatGptMessageYoN.getRole(),chatGptMessageYoN.getMessage()));
                if(chatGptMessageYoN.getRole().equals("user") && chatGptMessageYoN.getYorn()==0)
                {
                    flag=1;
//                    像gpt发送消息
                    RestTemplate restTemplate=new RestTemplate();
                    ChatGpt chatGpt=new ChatGpt();
                    chatGpt.setOpenid(chatGptMessageYoN.getOpenid());
                    chatGpt.setList_message(list1);
                    chatGpt.setPassword("2419646091");
                    chatGpt.setQuestion(chatGptMessageYoN.getMessage());
                    ChatGptResult chatGptResult=null;
                    try {
                        chatGptResult =restTemplate.postForObject("http://47.254.20.19:9660/chatgpt/getmessage",chatGpt,ChatGptResult.class);
                    }catch (Exception e)
                    {
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time=formatter.format(date);
                        System.out.println(time);
                        System.out.println("调用openai出现错误:");
                        System.out.println(e.getMessage());
                    }
                    if(chatGptResult==null)
                    {
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time=formatter.format(date);
                        int t1=chatGptDao.upmessage(tablename,1,id);
                        if(t1==1)
                        {
                            int t=chatGptDao.addmessage(tablename,null,"assistant","错误:建议重置会话后再试!", chatGpt.getOpenid(), time,1);
                            if(t==1)
                            {
                                chatGptDao.delqueue("ChatGptQueue",q_id);
                            }
                            else
                            {
                                chatGptDao.upmessage(tablename,0,id);
                            }
                        }
                        break;
                    }
                    else
                    {
                        if(chatGptResult.getCode()==1)
                        {
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time=formatter.format(date);
                            int t1=chatGptDao.upmessage(tablename,1,id);
                            if(t1==1)
                            {
                                int t=chatGptDao.addmessage(tablename,null,"assistant",chatGptResult.getMessage(), chatGpt.getOpenid(), time,1);
                                if(t==1)
                                {
                                    chatGptDao.delqueue("ChatGptQueue",q_id);
//                                    通知用户服务完成
                                    User user= userDao.get(chatGpt.getOpenid());
                                    if(user!=null && user.getEmail()!=null && !user.getEmail().equals(""))
                                    {
                                        String msg="chat回应了你，赶紧上线看看吧!";
                                        MailUtils.sendMail(user.getEmail(),msg,"【黄淮市场服务通知】");
                                    }
                                }
                                else
                                {

                                    chatGptDao.upmessage(tablename,0,id);
                                }
                            }
                        }
                        else if(chatGptResult.getCode()==0)
                        {
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time=formatter.format(date);
                            int t1=chatGptDao.upmessage(tablename,1,id);
                            if(t1==1)
                            {
                                int t=chatGptDao.addmessage(tablename,null,"assistant","错误:建议重置会话后再试!", chatGpt.getOpenid(), time,1);
                                if(t==1)
                                {
                                    chatGptDao.delqueue("ChatGptQueue",q_id);
                                }
                                else
                                {
                                    chatGptDao.upmessage(tablename,0,id);
                                }
                            }
                            break;
                        }
                        else if(chatGptResult.getCode()==2)
                        {
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String time=formatter.format(date);
                            int t1=chatGptDao.upmessage(tablename,1,id);
                            if(t1==1)
                            {
                                int t=chatGptDao.addmessage(tablename,null,"assistant","错误:建议重置会话后再试!", chatGpt.getOpenid(), time,1);
                                if(t==1)
                                {
                                    chatGptDao.delqueue("ChatGptQueue",q_id);
                                }
                                else
                                {
                                    chatGptDao.upmessage(tablename,0,id);
                                }
                            }
                            System.out.println(time);
                            System.out.println("调用openai出现错误:");
                            System.out.println(chatGptResult.getMessage());
                            break;
                        }
                        break;
                    }

                }
            }
            if(flag==0)
            {
                chatGptDao.delqueue("ChatGptQueue",q_id);
            }
        }
    }
}
