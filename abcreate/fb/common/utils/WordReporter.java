package abcreate.fb.common.utils;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.io.OutputStream;  
import java.math.BigDecimal;  
import java.net.HttpURLConnection;  
import java.net.MalformedURLException;  
import java.net.ProtocolException;  
import java.net.URL;  
import java.text.DecimalFormat;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import java.math.BigDecimal;  
  
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;  
import org.apache.poi.util.Units;  
import org.apache.poi.xwpf.usermodel.XWPFDocument;  
import org.apache.poi.xwpf.usermodel.XWPFParagraph;  
import org.apache.poi.xwpf.usermodel.XWPFRun;  
import org.apache.poi.xwpf.usermodel.XWPFTable;  
import org.apache.poi.xwpf.usermodel.XWPFTableCell;  
import org.apache.poi.xwpf.usermodel.XWPFTableRow;   
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;  
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;  
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;  
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;  
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;  
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;  
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;  
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
public class WordReporter {  
    private static final Logger logger = LoggerFactory.getLogger(WordReporter.class);  
      
    private String templatePath;  
    private XWPFDocument doc = null;  
    private FileInputStream is = null;  
    private OutputStream os = null;  
    public WordReporter(String templatePath) {  
        this.templatePath = templatePath;  
  
    }  
  
    public void init() throws IOException {  
        is = new FileInputStream(new File(this.templatePath));  
        doc = new XWPFDocument(is);  
    }  
      
    /** 
     * 替换掉占位符 
     * @param params 
     * @return 
     * @throws Exception  
     */  
    public boolean export(Map<String,Object> params) throws Exception{  
        this.replaceInPara(doc, params);  
        return true;  
    }  
      
    /** 
     * 替换掉表格中的占位符 
     * @param params 
     * @param tableIndex 
     * @return 
     * @throws Exception  
     */  
    public boolean export(Map<String,Object> params,int tableIndex) throws Exception{  
        this.replaceInTable(doc, params,tableIndex);  
        return true;  
    }  
      
    /** 
     * 循环生成表格 
     * @param params 
     * @param tableIndex 
     * @return 
     * @throws Exception 
     */  
    public boolean export(List<Map<String,String>> params,int tableIndex) throws Exception{  
          
        return export(params,tableIndex,false);  
    }  
  
    public boolean export(List<Map<String,String>> params,int tableIndex,Boolean hasTotalRow) throws Exception{  
        this.insertValueToTable(doc, params, tableIndex,hasTotalRow);  
        return true;  
    }  
      
    /** 
     * 导出图片 
     * @param params 
     * @return 
     * @throws Exception 
     */  
    public boolean exportImg(Map<String,Object> params) throws Exception{  
        /*List<XWPFParagraph> list = doc.getParagraphs(); 
        for(XWPFParagraph para : list){ 
            logger.info(para.getText()); 
        }*/  
        List<XWPFTable> list = doc.getTables();  
        System.out.print(list.size());  
        return true;  
    }  
      
    /** 
     * 生成word文档 
     * @param outDocPath 
     * @return 
     * @throws IOException 
     */  
    public boolean generate(String outDocPath) throws IOException{        
         os = new FileOutputStream(outDocPath);  
         doc.write(os);  
         this.close(os);  
         this.close(is);  
         return true;  
    }  
      
    /** 
     * 替换表格里面的变量 
     *  
     * @param doc 
     *            要替换的文档 
     * @param params 
     *            参数 
     * @throws Exception  
     */  
    private void replaceInTable(XWPFDocument doc, Map<String, Object> params,int tableIndex) throws Exception {  
        List<XWPFTable> tableList = doc.getTables();  
        if(tableList.size()<=tableIndex){  
            throw new Exception("tableIndex对应的表格不存在");  
        }  
        XWPFTable table = tableList.get(tableIndex);  
        List<XWPFTableRow> rows;  
        List<XWPFTableCell> cells;  
        List<XWPFParagraph> paras;  
        rows = table.getRows();  
        for (XWPFTableRow row : rows) {  
            cells = row.getTableCells();  
            for (XWPFTableCell cell : cells) {  
                paras = cell.getParagraphs();  
                for (XWPFParagraph para : paras) {  
                    this.replaceInPara(para, params);  
                }  
            }  
        }  
    }  
          
    /** 
     * 替换段落里面的变量 
     *  
     * @param doc 
     *            要替换的文档 
     * @param params 
     *            参数 
     * @throws Exception  
     */  
    private void replaceInPara(XWPFDocument doc, Map<String, Object> params) throws Exception {  
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();  
        XWPFParagraph para;  
        while (iterator.hasNext()) {  
            para = iterator.next();  
            this.replaceInPara(para, params);  
        }  
    }  
  
    /** 
     * 替换段落里面的变量 
     *  
     * @param para 
     *            要替换的段落 
     * @param params 
     *            参数 
     * @throws Exception  
     * @throws IOException  
     * @throws InvalidFormatException  
     */  
    private boolean replaceInPara(XWPFParagraph para, Map<String, Object> params) throws Exception {  
        boolean data = false;  
        List<XWPFRun> runs;  
        //有符合条件的占位符  
        if (this.matcher(para.getParagraphText()).find()) {  
            runs = para.getRuns();  
            data = true;  
            Map<Integer,String> tempMap = new HashMap<Integer,String>();  
            for (int i = 0; i < runs.size(); i++) {                
                XWPFRun run = runs.get(i);            
                String runText = run.toString();                  
                //以"$"开头  
                boolean begin = runText.indexOf("$")>-1;  
                boolean end = runText.indexOf("}")>-1;                 
                if(begin && end){  
                    tempMap.put(i, runText);  
                    fillBlock(para, params, tempMap, i);  
                    continue;  
                }else if(begin && !end){  
                    tempMap.put(i, runText);  
                    continue;  
                }else if(!begin && end){  
                    tempMap.put(i, runText);  
                    fillBlock(para, params, tempMap, i);  
                    continue;  
                }else{  
                    if(tempMap.size()>0){  
                        tempMap.put(i, runText);  
                        continue;  
                    }  
                    continue;  
                }  
            }  
        } else if (this.matcherRow(para.getParagraphText())) {  
            runs = para.getRuns();  
            data = true;  
        }  
        return data;  
    }  
  
    /** 
     * 填充run内容 
     * @param para 
     * @param params 
     * @param tempMap 
     * @param i 
     * @param runText 
     * @throws InvalidFormatException 
     * @throws IOException 
     * @throws Exception 
     */  
    private void fillBlock(XWPFParagraph para, Map<String, Object> params,  
            Map<Integer, String> tempMap, int index)  
            throws InvalidFormatException, IOException, Exception {  
        Matcher matcher;  
        if(tempMap!=null&&tempMap.size()>0){  
            String wholeText = "";  
            List<Integer> tempIndexList = new ArrayList<Integer>();  
            for(Map.Entry<Integer, String> entry :tempMap.entrySet()){  
                tempIndexList.add(entry.getKey());  
                wholeText+=entry.getValue();  
            }  
            if(wholeText.equals("")){  
                return;  
            }  
            matcher = this.matcher(wholeText);                    
            if (matcher.find()) {  
                boolean isPic = false;  
                int width = 0;  
                int height = 0;  
                int picType = 0;  
                String path = null;               
                String keyText = matcher.group().substring(2,matcher.group().length()-1);   
                Object value = params.get(keyText);  
                String newRunText = "";  
                if(value instanceof String){  
                    newRunText = matcher.replaceFirst(String.valueOf(value));                                                         
                }else if(value instanceof Map){//插入图片  
                    isPic = true;                             
                    Map pic = (Map)value;    
                    width = Integer.parseInt(pic.get("width").toString());    
                    height = Integer.parseInt(pic.get("height").toString());    
                    picType = getPictureType(pic.get("type").toString());                              
                    path = pic.get("path").toString();  
                }                         
                  
                //模板样式                
                XWPFRun tempRun = null;  
                // 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，  
                // 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。  
                for(Integer pos : tempIndexList){  
                    tempRun = para.getRuns().get(pos);  
                    tempRun.setText("", 0);  
                }                 
                if(isPic){  
                    //addPicture方法的最后两个参数必须用Units.toEMU转化一下  
                    //para.insertNewRun(index).addPicture(getPicStream(path), picType, "测试",Units.toEMU(width), Units.toEMU(height));  
                    tempRun.addPicture(getPicStream(path), picType, "测试",Units.toEMU(width), Units.toEMU(height));  
                }else{  
                    //样式继承  
                    if(newRunText.indexOf("\n")>-1){  
                        String[] textArr = newRunText.split("\n");  
                        if(textArr.length>0){  
                            //设置字体信息  
                            String fontFamily = tempRun.getFontFamily();  
                            int fontSize = tempRun.getFontSize();  
                            //logger.info("------------------"+fontSize);  
                            for(int i=0;i<textArr.length;i++){  
                                if(i==0){  
                                    tempRun.setText(textArr[0],0);  
                                }else{  
                                    if(StringUtils.isNotEmpty(textArr[i])){                                       
                                        XWPFRun newRun=para.createRun();  
                                        //设置新的run的字体信息  
                                        newRun.setFontFamily(fontFamily);  
                                        if(fontSize==-1){  
                                            newRun.setFontSize(10);  
                                        }else{  
                                            newRun.setFontSize(fontSize);  
                                        }  
                                        newRun.addBreak();  
                                        newRun.setText(textArr[i], 0);  
                                    }  
                                }  
                            }  
                        }  
                    }else{  
                        tempRun.setText(newRunText,0);    
                    }                     
                }  
            }  
            tempMap.clear();  
        }  
    }  
      
     /** 
     * Clone Object 
     * @param obj 
     * @return 
     * @throws Exception 
     */  
    private Object cloneObject(Object obj) throws Exception{  
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();   
        ObjectOutputStream out = new ObjectOutputStream(byteOut);   
        out.writeObject(obj);   
          
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());   
        ObjectInputStream in =new ObjectInputStream(byteIn);  
          
        return in.readObject();  
    }  
      
    private void insertValueToTable(XWPFDocument doc,List<Map<String, String>> params,int tableIndex) throws Exception {  
        insertValueToTable(doc,params,tableIndex,false);  
    }  
      
    private void insertValueToTable(XWPFDocument doc,List<Map<String, String>> params,int tableIndex,Boolean hasTotalRow) throws Exception {  
        List<XWPFTable> tableList = doc.getTables();  
        if(tableList.size()<=tableIndex){  
            throw new Exception("tableIndex对应的表格不存在");  
        }  
        XWPFTable table = tableList.get(tableIndex);  
        List<XWPFTableRow> rows = table.getRows();  
        if(rows.size()<2){  
            throw new Exception("tableIndex对应表格应该为2行");  
        }  
        //模板行  
        XWPFTableRow tmpRow = rows.get(1);  
        List<XWPFTableCell> tmpCells = null;  
        List<XWPFTableCell> cells = null;  
        XWPFTableCell tmpCell = null;  
          
        tmpCells = tmpRow.getTableCells();  
        String cellText = null;  
        String cellTextKey = null;  
          
        Map<String,Object> totalMap = null;   
          
        for (int i = 0, len = params.size(); i < len; i++) {  
            Map<String,String> map = params.get(i);  
            if(map.containsKey("total")){  
                totalMap = new HashMap<String,Object>();  
                totalMap.put("total", map.get("total"));  
                continue;  
            }  
            XWPFTableRow row = table.createRow();             
            row.setHeight(tmpRow.getHeight());  
            cells = row.getTableCells();  
            // 插入的行会填充与表格第一行相同的列数  
            for (int k = 0, klen = cells.size(); k < klen; k++) {  
                tmpCell = tmpCells.get(k);  
                XWPFTableCell cell = cells.get(k);                
                cellText = tmpCell.getText();  
                if(StringUtils.isNotBlank(cellText)){  
                    //转换为mapkey对应的字段  
                    cellTextKey = cellText.replace("$", "").replace("{", "").replace("}","");                     
                    if(map.containsKey(cellTextKey)){  
                        setCellText(tmpCell, cell, map.get(cellTextKey));                     
                    }                     
                }  
            }             
        }  
        // 删除模版行  
        table.removeRow(1);  
        if(hasTotalRow && totalMap!=null){  
            XWPFTableRow row = table.getRow(1);           
            //cell.setText("11111");  
            XWPFTableCell cell = row.getCell(0);  
            replaceInPara(cell.getParagraphs().get(0),totalMap);  
              
            /*String wholeText = cell.getText(); 
            Matcher matcher = this.matcher(wholeText); 
            if(matcher.find()){*/  
            /*List<XWPFParagraph> paras = cell.getParagraphs();            
             
            for (XWPFParagraph para : paras) { 
                this.replaceInPara(para, totalMap); 
            }*/               
            //}       
            table.addRow(row);        
            table.removeRow(1);           
        }  
    }  
  
    private void setCellText(XWPFTableCell tmpCell, XWPFTableCell cell,  
            String text) throws Exception {   
          
        CTTc cttc2 = tmpCell.getCTTc();  
        CTTcPr ctPr2 = cttc2.getTcPr();  
  
        CTTc cttc = cell.getCTTc();  
        CTTcPr ctPr = cttc.addNewTcPr();  
        //cell.setColor(tmpCell.getColor());  
        // cell.setVerticalAlignment(tmpCell.getVerticalAlignment());  
        if (ctPr2.getTcW() != null) {  
            ctPr.addNewTcW().setW(ctPr2.getTcW().getW());  
        }  
        if (ctPr2.getVAlign() != null) {  
            ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());  
        }  
        if (cttc2.getPList().size() > 0) {  
            CTP ctp = cttc2.getPList().get(0);  
            if (ctp.getPPr() != null) {  
                if (ctp.getPPr().getJc() != null) {  
                    cttc.getPList().get(0).addNewPPr().addNewJc()  
                            .setVal(ctp.getPPr().getJc().getVal());  
                }  
            }  
        }  
  
        if (ctPr2.getTcBorders() != null) {  
            ctPr.setTcBorders(ctPr2.getTcBorders());  
        }  
  
        XWPFParagraph tmpP = tmpCell.getParagraphs().get(0);  
        XWPFParagraph cellP = cell.getParagraphs().get(0);  
        XWPFRun tmpR = null;  
        if (tmpP.getRuns() != null && tmpP.getRuns().size() > 0) {  
            tmpR = tmpP.getRuns().get(0);  
        }  
          
        List<XWPFRun> runList = new ArrayList<XWPFRun>();  
        if(text==null){  
            XWPFRun cellR = cellP.createRun();    
            runList.add(cellR);  
            cellR.setText("");  
        }else{  
            //这里的处理思路是：$b认为是段落的分隔符，分隔后第一个段落认为是要加粗的  
            if(text.indexOf("\b") > -1){//段落，加粗，主要用于产品行程  
                String[] bArr = text.split("\b");  
                for(int b=0;b<bArr.length;b++){  
                    XWPFRun cellR = cellP.createRun();                    
                    runList.add(cellR);  
                    if(b==0){//默认第一个段落加粗  
                        cellR.setBold(true);                          
                    }  
                    if(bArr[b].indexOf("\n") > -1){  
                        String[] arr = bArr[b].split("\n");  
                        for(int i = 0; i < arr.length; i++){  
                            if(i > 0){  
                                cellR.addBreak();  
                            }                 
                            cellR.setText(arr[i]);  
                        }  
                    }else{                
                        cellR.setText(bArr[b]);  
                    }  
                }                     
            }else{  
                XWPFRun cellR = cellP.createRun();    
                runList.add(cellR);  
                if(text.indexOf("\n") > -1){  
                    String[] arr = text.split("\n");  
                    for(int i = 0; i < arr.length; i++){  
                        if(i > 0){  
                            cellR.addBreak();  
                        }                 
                        cellR.setText(arr[i]);  
                    }  
                }else{                
                    cellR.setText(text);  
                }  
            }  
              
        }  
          
        // 复制字体信息  
        if (tmpR != null) {  
            //cellR.setBold(tmpR.isBold());  
            //cellR.setBold(true);  
            for(XWPFRun cellR : runList){  
                if(!cellR.isBold()){  
                    cellR.setBold(tmpR.isBold());  
                }  
                cellR.setItalic(tmpR.isItalic());  
                cellR.setStrike(tmpR.isStrike());  
                cellR.setUnderline(tmpR.getUnderline());  
                cellR.setColor(tmpR.getColor());  
                cellR.setTextPosition(tmpR.getTextPosition());  
                if (tmpR.getFontSize() != -1) {  
                    cellR.setFontSize(tmpR.getFontSize());  
                }  
                if (tmpR.getFontFamily() != null) {  
                    cellR.setFontFamily(tmpR.getFontFamily());  
                }  
                if (tmpR.getCTR() != null) {  
                    if (tmpR.getCTR().isSetRPr()) {  
                        CTRPr tmpRPr = tmpR.getCTR().getRPr();  
                        if (tmpRPr.isSetRFonts()) {  
                            CTFonts tmpFonts = tmpRPr.getRFonts();  
                            CTRPr cellRPr = cellR.getCTR().isSetRPr() ? cellR  
                                    .getCTR().getRPr() : cellR.getCTR().addNewRPr();  
                            CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr  
                                    .getRFonts() : cellRPr.addNewRFonts();  
                            cellFonts.setAscii(tmpFonts.getAscii());  
                            cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());  
                            cellFonts.setCs(tmpFonts.getCs());  
                            cellFonts.setCstheme(tmpFonts.getCstheme());  
                            cellFonts.setEastAsia(tmpFonts.getEastAsia());  
                            cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());  
                            cellFonts.setHAnsi(tmpFonts.getHAnsi());  
                            cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());  
                        }  
                    }  
                }  
            }  
        }  
        // 复制段落信息  
        cellP.setAlignment(tmpP.getAlignment());  
        cellP.setVerticalAlignment(tmpP.getVerticalAlignment());  
        cellP.setBorderBetween(tmpP.getBorderBetween());  
        cellP.setBorderBottom(tmpP.getBorderBottom());  
        cellP.setBorderLeft(tmpP.getBorderLeft());  
        cellP.setBorderRight(tmpP.getBorderRight());  
        cellP.setBorderTop(tmpP.getBorderTop());  
        cellP.setPageBreak(tmpP.isPageBreak());  
        if (tmpP.getCTP() != null) {  
            if (tmpP.getCTP().getPPr() != null) {  
                CTPPr tmpPPr = tmpP.getCTP().getPPr();  
                CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP  
                        .getCTP().getPPr() : cellP.getCTP().addNewPPr();  
                // 复制段落间距信息  
                CTSpacing tmpSpacing = tmpPPr.getSpacing();  
                if (tmpSpacing != null) {  
                    CTSpacing cellSpacing = cellPPr.getSpacing() != null ? cellPPr  
                            .getSpacing() : cellPPr.addNewSpacing();  
                    if (tmpSpacing.getAfter() != null) {  
                        cellSpacing.setAfter(tmpSpacing.getAfter());  
                    }  
                    if (tmpSpacing.getAfterAutospacing() != null) {  
                        cellSpacing.setAfterAutospacing(tmpSpacing  
                                .getAfterAutospacing());  
                    }  
                    if (tmpSpacing.getAfterLines() != null) {  
                        cellSpacing.setAfterLines(tmpSpacing.getAfterLines());  
                    }  
                    if (tmpSpacing.getBefore() != null) {  
                        cellSpacing.setBefore(tmpSpacing.getBefore());  
                    }  
                    if (tmpSpacing.getBeforeAutospacing() != null) {  
                        cellSpacing.setBeforeAutospacing(tmpSpacing  
                                .getBeforeAutospacing());  
                    }  
                    if (tmpSpacing.getBeforeLines() != null) {  
                        cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());  
                    }  
                    if (tmpSpacing.getLine() != null) {  
                        cellSpacing.setLine(tmpSpacing.getLine());  
                    }  
                    if (tmpSpacing.getLineRule() != null) {  
                        cellSpacing.setLineRule(tmpSpacing.getLineRule());  
                    }  
                }  
                // 复制段落缩进信息  
                CTInd tmpInd = tmpPPr.getInd();  
                if (tmpInd != null) {  
                    CTInd cellInd = cellPPr.getInd() != null ? cellPPr.getInd()  
                            : cellPPr.addNewInd();  
                    if (tmpInd.getFirstLine() != null) {  
                        cellInd.setFirstLine(tmpInd.getFirstLine());  
                    }  
                    if (tmpInd.getFirstLineChars() != null) {  
                        cellInd.setFirstLineChars(tmpInd.getFirstLineChars());  
                    }  
                    if (tmpInd.getHanging() != null) {  
                        cellInd.setHanging(tmpInd.getHanging());  
                    }  
                    if (tmpInd.getHangingChars() != null) {  
                        cellInd.setHangingChars(tmpInd.getHangingChars());  
                    }  
                    if (tmpInd.getLeft() != null) {  
                        cellInd.setLeft(tmpInd.getLeft());  
                    }  
                    if (tmpInd.getLeftChars() != null) {  
                        cellInd.setLeftChars(tmpInd.getLeftChars());  
                    }  
                    if (tmpInd.getRight() != null) {  
                        cellInd.setRight(tmpInd.getRight());  
                    }  
                    if (tmpInd.getRightChars() != null) {  
                        cellInd.setRightChars(tmpInd.getRightChars());  
                    }  
                }  
            }  
        }  
    }  
  
    /** 
     * 删除表中的行 
     * @param tableAndRowsIdxMap 表的索引和行索引集合 
     */  
    public void deleteRow(Map<Integer,List<Integer>> tableAndRowsIdxMap){  
        if(tableAndRowsIdxMap!=null && !tableAndRowsIdxMap.isEmpty()){  
            List<XWPFTable> tableList = doc.getTables();  
            for(Map.Entry<Integer, List<Integer>> map : tableAndRowsIdxMap.entrySet()){  
                Integer tableIdx = map.getKey();  
                List<Integer> rowIdxList = map.getValue();  
                if(rowIdxList!=null && rowIdxList.size()>0){  
                    if(tableList.size()<=tableIdx){  
                        logger.error("表格"+tableIdx+"不存在");  
                        continue;  
                    }  
                    XWPFTable table = tableList.get(tableIdx);  
                    List<XWPFTableRow> rowList = table.getRows();  
                    for(int i=rowList.size()-1;i>=0;i--){  
                        if(rowIdxList.contains(i)){  
                            table.removeRow(i);  
                        }  
                    }  
                      
                }  
            }  
        }  
    }  
      
    /** 
     * 正则匹配字符串 
     *  
     * @param str 
     * @return 
     */  
    private Matcher matcher(String str) {  
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}",  
                Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher;  
    }  
  
    /** 
     * 正则匹配字符串 
     *  
     * @param str 
     * @return 
     */  
    private boolean matcherRow(String str) {  
        Pattern pattern = Pattern.compile("\\$\\[(.+?)\\]",  
                Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(str);  
        return matcher.find();  
    }  
      
    /**  
     * 根据图片类型，取得对应的图片类型代码  
     * @param picType  
     * @return int  
     */    
    private int getPictureType(String picType){    
        int res = XWPFDocument.PICTURE_TYPE_PICT;    
        if(picType != null){    
            if(picType.equalsIgnoreCase("png")){    
                res = XWPFDocument.PICTURE_TYPE_PNG;    
            }else if(picType.equalsIgnoreCase("dib")){    
                res = XWPFDocument.PICTURE_TYPE_DIB;    
            }else if(picType.equalsIgnoreCase("emf")){    
                res = XWPFDocument.PICTURE_TYPE_EMF;    
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){    
                res = XWPFDocument.PICTURE_TYPE_JPEG;    
            }else if(picType.equalsIgnoreCase("wmf")){    
                res = XWPFDocument.PICTURE_TYPE_WMF;    
            }    
        }    
        return res;    
    }    
      
    private InputStream getPicStream(String picPath) throws Exception{  
        URL url = new URL(picPath);    
        //打开链接    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
        //设置请求方式为"GET"    
        conn.setRequestMethod("GET");    
        //超时响应时间为5秒    
        conn.setConnectTimeout(5 * 1000);    
        //通过输入流获取图片数据    
        InputStream is = conn.getInputStream();  
        return is;    
    }  
      
    /** 
     * 关闭输入流 
     *  
     * @param is 
     */  
    private void close(InputStream is) {  
        if (is != null) {  
            try {  
                is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    /** 
     * 关闭输出流 
     *  
     * @param os 
     */  
    private void close(OutputStream os) {  
        if (os != null) {  
            try {  
                os.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
      
//  public static void main(String[] args) throws Exception{  
//      //WordReporter exporter = new WordReporter("c:\\booking_delivery.docx");  
//      //exporter.init();  
//      /*Map<String,Object> params1 = new HashMap<String,Object>();  
//      params1.put("myTable", "怡美假日旅行社");  
//      params1.put("username", "张三");  
//      params1.put("Title", "昆大丽5天4晚休闲游");  
//      Map<String,String> picMap = new HashMap<String,String>();  
//      picMap.put("width", "416");//经测试416可以占一行  
//      picMap.put("height", "120");  
//      picMap.put("type", "jpg");  
//      picMap.put("path", "http://192.168.1.81/p2/group1/M00/00/02/wKgBWVWBJF2AfcrKAAJnyycNFME158.jpg");  
//      params1.put("logo", picMap);      
//        
//      exporter.export(params1);  
//      List<Map<String,String>> list = new ArrayList<Map<String,String>>();  
//      Map<String,String> m1 = new HashMap<String,String>();  
//      m1.put("name", "李四1");  
//      m1.put("age", "20");  
//      m1.put("sex", "男");  
//      m1.put("job", "攻城狮1");  
//      m1.put("hobby", "篮球1");  
//      m1.put("phone", "1231231");  
//      list.add(m1);  
//      Map<String,String> m2 = new HashMap<String,String>();  
//      m2.put("name", "李四2");  
//      m2.put("age", "22");  
//      m2.put("sex", "女");  
//      m2.put("job", "攻城狮2");  
//      m2.put("hobby", "篮球2");  
//      m2.put("phone", "1231232");  
//      list.add(m2);  
//      Map<String,String> m3 = new HashMap<String,String>();  
//      m3.put("name", "李四3");  
//      m3.put("age", "23");  
//      m3.put("sex", "男3");  
//      m3.put("job", "攻城狮3");  
//      m3.put("hobby", "篮球3");  
//      m3.put("phone", "1231233");  
//      list.add(m3);  
//      exporter.export(list,0);*/  
//      Map<String,Object> m4 = new HashMap<String,Object>();  
//      m4.put("name", "李四4");  
//      m4.put("age", "24");  
//      m4.put("sex", "男4");  
//      /*Map<String,String> headMap = new HashMap<String,String>();  
//      headMap.put("width", "170");  
//      headMap.put("height", "170");  
//      headMap.put("type", "jpg");  
//      headMap.put("path", "http://192.168.1.81/p2/group1/M00/00/02/wKgBWVWBJF2AfcrKAAJnyycNFME158.jpg");  
//      m4.put("head", headMap);*/  
//      //exporter.export(m4, 2);  
//        
//      //exporter.exportImg(m4);  
//      //exporter.generate("c:\\"+System.currentTimeMillis()+".doc");  
//        
//      String text = "test"+"\n"+"test\n";  
//        
//      String[] arr = text.split("\n");  
//for(String str : arr){  
//  System.out.println(str);  
//}  
//        
//  }  
      
    /** 
     * 保留小数点后两位 
     * @param d 
     * @return 
     */  
    public static String getPoint2(Double d){  
        DecimalFormat df =new DecimalFormat("0.00");  
        return df.format(d);  
    }  
    public static void main(String[] args) {  
        /*BigDecimal b=new BigDecimal("56.244555"); 
        System.out.println(":"+getPoint2(b.doubleValue()));*/  
          
        Integer b = new Integer(1);  
        Integer a = 1000;  
        Integer d = 1000;  
        int c = 1;  
        System.out.println(a==d);  
    }  
    /** 
     * 判断字符串是否都为数字 
     * @param str 
     * @return 
     */  
    public static boolean isNumeric(String str){   
       Pattern pattern = Pattern.compile("[0-9]*");   
       Matcher isNum = pattern.matcher(str);  
       if( !isNum.matches() ){  
           return false;   
       }   
       return true;   
    }  
  
    /** 
     * 数字转换为汉语中人民币的大写<br> 
     *  
     * @author hongten 
     * @contact hongtenzone@foxmail.com 
     * @create 2013-08-13 
     */  
    public static class NumberToCN {  
        /** 
         * 汉语中数字大写 
         */  
        private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆",  
                "伍", "陆", "柒", "捌", "玖" };  
        /** 
         * 汉语中货币单位大写，这样的设计类似于占位符 
         */  
        private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元",  
                "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾",  
                "佰", "仟" };  
        /** 
         * 特殊字符：整 
         */  
        private static final String CN_FULL = "整";  
        /** 
         * 特殊字符：负 
         */  
        private static final String CN_NEGATIVE = "负";  
        /** 
         * 金额的精度，默认值为2 
         */  
        private static final int MONEY_PRECISION = 2;  
        /** 
         * 特殊字符：零元整 
         */  
        private static final String CN_ZEOR_FULL = "零元" + CN_FULL;  
  
        /** 
         * 把输入的金额转换为汉语中人民币的大写 
         *  
         * @param numberOfMoney 
         *            输入的金额 
         * @return 对应的汉语大写 
         */  
        public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {  
            StringBuffer sb = new StringBuffer();  
            // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or  
            // positive.  
            int signum = numberOfMoney.signum();  
            // 零元整的情况  
            if (signum == 0) {  
                return CN_ZEOR_FULL;  
            }  
            //这里会进行金额的四舍五入  
            long number = numberOfMoney.movePointRight(MONEY_PRECISION)  
                    .setScale(0, 4).abs().longValue();  
            // 得到小数点后两位值  
            long scale = number % 100;  
            int numUnit = 0;  
            int numIndex = 0;  
            boolean getZero = false;  
            // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11  
            if (!(scale > 0)) {  
                numIndex = 2;  
                number = number / 100;  
                getZero = true;  
            }  
            if ((scale > 0) && (!(scale % 10 > 0))) {  
                numIndex = 1;  
                number = number / 10;  
                getZero = true;  
            }  
            int zeroSize = 0;  
            while (true) {  
                if (number <= 0) {  
                    break;  
                }  
                // 每次获取到最后一个数  
                numUnit = (int) (number % 10);  
                if (numUnit > 0) {  
                    if ((numIndex == 9) && (zeroSize >= 3)) {  
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);  
                    }  
                    if ((numIndex == 13) && (zeroSize >= 3)) {  
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);  
                    }  
                    sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);  
                    sb.insert(0, CN_UPPER_NUMBER[numUnit]);  
                    getZero = false;  
                    zeroSize = 0;  
                } else {  
                    ++zeroSize;  
                    if (!(getZero)) {  
                        sb.insert(0, CN_UPPER_NUMBER[numUnit]);  
                    }  
                    if (numIndex == 2) {  
                        if (number > 0) {  
                            sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);  
                        }  
                    } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {  
                        sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);  
                    }  
                    getZero = true;  
                }  
                // 让number每次都去掉最后一个数  
                number = number / 10;  
                ++numIndex;  
            }  
            // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负  
            if (signum == -1) {  
                sb.insert(0, CN_NEGATIVE);  
            }  
            // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整  
            if (!(scale > 0)) {  
                sb.append(CN_FULL);  
            }  
            return sb.toString();  
        }  
    }  
}  