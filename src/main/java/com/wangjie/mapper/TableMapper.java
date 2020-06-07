package com.wangjie.mapper;

import com.wangjie.model.db.Table;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TableMapper
 * @Description
 * @Author wangjie
 * @Date 2020/5/21 1:39 下午
 * @Email wangjie_fourth@163.com
 **/
@Mapper
public interface TableMapper {
    /**
     * 查询这个表的基本信息
     *
     * @param tableName 不含schema的表名
     * @return 表的信息
     */
    Table queryTable(String tableName);

    /**
     * 查询这个数据库的所有表，除去flyway表
     *
     * @param map   查询条件
     * @return      数据库表的所有信息
     */
    List<Table> queryTableList(Map<String, Object> map);
}
