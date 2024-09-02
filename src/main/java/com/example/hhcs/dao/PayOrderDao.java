package com.example.hhcs.dao;

import com.example.hhcs.domain.PayOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PayOrderDao {
    @Insert("insert into payorder values(#{payid},#{cloundid},#{secret},#{openid})")
    int add(PayOrder payOrder);

    @Select("select * from payorder where payid=#{payid}")
    PayOrder get(@Param("payid") String payid);

    @Select("select * from payorder where secret=#{key}")
    PayOrder y(@Param("key") String key);

    @Update("update payorder set secret=#{key} where payid=#{payid}")
    int up(@Param("key") String key,@Param("payid") String payid);

    @Select("select secret from payorder where openid=#{openid}")
    List<String> getkey(@Param("openid") String openid);
}
