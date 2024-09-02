package com.example.hhcs.dao;

import com.example.hhcs.domain.AnimeMsgDomain;
import com.example.hhcs.domain.RenewAnimeHistoryDomain;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface AnimeDao {
    @Select("select * from renewAnimeHistory where name=#{url} order by updateTime desc")
    List<RenewAnimeHistoryDomain> getAllHistory(@Param("url") String url);

    @Insert("insert into animeMsg values(#{id},#{name},#{pic},#{msg},#{dec},#{createTime},#{updateTime},#{isDel},#{quarter},#{week},#{rate},#{subjectId},#{isHand})")
    Integer addAnimeMsg(AnimeMsgDomain animeMsgDomain);

    @Insert("insert into renewAnimeHistory values (#{id},#{name},#{createTime},#{updateTime},#{isDel})")
    Integer addHis(@Param("id") Integer id, @Param("name") String name,@Param("createTime")Date createTime,@Param("updateTime") Date updateTime,@Param("isDel") Integer isDel);

    @Select("select * from animeMsg where name=#{name} and quarter=#{quarter} limit 1")
    AnimeMsgDomain getMsgByNQ(@Param("name") String name,@Param("quarter") String quarter);

    @Update("update animeMsg set pic=#{pic},msg=#{msg},`dec`=#{dec},updateTime=#{updateTime},week=#{week},rate=#{rate},subjectId=#{subjectId},isHand=#{isHand} where name=#{name} and quarter=#{quarter}")
    Integer updateMsg(@Param("pic") String pic,@Param("msg") String msg,@Param("dec") String dec,@Param("updateTime") Date updateTime,@Param("name") String name,@Param("quarter") String quarter,@Param("week") String week,@Param("rate") String rate,@Param("subjectId") String subjectId,@Param("isHand") Integer isHand);

    @Select("select name from renewAnimeHistory")
    List<String> getAllQuarter();

    @Select("select * from animeMsg where quarter=#{quarter} and isDel=0")
    List<AnimeMsgDomain> getAnimeByQuarter(@Param("quarter") String quarter);

    @Select("select * from animeMsg where quarter=#{quarter} and week=#{week} and isDel=0")
    List<AnimeMsgDomain> getAnimeByQuarterAndWeek(@Param("quarter") String quarter,@Param("week") String week);

    @Select("select * from animeMsg where name like CONCAT('%',#{name},'%') and isDel=0")
    List<AnimeMsgDomain> searchAnime(@Param("name") String name);

    @Update("update animeMsg set name=#{name},pic=#{pic},msg=#{msg},`dec`=#{dec},updateTime=#{updateTime},isDel=#{isDel},quarter=#{quarter},week=#{week},rate=#{rate},subjectId=#{subjectId},isHand=#{isHand} where id=#{id}")
    Integer editMsg(AnimeMsgDomain animeMsgDomain);
}
