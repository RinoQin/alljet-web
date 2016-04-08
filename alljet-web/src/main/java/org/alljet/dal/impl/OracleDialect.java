/*
 * FileName: OracleDialect.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午1:40:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.impl;

import org.alljet.dal.dialect.AbstractDialect;

/**
 * SQL分页封装，Oracle方言<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class OracleDialect extends AbstractDialect {
    /**
     * 封装SQL，查询前几条记录
     * 
     * @param sql --源SQL
     * @return SQL串
     */
    public String getLimitStringForRandom(String sql) {
        return new StringBuffer(sql.length() + 100).append("select t.*,rownum rn from (").append(sql)
                .append(") t where ROWNUM <= :_limit)").toString();
    }

    /**
     * 封装SQL，查询从什么位置开始、指定行记录
     * 
     * @param sql --源SQL
     * @return SQL串
     */
    public String getLimitString(String sql) {
        return new StringBuffer(sql.length() + 100).append("select * from (select t.*, ROWNUM rn from (").append(sql)
                .append(") t where ROWNUM <= (:_offset + :_limit)) tb where rn > :_offset").toString();
    }
}
