/*
 * FileName: SqlParserManager.java
 * Author:   v_qinyuchen
 * Date:     2016年4月7日 上午11:23:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * SQL解析器缓存
 * 
 * @author 12010065
 */
public class SqlParserManager {
    /**
     * 解析器缓存
     */
    private static Map<Class<? extends Object>, SqlParser> cache = new HashMap<Class<? extends Object>, SqlParser>();

    /**
     * SQL解析器装载进缓存
     * 
     * @param clazz 类对象
     * @return SQL生成对象
     */
    public static SqlParser getSqlParser(Class<? extends Object> clazz) {
        SqlParser sqlParser = cache.get(clazz);
        if (sqlParser == null) {
            sqlParser = new SqlParser(clazz);
            synchronized (cache) {
                if (cache.get(clazz) == null) {
                    cache.put(clazz, sqlParser);
                }
            }
        }
        return sqlParser;
    }

    /**
     * 获取分表路由
     * 
     * @param clazz 类对象
     * @return 分表路由
     */
    public static String getRouteTable(Class<? extends Object> clazz) {
        return getSqlParser(clazz).getRouteTable();
    }

    /**
     * 私有构造函数SqlParserManager
     */
    private SqlParserManager() {

    }

}
