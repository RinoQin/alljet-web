/*
 * FileName: RedisKeyGenerator.java
 * Author:   v_qinyuchen
 * Date:     2016年4月11日 下午3:54:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.common.key;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CacheKeyGeneratorMethodName implements KeyGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
            sb.append(obj.toString());
        }
        System.out.println("generateKey: " + sb.toString());
        return sb.toString();
    }

}
