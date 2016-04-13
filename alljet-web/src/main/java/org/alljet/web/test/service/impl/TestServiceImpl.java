/*
 * FileName: TestServiceImpl.java
 * Author:   v_qinyuchen
 * Date:     2016年3月24日 下午5:02:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.web.test.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alljet.dal.Pagination;
import org.alljet.dal.PaginationResult;
import org.alljet.dal.client.IPaginationDalClient;
import org.alljet.web.test.entity.TestPO;
import org.alljet.web.test.service.ITestService;
import org.alljet.web.test.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service("testService")
public class TestServiceImpl implements ITestService {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(TestServiceImpl.class);

    private static final String BASE_SQL_PATH_TEST = "mysql.test.";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IPaginationDalClient dalClient;

    String insertSql1 = "insert t_test(test_name,test_value)values('5','5')";
    String insertSql2 = "insert t_test(test_name,test_value,create_time)values('5','5','2016-03-30 16:21:45')";

    @Override
    public String callbackTestReturn() {
        jdbcTemplate.execute(insertSql1);
        jdbcTemplate.execute(insertSql2);

        return "Hello!This is AllJet team.";
    }

    @Override
    public String testReturn() {
        jdbcTemplate.execute(insertSql1);
        jdbcTemplate.execute(insertSql2);

        return "Hello!This is AllJet team.";
    }

    @Override
    public String callbackTestClient() {
        // String siql1 = "insert t_test(test_name,test_value)values(:testName,:testValue)";
        String sql = BASE_SQL_PATH_TEST + "removeTestByTestName";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "page");
        paramMap.put("testValue", "page13");
        int op = dalClient.execute(sql, paramMap);
        log.debug("remove count : {}", op);
        // throw new RuntimeException("故意来一个异常试试回滚不");

        return "Hello!This is AllJet team & this is Dalclient.";
    }

    @Override
    public String testObjectClient() {
        String sql = BASE_SQL_PATH_TEST + "removeTestByTestName";
        TestPO po = new TestPO();
        po.setTestName("2");
        int op = dalClient.execute(sql, po);
        log.debug("remove count : {}", op);
        return "Hello!This is AllJet team & this is Dalclient.";
    }

    @Override
    @Cacheable("pageListTestVo")
    public PaginationResult<List<TestVO>> getTestListPage(Pagination pagination, TestVO queryVo) {
        String sql = BASE_SQL_PATH_TEST + "getTestList";
        PaginationResult<List<TestVO>> resultList = dalClient.queryForList(sql, queryVo, TestVO.class, pagination);
        System.out.println("没走缓存");
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // @Cacheable("listTestVo")
    public List<TestVO> getTestList(TestVO queryVo) {
        String sql = BASE_SQL_PATH_TEST + "getTestList";
        List<TestVO> result = dalClient.queryForList(sql, queryVo, TestVO.class, 5);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable("cacheManager")
    public List<TestVO> getTestList() {
        String sql = BASE_SQL_PATH_TEST + "getTestList";
        List<TestVO> result = dalClient.queryForList(sql, new TestVO(), TestVO.class, 4);
        return result;
    }

    @Override
    @Cacheable("cacheManager")
    public String getMessage() {
        System.out.println("没走缓存");
        return "Hello!This is AllJet team & this is Dalclient cache.";
    }

    @Override
    @Cacheable("cacheManager")
    public String getMessage2() {
        System.out.println("没走缓存");
        return "Hello!This is AllJet team & this is Dalclient & has cache.";
    }

    @Override
    @Cacheable("cacheTestVO")
    public TestPO getTestPOById(Long id) {
        TestPO vo = new TestPO();
        vo.setId(id);
        vo = dalClient.find(TestPO.class, vo);
        System.out.println("没走缓存");
        return vo;
    }

}
