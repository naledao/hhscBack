package com.example.hhcs.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

@Component
public class UploadPic {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private String rootpath="/root/tomcat/webapps/";
    @Transactional
    public int addpic(String choice, MultipartFile pic,String openid,String key,int warehouseid,int imageid,String simageid)
    {
        if(choice.equals("c"))
        {
//            判断有没有实体仓库
            String path=rootpath+"hhsc/common/"+openid+"/"+simageid+"/pic/";
            File file=new File(path);
            if(file.exists())
            {
                File[] list=file.listFiles();
                assert list != null;
                if(list.length==0)
                {
                    File file1=new File(path,"1.jpg");
                    try {
                        pic.transferTo(file1);
                        CompressPicture.cpmpress(path+"1.jpg");
                    } catch (IOException e) {
                        return 0;
                    }
                    return 1;
                }
                else
                {
                    TreeSet<Integer> set=new TreeSet<>();
                    for(File i:list)
                    {
                        String name=i.getName().split("[.]")[0];
                        set.add(Integer.parseInt(name));
                    }
                    String name= String.valueOf(set.last()+1)+".jpg";
                    File file1=new File(path,name);
                    try {
                        pic.transferTo(file1);
                        CompressPicture.cpmpress(path+name);
                    } catch (IOException e) {
                        return 0;
                    }
                    return 1;
                }
            }
            else
            {
                if(file.mkdirs())
                {
                    File file1=new File(path,"1.jpg");
                    try {
                        pic.transferTo(file1);
                        CompressPicture.cpmpress(path+"1.jpg");
                    } catch (IOException e) {
                        return 0;
                    }
                    return 1;
                }
                else
                {
                    return 0;
                }
            }
        }



        if(choice.equals("s"))
        {
            String name=stringRedisTemplate.opsForValue().get("startname");
            String resname=name+".jpg";
//            s级商品封面添加
            String path=rootpath+"hhsc/start/pic/";
            File file=new File(path,resname);
            try {
                pic.transferTo(file);
                CompressPicture.cpmpress(path+resname);
                int res=Integer.parseInt(name)+1;
                stringRedisTemplate.opsForValue().set("startname",String.valueOf(res));
            } catch (IOException e) {
                return 0;
            }
            return 1;
        }

        if(choice.equals("a"))
        {
            String name=stringRedisTemplate.opsForValue().get("rollname");
            String resname=name+".jpg";
//            a级商品封面添加
            String path=rootpath+"hhsc/roll/pic/";
            File file=new File(path,resname);
            try {
                pic.transferTo(file);
                CompressPicture.cpmpress(path+resname);
                int res=Integer.parseInt(name)+1;
                stringRedisTemplate.opsForValue().set("rollname",String.valueOf(res));
            } catch (IOException e) {
                return 0;
            }
            return 1;
        }

        if(choice.equals("b"))
        {
            String path=rootpath+"hhsc/slide/"+key+"/pic/";
            File file=new File(path);
            if(file.exists())
            {
                File file1=new File(path,key+".jpg");
                try {
                    pic.transferTo(file1);
                    CompressPicture.cpmpress(path+key+".jpg");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    return 0;
                }
                return 1;
            }
            else
            {
                if(file.mkdirs())
                {
                    File file1=new File(path,key+".jpg");
                    try {
                        pic.transferTo(file1);
                        CompressPicture.cpmpress(path+key+".jpg");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        return 0;
                    }
                    return 1;
                }
                else
                {
                    System.out.println(0);
                    return 0;
                }
            }
        }

        else
        {
            return 0;
        }
    }
}
