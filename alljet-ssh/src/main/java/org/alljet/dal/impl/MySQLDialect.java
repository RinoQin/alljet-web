/*
 * FileName: MySQLDialect.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午1:39:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.impl;

import org.alljet.dal.dialect.AbstractDialect;

/**
 * 功能描述：SQL分页封装， MySQl方言
 * 
 * @author v_qinyuchen
 * @version 1.0.0
 */
public class MySQLDialect extends AbstractDialect {
    /**
     * 封装SQL，查询前几条记录
     * 
     * @param sql --源SQL
     * @return SQL串
     */
    public String getLimitStringForRandom(String sql) {
        return new StringBuffer(sql.length() + 50).append(sql).append(" ORDER BY RAND() ").append(" limit :_limit")
                .toString();
    }

    /**
     * 封装SQL，查询从_offset什么位置开始、指定_limit行记录
     * 
     * @param sql --源SQL
     * @return SQL串
     */
    public String getLimitString(String sql) {
        return new StringBuffer(sql.length() + 20).append(sql).append(" limit :_offset, :_limit").toString();
    }
}
