/*
 * FileName: Excel2007WriterImpl.java
 * Author:   v_qinyuchen
 * Date:     2016年1月18日 上午11:44:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.service.util.writerExcel;

/**
 * 〈一句话功能简述〉<br>
 * 使用Excel2007Writer的例子
 * 
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Excel2007WriterImpl extends AbstractExcel2007Writer {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("............................");
        long start = System.currentTimeMillis();
        // 构建excel2007写入器
        AbstractExcel2007Writer excel07Writer = new Excel2007WriterImpl();
        // 调用处理方法
        excel07Writer.process("F://test07.xlsx");
        long end = System.currentTimeMillis();
        System.out.println("....................." + (end - start) / 1000);
    }

    /*
     * 可根据需求重写此方法，对于单元格的小数或者日期格式，会出现精度问题或者日期格式转化问题，建议使用字符串插入方法
     * @see com.excel.ver2.AbstractExcel2007Writer#generate()
     */
    @Override
    public void generate() throws Exception {
        // 电子表格开始
        beginSheet();
        for (int rownum = 0; rownum < 100; rownum++) {
            // 插入新行
            insertRow(rownum);
            // 建立新单元格,索引值从0开始,表示第一列
            createCell(0, "中国<" + rownum + "!");
            createCell(1, 34343.123456789);
            createCell(2, "23.67%");
            createCell(3, "12:12:23");
            createCell(4, "2010-10-11 12:12:23");
            createCell(5, "true");
            createCell(6, "false");

            // 结束行
            endRow();
        }
        // 电子表格结束
        endSheet();
    }
}
