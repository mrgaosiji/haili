<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>

<script type="text/javascript" src="/hl-payment/jquery-easyui-1.4.4/jquery.min.js"></script>

<title>支付完成-燃气充值</title>
<link rel="stylesheet" href="/hl-payment/res/public.css" type="text/css"></link>
<link rel="stylesheet" href="/hl-payment/res/css/xpf.css" type="text/css"></link>
<style type="text/css">
.rightCont *{line-height:1.4em;}
.rightCont{margin-left: 142px;margin-top:22px;width:778px;}
.infor-breadCrumb{padding-left:20px;margin:15px 0 10px;}
.infor-breadCrumb span{float:left;width:100px;_width:99px;text-align:center;}
.pageName{float:left;font-size:24px;color:#f60;font-weight:bold;}
.infor-fill{padding:30px 20px;margin:0 0 30px 0;border:2px solid #f3f3f3;border-width:0px 2px 2px 0;background:url(http://www.qqxmall.com:80/qtym/images/area-bg.gif) repeat #fbfbfb;}
.infor-fill .dl{clear:both;padding:10px 0;width:730px;overflow:visible;}
.infor-fill .dt{float:left;display:block;width:110px;overflow:hidden;padding-right:20px;border-right:1px dotted #ccc;font-weight:bold;margin:5px 0 0;text-align:right;}
.infor-fill .dt-border-none{border:none;padding-right:21px;}
.infor-fill .dd{float:left;padding:0 0 0 30px;}
.success-table{width:600px;margin-top: 0;margin-right: auto;margin-bottom: 0;margin-left: auto;}
.success-table th,.success-table td{font-size:15px;font-weight:100;}

.success-table th{width:120px;}
.success-table td.padding-tb7{padding:7px 0;}
.font-size-18{font-size:18px;color:#f60}
.success-table td.font-sizes-24{font-size:24px;}
.font-size-24{font-size:24px;font-weight:bold;color:#f60}
.success-table td .fs-15{font-size:15px;}
.cBlue{color:#36c;}
.cBlue:hover{color:#36c;text-decoration:underline;}
</style>

</head>
<body>

	<div class="cart_nav">
		<div class="cart_nav_center main_body mt-nav"></div>
	</div>

	<div class="cart_content main_body">

		<div class="rightCont">
			<div class="infor-breadCrumb fixed" style="clear: right;">
				<div class="pageName"></div>
			</div>
			<div class="infor-fill" style="padding: 16px;">
				<table border="0" cellpadding="0" cellspacing="0"
					class="success-table">
					<tbody>
						<tr>
							<th><img
								src="/hl-payment/res/images/ok_48px.png">
							</th>

							<td class="font-size-20">您的支付已经成功 ！</td>

						</tr>
						<tr>
							<th></th>
							<td class="padding-tb7">订单号：${orderID}
							</td>
						</tr>
						<tr>
							<th></th>
							<td class="padding-tb7">支付金额： <font
								class="cOrgB font-size-18">${feevisual}</font>元
							</td>
						</tr>
						<tr class="comments-suc">
							<th></th>
							<td class="padding-tb7">
								<div class="left" style="line-height: 26px;">
									您还可以 <a
										href="/"
										class="cBlue">继续充值</a>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>


</body>
</html>