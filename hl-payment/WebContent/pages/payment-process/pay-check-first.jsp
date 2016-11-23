<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>用户燃气缴费校验</title>

<link rel="shortcut icon" href="/hl-payment/res/images/HL2.ico"  />
<link rel="stylesheet" href="/hl-payment/res/assets/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="/hl-payment/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript" src="/hl-payment/pages/payment-process/pay-check-first.js"></script>
<script src="/hl-payment/res/common.js" type="text/javascript"></script>
<script src="/hl-payment/res/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="/hl-payment/res/sui.min.js"></script>
<script src="/hl-payment/res/assets/bootstrap-table/src/bootstrap-table.js"></script>

<script src="/hl-payment/res/layer/js/loading-overlay-order.min.js"></script>

<link rel="stylesheet" type="text/css" href="/hl-payment/res/public.css">


<link rel="stylesheet" type="text/css" href="/hl-payment/res/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/res/css/modiprocess/templatemo-style.css">


<link rel="stylesheet" href="/hl-payment/res/sui.css">

<link href="/hl-payment/res/layer/css/layer.css" rel="stylesheet" />

<!-- 
<script language="vbscript">
	Function str2asc(strstr) 
    	str2asc = hex(asc(strstr)) 
	End Function 

	Function asc2str(ascasc) 
    	asc2str = chr(ascasc) 
	End Function
</script>  -->


<style type="text/css">
		.cc{
			/*margin: 15px 40px 15px 50px;*/
			/*overflow:auto;*/
			width:100%;
			/*height:300px;*/
			background:#fafafa;
			/*border:1px solid #ccc;*/
		}
		
		.cc1{
			/*margin: 15px 40px 15px 50px;
			
			overflow:auto;*/
			margin-top:40px;
			width:100%;
			/*height:420px;*/
			background:#fafafa;
			/*border:1px solid #ccc;*/
		}

		
.top-title {
    float: left;
    font-size: 12px;
    color: #6e6e6e;
    line-height: 36px;
    padding-left: 10px;
}

.lazy {
    display: none;
}

#container {
    height: 600px;
    overflow: scroll;
}

.table th, .table td { 
				text-align: center; 
				height:38px;
			}

.alert-warning h3{display:inline;color:green}

#fixed-footer-wrap {
    width: 100%;
    height: 40px;
    background: #e8e8e8;
    border-top: solid 1px #CCC;
    /*position: fixed;*/
    bottom: 0px;
    z-index: 3;
    overflow: visible;
}

</style>
</head>

<body>

<header class="site-header" id="top">
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="row">
                    <div class="navbar-header">
                        
                        
                        <!--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                          <i class="fa fa-bars"></i>
                        </button>-->
                        
                         <style type="text/css">
                        
                         .logo-wrapper a{
                         	  /*opacity: .8;*/
                         	  display: inline-block;
                         	  font-size:14px;
                         	  letter-spacing:normal;
                         	  margin-left:8px;
                         	  padding-left:8px;
                         	  clolr:#fff;
                         	  /*border-left:1px solid #767675;*/
                         	}
                         	
                         	.dev_code{
                         	  position: relative;	
                         	}
                         	
                         	.bubble_tips {
                         		display: inline-block;
                         		vertical-align:middle;
                         		position:relative;
                         		color:#c1c2c3;
                         		} 
                         	
                         	.bubble_top {
                         		margin-top:12px;
                         		}
                         	
                         	.code_pop{
                         	  position:absolute;
                         	  /*left:30px;*/
                         	  top:34px;
                         	  text-align:center;
                         	  width:110px;	
                         	}
                         	
                         	.bubble_tips_inner{
                         	  padding:5.5px 12px;
                         	  border:1px solid #e6e7eC;
                         	  line-height:21px;
                         	  background-color:#fff;
                         	  word-wrap: break-word;
                         	  word-break: break-all;
                         	}
                         	
                         	.bubble_top .txt_enhanced{
                         	  color:#222;
                         	}
                         	
                         	/*
                         	.bubble_tips_inner p{
                            display:block;
                            -webkit-margin-before:1em;
                            -webkit-margin-after:1em;
                            -webkit-margin-start:0px;
                            -webkit-margin-end:0px;
                            color:#BE37C7; 	
                         	}*/
                         	
                         	.pic_devcode {
                           width: 85px;
                           height: 85px;
                           }

                           .bubble_top .bubble_tips_arrow.out {
                            border-bottom-color: #e6e7ec;
                            top: 0;
                            }
                            
                            
                            .bubble_top .bubble_tips_arrow {
                             left: 66%;
                            }
                            .bubble_top .bubble_tips_arrow.in {
                              top: 1px;
                            }
                            
                            .bubble_top .bubble_tips_arrow {
                              border-left-width: 6px;
                              border-right-color: transparent;
                              border-right-style: dashed;
                              border-top-width: 0;
                              border-bottom-color: #fff;
                              border-bottom-style: solid;
                              left: 50%;
                              margin-left: -6px;
                             }
                             
                             .bubble_top em,.bubble_top span{display:block;width:30px;height:10px;font-size:25px;overflow:hidden;_position:relative;margin-left:40px;}

                             .bubble_top em{margin-top:-187px;color:black;font-style:normal;}
                             
                             .bubble_top span{margin-top:-8px;color:white;}
         
                        </style>                        

						<div class="logo-wrapper">
							<a href="javascript:void(0);" id="biz_qrcode_a"
								class="navbar-brand">
							
							<img src="/hl-payment/res/images/icon_dev_code22c4cc.png">&nbsp关注微信号


								<div id="biz_qrcode_div" style="display: none"
									class="bubble_top code_pop">

									<div class="bubble_tips_inner">

										<p>
											<img src="/hl-payment/res/images/haili_company.png" class="pic_devcode">
										</p>
										<p class="txt_enhanced" style="font-size:10px">
											关注微信 <br> 接收重要信息
										</p>

									</div>

									<em>&#9670</em> <span>&#9670</span>
								</div>

							</a>
						</div>
					</div>
                    <div class="collapse navbar-collapse" id="main-menu">
                        <ul class="nav navbar-nav navbar-right">
                            <li><span></span><a href="#arrearage" class="home" id="abhistory" style="display:none">欠费历史信息</a></li>
                            <li><span></span><a href="#historydiv" class="home" id="payhistory" >历史缴费信息</a></li>
                            <li><span></span><a href="#tt" class="about">燃气充值</a></li>
                        </ul>
                    </div>
                </div> 
            </div>
        </nav>
    </header>


<div class="container-first" id="tkm">
<div class="placeholder" style="margin:100px"></div>
<div class="header">
<div class="top" style="wideth:100%;background-color:#f2f2f2">
					<div class="top-content after">
						<div class="top-title">	
									<b>尊敬的燃气用户:</b>
									<strong style="color:red">${customerName}</strong>
									,您好，请确认信息,填入缴费款并验证!
									<a href="/" target="_self" style="color:red;text-decoration:underline">退出</a>
						</div>
					</div>
</div>
<div class="sui-container">
<div class="sui-steps">
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
				<div class="current">
					<label>
						<span class="round">2</span>
						<span>缴费金额充值</span>
					</label>
					<i class="triangle-right-bg"></i>
					<i class="triangle-right"></i>
				</div>
			</div>
			
			<div class="wrap">
				<div class="todo">
					<label>
						<span class="round">3</span>
						<span>收银台支付</span>
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
		</div><!-- /.sui-steps END -->	
</div>



<!--  <div class="easyui-layout" style="width:100%;height:500px;">-->

<!-- <div data-options="region:'center',title:'缴费校验',iconCls:'icon-ok'" id="checkmask">-->

<div class="cc heading">

<div style="margin:40px;" ><input id="userid" class="textbox" style="width:60%;height:32px" type="hidden"></div> 

<!-- <div style="margin:40px;">所属燃气公司:&nbsp&nbsp<input class="easyui-textbox"  style="width:60%;height:32px" id="gas_company"/></div> -->

<!--<div style="margin:40px;">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp客户号:&nbsp&nbsp <input  class="easyui-textbox" disabled="disabled"  id="client_id"  style="width:60%;height:32px" /></div>-->



				<div id="customer" style="margin: 40px;" >
					<table>
						<tr>
							<td>
								<form class="form-inline">
									<div class="form-group">
										<h3>
											燃气公司：<input type="text" class="form-control" id="gas_company" value=${companyName}
												disabled="disabled" style="width: 60%">
										</h3>
									</div>
								</form>
							</td>
							<td>
								<form class="form-inline">
									<div class="form-group">
										<h3>
											客户姓名： <input id="client_name" value="${customerName}" style="/*width: 40%*/"
												type="text" class="form-control" disabled="disabled">
										</h3>

									</div>
								</form>
							</td>
							<td>
								<form class="form-inline">
									<div class="form-group">
										<h3>
											客户号： <input id="client_id" style="width: 60%" value=${customerCode}
												type="text" class="form-control" disabled="disabled">
										</h3>
									</div>
								</form>
							</td>
							
							<td>
								<form class="form-inline">
									<div class="form-group">
										<h3>
											账户余额：<div style="color:brown;display:inline">${bcye}</div>&nbsp&nbsp元
										</h3>
									</div>
								</form>
							</td>
						</tr>
						
					</table>
				</div>
				
				<!-- 
				<div style="margin: 40px;">
					<table>
						<tr>
							<td>
								<form class="form-inline">
									<div class="form-group">
										<h3>
											欠费状态：<input id="client_id" value="欠费"
									style="width: 100px; align: center" type="text"
									class="form-control" disabled="disabled">
										</h3>
									</div>
								</form>
							</td>
							<td>
								<form class="form-inline">
									<div class="form-group">
										<h3>
											&nbsp&nbsp&nbsp欠费金額：<input id="client_id" value="28.92"
									style="width: 100px; align: center" type="text"
									class="form-control" disabled="disabled">元
										</h3>

									</div>
								</form>
							</td>
							
						</tr>
					</table>
				</div> -->
				
				<div style="margin: 40px;display:none" id="arrearage" >
					<div class="alert alert-warning">
						<a href="#" id="mememe" class="close" data-dismiss="alert"> &times; </a> <strong>你的普表已欠费</strong>,欠费总金额是
						<h3>
							${abstractMoney}
							</h3>&nbsp元
					</div>

					<script type="text/javascript">
                 $(function(){
                 $("#mememe").click(function(){
                	 $("#arrearage").hide();
                	 $("#abhistory").hide();
                 });
                    });  
                    </script>
                    
                    
                    
					<h3>历史欠费明细：</h3>
                    <input value=${historyAbDetail} id="input_historyAbDetail" style="display:none" >
					<table class="table table-bordered" data-toggle="table"
						 id="abstractTable">

						<thead>
							<tr>
								<th data-field="month">欠费月份</th>
								<th data-field="abstractFee">欠费金额</th>
								<th data-field="beginVolume">当月起读数(方量)</th>
								<th data-field="endVolume">当月终止读数(方量)</th>
								<th data-field="addressDetail">详细地址</th>
								
							<tr>
						</thead>
					</table>
                    
                    
                    
					<script type="text/javascript">

					    var $abstractTable = $('#abstractTable');
						var ss = $('#input_historyAbDetail').val();
						
						
						
						var data_tmp = eval(ss);

						var rows1 = [];
						for ( var i = 0; i < data_tmp.length; i++) {
						
							rows1.push({
								month : data_tmp[i].month,
								abstractFee : data_tmp[i].abstractFee,
								addressDetail : data_tmp[i].addressDetail,
								beginVolume : data_tmp[i].beginVolume,
								endVolume : data_tmp[i].endVolume
							});
						}

						$(function() {
								
							$abstractTable.bootstrapTable('load', rows1);
							$abstractTable.bootstrapTable('hideLoading');
						})
					</script>

				</div>
				

				<script>
					var t = "${abstractMoney}";
					if (parseFloat(t) < 0.00){
						$('#arrearage').show();
						$('#abhistory').show();
					}
						
				</script>


<div style="margin:40px;" id="historydiv">
<div id="historyOneRecords" style="display:none"><strong>${historyOne}</strong></div>
<div id="historyThreeRecords" style="display:none"><strong>${historyThree}</strong></div>
<div class="alert alert-warning"><a id="kekeke" href="#" class="close" data-dismiss="alert"> &times; </a><h3>缴费历史记录：</h3></div>
<script type="text/javascript">
                 $(function(){
                 $("#kekeke").click(function(){
                	 $("#historydiv").hide();
                	 $("#payhistory").hide();
                 });
                    });  
   </script>
<ul class="nav nav-tabs">
	  <li  class="active"><a href="#one" data-toggle="tab">近一个月</a></li>
	  <li><a href="#three" data-toggle="tab">近三个月</a></li>
	  </ul>
	  <div id="myTabContent" class="tab-content">
	   <div class="tab-pane fade in active" id="one">
	<!--  <table class="table table-bordered" data-url="/hl-payment/payment/userPayinfo.do" id="onemonth" style="align:center"><tr><td data-field="meterNumber">发生日期</td><td>充值金额</td><td>购买放量</td><td>充值状态</td></tr></table>-->
							<table class="table table-bordered" data-toggle="table"
								id="onetable" data-pagination="true" >
								<thead>
									<tr>
										<th data-field="occurTime">发生日期</th>
										<th data-field="fee">充值金额</th>
									</tr>
								</thead>
							</table>
						</div>
	   <div class="tab-pane fade" id="three">
	   <!--<table class="table table-bordered" id="threemonth" style="align:center"><tr><td>发生日期</td><td>充值金额</td><td>购买放量</td><td>充值状态</td></tr></table>-->
	   <table id="threetable" class="table table-bordered" data-toggle="table" data-pagination="true">
    <thead>
        <tr>
            <th data-field="occurTime">发生日期</th>
            <th data-field="fee">充值金额</th>   
        </tr>
    </thead>
</table>
	   </div>
	</div>
</div>

<script>

	$(function() {

		
		var $onetable = $('#onetable');
		
		var ss = document.getElementById("historyOneRecords").innerText;
		
		var data_tmp = eval(ss);

		
		var rows2 = [];
		
		for ( var i = 0; i < data_tmp.length; i++) {		
			rows2.push({
				occurTime:data_tmp[i].createTime,
				fee:data_tmp[i].payFee
			});
		}		
		
		var $threetable = $('#threetable');
		
        ss = document.getElementById("historyThreeRecords").innerText;
		
		var data_tmp = eval(ss);
		
		if(data_tmp.length==0){
			$("#historydiv").hide();
			$("#payhistory").hide();
		}

		
		var rows3 = [];
		for ( var i = 0; i < data_tmp.length; i++) {
			rows3.push({
				occurTime : data_tmp[i].createTime,
				fee : data_tmp[i].payFee
			});
		}
		
		
		$onetable.bootstrapTable('load', rows2);
		$onetable.bootstrapTable('hideLoading');
		
		$threetable.bootstrapTable('load', rows3);
		$threetable.bootstrapTable('hideLoading');
	})
</script>


</div>  <!-- end by .cc -->
</div>  <!-- end by head -->
</div>   <!-- end by container first -->

<div class="row"><div class="triangle"></div></div>

<div class="cc1" >
<!-- 结合到一起 -->
<div class="sui-msg msg-block msg-tips" style="margin-bottom: 10px;">
		<div class="msg-con">在充值栏中，填写对应的燃气表充值金额，对于卡表类的燃气表，充值不低于其欠费金额，充值单位最小单位到分</div>
		<s class="msg-icon"></s>
	</div>

<input id="payInfo" style="display:none" value=${payInfo}>
		<table id="tt">
		</table>
		
		<script>

		
		
   //$("div.lazy").show();

    var $table = $('#tt'),
        $remove = $('#remove'),
        selections = [];
    
    var ss = $('#payInfo').val();

	var data_tmp = eval(ss);

	var rows4 = [];
	for ( var i = 0; i < data_tmp.length; i++) {
		rows4.push({
			addressDetail : data_tmp[i].addressDetail,
			addressID : data_tmp[i].addressID,
			currentMeterFee:data_tmp[i].currentMeterFee,
			meterName:data_tmp[i].meterName,
			meterSerialNo:data_tmp[i].meterSerialNo,
			meterType:data_tmp[i].meterType
			//userTotalChargeFee:data_tmp[i].userTotalChargeFee
		});
	}
    

    function initTable() {
   
    	$table.bootstrapTable({
    		    //url: '/hl-payment/payment/userPayinfo.do', // 接口 URL 地址
            columns: [
                
                [                   
                                    {
										field : 'addressID',
										title : '地址序号',

										footerFormatter : totalNameFormatter,
										align : 'center',
										visible:false

									},
									{
										field : 'meterSerialNo',
										title : '表序号',

										footerFormatter : totalNameFormatter,
										align : 'center',
										visible:false

									},
									{
										field : 'meterType',
										title : '表类型编码',

										footerFormatter : totalNameFormatter,
										align : 'center',
										visible:false
									},

									{
										field : 'meterName',
										title : '表类型',

										footerFormatter : totalNameFormatter,
										align : 'center',

									},
									{
										field : 'addressDetail',
										title : '详细地址',

										footerFormatter : totalNameFormatter,
										align : 'center',
									},
									{
										field : 'currentMeterFee',
										title : '气表金额',
										visible:false,
										footerFormatter : totalNameFormatter,
										align : 'center',
									},
									{
										field : 'userTotalChargeFee',
										title : '充值(单位：元)',
										formatter : function(value, row, index) {
											var e = '金额填写';
											return e;
										},
										align : 'center',
										cache : false,
										editable : {
											type : 'text',
											title : '充值金额',
											validate : function(value) {

												var regular = /\s+/g;
                                                if (regular.test(value)) {
                                                   return '输入不能带空格';
                                                 }   
												
												value = $.trim(value);

												var reg = /^[0-9]{1}\d*(\.{0,1}\d{0,2})$/;

												if (!reg.test(value)) {
													return '请填入充值金额数字，小数最小到2位,如无小数金额也请填齐';
												}
												if(value<=0)return '金额必须大于0';

												//if (!/^$/.test(value)) {
												//  return '后付费冲账金额必须大于或者等于欠费金额!';
												//}
												var data = $table
														.bootstrapTable('getData'),

														
												index = $(this).parents('tr')
														.data('index');

												$
														.each(
																data,
																function(i, row) {
																	if (i == index) {
																		meterFee = row.currentMeterFee;
																	}
																});

												if (parseFloat(meterFee) < 0) {
													isSmallValue = -parseFloat(meterFee) > parseFloat(value) ? true
															: false;
													if (isSmallValue)
														return '普表充值金额必须等于或者大于欠费金额';
												}

												totalMoney(data, index, value);

												return '';
											}
										}
									} ] ]
						});

				$table.bootstrapTable('load', rows4);
				$table.bootstrapTable('hideLoading');

			}

			function getIdSelections() {
				return $.map($table.bootstrapTable('getSelections'), function(
						row) {
					return row.id
				});
			}

			function responseHandler(res) {
				$.each(res.rows, function(i, row) {
					row.state = $.inArray(row.id, selections) !== -1;
				});
				return res;
			}

			function detailFormatter(index, row) {
				var html = [];
				$.each(row, function(key, value) {
					html.push('<p><b>' + key + ':</b> ' + value + '</p>');
				});
				return html.join('');
			}

			function operateFormatter(value, row, index) {
				return [
						'<a class="like" href="javascript:void(0)" title="Like">',
						'<i class="glyphicon glyphicon-heart"></i>',
						'</a>  ',
						'<a class="remove" href="javascript:void(0)" title="Remove">',
						'<i class="glyphicon glyphicon-remove"></i>', '</a>' ]
						.join('');
			}

			window.operateEvents = {
				'click .like' : function(e, value, row, index) {
					alert('You click like action, row: ' + JSON.stringify(row));
				},
				'click .remove' : function(e, value, row, index) {
					$table.bootstrapTable('remove', {
						field : 'id',
						values : [ row.id ]
					});
				}
			};

			function totalTextFormatter(data) {
				return 'Total';
			}

			function totalNameFormatter(data) {
				return data.length;
			}

			function totalPriceFormatter(data) {

				var total = 0;
				$.each(data, function(i, row) {
					total += +(row.price.substring(0));
				});

				return '$' + total;
			}

			$(function() {
				var scripts = [
						location.search.substring(1)
								|| '/hl-payment/res/assets/bootstrap-table/src/bootstrap-table.js',
						'/hl-payment/res/assets/bootstrap-table/src/extensions/editable/bootstrap-table-editable.js',
						'/hl-payment/res/assets/bootstrap3-editable/js/bootstrap-editable.js' ], eachSeries = function(
						arr, iterator, callback) {
					callback = callback || function() {
					};
					if (!arr.length) {
						return callback();
					}
					var completed = 0;
					var iterate = function() {
						iterator(arr[completed], function(err) {
							if (err) {
								callback(err);
								callback = function() {
								};
							} else {
								completed += 1;
								if (completed >= arr.length) {
									callback(null);
								} else {
									iterate();
								}
							}
						});
					};
					iterate();
				};

				eachSeries(scripts, getScript, initTable);
			});

			function getScript(url, callback) {
				var head = document.getElementsByTagName('head')[0];
				var script = document.createElement('script');
				script.src = url;

				var done = false;
				// Attach handlers for all browsers
				script.onload = script.onreadystatechange = function() {
					if (!done
							&& (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete')) {
						done = true;
						if (callback)
							callback();

						// Handle memory leak in IE
						script.onload = script.onreadystatechange = null;
					}
				};

				head.appendChild(script);

				// We handle everything using the script element injection
				return undefined;
			}

			function getScript(url, callback) {
				var head = document.getElementsByTagName('head')[0];
				var script = document.createElement('script');
				script.src = url;

				var done = false;
				// Attach handlers for all browsers
				script.onload = script.onreadystatechange = function() {
					if (!done
							&& (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete')) {
						done = true;
						if (callback)
							callback();

						// Handle memory leak in IE
						script.onload = script.onreadystatechange = null;
					}
				};

				head.appendChild(script);

				// We handle everything using the script element injection
				return undefined;
			}
		</script>

<!-- 
<div id="A_checkbar" class="checkbar">
					<div id="J_checkbar" class="checkbar">
						<div id="J_CheckbarMain" class="main">
							<div id="J_CheckDue" class="due">
								<div class="realPay" id="realPay_1">
									<p class="money">
										<span class="hd">实付款：</span><span class="bd"><span class="tc-rmb" >￥</span>
														<strong id="J_RealPay">0.00</strong>
										</span>
									</p>
									
								</div>
							</div>
						</div>
					</div>
					<div class="action">
						<div id="submitOrder_1" class="go-wrapper">
							<a href="javascript:doCashPost();" class="go-btn" title="提交订单">提交订单<s></s>
							</a>
						</div>
					</div>
	</div>
 -->
 

<!-- end -->


<div style="margin-left:px;"><!--  <a id="a_link" onclick="javascript:check()" class="easyui-linkbutton" style="width:150px;height:32px" iconCls="icon-ok">充&nbsp值&nbsp缴&nbsp费</a>-->

<!-- <a id="a_link" onclick="javascript:check();" class="btn btn-primary" style="width:200px;height:32px" >充值缴费</a> -->

<style type="text/css">

.checkbar {
    text-align: left;
    padding: 20px;
}

.checkbar .main {
    padding-right: 10px;
}

.checkbar .due {
    color: #999;
    min-height: 50px;
}

.checkbar .due .realPay {
    margin-top: -6px;
}

p {
    margin-bottom: 18px;
}

.checkbar .due .realPay .bd {
    color: #c00;
    font-size: 24px;
    font-weight: 700;
    font-family: tahoma;
}

.checkbar .go-btn {
    position: relative;
    display: inline-block;
    vertical-align: middle;
    width: 182px;
    height: 37px;
    line-height: 37px;
    cursor: pointer;
    text-align: center;
    font-size: 14px;
    font-weight: 700;
    background: #b10000;
    color: #fff;
    margin: 5px 5px 5px 40px;
}

</style>

<div id="A_checkbar" class="checkbar">
					<div id="J_checkbar" class="checkbar">
						<div id="J_CheckbarMain" class="main">
							<div id="J_CheckDue" class="due">
								<div class="realPay" id="realPay_1">
									<p class="money">
										<span class="hd">&nbsp&nbsp&nbsp&nbsp实付款：</span><span class="bd"><span class="tc-rmb" >￥</span>
														<strong id="J_RealPay">0.00</strong>
										</span>
									</p>
									    	
										<table style="margin:">
											<tr>
												<td>
													<form class="form-inline" id="form1">
														<div class="form-group">
															<span>验证码：</span>
															<input id="captchaInput" class="form-control"
																name="captchaInput" style="width: 90px;" maxlength="4" onkeypress="if(event.keyCode==13) {return false;}" />

															<img id="captchaImg" alt="填入验证码"
																style="width: 55px; height: 18px; vertical-align: middle; font-weight: bold;" />

															<img src="/hl-payment/res/images/reload.png"
																style="cursor: pointer; vertical-align: middle; font-weight: bold;"
																onclick="refresh()" /> <b id="errorMsg"
																style="COLOR: #ff0000; text-align: center; display: none;"></b>
															<b id="okMsg" style="text-align: center; display: none;"></b>
														</div>
													</form>
												</td>
											</tr>
										</table>
									
									</div>
							</div>
						</div>
					</div>
					<input style="display:none" id="customerCode" value=${customerCode}>
					<input style="display:none" id="companyCode" value=${companyCode}>
					<div class="action">
						<div id="submitOrder_1" class="go-wrap	per">
							<!-- <a href="javascript:doCashPost();"  class="go-btn" title="提交订单">提交订单<s></s> -->
							<a id="postOne"  class="go-btn" title="提交订单">提交订单<s></s>
							</a>
						</div>
					</div>
	</div>


</div>


</div><!-- end .cc1 -->





<!--	<div id="fixed-footer-wrap">
		
		<p class="copyright_banquan" style="text-align: center">
			@2005-2016  <a href="#" style="color: red">帮助中心</a>&nbsp;&nbsp;&nbsp;ICP备案号：蜀ICP备50263615号&nbsp;&nbsp;&nbsp;
		</p>
	</div>-->
	
	 <footer>
        <div class="container">
            <div class="footer-content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-8">
                            <div class="copyright-text">
								<p class="copyright_banquan" style="text-align: center;color:#333">
									@2005-2016 海力 <a href="#" style="color: red">帮助中心</a>&nbsp;&nbsp;&nbsp;ICP备案号：蜀ICP备50263615号&nbsp;&nbsp;&nbsp;
								</p>
							</div>
                        </div>
                        <div class="col-md-4">
                            <div class="back-to-top">
                                <a href="#top">回到顶部</a>
                            </div>
                        </div>
                        
                    </div>    
                </div>
            </div>
        </div>
    </footer>





</body>
</html>