$(function() {
			$("#datagrid").datagrid(myapp.dgOpts({
						title : '资源列表',
						// height : $.browser.msie?480:520,
						fit : true,
						idField : 'id',
						url : '../func/list.do',
						columns : [[{
									field : 'id',
									hidden : true
								}, {
									field : 'name',
									title : '资源名',
									align : 'center',
									width : 100
								}, {
									field : 'funcCode',
									title : '编码(页面地址)',
									align : 'center',
									width : 100
								}, {
									field : 'type',
									title : '类型',
									align : 'center',
									formatter : function(v, r) {
										if (v == 1) {
											return '菜单';
										} else if (v == 2) {
											return '子功能';
										} else {
											return '未知';
										}
									},
									width : 100
								}, {
									field : 'parentName',
									title : 'PARENT',
									align : 'center',
									width : 100
								}, {
									field : 'iconCls',
									title : '菜单CSS',
									align : 'center',
									width : 100
								}, {
									field : 'orderNum',
									title : '排序号',
									align : 'center',
									width : 60
								}, {
									field : 'addTime',
									title : '建立时间',
									align : 'center',
									width : 100
								}, {
									field : 'remark',
									title : '说明',
									align : 'center',
									width : 100
								}]]
					}));
		});
function query() {
	$('#datagrid').datagrid('load', $("#searchForm").serializeObject());
}
function create() {
	$('#new-record-form')[0].reset();
	$('#new-record-dialog').dialog({
		modal : true,
		buttons : [{
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				$('#new-record-form').form('submit', myapp.formOpts({
									url : '../func/create.do?dataType=json',
									enableSuccMsg : true,
									maskEl : '#new-record-dialog',
									onSubmit : function() {
										return $(this).form('validate');
									},
									success : function(data, resp) {
										if (resp.code == 0) {
											$('#new-record-dialog')
													.dialog('close');
											$('#datagrid').datagrid('reload');
										}
									}
								}));
			}
		}, {
			text : '重置',
			iconCls : 'icon-redo',
			handler : function() {
				$('#new-record-form')[0].reset();
			}
		}]
	});
}

function edit() {
	var row = $("#datagrid").datagrid("getSelected");
	if (row && row.id) {
		$("#edit-record-form").form("clear");
		$("#edit-record-dialog").dialog({
			modal : true,
			buttons : [{
				text : "提交",
				iconCls : 'icon-ok',
				handler : function() {
					$('#edit-record-form').form('submit', myapp.formOpts({
								url : '../func/edit.do?dataType=json',
								enableSuccMsg : true,
								maskEl : '#edit-record-dialog',
								onSubmit : function() {
									return $(this).form('validate');
								},
								success : function(data, resp) {
									if (resp.code == 0) {
										$('#edit-record-dialog')
												.dialog('close');
										$('#datagrid').datagrid('reload');
									}
								}
							}));
				}
			}]
		})
	}
	$('#edit-record-dialog').mask('加载中 ... ');
	$.ajax({
				url : '../func/view.do',
				data : {
					id : row.id
				},
				success : function(resp) {
					$('#edit-record-dialog').unmask();
					if (resp.code == 0) {
						var userData = resp.data;
						$('#edit-record-form').form('load', userData);
					} else {
						$.messager.alert('提示', resp.message, 'error');
					}
				}
			});
}
function deleteIt() {
	var row = $("#datagrid").datagrid("getSelected");
	if (row && row.id) {
		$.messager.confirm("确认", "确认要删除功能资源 (" + row.name + ") 么？",
				function(b) {
					if (b) {
						$.ajax({
									url : '../func/delete.do',
									data : {
										id : row.id
									},
									success : function(resp) {
										if (resp.code == 0) {
											$('#datagrid').datagrid('reload');
										} else {
											$.messager.alert('提示',
													resp.message, 'error');
										}
									}
								});
					} else {

					}
				});
	}

}