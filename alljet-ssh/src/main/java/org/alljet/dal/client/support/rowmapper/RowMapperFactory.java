/*
 * FileName: RowMapperFactory.java
 * Author:   v_qinyuchen
 * Date:     2016年4月6日 下午3:49:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.client.support.rowmapper;

import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 * 
 * 映射类型工厂
 * 
 * @param <T> class 实现类
 * @author 12010065
 */
public class RowMapperFactory<T> {
    /**
     * 需处理的类型
     */
    private final Class<T> requiredType;

    /**
     * 构造函数
     * 
     * @param requiredType 需处理的类型
     */
    public RowMapperFactory(Class<T> requiredType) {
        this.requiredType = requiredType;
    }

    /**
     * 获取翻页处理逻辑
     * 
     * @return 翻页处理逻辑
     */
    public RowMapper<T> getRowMapper() {
        if (requiredType.equals(String.class) || Number.class.isAssignableFrom(requiredType)
                || requiredType.equals(Date.class)) {
            return new SingleColumnRowMapper<T>(requiredType);
        } else {
            return new DefaultBeanPropertyRowMapper<T>(requiredType);
        }
    }
}
