/*
 * FileName: TestController.java
 * Author:   v_qinyuchen
 * Date:     2016年3月24日 下午4:19:37
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.web.test.controller;

import java.util.List;

import org.alljet.web.test.service.ITestService;
import org.alljet.web.test.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /** log */
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    ITestService testService;

    @RequestMapping("/custmGiftRightsList")
    public String custmGiftRightsList(Model model) {
        String test = testService.callbackTestClient();
        List<TestVO> list = testService.getTestList();
        log.debug(test);
        model.addAttribute("hello", test);
        model.addAttribute("resultlist", list);
        return "test/test.ftl";
    }

}
