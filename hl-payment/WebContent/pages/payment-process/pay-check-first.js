$(document).ready(function(){

	$('#biz_qrcode_a').hover(function(){
	        $('#biz_qrcode_div').show();
	    }, function(){
	        $('#biz_qrcode_div').hide();
	    });
	
	
	// 验证码校验
	$("#captchaInput").blur(function() {
		if ($(this).val() == "") {
			$("#captchaError").text("请输入验证码");
			$("#captchaError").show();
		}else{
			$("#captchaError").hide();
		}
	});
	
	reloadCaptcha();
	
	$('#postOne').unbind('click').click(function () {
		  doCashPost();
  	});
	
//	$('#tkm').modal();

});


/**
 * 验收确认
 */
function check() {
	
	var data = $("#form1").serialize();
  	$.ajax({
		url : "/hl-payment/payment/check.do",
		data : data,
		error:function(result){
			alert("error:"+result);
		},
		success : function(resp) {
			$("#errorMsg").hide();
			if (resp.code == 0) {
				location = "/hl-payment/payment/secondConfirm.do";
			} else {
					$("#errorMsg").text(resp.message);
					$("#errorMsg").show();
     			    reloadCaptcha();
				}
		}
	});
 
}



function refresh(){
    reloadCaptcha();
	$("#errorMsg").hide();
}

//刷新验证码
function reloadCaptcha() {

	jQuery("#captchaImg").attr("src",
	"/hl-payment/entry/captcha.do?date=" + new Date());

}



function historyQuery(txt)
{
	
	return '<ul class="nav nav-tabs">'+
	  '<li  class="active"><a href="#one" data-toggle="tab">近一个月</a></li>'+
	  '<li><a href="#three" data-toggle="tab">近三个月</a></li>'+
	  '</ul>'+
	  '<div id="myTabContent" class="tab-content">'+
	   '<div class="tab-pane fade in active" id="one">'+
	   '<table class="table table-bordered"><tr><td>发生日期</td><td>发生日期</td><td>充值金额</td><td>购买放量</td><td>充值状态</td></tr></table>'+
	   '</div>'+
	   '<div class="tab-pane fade" id="three">'+
	   '<table class="table table-bordered"><tr><td>发生日期</td><td>发生日期</td><td>充值金额</td><td>购买放量</td><td>充值状态</td></tr></table>'+
	   '</div>'+
	'</div>';

}

/**
 *订单生成，并且支付 
 */
var iSubmit = false;


function checkSubmit(customerCode)
{

}

function doCashPost(customerCode)
{
	
	var target = $('#submitOrder_1');

	if (iSubmit == true) {

		alert('不要重复递交你的订单，谢谢!');
		return;
	}
	
	 var datagrid_info = $('#tt').bootstrapTable('getData');

	 var customerCode = $('#customerCode').val();
	 
	 var companyCode = $('#companyCode').val();
	 
	 var datagrid_field_info = [];
	 
	 for(var payInfo in datagrid_info)
		{
			
			datagrid_field_info.push({
				'"addressID"' : ("\""+datagrid_info[payInfo].addressID+"\"")
				,
				'"meterSerialNo"': ("\""+datagrid_info[payInfo].meterSerialNo+"\"")
				,
				'"addressDetail"' : ("\""+datagrid_info[payInfo].addressDetail+"\"")
				,
				'"currentMeterFee"' : ("\""+datagrid_info[payInfo].currentMeterFee+"\"")
				,
				'"userTotalChargeFee"': ("\""+datagrid_info[payInfo].userTotalChargeFee+"\"")
				,
				'"meterType"': ("\""+datagrid_info[payInfo].meterType+"\"")
			});
		}
		
	var orderForm = obj2Str(datagrid_field_info);
	
	var data_Confirm_Cash = $('#J_RealPay').html();

	var check_code = $("#form1").serialize();
	
	if(parseFloat(data_Confirm_Cash)<=0.00){alert("请对气表进行充值");return;}
	

	//alert(data_Confirm_Cash);
	
	target.loadingOverlay();
	
	iSubmit = true;
	
	
	$.ajax({
		url : "/hl-payment/payment/checkSecond.do",
		data : {
			orderForm : orderForm,// 明细
			totalMoney : data_Confirm_Cash,// 金额
			check_code : check_code,//校验码
			customerCode:customerCode,
			companyCode:companyCode
		},
		
		error:function(XMLHttpRequest, textStatus, errorThrown) {

			target.loadingOverlay('remove');
			
			alert("生成订单异常,请联系管理员！错误响应码：" + XMLHttpRequest.status);

			iSubmit = false;
			
		},
		success : function(result) {
			
			
			$("#errorMsg").hide(); 
				
//			var str_data =	String(result.data);
			
//			var order_id =str_data.substring(0,str_data.indexOf("|"));
			
//			var txnTime = str_data.substring(str_data.indexOf("|")+1);
			
			
			target.loadingOverlay('remove');
			
			if (result.code == 0) {

				var str_data = result.data;

				var order_id = str_data[0];
				
				var txnTime = str_data[1];
				
               	var URL_TRANS = "/hl-payment/payment/chargeOrder.do?order_id="+order_id+"&txnTime="+txnTime;
               	
               	location = encodeURI(URL_TRANS); //"/hl-payment/payment/chargeOrder.do?order_id="+order_id+"&total_money="+parseInt(data_Confirm_Cash*100)+"&txnTime="+txnTime;

			} 
			else if(result.code==-2){

				alert(result.message);
				
			}
			else if(result.code==601){

				alert(result.message);
				
				location = "/hl-payment/index.jsp";
				
			}
			else {
					$("#errorMsg").text(result.message);
					$("#errorMsg").show();
     			    reloadCaptcha();
				}
			
			iSubmit = false;

		}
	});
	
	
	

}

function getvalue(name){
    var str=window.location.search;   

    if (str.indexOf(name)!=-1){           
        var pos_start=str.indexOf(name)+name.length+1;
        var pos_end=str.indexOf("&",pos_start);
        if (pos_end==-1){
            //alert( str.substring(pos_start));
        	return str.substring(pos_start);
        }else{
            alert("对不起这个值不存在！");
        }
    }
}

function totalMoney(data,index,fee)
{
   var reg=/^[0-9]{1}\d*(\.{0,1}\d{0,2})$/;
	
   var totalValue = 0;
            $.each(data, function (i, row) {
        	if(i==index)return;
        	
//        	if(!reg.test(row.userTotalChargeFee))return;
        	
//        	totalValue += +(row.userTotalChargeFee);
        	
        	var charge_value = $.trim(row.userTotalChargeFee);
        	
        	if(!reg.test(charge_value))return;
        	
        	totalValue += +charge_value;
        	
        });

  totalValue += +fee;
  
  $('#J_RealPay').html(totalValue.toFixed(2));
  
}

function rowformater(value,row,index)
{
	
	var str = '<input type="button" style="width:60px" value="确认" name="kao" onclick="rowAccept()">';
	
	return str;

}



function rowAccept()
{
	if (endEditing()){
	
		$('#tt').datagrid('acceptChanges');
		
		totalMoney();
		
		
	}
}

function endEditing(){
	if (editIndex == undefined)
	{
		   editIndex = undefined;
		   return true;
	}
	
	
	if ($('#tt').datagrid('validateRow', editIndex))
	{
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function obj2Str(o){
	   var r = [];
	   if(typeof o == "string" || o == null) {
	     return o;
	   }
	   if(typeof o == "object"){
	     if(!o.sort){
	       r[0]="{"
	       for(var i in o){
	         r[r.length]=i;
	         r[r.length]=":";
	         r[r.length]=obj2Str(o[i]);
	         r[r.length]=",";
	       }
	       r[r.length-1]="}"
	     }else{
	       r[0]="["
	       for(var i =0;i<o.length;i++){
	         r[r.length]=obj2Str(o[i]);
	         r[r.length]=",";
	       }
	       r[r.length-1]="]"
	     }
	     return r.join("");
	   }
	   return o.toString();
	}
