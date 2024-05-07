package com.example.demo.temp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class WordTemplateUtils {
    private static Map<String, String> hashMap = new HashMap<>();
    private static List<String> keyList = Lists.newArrayList();
    private static Map<String, String> typeMap = new HashMap<>();
    // 文本类型
    private static final String DOC_TTYPE_EXT = "text";
    // 图片类型
    private static final String DOC_TTYPE_IMG = "img";
    // 文件类型
    private static final String DOC_TTYPE_FILE= "file";
    // 固定表格
    private static final String DOC_TTYPE_FIXED_TABLE= "fixTable";
    // 动态表格
    private static final String DOC_TTYPE_DYNAMIC_TABLE= "dynamicTable";
    public static void main(String[] args) {
        try {
            // 加载 Word 文档模板
            InputStream templateStream = new FileInputStream("D:\\idea\\test.docx");
            XWPFDocument document = new XWPFDocument(templateStream);
            hashMap.put("version", "1.0.1");
            hashMap.put("type", "C");
            hashMap.put("date", "2022-12-12");
            hashMap.put("final", "棒啊！");
            typeMap.put("version",DOC_TTYPE_EXT);
            typeMap.put("type", DOC_TTYPE_EXT);
            typeMap.put("date", DOC_TTYPE_EXT);
            typeMap.put("final", "棒啊！");
            keyList.add("version");
            keyList.add("type");
            keyList.add("date");
            keyList.add("final");
//            keyList.add("as");
//            keyList.add("bss");
            // 获取文档中的段落和占位符（变量）并替换为实际的数据
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replacePlaceholder(paragraph, hashMap,keyList);
            }

            // 获取文档中的表格并替换表格中的占位符（变量）为实际的数据
            for (XWPFTable table : document.getTables()) {
                log.info("getElementType=={}",table.getElementType());
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        replaceParagraphPlaceholders(cell, hashMap,keyList);
                    }
                }
            }

            // 将填充后的内容写入新的 Word 文档
            OutputStream out = new FileOutputStream("D:\\idea\\output.docx");
            document.write(out);
            out.close();

            document.close();
            System.out.println("写入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void replacePlaceholder(XWPFRun run, String placeholder, String replacement) {
        String text = run.getText(0);
        if (text != null && text.contains(placeholder)) {
            text = text.replace(placeholder, replacement);
            run.setText(text, 0);
        }
    }

    private static void replacePlaceholder(XWPFParagraph paragraph, Map<String,String> params,List<String> keyList) throws Exception{
        for (XWPFRun run : paragraph.getRuns()) {
            replacePlaceholder(run, params,keyList);
        }
    }

    private static void replacePlaceholder( XWPFRun run, Map<String,String> params,List<String> keyList) throws Exception{
        String text = run.getText(0);
        if (text != null){
            for(String word : keyList) {
                String value = params.get(word);
                String key = "${"+word+"}";
                if (text.contains(key)) {
                    text = text.replace(key, value);
//                    insertImage(run,"");
                    run.setText(text, 0);
                }
            }
        }
    }

    //将图片插入到指定位置
    private static void insertImage(XWPFRun run, String fileName) throws IOException {
        InputStream pictureInputStream = null;
        try{
            pictureInputStream = new FileInputStream(fileName);
            run.addPicture(pictureInputStream, XWPFDocument.PICTURE_TYPE_PNG, "dragon1.png", Units.toEMU(200), Units.toEMU(200));
        }catch (Exception e) {
            log.error("{}",e);
            throw new RuntimeException(e);
        } finally {
            if (null!=pictureInputStream) {
                pictureInputStream.close();
            }
        }
    }

    //将pdf转为png
    private static void convertPdfToImage(String pdfPath, String imagePath) throws IOException {
        PDDocument document = PDDocument.load(new File(pdfPath));
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = renderer.renderImage(0); // 渲染第一页为图像
        ImageIO.write(image, "PNG", new File(imagePath));
        document.close();
    }

    //将pdf转为png
    private static void convertPdfToImage(InputStream input, OutputStream out) throws IOException {
        PDDocument document = PDDocument.load(input);
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = renderer.renderImage(0); // 渲染第一页为图像
        ImageIO.write(image, "PNG", out);
        document.close();
    }

    private static void replaceParagraphPlaceholders(XWPFTableCell cell, Map<String,String> params,List<String> keyList) throws Exception{
        for (XWPFParagraph paragraph : cell.getParagraphs()) {
            replacePlaceholder(paragraph, params, keyList);
        }
    }

    private static void replacePlaceholder(XWPFParagraph paragraph, String placeholder, String replacement) {
        for (XWPFRun run : paragraph.getRuns()) {
            replacePlaceholder(run, placeholder, replacement);
        }
    }

    private static void replaceParagraphPlaceholders(XWPFTableCell cell, String placeholder, String replacement) {
        for (XWPFParagraph paragraph : cell.getParagraphs()) {
            replacePlaceholder(paragraph, placeholder, replacement);
        }
    }
//
//    private static void replacePlaceholdersInDocument(XWPFDocument document, Map<String, String> hashMap) {
//        for (XWPFParagraph paragraph : document.getParagraphs()) {
//            replacePlaceholder(paragraph, hashMap);
//        }
//
//        for (XWPFTable table : document.getTables()) {
//            for (XWPFTableRow row : table.getRows()) {
//                for (XWPFTableCell cell : row.getTableCells()) {
//                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
//                        replaceParagraphPlaceholders(paragraph, hashMap);
//                    }
//                }
//            }
//        }
//    }

    private static void insertPictureInTableCell(XWPFDocument document, String placeholder, String picturePath) throws Exception {
        List<XWPFTable> tables = new ArrayList<>(document.getTables());
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = new ArrayList<>(table.getRows());
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = new ArrayList<>(row.getTableCells());
                for (XWPFTableCell cell : cells) {
                    List<XWPFParagraph> paragraphs = new ArrayList<>(cell.getParagraphs());
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = new ArrayList<>(paragraph.getRuns());
                        for (XWPFRun run : runs) {
                            String text = run.getText(0);
                            if (text != null && text.contains(placeholder)) {
                                int index = text.indexOf(placeholder);
                                if (index >= 0) {
                                    // 移除占位符
                                    run.setText(text.replace(placeholder, ""), 0);
                                    // 在当前段落中插入图片
                                    XWPFRun pictureRun = paragraph.createRun();
                                    try (FileInputStream pictureInputStream = new FileInputStream(picturePath)) {
                                        pictureRun.addPicture(pictureInputStream, Document.PICTURE_TYPE_JPEG, "picture.jpg", Units.toEMU(200), Units.toEMU(200));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
