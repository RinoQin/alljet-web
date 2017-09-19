/*
 * FileName: TestReader.java
 * Author:   v_qinyuchen
 * Date:     2016年1月22日 上午10:48:39
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.service.util.readExcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TestReader implements IRowReader {
    private static final Logger log = LoggerFactory.getLogger(TestReader.class);

    /*
     * 业务逻辑实现方法
     */
    @Override
    public Map<String, Object> getRows(int sheetIndex, int curRow, List<String> rowlist) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        if (curRow == 0) {
            log.debug(" 第{}行,标题行：{}", curRow, gson.toJson(rowlist));
            map.put(MAP_KEY_STATUS, "true");
            map.put(MAP_KEY_ISTITLE, "true");
            map.put(MAP_KEY_ISEMPTY, "false");
            return map;
        }
        try {
            map.put(MAP_KEY_ISTITLE, "false");

            if ((!rowlist.isEmpty()) && rowlist.size() == 2 && (!(rowlist.get(1)).isEmpty())) {
                map.put(MAP_KEY_ISEMPTY, "false");
                map.put(MAP_KEY_STATUS, "true");
            } else {
                map.put(MAP_KEY_ISEMPTY, "true");
                map.put(MAP_KEY_STATUS, "false");
                map.put(MAP_KEY_FAILRECORDS, (curRow + 1) + "");
            }
            log.debug(" 第{}行：{},size={},isTitle={}, isEmpty={}", curRow, gson.toJson(rowlist), rowlist.size(),
                    map.get(MAP_KEY_ISTITLE), map.get(MAP_KEY_ISEMPTY));
        } catch (Exception e) {
            map.put(MAP_KEY_STATUS, "false");
            map.put(MAP_KEY_FAILRECORDS, (curRow + 1) + "");
        }
        return map;
    }

}
