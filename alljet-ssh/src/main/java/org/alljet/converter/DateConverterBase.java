/*
 * FileName: DateConverterBase.java
 * Author:   v_qinyuchen
 * Date:     2016年3月24日 下午6:03:37
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DateConverterBase {
    /** datePattern */
    private final String datePattern = "yyyy-MM-dd";
    /** timePattern */
    private final String timePattern = "HH:mm:ss";
    /** dateFormat */
    private DateFormat dateFormat = new SimpleDateFormat(datePattern);
    /** dateTimeFormat */
    private DateFormat dateTimeFormat = new SimpleDateFormat(datePattern + " " + timePattern);

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @return 日期
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param dateFormat 格式
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return the dateTimeFormat
     */
    public DateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * @param dateTimeFormat the dateTimeFormat to set
     */
    public void setDateTimeFormat(DateFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

}
