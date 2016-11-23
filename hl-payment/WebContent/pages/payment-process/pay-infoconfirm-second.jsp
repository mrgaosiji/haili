<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

    <link rel="stylesheet" href="/hl-payment/res/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/hl-payment/res/assets/bootstrap-table/src/bootstrap-table.css">
    <link rel="stylesheet" href="/hl-payment/res/assets/bootstrap3-editable/css/bootstrap-editable.css">
  
    <script src="/hl-payment/jquery-easyui-1.4.4/jquery.min.js"></script>
    <script src="/hl-payment/res/assets/bootstrap/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="/hl-payment/res/public.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/res/pay.css">
<link rel="stylesheet" type="text/css" href="/hl-payment/res/index.css">


<script src="/hl-payment/res/common.js" type="text/javascript"></script>
<script type="text/javascript" src="/hl-payment/pages/payment-process/pay-infoconfirm-second.js"></script>
<link rel="stylesheet" href="/hl-payment/res/sui.css">


<style type="text/css">
		.cc{
			margin: 100px 40px 15px 50px;
			overflow:auto;
			width:90%;
			height:300px;
			background:#fafafa;
			border:1px solid #ccc;
		}
.STYLE2 {color: #CC3300}
</style>

</head>

<body>

<% 
String company = request.getAttribute("company").toString();
String customer = request.getAttribute("customer").toString();

out.write("<script>var company='"+company+"';var customer='"+customer+"';</script>");

%>




<div class="container">
	
	
<div class="placeholder"></div>
<div class="header">

	
<div class="top">
  <div class="top-content after">
	<div class="top-title">
		<b>尊敬的燃气用户:${name}</b>
		<b>您好，请填入名下燃气表充值金额</b>
		<a href="/hl-payment/hl-portal.html" target="_self" style="color:red">退出</a>
</div>
</div>
</div>

</div>
</div>


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
				<div class="finished">
					<label>
						<span class="round">&#10004;</span>
						<span>充值验证</span>
					</label>
					<i class="triangle-right-bg"></i>
					<i class="triangle-right"></i>
				</div>
			</div>
			<div class="wrap">
				<div class="current">
					<label>
						<span class="round">3</span>
						<span>缴费金额充值</span>
					</label>
					<i class="triangle-right-bg"></i>
					<i class="triangle-right"></i>
				</div>
			</div>
			<div class="wrap">
				<div class="todo">
					<label>
						<span class="round">4</span>
						<span>收银台支付</span>
					</label>
					<i class="triangle-right-bg"></i>
					<i class="triangle-right"></i>
				</div>
			</div>
			<div class="wrap">
				<div class="todo">
					<label>
						<span class="round">5</span>
						<span>充值成功</span>
					</label>
				</div>
			</div>
		</div><!-- /.sui-steps END -->

	<div class="sui-msg msg-block msg-tips" style="margin-bottom: 10px;">
		<div class="msg-con">在充值栏中，填写对应的燃气表充值金额，对于卡表类的燃气表，充值不低于其欠费金额，充值单位最小单位到分</div>
		<s class="msg-icon"></s>
	</div>

	<table id="tt">
	</table>

	<div >

<!--<div style="margin:40px;">房主姓名: <input class="easyui-textbox"  style="width:30%;height:32px" disabled="disabled" /></div>-->
<!--<div style="margin:40px;">缴费单位详细地址: <input class="easyui-textbox" id="address" style="width:60%;height:32px" disabled="disabled" /></div>-->
<!--  <div style="margin:40px;">气表账户金额: <input class="easyui-textbox"  style="width:30%;height:32px" disabled="disabled" /></div>-->




<script>
    var $table = $('#tt'),
        $remove = $('#remove'),
        selections = [];

    function initTable() {
    	$table.bootstrapTable({
    		    url: '/hl-payment/payment/userPayinfo.do', // 接口 URL 地址
            columns: [
                
                [
                    {
                        field: 'meterNumber',
                        title: '表号',
                        
                        footerFormatter: totalNameFormatter,
                        align: 'center'
                    }, {
                        field: 'meterType',
                        title: '表类型',
                        
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                        
                    }, {
                        field: 'address',
                        title: '详细地址',
                        
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                    }, {
                        field: 'meterFee',
                        title: '气表金额',
                       
                        footerFormatter: totalNameFormatter,
                        align: 'center',
                    }, {
                        field: 'meterNowChargeFee',
                        title: '充值(单位：元)',
                        align: 'center',
                        cache: false, 
                        editable: {
                            type: 'text',
                            title: '充值金额',
                            validate: function (value) {
                            	
                            	value = $.trim(value);
                                
                            	var reg=/^[1-9]{1}\d*(\.\d{2})$/;
                                
                            	if (!reg.test(value)) {
                                    return '请填入充值金额数字，小数最小到2位,如无小数金额也请填齐';
                                }
                            	//if (!/^$/.test(value)) {
                                //  return '后付费冲账金额必须大于或者等于欠费金额!';
                                //}
                              var data = $table.bootstrapTable('getData'),
                                  
                                  index = $(this).parents('tr').data('index');
                              
                              totalMoney(data,index,value);
                              
                              return '';
                            }
                        }
                    }
                ]
            ]
        });
        
    	
        
        
    }

    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.id
        });
    }

    function responseHandler(res) {
        $.each(res.rows, function (i, row) {
            row.state = $.inArray(row.id, selections) !== -1;
        });
        return res;
    }

    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
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
            '<i class="glyphicon glyphicon-remove"></i>',
            '</a>'
        ].join('');
    }

    window.operateEvents = {
        'click .like': function (e, value, row, index) {
            alert('You click like action, row: ' + JSON.stringify(row));
        },
        'click .remove': function (e, value, row, index) {
            $table.bootstrapTable('remove', {
                field: 'id',
                values: [row.id]
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
        $.each(data, function (i, row) {
            total += +(row.price.substring(0));
        });
        
        
        return '$' + total;
    }

    $(function () {
    	   var scripts = [
                location.search.substring(1) || '/hl-payment/res/assets/bootstrap-table/src/bootstrap-table.js',
                '/hl-payment/res/assets/bootstrap-table/src/extensions/editable/bootstrap-table-editable.js',
                '/hl-payment/res/assets/bootstrap3-editable/js/bootstrap-editable.js'
            ],
            eachSeries = function (arr, iterator, callback) {
                callback = callback || function () {};
                if (!arr.length) {
                    return callback();
                }
                var completed = 0;
                var iterate = function () {
                    iterator(arr[completed], function (err) {
                        if (err) {
                            callback(err);
                            callback = function () {};
                        }
                        else {
                            completed += 1;
                            if (completed >= arr.length) {
                                callback(null);
                            }
                            else {
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
            if (!done && (!this.readyState ||
                    this.readyState == 'loaded' || this.readyState == 'complete')) {
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
            if (!done && (!this.readyState ||
                    this.readyState == 'loaded' || this.readyState == 'complete')) {
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

</div> 
<!--
<table  name="table_data" id="tt" style="width:100%"
			data-options="singleSelect:true,url:'datagrid_data1.json',method:'get'">
		<thead>
				<tr>
				<th data-options="field:'meternumber',width:'200px',align:'center'">表号</th>
				<th data-options="field:'metertype',width:'200px',align:'center'">表类型</th>
				<th data-options="field:'address',width:'300px',align:'center'">详细地址</th>
				<th data-options="field:'currentfee',width:'100px',align:'center'">气表金额</th>
				<th data-options="field:'chargefee',width:'150px',editor:{type:'numberbox',options:{ onChange:totalMoney,precision:'2'}},align:'center'">充值(元)</th>
				<th data-options="field:'operate',width:'150px',formatter:rowformater,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
-->
<!--  	
<div style="margin:40px;" align="right">充值金额: <input class="easyui-textbox" id="cash_value" name="total_value" style="width:30%;height:32px" />元</div>
<p></p>
<p></p>
</form>
<a href="pay-casher-third.html"   class="easyui-linkbutton" data-options="iconCls:'icon-pay'"  style="width:100px;height:32px" id="pay_cash_confirm">付款</a>

<a href="" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" style="width:100px;height:32px">取消</a>
-->

  


<div>
	<div class="copyright" style="padding-top:100px;">
  				<div class="copyright_nav">
  					<ul class="after">
  						<!-- <li><a href="#">联系我们</a></li> -->
  					</ul>
  				</div>

  				<p class="copyright_banquan" style="text-align:center">2010-2016 海力 <a href="http://www.xzmjzctdyg2.cn.qiyeku.com/" style="color:red">帮助中心</a>&nbsp;&nbsp;&nbsp;ICP备案号：蜀ICP备15018741号&nbsp;&nbsp;&nbsp;</p>
  			</div>
</div>

</body>
</html>