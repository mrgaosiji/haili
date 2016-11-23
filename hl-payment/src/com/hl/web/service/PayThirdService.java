package com.hl.web.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import org.apache.axis2.databinding.types.xsd.DateTime;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.Now;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hl.common.base.BaseTxService;
import com.hl.web.entity.ChargeAccountCardRecord;
import com.hl.web.entity.ChargeAccountCardRecord2;
import com.hl.web.entity.CompanyInfo;
import com.hl.web.entity.ProductionOrdering;
import com.hl.web.entity.ProductionOrdering2;
import com.hl.web.form.OrderDetailForm;
import com.hl.web.form.OrderForm;
import com.hl.web.form.PayInfoForm;
import com.hl.web.sdk.SDKUtil;
import com.hl.web.util.MmToolkit;
import com.hl.web.util.RpcServletTool;
import com.hl.wsdl.node.Hl6WsdlService;

@Service("payservice")
public class PayThirdService extends BaseTxService{

	
	Logger logger = Logger.getLogger(PayThirdService.class);
	/**
	 * 燃气公司加载信息
	 * @param code
	 * @return companyInfo
	 */
	
	@SuppressWarnings("unchecked")
	public CompanyInfo getComInfoByCode(String code){
		List<CompanyInfo> list= hibernateTemplate.find("from CompanyInfo where companyCode='"+code+"'");
		if(list!= null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public int queryOrderMoney(String orderId)
	{
		List<ProductionOrdering> list= hibernateTemplate.find("from ProductionOrdering where id='"+orderId+"'");
		if(list!= null && list.size()>0){
			return (int) ((list.get(0).getTotalMoney())*100);
		}else{
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean isSureNotPayOrder(String orderId)
	{
		List<ProductionOrdering> list= hibernateTemplate.find("from ProductionOrdering where status=0 and id='"+orderId+"'");
		if(list!= null && list.size()>0){
			return true ;
		}else{
			return false;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public int queryOrderMoney2(String orderId)
	{
		List<ProductionOrdering2> list= hibernateTemplate.find("from ProductionOrdering2 where id='"+orderId+"'");
		if(list!= null && list.size()>0){
			return list.get(0).getTotalMoney().intValue()*100;
		}else{
			return 0;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public CompanyInfo getComInfoByOrderID(String orderID){
		
		List<ProductionOrdering> list_pro =  hibernateTemplate.find("from ProductionOrdering where id='"+orderID+"'");
		ProductionOrdering orderInfo = new ProductionOrdering();
		if(list_pro!= null && list_pro.size()>0){
			orderInfo = list_pro.get(0);
		}else{
			return null;
		}
		
		List<CompanyInfo> list= hibernateTemplate.find("from CompanyInfo where companyCode='"+orderInfo.getGasCode().trim()+"'");
		if(list!= null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> getRefundUnionByOrderID(String orderID){
		
		Map<String,Object> map =new HashMap<String,Object>(); 
		
		List<ProductionOrdering2> list_pro =  hibernateTemplate.find("from ProductionOrdering2 where id='"+orderID+"'");
		ProductionOrdering2 orderInfo = new ProductionOrdering2();
		if(list_pro!= null && list_pro.size()>0){
			orderInfo = list_pro.get(0);
		}else{
			return null;
		}
		
		CompanyInfo companyInfo = new CompanyInfo();
		List<CompanyInfo> list= hibernateTemplate.find("from CompanyInfo where companyCode='"+orderInfo.getGasCode().trim()+"'");
		if(list!= null && list.size()>0){
			companyInfo = list.get(0);
		}else{
			return null;
		}
		
		map.put("orderInfo", orderInfo);
		map.put("comapnyInfo", companyInfo);
		
		return map;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void saveProductionOrdering(ProductionOrdering orderForm)
	{
		hibernateTemplate.save("ProductionOrdering",orderForm);//充值账单
	}
	
	@SuppressWarnings("unchecked")
	public void saveChargeAccountCardRecord(ChargeAccountCardRecord chargeDetail) throws Exception
	{
		hibernateTemplate.save("ChargeAccountCardRecord",chargeDetail);//充值账单
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public String generateOrderingInfo(String paydetail,Double totalMoney,String companyBM,String customerID) throws Exception{
		
//		String orderid = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +String.format("%05d", new Random().nextInt(10000));//订单号

		String orderid = SDKUtil.generateOrderId();
		
		Double calMoney = totalMoney;//总金额	
        
		String belongCompany = companyBM;//公司编码
        
		String client_Number = customerID;//客户号
		
		ProductionOrdering orderInfo = new ProductionOrdering();
		
		Date date = new Date();
		
		String createTime_str = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		
		orderInfo.setId(orderid);
		orderInfo.setTotalMoney(calMoney);
		orderInfo.setClientNumber(client_Number);
		orderInfo.setGasCode(belongCompany);
		orderInfo.setCreateTime(createTime_str);
		orderInfo.setPayTime("");
		orderInfo.setBz("portal");//portal端
		orderInfo.setStatus(0);//订单生成未付款
		
		saveProductionOrdering(orderInfo);
		
		logger.info("Orderid:"+orderid+" | TotalMoney:"+calMoney);
		
	    JSONArray jsonArray = new JSONArray(paydetail);
	    
	    
	    
	    for(int i=0;i<jsonArray.length();i++){
	    	JSONObject obj = jsonArray.getJSONObject(i);
	    	ChargeAccountCardRecord chargeRecord= new ChargeAccountCardRecord();
	    	chargeRecord.setBz(obj.get("addressID").toString());//地址序号
	    	//chargeRecord.setChargeFee(Double.valueOf((StringUtils.isNumeric(obj.getString("userTotalChargeFee"))?obj.getString("userTotalChargeFee"):"0")));
	    	chargeRecord.setChargeFee(Double.valueOf((MmToolkit.isDouble((obj.getString("userTotalChargeFee").trim()))?obj.getString("userTotalChargeFee"):"0")));
	    	chargeRecord.setCreateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
	    	chargeRecord.setProductionOrdering(orderInfo);
	    	chargeRecord.setAddress(obj.getString("addressDetail"));
	    	chargeRecord.setMeter_number(obj.get("meterSerialNo").toString());//表序号
	    	chargeRecord.setStatus(0);
	    	saveChargeAccountCardRecord(chargeRecord);
	    	
	    	logger.info("AddressId:"+ obj.get("addressID").toString()+"| AddressDetail:"+obj.getString("addressDetail") +"| MeterId:"+obj.get("meterSerialNo").toString()+" | ChargeMoney:"+Double.valueOf((StringUtils.isNumeric(obj.getString("userTotalChargeFee"))?obj.getString("userTotalChargeFee"):"0")));
	    	
	    }

		return orderid+"|"+createTime_str;		
	}
	
	
	public List<OrderDetailForm> queryDetailByOrderID(String orderid)
	{
		
		List<ChargeAccountCardRecord> list = hibernateTemplate.find("from ChargeAccountCardRecord as A where productionOrdering.id=?",new Object[]{orderid});
		
		List<OrderDetailForm> detailist = new ArrayList<OrderDetailForm>();
		
		for(ChargeAccountCardRecord cardRecord:list)
		{
			OrderDetailForm payInfo = new OrderDetailForm();
			
			payInfo.setAddress(cardRecord.getAddress());
			payInfo.setChargeFee(String.valueOf((cardRecord.getChargeFee()))+" 元");
			payInfo.setMeterNumber(cardRecord.getMeter_number());
			payInfo.setClientNumber(cardRecord.getProductionOrdering().getClientNumber());
			payInfo.setOrderID(orderid);
			detailist.add(payInfo);
		}
		return detailist;
	}
	
	public boolean isPayStatusSuccessed(String orderid)
	{
		boolean isPaySuccess = false;
		
		List<ProductionOrdering> list = hibernateTemplate.find("from ProductionOrdering2 where id='"+orderid+"' "+ "and status=2");//status=2为已支付
		
		if (list.size() > 0) {
			isPaySuccess = true;
		}
		
		
		return isPaySuccess;
	}
	
	public void updatePayStatusSuccess(String orderid)
	{
		jdbcTemplate.update("update production_ordering set status=1 where  id='"+orderid+"'");	
	}
	
	public void updateHl6StatusSuccess(String orderid)
	{
		jdbcTemplate.update("update production_ordering set status=2 where  id='"+orderid+"'");
			
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void saveChargeAccountCardRecord2(ProductionOrdering orderInfo,Set chargeAccountCardRecordSet,String source_subject)
	{
		Iterator<ChargeAccountCardRecord> i = chargeAccountCardRecordSet.iterator();
		while(i.hasNext())
		{
			ChargeAccountCardRecord accountCardRecordInfo = (ChargeAccountCardRecord)i.next();
		    ChargeAccountCardRecord2 accountCardRecordInfo2 =new ChargeAccountCardRecord2();
		    
		    accountCardRecordInfo2.setAddress(accountCardRecordInfo.getAddress());
		    accountCardRecordInfo2.setBz(accountCardRecordInfo.getBz());
		    accountCardRecordInfo2.setChargeFee(accountCardRecordInfo.getChargeFee());
		    accountCardRecordInfo2.setCloseBillId(accountCardRecordInfo.getCloseBillId());
		    accountCardRecordInfo2.setCreateTime(accountCardRecordInfo.getCreateTime());
		    accountCardRecordInfo2.setStatus(2);
		    accountCardRecordInfo2.setMeter_number(accountCardRecordInfo.getMeter_number());
		    accountCardRecordInfo2.setOrderId(orderInfo.getId());
		    hibernateTemplate.save(accountCardRecordInfo2);
		    hibernateTemplate.delete(accountCardRecordInfo);
		}
		
		ProductionOrdering2 productionOrdering2 = new  ProductionOrdering2();
//		productionOrdering2.setBz(orderInfo.getBz());
		productionOrdering2.setBz(source_subject);
		productionOrdering2.setClientNumber(orderInfo.getClientNumber());
		productionOrdering2.setCreateTime(orderInfo.getCreateTime());
		productionOrdering2.setGasCode(orderInfo.getGasCode());
		productionOrdering2.setId(orderInfo.getId());
		productionOrdering2.setPayTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		productionOrdering2.setStatus(2);
		productionOrdering2.setTotalMoney(orderInfo.getTotalMoney());
		
		System.out.println("id:"+orderInfo.getId()+" total_money:"+orderInfo.getTotalMoney()+ " gas_code:"+orderInfo.getGasCode()+" pay_time"+orderInfo.getPayTime()+" client_number:"+orderInfo.getClientNumber()+" bz"+orderInfo.getBz());
		
		hibernateTemplate.save(productionOrdering2);
		
		hibernateTemplate.delete(orderInfo);
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void hl6UpdatePayStatus(String orderid,CompanyInfo companyInfo,String source_subject) throws RemoteException, MalformedURLException, ServiceException
	{
		List<ProductionOrdering> list = hibernateTemplate.find("from ProductionOrdering where id='"+orderid+"' "+ "and status=1");//已支付
		
	    if(list.size()<=0){logger.warn("订单号没有正确更新状态，查核："+orderid);return;}
		ProductionOrdering orderInfo= list.get(0);
		Set<ChargeAccountCardRecord> chargeAccountRecordSet =  orderInfo.getChargeAccountCardRecords();
		
		
		String customerNumber = orderInfo.getClientNumber();//客户号
		String orderId = orderInfo.getId();//orderID
		Double totalMoney = orderInfo.getTotalMoney();
		String payTime = orderInfo.getPayTime();//付款时间
		
		Iterator<ChargeAccountCardRecord> i = chargeAccountRecordSet.iterator();
		short isSuccessed = -1;
		while(i.hasNext())
		{
			ChargeAccountCardRecord accountCardRecordInfo = (ChargeAccountCardRecord)i.next();
		   
			Map<String, String> params = new HashMap<String, String>();
		    params.put("CUSTOMERID",customerNumber);//1
		    params.put("ORDERID",orderId);//1
		    params.put("TOTALFEE",totalMoney.toString());//1
		    
//			DateTime date=new DateTime(payTime);
//		    params.put("CREATEDATE",String.format("{0:d/M/yyyy HH:mm:ss}",date));//1
		    
		    Date nowTime = new Date(System.currentTimeMillis());
			  SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  String retStrFormatNowDate = sdFormatter.format(nowTime);
		    
		    params.put("CREATEDATE",retStrFormatNowDate);
		    params.put("meterSerialNo",accountCardRecordInfo.getMeter_number());//2
		    params.put("FEE",accountCardRecordInfo.getChargeFee().toString());//2
		    params.put("SERIALNUM",accountCardRecordInfo.getCloseBillId());//2
		    params.put("addressID",accountCardRecordInfo.getBz());//2

            isSuccessed = (short)RpcServletTool.callAsmxGenerateFileWebService(
			RpcServletTool.combineQrCustomerInfoURL(companyInfo.getCloudIp(),companyInfo.getPort()),
	        "http://tempuri.org/", "GenerateChargeInfo", params,XMLType.XSD_SHORT);
//		    isSuccessed = 0;//test by 2016-08-20

		}

		if(isSuccessed==0)
		{
			orderInfo.setStatus(2);
			
			saveChargeAccountCardRecord2(orderInfo,chargeAccountRecordSet,source_subject);
			
		}

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
	public void hl6AtmUpdatePayStatus(String orderid,CompanyInfo companyInfo,String source_subject) throws RemoteException, MalformedURLException, ServiceException
	{
		List<ProductionOrdering> list = hibernateTemplate.find("from ProductionOrdering where id='"+orderid+"' "+ "and status=1");//已支付
		
	    if(list.size()<=0){logger.warn("订单号没有正确更新状态，查核："+orderid);return;}
		ProductionOrdering orderInfo= list.get(0);
		Set<ChargeAccountCardRecord> chargeAccountRecordSet =  orderInfo.getChargeAccountCardRecords();
		
		
		String customerNumber = orderInfo.getClientNumber();//客户号
		String orderId = orderInfo.getId();//orderID
		Double totalMoney = orderInfo.getTotalMoney();
		String payTime = orderInfo.getPayTime();//付款时间
		
		Iterator<ChargeAccountCardRecord> i = chargeAccountRecordSet.iterator();
		short isSuccessed = -1;
		while(i.hasNext())
		{
			ChargeAccountCardRecord accountCardRecordInfo = (ChargeAccountCardRecord)i.next();
		   
			Map<String, String> params = new HashMap<String, String>();
		    params.put("CUSTOMERID",customerNumber);//1
		    params.put("ORDERID",orderId);//1
		    params.put("TOTALFEE",totalMoney.toString());//1
		    
//			DateTime date=new DateTime(payTime);
//		    params.put("CREATEDATE",String.format("{0:d/M/yyyy HH:mm:ss}",date));//1
		    
		    Date nowTime = new Date(System.currentTimeMillis());
			  SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  String retStrFormatNowDate = sdFormatter.format(nowTime);
		    
		    params.put("CREATEDATE",retStrFormatNowDate);
		    params.put("meterSerialNo",accountCardRecordInfo.getMeter_number());//2
		    params.put("FEE",accountCardRecordInfo.getChargeFee().toString());//2
		    params.put("SERIALNUM",accountCardRecordInfo.getCloseBillId());//2
		    params.put("addressID",accountCardRecordInfo.getBz());//2

		    isSuccessed = 0;//test by 2016-08-20

		}

		if(isSuccessed==0)
		{
			orderInfo.setStatus(2);
			
			saveChargeAccountCardRecord2(orderInfo,chargeAccountRecordSet,source_subject);
			
		}

	}
	

}
