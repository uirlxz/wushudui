package com.example.fms.api;

import com.example.fms.model.DownloadInfoEntity;
import com.example.fms.model.FileInfo;
import com.example.fms.repository.DownloadInfoRepository;
import com.example.fms.repository.FileInfoRepository;
import com.example.fms.utils.LocationUtils;
import com.example.fms.utils.UaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.fms.utils.IpUtils.getIpAddress;
import static com.example.fms.utils.LocationUtils.getLocation;
import static com.example.fms.utils.UaUtils.parseUa;


@RestController
public class img {

    private static final Logger logger = LoggerFactory.getLogger(img.class);

    static final byte[] bmpByte = new byte[]{
            0x42, 0x4d, 0x42, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x3e, 0x00, 0x00, 0x00, 0x28, 0x00,
            0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00,
            0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x04, 0x00, 0x00, 0x00, (byte)0xc4, 0x0e,
            0x00, 0x00, (byte)0xc4, 0x0e, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, (byte)0xff, (byte)0xff, (byte)0xff, 0x00, (byte)0x80, 0x00,
            0x00, 0x00
    };

    @Autowired
    private DownloadInfoRepository downloadInfoRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

//    @RequestMapping(value = "/img/{filename:^[0-9a-z]{32}.jpg$}")
    @RequestMapping(value = "")
    @ResponseBody
//    public ResponseEntity<?> getFile(@PathVariable String filename, HttpServletRequest request) {
    public ResponseEntity<?> getFile( HttpServletRequest request) {
        logger.debug("method : " + request.getMethod());
        if(!request.getMethod().equals("GET")){
            return ResponseEntity.ok().build();
        }
        System.out.println("已连接");
//        String uuid = filename.substring(0, filename.indexOf('.'));
        String uuid = "5fa0b73d4685489a8f78fb9e1dc483ac";
        logger.debug( "uuid:"+ uuid);

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentDispositionFormData("attachment", "5fa0b73d4685489a8f78fb9e1dc483ac.jpg");
        headers.setContentType(MediaType.IMAGE_JPEG);

        String ua = request.getHeader("User-Agent");
        logger.debug( "download ua:"+ ua);

        FileInfo fi = fileInfoRepository.findOneByUuid(uuid);
        if(fi == null) {
            logger.info("Invalid uuid:"+ uuid + ", User-Agent:" + ua);
            return new ResponseEntity<byte[]>(bmpByte, headers, HttpStatus.CREATED);
        }

        UaUtils.UaClass usClass = parseUa(ua);

        logger.debug( "browser:"+ usClass.browser);
        logger.debug( "browserVersion:"+ usClass.browserVersion);
        logger.debug( "system:"+ usClass.system);
        logger.debug( "systemVersion:"+ usClass.systemVersion);
        logger.debug( "device:"+ usClass.device);

        String ip = getIpAddress(request);
        logger.debug( "download ip:"+ ip);

        LocationUtils.LocationClass locationClass = getLocation(ip);
        logger.debug( "isp:"+ locationClass.isp);
        logger.debug( "country:"+ locationClass.country);
        logger.debug( "region:"+ locationClass.region);
        logger.debug( "city:"+ locationClass.city);


        try {
            downloadInfoRepository.save(new DownloadInfoEntity(uuid, ip, ua, usClass.browser,
                    usClass.browserVersion, usClass.system, usClass.systemVersion, usClass.device,
                    locationClass.isp, locationClass.country, locationClass.region, locationClass.city));
            return new ResponseEntity<>(bmpByte, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.debug( "error:"+ e.toString());
            return ResponseEntity.notFound().build();
        }
    }


}
