/*
 * FileName: Excel2007Reader.java
 * Author:   v_qinyuchen
 * Date:     2016年1月12日 下午1:43:37
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package org.alljet.service.util.readExcel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author v_qinyuchen
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Excel2007Reader extends DefaultHandler {
    // 共享字符串表
    private SharedStringsTable sst;
    // 上一次的内容
    private String lastContents;
    private boolean nextIsString;

    private int sheetIndex = -1;
    private final List<String> rowlist = new ArrayList<String>();
    // 当前行
    private int curRow = 0;
    // 当前列
    private int curCol = 0;

    // 定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等
    private String preRef = null, ref = null;
    // 定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格
    private String maxRef = null;

    private Map<String, Object> map = new HashMap<String, Object>();
    int total = 0;
    int succCount = 0;
    int failCount = 0;
    StringBuffer msg = new StringBuffer();

    private static final String MAP_KEY_SUCC_COUNT = "succCount";
    private static final String MAP_KEY_FAIL_COUNT = "failCount";
    private static final String MAP_KEY_MSG = "msg";
    private static final String MAP_KEY_STATUS = "status";
    private static final String MAP_KEY_TOTAL = "total";

    // 日期标志
    // private boolean dateFlag;
    // 数字标志
    // private boolean numberFlag;

    private boolean isTElement;

    private IRowReader rowReader;

    public void setRowReader(IRowReader rowReader) {
        this.rowReader = rowReader;
    }

    public Map<String, Object> getResultMap() {
        return this.map;
    }

    /**
     * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     * 
     * @param filename
     * @param sheetId
     * @throws Exception
     */
    public void processOneSheet(InputStream in, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(in);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);

        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    /**
     * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     * 
     * @param filename
     * @param sheetId
     * @throws Exception
     */
    public void processOneSheet(String fileName, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);

        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    /**
     * 遍历工作簿中所有的电子表格
     * 
     * @param filename
     * @throws Exception
     */
    public void process(InputStream in) throws Exception {
        OPCPackage pkg = OPCPackage.open(in);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    /**
     * 遍历工作簿中所有的电子表格
     * 
     * @param filename
     * @throws Exception
     */
    public void process(String fileName) throws Exception {
        OPCPackage pkg = OPCPackage.open(fileName);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    // 接收元素开始的通知 默认情况下，不执行任何操作。
    // 应用程序编写者可以在子类中重写此方法， 以便在每个元素的开始处采取特定的操作（如，分配新的树节点或将输出写入文件）
    // uri - 名称空间 URI，如果元素没有任何名称空间 URI，或者没有正在执行名称空间处理，则为空字符串。
    // localName - 本地名称（不带前缀），如果没有正在执行名称空间处理，则为空字符串。
    // qName - 限定的名称（带有前缀），如果限定的名称不可用，则为空字符串。
    // attributes - 附加到元素的属性。如果没有属性，则它将是空的 Attributes 对象。
    @Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

        // c => 单元格
        if ("c".equals(name)) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");

            if (preRef == null) {
                preRef = attributes.getValue("r");
            } else {
                preRef = ref;
            }
            // 当前单元格的位置
            ref = attributes.getValue("r");

            if ("s".equals(cellType)) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }

            // attributes.getValue("r");
            // // 日期格式
            // String cellDateType = attributes.getValue("s");
            // // if ("1".equals(cellDateType)) {
            // // dateFlag = true;
            // // } else {
            // // dateFlag = false;
            // // }
            // String cellNumberType = attributes.getValue("s");
            // if ("2".equals(cellNumberType)) {
            // numberFlag = true;
            // } else {
            // numberFlag = false;
            // }

        }
        // 当元素为t时
        if ("t".equals(name)) {
            isTElement = true;
        } else {
            isTElement = false;
        }

        // 置空
        lastContents = "";
    }

    // 接收元素结束的通知。默认情况下，不执行任何操作。
    // 应用程序编写者可以在子类中重写此方法，以便在每个元素的结束处采取特定的操作（如，结束树节点或将输出写入文件）。
    // uri - 名称空间 URI，如果元素没有任何名称空间 URI，或者没有正在执行名称空间处理，则为空字符串。
    // localName - 本地名称（不带前缀），如果没有正在执行名称空间处理，则为空字符串。
    // qName - 限定的名称（带有前缀），如果限定的名称不可用，则为空字符串。
    @Override
    public void endElement(String uri, String localName, String name) throws SAXException, RuntimeException {

        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {

            }
        }
        // t元素也包含字符串
        if (isTElement) {
            String value = lastContents.trim();
            rowlist.add(curCol, value);
            curCol++;
            isTElement = false;
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        } else if ("v".equals(name)) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            // // 日期格式处理
            // if (dateFlag) {
            // Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
            // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            // value = dateFormat.format(date);
            // }
            // // 数字类型处理
            // if (numberFlag) {
            // BigDecimal bd = new BigDecimal(value);
            // value = bd.setScale(3, BigDecimal.ROUND_UP).toString();
            // }

            if (!ref.equals(preRef)) {
                int len = countNullCell(ref, preRef);
                for (int i = 0; i < len; i++) {
                    rowlist.add(curCol, "");
                    curCol++;
                }
                // 开头缺失的单元格
                int len2 = countNullCell2(ref, preRef);
                for (int i = 0; i < len2; i++) {
                    rowlist.add(curCol, "");
                    curCol++;
                }
            }

            rowlist.add(curCol, value);
            curCol++;
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {

                // 默认第一行为表头，以该行单元格数目为最大数目
                if (curRow == 0) {
                    maxRef = ref;
                }
                // 补全一行尾部可能缺失的单元格
                if (maxRef != null) {
                    int len = countNullCell(maxRef, ref);
                    for (int i = 0; i <= len; i++) {
                        rowlist.add(curCol, "");
                        curCol++;
                    }
                }

                map = rowReader.getRows(sheetIndex, curRow, rowlist);
                if (Boolean.parseBoolean(map.get("isEmpty").toString())) {

                } else {
                    total++;
                    if (Boolean.parseBoolean(map.get("status").toString())) {
                        succCount++;
                    } else {
                        failCount++;
                        msg.append("\n").append(map.get("failRecords"));
                    }

                }

                rowlist.clear();
                curRow++;
                map.put(MAP_KEY_TOTAL, total);
                map.put(MAP_KEY_SUCC_COUNT, succCount);
                map.put(MAP_KEY_FAIL_COUNT, failCount);
                map.put(MAP_KEY_STATUS, map.get("status").toString());
                map.put(MAP_KEY_MSG, msg);
                curCol = 0;

            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**
     * 计算两个单元格之间的单元格数目(同一行)
     * 
     * @param ref
     * @param preRef
     * @return
     */
    public int countNullCell(String ref, String preRef) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = preRef.replaceAll("\\d+", "");

        xfd = fillChar(xfd, 3, '@', true);
        xfd_1 = fillChar(xfd_1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        int res = (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2] - letter_1[2]);
        return res - 1;
    }

    /**
     * 计算两个单元格之间的单元格数目(不同行)
     * 
     * @param ref
     * @param preRef
     * @return
     */
    public int countNullCell2(String ref, String preRef) {
        // excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = preRef.replaceAll("\\d+", "");
        int num = 0;
        // 计算出上一个单元格的下标如果大于1，要从A开始算起
        String str = ref.replaceAll("[^0-9]", "");
        str = str + 1;
        Integer nfd = Integer.parseInt(ref.replaceAll("[^0-9]", ""));
        Integer nfd_1 = Integer.parseInt(preRef.replaceAll("[^0-9]", ""));
        if (nfd > nfd_1) {
            // 已经换行
            char xfd_c = xfd.charAt(0);
            char xfd_c_1 = xfd_1.charAt(0);
            if (xfd_c > 'A') {
                num = xfd_c - 'A';
            }
        }

        return num;
    }

    /**
     * 字符串的填充
     * 
     * @param str
     * @param len
     * @param let
     * @param isPre
     * @return
     */
    String fillChar(String str, int len, char let, boolean isPre) {
        int len_1 = str.length();
        if (len_1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - len_1); i++) {
                    str = let + str;
                }
            } else {
                for (int i = 0; i < (len - len_1); i++) {
                    str = str + let;
                }
            }
        }
        return str;
    }
}
