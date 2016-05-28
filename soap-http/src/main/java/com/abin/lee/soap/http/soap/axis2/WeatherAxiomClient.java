package com.abin.lee.soap.http.soap.axis2;

import java.util.Date;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties.ProxyProperties;

public class WeatherAxiomClient {

    private static EndpointReference targetEPR =
            new EndpointReference(
                    "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx");

    private static OMFactory fac = OMAbstractFactory.getOMFactory();
    static OMNamespace omNs = fac.createOMNamespace("http://WebXml.com.cn/", "tns");

    public static void main(String args[]) throws AxisFault{
        Date start=new Date();
        System.out.println("start:"+start);
        ServiceClient sender = new ServiceClient();

        /****************************City**************************************/
        //sender.setOptions(buildOptions("http://WebXml.com.cn/getSupportCity"));
        //OMElement result = sender.sendReceive(buildParam("getSupportCity",new String[]{"byProvinceName"},new String[]{"All"}));
        //查询本天气预报Web Services支持的国内外城市或地区信息
        /**************************************************************************/

        /****************************Province**************************************/
        //sender.setOptions(buildOptions("http://WebXml.com.cn/getSupportProvince"));
        //OMElement result = sender.sendReceive(null);
        //调用得到province方法,获得本天气预报Web Services支持的洲、国内外省份和城市信息
        /**************************************************************************/

        /****************************DataSet**************************************/
        //sender.setOptions(buildOptions("http://WebXml.com.cn/getSupportDataSet"));
        //OMElement result = sender.sendReceive(null);
        //获得本天气预报Web Services支持的洲、国内外省份和城市信息
        /**************************************************************************/


        /****************************Weather**************************************/
        //sender.setOptions(buildOptions("http://WebXml.com.cn/getWeatherbyCityName"));
        //OMElement result = sender.sendReceive(buildParam("getWeatherbyCityName",new String[]{"theCityName"},new String[]{"杭州"}));
        //根据城市或地区名称查询获得未来三天内天气情况、现在的天气实况、天气和生活指数
        /**************************************************************************/



        sender.setOptions(buildOptions("http://WebXml.com.cn/getWeatherbyCityNamePro"));
        OMElement result = sender.sendReceive(buildParam("getWeatherbyCityNamePro",new String[]{"theCityName","theUserID"},new String[]{"杭州","01"}));

        System.out.println(result);
        Date end=new Date();
        System.out.println("end:"+end);
        System.out.println("between:"+(end.getTime()-start.getTime()));
    }
    /**
     * @see 调用webservice得到天气预报支持的城市
     * @return
     */
    public static  OMElement buildParam(String method,String[] arg,String[] val) {
        OMElement data = fac.createOMElement(method, omNs);
        for(int i=0;i<arg.length;i++){
            OMElement inner = fac.createOMElement(arg[i], omNs);
            inner.setText(val[i]);
            data.addChild(inner);
        }
        return data;

    }


    /**
     * @see 设置连接属性
     * @return
     */
    public static Options  buildOptions(String action){
        Options options = new Options();
        options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        options.setAction("http://WebXml.com.cn/getSupportCity");
        options.setTo(targetEPR);
        options.setTransportInProtocol(Constants.TRANSPORT_HTTP);
        options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.
        options.setProperty(HTTPConstants.PROXY, buildProxy());
        options.setProperty(Constants.Configuration.HTTP_METHOD,HTTPConstants.HTTP_METHOD_POST);
        //options.setAction(action);
        return options;

    }

    /**
     * @see 设置代理属性
     * @return
     */
    public static ProxyProperties buildProxy(){
        ProxyProperties proxyProperties=new ProxyProperties();
        proxyProperties.setProxyName("www.webxml.com.cn");
        proxyProperties.setProxyPort(80);
        return proxyProperties;
    }


}