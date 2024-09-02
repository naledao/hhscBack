package com.example.hhcs.dao;

import com.example.hhcs.domain.ImageB;
import com.example.hhcs.domain.ImageC;
import com.example.hhcs.domain.ImageS;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ImageDao {
//    s级商品图片的获取
    @Select("select * from imageS")
    ImageS get();


//    a级商品图片的获取
    @Select("select * from imageA")
    ImageS geta();

    //    b级商品图片的获取
    @Select("select * from imageB where keynm=#{key}")
    ImageB getb(@Param("key") String key);

//    删除s级商品的所有图片
    @Delete("delete from imageS")
    int dels();

//    删除a级商品的所有图片
    @Delete("delete from imageA")
    int dela();

    //    删除a级商品的所有图片
    @Delete("delete from imageB where keynm=#{key}")
    int delb(@Param("key") String key);

    //    a级商品图片的获取
    @Select("select * from imageA")
    ImageS getaimage();


//    插入s商品图片仓库
    @Insert("insert into imageS values(#{id},#{img1},#{img2},#{img3},#{img4},#{img5},#{img6})")
    int adds(ImageS imageS);


    //    插入a商品图片仓库
    @Insert("insert into imageA values(#{id},#{img1},#{img2},#{img3},#{img4},#{img5},#{img6})")
    int adda(ImageS imageS);

    //    插入b商品图片仓库
    @Insert("insert into imageB values(#{id},#{img1},#{img2},#{img3},#{img4},#{img5},#{img6},#{keynm})")
    int addb(ImageB imageS);


//    插入c商品仓库
    @Insert("insert into imageC values(#{id},#{img1},#{img2},#{img3},#{img4},#{img5},#{img6})")
    int addc(ImageC imageC);

    @Select("select * from imageC where id=#{id}")
    ImageC getimgc(@Param("id") String id);
}
