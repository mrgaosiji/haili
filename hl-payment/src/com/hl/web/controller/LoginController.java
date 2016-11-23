package com.hl.web.controller;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;

import com.hl.common.img.VcodeImageGen;
import com.hl.common.model.RespInfo;
import com.hl.common.web.BaseController;
import com.hl.common.web.JsonModelAndView;
import com.hl.common.web.security.AuthCfg;
import com.hl.web.entity.MmFunction;
import com.hl.web.entity.MmOperationLog;
import com.hl.web.entity.MmRole;
import com.hl.web.entity.MmUser;
import com.hl.web.form.LoginForm;
import com.hl.web.security.AuthUser;
import com.hl.web.service.UserService;
import com.hl.web.util.MenuTreeBuilder;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private UserService userService;
	/***
	 * 登录验证 码session attribute key
	 */
	public static final String KEY_SESSION_VERIFY_CODE="key.verify.code";
	
	@RequestMapping("/login.do")
	@AuthCfg(auth=false,code="func.login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response,@Valid LoginForm loginForm, BindingResult result) {
        
//		System.out.println("取得表单的方式:"+request.getMethod());
		
		RespInfo resp=new RespInfo();
		String fieldError=transFieldErrorToMsg(result, request);
		if(fieldError!=null){
			resp.setValues(-1, fieldError, null);
		}else{
			//String verifyCode=String.valueOf(request.getSession().getAttribute(KEY_SESSION_VERIFY_CODE));
			//if(!loginForm.getVerifyCode().equalsIgnoreCase(verifyCode)){
				//return new JsonModelAndView(resp.setValues(-1, "验证码输入不正确", null));
			//}else{
				//user password match
				MmUser user=userService.loadUser(loginForm.getUserName());
				if(user==null){
					return new JsonModelAndView(resp.setValues(-1, "用户名不正确", null));
				}
				String pwd=userService.getMd5Str(loginForm.getPassword());
				System.out.println(pwd+" the user password:"+user.getUserPwd());
				if(!pwd.equalsIgnoreCase(user.getUserPwd())){
					return new JsonModelAndView(resp.setValues(-1, "密码不正确", null));
				}
				if(user.getStatus()!=0){
					return new JsonModelAndView(resp.setValues(-1, "用户已停用", null));
				}
				//
				AuthUser auser=new AuthUser();
				auser.setLastLoginTime(user.getLastLoginTime());
				auser.setId(user.getUserId());
				user.setLastLoginTime(new Date());
				user.setLoginCount((user.getLoginCount()==null?0:user.getLoginCount())+1);
				userService.updateEntity(user);
				auser.setLoginCount(user.getLoginCount().intValue());
				//记录操作日志
				MmOperationLog opLog=new MmOperationLog();
				opLog.setClientDesc(request.getHeader("User-Agent"));
				opLog.setFuncCode("login.do");
				opLog.setOpDesc("登录");
				opLog.setOpTime(new Date());
				opLog.setOpType("R");
				opLog.setRefTable("MM_USER");
				opLog.setSrcIp(getRemoteAddr(request));
				opLog.setUserId(user.getUserId());
				userService.saveEntity(opLog);
				//
				List<String> fcodes=new ArrayList<String>();
				if(user.getRoles()!=null){
					for(MmRole r:user.getRoles()){
						if(r.getFunctions()!=null){
							for(MmFunction f:r.getFunctions()){
								if(StringUtils.hasText(f.getFuncCode())){
									fcodes.add(f.getFuncCode());
								}
							}
						}
					}
				}
				auser.setFuncCodeList(fcodes);
				auser.setData(user);
				request.getSession().setAttribute(AuthUser.AUTH_USER_SESSION_KEY, auser);
				request.getSession().setAttribute(KEY_SESSION_VERIFY_CODE,null);
			}
		//}
		return new JsonModelAndView(resp);
	}
	
	@RequestMapping("/verifyCode.do")
	@AuthCfg(auth=false)
	public ModelAndView generateImgCode(HttpServletRequest request,
			HttpServletResponse response){
		
		ModelAndView mav=new ModelAndView(new AbstractView() {
			
			@Override
			protected void renderMergedOutputModel(Map<String, Object> model,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				NumberFormat nf=new DecimalFormat("0000");
				String contentType="img/jpeg";
				setContentType(contentType);
				response.setContentType(contentType);
				String code=nf.format(Math.random()*10000);
				request.getSession().setAttribute(KEY_SESSION_VERIFY_CODE, code);
				VcodeImageGen.generateVcodeImg(code, response.getOutputStream());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		});
		
		return mav;
	}
	
	@RequestMapping("/cur-user-info.do")
	@AuthCfg(auth=false)
	public ModelAndView sessionUserInfo(HttpServletRequest request,
			HttpServletResponse response){
		RespInfo resp=new RespInfo();
		AuthUser user = (AuthUser) request.getSession().getAttribute(
				AuthUser.AUTH_USER_SESSION_KEY);
		if(user==null){
			return new JsonModelAndView(resp.setValues(-1, "请先登录系统", null));
		}else{
			MmUser mmUser=(MmUser) user.getData();
			Map<String, Object> userInfo=new HashMap<String, Object>();
			userInfo.put("userId", mmUser.getUserId());
			userInfo.put("lastLoginTime", user.getLastLoginTime());
			userInfo.put("loginCount", user.getLoginCount());
			userInfo.put("fullName", mmUser.getFullName());
			List<String> roles=new ArrayList<String>();
			if(mmUser.getRoles()!=null){
				for(MmRole r:mmUser.getRoles()){
					roles.add(r.getName());
				}
			}
			userInfo.put("roles", roles);
			userInfo.put("ip", getRemoteAddr(request));
			resp.setValues(0, "success", userInfo);
		}
		return new JsonModelAndView(resp);
	}
	
	@RequestMapping("/logout.do")
	@AuthCfg(auth=false)
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response){
		RespInfo resp=new RespInfo();
		HttpSession session=request.getSession();
		Enumeration<?> enus=session.getAttributeNames();
		if(enus!=null){
			while(enus.hasMoreElements()){
				String el=String.valueOf(enus.nextElement());
				session.removeAttribute(el);
			}
		}
		return new JsonModelAndView(resp);
	}
	
	@RequestMapping("/userMenuTree.do")
	@AuthCfg(auth=false)
	public ModelAndView menuTree(HttpServletRequest request,
			HttpServletResponse response){
		AuthUser auser = (AuthUser) request.getSession().getAttribute(
				AuthUser.AUTH_USER_SESSION_KEY);
		Map<String, MmFunction> funcList=new HashMap<String, MmFunction>();
		List<MmFunction> rootChildren=new ArrayList<MmFunction>();
		if(auser!=null){
			MmUser user=(MmUser) auser.getData();
			if(user!=null&&user.getRoles()!=null){
				for(MmRole r:user.getRoles()){
					if(r.getFunctions()!=null){
						for(MmFunction f:r.getFunctions()){
							if(f.getType()==1){//menu
								if(!StringUtils.hasText(f.getParentId())){
									rootChildren.add(f);
								}else{
									funcList.put(f.getId(), f);
								}
							}
						}
					}
				}
			}
		}
		return new JsonModelAndView(MenuTreeBuilder.buildTreeData(funcList, rootChildren));
	}
	
}
