package com.example.hhcs.dao;

import com.example.hhcs.domain.BrowseHistory;
import org.apache.ibatis.annotations.*;

@Mapper
public interface BrowseHistoryDao {
    @Select("select * from browse_history where id=#{id}")
    BrowseHistory get(@Param("id") int id);

    @Insert("insert into browse_history values(#{id},#{s},#{a},#{b},#{c})")
    int inithistory(BrowseHistory browseHistory);

    @Update("update browse_history set s=#{s},a=#{a},b=#{b},c=#{c} where id=#{id}")
    int updatehistory(BrowseHistory browseHistory);
}
