/**
 * 点击标签页
 * @param tab
 * 1全部订单
 * 2待出库
 * 3已出库
 */
function chooseTabFn(basePath,tab){
    $("#shopOrderTabs table tbody").html("");
    var url = basePath + '/logisticsOrder/logistics/ajaxShopOrderList';
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json;charset=utf-8',
        data: null,
        dataType: "json",
        success: function (data) {
            if (data.retCode != 0) {

                return;
            }
            var dataArrays=data.retData.results;
            var htmlData=createShopOrderShowHtmlFn(dataArrays);
            $("#showAllShopOrder tbody").html(htmlData);
            showPageFn(data.retData.page,data.retData.totalPages,data.retData.total);
        },
        error: function (data) {
            console.log("loadOanotificationFn=>" + data);
        }
    });
}

/**
 * 查询商户订单信息
 *
 */
function createShopOrderShowHtmlFn(dataArrays) {
    var showHtml = "";
    for (var idx = 0; idx < dataArrays.length; idx++) {
        var dataObj = dataArrays[idx];
        var headTitleHtml=createHeadTitleFn(dataObj.shopOrderShowTitle);
        var dataBodyHtml=createDataBodyFn(dataObj.shopOrderShowTitle,dataObj.shopOrderShowDataList);
        showHtml += headTitleHtml+dataBodyHtml;
    }
    return showHtml;
}

/**
 * 生成一行数据的头部信息
 *
 * @param headTitleObj
 * @returns {string}
 */
function createHeadTitleFn(headTitleObj){
    var headTitleHtml = "<tr class=\"head_tr\">";
    headTitleHtml += "<td class=\"checkbox_td\"><input type=\"checkbox\" /></td>";
    headTitleHtml += "<td colspan=\"9\" class=\"orderTitle\">";
    headTitleHtml += "<span>订单号:"+headTitleObj.jdOrderNo+"</span>";
    headTitleHtml += "<span>下单时间:"+headTitleObj.orderDtime+"</span>";
    headTitleHtml += "<span>付款时间:"+headTitleObj.orderPayTime+"</span>";
    headTitleHtml += "</td>";
    headTitleHtml += "</tr>";
    return headTitleHtml;
}

/**
 * 生成信息展示的主体部分
 *
 * @param dataHeadObj
 * @param dataBodyObjArray
 * @returns {string}
 */
function createDataBodyFn(dataHeadObj,dataBodyObjArray) {
    var retHtml = "";
    var count = dataBodyObjArray.length;
    for (var idx = 0; idx < count; idx++) {
        var detailObj=dataBodyObjArray[idx];
        var dataBodyHtml = "<tr>";
        dataBodyHtml += "<td colspan=\"2\"><img title='"+detailObj.goodsName+"' src=\""+detailObj.goodsImgUrl+"\"></td>";
        dataBodyHtml += "<td><div class='goodsInfo' title='"+detailObj.goodsName+"'><a href=''>"+detailObj.goodsNameShort+"</a></div><div class='goodsInfo'>规格:"+detailObj.goodsSpecDesc+"</div></td>";
        dataBodyHtml += "<td class=\"price_num_td\"><div>￥"+detailObj.goodsPrice+"元</div><div>x"+detailObj.goodsNum+"</div></td>";
        if (idx == 0) {
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">￥&nbsp;"+dataHeadObj.orderTotalMoney+"&nbsp;元</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">"+dataHeadObj.buyerJdLoginNo+"</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">"+dataHeadObj.logisticsInfo+"</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">￥&nbsp;"+dataHeadObj.outStoreCommissionPrice+"&nbsp;元</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">"+dataHeadObj.orderStatusName+"</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">";
            dataBodyHtml += "<div><a href='javascript:void(0)' onclick='showOrderInfoFn()'>详&nbsp;&nbsp;情</a></div>";
            dataBodyHtml +="</td>";
        }
        dataBodyHtml += "</tr>";
        retHtml += dataBodyHtml;
    }
    return retHtml;
}

/**
 * 展示分页数据
 *
 * @param curentPage
 * @param totalPage
 * @param totalRecord
 */
function showPageFn(curentPage,totalPage,totalRecord){
    var pageHtml="<a>第一页</a>&nbsp;&nbsp;<a>上一页</a>&nbsp;&nbsp;<a>下一页</a>&nbsp;&nbsp;<a>尾页</a>";
    pageHtml+="&nbsp;&nbsp;共&nbsp;<span id='currentPage'>"+curentPage+"</span>&nbsp;/&nbsp;<span id='totalPage'>"+totalPage+"</span>&nbsp;页";
    pageHtml+="&nbsp;&nbsp;共&nbsp;<span id='totalRecords'>"+totalRecord+"</span>&nbsp;记录";
    $("#orderPageDiv").html(pageHtml);
}

/**
 * 全选/反选复选框
 *
 * @param obj
 */
function checkAllBoxFn(obj){
    var parantTbId=$(obj).attr("parantTbId");
    $("#"+parantTbId).find(" tbody input[type=checkbox][parantTbId="+parantTbId+"]").attr("checked",$(obj).attr("checked"));
}
/**
 * 增加订单出库操作
 *
 */
function addOutStoreFn(jdOrderNo){
    var url = '${adminPath}/logisticsOrder/logistics/addOutStoreOrder';
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json;charset=utf-8',
        data: null,
        dataType: "json",
        success: function (data) {
            alert(data.msg);
        },
        error: function (data) {
            console.log("loadOanotificationFn=>" + data);
        }
    });
}

function addOutStoreBatchFn(obj){
    var parantTbId=$(obj).attr("parantTbId");
    var checkedBox="";
    $("#"+parantTbId).find(" tbody input[type=checkbox][parantTbId="+parantTbId+"]:checked").each(function(){

    });
};

/**
 * 显示订单详情信息
 *
 * @param jdOrderNo
 */
function showOrderInfoFn(jdOrderNo){

}