package com.example.demo.temp;


import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Testing {

    private static Pattern NUMBER_PATTERN = Pattern.compile("\\$\\{(.*?)}");

    public static void main(String[] args) throws IOException {

        String param = "{\n" +
                "\t\"requestParams\": {\n" +
                "\t\t\"seller\": \"惠州鑫华胜建设工程有限公司\",\n" +
                "\t\t\"amount\": \"2,199.00\",\n" +
                "\t\t\"coreCompanyCode\": \"中国兵工物资集团有限公司\",\n" +
                "\t\t\"coreCompanyName\": \"中国兵工物资集团有限公司\",\n" +
                "\t\t\"expireMonth\": \"01\",\n" +
                "\t\t\"assetNo\": \"YSZC2024019579\",\n" +
                "\t\t\"buyerCode\": \"91110000100009999G\",\n" +
                "\t\t\"buyer\": \"中国兵工物资集团有限公司\",\n" +
                "\t\t\"confirmDate\": \"2024-01-08\",\n" +
                "\t\t\"sellerCode\": \"91441303325059326Q\",\n" +
                "\t\t\"expireDay\": \"31\",\n" +
                "\t\t\"assetId\": 1744312077120397314,\n" +
                "\t\t\"contractName\": \"fgfa001;fgfa002;fgfa003\",\n" +
                "\t\t\"expireDate\": \"2024-01-31\",\n" +
                "\t\t\"contractCode\": \"fgfa001;fgfa002;fgfa003\",\n" +
                "\t\t\"expireYear\": \"2024\"\n" +
                "\t}\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(param);

        // 提取内部的 requestParams 对象
        JsonNode requestParamsNode = jsonNode.get("requestParams");

        // 将内部对象转换为 Map<String, Object>
        Map<String, Object> map = objectMapper.convertValue(requestParamsNode, Map.class);

        System.out.println("---------" + JSONUtil.toJsonStr(map));

        //模板地址
        File file = new File("src/main/resources/static/中国农业银行北京支行融资合同1.docx");
        InputStream inputStream = new FileInputStream(file);
        XWPFDocument document = fillContent(map, inputStream);
        System.out.println("document----" + document);

        //输出文件地址
        //File localFile = new File("D:\\" + System.currentTimeMillis() + ".doc");
        File localFile = new File("src/main/resources/" + System.currentTimeMillis() + ".doc");
        FileOutputStream fileOutputStream = new FileOutputStream(localFile);
        document.write(fileOutputStream);
        fileOutputStream.flush();
    }

    private static XWPFDocument fillContent(Map<String, Object> paramData, InputStream inputStream) {
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(inputStream);
        } catch (IOException e) {

        }

        //获取段落
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            //设置对其方式
            paragraph.setAlignment(ParagraphAlignment.BOTH);
            //获取了当前段落中的所有运行对象，即文本内容
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String runString = run.toString();
                //匹配${...} 的字符串格式
                Matcher m = NUMBER_PATTERN.matcher(runString);
                if (m.find()) {
                    for (Map.Entry<String, Object> entry : paramData.entrySet()) {
                        String key = entry.getKey();
                        runString = runString.replace("${" + key + "}", paramData.get(key).toString());

                    }
                    //匹配${} 替换map中的值
                    run.setText(runString, 0);
                }
            }
        }

        //获取表格,替换合同条件表格数据,替换发票表格数据
        List<XWPFTable> tables = document.getTables();
        processContractTable(tables.get(0), paramData);
        processContractTable(tables.get(1), paramData);
        processContractTable(tables.get(2), paramData);
        processContractTable(tables.get(3), paramData);

        //新增中信银行发票数据
        List<Map<String, Object>> listMap = (List<Map<String, Object>>) paramData.get("invoiceList");

        if (CollectionUtils.isNotEmpty(listMap)) {
            addTableInDocFooterPos(listMap, tables.get(1));
        }
        return document;
    }

    /**
     * 通过创建或获取段落，并在段落中添加新的文本内容，同时设置了文本的字体大小。
     *
     * @param table
     * @param params
     */
    private static void processContractTable(XWPFTable table, Map<String, Object> params) {
        //获取表格所有行
        List<XWPFTableRow> rows = table.getRows();
        for (XWPFTableRow row : rows) {
            //对于每一行，获取其中的所有单元格
            List<XWPFTableCell> tableCells = row.getTableCells();
            for (XWPFTableCell tableCell : tableCells) {
                //获取单元格中的文本内容
                String text = tableCell.getText();
                if (!text.contains("${")) {    //如果该单元格中不包含 ${} 的格式，就跳过处理
                    continue;
                }
                CTTc ctTc = tableCell.getCTTc();
                //垂直居中
                tableCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                //正则判断
                Matcher m = NUMBER_PATTERN.matcher(text);
                // 表格固定值替换
                if (m.find()) {
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        String key = entry.getKey();
                        text = text.replace("${" + key + "}", params.get(key).toString());

                    }
                    //判断单元格中段落的数量，如果当前单元格没有段落，则创建一个新的段落
                    CTP ctP = (ctTc.sizeOfPArray() == 0) ? ctTc.addNewP() : ctTc.getPArray(0);
                    //获取当前单元格中的段落
                    XWPFParagraph paragraph = tableCell.getParagraph(ctP);
                    //删除第一个段落文本对象
                    paragraph.removeRun(0);
                    //删除段落
                    while (StringUtils.isNotBlank(paragraph.getText())) {
                        paragraph.removeRun(0);
                    }
                    XWPFRun run = paragraph.createRun();
                    run.setFontSize(10);   //设置字体大小
                    run.setText(text);
                }
            }
        }
    }


    private static void addTableInDocFooterPos(List<Map<String, Object>> list, XWPFTable table) {
        for (Map<String, Object> map : list) {
            // 获取模板表格全部行
            XWPFTableRow row = table.getRows().get(0);
            XmlObject copy = row.getCtRow().copy();
            XWPFTableRow newCreateRow = new XWPFTableRow((CTRow) copy, table);
            for (int i = 0; i < row.getTableCells().size(); i++) {
                XWPFTableCell cell = newCreateRow.getCell(i);
                //设置字体格式
                CTTc cttc = cell.getCTTc();
                CTP ctp = (cttc.sizeOfPArray() == 0) ?
                        cttc.addNewP() : cttc.getPArray(0);
                XWPFParagraph paragraph = cell.getParagraph(ctp);
                paragraph.removeRun(0);
                XWPFRun run = paragraph.createRun();
                run.setFontSize(10);

                //根据索引获取对应的值
                switch (i) {
                    case 0:
                        Integer index = (Integer) map.get("index");
                        index = index + 1;
                        run.setText(index.toString());
                        break;
                    case 1:
                        run.setText(map.get("contractNo").toString());
                        break;
                    case 2:
                        run.setText(map.get("coreCompanyName").toString());
                        break;
                    case 3:
                        run.setText(map.get("billCode").toString());
                        break;
                    case 4:
                        run.setText(map.get("tsAssetNo").toString());
                        break;

                    case 5:
                        run.setText(map.get("confirmDate").toString());
                        break;
                    case 6:
                        run.setText(map.get("assetAmount").toString());
                        break;
                    case 7:
                        run.setText(map.get("invoicer").toString());
                        break;
                    case 8:
                        run.setText(map.get("receiveTicket").toString());
                        break;
                    case 9:
                        run.setText(map.get("tsAssetExpireDate").toString());
                        break;

                    default:
                        break;
                }
            }
            table.addRow(newCreateRow);
        }
    }



}
