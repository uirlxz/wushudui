package com.example.fms.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by www on 2016/9/1.
 */
public class LocationUtils {

    private static final Logger logger = LoggerFactory.getLogger(LocationUtils.class);

    public static class LocationClass{

        public String isp;
        public String country;
        public String region;
        public String city;

        public LocationClass(String isp, String country, String region, String city) {
            this.isp = isp;
            this.country = country;
            this.region = region;
            this.city = city;
        }
    }

    public static LocationClass getLocation(String ip){
        String isp = "";
        String country = "";
        String region = "";
        String city = "";

        String url = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode data = root.path("data");
            JsonNode ispNode = data.path("isp");
            JsonNode countryNode = data.path("country");
            JsonNode regionNode = data.path("region");
            JsonNode cityNode = data.path("city");
            isp = ispNode.asText();
            country = countryNode.asText();
            region = regionNode.asText();
            city = cityNode.asText();
        }catch (Exception e) {
            logger.error( "error:"+ e.toString());
        }

        return new LocationClass(isp, country, region, city);
    }
}
