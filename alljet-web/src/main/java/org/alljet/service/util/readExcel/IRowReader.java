/*
 * Copyright (C), 2013-2016, 上海汽车集团股份有限公司
 * FileName: IRowReader.java
 * Author:   v_qinyuchen
 * Date:     2016年1月12日 下午1:59:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.service.util.readExcel;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IRowReader {

    /**
     * map的key，value为true/false，意为对行的操作成功或失败
     */
    public static final String MAP_KEY_STATUS = "status";
    /**
     * map的key，value为对行操作失败的记录的行号
     */
    public static final String MAP_KEY_FAILRECORDS = "failRecords";
    /**
     * map的key，value为true/false，当读取的这一行是空行为true，否则false
     */
    public static final String MAP_KEY_ISEMPTY = "isEmpty";
    /**
     * map的key，value为true/false，当读取的这一行为标题行时为true，否则false
     */
    public static final String MAP_KEY_ISTITLE = "isTitle";

    /**
     * 业务逻辑实现方法
     * 
     * @param sheetIndex
     * @param curRow
     * @param rowlist
     */
    public Map<String, Object> getRows(int sheetIndex, int curRow, List<String> rowlist);
}
