package com.asa.jtools.ocr;

import com.asa.base.utils.PythonExec;

import java.util.Arrays;

/**
 * @author andrew_asa
 * @date 2022/6/12.
 */
public class TableOcr {

    public static final String TABLE_TO_OCR_PY_PATH = "/Users/andrew_asa/Documents/code/github/andrew-asa/exec/jtools/bin/table_to_ocr.py";

    public TableOcr() {

    }

    public void doOcr(String src, String dst) {

        PythonExec.waitExec(TABLE_TO_OCR_PY_PATH,
                            Arrays.asList("--img_path=" + src, "--save_folder=" + dst));
    }
}
