package com.example.fms.utils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Author:
 * Date: uir_zyw
 * Tool: IntelliJ IDEA
 * info:
 */
public class ppt {
    private ActiveXComponent ppt;//ppt对象
    private ActiveXComponent presentation;//当前ppt对象
    private Dispatch pptPage = null;// 当前ppt页面
    /**
     * 打开excel文件
     * @param filepath 文件路径名称
     */
    protected void OpenPPT(String filepath) {
        try {
            ComThread.InitSTA();
            ppt = new ActiveXComponent("PowerPoint.Application"); //ppt对象
            ppt.setProperty("Visible", new Variant(true));//设置是否显示打开ppt
            ActiveXComponent presentations = ppt.getPropertyAsComponent("Presentations");
            presentation = presentations.invokeGetComponent("Open", new Variant(filepath), new Variant(false));

        } catch (Exception e) {
            System.out.println("打开ppt失败，请重新上传附件！"+e);
          //  System.exit(0);
        }
    }

    /**
     * 获取第几个幻灯片
     * @param pageIndex 序号，从1开始
     */
    private void getPptPage(int pageIndex) {
        //获取幻灯片对象
        ActiveXComponent slides = presentation.getPropertyAsComponent("Slides");
        //获得第几个PPT
        pptPage = Dispatch.call(slides, "Item", new Object[]{new Variant(pageIndex)}).toDispatch();
        Dispatch.call(pptPage, "Select");
    }

    /**
     *@param imgUrl 服务器图片文件路径
     * 在当前插入点插入图片
     */
    private void insertImage(String imgUrl) {
        try{

            getPptPage(1);
            Dispatch.call(Dispatch.get(pptPage, "Shapes").toDispatch(),
                    "AddPicture", imgUrl, true, true, "0", "0","0","0").toDispatch(); // 添加图片
        }
        catch (Exception e){

            System.out.println("插入图片失败，检查服务器是否开启或imgUrl是否配置正确！"+e);

            //System.exit(0);
        }
    }
    /**
     * 保存后关闭PPT并释放线程
     */
    private void closePpt() {
        if (null != presentation) {
            Dispatch.call(presentation, "Save");
            Dispatch.call(presentation, "Close");
        }
        ppt.invoke("Quit", new Variant[]{});
        ComThread.Release();
    }
    /**
     * 制作标记文件
     *@param filePath 上传的excel文件路径
     *@param imgUrl 服务器图片文件路径
     */
    public static boolean makeFile(String filePath,String imgUrl) {
        try{
            ppt pt = new ppt();
            pt.OpenPPT(filePath);
            pt.insertImage(imgUrl);
            pt.closePpt();
            System.out.println("制作成功！");
            return true;
        }catch (Exception e) {
            System.out.println("制作失败"+e);
            //System.exit(0);
            return false;
        }
    }
}
