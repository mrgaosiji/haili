<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut icon" href="/hl-payment/res/images/HL2.ico"  />
<link rel="stylesheet" type="text/css" href="/hl-payment/jquery-easyui-1.4.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/jquery-easyui-1.4.4/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/res/web.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/res/pay.css">

<script type="text/javascript" src="/hl-payment/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/hl-payment/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script src="/hl-payment/res/common.js" type="text/javascript"></script>
<script src="/hl-payment/res/map.js" type="text/javascript"></script>
<script src="/hl-payment/jquery-easyui-1.4.4/jquery.qrcode.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/hl-payment/pages/payment-process/pay-casher-third.js"></script>
<link rel="stylesheet" href="/hl-payment/res/sui.css">

<title>收银台</title>

<style>
.top{background-color:#f2f2f2;width:100%;}

.top-title {
    float: left;
    font-size: 12px;
    color: #6e6e6e;
    line-height: 36px;
    padding-left: 10px;
}

.top-content{width:1190px;margin:0px auto;height:36px;}

</style>

<script type="text/javascript">

 var i;
 
 function fresh()
 {
   
   //window.location.reload();
  
 }

 //setTimeout('fresh()',2000);

</script>
</head>
<body>

<div class="top" style="wideth:100%">
					<div class="top-content after">
						<div class="top-title">	
									<b>尊敬的燃气用户</b>
									<!--<strong style="color:red">${customerName}</strong>-->
									,您好，请选择第三方支付网关支付!
									<a href="/" target="_self" style="color:red;text-decoration:underline">退出</a>
						</div>
					</div>
</div>

<!-- header -->
<div id="header">

  <div class="logo">
    <img src="/hl-payment/res/images/HENL_CENTER.png"></img>
  </div>
  <a href="http://www.xzmjzctdyg2.cn.qiyeku.com/" class="aProblem">支付遇到问题?</a>
</div>
<!--header-->


<div class="width1003">

<div class="sui-steps">
			<div class="pay">
			<div class="wrap">
				<div class="finished">
					<label>
						<span class="round">&#10004;</span>
						<span>选择燃气用户</span>
					</label>
					<i class="triangle-right-bg"></i>
					<i class="triangle-right"></i>
				</div>
			</div>
			<div class="wrap">
				<div class="finished">
					<label>
						<span class="round">&#10004;</span>
						<span>缴费金额充值</span>
					</label>
					<i class="triangle-right-bg"></i>
					<i class="triangle-right"></i>
				</div>
			</div>
			<div class="wrap">
				<div class="current">
					<label>
						<span class="round">3</span>
						<span>支付中心收费</span>
					</label>
					<i class="triangle-right-bg"></i>
					<i class="triangle-right"></i>
				</div>
			</div>
			<div class="wrap">
				<div class="todo">
					<label>
						<span class="round">4</span>
						<span>充值成功</span>
					</label>
				</div>
			</div>
		</div>
		</div><!-- /.sui-steps END --> 

<div class="pay_infor">
    <div class="infor_box">
      <span>你一共需支付</span>
      <span id="amount"><font style="font-size:15px;font-weight:bold;color:#f60;"></font></span>
      <span>元</span>
      <span class="helpInfo">
        <a href="javascript:void(0)" onMouseOver="showInfo()" onMouseOut="hideInfo()">查看详单</a>
     <u style="display:none" class="helpDelimiterInfo"> 
     <div  id="booking_detail">
     <table id="datagrid_booking" title="详单明细" style="width:620px;height:250px"
			data-options="singleSelect:true,collapsible:false,url:'datagrid_data1.json',method:'get'">
	</table>
	</div>
	</u>
      </span> 
      <label><span>您应付金额：</span><span id="amount2"><font class="cOrange" style="font-size: 18px;" id="amount2"></font>&nbsp;&nbsp;元</span> </label>
      
    <div class="clear"></div>
    </div>
</div>

<div class="pay_mode">
<div class="pay_mode_nav">
<span>请选择支付方式</span>
</div>
<div class="bank_list savings_card">
<input id="pay_bank"  name="pay_bank"  style="display: none"/>
<h6><label>银联支付</label><span style="font-weight:normal">一步到位</span> </h6>
<ul class="sel_list">
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_UNION" value="UNIONPAY" onclick="bankadio(this.value)">
            <a class="DEBITCARD_UNIONPAY" href="#" onclick="imageSel('DEBITCARD_UNIONPAY');return false;"></a>
          </label>
        </li>
        
        
</ul>
<h6><label>网银支付</label><span style="font-weight:normal">需开通网银</span> </h6>
<ul class="sel_list">
      <li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_ICBC" value="ICBC-DEBIT" onclick="bankadio(this.value)">
            <a class="ICBC" href="#" onclick="imageSel('ICBC');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_ABC" value="ABC"  onclick="bankadio(this.value)">
            <a class="ABC" href="#" onclick="imageSel('ABC');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_CMBCHINA" value="CMB-DEBIT"  onclick="bankadio(this.value)">
            <a class="CMBCHINA" href="#" onclick="imageSel('CMBCHINA');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_CCB" value="CCB-DEBIT"  onclick="bankadio(this.value)">
            <a class="CCB" href="#" onclick="imageSel('CCB');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_BOC" value="BOC-DEBIT"  onclick="bankadio(this.value)">
            <a class="BOC" href="#" onclick="imageSel('BOC');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_BOCO" value="COMM-DEBIT"  onclick="bankadio(this.value)">
            <a class="BOCO" href="#" onclick="imageSel('BOCO');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_CIB" value="CIB"  onclick="bankadio(this.value)">
            <a class="CIB" href="#" onclick="imageSel('CIB');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_CEB" value="CEB-DEBIT"  onclick="bankadio(this.value)">
            <a class="CEB" href="#" onclick="imageSel('CEB');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_SPDB" value="SPDB-DEBIT"  onclick="bankadio(this.value)">
            <a class="SPDB" href="#" onclick="imageSel('SPDB');return false;"></a>
          </label>
        </li>
            
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_CMBC" value="CMBC"  onclick="bankadio(this.value)">
            <a class="CMBC" href="#" onclick="imageSel('CMBC');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_ECITIC" value="CITIC-DEBIT"  onclick="bankadio(this.value)">
            <a class="ECITIC" href="#" onclick="imageSel('ECITIC');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_POST" value="PSBC-DEBIT"  onclick="bankadio(this.value)">
            <a class="POST" href="#" onclick="imageSel('POST');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_PAB" value="SPA-DEBIT"  onclick="bankadio(this.value)">
            <a class="PAB" href="#" onclick="imageSel('PAB');return false;"></a>
          </label>
        </li>
      
      	<li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_GDB" value="GDB-DEBIT"  onclick="bankadio(this.value)">
            <a class="GDB" href="#" onclick="imageSel('GDB');return false;"></a>
          </label>
        </li>
      
</ul>

<h6><label>快捷支付</label><span style="font-weight:normal">一步验证，无需网银!</span> </h6>
<ul class="sel_list">
        <li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_ICBC" value="WEIXINPAY" onclick="bankadio(this.value)">
            <a class="WX_TENPAY" href="#" onclick="imageSel('WX_TENPAY');return false;"></a>
          </label>
        </li>
        
        <li>
          <label>
          	<input type="radio" name="paymentInfo.bankSelect" id="bank_ICBC" value="ICBC-ZHIFU" onclick="bankadio(this.value)">
            <a class="AliPay01" href="#" onclick="imageSel('AliPay01');return false;"></a>
          </label>
        </li>
</ul>
<div class="confirm_pay">
 		<a href="javascript:void(0)" onclick="pay();" class="confirm_pay_btn" ></a>
 </div>

</div>

</div>


<div class="noProblem">
<h2>支付遇到的问题</h2>
<dl>
<dt>银联支付支持哪些银行？</dt>
<dd>答：网银支付需要提前开通网银，可以选择个人网上银行（支持工商银行、农业银行、招商银行、建设银行、中国银行、交通银行、兴业银行、光大银行等在内的20多家银行）
和企业网上银行（工商银行、农业银行、招商银行、建设银行、光大银行、中国银行等带有银联标志的银行卡或信用卡）进行支付。</dd>
<dd></dd>
</dl>

<dl>
<dt>账户支付支持哪些账户？</dt>
<dd>答：账户支付可以选择易宝、支付宝、财付通和沃支付多种账户进行支付。</dd>
<dd></dd>
</dl>
<div class="footerBar"></div>
</div>

</div>



<div class="up-box w600" style="display:none;z-index: 20000; position: fixed; left: 420px; top: 230px;" id="waitpay_online" dhxbox="1">
<div class="up-box-hd">网上支付提示</div>

<div class="up-box-bd">
<div class="up-con clearfix"><!-- <span class="icon i-work"></span> -->
<span style="vertical-align:middle"></span>
<div class="r-txt">

<p class="ft14"><img src="/hl-payment/res/images/working.gif">支付完成前，请不要关闭此支付验证窗口。<br clear="none">
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp支付完成后，请根据您支付的情况点击下面按钮。</p>
</div>
</div>

<div class="lay-btn">
<a href="#" class="btn92" id="online_have_question" shape="rect">支付遇到问题</a>
<a href="#" class="btn92s" id="finish_pay_order" shape="rect">支付完成</a>
</div>
</div>
</div>

<div class="up-box w600" style="display:none;z-index: 20000; position: fixed; left: 420px; top: 100px;" id="weixin_pay_qr" dhxbox="1">
<div class="up-box-hd">微信二维码支付</div>
<div class="up-box-bd">
<div style="text-align:center;">扫一扫支付燃气款</div>
<div style="text-align:center;"><span id="wexin_total"><font style="font-size:100px;font-weight:bold;color:#f60;"></font></span></div>

<style>
.qrcode-loading {
    position: absolute;
    top: 36%;
    left: 38%;
}

.qrcode-img-area {
    width: 220px;
    margin: 0 auto;
    position: relative;
    height: 185px;
}

.ui-loading {
    width: 50px;
    height: 50px;
    background-repeat: no-repeat;
    background-image: url(/hl-payment/res/images/qr_code/M9UQl3TuH.gif);
    text-align: center;
    line-height: 50px;
    font-size: 11px;
    color: #777;
}
</style>

 <div id="qrcode-waiting">
<!--<div class="qrcode-img-area">
  <div class="ui-loading qrcode-loading">加载中</div>
</div> -->
</div>


<div  class="up-con clearfix" style="text-align:center">
<div id="qr_code"></div>
<div style="color: #666;margin-top:20px">
<span style="font-size: 14px;width:100%; height:35px; line-height:20px; text-align:center; float:right;"><img src="/hl-payment/res/images/qr_code/T1bdtfXfdiXXXXXXXX.png" style="float:;position:relative">
打开手机微信 扫一扫继续付款<br clear="none">
支付完成前，请不要关闭此支付验证窗口</span>
</div>
</div>

<!-- <div class="lay-btn">
<a href="#" class="btn92" id="online_have_question" shape="rect">支付遇到问题</a>
<a href="#" class="btn92s" id="finish_pay_order" shape="rect">支付完成</a>
</div> -->
</div>
</div>


<div>
	<div class="copyright" style="padding-top:100px;">
  				<div class="copyright_nav">
  					<ul class="after">
  						<!-- <li><a href="#">联系我们</a></li> -->
  					</ul>
  				</div>

  				<p class="copyright_banquan" style="text-align:center">@2005-2016 海力 <a href="#" style="color:red">帮助中心</a>&nbsp;&nbsp;&nbsp;ICP备案号：蜀ICP备50263615号&nbsp;&nbsp;&nbsp;</p>
  			</div>

</div>
<div class="mask" id="ztky_mask" style="display:none;"></div>
</body>
</html>