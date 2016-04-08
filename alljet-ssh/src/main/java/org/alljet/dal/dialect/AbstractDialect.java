/*
 * FileName: AbstractDialect.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午1:37:22
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.dialect;

/**
 * 数据库方言操作虚类<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class AbstractDialect implements Dialect {
    /**
     * 生成SQL串，查询总记录数
     * 
     * @param sql --源SQL
     * @return SQL串
     */
    public String getCountString(String sql) {
        return new StringBuffer(sql.length() + 20).append("select count(1) from( ").append(sql).append(" ) t")
                .toString();
    }
}
