/*
 * FileName: DateStringConverter.java
 * Author:   v_qinyuchen
 * Date:     2016年3月24日 下午6:01:35
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.dome.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DateStringConverter extends DateConverterBase implements Converter<Date, String> {

    @Override
    public String convert(Date source) {
        if (source == null) {
            return "";
        }

        return getDateFormat().format(source);
    }
}
