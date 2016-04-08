/*
 * FileName: FreeMakerParser.java
 * Author:   v_qinyuchen
 * Date:     2016年4月8日 下午2:32:48
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.sql;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class FreeMakerParser {

    /**
     * 默认模板KEY
     */
    private static final String DEFAULT_TEMPLATE_KEY = "default_template_key";

    /**
     * 默认模板表达式
     */
    private static final String DEFAULT_TEMPLATE_EXPRESSION = "default_template_expression";

    /**
     * 配置器
     */
    private static final Configuration CONFIGURER = new Configuration();

    static {
        // 處理空值為空字元串
        CONFIGURER.setClassicCompatible(true);
    }

    /**
     * 配置SQL表达式缓存
     */
    private static Map<String, Template> templateCache = new HashMap<String, Template>();

    // /**
    // * 分库表达式缓存
    // */
    // private static Map<String, Template> expressionCache = new HashMap<String, Template>();

    // /**
    // * 解析处理方法
    // *
    // * @param expression 表达式
    // * @param root 根结点
    // * @return 处理结果字符串
    // */
    // public static String process(String expression, Map<String, Object> root) {
    // StringReader reader = null;
    // StringWriter out = null;
    // Template template = null;
    // /** 表达式注入模板 */
    // try {
    // if (expressionCache.get(expression) != null) {
    // template = expressionCache.get(expression);
    // }
    // if (template == null) {
    // template = createTemplate(DEFAULT_TEMPLATE_EXPRESSION, new StringReader(expression));
    // expressionCache.put(expression, template);
    // }
    // out = new StringWriter();
    // template.process(root, out);
    // return out.toString();
    // } catch (Exception e) {
    // throw new RuntimeException("error.dal.005 process freemarker expressionCache Exception.");
    // } finally {
    // if (reader != null) {
    // reader.close();
    // }
    // try {
    // if (out != null) {
    // out.close();
    // }
    // } catch (IOException e) {
    // return null;
    // }
    // }
    // }

    /**
     * 创建模板
     * 
     * @param templateKey 模板键值
     * @param reader 待处理字符流
     * @return 模板
     * @throws IOException IO异常
     */
    private static Template createTemplate(String templateKey, StringReader reader) throws IOException {
        Template template = new Template(DEFAULT_TEMPLATE_KEY, reader, CONFIGURER);
        template.setNumberFormat("#");
        return template;
    }

    /**
     * 解析处理重载方法
     * 
     * @param root root对象
     * @param sql SQL串
     * @param sqlId SQLID
     * @return StringWriter字符串
     */
    public static String process(Map<String, Object> root, String sql, String sqlId) {
        StringReader reader = null;
        StringWriter out = null;
        Template template = null;
        /** 表达式注入模板 */
        try {
            if (templateCache.get(sqlId) != null) {
                template = templateCache.get(sqlId);
            }
            if (template == null) {
                reader = new StringReader(sql);
                template = createTemplate(DEFAULT_TEMPLATE_KEY, reader);
                templateCache.put(sqlId, template);
            }
            out = new StringWriter();
            template.process(root, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("error.dal.005 process freemarker templateCache Exception.");
        } finally {
            if (reader != null) {
                reader.close();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                return null;
            }
        }
    }

}
