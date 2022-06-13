package com.asa.base.utils;

import com.asa.base.log.LoggerFactory;
import com.asa.base.utils.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author andrew_asa
 * @date 2022/6/12.
 */
public class ClipboardUtils {

    /**
     * 从剪切板获得文字。
     */
    public static String getSysClipboardText() {

        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪切板中的内容
        Transferable clipTf = sysClip.getContents(null);

        if (clipTf != null) {
            // 检查内容是否是文本类型
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    /**
     * 将字符串复制到剪切板。
     */
    public static void setSysClipboardText(String writeMe) {

        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

    /**
     * 从剪切板获得图片。
     */
    public static Image getImageFromClipboard() throws Exception {

        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable cc = sysc.getContents(null);
        if (cc == null)
            return null;
        else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);
        return null;
    }

    /**
     * 复制图片到剪切板。
     */
    public static void setClipboardImage(final Image image) {

        Transferable trans = new Transferable() {

            public DataFlavor[] getTransferDataFlavors() {

                return new DataFlavor[]{DataFlavor.imageFlavor};
            }

            public boolean isDataFlavorSupported(DataFlavor flavor) {

                return DataFlavor.imageFlavor.equals(flavor);
            }

            public Object getTransferData(DataFlavor flavor)
                    throws UnsupportedFlavorException, IOException {

                if (isDataFlavorSupported(flavor))
                    return image;
                throw new UnsupportedFlavorException(flavor);
            }

        };
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(trans,
                                                                     null);
    }

    private static boolean safeSaveImagePath(String dst) {

        return StringUtils.isNotEmpty(dst)
                && !StringUtils.contains(dst, "..")
                && StringUtils.equals("png", FilenameUtils.getExtension(dst));
    }

    public static boolean saveImageFromClipboard(String dst) throws Exception {

        if (!safeSaveImagePath(dst)) {
            LoggerFactory.getLogger().error("error save img path [{}]", dst);
            return false;
        }
        //获取粘贴板图片
        Image image = getImageFromClipboard();
        File file= new File(dst);
        //转成jpg
        //BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //转成png
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        //ImageIO.write((RenderedImage)bufferedImage, "jpg", file);
        ImageIO.write((RenderedImage)bufferedImage, "png", file);
        return true;
    }

    public static boolean saveImageFromClipboardNoException(String dst) {

        try {
            saveImageFromClipboard(dst);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
