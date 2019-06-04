//显示首页信息
$("#indexNoticePanl").html("<li>暂无通告信息</li>");
/**
 * 加载通告数据信息
 * @returns
 */
function loadOanotificationFn(basePath){
	$("#indexNoticePanl").html("");
	console.log(basePath);
	var url=basePath+"/oa/oanotification/getLatestNotifyList";
	$.ajax({
		type: "POST",
		url: url,
		contentType:'application/json;charset=utf-8',
		data: null,
		dataType: "json",
		success: function(data){
			if(data.retCode == 0){
				var htmlStr="";
				for(var idx=0;idx<data.retData.length;idx++){
					var objTemp=data.retData[idx];
					htmlStr+="<li style='margin-top:4px;font-size: 14px;'><a href='javascript:void(0)' onclick='showNotifyDetailFn(\""+basePath+"\",\""+objTemp.id+"\")'>关于  \""+objTemp.title+"\"  的公告通知</a></li>";
				}
				$("#indexNoticePanl").html(htmlStr);
			}
			console.log(data.msg);
		},
		error: function (data) {
			console.log("loadOanotificationFn=>"+data);
		}
	});
}

/**
 * 显示消息通知的详情信息
 *
 * @param notifyId
 */
function showNotifyDetailFn(basePath,notifyId){
	var url=basePath+"/oa/oanotification/notifyDetail/"+notifyId;
	top.layer.open({
		type: 2,
		id:"MytaskId",
		area: ["500px", "600px"],
		title: "通知详情",
		maxmin: true, //开启最大化最小化按钮
		content: url ,
		btn: ['关闭'],
		cancel: function(index){

		}
	});
}