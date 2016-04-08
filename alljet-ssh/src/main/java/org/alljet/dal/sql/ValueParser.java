/*
 * FileName: ValueParser.java
 * Author:   v_qinyuchen
 * Date:     2016年4月7日 上午11:17:21
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.sql;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import org.alljet.dal.constants.DalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * 
 * 对象解析<br>
 * 
 * @author 12010065
 * 
 */
public class ValueParser {

    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(ValueParser.class);

    /**
     * 对象解析器，返回Map类型的解析结果
     * 
     * @param entity 解析对象
     * @return 解析结果Map
     */
    public static Map<String, Object> parser(Object entity) {
        Map<String, Object> values = new HashMap<String, Object>();
        Method[] methods = entity.getClass().getMethods();
        /** 分表路由解析，返回对应关系 */
        for (Method method : methods) {
            if (method.isAnnotationPresent(Column.class)) {
                Column column = method.getAnnotation(Column.class);
                /** 调用Bean的方法 */
                PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(method);
                String key = descriptor.getName();
                Object value = null;
                try {
                    /** 方法函数回调 */
                    value = method.invoke(entity, new Object[] {});
                    if (value instanceof java.util.Date) {
                        value = dateFormat(column, (Date) value);
                    }
                } catch (Exception e) {
                    logger.debug("reflect error.[" + method + "]", e);
                }
                if (column.table() != null && !"".equals(column.table())) {
                    values.put(DalConstants.ROUTE_TABLE, column.table());
                }
                values.put(key, value);
            }
        }

        return values;
    }

    /**
     * 日期-时间格式化
     * 
     * @param column 列
     * @param date 日期
     * @return 格式化结果
     */
    private static Object dateFormat(Column column, Date date) {
        if (date != null && !"".equals(column.columnDefinition())) {
            SimpleDateFormat format = new SimpleDateFormat(column.columnDefinition());
            return format.format(date);
        }
        return date;
    }

    /**
     * 私有构造函数ValueParser
     */
    private ValueParser() {

    }
}
