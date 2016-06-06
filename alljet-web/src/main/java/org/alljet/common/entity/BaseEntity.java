/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: BaseEntity.java
 * Author:   v_qinyuchen
 * Date:     2016年6月6日 上午11:32:35
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.common.entity;

import java.util.Date;

import javax.persistence.Column;
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
public class BaseEntity {

    private Long id;

    private Integer delFlag;

    private Date createTime;

    private Long createById;

    private String createByName;

    private Date updateTime;

    private Long updateById;

    private String updateByName;

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
     * @return the delFlag
     */
    public Integer getDelFlag() {
        return delFlag;
    }

    /**
     * @param delFlag the delFlag to set
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    /**
     * @return the createById
     */
    @Column(name = "create_by_id")
    public Long getCreateById() {
        return createById;
    }

    /**
     * @param createById the createById to set
     */
    public void setCreateById(Long createById) {
        this.createById = createById;
    }

    /**
     * @return the createByName
     */
    @Column(name = "create_by_name")
    public String getCreateByName() {
        return createByName;
    }

    /**
     * @param createByName the createByName to set
     */
    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    /**
     * @return the updateTime
     */
    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the updateById
     */
    @Column(name = "update_by_id")
    public Long getUpdateById() {
        return updateById;
    }

    /**
     * @param updateById the updateById to set
     */
    public void setUpdateById(Long updateById) {
        this.updateById = updateById;
    }

    /**
     * @return the updateByName
     */
    @Column(name = "update_by_name")
    public String getUpdateByName() {
        return updateByName;
    }

    /**
     * @param updateByName the updateByName to set
     */
    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

}
