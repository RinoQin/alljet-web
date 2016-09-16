/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: TestCallBack.java
 * Author:   v_qinyuchen
 * Date:     2016年6月29日 上午10:16:01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.test;

import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TestCallBack {

    JiekouService jiekouService = new JiekouServiceImpl();

    @Test
    public void testCallBack() {
        String param = "今天吃水果";
        CallBack callBack = new CallBack() {

            @Override
            public void callBack(Map<String, Object> map) {
                // TODO Auto-generated method stub
                Gson gson = new Gson();
                System.out.println("今天吃了什么水果啊？看下边！");
                System.out.println(gson.toJson(map));
            }

        };
        jiekouService.testYibuHuidiao(param, callBack);
    }

}
