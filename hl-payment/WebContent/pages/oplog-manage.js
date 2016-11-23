$(function() {
			$("#datagrid").datagrid(myapp.dgOpts({
						
						//height : $.browser.msie?480:520,
						fit:true,
						fitColumns : false,
						idField : 'id',
						url : '../oplog/list.do',
						columns : [[{
									field : 'id',
									hidden:true
								},{
									field : 'userId',
									title : '用户',
									align : 'center',
									width : 80
								},{
								   field : 'opDesc',
									title : '操作描述',
									align : 'center',
									width : 120
								},{
									field : 'srcIp',
									title : 'IP',
									align : 'center',
									width : 90
								},{
									field : 'funcCode',
									title : '功能代码',
									align : 'center',
									width : 160
								}, {
									field : 'opTime',
									title : '操作时间',
									align : 'center',
									width : 120
								}, {
									field : 'clientDesc',
									title : '客户端',
									align : 'center',
									width : 600
								}, {
									field : 'remark',
									title : '说明',
									align : 'center',
									width : 100
								}]]
					}));
		});
function query() {
	
	console.info(document.getElementById($('#jie').value));
	
	$('#datagrid').datagrid('load', $("#searchForm").serializeObject());
}
function  view(){
	var row = $("#datagrid").datagrid('getSelected');
	if(row && row.id){
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
					url : '../oplog/view.do',
					data : {
						id : row.id
					},
					success : function(resp) {
						$('#view-record-dialog').unmask();
						if (resp.code == 0) {
							var userData = resp.data;
							$('#view-record-form').form('load', userData);
						} else {
							$.messager.alert('提示', resp.message, 'error');
						}
					}
				});
	}
}