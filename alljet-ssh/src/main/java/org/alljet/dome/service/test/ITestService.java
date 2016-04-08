/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: ITestService.java
 * Author:   v_qinyuchen
 * Date:     2016年3月24日 下午5:02:12
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dome.service.test;

import java.util.List;

import org.alljet.dal.Pagination;
import org.alljet.dal.PaginationResult;
import org.alljet.dome.vo.TestVO;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ITestService {

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String callbackTestReturn();

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String testReturn();

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String callbackTestClient();

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String testObjectClient();

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<TestVO> getTestList();

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param queryVo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<TestVO> getTestList(TestVO queryVo);

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param pagination
     * @param queryVo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    PaginationResult<List<TestVO>> getTestListPage(Pagination pagination, TestVO queryVo);

}
