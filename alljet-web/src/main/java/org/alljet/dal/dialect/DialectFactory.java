/*
 * FileName: DialectFactory.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午1:45:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.dialect;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据库方言工厂类<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DialectFactory {
    /**
     * 方言Map
     */
    private Map<String, Dialect> mapDialect;

    /**
     * 获取方言Map
     * 
     * @return 方言Map
     */
    public Map<String, Dialect> getMapDialect() {
        return mapDialect;
    }

    /**
     * 设置方言Map
     * 
     * @param mapDialect 方言Map
     */
    public void setMapDialect(Map<String, Dialect> mapDialect) {
        this.mapDialect = mapDialect;
    }

    /**
     * 获取方言数据库类型
     * 
     * @param dbType 数据源类型
     * @return 数据库方言对象
     */
    public Dialect getDBDialect(String dbType) {
        if (StringUtils.isBlank(dbType)) {
            throw new RuntimeException("error.dal.002 DBType is null or empty.");
        }
        return mapDialect.get(dbType.toLowerCase());
    }
}
