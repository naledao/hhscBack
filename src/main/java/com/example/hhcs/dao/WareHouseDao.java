package com.example.hhcs.dao;

import com.example.hhcs.domain.WareHouse;
import com.example.hhcs.domain.WareHouseA;
import com.example.hhcs.domain.WareHouseB;
import com.example.hhcs.domain.WareHouseC;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WareHouseDao {
//    s级商品插入
    @Insert("insert into warehouse_s values(#{id},#{sid})")
    int adds(@Param("id") int id,@Param("sid") int sid);

    //    a级商品插入
    @Insert("insert into warehouse_a values(#{id},#{aid})")
    int adda(@Param("id") int id,@Param("aid") int aid);


    @Delete("delete from warehouse_s")
    int dels();


    @Delete("delete from warehouse_a")
    int dela();

    @Select("select * from warehouse_s")
    WareHouse get();

    @Select("select * from warehouse_a")
    WareHouse geta();

    @Select("select * from warehouse_a")
    WareHouseA getA();

    @Select("select * from warehouse_b where id=#{id}")
    WareHouseB getb(@Param("id") int id);

    @Insert("insert into warehouse_b values(#{id},#{bid1},#{bid2},#{bid3})")
    int addb(WareHouseB wareHouseB);

    @Update("update warehouse_b set bid1=#{bid1},bid2=#{bid2},bid3=#{bid3} where id=#{id}")
    int upb(WareHouseB wareHouseB);

    @Select("select * from warehouse where id=#{id}")
    WareHouseC getc(@Param("id") int id);

    @Insert("insert into warehouse values(#{id},#{cid})")
    int addc(WareHouseC wareHouseC);

    @Select("select * from warehouse where id=#{id}")
    WareHouseC getcw(@Param("id") int id);

    @Update("update warehouse set cid=#{cid} where id=#{id}")
    int upcw(@Param("cid") String cid,@Param("id") int id);
}
