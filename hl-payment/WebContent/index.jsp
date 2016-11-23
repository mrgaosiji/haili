<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>海力燃气收费平台</title>

<link rel="shortcut icon" href="/hl-payment/res/images/HL2.ico"  />


<link href="/hl-payment/res/share.css" type="text/css" rel="stylesheet"/>


<link rel="stylesheet" type="text/css" href="/hl-payment/res/clicki.web.css" media="screen" />
<link rel="stylesheet" href="/hl-payment/res/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/res/clicki.loginandreg.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/hl-payment/res/css/modiprocess/jquery-confirm.css" media="screen" />


<link href="/hl-payment/res/layer/css/layer.css" rel="stylesheet" />

<link rel="stylesheet" href="/hl-payment/res/public.css"type="text/css"></link>
<link rel="stylesheet" href="/hl-payment/res/index.css"type="text/css"></link>
<script src="/hl-payment/jquery-easyui-1.4.4/jquery.min.js" type="text/javascript"></script>
<script src="/hl-payment/jquery-easyui-1.4.4/jquery-confirm.js" type="text/javascript"></script>
<script type="text/javascript" src="/hl-payment/res/jquery.cookie.js"></script>
<script type="text/javascript" src="/hl-payment/res/common.js"></script>
<script type="text/javascript" src="/hl-payment/res/global.js"></script>
<script type="text/javascript" src="/hl-payment/res/jquery.smart-form.js"></script>
<script src="/hl-payment/res/layer/js/loading-overlay.min.js"></script>




<style type="text/css">


select {
	padding: 3px;
	font-size: 14px;
	font-weight: bold;
	color: #555;
}

input {
	font-family: 'Microsoft YaHei';
	font-size: 12px;
}

.type_one {
	border-radius: 5px;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	width: 60px;
	height: 60px;
	border: 1px solid #adadad;
	float: left;
	cursor: pointer;
}

.cur_type {
	border: 1px solid #008dda;
}

.theLoginArea .cur_type .type_pic {
	background: #ff0000 ;
}

.error_rem{
    color: #0097E9;
    font-size: 12px;
    left: 5px;
    top: 28px;
}

#fixed-footer-wrap {
    width: 100%;
    height: 40px;
    background: #e8e8e8;
    border-top: solid 1px #CCC;
    position: fixed;
    bottom: 0px;
    z-index: 3;
    overflow: visible;
}



</style>


<script type="text/javascript">
var companyBM ='';
var provinceBM ='';
var cityBM ='';
var areaBM ='';
$(document).ready(function(){

	var company = $.cookie('company'); // 公司
	if(company==null)return;

	var province = $.cookie('province'); // 省
	var city = $.cookie('city'); // 市
	var area = $.cookie('area'); // 区域
	
	companyBM = company;
	provinceBM = province;
	cityBM = city;
	areaBM = area;
	
	
//	$.ajax(
//   {
//	url : "payment/testIP.do", //目标地址  
//	error: function(XMLHttpRequest, textStatus, errorThrown) 
//	{
		
//	},
//		success : function(data) 
//	{
//		alert(data.data);
//	}
//	});
});


$(document).keydown(function(event)
	{
		if (event.keyCode == 13) { //绑定回车 
			$('#payment_gasline').click();
		}
	});

	var iSubmit = false;

	function onClick(postData) {
		
		var target = $('#layOne');

		if ($('#billNoError').is(':visible'))
			return;

		if (iSubmit == true) {
			alert('已经递交，请勿重复递交!');
			return;
		}

		var postData = eval(postData);

		var companyBM = postData.company;

		var clientNB = client_number.value;

		if (companyBM == null | companyBM == '') {
			alert('未选择付款的燃气公司!');
			return;
		}

		if (clientNB == null | clientNB == '') {
			alert('客户号不能为空!');
			return;
		}

		var reg = /^[0-9]+$/;
		if (!reg.test(clientNB)) {
			alert("你输入的字符非法，请认真填写!");
		}

		iSubmit = true;

		target.loadingOverlay();

		$
				.ajax({
					url : "payment/authorize.do", //目标地址  
					type : 'post',
					data : {
						companyCode : companyBM,
						clientNumber : clientNB
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {

						if (target.hasClass('loading')) {
							target.loadingOverlay('remove');
						}

						alert("燃气公司BOSS系统连接失败，请联系管理员！错误响应码："
								+ XMLHttpRequest.status);

						iSubmit = false;

					},
					success : function(data, textStatus, jqXHR) {

						$.cookie('company', postData.company, {
							expires : 30
						});
						$.cookie('province', postData.province, {
							expires : 30
						});
						$.cookie('city', postData.city, {
							expires : 30
						});
						$.cookie('area', postData.region, {
							expires : 30
						});

						if (target.hasClass('loading')) {
							target.loadingOverlay('remove');
						}
						
						if (data.code == 0) {

							var test_obj = $.parseJSON(data.data);
							
							$.confirm({
			                    title: '用户缴费确认',
			                    content: '地址信息：'+test_obj.customer[0].adr+'的'+test_obj.customer[0].yhname +'用户,是否点击【下一步】，进行燃气充值，否则可自动取消操作',
			                    autoClose: 'cancel|30000',
			                    confirmButtonClass: 'btn-danger',
			                    confirmButton: '确认下一步',
			                    cancelButton: '取消',
			                        confirm : function() {

											
			                        	   document
													.write("<form action=payment/firstPage.do name=formx1 style='display:none' enctype='multipart/form-data'>");
											document.write("</form>");

											var input_info = document
													.createElement("input");
											input_info.type = "text";
											input_info.name = "customerInfo";
											
											input_info.value = data.data;

											document.formx1
													.appendChild(input_info);

											document.formx1.method = "POST";

											document.formx1.charset = "UTF-8";

											document.formx1.submit();

										},
										cancel : function() {
											//$.alert('Ben just got saved!');
										}
									});

							//var input1 = document.createElement("input");
							// 设置公司编码参数  
							//input1.type = "text";
							//input1.name = "companyBM";
							//input1.value = companyBM;

							//var input2 = document.createElement("input");
							// 设置客户编码参数  
							//input2.type = "text";
							//input2.name = "clientNumber";
							//input2.value = clientNB;

							//document.formx1.appendChild(input1);
							//document.formx1.appendChild(input2);

						}

						else if (data.code == -1) {
							alert(data.message);
						}

						iSubmit = false;
					}
				});

	}
</script>

</head>

<!--  <body style="background:url(/hl-payment/res/images/backgrounds/0.png) repeat";background-size:100%>-->
<body>

<!-- <div class="placeholder"></div>-->
<style type="text/css">
.header {
  padding:17px 0;
}

.header p {
    float: right;
    padding-top: 7px;
    font-size: 12px;
    line-height: 20px;
    color: #666;
}

.header .col {
    width: 916px;
    margin: 0 auto;
}
.header p .it {
    color: #009cde;
    font-weight: 700;
}

.it {
    font-style: italic;
}
</style>


<div class="header">
<div class="col">
<p><img src="/hl-payment/res/images/backgrounds/Phone_old_contact.png" width="25" height="15" alt=""> 有疑问？拨打<span class="it">800-8000-80000</span>咨询</p>
</div>
</div>

<style type="text/css" >

.banner {
    height: 336px;
    background: url(/hl-payment/res/images/backgrounds/bg_banner.jpg) no-repeat 60% center;
    color: #fff;
    background-size: cover;
}

.col {
    width: 916px;
    margin: 0 auto;
}

.banner h1 {
    padding: 55px 0 0 0;
    font-size: 46px;
    font-weight: 400;
}

.banner h3 {
    float: left;
    font-size: 20px;
    border: 1px solid #fff;
    border-radius: 5px;
    padding: 2px 10px;
    font-weight: 400;
}

</style>

<div class="banner">
        <div class="col">
            <h1>网上缴燃气费， 找海力易支付没错</h1>
            <h2>欠费查询，历史账单查询、安全身份登录，燃气充值即时上表</h2>
            <p>不管您有没有网站，收藏海力网上缴费站点，即可进行燃气支付，而他们可以用最常用的信用卡、借记卡或银行、微信账户等多种方式支付。燃气网上缴款就是这么简单。</p>
            <h3>无任何手续费</h3>
        </div>
</div>
 

<div class="theCenterBox" >

<div style="margin-top:80px;"></div>

<style>
.getting {
    background: url(/hl-payment/res/images/contact3.png) no-repeat;
    padding: 4px 0 0 24px;
    color:red;
}
</style>


<div class="theLoginBootstrap">
<div id="layOne">
  <div class="theLoginArea" id="loginBox" style="position:relative;">
  <div class="slb">
  <div style="float:right"><a class="getting" href="/hl-payment/help.html" target="_blank" >新手入门</a></div></div>
  <!--<div class="type_one cur_type"><div class="type_pic"></div></div>-->
  <br>
    <p style="position: relative;">
    <img  src="/hl-payment/res/images/product_1000000211.jpg">
    <label for="LoginForm_password">选择燃气公司：</label></p>
        <div class="theProvince" style="padding:15px">
                <form action="#" id="formContainer" class="form form-horizontal"></form>
        </div>
   
      
		<script>
            $(function () {
             var eles = [
            [
                  { ele: { type: 'select', name: 'province', title: '省:', withNull: true, items: [{ text: '四川', value: 'SiChuan', extendAttr: { id: 1000 } }] } },
                  { ele: { type: 'select', name: 'city', title: '市:', withNull: true, items: [{ "text": "成都", "value": "ChengDu", "extendAttr": { "id": "1000001", "parentId": "1000" } }, { "text": "达州", "value": "DaZhou", "extendAttr": { "id": "1000002", "parentId": "1000" } }, { "text": "眉山", "value": "MeiShan", "extendAttr": { "id": "2000001", "parentId": "2000" } }, { "text": "绵阳", "value": "MianYang", "extendAttr": { "id": "2000002", "parentId": "2000" } }] } },
                  { ele: { type: 'select', name: 'region', title: '区/县:', withNull: true, items: [{ "text": "天河区", "value": "TH", "extendAttr": { "id": "1000001001", "parentId": "1000001" } }, { "text": "海珠区", "value": "HZ", "extendAttr": { "id": "1000001002", "parentId": "1000001" } }, { "text": "越秀区", "value": "YX", "extendAttr": { "id": "1000001003", "parentId": "1000001" } }, { "text": "白云区", "value": "BY", "extendAttr": { "id": "1000001004", "parentId": "1000001" } }] } },
                  { ele: { type: 'select', name: 'company', title: '公司:', withNull: true, items: [{ "text": "燃气第一燃气公司", "value": "CDGAS", "extendAttr": { "id": "1000001001001", "parentId": "1000001001" } }] } }
            ]
      ];
             
            var bsForm = new BSForm({ eles: eles,autoLayout: true }).Render('formContainer', function (sf) {
            	
            	//编辑页面的绑定
                sf.InitFormData({
                    province: provinceBM,
                    city: cityBM,
                    region:areaBM,
                    company:companyBM
                });
                //必须先赋值再生成插件
                global.Fn.CascadeSelect({ targets: ['province', 'city', 'region','company'], primaryKey: 'data-id', relativeKey: 'data-parentId' });
            });
             
            
             
            $("#payment_gasline").bind('click',function () {
            	 
                   var postData=bsForm.GetFormData();
                   onClick(postData); 
            });
            
            
            
            });
        </script>
				<p style="position: relative;margin-top:20px">
				<img  src="/hl-payment/res/images/backgrounds/user_multiple_32.png">
			    <label for="LoginForm_password">填写认证信息：</label>
				</p>
				<div class="input-group input-group-lg" >
					
					<input type="text" autocomplete="on" id="client_number" style="width: 500px"
						class="form-control" placeholder="请输入你的燃气公司客户号、户主姓名或身份证号" onblur="validInputData()"
						 maxlength="25"/>
					<div class="error_rem" id="billNoError" style="display:none">
					<img src="/hl-payment/res/images/icon_error.jpg">
					 你输入的字符非法，请认真填写!</div>
				</div>
				
				<br>
				<br>
				<br>
				<br>
				<script type="text/javascript">
				
				function validInputData()
				{
					var clientNB = client_number.value;
					
					if(clientNB=='')return;
					
					var reg = /^[0-9]+$/;
					if (!reg.test(clientNB)) {
						$("#billNoError").show(); 
					}
					else{
						$("#billNoError").hide();
					}
					
				}
				</script>
				
				
				<!-- 
						 <input
							 name="client_number"
							id="client_number" class="form-control" maxlength="16" onkeydown="javascript:if(event.keyCode==13) $('#userSubmit').click();"/> 
					-->
					
				    
					
</div>
                    
</div>
</div>
    
    
 <style type="text/css">
.button01 {
  width: 200px;
  margin: 100px auto 20px auto;
}

.button01 a {
  display: block;
  height: 50px;
  width: 200px;
  
  /*TYPE*/
  color: white;
  font: 17px/50px Helvetica, Verdana, sans-serif;
  text-decoration: none;
  text-align: center;
  text-transform: uppercase;
  
  /*GRADIENT*/  
  background: #00b7ea; /* Old browsers */
  background: -moz-linear-gradient(top, #00b7ea 0%, #009ec3 100%); /* FF3.6+ */
  background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,#00b7ea), color-stop(100%,#009ec3)); /* Chrome,Safari4+ */
  background: -webkit-linear-gradient(top, #00b7ea 0%,#009ec3 100%); /* Chrome10+,Safari5.1+ */
  background: -o-linear-gradient(top, #00b7ea 0%,#009ec3 100%); /* Opera 11.10+ */
  background: -ms-linear-gradient(top, #00b7ea 0%,#009ec3 100%); /* IE10+ */
  background: linear-gradient(top, #00b7ea 0%,#009ec3 100%); /* W3C */
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#00b7ea', endColorstr='#009ec3',GradientType=0 ); /* IE6-9 */
}

.button01 a, p {
    -webkit-border-radius: 10px;
     -moz-border-radius: 10px;
          border-radius: 10px;
  

}

.button01 p {
  background: #222;
  display: block;
  height: 40px;
  width: 180px; 
  margin: -50px 0 0 10px;
  
  /*TYPE*/
  text-align: center;
  font: 12px/45px Helvetica, Verdana, sans-serif;
  color: #fff;
  
  /*POSITION*/
  position: absolute;
  z-index: -1;
  
  /*TRANSITION*/  
  -webkit-transition: margin 0.5s ease;
     -moz-transition: margin 0.5s ease;
       -o-transition: margin 0.5s ease;
      -ms-transition: margin 0.5s ease;
          transition: margin 0.5s ease;
}

/*HOVER*/
.button01:hover .bottom {
  margin: -10px 0 0 10px;
}

.button01:hover .top {
  margin: -80px 0 0 10px;
  line-height: 35px;
}

/*ACTIVE*/
.button01 a:active {
	  background: #00b7ea; /* Old browsers */
	  background: -moz-linear-gradient(top,  #00b7ea 36%, #009ec3 100%); /* FF3.6+ */
	  background: -webkit-gradient(linear, left top, left bottom, color-stop(36%,#00b7ea), color-stop(100%,#009ec3)); /* Chrome,Safari4+ */
	  background: -webkit-linear-gradient(top,  #00b7ea 36%,#009ec3 100%); /* Chrome10+,Safari5.1+ */
	  background: -o-linear-gradient(top,  #00b7ea 36%,#009ec3 100%); /* Opera 11.10+ */
	  background: -ms-linear-gradient(top,  #00b7ea 36%,#009ec3 100%); /* IE10+ */
	  background: linear-gradient(top,  #00b7ea 36%,#009ec3 100%); /* W3C */
	  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#00b7ea', endColorstr='#009ec3',GradientType=0 ); /* IE6-9 */

}

.button01:active .bottom {
  margin: -20px 0 0 10px;
}

.button01:active .top {
  margin: -70px 0 0 10px;
}

</style>
    
<div class="button01">
      <a  href="javascript:void(0)" id="payment_gasline">燃气缴纳  ►</a>
      <p class="top">点击按钮开始充值</p>
</div>

	
</div>


<br>
<br>
<br>
<br>
<br>
<br>
<br>

<div id="fixed-footer-wrap">
<div id="fixed-footer">        
     <p class="copyright_banquan">©2005-2016  版权所有，并保留所有权利。&nbsp;&nbsp;&nbsp;版本号:beta1.0.01&nbsp;&nbsp;&nbsp;ICP备案号：蜀ICP备50263615号</p>   
    </div>
</div>




</body>
</html>