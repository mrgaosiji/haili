$(function() {
			$("#datagrid").datagrid(myapp.dgOpts({
						
						//height : $.browser.msie?480:520,
						fit:true,
						fitColumns : false,
						url : '../placefile/applyview.do',
						checkOnSelect:true,
						selectOnCheck:false,
						singleSelect:false,
						columns : [[
						            {
	 	                             field : 'applyDateBegin',
		                             hidden:true
	                                },
	                                {
		                             field : 'applyDateEnd',
		                             hidden:true
	                                 },
						            {
									 field : 'applyId',
									 hidden:true
								    },
								   {
									checkbox:true
								   },
								   {
									field : 'tableName',
									title : '归档表',
									align : 'center',
									width : 120
								  },
									{
										field : 'objectChn',
										title : '中文名',
										align : 'center',
										width : 120
										
									},
									{
										field : 'loadType',
										title : '加载类型',
										align : 'center',
										width : 120,
										formatter : function(value, rec) {
											console.info(value);
											if (value == "INIT") {
												return "初始化";
											} 
											else if (value == "ADD") {
												return "数据新增和修改";
											} 
											else if (value == "ALL") {
												return "数据全量";
											} 
											}
									},
									{
										field : 'playOnFileType',
										title : '归档方式',
										align : 'center',
										width : 120,
										formatter : function(value, rec) {
											if (value == 0) {
												return "正常归档";
											} else if (value == 1) {
												return "追加归档";
											}
											}
									},
									{
										field : 'organizationType',
										title : '机构属性',
										align : 'center',
										width : 120,
										formatter : function(value, rec) {
											if (value == "BR0") {
												return "不按分行提供";
											} else if (value == "BR2") {
												return "按南北中心提供";
											}
											else if (value == "BR11") {
												return "按11家海外分行提供";
											}
											else if (value == "BR38") {
												return "按38家一级行提供";
											}
											else if (value == "BR39") {
												return "按38家一级行+011提供";
											}
											else if (value == "BR39+01N") {
												return "按BR39+北中心提供";
											}
											else if (value == "BR41") {
												return "按BR39+南北中心提供";
											}
											
											return "未知";
										}
									},
									{
									   field : 'systemName',
										title : '所属系统',
										align : 'center',
										width : 120
									},
									{
								      field : 'systemChn',
									  title : '框架单元中文名',
								      align : 'center',
									  width : 120
									},
									{
										field : 'nodeId',
										title : '安全节点号',
										align : 'center',
										width : 80
									},{
										field : 'frequency',
										title : '供数频率',
										align : 'center',
										width : 80,
										formatter : function(value, rec) {
											if (value == "Q") {
												return "按季";
											} else if (value == "M") {
												return "按月";
											}
											else if (value == "D") {
												return "按日";
											}
											else if (value == "Y") {
												return "按年";
											}
											else if (value == "W") {
												return "按周";
											}
											else if (value == "W") {
												return "按周";
											}
											else if (value == "X") {
												return "按旬";
											}
											else if (value == "H") {
												return "按半年";
											}
											
											return "未知";
										}
									}, {
										field : 'applyFirstDate',
										title : '首次供数业务数据日期',
										align : 'center',
										width : 80
									}, {
										field : 'applyLastDate',
										title : '最后供数业务数据日期',
										align : 'center',
										width : 80
									}, {
										field : 'applyStartDate',
										title : '任务生效自然日期',
										align : 'center',
										width : 80
									}, {
										field : 'fileType',
										title : '数据文件类型',
										align : 'center',
										width : 100
									}, 
									{
										field : 'addSpace',
										title : '增量文件大小',
										align : 'center',
										width : 100
									}, 
									{
										field : 'saveCircle',
										title : '归档保存周期(月)',
										align : 'center',
										width : 100
									}, 
									{
										field : 'status',
										title : '归档状态',
										align : 'center',
										width : 80,
										formatter : function(value, rec) {
											if (value == 0) {
												return "未处理";
											} else if (value == 1) {
												return "已处理";
											}
											return "未知";
										}
									},
									{
										field : 'detailFieldName',
										title : '明细数据日期字段名称',
										align : 'center',
										width : 120
									},
									{
										field : 'allSpace',
										title : '主档全量截面数据大小',
										align : 'center',
										width : 120
									},
									{
										field : 'sectionRequired',
										title : '截面需求',
										align : 'center',
										width : 120
									},
									{
										field : 'batchNo',
										title : '最大批次号',
										align : 'center',
										width : 120
									}
								]]
					}));
		});
function query() {
	
	//console.info($("#s").datebox("getValue"));  
	
	$('#datagrid').datagrid('load', $("#searchForm").serializeObject());

}

function productExcelurl(){
	
//	var row = $("#datagrid").datagrid('getSelected');
	
	var rows = $("#datagrid").datagrid('getChecked');
	
	console.info(rows.length);
	
	if(rows)
	{
		$('#toolbar').mask('归档处理中...');
		$.ajax({
			url : '../placefile/handleapplyinfo.do',
			data:{
				ids : rows
			},
			success:function(resp)
			{
				$('#toolbar').unmask();
				if (resp.code == 0) {
//					var applyData = resp.data;
					$('#datagrid').datagrid('reload');
					
					$.messager.alert('提示', resp.message,'info');
					
				} else {
					$.messager.alert('提示', resp.message, 'error');
				}
				
				$('#datagrid').datagrid('clearChecked');
			}
		})
	}
	
	
	
}


function executehandlefile(){
	
	var rows =  $('#datagrid').datagrid('getChecked');
	
	var strs =[];
	
	for(var i=0; i<rows.length; i++){
		
		var s = rows[i];
        
		strs.push(s.applyId);
		
		console.info('id值:'+s.id);
	}
	
	console.info(strs);	
	
	if(strs)
	{
		$('#toolbar').mask('归档处理中...');
		$.ajax({
			url : '../placefile/handleapplyinfo.do',
			data:{
				ids : strs
			},
			success:function(resp)
			{
				$('#toolbar').unmask();
				if (resp.code == 0) {
//					var applyData = resp.data;
					$('#datagrid').datagrid('reload');
					
					$.messager.alert('提示', resp.message,'info');
					
				} else {
					$.messager.alert('提示', resp.message, 'error');
					
				}
				
				$("#datagrid").datagrid('clearChecked');
			}
		})
	}
}

function startprogress() {
	
    var value = $('#p').progressbar('getValue');
    if (value < 100) 
    {
      value += Math.floor(Math.random() * 10);
      $('#p').progressbar('setValue', value);
        setTimeout(arguments.callee, 200);
      if (value >= 100) 
      {
        $('#button').linkbutton('disable');//文件上传成功之后禁用按钮
        $('#p').progressbar('disable');//文件上传成功之后禁用进度条
      }
    }
  }


function importapplyfile(){
	
	
	$("importExcelForm").form('clear');
	
	
	$("#choosedfile").form('clear');
	
	$("#t").hide();
	
	$("#view-apply-file").dialog({
		modal:true,
		buttons:[{text:'确定导入',iconCls:'icon-ok',handler:function(){
			
			
			  var file = $('input[name=choosedfile]').val(); 
			  
			  if(file.lastIndexOf('.xls')==-1&&file.lastIndexOf('.xlsx')==-1)
			  {
				  $.messager.alert('提示框','附件格式不符，请选择excel文档','error');
			  }
			  
			  //$("#t").show();
			  
			  startprogress();
			  
			  $.ajax({
					//url : '../placefile/placefileview.do',
			url : '../placefile/importapplyinfo.do',		
			data : {
						url : file
					},
					success : function(resp) {
						
						if (resp.code == 0) {
							var applyData = resp.data;
							
							$.messager.alert('提示', '导入完成');
							
						} else {
							$.messager.alert('提示', resp.message, 'error');
						}
					}
				});

		}},
		    {text:'取消操作',iconCls:'icon-cancel',handler:function(){$('#view-apply-file').dialog('close')}}]
		          
	});
}

function  view(){
	var row = $("#datagrid").datagrid('getSelected');
	if(row && row.applyId){
		$('#view-apply-form').form('clear');
		$('#view-apply-dialog').dialog({
					modal : true,
					buttons : [{
								text : '关闭',
								iconCls : 'icon-ok',
								handler : function() 
								{
									$('#view-apply-dialog').dialog('close');
								}
							}]
				});
		$('#view-record-dialog').mask('加载中 ... ');
		$.ajax({
					//url : '../placefile/placefileview.do',
			url : '../placefile/view.do',		
			data : {
						id : row.applyId
					},
					success : function(resp) {
						$('#view-apply-dialog').unmask();
						if (resp.code == 0) {
							var applyData = resp.data;
							//$('#view-apply-dialog').dialog('reload');
							$('#view-apply-form').form('load', applyData);
						} else {
							$.messager.alert('提示', resp.message, 'error');
						}
					}
				});
	}
}