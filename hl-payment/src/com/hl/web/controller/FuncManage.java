package com.hl.web.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.hl.web.entity.MmFunction;
import com.hl.web.entity.MmOperationLog;
import com.hl.web.form.BaseListQueryForm;
import com.hl.web.form.FunctionForm;
import com.hl.web.service.UserService;
import com.hl.web.util.MenuTreeBuilder;
import com.hl.web.util.MmToolkit;

@Controller
@RequestMapping("/func")
public class FuncManage extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping("/list.do")
	@AuthCfg(auth = true, code = "pages/func-manage.html")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response, BaseListQueryForm form, BindingResult result) {
		PageData pd = new PageData();
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			pd.setValues(-1, fieldError, null);
		} else {
			pd = this.userService.queryFunc(null,form.getFilter(), form.getPage(), form.getRows());
		}

		return new JsonModelAndView(pd);
	}

	@RequestMapping("/create.do")
	@AuthCfg(auth = true, code = "pages/func-manage.html")
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response,@Valid FunctionForm form, BindingResult result) {
		RespInfo resp = new RespInfo(0, "资源建立成功", null);
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			resp.setValues(-1, fieldError, null);
		} else {
			String parentId = form.getParentId();
			MmFunction func = new MmFunction();
			func.setAddTime(new Date());
			func.setFuncCode(form.getFuncCode());
			func.setName(form.getName());
			func.setOrderNum(form.getOrderNum());
			func.setParentId(parentId);
			func.setRemark(form.getRemark());
			func.setType(form.getType());
			func.setIconCls(form.getIconCls());
			userService.saveEntity(func);
			MmOperationLog opLog=MmToolkit.createOpLog(request);
			opLog.setOpType("C");
			opLog.setNewValue("id="+func.getId()+"&name="+func.getName()+"&funcCode="+func.getFuncCode());
			opLog.setRefTable("MM_FUNCTION");
			opLog.setOpDesc("创建功能");
			userService.saveEntity(opLog);
		}

		return new JsonModelAndView(resp);
	}

	@RequestMapping("/edit.do")
	@AuthCfg(auth = true, code = "pages/func-manage.html")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,@Valid FunctionForm form, BindingResult result) {
		RespInfo resp = new RespInfo(0, "建立成功", null);
		String fieldError = transFieldErrorToMsg(result, request);
		if (fieldError != null) {
			resp.setValues(-1, fieldError, null);
		} else {
			String id = form.getId();
			MmFunction func = (MmFunction) userService.loadEntity(MmFunction.class, id);
			if (func == null) {
				return new JsonModelAndView(resp.setValues(-1, "该记录不存在", null));
			} else {
				func.setFuncCode(form.getFuncCode());
				func.setName(form.getName());
				func.setOrderNum(form.getOrderNum());
				func.setParentId(form.getParentId());
				func.setRemark(form.getRemark());
				func.setType(form.getType());
				func.setIconCls(form.getIconCls());
				userService.updateEntity(func);
				MmOperationLog opLog=MmToolkit.createOpLog(request);
				opLog.setOpType("U");
				opLog.setNewValue("id="+func.getId()+"&name="+func.getName()+"&funcCode="+func.getFuncCode());
				opLog.setRefTable("MM_FUNCTION");
				opLog.setOpDesc("更新功能");
				userService.saveEntity(opLog);
			}
		}

		return new JsonModelAndView(resp);
	}

	@RequestMapping("/view.do")
	@AuthCfg(auth = true, code = "pages/func-manage.html")
	public ModelAndView getRole(HttpServletRequest request, HttpServletResponse response) {
		RespInfo resp = new RespInfo(0, "用户建立成功", null);
		String id = request.getParameter("id");
		if (StringUtils.hasText(id)) {
			MmFunction func = (MmFunction) userService.loadEntity(MmFunction.class, id);
			if (func == null) {
				return new JsonModelAndView(resp.setValues(-1, "资源不存在", null));
			} else {
				FunctionForm form = new FunctionForm();
				form.setId(func.getId());
				form.setFuncCode(func.getFuncCode());
				form.setName(func.getName());
				form.setOrderNum(func.getOrderNum());
				form.setRemark(func.getRemark());
				form.setType(func.getType());
				form.setIconCls(func.getIconCls());
				form.setParentId(func.getParentId() == null ? "" : func.getParentId());
				resp.setData(form);
			}
		}
		return new JsonModelAndView(resp);
	}

	@RequestMapping("/funclist.do")
	@AuthCfg(auth = true, code = "pages/func-manage.html")
	public ModelAndView getFuncs(HttpServletRequest request, HttpServletResponse response) {
		Map<String, MmFunction> funcList = new HashMap<String, MmFunction>();
		List<MmFunction> rootChildren = new ArrayList<MmFunction>();
		List<MmFunction> funcs = userService.queryAllFuncs();
		for (MmFunction f : funcs) {
			if (!StringUtils.hasText(f.getParentId())) {
				rootChildren.add(f);
			} else {
				funcList.put(f.getId(), f);
			}
		}

		return new JsonModelAndView(MenuTreeBuilder.buildTreeData(funcList, rootChildren));
	}

	@RequestMapping("/delete.do")
	@AuthCfg(auth = true, code = "pages/func-manage.html")
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {
		RespInfo resp = new RespInfo(0, "", null);
		String id = request.getParameter("id");
		
		if (StringUtils.hasText(id)) {
			MmFunction func = (MmFunction) userService.loadEntity(MmFunction.class, id);
			if (func == null) {
				return new JsonModelAndView(resp.setValues(-1, "该记录不存在", null));
			} else {
				
				userService.deleteFunction(func);
				MmOperationLog opLog=MmToolkit.createOpLog(request);
				opLog.setOpType("D");
				opLog.setNewValue("id="+func.getId()+"&name="+func.getName()+"&funcCode="+func.getFuncCode());
				opLog.setRefTable("MM_FUNCTION");
				opLog.setOpDesc("删除功能");
				userService.saveEntity(opLog);
			}
		} else {
			return new JsonModelAndView(resp.setValues(-1, "ID is null", null));
		}
		return new JsonModelAndView(resp);
	}
}
