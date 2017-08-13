package com.example.fms.utils.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/18.
 */
public class word {
        // word文档
        private Dispatch doc;
        // word运行程序对象
        private ActiveXComponent word;
        // 所有word文档集合
        private Dispatch documents;
        // 选定的范围或插入点
        private Dispatch selection;
        private boolean saveOnExit = true;

        public word() throws Exception {
            if (word == null) {
                word = new ActiveXComponent("Word.Application");
                word.setProperty("Visible", new Variant(false)); //不可见打开word
                word.setProperty("AutomationSecurity", new Variant(3)); //禁用宏
            }
            if (documents == null)
                documents = word.getProperty("Documents").toDispatch();
        }

        /**
         * 打开一个已存在的文档
         */
        public void openDocument(String docPath) {
            try {
                doc = Dispatch.call(documents, "Open", docPath).toDispatch();

            }
            catch (Exception e){
                System.out.println("打开word失败，请重新上传附件！");
              //  System.exit(0);
                throw e;
            }
            selection = Dispatch.get(word, "Selection").toDispatch();
        }

        /**
         * 关闭当前word文档
         */
        public void closeDocument() {
            try {
                if (doc != null) {
                    Dispatch.call(doc, "Save");
                    Dispatch.call(doc, "Close", new Variant(saveOnExit));
                    doc = null;
                }
            }
            catch (Exception e){
                System.out.println("保存失败，请确认用户操作word权限正常，再重新上传新附件！");
                //System.exit(0);
                throw e;
            }
        }
        /**
         * 关闭全部应用
         */
        public void close() {
        //closeDocument();
            if (word != null) {
                Dispatch.call(word, "Quit");
                word = null;
            }
            selection = null;
            documents = null;
        }

        /**
         * 在当前插入点插入图片
         */
        public void insertImage(String imagePath){
            try {

                Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
                    "AddPicture", imagePath,"true","false").toDispatch(); // 添加图片
                Dispatch.put(picture, "Width", new Variant(0)); //
                Dispatch.put(picture, "Height", new Variant(0)); // 图片的宽度，高度都设为1，使人很难察觉
            }
            catch (Exception e){
                System.out.println("插入图片失败，检查服务器是否开启或imgUrl是否配置正确！");
               throw e;
            }
        }

        /**
         * 复制文件
         * @param oldPath 原文件目录
         * @param newPath 新文件目录
         */
        public static void copyFile(String oldPath, String newPath) {
            try {
                int bytesum = 0;
                int byteread = 0;
                File oldfile = new File(oldPath);
                if (oldfile.exists()) { //文件存在时
                    InputStream inStream = new FileInputStream(oldPath); //读入原文件
                    FileOutputStream fs = new FileOutputStream(newPath);
                    byte[] buffer = new byte[1444];
                    while ( (byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread; //字节数 文件大小
                        fs.write(buffer, 0, byteread);
                    }
                    inStream.close();
                    fs.close();
                    System.out.println("copy successfully");
                }
            }
            catch (Exception e) {
                System.out.println("复制单个文件操作出错"+e);
            }
        }

        /**
         * 制作标记文件
         * @param filePath 要制作的文件地址
         * @param imgUrl 服务器配置的地址，例：String imgUrl = "http://192.168.1.43:8081/img/***.jpg"
         */
        public static boolean makeFile(String filePath, String imgUrl){
            word word =null;
            try {
                word = new word();
                word.openDocument(filePath);
                word.insertImage(imgUrl);
                word.closeDocument();
                System.out.println("制作word成功！");
                return true;
            }
            catch (Exception e){
                System.out.println("制作失败");
                return false;
            }finally {
                if(word != null)
                    System.out.println("word close");
                word.close();
            }

        }

    public static void main(String[] args) {
       String uuid = UUID.randomUUID().toString().replace("-", "");
       // makeFile("C:\\upload\\file\\\\20170727104133027223\\1111.doc","http://192.168.1.15:8081/wyfx/img/448bb6344bcb491f8ed39702755bf2ec.jpg");
        String property = System.getProperty("java.library.path");
    }
}

