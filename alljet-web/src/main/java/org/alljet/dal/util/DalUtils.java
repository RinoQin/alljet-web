/*
 * FileName: DalUtils.java
 * Author:   v_qinyuchen
 * Date:     2016年3月31日 上午9:47:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DalUtils {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(DalUtils.class);

    /**
     * 把参数转成对象数组
     * 
     * @param parameter 参数
     * @return 对象数组
     */
    public static Object[] convertToObjectArray(Object parameter) {
        Object[] retObject = null;
        if (parameter instanceof Object[]) {
            retObject = (Object[]) parameter;
        } else {
            retObject = new Object[] { parameter };
        }
        return retObject;
    }

    /**
     * 把对象转成Map集合，分表路由使用
     * 
     * @param arg 参数
     * @return Map集合
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertToMap(Object arg) {
        if (arg instanceof Map) {
            return (Map<String, Object>) arg;
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();
        /** 将实体Bean转为Map，设置分表路由 */
        try {
            returnMap = convertBeanToMap(arg);
            // setRouteTable(arg, returnMap);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return returnMap;
    }

    // /**
    // * 设置分表路由
    // *
    // * @param entity 实体类型
    // * @param paramMap 分表参数
    // */
    // public static void setRouteTable(Object entity, Map<String, Object> paramMap) {
    // String routeTable = SqlParserManager.getRouteTable(entity.getClass());
    // if (!StringUtils.isBlank(routeTable)) {
    // paramMap.put(DalConstants.ROUTE_TABLE, routeTable);
    // }
    // }

    /**
     * 将实体Bean转换成Map集合
     * 
     * @param bean 对象
     * @return Map集合
     * @throws Exception 异常
     */
    public static Map<String, Object> convertBeanToMap(Object bean) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        PropertyUtilsBean propUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
        /** 通过传入的对象类参数值，读取其属性数组 */
        PropertyDescriptor[] descriptors = propUtilsBean.getPropertyDescriptors(bean);
        /** 遍历属性数组，生成Map */
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            if ("class".equals(name)) {
                continue;
            }
            /** 获取属性的Method对象 */
            Method method = propUtilsBean.getReadMethod(descriptors[i]);
            /** 若其不为空则执行invoke调用 */
            if (method != null) {
                Object value = method.invoke(bean);
                if (value != null) {
                    map.put(name, value);
                }
            }
        }
        return map;
    }

    //
    /**
     * Map集合为空则新建
     * 
     * @param map Map集合
     * @return 非空Map集合
     */
    public static Map<String, Object> mapIfNull(Map<String, Object> map) {
        if (map == null) {
            return new HashMap<String, Object>();
        }
        return map;
    }

    /**
     * 设置对象属性
     * 
     * @param targetObject 目标对象
     * @param propertyName 对象属性名
     * @param propertyValue 对象属性值
     */
    public static void setProperty(Object targetObject, String propertyName, Object propertyValue) {
        try {
            Field field = targetObject.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(targetObject, propertyValue);
        } catch (SecurityException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage(), e);
        } catch (NoSuchFieldException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
