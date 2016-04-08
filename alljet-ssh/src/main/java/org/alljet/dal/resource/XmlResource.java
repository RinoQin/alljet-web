/*
 * FileName: XmlResource.java
 * Author:   v_qinyuchen
 * Date:     2016年4月8日 上午11:27:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.resource;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class XmlResource {
    /** SQL模板容器 */
    private final Map<String, String> sqlContainer = new HashMap<String, String>();

    /** SQL资源模板 */
    private Resource[] resources;

    /**
     * 获取资源模板
     * 
     * @return 资源模板
     */
    public synchronized Resource[] getResources() {
        return resources;
    }

    /**
     * 设置资源模板
     * 
     * @param resources 资源模板
     */
    public synchronized void setResources(Resource[] resources) {
        this.resources = resources.clone();
    }

    /**
     * 
     * 功能描述: 解析SQL模板
     * 
     */
    protected void parseResource() {
        XmlParser.getInstance().parse(getResources(), sqlContainer);
    }

    /**
     * 
     * 功能描述: 根据SQLID获取SQLBean
     * 
     * @param sqlId SQLID
     * @return SQL模板映射
     */
    protected String getSQL(String sqlId) {
        String sql = sqlContainer.get(sqlId);
        if (sql == null || sql.isEmpty()) {
            throw new RuntimeException("error.dal.getSql sql is null.");
        }
        return sql;
    }
}
