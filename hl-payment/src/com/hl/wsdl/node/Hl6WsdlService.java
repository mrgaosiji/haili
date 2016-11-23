package com.hl.wsdl.node;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.hl.web.form.CustomerForm;

public class Hl6WsdlService {

    private static final String targetURL = "http://172.29.0.1:8080/HL_DCME/app/rest/userPayinfo.do";
	
	private static final String getUserTokenURL = "http://172.29.0.1:8080/HL_DCME/user/authentication.do?userid=user&passwd=456";
	
	private static final String queryCompanyURL = "http://172.29.0.1:8080/HL_DCME/app/rest/companyInfoQuery.do";

	/**
	 * 远程调用服务获取付费用户信息
	 * @param clientNumber
	 */
	public static CustomerForm getUserInfoByNum(String clientNumber)
	{
		CustomerForm payInfo = new CustomerForm();
		
		return payInfo;
	}
	
	public static void  testGetToken() throws Exception
	{
     try {
			
			URL queryUserInfoURL = new URL(getUserTokenURL);
			
			HttpURLConnection httpConnection = (HttpURLConnection) queryUserInfoURL.openConnection();

			httpConnection.setRequestMethod("GET");
			
			httpConnection.setRequestProperty("Accept", "application/json");

			httpConnection.getResponseMessage();
			
			
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException(
						"HTTP GET Request Failed with Error code : "
								+ httpConnection.getResponseCode());
			}
		    
			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
		    
			
			String output;
			
            System.out.println("Output from Server:  \n");

            while ((output = responseBuffer.readLine()) != null) {
                   System.out.println(output);
            }

            httpConnection.disconnect();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}
	}
	
	public static void testCompanyInfoQuery() throws Exception 
	{
		
		try {
			
			URL queryUserInfoURL = new URL(queryCompanyURL);
			

			HttpURLConnection httpConnection = (HttpURLConnection) queryUserInfoURL.openConnection();

			httpConnection.setRequestMethod("POST");
			
			httpConnection.setRequestProperty("Accept", "application/json");
			
			httpConnection.setDoOutput(true);
			
			httpConnection.setRequestProperty("Authorization", "Bearer "+"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJZb3VyQ29tcGFueU9yQXBwTmFtZUhlcmUiLCJhdWQiOiJOb3RSZWFsbHlJbXBvcnRhbnQiLCJpYXQiOjE0NTY4OTkwNTksImV4cCI6MTQ1NzMzMTA1OSwiaW5mbyI6eyJ1c2VySWQiOiJ1c2VyIn19.iGeHPnyfepdDGGp7QfgxjZHaya8LGNsSAOsHV4u6mIc");
			
			
			DataOutputStream out = new DataOutputStream(httpConnection
	                .getOutputStream());
	        // The URL-encoded contend
	        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
	        
			String content = "userid=" + URLEncoder.encode("123", "UTF-8");
	        
	        content +="&passwd="+URLEncoder.encode("321", "UTF-8");;
	        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
	       out.writeBytes(content);

	       out.flush();
	       out.close(); 
			
		    httpConnection.getResponseMessage();
			
			
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException(
						"远程燃气公司服务异常 "+" HTTP GET Request Failed with Error code : "
								+ httpConnection.getResponseCode());
			}
		    
			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));
		    
			
			String output;
			
            System.out.println("Output from Server:  \n");

            while ((output = responseBuffer.readLine()) != null) {
                   System.out.println(output);
            }

            httpConnection.disconnect();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	} 
	
	
}
