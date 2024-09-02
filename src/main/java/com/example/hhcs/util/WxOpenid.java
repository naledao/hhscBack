package com.example.hhcs.util;

import com.example.hhcs.domain.Result;
import com.example.hhcs.domain.WxReturn;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WxOpenid {
    public Result getcode(String code){
        HttpClient httpClient = new HttpClient();
        String url="https://api.weixin.qq.com/sns/jscode2session?appid=wxdf34f89e3ec587b5"+
                "&secret=5f83dd6cd8a18a691bb8ff723775df06&js_code="+code+"&grant_type=authorization_code";
        GetMethod getMethod=new GetMethod(url);
        try {
            int num = httpClient.executeMethod(getMethod);
            if(num==200){
                String c=getMethod.getResponseBodyAsString();
                WxReturn stu = new Gson().fromJson(c, WxReturn.class);
                if(stu.getErrcode()==0){
                    return new Result(0,stu);
                }
                else{
                    System.out.println(1);
                    return new Result(1,"");
                }

            }
            else {
                return new Result(1,"");
            }
        } catch (IOException e) {
            return new Result(1,"");
        }
    }
}
