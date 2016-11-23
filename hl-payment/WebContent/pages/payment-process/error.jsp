<%@page import="com.hl.common.web.WebUtil"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Log log = LogFactory.getLog("error_jsp");
	Object errorCode = request
			.getAttribute("javax.servlet.error.status_code");
	Object errorUri = (request
			.getAttribute("javax.servlet.error.request_uri"));
	String url = errorUri.toString().toLowerCase();
	Throwable error=null;
	if (url.endsWith(".jsp") || url.endsWith(".do")) {

		error = (Throwable) (request
				.getAttribute("javax.servlet.error.exception"));
		if (error == null) {
			error = (Throwable) (request.getAttribute("exception"));
		}
		if (error == null) {
			log.error(WebUtil.getRemoteIpAddr(request) + " 访问发生错误:" + errorCode+"/"+errorUri);
		} else {
			log.error(WebUtil.getRemoteIpAddr(request) + " 访问发生错误:"
					+ errorCode + "/" + errorUri, error);
		}
	}
%>
<html>
<head>
<title>出错了</title>
<link rel="stylesheet" href="/hl-payment/res/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/hl-payment/res/css/ace.min.css">
<link rel="stylesheet" href="/hl-payment/res/css/font-awesome.min.css">



</head>
<body>



<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<div class="error-container">
									<div class="well">
										<h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="icon-random"><span class="glyphicon glyphicon-minus-sign"></span></i>
												
												<%=errorCode%>
											</span>
											您好，出了一些问题！
										</h1>

										<hr>
										<h3 class="lighter smaller">
											后台处理异常出错，请联系运维管理人员人！
											<i class="glyphicon glyphicon-wrench bigger-125"></i>
											维修一下
										</h3>

										<div class="space"></div>

										<div>
											<h4 class="lighter smaller">同时, 阅读以下报错处:</h4>

											<ul class="list-unstyled spaced inline bigger-110 margin-15">
												<li>
													<div><%if(error!=null){
			                                       error.printStackTrace(new PrintWriter(out));
		                                        } %></div>
												</li>

											
											</ul>
										</div>

										<hr>
										<div class="space"></div>

										<div class="center">
											

											<a href="/hl-payment/index.jsp" class="btn btn-primary">
												<i class="icon-dashboard"></i>
						                                    回到首页
											</a>
										</div>
									</div>
								</div>

								<!-- PAGE CONTENT ENDS -->
							</div>
			
</body>
</html>