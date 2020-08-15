package com.zhangzhe.mapper;

import com.zhangzhe.pojo.SearchRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchRecordsMapper {

    int insert(SearchRecords searchRecords);

    List<String> getHotWords();
}
