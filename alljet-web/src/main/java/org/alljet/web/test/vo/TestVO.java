/*
 * FileName: TestVO.java
 * Author:   v_qinyuchen
 * Date:     2016年4月6日 下午5:07:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.web.test.vo;

import java.io.Serializable;

import org.alljet.web.test.entity.TestPO;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TestVO extends TestPO implements Serializable {

    /**
     */
    private static final long serialVersionUID = -328556489380433323L;
    private int index;

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
