var order_id = undefined;
var total_fee = undefined;
var txn_time = undefined;

$(document).ready(function(){

         
     order_id = decodeURIComponent(getvalue('order_id'));
      
     $.ajax({
         url: "/hl-payment/payment/orderInfo.do?order_num="+order_id,
         type: "POST",
         dataType: "json",
         cache: false,
         async: false,  
         success: function(result) {
        	 
         if (result.code==0) 
            { 
        	  total_fee = result.data;
            }
          }
         });
//     total_fee = getvalue('total_money');
       
       txn_time = decodeURIComponent(getvalue('txnTime'));
       
//     $('#amount').html(getvalue('total_money')/100);
       
       $('#amount').html(total_fee/100);
       
       $("#amount").css({"font-size":"15px","font-weight":"bold","color":"#f60"});

//     $('#amount2').html('<font class="cOrange" style="font-size: 18px;" id="amount2">'+getvalue('total_money')/100+'</font>'+'&nbsp;&nbsp;元');
       
       $('#amount2').html('<font class="cOrange" style="font-size: 18px;" id="amount2">'+total_fee/100+'</font>'+'&nbsp;&nbsp;元');
   
   });


function bankadio(thechose){
	 $('#pay_bank').val(thechose);
}

//非空验证
function checks() {
	if($("#pay_bank").val() == ""){
		alert("请选择支付方式！");
		return false;
	}else{
		return true;
	}
}

function getvalue(name){
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}


function pay() {
	if(checks()){
		if($('#pay_bank').val()=='UNIONPAY'){
	        	
	    	     $("#ztky_mask").css({
                          display: "block", height: $(document).height()
                                     });
                 $("#waitpay_online").css({
                                      display: "block"
                                         });
                 
                 
               window.open("/hl-payment/payment/unionpay.do?total_money="+total_fee+"&order_id="+order_id+"&txnTime="+txn_time);
              
                 
              // 检测支付
              
                 
//             $('#online_have_question').attr('href','javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='+order_id+'&amount='+total_fee+'&txnTime='+txn_time+'")');
             
//             $('#finish_pay_order').attr('href','javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='+order_id+'&amount='+total_fee+'&txnTime='+txn_time+'")');                          
               
               $('#online_have_question').attr('href','javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='+order_id+'&txnTime='+txn_time+'")');
               
               $('#finish_pay_order').attr('href','javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='+order_id+'&txnTime='+txn_time+'")');                          
               
             // 检测支付
                  
             setInterval(function() {
               $.ajax({
               url: "/hl-payment/payment/refresh_pay_state.do?order_num="+order_id,
               type: "POST",
               dataType: "json",
               cache: false,
               success: function(data1) {
            	   
               if (data1.code==0) {
            	 //location.replace("/hl-payment/payment/refresh_pay_state.do?order_num="+order_id+"&amount="+total_fee+"&txnTime="+txn_time);
            	   location.replace("/hl-payment/payment/successNotice.do?order_num="+order_id+"&amount="+total_fee+"&txnTime="+txn_time);
                   }
                }
               });
              }, 2000);     
	       }
 		else if ($('#pay_bank').val() == 'WEIXINPAY') {
			$("#ztky_mask").css({
				display : "block",
				height : $(document).height()
			});
			$("#weixin_pay_qr").css({
				display : "block"
			});
			
			 $('#wexin_total').html('<font style="font-size: 26px;font-weight: 700;color: #f60;">'+total_fee/100+'</font>'+'&nbsp;&nbsp;元');
		     
			 
			 $.ajax({   
				url: "/hl-payment/payment/getqr_Url.do?order_id="+order_id+"&amount="+total_fee,    
				beforeSend:function() {  
				      //alert("before_send");  
					$('#qrcode-waiting').html('<div class="qrcode-img-area"><div class="ui-loading qrcode-loading">加载中</div></div>');
					 
				   },  
//				complete:function(data) {  
//				      alert('complete info');
//				    },  
				   success: function(data) {  
//				        //alert("url:"+data.data);
					   $('#qrcode-waiting').remove();
					   //$('#qr_code').html('<span class="icon"><img src="/hl-payment/res/images/qr_code/qr_code_test.png"></span>');
					   $('#qr_code').qrcode(data.data);
   
				   },  
				   error: function(data) {  
				             alert(data.msg);  
				            }  
				        });  
			 
			 setInterval(function() {
	               $.ajax({
	               url: "/hl-payment/payment/refresh_pay_state.do?order_num="+order_id,
	               type: "POST",
	               dataType: "json",
	               cache: false,
	               success: function(data1) {
	            	   
	               if (data1.code==0) {
	            	 //location.replace("/hl-payment/payment/refresh_pay_state.do?order_num="+order_id+"&amount="+total_fee+"&txnTime="+txn_time);
	            	   location.replace("/hl-payment/payment/successNotice.do?order_num="+order_id+"&amount="+total_fee+"&txnTime="+txn_time);
	                   }
	                }
	               });
	              }, 2000);     
 		}
 		else
 		{
//			 	$("#ztky_mask").css({
//				display : "block",
//				height : $(document).height()
//			});
//			$("#waitpay_online").css({
//				display : "block"
//			});
			
			var pay_bank_value = $('#pay_bank').val();

			
//			window.open("/hl-payment/payment/alipayapi.do?total_money="
//					+ total_fee + "&order_id=" + order_id+"&WIDdefaultbank="+pay_bank_value);
			
			
			$.ajax({
	    		url : "/hl-payment/payment/alipayapi.do",
	    		data : "total_money="+ total_fee + "&order_id=" + order_id+"&WIDdefaultbank="+pay_bank_value,
	    		dataType: 'html',
	    		type:	'post',
	    		success : function(data, textStatus, jqXHR) {
	    	//	location = data;
	    		document.write(data);
	    		document.close();
	    		}
	    	});



			// 检测支付

			// $('#online_have_question').attr('href','javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='+order_id+'&amount='+total_fee+'&txnTime='+txn_time+'")');

			// $('#finish_pay_order').attr('href','javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='+order_id+'&amount='+total_fee+'&txnTime='+txn_time+'")');

//			$('#online_have_question')
//					.attr(
//							'href',
//							'javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='
//									+ order_id + '&txnTime=' + txn_time + '")');
//
//			$('#finish_pay_order')
//					.attr(
//							'href',
//							'javascript:location.replace("/hl-payment/payment/hrefresh_pay_state.do?order_num='
//									+ order_id + '&txnTime=' + txn_time + '")');
//
//			// 检测支付
//
//			setInterval(
//					function() {
//						$
//								.ajax({
//									url : "/hl-payment/payment/refresh_pay_state.do?order_num="
//											+ order_id,
//									type : "POST",
//									dataType : "json",
//									cache : false,
//									success : function(data1) {
//
//										if (data1.code == 0) {
//											// location.replace("/hl-payment/payment/refresh_pay_state.do?order_num="+order_id+"&amount="+total_fee+"&txnTime="+txn_time);
//											location
//													.replace("/hl-payment/payment/successNotice.do?order_num="
//															+ order_id
//															+ "&amount="
//															+ total_fee
//															+ "&txnTime="
//															+ txn_time);
//										}
//									}
//								});
//					}, 2000);
 		}
      }  	
	}

    function showInfo(){
  
    
    $(".helpDelimiterInfo").show();
    
    $("#datagrid_booking").datagrid({
    	url : '/hl-payment/payment/queryDetailPayment.do?orderID='+order_id,
    	columns:[[{
		field : 'address',
		title : '地址信息',
		align : 'center',
		width : 300
	},{
		field : 'clientNumber',
		title : '客户号',
		align : 'center',
		width : 200
	},{
		field : 'chargeFee',
		title : '充值金额',
		align : 'center',
		width : 100
	}]]
    });
    
//	$("#booking_detail").show();
}

	function hideInfo(){
$(".helpDelimiterInfo").hide();
//	$("#booking_detail").hide();
}

	


