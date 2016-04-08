/*
 * FileName: PaginationResult.java
 * Author:   v_qinyuchen
 * Date:     2016年3月30日 下午4:57:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PaginationResult<R> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2361929152482573703L;

    /**
     * 结果集
     */
    private R r;

    /**
     * 分页信息
     */
    private Pagination pagination;

    /**
     * 构造方法
     * 
     * @param r 结果集
     * @param pagination 分页配置参数
     */
    public PaginationResult(R r, Pagination pagination) {
        super();
        this.r = r;
        this.pagination = pagination;
    }

    public PaginationResult() {

    }

    /**
     * 返回结果集
     * 
     * @return 结果集
     */
    public R getR() {
        return r;
    }

    /**
     * 设置结果集
     * 
     * @param r 结果集
     */
    public void setR(R r) {
        this.r = r;
    }

    /**
     * 获取分页配置
     * 
     * @return 分页配置
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * 设置分页配置
     * 
     * @param pagination 分页配置
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
