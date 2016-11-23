$(function() {
			$("#datagrid").datagrid(myapp.dgOpts({
						title : '角色管理',
						iconCls : 'icon-user',
						// height : $.browser.msie?480:520,
						fit : true,
						idField : 'id',
						url : '../role/list.do',
						pagination : false,
						columns : [[{
									field : 'id',
									hidden : true
								}, {
									field : 'name',
									title : '角色名',
									align : 'center',
									width : 100
								}, {
									field : 'addTime',
									title : '建立时间',
									align : 'center',
									width : 100
								}, {
									field : 'description',
									title : '说明',
									align : 'center',
									width : 100
								}]]
					}));

		});

function create() {

	$('#new-record-form')[0].reset();
	$('#new-record-dialog').dialog({
		modal : true,
		buttons : [{
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				$('#new-record-form').form('submit', myapp.formOpts({
									url : '../role/create.do?dataType=json',
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
								url : '../role/edit.do?dataType=json',
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
				url : '../role/view.do',
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
		$.messager.confirm("确认", "确认要删除角色 (" + row.name + ") 么？", function(b) {
					if (b) {
						$.ajax({
									url : '../role/delete.do',
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