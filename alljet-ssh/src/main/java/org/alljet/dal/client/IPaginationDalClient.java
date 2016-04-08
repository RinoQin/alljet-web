/*
 * FileName: IPaginationDalClient.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午6:05:55
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.client;

import java.util.List;
import java.util.Map;

import org.alljet.dal.Pagination;
import org.alljet.dal.PaginationResult;
import org.springframework.jdbc.core.RowMapper;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IPaginationDalClient extends Dalclient {
    /**
     * 偏移量
     */
    String OFFSET = "offset";

    /**
     * 返回指定的记录数
     */
    String LIMIT = "limit";

    /**
     * 初始SQL
     */
    String ORIGSQL = "origsql";

    /**
     * 获取分页处理结果
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param pagination 分页
     * @return List类型的查询结果
     */
    PaginationResult<List<Map<String, Object>>> queryForList(String sqlId, Object param, Pagination pagination);

    /**
     * 获取分页处理结果 重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param pagination 分页
     * @return List类型的查询结果
     */
    PaginationResult<List<Map<String, Object>>> queryForList(String sqlId, Map<String, Object> paramMap,
            Pagination pagination);

    /**
     * 获取分页处理结果 重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要操作的类型
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> PaginationResult<List<T>> queryForList(String sqlId, Map<String, Object> paramMap, Class<T> requiredType,
            Pagination pagination);

    /**
     * 获取分页处理结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要操作的类型
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> PaginationResult<List<T>> queryForList(String sqlId, Object param, Class<T> requiredType, Pagination pagination);

    /**
     * 获取分页处理结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> PaginationResult<List<T>> queryForList(String sqlId, Object param, RowMapper<T> rowMapper, Pagination pagination);

    /**
     * 获取分页处理结果 重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> PaginationResult<List<T>> queryForList(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper,
            Pagination pagination);

    /**
     * 随机取
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param num 随机数
     * @return List类型的查询结果
     */
    List<Map<String, Object>> queryForList(String sqlId, Object param, int num);

    /**
     * 随机取
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param num 随机数
     * @return List类型的查询结果
     */
    List<Map<String, Object>> queryForList(String sqlId, Map<String, Object> paramMap, int num);

    /**
     * 随机取
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要操作的类型
     * @param num 随机数
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, Class<T> requiredType, int num);

    /**
     * 随机取
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要操作的类型
     * @param num 随机数
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> List<T> queryForList(String sqlId, Object param, Class<T> requiredType, int num);

    /**
     * 随机取
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param num 随机数
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> List<T> queryForList(String sqlId, Object param, RowMapper<T> rowMapper, int num);

    /**
     * 随机取
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param num 随机数
     * @param <T> 泛型对象
     * @return List类型的查询结果
     */
    <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper, int num);

}
