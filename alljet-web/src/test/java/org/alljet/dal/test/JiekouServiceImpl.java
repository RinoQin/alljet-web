/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: JiekouServiceImpl.java
 * Author:   v_qinyuchen
 * Date:     2016年6月29日 上午10:17:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dal.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class JiekouServiceImpl implements JiekouService {

    @Override
    public void testYibuHuidiao(String param, CallBack callBack) {
        System.out.println("先来点甜点什么的。");
        System.out.println("要开吃啦！：" + param);
        System.out.println("吃完啦！");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("苹果", "不好吃");
        map.put("橙子", "吃完了");
        map.put("香蕉", "吃了点");
        callBack.callBack(map);

    }

}
