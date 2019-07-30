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

/**
 * 查询商户订单信息
 *
 */
function createShopOrderShowHtmlFn(dataArrays) {
    var showHtml = "";
    for (var idx = 0; idx < dataArrays.length; idx++) {
        var dataObj = dataArrays[idx];
        var headTitleHtml=createHeadTitleFn(dataObj.headTitle);
        var dataBodyHtml=createDataBodyFn(dataObj.dataBody);
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
    headTitleHtml += "<span>订单号:20190313001</span>";
    headTitleHtml += "<span>下单时间:2019-03-19 12:23:30</span>";
    headTitleHtml += "<span>付款时间:2019-03-19 12:23:30</span>";
    headTitleHtml += "</td>";
    headTitleHtml += "</tr>";
    return headTitleHtml;
}

/**
 * 生成信息展示的主体部分
 *
 * @param dataBodyObjArray
 * @returns {string}
 */
function createDataBodyFn(dataBodyObjArray) {
    var retHtml = "";
    var count = dataBodyObjArray.size;
    for (var idx = 0; idx < count; idx++) {
        var dataBodyHtml = "<tr>";
        dataBodyHtml += "<td colspan=\"2\"><img src=\"${staticPath}/common/img/default_avatar_male.jpg\"></td>";
        dataBodyHtml += "<td><div>基本信息</div><div>规格:</div></td>";
        dataBodyHtml += "<td class=\"price_num_td\"><div>￥189.00元</div><div>x1</div></td>";
        if (idx == 0) {
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">货款金额</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">下单帐号</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">物流信息</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">商家备注</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">订单状态</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\"><div><a>详情</a></div><div><a>出库</a></div></td>";
        }
        dataBodyHtml += "</tr>";
        retHtml += dataBodyHtml;
    }
    return retHtml;
}
/**
 * 增加订单出库操作
 *
 */
function addOutStoreFn(){

}