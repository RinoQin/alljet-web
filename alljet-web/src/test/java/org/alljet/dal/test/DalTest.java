/*
 * FileName: DalTest.java
 * Author:   v_qinyuchen
 * Date:     2016年4月7日 下午2:15:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alljet.dal.Pagination;
import org.alljet.dal.PaginationResult;
import org.alljet.dal.client.IPaginationDalClient;
import org.alljet.dome.dal.entity.TestPO;
import org.alljet.dome.vo.TestVO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring/applicationContext-dal.xml" })
public class DalTest {

    @Autowired
    private IPaginationDalClient dalClient;

    /**
     * 执行查询，返回结果集记录数目
     * 
     * @param sqlId SQLID
     * @param paramMap 执行参数
     * @return 查询结果
     */
    // @Test
    public void execute() {

        String sqlId = "mysql.test.insertTest";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "测试类execute");
        paramMap.put("testValue", "测试类execute");
        System.out.println(dalClient.execute(sqlId, paramMap));
    }

    /**
     * 执行查询，execute重载方法
     * 
     * @param sqlId SQLID
     * @param param 执行参数
     * @return 查询结果
     */
    // @Test
    public void executeObj() {
        String sqlId = "mysql.test.removeTestByTestName";
        TestVO param = new TestVO();
        param.setTestName("测试类execute");
        System.out.println(dalClient.execute(sqlId, param));
    }

    /**
     * 查询并返回分页结果，pagesize为负数时，返回所有记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param pagination 分页
     * @return 分页查询结果
     */
    // @Test
    public void queryForListPaginationResultListMap() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "page");
        Pagination pagination = new Pagination(10, 1);
        PaginationResult<List<Map<String, Object>>> result = dalClient.queryForList(sqlId, paramMap, pagination);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param pagination 分页
     * @return 分页查询结果
     */
    // @Test
    public void queryForListPaginationResultListMapObj() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("page");
        Pagination pagination = new Pagination(5, 1);
        PaginationResult<List<Map<String, Object>>> result = dalClient.queryForList(sqlId, param, pagination);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回分页结果
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param pagination 分页
     * @param <TestVO> 泛型对象
     * @return 分页查询结果
     */
    // @Test
    public void queryForListPaginationResultListMapTestVO() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "page");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum);
                vo.setId(rs.getLong("id"));
                vo.setTestName(rs.getString("test_name"));
                vo.setTestValue(rs.getString("test_value"));
                return vo;
            }
        };
        Pagination pagination = new Pagination(10, 1);
        PaginationResult<List<TestVO>> result = dalClient.queryForList(sqlId, paramMap, rowMapper, pagination);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param pagination 分页
     * @param <TestVO> 泛型对象
     * @return 分页查询结果
     */
    // @Test
    public void queryForListPaginationResultListObjTestVO() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("page");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum);
                vo.setId(rs.getLong("id"));
                vo.setTestName(rs.getString("test_name"));
                vo.setTestValue(rs.getString("test_value"));
                vo.setCreateTime(rs.getTimestamp("create_time"));
                return vo;
            }
        };
        Pagination pagination = new Pagination(5, 1);
        PaginationResult<List<TestVO>> result = dalClient.queryForList(sqlId, param, rowMapper, pagination);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要操作的类型
     * @param pagination 分页
     * @param <TestVO> 泛型对象
     * @return 分页查询结果
     */
    // @Test
    public void queryForListPaginationResultListMapTestVOClazz() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "page");
        Class<TestVO> requiredType = TestVO.class;
        Pagination pagination = new Pagination(10, 2);
        PaginationResult<List<TestVO>> result = dalClient.queryForList(sqlId, paramMap, requiredType, pagination);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回分页结果 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要操作的类型
     * @param pagination 分页
     * @param <TestVO> 泛型对象
     * @return 分页查询结果
     */
    // @Test
    public void queryForListPaginationResultListObjTestVOClazz() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("page");
        Class<TestVO> requiredType = TestVO.class;
        Pagination pagination = new Pagination(5, 2);
        PaginationResult<List<TestVO>> result = dalClient.queryForList(sqlId, param, requiredType, pagination);
        System.out.println(gson.toJson(result));
    }

    /**
     * 数据持久化操作
     * 
     * @param entity 实体对象
     * @return 持久化操作成功记录id
     */
    // @Test
    public void persist() {
        TestPO entity = new TestPO();
        entity.setTestName("persistNum");
        entity.setTestValue("persistNum");
        entity.setCreateTime(new Date());
        Number result = dalClient.persist(entity);
        System.out.println(result.longValue());
    }

    /**
     * 数据持久化
     * 
     * @param entity 数据实体
     * @param requiredType 需要处理的类型
     * @param <TestVO> 泛型对象
     * @return 持久化操作成功记录id
     */
    // @Test
    public void persistLong() {
        TestPO entity = new TestPO();
        entity.setTestName("persistObj");
        entity.setTestValue("persistObj");
        entity.setCreateTime(new Date());
        Class<Long> requiredType = Long.class;
        Long result = dalClient.persist(entity, requiredType);
        System.out.println(result.intValue());
    }

    /**
     * 数据覆盖与更新
     * 
     * @param entity 更新的数据实体
     * @return 数据更新后的结果
     */
    // @Test
    public void merge() {
        TestPO entity = new TestPO();
        entity.setId(33l);
        entity.setTestName("merge");
        entity.setTestValue("merge");
        System.out.println(dalClient.merge(entity));
    }

    /**
     * 动态更新
     * 
     * @param entity 更新的数据实体
     * @return 返回更新的记录数目
     */
    // @Test
    public void dynamicMerge() {
        TestPO entity = new TestPO();
        entity.setId(32l);
        entity.setTestName("dynamicMerge");
        entity.setTestValue("[dynamicMerge]");
        System.out.println(dalClient.dynamicMerge(entity));
    }

    /**
     * 数据删除
     * 
     * @param entity 删除的数据实体
     * @return 返回删除的记录数目
     */
    // @Test
    public void remove() {
        TestPO entity = new TestPO();
        entity.setId(6l);
        System.out.println(dalClient.remove(entity));
    }

    /**
     * 根据传入实体类查询单个记录
     * 
     * @param entityClass 实体类
     * @param entity 查询对象
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void find() {
        Class<TestVO> entityClass = TestVO.class;
        TestPO entity = new TestPO();
        entity.setId(34l);
        TestVO result = dalClient.find(entityClass, entity);
        Gson gson = new Gson();
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询单个记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要处理的类型
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForObjectMapObjClazz() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testValue", "page11");
        Class<TestVO> requiredType = TestVO.class;

        TestVO result = dalClient.queryForObject(sqlId, paramMap, requiredType);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询单个记录
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要处理的类型
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForObjectObjObjClazz() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestValue("page10");
        Class<TestVO> requiredType = TestVO.class;

        TestVO result = dalClient.queryForObject(sqlId, param, requiredType);
        System.out.println(gson.toJson(result));
    }

    /**
     * 根据sqlId查询单条记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForObjectMapObjRow() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testValue", "page9");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum + 1);
                vo.setId(rs.getLong("id"));
                vo.setTestName(rs.getString("test_name"));
                vo.setTestValue(rs.getString("test_value"));
                vo.setCreateTime(rs.getTimestamp("create_time"));
                return vo;
            }
        };
        TestVO result = dalClient.queryForObject(sqlId, paramMap, rowMapper);
        System.out.println(gson.toJson(result));
    }

    /**
     * queryForObject重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForObjectObjObjRow() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestValue("page8");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum + 2);
                vo.setId(rs.getLong("id"));
                vo.setTestName(rs.getString("test_name"));
                vo.setTestValue(rs.getString("test_value"));
                vo.setCreateTime(rs.getTimestamp("create_time"));
                return vo;
            }
        };
        TestVO result = dalClient.queryForObject(sqlId, param, rowMapper);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回映射集
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @return 查询结果
     */
    // @Test
    public void queryForMapMap() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testValue", "page7");

        Map<String, Object> result = dalClient.queryForMap(sqlId, paramMap);
        System.out.println(gson.toJson(result));
    }

    /**
     * queryForMap重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @return 查询结果
     */
    // @Test
    public void queryForMapObj() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestValue("page6");
        Map<String, Object> result = dalClient.queryForMap(sqlId, param);
        System.out.println(gson.toJson(result));
    }

    /**
     * queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要处理的类型
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListMapClazz() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "3");
        Class<TestVO> requiredType = TestVO.class;
        List<TestVO> result = dalClient.queryForList(sqlId, paramMap, requiredType);
        System.out.println(gson.toJson(result));
    }

    /**
     * queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要处理的类型
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListObjClazz() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("page");
        Class<TestVO> requiredType = TestVO.class;
        List<TestVO> result = dalClient.queryForList(sqlId, param, requiredType);
        System.out.println(gson.toJson(result));
    }

    /**
     * 根据sqlId查询多条记录，返回List<Map<String, Object>>型结果集，queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @return 查询结果
     */
    // @Test
    public void queryForListMapMap() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "4");
        List<Map<String, Object>> result = dalClient.queryForList(sqlId, paramMap);
        System.out.println(gson.toJson(result));
    }

    /**
     * queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @return 查询结果
     */
    // @Test
    public void queryForListMapObj() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("5");

        List<Map<String, Object>> result = dalClient.queryForList(sqlId, param);
        System.out.println(gson.toJson(result));
    }

    /**
     * 根据sqlId查询多条记录，返回list型结果集
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListObjMapRow() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "6");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum + 3);
                vo.setId(rs.getLong("id"));
                vo.setTestName(rs.getString("test_name"));
                vo.setTestValue(rs.getString("test_value"));
                vo.setCreateTime(rs.getTimestamp("create_time"));
                return vo;
            }
        };
        List<TestVO> result = dalClient.queryForList(sqlId, paramMap, rowMapper);
        System.out.println(gson.toJson(result));

    }

    /**
     * 查询并返回结果集，queryForList重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListObjObjRow() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestValue("3");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum + 4);
                vo.setId(rs.getLong("id"));
                vo.setTestName(rs.getString("test_name"));
                vo.setTestValue(rs.getString("test_value"));
                vo.setCreateTime(rs.getTimestamp("create_time"));
                return vo;
            }
        };
        List<TestVO> result = dalClient.queryForList(sqlId, param, rowMapper);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param num 随机数
     * @return 查询结果
     */
    // @Test
    public void queryForListMapObjRand() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("page");
        List<Map<String, Object>> result = dalClient.queryForList(sqlId, param, 6);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回结果集，num为负数时，返回所有记录
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param num 随机数
     * @return 查询结果
     */
    // @Test
    public void queryForListMapMapRand() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "page");
        List<Map<String, Object>> result = dalClient.queryForList(sqlId, paramMap, 4);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param requiredType 需要操作的类型
     * @param num 随机数
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListObjMapClazzRand() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "page");
        Class<TestVO> requiredType = TestVO.class;
        List<TestVO> result = dalClient.queryForList(sqlId, paramMap, requiredType, 5);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param requiredType 需要操作的类型
     * @param num 随机数
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListObjObjClazzRand() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("page");
        Class<TestVO> requiredType = TestVO.class;
        List<TestVO> result = dalClient.queryForList(sqlId, param, requiredType, 3);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回结果集 重载方法
     * 
     * @param sqlId SQLID
     * @param param 查询参数
     * @param rowMapper 翻页处理规则
     * @param num 随机数
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListListObjRowRand() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        TestVO param = new TestVO();
        param.setTestName("page");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum + 10);
                vo.setId(rs.getLong("id"));
                vo.setTestName("页面" + rowNum);
                vo.setTestValue(rs.getString("test_value"));
                vo.setCreateTime(rs.getTimestamp("create_time"));
                return vo;
            }
        };
        List<TestVO> result = dalClient.queryForList(sqlId, param, rowMapper, 7);
        System.out.println(gson.toJson(result));
    }

    /**
     * 查询并返回结果集
     * 
     * @param sqlId SQLID
     * @param paramMap 查询参数
     * @param rowMapper 翻页处理规则
     * @param num 随机数
     * @param <TestVO> 泛型对象
     * @return 查询结果
     */
    // @Test
    public void queryForListListMapRowRand() {
        Gson gson = new Gson();
        String sqlId = "mysql.test.getTestList";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "page");
        RowMapper<TestVO> rowMapper = new RowMapper<TestVO>() {

            @Override
            public TestVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                TestVO vo = new TestVO();
                vo.setIndex(rowNum + 20);
                vo.setId(rs.getLong("id"));
                vo.setTestName("页面" + rowNum);
                vo.setTestValue(rs.getString("test_value"));
                vo.setCreateTime(rs.getTimestamp("create_time"));
                return vo;
            }
        };
        List<TestVO> result = dalClient.queryForList(sqlId, paramMap, rowMapper, 5);
        System.out.println(gson.toJson(result));
    }

    /**
     * 批量更新
     * 
     * @param sqlId SQLID
     * @param batchValues 需要批处理的集合
     * @return 批处理成功记录数
     */
    // @Test
    public void batchUpdate() {
        String sqlId = "mysql.test.updateTestValueById";
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("testValue", "batchUpdate11");
        map1.put("id", 35);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("testValue", "batchUpdate22");
        map2.put("id", 36);
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("testValue", "batchUpdate33");
        map3.put("id", 37);
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("testValue", "batchUpdate44");
        map4.put("id", 38);

        Map<String, Object> batchValues[] = (Map<String, Object>[]) new HashMap<?, ?>[4];
        batchValues[0] = map1;
        batchValues[1] = map2;
        batchValues[2] = map3;
        batchValues[3] = map4;
        int[] result = dalClient.batchUpdate(sqlId, batchValues);
        System.out.println(Arrays.toString(result));
    }

    /**
     * 调存储过程
     * 
     * @param sqlId SQLID
     * @param paramMap 执行参数
     * @param sqlParameters sqlcommand参数的对象
     * @return 存储过程执行结果
     */
    // @Test
    public void call() {
        Gson gson = new Gson();
        String sqlId = "mysql.testPro.callProTest1";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", "李四");
        paramMap.put("value", "is sb +");
        List<SqlParameter> sqlParameters = new ArrayList<SqlParameter>();
        sqlParameters.add(new SqlParameter("name", Types.VARCHAR));
        sqlParameters.add(new SqlParameter("value", Types.VARCHAR));
        Map<String, Object> result = dalClient.call(sqlId, paramMap, sqlParameters);
        System.out.println(gson.toJson(result));
    }

}
