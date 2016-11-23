$(function() {
			$("#datagrid").datagrid(myapp.dgOpts({
						title : '用户管理',
						iconCls : 'icon-user',
						//height : $.browser.msie?480:520,
						fit:true,
						idField : 'userId',
						url : '../user/list.do',
						columns : [[{
									field : 'userId',
									title : '用户名',
									align : 'center',
									width : 100
								}, {
									field : 'gender',
									title : '性别',
									width : 30,
									align : 'center',
									formatter : function(value, rec) {
										if (value == 1) {
											return "男";
										} else if (value == 2) {
											return "女";
										}
										return "未知";
									}
								}, {
									field : 'fullName',
									title : '姓名',
									align : 'center',
									width : 100
								}, {
									field : 'cellphone',
									title : '电话',
									align : 'center',
									width : 100
								}, {
									field : 'status',
									title : '状态',
									width : 30,
									align : 'center',
									rowspan : 2,
									formatter : function(value, rec) {
										if (value == 0) {
											return '<font color="green">正常</font>';
										} else {
											return '<font color="red">停用</font>';
										}
									}
								}, {
									field : 'addTime',
									title : '建立时间',
									align : 'center',
									width : 100
								}, {
									field : 'lastLoginTime',
									title : '最近登录',
									align : 'center',
									width : 100
								}]],
						onSelect : function(index, row) {
							if (row) {
								if (row.status == 0) {
									$("#enableUserBtn").linkbutton('disable');
									$("#disableUserBtn").linkbutton('enable');
								} else {
									$("#enableUserBtn").linkbutton('enable');
									$("#disableUserBtn").linkbutton('disable');
								}
							}
						}
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
									url : '../user/create.do?dataType=json',
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
function remove() {
	var row = $('#datagrid').datagrid('getSelected');
}
function edit() {
	var row = $('#datagrid').datagrid('getSelected');
	if (row && row.userId) {
		$('#edit-record-form').form('clear');
		$('#edit-record-dialog').dialog({
			modal : true,
			buttons : [{
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					$('#edit-record-form').form('submit', myapp.formOpts({
								url : '../user/edit.do?dataType=json',
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
		});
		$('#edit-record-dialog').mask('加载中 ... ');
		$.ajax({
					url : '../user/view.do',
					data : {
						userName : row.userId
					},
					success : function(resp) {
						$('#edit-record-dialog').unmask();
						if (resp.code == 0) {
							var userData = resp.data;
							if (userData.status == 0) {
								userData.statusText = '正常';
							} else {
								userData.statusText = '停用';
							}
							if (userData.gender == 1) {
								userData.genderText = '男';
							} else if (userData.gender == 2) {
								userData.genderText = '女';
							} else {
								userData.genderText = '保密';
							}
							if (userData.roleNames
									&& userData.roleNames.length > 0) {
								userData.roleNames = userData.roleNames
										.join('\n');
							}
							$('#edit-record-form').form('load', userData);
						} else {
							$.messager.alert('提示', resp.message, 'error');
						}
					}
				});
	}

}
function enableUser() {
	var row = $('#datagrid').datagrid('getSelected');
	if (row) {
		if (row.status == 0) {
			$.messager.alert('提示', '用户状态正常，不需要启用', 'info');
		} else {
			$.ajax({
						url : '../user/edit.do',
						data : {
							status : 0,
							changeStatusAction : '1',
							userName : row.userId
						},
						success : function(resp) {
							if (resp.code == 0) {
								$('#datagrid').datagrid('reload');
							} else {
								$.messager.alert('提示', resp.message, 'error');
							}
						}
					});
		}
	}
}
function disableUser() {
	var row = $('#datagrid').datagrid('getSelected');
	if (row) {
		if (row.status == 1) {
			$.messager.alert('提示', '用户状态已为停用，不需要停用', 'info');
		} else {
			$.ajax({
						url : '../user/edit.do',
						data : {
							status : 1,
							changeStatusAction : '1',
							userName : row.userId
						},
						success : function(resp) {
							if (resp.code == 0) {
								$('#datagrid').datagrid('reload');
							} else {
								$.messager.alert('提示', resp.message, 'error');
							}
						}
					});
		}
	}
}

function view() {
	var row = $('#datagrid').datagrid('getSelected');
	if (row && row.userId) {
		$('#view-record-form').form('clear');
		$('#view-record-dialog').dialog({
					modal : true,
					buttons : [{
								text : '关闭',
								iconCls : 'icon-ok',
								handler : function() {
									$('#view-record-dialog').dialog('close');
								}
							}]
				});
		$('#view-record-dialog').mask('加载中 ... ');
		$.ajax({
					url : '../user/view.do',
					data : {
						userName : row.userId
					},
					success : function(resp) {
						$('#view-record-dialog').unmask();
						if (resp.code == 0) {
							var userData = resp.data;
							if (userData.status == 0) {
								userData.statusText = '正常';
							} else {
								userData.statusText = '停用';
							}
							if (userData.gender == 1) {
								userData.genderText = '男';
							} else if (userData.gender == 2) {
								userData.genderText = '女';
							} else {
								userData.genderText = '保密';
							}
							if (userData.roleNames
									&& userData.roleNames.length > 0) {
								userData.roleNames = userData.roleNames
										.join('\n');
							}
							$('#view-record-form').form('load', userData);
						} else {
							$.messager.alert('提示', resp.message, 'error');
						}
					}
				});
	}
}