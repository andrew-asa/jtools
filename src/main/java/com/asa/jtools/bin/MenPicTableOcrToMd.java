package com.asa.jtools.bin;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.ClipboardUtils;
import com.asa.base.utils.DateUtils;
import com.asa.base.utils.StringUtils;
import com.asa.base.utils.io.FileUtils;
import com.asa.base.utils.io.FilenameUtils;
import com.asa.jtools.base.lang.ConsoleSupport;
import com.asa.jtools.base.lang.GuiSupport;
import com.asa.jtools.ocr.TableOcr;
import com.asa.jtools.ocr.TableOcrResultParse;

/**
 * @author andrew_asa
 * @date 2021/10/5.
 * 切换host文件
 */
public class MenPicTableOcrToMd implements ConsoleSupport, GuiSupport {

    public static final String MEN_IMG_SAVE_PATH = "/Users/andrew_asa/temp/ocr/";

    public static final String MEN_IMG_OCR_SAVE_PATH = "/Users/andrew_asa/temp/ocr/out";

    public MenPicTableOcrToMd() {


    }

    private void doOcr() {

        try {
            String fileName = StringUtils.messageFormat("img-{}.png", DateUtils.formatDate(DateUtils.YYMMDDHHMMSS));
            String imgDir = FilenameUtils.concat(MEN_IMG_SAVE_PATH, fileName);
            String ocrDir = FilenameUtils.concat(imgDir, "out");
            FileUtils.forceMkdir(imgDir);
            String filePath = FilenameUtils.concat(imgDir, fileName);
            ClipboardUtils.saveImageFromClipboardNoException(filePath);
            LoggerFactory.getLogger().debug("save men img to  {}", filePath);
            TableOcr tableOcr = new TableOcr();
            tableOcr.doOcr(filePath, ocrDir);
            TableOcrResultParse resultParse = new TableOcrResultParse();
            resultParse.parseToMdAndSaveToClipboard(ocrDir, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        MenPicTableOcrToMd ot = new MenPicTableOcrToMd();
        ot.doOcr();
    }

}
