/*
 * FileName: TestServiceImpl.java
 * Author:   v_qinyuchen
 * Date:     2016年3月24日 下午5:02:44
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dome.service.test.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alljet.dal.Pagination;
import org.alljet.dal.PaginationResult;
import org.alljet.dal.client.IPaginationDalClient;
import org.alljet.dome.dal.entity.TestPO;
import org.alljet.dome.service.test.ITestService;
import org.alljet.dome.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        String sql = "delete from t_test where test_name=:testName";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("testName", "1");
        paramMap.put("testValue", "1");
        int op = dalClient.execute(sql, paramMap);

        // int op = dalClient.execute(sql, paramMap);
        log.debug("remove count : {}", op);
        return "Hello!This is AllJet team & this is Dalclient.";
    }

    @Override
    public String testObjectClient() {
        String sql = "delete from t_test where test_name = :testName";
        TestPO po = new TestPO();
        po.setTestName("2");
        int op = dalClient.execute(sql, po);
        log.debug("remove count : {}", op);
        return "Hello!This is AllJet team & this is Dalclient.";
    }

    @Override
    public PaginationResult<List<TestVO>> getTestListPage(Pagination pagination, TestVO queryVo) {
        String sql = "select id,test_name,test_value,create_time from t_test";
        PaginationResult<List<TestVO>> resultList = dalClient.queryForList(sql, queryVo, TestVO.class, pagination);
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TestVO> getTestList(TestVO queryVo) {
        String sql = "select id,test_name,test_value,create_time from t_test";
        List<TestVO> result = dalClient.queryForList(sql, queryVo, TestVO.class, 5);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TestVO> getTestList() {
        String sql = "select id,test_name,test_value,create_time from t_test";
        List<TestVO> result = dalClient.queryForList(sql, new TestVO(), TestVO.class, 5);
        return result;
    }
}
