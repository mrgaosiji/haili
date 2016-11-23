var editIndex=undefined;
var company = undefined;
$(document).ready(function()
{	
	var client_id = getvalue("client_id");

	$('#pay_cash_confirm').bind('click',function(){
		document.getElementById("pay_cash_confirm").href="javascript:doCashPost()";
	});
})

function loadPayDetail(){
	 $.ajax({
			url:"/hl-payment/payment/userPayinfo.do",
			type:'post',
			data:{
				customerID:'123123'
				//companyBM:customer
			},
			success:function(resp)
			{
				var fieldinfo = [];
				
				for(var payInfo in resp.data)
				{
					fieldinfo.push({
						'address': resp.data[payInfo].address,
						'currentfee':resp.data[payInfo].meterFee,
						'chargefee':resp.data[payInfo].meterNowChargeFee,
						'meternumber':resp.data[payInfo].meterNumber,
						'name':"123"
					});
				}
					
				$('#tt').bootstrapTable('load',fieldinfo);
				
				//$('#tt').datagrid('loadData',fieldinfo);
			},
			error:function(result)
			{
			  alert("error");
			}
			
			});  
}


function doCashPost()
{

	// var datagrid_info = $('#tt').datagrid('getRows');
	 
	 var datagrid_info = $('#tt').bootstrapTable('getData');

	 
	 var datagrid_field_info = [];
	 
	 for(var payInfo in datagrid_info)
		{
			
			datagrid_field_info.push({
				'"meternumber"' : ("\""+datagrid_info[payInfo].meterNumber+"\"")
				,
				'"metertype"': ("\""+datagrid_info[payInfo].meterType+"\"")
				,
				'"address"' : ("\""+datagrid_info[payInfo].address+"\"")
				,
				'"currentfee"' : ("\""+datagrid_info[payInfo].meterFee+"\"")
				,
				'"chargefee"': ("\""+datagrid_info[payInfo].meterNowChargeFee+"\"")
			});
		}
		
	var orderForm = obj2Str(datagrid_field_info);
	
	
//	var data_Confirm_Cash = $('#cash_money').val();
	
	var data_Confirm_Cash = $('#J_RealPay').html();
	

	$.ajax({
		url : "/hl-payment/payment/checkSecond.do",
		type : 'post',
		data : {
			orderForm : orderForm,// 明细
			totalMoney : data_Confirm_Cash,// 金额
		},
		
		success : function(data) {
			
			
			$("#errorMsg").hide();

			var order_id = String(data.data);
			
			if (data.code == 0) {

				location = "/hl-payment/payment/chargeOrder.do?order_id="+order_id+"&total_money="+data_Confirm_Cash;

			} else {
					$("#errorMsg").text(data);
					$("#errorMsg").show();
     			    reloadCaptcha();
				}

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
   var totalValue = 0;
        $.each(data, function (i, row) {
        	  if(i==index)return;
            totalValue += +(row.meterNowChargeFee);
        });
  
  totalValue += +fee;
  
  $('#J_RealPay').html(totalValue.toFixed(2));
  
}

function rowformater(value,row,index)
{
	
	var str = '<input type="button" style="width:60px" value="确认" name="kao" onclick="rowAccept()"></input>'
	
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





