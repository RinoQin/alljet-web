/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: SaiYaPerson.java
 * Author:   v_qinyuchen
 * Date:     2016年7月4日 下午5:39:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.test;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SaiYaPerson implements Comparable<SaiYaPerson> {

    private String name;

    private Integer power;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the power
     */
    public Integer getPower() {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(Integer power) {
        this.power = power;
    }

    /**
     * 返回-1排在前面，返回1排在后面，返回0代表相等
     */
    @Override
    public int compareTo(SaiYaPerson o) {

        if (o == null) {
            return 1;
        }
        if (this.getPower().intValue() > o.getPower().intValue()) {
            return -1;
        } else if (this.getPower().intValue() < o.getPower().intValue()) {
            return 1;
        } else {
            return 0;
        }
    }
}
