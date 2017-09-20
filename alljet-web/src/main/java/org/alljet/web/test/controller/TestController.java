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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alljet.web.test.entity.TestPO;
import org.alljet.web.test.service.ITestService;
import org.alljet.web.test.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        String test = testService.getMessage();
        String test2 = testService.getMessage2();
        TestPO testVo = testService.getTestPOById(42l);
        List<TestVO> list = testService.getTestList();
        // PaginationResult<List<TestVO>> pageResult = testService.getTestListPage(new Pagination(5, 1), new TestVO());
        log.debug(test);
        System.out.println("试一试输出不");
        model.addAttribute("hello", test + "<br>" + test2);
        model.addAttribute("resultlist", list);
        // model.addAttribute("pageList", pageResult.getR());
        // model.addAttribute("pagination", pageResult.getPagination());
        model.addAttribute("testVo", testVo);
        return "test/test.ftl";
    }

    /** 活动明细---批次执行结果统计 */
    @RequestMapping(value = "/statChartsResult.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> statChartsResult(@RequestParam("activityId") Long activityId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String[] xAxisRes = { "短信响应数", "微信响应数", "App响应数", "网页点击量", "下单数" };
            String[] legends = { "计划", "实际" };

            List<Integer> yAxisExpect = new ArrayList<Integer>();
            List<Integer> yAxisStat = new ArrayList<Integer>();

            yAxisExpect.add(11);
            yAxisExpect.add(33);
            yAxisExpect.add(22);
            yAxisExpect.add(44);
            yAxisExpect.add(17);

            yAxisStat.add(18);
            yAxisStat.add(30);
            yAxisStat.add(25);
            yAxisStat.add(44);
            yAxisStat.add(27);

            map.put("xAxisRes", xAxisRes);
            map.put("legends", legends);
            map.put("yAxisExpect", yAxisExpect);
            map.put("yAxisStat", yAxisStat);
            map.put("flag", "true");
        } catch (Exception e) {
            map.put("flag", "false");
            log.debug("活动明细---批次执行结果统计失败：{}", e);
        }

        return map;
    }

    @RequestMapping("/restDemo")
    public String getRestDemo(Model model) {
    	log.debug("debugger的日志");
    	log.info("info的日志");
    	log.error("error的日志");
        return "restful.html";
    }

}
