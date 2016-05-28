package com.abin.lee.soap.http.soap.test;

import com.abin.lee.soap.common.util.JsonUtil;
import com.abin.lee.soap.http.util.HttpSoapUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import java.util.List;

/**
 * Created by tinkpad on 2016/5/14.
 */
public class CityGetTest {

    @Test
    public void testCityGet() throws DocumentException {
        String cityName = "北京";
        String soapResponse = HttpSoapUtil.create(cityName);
        System.out.println("soapResponse=" + soapResponse);

        List<String> result = HttpSoapUtil.parseSoap(soapResponse);
        System.out.println("result="+ JsonUtil.toJson(result));

        Document document = DocumentHelper.parseText(soapResponse);
        Element root = document.getRootElement();
//        root.addNamespace("namespace", "http://WebXml.com.cn/");
        List<Element> list = root.selectNodes("//Envelope/Body/getSupportCityResponse/getSupportCityResult");
        System.out.println("list="+ JsonUtil.toJson(list));

    }


}
