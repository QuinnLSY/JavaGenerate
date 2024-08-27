package com.java_generate_demo.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T, P> {
    /**
     * insert:(插入)
     */
    Long insert(@Param("bean") T t);

    /**
     * insertOrUpdate:(更新)
     */
    Long insertOrUpdate(@Param("bean") T t);

    /**
     * delete:(删除)
     */
    Long delete(@Param("bean") T t);

    /**
     * insertBatch:(批量插入)
     */
    Long insertBatch(@Param("list") List<T> list);

    /**
     * deleteBatch:(批量删除)
     */
    Long deleteBatch(@Param("list") List<T> list);

    /**
     * insertOrUpdateBatch:(批量插入或更新)
     */
    Long insertOrUpdateBatch(@Param("list") List<T> list);

    /**
     * selectList:(根据参数查询集合)
     */
    List<T> selectList(@Param("query") P p);

    /**
     * selectCount:(根据集合查询数量)
     */
    Long selectCount(@Param("query") P p);
}
