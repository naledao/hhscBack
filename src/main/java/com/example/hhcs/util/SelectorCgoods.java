package com.example.hhcs.util;

import com.example.hhcs.dao.CstoreDao;
import com.example.hhcs.domain.Cstore;
import com.example.hhcs.domain.Result;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class SelectorCgoods {
    @Autowired
    private CstoreDao cstoreDao;
    public Result get(String area,int page)
    {
        if(area.equals("all"))
        {
            int count=cstoreDao.getallall(1);
            if(count%9==0)
            {
                int countpage=count/9;
                if(page>countpage)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getareaalllist(1);
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
            else
            {
                int countpage=(count/9)+1;
                if(page>countpage)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getareaalllist(1);
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
        }


        else if(area.equals("hospital"))
        {
            int count=cstoreDao.getalldisarea(1,"医学院区");
            if(count%9==0)
            {
                int pagesum=count/9;
                if(page>pagesum)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getarealist(1,"医学院区");
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
            else
            {
                int pagesum=count/9+1;
                if(page>pagesum)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getarealist(1,"医学院区");
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
        }

        else if(area.equals("north"))
        {
            int count=cstoreDao.getalldisarea(1,"北区");
            if(count%9==0)
            {
                int pagesum=count/9;
                if(page>pagesum)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getarealist(1,"北区");
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
            else
            {
                int pagesum=count/9+1;
                if(page>pagesum)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getarealist(1,"北区");
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
        }

        else if(area.equals("south"))
        {
            int count=cstoreDao.getalldisarea(1,"南区");
            if(count%9==0)
            {
                int pagesum=count/9;
                if(page>pagesum)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getarealist(1,"南区");
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
            else
            {
                int pagesum=count/9+1;
                if(page>pagesum)
                {
                    return new Result(0,"");
                }
                else
                {
                    PageHelper.startPage(page,9);
                    List<Cstore> list=cstoreDao.getarealist(1,"南区");
                    if(list==null ||list.size()==0)
                    {
                        return new Result(0,"");
                    }
                    else
                    {
                        return new Result(1, Collections.singletonList(list));
                    }
                }
            }
        }
        else
        {
            return new Result(0,"");
        }
    }


    public Result getfree(String text,int page)
    {
        int count =cstoreDao.getfree(text);
        if(count%9==0)
        {
            int pagesize=count/9;
            if(page>pagesize)
            {
                return new Result(0,"");
            }
            else
            {
                PageHelper.startPage(page,9);
                List<Cstore> list=cstoreDao.getfreesearch(text);
                if(list==null || list.size()==0)
                {
                    return new Result(0,"");
                }
                else
                {
                    return new Result(1,Collections.singletonList(list));
                }
            }
        }
        else
        {
            int pagesize=count/9+1;
            if(page>pagesize)
            {
                return new Result(0,"");
            }
            else
            {
                PageHelper.startPage(page,9);
                List<Cstore> list=cstoreDao.getfreesearch(text);
                if(list==null || list.size()==0)
                {
                    return new Result(0,"");
                }
                else
                {
                    return new Result(1,Collections.singletonList(list));
                }
            }
        }
    }
}
