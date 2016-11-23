package com.hl.web.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hl.common.model.BaseBean;
import com.hl.web.entity.MmFunction;

/**
 * 一次把树形菜单数据加载完，不异步加载
 * @author 
 *
 */
public class MenuTreeBuilder {

	public static List<?> buildTreeData(Map<String, MmFunction> funList,List<MmFunction> rootChildren){
		if(funList!=null&&!funList.isEmpty()&&rootChildren!=null&&!rootChildren.isEmpty()){
			List<Node> rnodes=new ArrayList<MenuTreeBuilder.Node>();
			Set<String> idset=new HashSet<String>();
			for(MmFunction f:rootChildren){
				if(!idset.contains(f.getId())){//过滤掉重复的
				   rnodes.add(buildChildren(funList, new Node(f)));
				   idset.add(f.getId());
				   System.out.println(f.getIconCls()+" ID:"+f.getId()+" parID:"+f.getParentId()+" NAME:"+f.getName());
				}
			}
			idset.clear();
			idset=null;
			sortNodes(rnodes);
			
			return rnodes;
		}
		return Collections.EMPTY_LIST;
	}
	
	private static void sortNodes(List<Node> nodes){
		if(nodes!=null&&nodes.size()>1){
			Collections.sort(nodes);
			for(Node n:nodes){
				if(n.children.size()>1){
					sortNodes(n.children);
				}
			}
		}
		
	}
	
	private static Node  buildChildren(Map<String, MmFunction> funList,Node n){
		List<MmFunction> values=new ArrayList<MmFunction>(funList.values());
		for(MmFunction f:values){
			if(f.getParentId().equals(n.id)){
				funList.remove(f.getId());
				n.children.add(buildChildren(funList, new Node(f)));
			}
		}
		return n;
	}
	
	public static class Node extends BaseBean implements Comparable<Node>{
		private String id;
		private String iconCls;
		private String text;
		private int orderNum;
		private List<Node> children=new ArrayList<MenuTreeBuilder.Node>();
		private Map<String, String> attributes=new HashMap<String, String>();
		public Node(MmFunction f) {
			super();
			id=f.getId();
			text=f.getName();
			iconCls=f.getIconCls();
			orderNum=(f.getOrderNum()==null?0:f.getOrderNum().intValue());
			attributes.put("url", f.getFuncCode());
		}
		@Override
		public int compareTo(Node o) {
			
			return orderNum-o.orderNum;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getIconCls() {
			return iconCls;
		}
		public void setIconCls(String iconCls) {
			this.iconCls = iconCls;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public int getOrderNum() {
			return orderNum;
		}
		public void setOrderNum(int orderNum) {
			this.orderNum = orderNum;
		}
		public List<Node> getChildren() {
			return children;
		}
		public void setChildren(List<Node> children) {
			this.children = children;
		}
		public Map<String, String> getAttributes() {
			return attributes;
		}
		public void setAttributes(Map<String, String> attributes) {
			this.attributes = attributes;
		}
		@Override
		public String toString() {
			return "{id:"+id+",text:"+text+",orderNum:"+orderNum+"}";
		}
		
	}
}
