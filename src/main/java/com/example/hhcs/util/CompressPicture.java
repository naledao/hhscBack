package com.example.hhcs.util;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.jdbc.core.metadata.HanaCallMetaDataProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompressPicture {
    public static void again(String rootpic,char s) throws IOException {
        Map<Character,Float> map=new HashMap<>();
        map.put('3',0.9f);
        map.put('4',0.8f);
        map.put('5',0.7f);
        map.put('6',0.6f);
        map.put('7',0.5f);
        map.put('8',0.4f);
        map.put('9',0.3f);
        if(map.containsKey(s))
        {
            Thumbnails.of(rootpic)
                    .scale(map.get(s))
                    .toFile(rootpic);
        }
    }
    public static void cpmpress(String rootpic) throws IOException {
        File file=new File(rootpic);
        float len= (float) file.length()/1024;
        if(len>300 && len<1000)
        {
            again(rootpic,String.valueOf(len).charAt(0));
        }
        else if(len>1000)
        {
            float bei=len/300;
            float[] scale=new float[]{0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f};
            float min=10000;
            float sc=0.6f;
            for(float f:scale)
            {
                float fit=146*f*f-172*f+48;
                float cha=Math.abs(bei-fit);
                if(cha<min)
                {
                    min=cha;
                    sc=f;
                }
            }
            sc= (float) (sc+0.2);
            if(sc>0.8)
            {
                sc=0.3f;
            }
            Thumbnails.of(rootpic)
                    .scale(sc)
                    .toFile(rootpic);
            File file1=new File(rootpic);
            len= (float) file1.length() /1024;
            if(len>300 && len<1000)
            {
                again(rootpic,String.valueOf(len).charAt(0));
            }
        }
    }
}
