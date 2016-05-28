package com.abin.lee.soap.http.util;

import com.abin.lee.soap.http.util.HttpClientUtil;
import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tinkpad on 2016/5/14.
 */
public class HttpSoapUtil {

    public static void main(String[] args) throws IOException {

    }


    public static String create(String cityName){
        HttpClient httpclient = HttpClientUtil.getHttpClient();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(
                    "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx");
            StringEntity requestBody = spliceXml(cityName);
            httpPost.setEntity(requestBody);
            httpPost.setHeader("Host", "www.webxml.com.cn");
            httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
            httpPost.setHeader("SOAPAction", "http://WebXml.com.cn/getSupportCity");
//            int requestLength = requestBody.toString().length();
//            System.out.println("requestLength="+requestLength);
//            httpPost.setHeader("Content-Length", String.valueOf(requestLength));

            HttpResponse httpResponse = httpclient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(httpResponse.getStatusLine());
            result =EntityUtils.toString(httpEntity, Consts.UTF_8);
            System.out.println(result);
            System.out.println("----------------------------------------");
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }


    public static StringEntity spliceXml(String cityName){
        StringBuilder sb=new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://WebXml.com.cn/\">");
        sb.append("<soapenv:Header/>");
        sb.append("<soapenv:Body>");
        sb.append("<web:getSupportCity>");
        sb.append("<!--Optional:-->");
        sb.append("<web:byProvinceName>");
        sb.append(cityName);
        sb.append("</web:byProvinceName>");
        sb.append("</web:getSupportCity>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");
        StringEntity stringEntity = new StringEntity(sb.toString(),"UTF-8");
        return stringEntity;
    }

    public static List<String> parseSoap(String soapResponse) throws DocumentException {
        List<String> result = Lists.newArrayList();
        Document document = DocumentHelper.parseText(soapResponse);
        Element root = document.getRootElement();
        root.addNamespace("namespace", "http://WebXml.com.cn/");
        List<Element> list = root.selectNodes("//namespace:getSupportCityResult");
        for (Element element : list) {
            //element.selectNodes(".//namespace:key")获取当前节点下元素key的值；element.selectNodes("//namespace:key")获取所有元素为key的值；记得加“//namespace:”
            List<Element> keys = element.selectNodes(".//namespace:string");
            for (Element key : keys) {
                result.add(key.getTextTrim());
            }
        }
        return result;
    }
    /**
     * 通过SaxReader方式解析Xml文件
     * @param filename
     * @throws Exception
     */
    public static void parseXmlSax(String soapResponse) throws Exception{

        Document document = DocumentHelper.parseText(soapResponse);
        Element root = document.getRootElement();
        System.out.println("根节点 为　：　" + root.getName());

        Element elm = root.element("Body");
        // 获取Body下的所有子节点
        List<?> bodyChild = elm.elements();
        // 遍历所有的AccountManagementResponse当前节点
        for(Iterator<?> it = bodyChild.iterator();it.hasNext();){
            Element elm1= (Element) it.next();

            System.out.println(elm1.getName());

            List<?> responseChild = elm1.elements();
            // 遍历AccountManagementResponse节点下的所有节点
            for(Iterator<?> it1 = responseChild.iterator();it1.hasNext();){
                Element elm2= (Element) it1.next();
                // 取得最后节点下的名称和值
                System.out.println(elm2.getName() + " ===== " + elm2.getText());

            }

        }
    }


}
