//显示首页信息
$("#indexNoticePanl").html("<li>暂无通告信息</li>");

/**
 * 加载通告数据信息
 * @returns
 */
function loadOanotificationFn(basePath) {
    loadLatestNotifyFn(basePath);
    loadNeedFinishTaskFn(basePath);
}

/**
 * 获取最近的20条消息通知信息
 *
 * @param basePath
 */
function loadLatestNotifyFn(basePath) {
    $("#indexNoticePanl").html("");
    console.log(basePath);
    var url = basePath + "/oa/oanotification/getLatestNotifyList";
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json;charset=utf-8',
        data: null,
        dataType: "json",
        success: function (data) {
            if (data.retCode == 0) {
                var htmlStr = "";
                for (var idx = 0; idx < data.retData.length; idx++) {
                    var objTemp = data.retData[idx];
                    htmlStr += "<li style='margin-top:4px;font-size: 14px;'><a href='javascript:void(0)' onclick='showNotifyDetailFn(\"" + basePath + "\",\"" + objTemp.id + "\")'>关于  \"" + objTemp.title + "\"  的公告通知</a></li>";
                }
                $("#indexNoticePanl").html(htmlStr);
            }
            console.log(data.msg);
        },
        error: function (data) {
            console.log("loadOanotificationFn=>" + data);
        }
    });
}

/**
 * 加载最近的需要确认的任务单
 */
function loadNeedFinishTaskFn(basePath) {
    var url = basePath + '/buyer/TmyTaskDetail/getLatestNeedFinishTaskList';
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json;charset=utf-8',
        data: null,
        dataType: "json",
        success: function (data) {
            if (data.code == 0 && data.rows > 0) {
                showNeedFinsishTaskTadaFn(data.results);
            }
            console.log(data.msg);
        },
        error: function (data) {
            console.log("loadOanotificationFn=>" + data);
        }
    });
}

function showNeedFinsishTaskTadaFn(showData) {
    jQuery("#needFinishTaskGrid").jqGrid({
        datatype: "local",
        height: 280,
        rownumbers: true,
        colNames: ['京东单号', '下单时间', '买手名称', '店铺名称'],
        colModel: [
            {name: 'jdorderno', index: 'jdorderno', sortable: false, align: "center", fixed: true, width: 200},
            {name: 'orderdate', index: 'orderdate', sortable: false, align: "center", fixed: true, width: 200},
            {name: 'buyeridName', index: 'buyeridName', sortable: false, align: "center", fixed: true, width: 200},
            {name: 'shopidName', index: 'shopidName', sortable: false, align: "center", fixed: true, width: 200}
        ],
        multiselect: false,
        ondblClickRow: function (rowid, iRow, iCol, e) {
            alert('双击我,查看详情');
        },
        gridComplete: function () {
            $("#lui_needFinishTaskGrid").remove();
        }
    });
    for (var i = 0; i <= showData.length; i++) {
        jQuery("#needFinishTaskGrid").jqGrid('addRowData', i + 1, showData[i]);
    }
}

/**
 * 显示消息通知的详情信息
 *
 * @param notifyId
 */
function showNotifyDetailFn(basePath, notifyId) {
    var url = basePath + "/oa/oanotification/notifyDetail/" + notifyId;
    top.layer.open({
        type: 2,
        id: "MytaskId",
        area: ["900px", "950px"],
        title: "通知详情",
        maxmin: true, //开启最大化最小化按钮
        content: url,
        btn: ['关闭'],
        cancel: function (index) {

        }
    });
}