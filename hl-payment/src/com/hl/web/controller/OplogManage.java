package com.hl.web.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hl.common.model.PageData;
import com.hl.common.model.RespInfo;
import com.hl.common.web.BaseController;
import com.hl.common.web.JsonModelAndView;
import com.hl.common.web.security.AuthCfg;
import com.hl.web.entity.MmOperationLog;
import com.hl.web.form.OplogForm;
import com.hl.web.service.UserService;

@Controller
@RequestMapping("/oplog")
public class OplogManage extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping("/list.do")
	@AuthCfg(auth=true,code="pages/oplog-manage.html")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, OplogForm form,
			BindingResult result) {
		
		System.out.println(form.getOpTime());
		
		PageData pd = new PageData();
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			pd.setValues(-1, fieldError, null);
		} else {
			pd = this.userService.queryOplog(form.getFuncCode(), form.getSrcIp(), 
					form.getUserId(), form.getOpTime(), form.getPage(), form.getRows());
		}

		return new JsonModelAndView(pd);
	}
	@RequestMapping("/view.do")
	@AuthCfg(auth=true,code="pages/oplog-manage.html")
	public ModelAndView loadOpLog(HttpServletRequest request,
			HttpServletResponse response){
		RespInfo resp = new RespInfo(0,"建立成功",null);
		String id=request.getParameter("id");
		if(StringUtils.hasText(id)){
			MmOperationLog log =(MmOperationLog) userService.loadEntity(MmOperationLog.class, id);
			if(log==null){
				return new JsonModelAndView(resp.setValues(-1, "该记录不存在", null));
			} else{
				resp.setData(log);
			}
		}
		return new JsonModelAndView(resp);
	}
	
	@RequestMapping("/common.do")
	public ModelAndView commonOpLog(HttpServletRequest request,
			HttpServletResponse response){
		RespInfo resp = new RespInfo(0,"建立的成功",null);
		
		InputStream in = null;
		
		StringBuffer sb = new StringBuffer();
		
		Process pro;
		try {
			
			 String[] cmds = {"/bin/sh","-c","grep upstream /usr/local/nginx/conf/nginx.conf&&grep server_name /usr/local/nginx/conf/nginx.conf"};
		        pro = Runtime.getRuntime().exec(cmds);  
		        pro.waitFor();  
		        in = pro.getInputStream();  
		        BufferedReader read = new BufferedReader(new InputStreamReader(in));  
		        String line = null;  
		        while((line = read.readLine())!=null){  
		        	sb.append(line);  
		        }  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.setData(sb.toString());
		
		return new JsonModelAndView(resp);
	}
	
}
