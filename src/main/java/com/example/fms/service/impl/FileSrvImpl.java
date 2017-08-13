package com.example.fms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.example.fms.model.FileInfo;
import com.example.fms.model.FileType;
import com.example.fms.model.vo.FileInfoVo;
import com.example.fms.model.vo.FileTypeVo;
import com.example.fms.repository.FileRepo;
import com.example.fms.repository.FileTypeRepo;
import com.example.fms.service.FileService;
import com.example.fms.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author: liuxingyu
 * DATE: 2017-07-17.17:11
 * description:
 * version:
 */
@Service
@Transactional
public class FileSrvImpl implements FileService {
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private FileTypeRepo fileTypeRepo;

    @Override
    public FileInfo save(FileInfo fileInfo) {
        //设置文件属性 未标记
        fileInfo.setStatus(Constants.NO_FILE_SIGN);
        //设置添加时间
        fileInfo.setTime(new Date());
        fileInfo.setUuid(UUID.randomUUID().toString().replace("-", ""));
        FileInfo info = fileRepo.save(fileInfo);
        return info;
    }

    @Override
    public List<FileTypeVo> getFileType() throws Exception {
        List<FileType> fileTypes = fileTypeRepo.findByStatus(Constants.YES_FILE_TYPE);
        List<FileTypeVo> fileTypeVos = ModelChange.changeList(FileTypeVo.class, fileTypes);

        return fileTypeVos;
    }


    public PageQuery getAllFileSign(int pageNo, int pageSize) throws Exception {
        //设置查询条件 ，已标记的文件 分页
        Pageable page = new PageRequest(pageNo -1,pageSize);
        Page<FileInfo> fileInfos = fileRepo.findByStatus(Constants.YES_FILE_SIGN,page);
        PageQuery pageQuery = PageUtils.pagingList(FileTypeVo.class, fileInfos, pageNo, pageSize);
        return pageQuery;
    }

    @Override
    public PageQuery getAllFileSignByCondition(Long fileTypeId, Date startTime, Date endTime, int pageNo, int pageSize) throws Exception {
        //设置文件格式
        final FileType fileType;
        if(fileTypeId != null){
            fileType = new FileType();
            fileType.setId(fileTypeId);
        }else {
            fileType = null;
        }
        //分页对象
        Pageable page = new PageRequest(pageNo -1,pageSize);
        //按照条件查询
        Page<FileInfo> fileInfos = fileRepo.findAll(new Specification<FileInfo>() {
            @Override
            public Predicate toPredicate(Root<FileInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //设置查询条件
                if (fileType != null ) {
                    list.add(cb.equal(root.get("fileType"),fileType));
                }
                list.add(cb.equal(root.get("status"),Constants.YES_FILE_SIGN));


                if (startTime != null) {
                    list.add(cb.between(root.get("longTime"),startTime.getTime(),endTime.getTime()));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, page);
        PageQuery pageQuery = PageUtils.pagingList(FileInfoVo.class, fileInfos, pageNo, pageSize);

        return pageQuery;
    }

    @Override
    public void delFile(Long fileId) {

        FileInfo fileInfo = fileRepo.findById(fileId);
        String path = fileInfo.getPath();
        //删除文件
        boolean flag = FileUtils.delete(path);
        if(flag){
            //删除目录
            int i = path.lastIndexOf("/");
            path = path.substring(0,i);
            FileUtils.delete(path);
        }

        // 删除数据库记录
        fileRepo.delete(fileInfo);
    }

    @Override
    public FileInfo getFileById(Long fileId) {
        FileInfo fileInfo = fileRepo.findById(fileId);
        return fileInfo;
    }

    @Override
    public void signFile(String fileIds,  String remark) throws Exception {
        JSONArray jsonArray = JSONArray.parseArray(fileIds);
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(jsonArray.size());
        if(jsonArray != null && jsonArray.size() > 0) {
            for(int i = 0; i < jsonArray.size();i ++){
                //    fixedThreadPool.submit(new Runnable() {
                //  @Override
                //  public void run() {
                //  try {
                Long fileId = jsonArray.getLong(i);
                FileInfo fileInfo = fileRepo.findById(fileId);
                //查询支持的格式
                List<FileType> fileTypes = fileTypeRepo.findByStatus(Constants.YES_FILE_TYPE);
                boolean flag = false;
                //判断格式是否支持
                for (FileType fileType : fileTypes) {
                    String fileName = fileInfo.getFileName();
                    int lastIndex = fileName.lastIndexOf(".");
                    String type = fileName.substring(lastIndex+ 1);
                    if (fileType.getType().indexOf(type) != -1) {
                        fileInfo.setFileType(fileType);
                        flag = true;
                        break;
                    }

                }
                //格式支持
                if (flag) {
                    try {
                        //生成UUID
                        String uuid;
//                    if(fireWallNumber){
//                        uuid = null;
//                    }else {
//                        uuid = UUID.randomUUID().toString().replace("-", "");
//                    }
//                    fileInfo.setUuid(uuid);
                        uuid = fileInfo.getUuid();
                        // fileRepo.save(fileInfo);

                        //获取本机IP
                        String ip = InetAddress.getLocalHost().getHostAddress();
//                        拼接服务器地址
//                        String ip = Constants.SERVER_IP;
                        String url = "http://" + ip +":8081"+ "/fms/img/" + uuid + ".jpg";
                        String catalog = fileInfo.getFileType().getCatalog();
                        String method = fileInfo.getFileType().getMethod();
                        Class cls = Class.forName(catalog);
                        Method staticMethod = cls.getDeclaredMethod(method,String.class,String.class);
                        boolean b = (boolean) staticMethod.invoke(cls, fileInfo.getPath(),url);
//                        boolean b = word.makeFile(fileInfo.getPath(), url);
                        if(b){
                            //标记成功
                            fileInfo.setStatus(Constants.YES_FILE_SIGN);

                            fileInfo.setRemark(remark);

                        } else {
                            //标记失败
                            fileInfo.setStatus(Constants.DANGER_FILE_SIGN);
                            String path = fileInfo.getPath();
                            //删除文件
                            boolean f = FileUtils.delete(path);

                            if (f) {
                                //删除目录
                                int lastIndex = path.lastIndexOf("\\");
                                path = path.substring(0, lastIndex);
                                FileUtils.delete(path);
                            }
                        }

                    } catch (Exception e) {
                        //标记失败
//                        fileInfo.setStatus(Constants.DANGER_FILE_SIGN);
                        String path = fileInfo.getPath();
                        //删除文件
                        boolean f = FileUtils.delete(path);

                        if (f) {
                            //删除目录
                            int lastIndex = path.lastIndexOf("\\");
                            path = path.substring(0, lastIndex);
                            FileUtils.delete(path);
                        }
                    }

                } else {
                    //格式不支持,标记格式错误
//                    fileInfo.setStatus(Constants.ERROR_FILE_SIGN);
                    String path = fileInfo.getPath();
                    //删除文件
                    boolean f = FileUtils.delete(path);

                    if (f) {
                        //删除目录
                        int lastIndex = path.lastIndexOf("\\");
                        path = path.substring(0, lastIndex);
                        FileUtils.delete(path);
                    }
                }

                fileRepo.save(fileInfo);


                //   } catch (Exception e) {
                //        e.printStackTrace();
                //  }
                //  }

            }
       // });
        }
      //  fixedThreadPool.shutdown();
    }

    @Override
    public List<FileInfoVo> getFileByIds(String fileIds) throws Exception {
        JSONArray jsonArray = JSONArray.parseArray(fileIds);
        //将数组中的只转换为Long类型
        for(int i = 0; i < jsonArray.size();i++){
            jsonArray.set(i,Long.parseLong(jsonArray.getString(i)));
        }
        List<FileInfo> fileInfos = fileRepo.findByIdIn(jsonArray);
        List<FileInfoVo> fileInfoVos = ModelChange.changeList(FileInfoVo.class, fileInfos);
        return fileInfoVos;
    }


    public static void main(String[] args) throws Exception {
        UUID uuid = UUID.randomUUID();
        int length = uuid.toString().replace("-","").length();
        InetAddress addr = InetAddress.getLocalHost();
        String ip=addr.getHostAddress().toString(); //获取本机ip
        String hostName=addr.getHostName().toString(); //获取本机计算机名称
        System.out.println(ip);
        System.out.println(hostName);
        Class cls = Class.forName("com.example.fms.utils.word");
        Method staticMethod = cls.getDeclaredMethod("stringToDate",String.class);
        Object chb = staticMethod.invoke(cls, "2016-12-12 00:00:00");

    }
}
