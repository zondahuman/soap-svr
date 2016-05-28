package com.abin.lee.soap.http.soap.axis2;

import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axiom.soap.SOAP12Constants;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.jaxws.Constants;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import javax.xml.namespace.QName;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-5-15
 * Time: 上午12:29
 * To change this template use File | Settings | File Templates.
 */
public class Axis2Util {
    private static final String address = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx";
    private static final String namespaceURI = "http://WebXml.com.cn/";
    private static final String method = "getSupportCity";
    private static final String param = "北京 ";
    private static final String soapAction = "http://WebXml.com.cn/getSupportCity";


    public static void main(String[] args) throws AxisFault {
        get();
    }

    public static String get() throws AxisFault {
        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = new Options();
        options.setTimeOutInMilliSeconds(100000);
        options.setTo(new EndpointReference(address));
        options.setAction(soapAction);
        options.setTransportInProtocol(Constants.SOAP_HTTP_BINDING);
//        options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
//        options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.
        options.setProperty(org.apache.axis2.Constants.Configuration.HTTP_METHOD,HTTPConstants.HTTP_METHOD_GET);
//        options.setProperty(org.apache.axis2.Constants.Configuration.HTTP_METHOD,HTTPConstants.HTTP_METHOD_POST);
        serviceClient.setOptions(options);
        Object[] params = new Object[] {param};
//        QName qName = new QName(address, method);
        QName qName = new QName(namespaceURI, method);
        Class[] classes = new Class[]{Object.class};
        Object[] omElement = serviceClient.invokeBlocking(qName, params, classes);
        System.out.println(omElement);
        return null;
    }
}
