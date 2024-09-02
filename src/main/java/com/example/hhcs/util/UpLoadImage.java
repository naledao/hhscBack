package com.example.hhcs.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;

@Component
public class UpLoadImage {
    private String rootpath="/root/tomcat/webapps/";
    public  int addimage(String openid, String choice, MultipartFile image,String key,int warehouseid,int imageid,String simageid)
    {
        if(choice.equals("s"))
        {
            String path=rootpath+"hhsc/start/image/";
            File file=new File(path);
            File[] list = file.listFiles();
            if(list.length==0)
            {
                File file1=new File(path,key+"_"+"1.jpg");
                try {
                    image.transferTo(file1);
                    CompressPicture.cpmpress(path+key+"_"+"1.jpg");
                } catch (IOException e) {
                    return 0;
                }
                return 1;
            }
            else
            {
                String[] zan=list[0].getName().split("_");
                if(zan[0].equals(key))
                {
                    TreeSet<Integer> set=new TreeSet<>();
                    for(File file1:list)
                    {
                        String[] nm=file1.getName().split("_");
                        String name=nm[1];
                        name=name.substring(0,name.indexOf("."));
                        set.add(Integer.parseInt(name));
                    }
                    String name=key+"_"+String.valueOf(set.last()+1)+".jpg";
                    File file1=new File(path,name);
                    try {
                        image.transferTo(file1);
                        CompressPicture.cpmpress(path+name);
                    } catch (IOException e) {
                        return 0;
                    }
                    return 1;
                }
                else
                {
                    boolean res=true;
                    for(File n:list)
                    {
                        boolean x=n.delete();
                        res=res && x;
                    }
                    if(res)
                    {
                        File file1=new File(path,key+"_"+"1.jpg");
                        try {
                            image.transferTo(file1);
                            CompressPicture.cpmpress(path+key+"_"+"1.jpg");
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
        }

        if(choice.equals("a"))
        {
            String path=rootpath+"hhsc/roll/image/";
            File file=new File(path);
            File[] list = file.listFiles();
            if(list.length==0)
            {
                File file1=new File(path,key+"_"+"1.jpg");
                try {
                    image.transferTo(file1);
                    CompressPicture.cpmpress(path+key+"_"+"1.jpg");
                } catch (IOException e) {
                    return 0;
                }
                return 1;
            }
            else
            {
                String[] zan=list[0].getName().split("_");
                if(zan[0].equals(key))
                {
                    TreeSet<Integer> set=new TreeSet<>();
                    for(File file1:list)
                    {
                        String[] nm=file1.getName().split("_");
                        String name=nm[1];
                        name=name.substring(0,name.indexOf("."));
                        set.add(Integer.parseInt(name));
                    }
                    String name=key+"_"+String.valueOf(set.last()+1)+".jpg";
                    File file1=new File(path,name);
                    try {
                        image.transferTo(file1);
                        CompressPicture.cpmpress(path+name);
                    } catch (IOException e) {
                        return 0;
                    }
                    return 1;
                }
                else
                {
                    boolean res=true;
                    for(File n:list)
                    {
                        boolean x=n.delete();
                        res=res && x;
                    }
                    if(res)
                    {
                        File file1=new File(path,key+"_"+"1.jpg");
                        try {
                            image.transferTo(file1);
                            CompressPicture.cpmpress(path+key+"_"+"1.jpg");
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
        }
        if(choice.equals("b"))
        {
            String path=rootpath+"hhsc/slide/"+key+"/image/";
            File file=new File(path);
            if(file.exists())
            {
                File[] files=file.listFiles();
                if(files.length==0)
                {
                    File file1=new File(path,"1.jpg");
                    try {
                        image.transferTo(file1);
                        CompressPicture.cpmpress(path+"1.jpg");
                    } catch (IOException e) {
                        return 0;
                    }
                    return 1;
                }
                else
                {
                    int max=-1;
                    for(File file1:files)
                    {
                        String name=file1.getName();
                        name=name.substring(0,name.indexOf("."));
                        max=Math.max(max,Integer.parseInt(name));
                    }
                    String name=(max+1)+".jpg";
                    File file1=new File(path,name);
                    try {
                        image.transferTo(file1);
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
                        image.transferTo(file1);
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


        if(choice.equals("c"))
        {
            String path=rootpath+"hhsc/common/"+openid+"/"+simageid+"/image";
            File file=new File(path);
            if(file.exists())
            {
                File[] list=file.listFiles();
                assert list != null;
                if(list.length==0)
                {
                    File file1=new File(path,"1.jpg");
                    try {
                        image.transferTo(file1);
                        CompressPicture.cpmpress(path+"/1.jpg");
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
                        image.transferTo(file1);
                        CompressPicture.cpmpress(path+"/"+name);
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
                        image.transferTo(file1);
                        CompressPicture.cpmpress(path+"/1.jpg");
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



        else
        {
            return 0;
        }
    }
}
