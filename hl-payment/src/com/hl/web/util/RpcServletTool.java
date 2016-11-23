package com.hl.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.SOAPException;


import org.apache.axis.client.Call;
import org.apache.axis.message.MessageElement;

import org.apache.axis.message.SOAPHeaderElement;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.client.methods.HttpUriRequest;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import com.hl.web.controller.PayManager;
import com.hl.web.entity.CompanyInfo;
import com.hl.wxin.common.MD5;

public class RpcServletTool {

	private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
	
	private final static String DEFAULT_ENCODING = "UTF-8";
	
	private Logger logger = Logger.getLogger(RpcServletTool.class);
	
	/**
	 * 
	 * @param serviceUrl
	 * @param serviceNamespace
	 * @param methodName
	 * @param params
	 * @return json
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 * @throws SOAPException 
	 */
	@SuppressWarnings("finally")
	public static Object callAsmxWebService(String serviceUrl, String serviceNamespace,
            String methodName, Map<String, String> params,QName qname )
            throws ServiceException, RemoteException, MalformedURLException {

		
		serviceUrl = "http://112.126.93.20/ws/service.asmx";
		
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(serviceUrl));
        call.setOperationName(new QName(serviceNamespace, methodName));
        
        call.setTimeout(5000); //timed out mileseconds

        
        ArrayList<String> paramValues = new ArrayList<String>();
        for (Entry<String, String> entry : params.entrySet()) {
            call.addParameter(new QName(serviceNamespace, entry.getKey()),
                    XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//            System.out.println(entry.getKey() + "|" + entry.getValue());
            paramValues.add(entry.getValue());
        }

        call.setReturnType(XMLType.XSD_SHORT);
        call.setReturnType(qname);
        call.setUseSOAPAction(true);
        call.setSOAPActionURI(serviceNamespace + methodName);
        
        /** 添加请求的header */
//        //父节点，根节点
//        SOAPHeaderElement el = new SOAPHeaderElement("http://tempuri.org","MySoapHeader");
//        //文本节点
//        MessageElement userME = new MessageElement(new QName("UserName"), "schl");
//        MessageElement passwdME = new MessageElement(new QName("PassWord"), "schlschl");
//        
//        el.addChild(userME);
//        el.addChild(passwdME);
//        
//       el.addChild(msgEl);
        
//        el.addChild(new MessageElement(new QName("userName"), "schl"));
        
//        el.addChild(new MessageElement(new QName("PassWord"), "schlschl"));
        
//        System.out.println("Header Content:"+el.toString());
//        
//        call.addHeader(el);
        
//        System.out.println("参数打印数据："+paramValues.toArray());
        
//        System.out.println("URI:" + call.getTransportForProtocol(arg0));
        

//        Message msg = call.getResponseMessage();
//        
//        SOAPEnvelope se = msg.getSOAPEnvelope();
//        
//        System.out.println("responseMessage:"+se.getHeader());
        
		String result = "";
		try {

			result = (String) call
					.invoke(new Object[] { paramValues.toArray() });
		} catch (Exception e) {
			
			Thread.sleep(2000);
			
			int i = 0;
			
			System.out.println("AXIS的超时处理："+e.getMessage()+"|"+e.toString()+":进行重连第一次");
			
			while(i<3){

				try {
					
					result = (String) call
							.invoke(new Object[] { paramValues.toArray() });
					break;
				} catch (Exception c) {
					System.out.println("AXIS的再次超时处理：" + c.getMessage() + "|"
							+ c.toString() + ":重新连接第" + (i + 1) + "次");
					Thread.sleep(1000);
					i++;
					continue;
				}
			}
			
		}
		finally{
			return result;
		}
	}
	
	/**
	 * 
	 * @param serviceUrl
	 * @param serviceNamespace
	 * @param methodName
	 * @param params
	 * @return json
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws MalformedURLException
	 */
	public static Object callAsmxGenerateFileWebService(String serviceUrl, String serviceNamespace,
            String methodName, Map<String, String> params,QName qname )
            throws ServiceException, RemoteException, MalformedURLException {

        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(serviceUrl));
        call.setOperationName(new QName(serviceNamespace, methodName));
        
        call.addParameter(new QName(serviceNamespace, "CUSTOMERID"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);           
        call.addParameter(new QName(serviceNamespace, "ORDERID"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);           
        call.addParameter(new QName(serviceNamespace, "TOTALFEE"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
        call.addParameter(new QName(serviceNamespace, "CREATEDATE"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
        call.addParameter(new QName(serviceNamespace, "addressID"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
        call.addParameter(new QName(serviceNamespace, "meterSerialNo"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
        call.addParameter(new QName(serviceNamespace, "SERIALNUM"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
        call.addParameter(new QName(serviceNamespace, "FEE"),
                org.apache.axis.encoding.XMLType.XSD_STRING,
                javax.xml.rpc.ParameterMode.IN);
        
      call.setReturnType(qname);
      call.setUseSOAPAction(true);
      call.setSOAPActionURI(serviceNamespace + methodName);
  
      
        Object[] obj = { params.get("CUSTOMERID"),  params.get("ORDERID"),params.get("TOTALFEE"),params.get("CREATEDATE"),params.get("addressID"),params.get("meterSerialNo"),params.get("SERIALNUM"),params.get("FEE")};
        return call.invoke(obj);
        
    }
	
	public static String combineQrCustomerInfoURL(String IP,int Port)
	{
	   System.out.println(IP+ ":"+Port);
       return "http://"+IP+":"+String.valueOf(Port)+"/ws/Service.asmx";
	}
	

	
	public static String postData(String urlStr, String data, String contentType) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            if(StringUtils.isNotBlank(contentType))
                conn.setRequestProperty("content-type", contentType);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if(data == null)
                data = "";
            writer.write(data); 
            writer.flush();
            writer.close();  
 
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
//            logger.error("Error connecting to " + urlStr + ": " + e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
            }
        }
        return null;
    }
	
	public static String payHttps(String url,String data) throws Exception {
	    //商户id
	    String MCH_ID = "1367943002";
	    //指定读取证书格式为PKCS12
	    KeyStore keyStore = KeyStore.getInstance("PKCS12");
	    String path ="D:"+File.separator+"apiclient_cert.p12";
	    //读取本机存放的PKCS12证书文件
	    FileInputStream instream = new FileInputStream(new File(path));
	    try {
	    //指定PKCS12的密码(商户ID)
	    
	    keyStore.load(instream, MCH_ID.toCharArray());
	    } finally {
	    instream.close();
	    }
	    SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCH_ID.toCharArray()).build();
	    //指定TLS版本
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	    sslcontext,new String[] { "TLSv1" },null,
	    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	    //设置httpclient的SSLSocketFactory
	    CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

	    return "123";
	    }
	
	
	public byte[] httpPost(String url, String entity) throws Exception {

		if (url == null || url.length() == 0) {
//			Log.e(TAG, "httpPost, url is null");
		return null;
		}	
		//HttpClient httpClient = getNewHttpClient();	
		//指定读取证书格式为PKCS12
		KeyStore keyStore = KeyStore.getInstance("PKCS12");	
		//读取本机存放的PKCS12证书文件
		
		FileInputStream instream = new FileInputStream(new File("mnt/sdcard/cert/apiclient_cert.p12"));	
		try {
		//指定PKCS12的密码(商户ID)
		keyStore.load(instream, "1367943002".toCharArray());
		} finally {
		instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom()
		.loadKeyMaterial(keyStore, "1367943002".toCharArray()).build();	
		//指定TLS版本
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
		sslcontext,new String[] { "TLSv1" },null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		//设置httpclient的SSLSocketFactory
		CloseableHttpClient httpclient = HttpClients.custom()
		.setSSLSocketFactory(sslsf).build();

		HttpPost httpPost = new HttpPost(url);

		try {
		httpPost.setEntity(new StringEntity(entity));
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		HttpResponse resp = httpclient.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
		
		return null;
		}

		return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {	
		
		e.printStackTrace();
		return null;
		}
		}
	
	
	public static byte[] postHttpsData(String urlStr, String data, CompanyInfo companyInfo) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {
		 
		 BufferedReader reader = null;
		 
		 String url = urlStr;
		 String entity = data;
		 
//		 String path ="D:"+File.separator+"apiclient_cert.p12";
		 String path = companyInfo.getWx_cert();
		 String mch_num= companyInfo.getMerchantNum();
		 
		 
		 if (url == null || url.length() == 0) {
//				Log.e(TAG, "httpPost, url is null");
			return null;
			}	
			//指定读取证书格式为PKCS12
			KeyStore keyStore = KeyStore.getInstance("PKCS12");	
			//读取本机存放的PKCS12证书文件

			FileInputStream instream = new FileInputStream(new File(path));	
			try {
			//指定PKCS12的密码(商户ID)
			keyStore.load(instream, mch_num.toCharArray());
			} finally {
			instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom()
			.loadKeyMaterial(keyStore, mch_num.toCharArray()).build();	
			//指定TLS版本
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
			sslcontext,new String[] { "TLSv1" },null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			//设置httpclient的SSLSocketFactory
			CloseableHttpClient httpclient = HttpClients.custom()
			.setSSLSocketFactory(sslsf).build();

			HttpPost httpPost = new HttpPost(url);

			try {
			httpPost.setEntity(new StringEntity(entity));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse resp = httpclient.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			
			return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());
			} catch (Exception e) {	
			
			e.printStackTrace();
			return null;
			}
    }
	

	

public static String getSign(Map<String,String> map){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,String> entry:map.entrySet()){
            if(entry.getValue()!=""){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + "90a8360a8e13da8276b1b9dac24fefd7";//密钥
        //Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result,"UTF-8").toUpperCase();
        //Util.log("Sign Result:" + result);
        return result;
    }

}
