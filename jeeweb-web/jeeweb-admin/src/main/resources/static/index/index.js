//显示首页信息
$("#indexNoticePanl").html("<li>暂无通告信息</li>");
/**
 * 加载通告数据信息
 * @returns
 */
function loadOanotificationFn(basePath){
	$("#indexNoticePanl").html("");
	console.log(basePath);
	var url=basePath+"/oa/oanotification/ajaxList";
	$.ajax({
		type: "POST",
		url: url,
		contentType:'application/json;charset=utf-8',
		data: null,
		dataType: "json",
		success: function(data){
			console.log(data.msg);
		},
		error: function (data) {
			console.log("loadOanotificationFn=>"+data);
		}
	});
}