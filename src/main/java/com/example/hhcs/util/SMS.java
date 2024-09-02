package com.example.hhcs.util;

import org.springframework.stereotype.Component;

import java.util.Random;


public class SMS {
    public static String sms(){
        Random random=new Random(System.currentTimeMillis());
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=1;i<=6;i++)
        {
            stringBuilder.append(random.nextInt(10));
        }
        return new String(stringBuilder);
    }
}
