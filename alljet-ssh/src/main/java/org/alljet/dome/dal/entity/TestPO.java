/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: TestPO.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午3:33:21
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dome.dal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Entity(name = "t_test")
public class TestPO {

    private Long id;

    /** 手机号码 */
    private String testName;
    /** 车架号 */
    private String testValue;
    /** 创建时间 */
    private Date createTime;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the testName
     */
    @Column(name = "test_name")
    public String getTestName() {
        return testName;
    }

    /**
     * @param testName the testName to set
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * @return the testValue
     */
    @Column(name = "test_value")
    public String getTestValue() {
        return testValue;
    }

    /**
     * @param testValue the testValue to set
     */
    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    /**
     * @return the createTime
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
