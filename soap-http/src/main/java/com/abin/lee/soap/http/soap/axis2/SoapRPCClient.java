package com.abin.lee.soap.http.soap.axis2;

import com.abin.lee.soap.model.rpc.ProvinceBean;
import com.google.common.collect.Maps;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.jaxws.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-5-15
 * Time: 下午3:31
 * To change this template use File | Settings | File Templates.
 */
public class SoapRPCClient {
    private static final String address = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
    private static final String namespaceURI = "http://WebXml.com.cn/";
    private static final String methodName = "getSupportCity ";
    private static final String param = "北京 ";
    private static final String soapAction = "http://WebXml.com.cn/getSupportCity";

    public static void main(String[] args) throws AxisFault {
//        get1();
//        get2();
        get3();
//        get4();
//        get5();

    }

    public static void get5() throws AxisFault {
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = new Options();

        options.setAction(soapAction); //因为soap1.2规范必须指定action
        EndpointReference targetEPR  = new EndpointReference(address);
        options.setTo(targetEPR);
        options.setTimeOutInMilliSeconds(6000000000L);//设置超时时间
        options.setTransportInProtocol(org.apache.axis2.Constants.TRANSPORT_HTTP); //传输协议
        serviceClient.setOptions(options);
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace(namespaceURI,"");
        OMElement method = fac.createOMElement(methodName, omNs);
        OMElement wordKey = fac.createOMElement("byProvinceName",omNs);
        wordKey.setText(param);
        method.addChild(wordKey);
        method.build();
        OMElement result = serviceClient.sendReceive(method);
        System.out.println(result);
        System.exit(0);
    }

    public static void get4() throws AxisFault {
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = new Options();

        options.setAction("http://WebXml.com.cn/TranslatorString"); //因为soap1.2规范必须指定action
        EndpointReference targetEPR  = new EndpointReference("http://fy.webxml.com.cn/webservices/EnglishChinese.asmx?WSDL");
        options.setTo(targetEPR);
        options.setTimeOutInMilliSeconds(6000000000L);//设置超时时间
        options.setTransportInProtocol(org.apache.axis2.Constants.TRANSPORT_HTTP); //传输协议
        serviceClient.setOptions(options);
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace(
                "http://WebXml.com.cn/","");
        OMElement method = fac.createOMElement("TranslatorString", omNs);
        OMElement wordKey = fac.createOMElement("wordKey",omNs);
        wordKey.setText("随便");
        method.addChild(wordKey);
        method.build();
        OMElement result = serviceClient.sendReceive(method);
        System.out.println(result);
        System.exit(0);
    }

    public static void get3(){
        try {
            Map<String, String> request = Maps.newConcurrentMap();
            request.put("byProvinceName", param);
            Object[] inputParams = new Object[] {"byProvinceName="+param};
            Class<?>[] opReturnType = new Class[] { String[].class };
            RPCServiceClient serviceClient = new RPCServiceClient();//此处RPCServiceClient 对象实例建议定义成类中的static变量，否则多次调用会出现连接超时的错误。
            Options options = serviceClient.getOptions();
            EndpointReference targetEPR = new EndpointReference(address);
            options.setTo(targetEPR);
            options.setAction(soapAction);
            options.setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);//设置不受限制
            QName opQName = new QName(namespaceURI, methodName);
//            Object[] ret = serviceClient.invokeBlocking(opQName, opArgs, opReturnType);
//            System.out.println(ret);
            serviceClient.invokeBlocking(opQName, inputParams);
        } catch (AxisFault e) {
            e.printStackTrace();
        }
    }

    public static void get2() throws AxisFault {
        // 使用RPC方式调用WebService
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = serviceClient.getOptions();
        // 指定调用WebService的URL
        EndpointReference targetEPR = new EndpointReference(address);
        options.setTo(targetEPR);
        options.setAction(soapAction);
        options.setTransportInProtocol(org.apache.axis2.Constants.TRANSPORT_HTTP);
        options.setProperty(org.apache.axis2.Constants.Configuration.HTTP_METHOD,HTTPConstants.HTTP_METHOD_GET);
        options.setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);//设置不受限制.
        // 指定方法的参数值
        Object[] requestParam = new Object[]{param};
        // 指定方法返回值的数据类型的Class对象
        Class[] responseParam = new Class[]{String.class};
        // 指定要调用的getGreeting方法及WSDL文件的命名空间
        QName requestMethod = new QName(namespaceURI, methodName);
        // 调用方法并输出该方法的返回值
        try {
            System.out.println(serviceClient.invokeBlocking(requestMethod, requestParam, responseParam)[0]);
        } catch (AxisFault e) {
            e.printStackTrace();
        }
    }

    public static void get1() throws AxisFault {
        ServiceClient sender = new ServiceClient();
        Options option = new Options();
        option.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        option.setAction("http://WebXml.com.cn/getWeatherbyCityName");
        EndpointReference epfs = new EndpointReference(address);
        option.setTransportInProtocol(org.apache.axis2.Constants.TRANSPORT_HTTP);
        option.setTo(epfs);
        sender.setOptions(option);

        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMNamespace omNs = fac.createOMNamespace("http://WebXml.com.cn/", "");
        OMElement data = fac.createOMElement("getWeatherbyCityName", omNs);
        OMElement inner = fac.createOMElement("theCityName", omNs);
        inner.setText("长沙");
        data.addChild(inner);
        OMElement result = sender.sendReceive(data);
        System.out.println(result);
    }


}
