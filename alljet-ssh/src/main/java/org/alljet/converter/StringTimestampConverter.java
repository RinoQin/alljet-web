/*
 * FileName: StringTimestampConverter.java
 * Author:   v_qinyuchen
 * Date:     2016年3月24日 下午6:02:21
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.converter;

import java.sql.Timestamp;
import java.text.ParseException;

import org.springframework.core.convert.converter.Converter;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StringTimestampConverter extends DateConverterBase implements Converter<String, Timestamp> {

    @Override
    public Timestamp convert(String source) {

        if (source == null) {
            return null;
        }

        String trim = source.trim();
        if (trim.length() == 0) {
            return null;
        }
        try {
            return source.contains(":") ? (Timestamp) getDateTimeFormat().parse(trim) : (Timestamp) getDateFormat()
                    .parse(trim);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
