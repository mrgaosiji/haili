package test.com.hl.dchl.service.app;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.soap.SOAPException;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis2.databinding.types.xsd.DateTime;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hl.web.entity.ProductionOrdering;
import com.hl.web.form.CustomerForm;
import com.hl.web.form.CustomerTotalPayInfoForm;
import com.hl.web.form.HistoryAbDetailForm;
import com.hl.web.form.HistoryPayInfoForm;
import com.hl.web.form.PayInfoForm;
import com.hl.web.util.RpcServletTool;
import com.hl.wsdl.node.ThirdPartyService;

public class ClientRequestServiceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	
	private static final String targetURL = "http://localhost:8080/hl-payment/payment/userPayinfo.do";
	
	
	@Test
	public void test() {
		
		try {
		
			URL queryUserInfoURL = new URL(targetURL);

			HttpURLConnection httpConnection = (HttpURLConnection) queryUserInfoURL
					.openConnection();

			httpConnection.setRequestMethod("POST");
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
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
    public void testCallAsmx() throws RemoteException, ServiceException,
            MalformedURLException, SOAPException {        

        Map<String, String> params = new HashMap<String, String>();
        params.put("customerid", "03301040");

        ThirdPartyService service = new ThirdPartyService();
        
        
        String result = (String)RpcServletTool.callAsmxWebService(
              "http://223.85.248.171:2398/ws/Service.asmx",
                "http://tempuri.org/", "QueryCustomerInfoById", params,XMLType.XSD_STRING);

        
        
//        JSONObject jsonobj = new JSONObject(result);
        
        //String result = "{\"customer\":[{\"adr\":\"阳光。维多利亚3-1-13-3\",\"yhname\":\"熊淑文郭天珍\",\"yhcode\":\"03301040\",\"LandPhone\":\"15298015712\",\"abstractTotalMoney\":\"23.07\"}],\"historyAbstractDetail\":[{\"month\":\"201501\",\"abstractFee\":\"200.01\",\"addressDetail\":\"供销社三街区一单元2号\",\"beginVolume\":\"2381.01\",\"endVolume\":\"7261.02\"}],\"historyOne\":[{\"createTime\":\"20150932\",\"payFee\":\"45.08元\",\"payVolume\":\"1929.01\"}],\"historyThree\":[{\"createTime\":\"20151032\",\"payFee\":\"72.08元\",\"payVolume\":\"1929.01\"}],\"payInfo\":[{\"addressID\":\"123458\",\"meterSerialNo\":\"727281\",\"meterType\":\"pb\",\"meterName\":\"普表\",\"addressDetail\":\"供销社街区一单元一号\",\"currentMeterFee\":\"-123.01元\"},{\"addressID\":\"123458\",\"meterSerialNo\":\"727281\",\"meterType\":\"net\",\"meterName\":\"网络表\",\"addressDetail\":\"供销社街区二单元三号\",\"currentMeterFee\":\"45.01元\"}]}";     
        
        System.out.println(result);
        
        JSONObject jsonobj = JSONObject.fromObject(result);
		
        Map<String, Class> classMap = new HashMap<String, Class>();  
        
        classMap.put("customer", CustomerForm.class);
        classMap.put("historyAbstractDetail", HistoryAbDetailForm.class);
        classMap.put("historyOne", HistoryPayInfoForm.class);
        classMap.put("historyThree", HistoryPayInfoForm.class);
        classMap.put("payInfo", PayInfoForm.class);
         
        CustomerTotalPayInfoForm customerForm = (CustomerTotalPayInfoForm)JSONObject.toBean(jsonobj,CustomerTotalPayInfoForm.class,classMap);
        
        System.out.println(customerForm.getCustomer().get(0).getAdr());
 
        
    }
	
	
	@Test
    public void testCallAsmxInsert() throws RemoteException, ServiceException,
            MalformedURLException {        

		Date nowTime = new Date(System.currentTimeMillis());
		  SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String retStrFormatNowDate = sdFormatter.format(nowTime);
		  
        Map<String, String> params = new HashMap<String, String>();
        params.put("CUSTOMERID","265E4371-1B7C-411F-BDAE-CB81DAFB386B");//1
	    params.put("ORDERID","160309-1");//1
	    params.put("TOTALFEE","100");//1
	    params.put("CREATEDATE",retStrFormatNowDate);//1
		
	    params.put("addressID","D1FD5709-EBA2-40B2-8E36-0004096C4BCE");//2
	    params.put("meterSerialNo","1");//2
	    params.put("SERIALNUM","1");//2
	    params.put("FEE","100");//2
        

        ThirdPartyService service = new ThirdPartyService();
        
        
        short result = (short)RpcServletTool.callAsmxGenerateFileWebService(
              "http://192.168.5.32:2398/ws/Service.asmx",
              "http://tempuri.org/", "GenerateChargeInfo",params,XMLType.XSD_SHORT);

        //System.out.println(result);
        
        
//        JSONObject jsonobj = new JSONObject(result);
        
        //String result = "{\"customer\":[{\"adr\":\"阳光。维多利亚3-1-13-3\",\"yhname\":\"熊淑文郭天珍\",\"yhcode\":\"03301040\",\"LandPhone\":\"15298015712\",\"abstractTotalMoney\":\"23.07\"}],\"historyAbstractDetail\":[{\"month\":\"201501\",\"abstractFee\":\"200.01\",\"addressDetail\":\"供销社三街区一单元2号\",\"beginVolume\":\"2381.01\",\"endVolume\":\"7261.02\"}],\"historyOne\":[{\"createTime\":\"20150932\",\"payFee\":\"45.08元\",\"payVolume\":\"1929.01\"}],\"historyThree\":[{\"createTime\":\"20151032\",\"payFee\":\"72.08元\",\"payVolume\":\"1929.01\"}],\"payInfo\":[{\"addressID\":\"123458\",\"meterSerialNo\":\"727281\",\"meterType\":\"pb\",\"meterName\":\"普表\",\"addressDetail\":\"供销社街区一单元一号\",\"currentMeterFee\":\"-123.01元\"},{\"addressID\":\"123458\",\"meterSerialNo\":\"727281\",\"meterType\":\"net\",\"meterName\":\"网络表\",\"addressDetail\":\"供销社街区二单元三号\",\"currentMeterFee\":\"45.01元\"}]}";     
        
        System.out.println(result);
 
        
    }
	
	
	@Test
	public void testJSON() {
		
        String payInfo = "{\"customer\":[{\"name\":\"nihao\"}],\"historyAbstractDetail\":[],\"historyOne\":[],\"historyThree\":[],\"payInfo\":[]}";
    	
    	JSONObject obj = JSONObject.fromObject(payInfo);
    	
    	JSONArray objj =(JSONArray)obj.get("customer");
    	
    	System.out.println(objj.size());
	}
	
	@Test
	public void testSoapHeader() throws HttpException, IOException
	{
		String soapRequestData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
				"<soap:Header><MySoapHeader xmlns=\"http://tempuri.org/\">" +
				"<UserName>sch22l</UserName>" +
				"<PassWord>schlschl</PassWord>" +
				"</MySoapHeader></soap:Header>" +
				"<soap:Body>" +
				"<QueryCustomerInfoById xmlns=\"http://tempuri.org/\">" +
				"<customerid>10086</customerid>" +
				"</QueryCustomerInfoById>" +
				"</soap:Body>" +
				"</soap:Envelope>";
		
		//System.out.println(soapRequestData);

		PostMethod postMethod = new PostMethod("http://223.85.248.171:2398/ws/Service.asmx?op=QueryCustomerInfoById");
		
		byte[] b = soapRequestData.getBytes("utf-8");
		InputStream is = new ByteArrayInputStream(b,0,b.length);
		RequestEntity re = new InputStreamRequestEntity(is,b.length,"application/soap+xml; charset=utf-8");
		postMethod.setRequestEntity(re);
		
		HttpClient httpClient = new HttpClient();
		int statusCode = httpClient.executeMethod(postMethod);
		System.out.println("statusCode====="+statusCode);
		soapRequestData =  postMethod.getResponseBodyAsString();
		
		System.out.println(soapRequestData);
		
		// 调用请求END
	}
	
	@Test
	public void testCatchHeader() throws HttpException, IOException
	{

			

				try {

				
					throw new Exception("123");
					
				} catch (Exception excetion) {
	
					int k=0;
					while(k<3)
					{
						
							System.out.println("i值:"+k);
							try {

							
								throw new Exception("123");
								
							} catch (Exception l) {
				
								k++;
								
								continue;
							}
					}
				
		}
	}
	
	@Test
	public void testAppendHeader() throws HttpException, IOException
	{
		
		String str1 = "jii";
		String str2 = "123";
		
		StringBuffer strbuffer = new StringBuffer();
		strbuffer.append(str1+"\n");
		strbuffer.append(str2+"\n");
		System.out.println(strbuffer.toString());
		
	}
	
}
