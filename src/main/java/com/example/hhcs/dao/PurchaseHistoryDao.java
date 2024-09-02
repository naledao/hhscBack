package com.example.hhcs.dao;

import com.example.hhcs.domain.PurchaseHistory;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PurchaseHistoryDao {

    @Insert("insert into purchasehistory values(#{openid},#{s},#{a},#{b},#{c})")
    int addpeop(PurchaseHistory purchaseHistory);

//    更新用户求购历史
    @Update("update purchasehistory set s=#{s} where openid=#{openid}")
    int ups(@Param("s") int s, @Param("openid") String openid);

    @Update("update purchasehistory set b=#{b} where openid=#{openid}")
    int upb(@Param("b") String b,@Param("openid") String openid);

    @Update("update purchasehistory set c=#{c} where openid=#{openid}")
    int upc(@Param("c") String c,@Param("openid") String openid);

    @Select("select openid from purchasehistory where openid=#{openid}")
    String getyn(@Param("openid") String openid);

    @Select("select * from purchasehistory where openid=#{openid}")
    PurchaseHistory gethis(@Param("openid") String openid);


}
