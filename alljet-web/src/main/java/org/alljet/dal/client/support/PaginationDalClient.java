/*
 * FileName: PaginationDalClient.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午6:00:07
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.client.support;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.alljet.dal.Pagination;
import org.alljet.dal.PaginationResult;
import org.alljet.dal.client.IPaginationDalClient;
import org.alljet.dal.client.support.rowmapper.RowMapperFactory;
import org.alljet.dal.dialect.Dialect;
import org.alljet.dal.dialect.DialectFactory;
import org.alljet.dal.resource.XmlResource;
import org.alljet.dal.sql.FreeMakerParser;
import org.alljet.dal.sql.SqlParser;
import org.alljet.dal.sql.SqlParserManager;
import org.alljet.dal.sql.ValueParser;
import org.alljet.dal.util.DalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PaginationDalClient extends XmlResource implements IPaginationDalClient {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(PaginationDalClient.class);

    /** SQL失效时间 */
    private final Long sqlTimeOut = 100L;
    /**
     * 指定记录数
     */
    private final String LIMIT = "_limit";

    private String dbType;

    /**
     * 数据库方言工厂
     */
    @Autowired
    private DialectFactory dialectFactory;

    private NamedParameterJdbcTemplate template;

    // private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    public void afterPropertiesSet() throws Exception {
        parseResource();
    }

    public void setDataSource(DataSource dataSource) throws SQLException, Exception {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        // this.jdbcTemplate = new JdbcTemplate(dataSource);
        DatabaseMetaData md = dataSource.getConnection().getMetaData();
        this.dbType = md.getDatabaseProductName();
        log.info("This db type is {}", this.dbType);
        this.dataSource = dataSource;
        afterPropertiesSet();
    }

    /**
     * 执行查询，返回结果集记录数目
     * 
     * @param sqlId SQLID
     * @param paramMap 执行参数
     * @return 查询结果
     */
    @Override
    public int execute(String sqlId, Map<String, Object> paramMap) {

        // Integer count = template.execute(sqlId, paramMap, new PreparedStatementCallback<Integer>() {
        //
        // public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
        // ps.execute();
        // return ps.getUpdateCount();
        // }
        //
        // });
        // if (count != null && count > 0)
        // return count;
        // else
        // return 0;
        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        /** 调用JDBCTemplate实现更新，返回更新成功的记录数 */
        int result = template.update(sql, DalUtils.mapIfNull(paramMap));

        return result;
    }

    /**
     * 执行查询，execute重载方法
     * 
     * @param sqlId SQLID
     * @param param 执行参数
     * @return 查询结果
     */
    @Override
    public int execute(String sqlId, Object param) {
        // SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(param);
        // Integer count = template.execute(sqlId, sqlParameterSource, new PreparedStatementCallback<Integer>() {
        //
        // public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
        // ps.execute();
        // return ps.getUpdateCount();
        // }
        //
        // });
        // if (count != null && count > 0)
        // return count;
        // else
        // return 0;
        return this.execute(sqlId, DalUtils.convertToMap(param));
    }

    /**
     * 查询并返回分页结果，pagesize为负数时，返回所有记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param pagination 分页
     * @return 分页查询结果
     */
    @Override
    public PaginationResult<List<Map<String, Object>>> queryForList(String sqlId, Map<String, Object> paramMap,
            Pagination pagination) {

        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        /** 装配分页信息 */
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        /** 当pagesize为负数时 ,查询该表中的所有记录 */
        List<Map<String, Object>> list = null;
        if (pagination.getPagesize() < 0) {
            list = queryForList(sqlId, paramMap);
            pagination.setTotalRows(list.size());
        } else {
            paramMap.put(LIMIT, pagination.getPagesize());
            paramMap.put("_offset", pagination.getFirstRowIndex());
            this.configurePagination(template, sql, paramMap, pagination, this.dbType);
            list = template.queryForList(generatePaginationSql(sql, this.dbType), paramMap);
        }
        /** 执行分页查询 */
        return new PaginationResult<List<Map<String, Object>>>(list, pagination);
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param pagination 分页
     * @return 分页查询结果
     */
    @Override
    public PaginationResult<List<Map<String, Object>>> queryForList(String sqlId, Object param, Pagination pagination) {
        return queryForList(sqlId, DalUtils.convertToMap(param), pagination);
    }

    /**
     * 查询并返回分页结果
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return 分页查询结果
     */
    @Override
    public <T> PaginationResult<List<T>> queryForList(String sqlId, Map<String, Object> paramMap,
            RowMapper<T> rowMapper, Pagination pagination) {

        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        /** 装配分页信息 */
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        List<T> list = null;
        /** 当pagesize为负数时 ,查询该表中的所有记录 */
        if (pagination.getPagesize() < 0) {
            list = queryForList(sqlId, paramMap, rowMapper);
            pagination.setTotalRows(list.size());
        } else {
            paramMap.put(LIMIT, pagination.getPagesize());
            paramMap.put("_offset", pagination.getFirstRowIndex());
            this.configurePagination(template, sql, paramMap, pagination, this.dbType);
            list = template.query(generatePaginationSql(sql, this.dbType), paramMap, rowMapper);
        }
        /** 执行分页查询 */
        return new PaginationResult<List<T>>(list, pagination);
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return 分页查询结果
     */
    @Override
    public <T> PaginationResult<List<T>> queryForList(String sqlId, Object param, RowMapper<T> rowMapper,
            Pagination pagination) {
        return queryForList(sqlId, DalUtils.convertToMap(param), rowMapper, pagination);
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要操作的类型
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return 分页查询结果
     */
    @Override
    public <T> PaginationResult<List<T>> queryForList(String sqlId, Map<String, Object> paramMap,
            Class<T> requiredType, Pagination pagination) {
        return this.queryForList(sqlId, paramMap, new RowMapperFactory<T>(requiredType).getRowMapper(), pagination);
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要操作的类型
     * @param pagination 分页
     * @param <T> 泛型对象
     * @return 分页查询结果
     */
    @Override
    public <T> PaginationResult<List<T>> queryForList(String sqlId, Object param, Class<T> requiredType,
            Pagination pagination) {
        return queryForList(sqlId, DalUtils.convertToMap(param), requiredType, pagination);
    }

    /**
     * 数据持久化操作
     * 
     * @param entity 实体对象
     * @return 持久化操作成功记录id
     */
    @Override
    public Number persist(Object entity) {
        return persist(entity, Number.class);
    }

    /**
     * 数据持久化
     * 
     * @param entity 数据实体
     * @param requiredType 需要处理的类型
     * @param <T> 泛型对象
     * @return 持久化操作成功记录id(支持Number，Long)
     */
    @Override
    public <T> T persist(Object entity, Class<T> requiredType) {
        SqlParser sqlParser = SqlParserManager.getSqlParser(entity.getClass());
        String insertSQL = sqlParser.getInsert();
        Map<String, Object> paramMap = ValueParser.parser(entity);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        logMessage("persist", insertSQL, paramMap);
        long beginDate = System.currentTimeMillis();
        /** 渲染后获取JDBC模板 */
        template.update(insertSQL, new MapSqlParameterSource(paramMap), keyHolder,
                new String[] { sqlParser.getIdName() });
        Object key = paramMap.get(sqlParser.getId());
        if (key == null || (key instanceof Number && ((Number) key).doubleValue() == 0.0d)) {
            return (T) keyHolder.getKey();
        }
        logMessage("persist", insertSQL, paramMap, System.currentTimeMillis() - beginDate);
        return (T) key;
    }

    /**
     * 数据合并与更新
     * 
     * @param entity 更新的数据实体
     * @return 数据更新后的结果
     */
    @Override
    public int merge(Object entity) {
        String updateSql = SqlParserManager.getSqlParser(entity.getClass()).getUpdate();
        Map<String, Object> paramMap = ValueParser.parser(entity);
        logMessage("merge", updateSql, paramMap);
        long beginDate = System.currentTimeMillis();
        /** 调用JDBCTemplate实现更新，返回更新成功的记录数 */
        int result = template.update(updateSql, paramMap);
        logMessage("merge", updateSql, paramMap, System.currentTimeMillis() - beginDate);

        return result;
    }

    /**
     * 动态更新
     * 
     * @param entity 更新的数据实体
     * @return 返回更新的记录数目
     */
    @Override
    public int dynamicMerge(Object entity) {
        Map<String, Object> paramMap = ValueParser.parser(entity);
        String updateSql = SqlParserManager.getSqlParser(entity.getClass()).getDynamicUpdate(paramMap);
        logMessage("dynamicMerge", updateSql, paramMap);
        long beginDate = System.currentTimeMillis();
        /** 调用JDBCTemplate实现更新，返回更新成功的记录数 */
        int result = template.update(updateSql, paramMap);
        logMessage("dynamicMerge", updateSql, paramMap, System.currentTimeMillis() - beginDate);

        return result;
    }

    /**
     * 数据删除
     * 
     * @param entity 删除的数据实体
     * @return 返回删除的记录数目
     */
    @Override
    public int remove(Object entity) {
        String removeSql = SqlParserManager.getSqlParser(entity.getClass()).getDelete();
        Map<String, Object> paramMap = ValueParser.parser(entity);
        logMessage("remove", removeSql, paramMap);
        long beginDate = System.currentTimeMillis();
        /** 调用JDBCTemplate实现更新，返回更新成功的记录数 */
        int result = template.update(removeSql, paramMap);
        logMessage("remove", removeSql, paramMap, System.currentTimeMillis() - beginDate);

        return result;
    }

    /**
     * 根据传入实体类查询单个记录
     * 
     * @param entityClass 实体类
     * @param entity 查询对象
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> T find(Class<T> entityClass, Object entity) {
        String findSql = SqlParserManager.getSqlParser(entity.getClass()).getSelect();
        Map<String, Object> paramMap = ValueParser.parser(entity);
        logMessage("find", findSql, paramMap);
        long beginDate = System.currentTimeMillis();
        /** 调用JDBCTemplate实现单记录查询，并返回查询结果 */
        List<T> resultList = template.query(findSql, paramMap, new RowMapperFactory<T>(entityClass).getRowMapper());
        logMessage("find", findSql, paramMap, System.currentTimeMillis() - beginDate);

        return singleResult(resultList);
    }

    /**
     * 批量更新
     * 
     * @param sqlId SQLID
     * @param batchValues 需要批处理的集合
     * @return 批处理成功记录数
     */
    @Override
    public int[] batchUpdate(String sqlId, Map<String, Object>[] batchValues) {
        /** FreeMarker模板渲染 */
        String sql = getSQL(sqlId);
        int[] result;
        /** 调用JDBCTemplate批量更新，返回更新成功的记录数 */
        result = template.batchUpdate(sql, batchValues);

        return result;
    }

    /**
     * 调存储过程
     * 
     * @param sqlId SQLID
     * @param paramMap 执行参数
     * @param sqlParameters sqlcommand参数的对象
     * @return 存储过程执行结果
     */
    @Override
    public Map<String, Object> call(String sqlId, Map<String, Object> paramMap, List<SqlParameter> sqlParameters) {
        Map<String, Object> paramMapTmp = DalUtils.mapIfNull(paramMap);
        String sql = getSQL(sqlId);
        /** 调用存储过程 */
        GenericStoredProcedure storedProcedure = new GenericStoredProcedure();
        /** 放入数据源 */
        storedProcedure.setDataSource(dataSource);
        /** 放入SQL */
        storedProcedure.setSql(sql);
        for (SqlParameter sqlParameter : sqlParameters) {
            storedProcedure.declareParameter(sqlParameter);
        }
        return storedProcedure.execute(paramMapTmp);
    }

    /**
     * 查询单个记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要处理的类型
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> T queryForObject(String sqlId, Map<String, Object> paramMap, Class<T> requiredType) {
        return this.queryForObject(sqlId, paramMap, new RowMapperFactory<T>(requiredType).getRowMapper());
    }

    /**
     * 查询单个记录
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要处理的类型
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> T queryForObject(String sqlId, Object param, Class<T> requiredType) {
        return this.queryForObject(sqlId, DalUtils.convertToMap(param), requiredType);
    }

    /**
     * 根据sqlId查询单条记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> T queryForObject(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper) {
        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        /** 调用JDBCTemplate实现查询，并返回查询结果 */
        List<T> resultList = template.query(sql, paramMap, rowMapper);

        return singleResult(resultList);
    }

    /**
     * queryForObject重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> T queryForObject(String sqlId, Object param, RowMapper<T> rowMapper) {
        return this.queryForObject(sqlId, DalUtils.convertToMap(param), rowMapper);
    }

    /**
     * 查询并返回映射集
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryForMap(String sqlId, Map<String, Object> paramMap) {
        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        /** 调用JDBCTemplate实现查询，并返回查询结果 */
        Map<String, Object> map = singleResult(template.queryForList(sql, paramMap));
        return map;
    }

    /**
     * queryForMap重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryForMap(String sqlId, Object param) {
        return this.queryForMap(sqlId, DalUtils.convertToMap(param));
    }

    /**
     * queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要处理的类型
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, Class<T> requiredType) {
        return this.queryForList(sqlId, paramMap, new RowMapperFactory<T>(requiredType).getRowMapper());
    }

    /**
     * queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要处理的类型
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Object param, Class<T> requiredType) {
        return queryForList(sqlId, DalUtils.convertToMap(param), requiredType);
    }

    /**
     * 根据sqlId查询多条记录，返回List<Map<String, Object>>型结果集，queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> queryForList(String sqlId, Map<String, Object> paramMap) {
        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        long beginDate = System.currentTimeMillis();
        /** 调用JDBCTemplate实现多记录查询，并返回查询结果 */
        List<Map<String, Object>> list = template.queryForList(sql, DalUtils.mapIfNull(paramMap));
        return list;
    }

    /**
     * queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> queryForList(String sqlId, Object param) {
        return queryForList(sqlId, DalUtils.convertToMap(param));
    }

    /**
     * 根据sqlId查询多条记录，返回list型结果集
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper) {
        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        /** 调用JDBCTemplate实现查询，并返回查询结果 */
        List<T> list = template.query(sql, DalUtils.mapIfNull(paramMap), rowMapper);

        return list;
    }

    /**
     * 查询并返回结果集，queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Object param, RowMapper<T> rowMapper) {
        return queryForList(sqlId, DalUtils.convertToMap(param), rowMapper);
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param num 随机数
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> queryForList(String sqlId, Object param, int num) {
        return queryForList(sqlId, DalUtils.convertToMap(param), num);
    }

    /**
     * 查询并返回结果集，num为负数时，返回所有记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param num 随机数
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> queryForList(String sqlId, Map<String, Object> paramMap, int num) {
        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        List<Map<String, Object>> list;
        if (num < 0) {
            list = queryForList(sqlId, paramMap);
        } else {
            paramMap.put(LIMIT, num);
            list = template.queryForList(generatePaginationSqlForRandom(sql, this.dbType), paramMap);
        }
        /** 执行分页查询 */
        return list;
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要操作的类型
     * @param num 随机数
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, Class<T> requiredType, int num) {
        return queryForList(sqlId, paramMap, new RowMapperFactory<T>(requiredType).getRowMapper(), num);
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要操作的类型
     * @param num 随机数
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Object param, Class<T> requiredType, int num) {
        return queryForList(sqlId, DalUtils.convertToMap(param), requiredType, num);
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param num 随机数
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Object param, RowMapper<T> rowMapper, int num) {
        return queryForList(sqlId, DalUtils.convertToMap(param), rowMapper, num);
    }

    /**
     * 查询并返回结果集
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param num 随机数
     * @param <T> 泛型对象
     * @return 查询结果
     */
    @Override
    public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper, int num) {
        String sql = FreeMakerParser.process(paramMap, getSQL(sqlId), sqlId);
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        List<T> list;
        if (num < 0) {
            list = queryForList(sqlId, paramMap, rowMapper);
        } else {
            paramMap.put(LIMIT, num);
            list = template.query(generatePaginationSqlForRandom(sql, this.dbType), paramMap, rowMapper);
        }
        return list;
    }

    /**
     * 装配分页信息
     * 
     * @param template 模板
     * @param sql SQL语句
     * @param paramMap 查询参数
     * @param pagination 分页
     * @param dbType 数据源类型
     */
    private void configurePagination(NamedParameterJdbcTemplate template, String sql, Map<String, Object> paramMap,
            Pagination pagination, String dbType) {
        if (pagination.getTotalRows() == 0 || pagination.getTotalRows() == -1) {
            /** 获取数据总数 */
            int totalRows = template.queryForObject(generateCountSql(sql, dbType), DalUtils.mapIfNull(paramMap),
                    Integer.class);
            pagination.setTotalRows(totalRows);
        }
    }

    private void configurePaginationTotalrows(NamedParameterJdbcTemplate template, String sql,
            Map<String, Object> paramMap, Pagination pagination, String dbType) {
        if (pagination.getTotalRows() == 0 || pagination.getTotalRows() == -1) {
            /** 获取数据总数 */
            int totalRows = template.queryForObject(sql, DalUtils.mapIfNull(paramMap), Integer.class);
            pagination.setTotalRows(totalRows);
        }
    }

    /**
     * 查询指定SQL的总记录数
     * 
     * @param sql SQL语句
     * @param dbType 数据源类型
     * @return 统计SQL串
     */
    private String generateCountSql(String sql, String dbType) {
        Dialect dialect = dialectFactory.getDBDialect(dbType);
        if (dialect == null) {
            throw new RuntimeException("error.dal.002");
        }
        return dialect.getCountString(sql);
    }

    /**
     * 生成分页sql，查询指定位置、指定行数的记录
     * 
     * @param sql SQL语句
     * @param dbType 数据源类型
     * @return 分页SQL串
     */
    private String generatePaginationSql(String sql, String dbType) {
        Dialect dialect = dialectFactory.getDBDialect(dbType);
        if (dialect == null) {
            throw new RuntimeException("error.dal.002");
        }
        return dialect.getLimitString(sql);
    }

    /**
     * 生成分页sql，查询前几行记录
     * 
     * @param sql SQL语句
     * @param dbType 数据源类型
     * @return 分页SQL串
     */
    private String generatePaginationSqlForRandom(String sql, String dbType) {
        Dialect dialect = dialectFactory.getDBDialect(dbType);
        if (dialect == null) {
            throw new RuntimeException("error.dal.002");
        }
        return dialect.getLimitStringForRandom(sql);
    }

    /**
     * 返回结果集中的第一条记录
     * 
     * @param resultList 结果集
     * @param <T> 泛型对象
     * @return 结果集中的第一条记录
     */
    private <T> T singleResult(List<T> resultList) {
        if (resultList != null) {
            int size = resultList.size();
            if (size > 0) {
                return resultList.get(0);
            }
            if (size == 0) {
                return null;
            }
        }
        return null;
    }

    /**
     * 打印sql的执行信息
     * 
     * @param method 方法名
     * @param sql SQL串
     * @param object 对象
     */
    private void logMessage(String method, String sql, Object object) {
        if (log.isDebugEnabled()) {
            log.debug(method + " method SQL: [" + sql + "]");
            log.debug(method + " method parameter:" + object);
        }
    }

    /**
     * 打印超时sql的执行时间
     * 
     * @param method 方法名
     * @param sql SQL串
     * @param object 对象
     * @param executeTime 执行时间
     */
    private void logMessage(String method, String sql, Object object, long executeTime) {
        /** 打印超时sql的执行时间 */
        if (executeTime >= sqlTimeOut) {
            if (log.isDebugEnabled()) {
                log.error(method + " method executeTime:" + executeTime + "ms");
            }
        }
    }
}
