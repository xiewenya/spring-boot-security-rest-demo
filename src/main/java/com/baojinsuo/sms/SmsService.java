package com.baojinsuo.sms;

import com.baojinsuo.base.ResponseCode;
import com.baojinsuo.common.exception.BaseException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * Created by bresai on 2016/10/11.
 */


public class SmsService {
    String url = "http://sendcloud.sohu.com/smsapi/send";
    String smsKey = "***";
    Map<String, String> templateRelation;

    public SmsService() {
        templateRelation = new HashMap<>();
        templateRelation.put("register", "1");
        templateRelation.put("resetPassword", "2");
    }

    public void sendSms(String smsCode, String mobile, String template){
        // 填充参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("smsUser", "***");
        params.put("templateId", templateRelation.get(template));
        params.put("msgType", "0");
        params.put("phone", mobile);
        params.put("vars", "{\"code\":\""+ smsCode + "\"}");

        // 对参数进行排序
        Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                // 忽略大小写
                return arg0.compareToIgnoreCase(arg1);
            }
        });
        sortedMap.putAll(params);

        // 计算签名
        String sig = signMd5(sortedMap);

        // 将所有参数和签名添加到post请求参数数组里
        List<NameValuePair> postparams = constructPostParams(sortedMap, sig);

        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postparams, "utf8"));
            CloseableHttpClient httpClient;
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(100000).build();
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
            EntityUtils.consume(entity);
        } catch (Exception e) {
            throw new BaseException(ResponseCode.sms_send_failed);
        } finally {
            httpPost.releaseConnection();
        }
    }

    private List<NameValuePair> constructPostParams(Map<String, String> sortedMap, String sig) {
        List<NameValuePair> postparams = new ArrayList<NameValuePair>();
        for (String s : sortedMap.keySet()) {
            postparams.add(new BasicNameValuePair(s, sortedMap.get(s)));
        }
        postparams.add(new BasicNameValuePair("signature", sig));
        return postparams;
    }

    private String signMd5(Map<String, String> sortedMap) {
        StringBuilder sb = new StringBuilder();
        sb.append(smsKey).append("&");
        for (String s : sortedMap.keySet()) {
            sb.append(String.format("%s=%s&", s, sortedMap.get(s)));
        }
        sb.append(smsKey);
        return DigestUtils.md5Hex(sb.toString());
    }
}
