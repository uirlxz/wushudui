package com.example.fms.api;

import com.alibaba.fastjson.JSONArray;
import com.example.fms.model.FileInfo;
import com.example.fms.model.vo.FileInfoVo;
import com.example.fms.service.FileService;
import com.example.fms.utils.Constants;
import com.example.fms.utils.FileUtils;
import com.example.fms.utils.ResponseBody;
import com.example.fms.utils.ZipUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping(value = "/file")
public class  FileController {
    @Autowired
    private FileService fileService;
    /**
     * 多文件上传
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(MultipartFile file, HttpServletRequest request) {
        //获取上传文件

        System.err.println("upload");
        //保存文件
        if (file != null) {
            FileInfo fileInfo = new FileInfo();
            String filename = file.getOriginalFilename();
            fileInfo.setFileName(filename);
            String path = FileUtils.saveFile(request, file);
            fileInfo.setPath(path);
            FileInfo info = fileService.save(fileInfo);
            return new ResponseBody(Constants.SUCCESS_CODE, info);
        }
        //查询未标记的文件
        return null;
    }
    /**
     * 多文件标记
     * @param fileIds 多个文件ID

     * @param remark 备注

     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/signFile",method = RequestMethod.GET)

    public Object signFile(String fileIds,String remark) throws Exception {


        //文件标记
        fileService.signFile(fileIds,remark);
        //返回文件标记结果
        List<FileInfoVo> fileInfoVos = fileService.getFileByIds(fileIds);
        return fileInfoVos;
    }
    /**
     * 文件下载
     * @param fileIds 多个文件id
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downLoadFile",method = RequestMethod.GET)
    public Object downLoadFile(String fileIds, HttpServletResponse response, HttpServletRequest request) throws IOException {

        JSONArray jsonArray = JSONArray.parseArray(fileIds);
        //下载文件的个数为0，就直接返回
        if(jsonArray == null || jsonArray.size() == 0)
            return null;

        if(jsonArray.size() == 1){
            //下载单个文件
            OutputStream os = null;
            try{
                os = response.getOutputStream();
                Long fileId = jsonArray.getLong(0);
                FileInfo fileInfo = fileService.getFileById(fileId);
                File file = new File(fileInfo.getPath());
                if(file.exists()){
                    response.reset();
                    String fileName = fileInfo.getFileName();
                    //处理文件中文名乱码问题
                    String userAgent = request.getHeader("User-Agent");
                    byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes()
                            : fileName.getBytes("UTF-8");
                    fileName = new String(bytes, "ISO-8859-1");
                    response.setHeader("Content-Disposition", "attachment;filename="+fileName);
                    response.setContentType("application/octet-stream; charset=utf-8");

                    os.write(org.apache.commons.io.FileUtils.readFileToByteArray(file));
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                os.close();
            }

        }else {
            //下载多个文件，将文件压缩zip格式
            String zipName = "myfile.zip";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition","attachment; filename="+zipName);
            ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
            try{
                for(int i = 0;i < jsonArray.size();i++){
                    FileInfo fileInfo = fileService.getFileById(jsonArray.getLong(i));
                    ZipUtils.doCompress(fileInfo.getPath(),out);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                out.close();
            }


        }
        return null;
    }
}
