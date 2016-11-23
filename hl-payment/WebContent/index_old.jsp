<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>海力燃气收费平台</title>

<link rel="shortcut icon" href="/hl-payment/res/images/HL2.ico"  />


<link href="/hl-payment/res/share.css" type="text/css" rel="stylesheet"/>

<link rel="stylesheet" href="/hl-payment/res/public.css"type="text/css"></link>
<link rel="stylesheet" href="/hl-payment/res/index.css"type="text/css"></link>
<link rel="stylesheet" type="text/css" href="/hl-payment/res/clicki.web.css" media="screen" />
<link rel="stylesheet" href="/hl-payment/res/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/res/clicki.loginandreg.css" media="screen" />

<link href="/hl-payment/res/layer/css/layer.css" rel="stylesheet" />

<script src="/hl-payment/jquery-easyui-1.4.4/jquery.min.js" type="text/javascript"></script>

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

							document
									.write("<form action=payment/firstPage.do name=formx1 style='display:none' enctype='multipart/form-data'>");
							document.write("</form>");

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

							var input_info = document.createElement("input");
							input_info.type = "text";
							input_info.name = "customerInfo";
							input_info.value = data.data;

							document.formx1.appendChild(input_info);

							document.formx1.method = "POST";

							document.formx1.charset = "UTF-8" ;
							
							document.formx1.submit();

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
<body>

<div class="placeholder"></div>

<div class="theCenterBox" >

<div style="margin-top:80px;"></div>

<style>
.getting {
    background: url(/hl-payment/res/images/contact3.png) no-repeat;
    padding: 4px 0 0 24px;
}
</style>

<div class="theLoginBootstrap">
<div id="layOne">
  <div class="theLoginArea" id="loginBox" style="position:relative;">
  <div class="slb">
  <div style="float:right"><a class="getting" href="/hl-payment/help.html" target="_blank">新手入门</a></div></div>
  <!--<div class="type_one cur_type"><div class="type_pic"></div></div>-->
  <img  src="/hl-payment/res/images/product_1000000211.jpg">
  <label class="loginTxt">燃气易支付缴费</label>
  <br></br>
    <p style="position: relative;">
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
					<label for="LoginForm_password">客户号：</label>
				</p>
				<div class="input-group input-group-lg" >
					
					<input type="text" autocomplete="on" id="client_number" style="width: 500px"
						class="form-control" placeholder="请输入你的燃气公司客户号" onblur="validInputData()"
						 maxlength="25"/>
					<div class="error_rem" id="billNoError" style="display:none">
					<img src="https://s.shfft.com/bill/static/images/icon_error.jpg">
					 你输入的字符非法，请认真填写!</div>
				</div>
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
					<div class="dengLu" style="margin-top:80px;width:400px;border:0px solid red;text-align:center;padding-top:20px;" >
					<input type="submit" name="payment" value="缴纳燃气费" id="payment_gasline"/>
					<p style="margin:18%"></p>
					</div>
					
</div>
                    
</div>
  </div>

			<div class="placeholder1">
				<div class="copyright" style="padding-top: 10px;">
					<!-- 
  				<div class="copyright_nav">
  					<!--<ul class="after">
  						 <li><a href="#">联系我们</a></li> 
  					</ul>-->
				</div>
				<p class="copyright_banquan">©2005-2016 海力
					版权所有，并保留所有权利。&nbsp;&nbsp;&nbsp;版本号:beta1.0.01&nbsp;&nbsp;&nbsp;ICP备案号：蜀ICP备50263615号</p>
			</div>

</div>


</body>
</html>