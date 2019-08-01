/**
 * 点击标签页
 * @param tab
 * 1全部订单
 * 2待出库
 * 3已出库
 */
function chooseTabFn(tab){
    createShopOrderShowTbHeadHtmlFn(tab);
    var basePath=$("#queryBasePath").val();
    var queryType=0
    if(1 == tab){
        queryType=2;
    }else if(2 == tab){
        queryType=3;
    }
    $("#logisticsQueryShopOrderForm input[name=queryType]").val(queryType);
    var queryParam={};
    $("#logisticsQueryShopOrderForm input").each(function(){
        queryParam[$(this).attr("name")]=$(this).val();
    });
    $("#shopOrderTabs table tbody").html("");
    var url = basePath + '/logisticsOrder/logistics/ajaxShopOrderList';
    $.ajax({
        type: "POST",
        url: url,
        contentType:'application/json;charset=utf-8',
        data: JSON.stringify(queryParam),
        dataType: "json",
        success: function (data) {
            if (data.retCode != 0) {

                return;
            }
            var dataArrays=data.retData.results;
            createShopOrderShowHtmlFn(tab,dataArrays);
            showPageFn(data.retData.page,data.retData.totalPages,data.retData.total);
        },
        error: function (data) {
            console.log("chooseTabFn=>" + data);
        }
    });
}

function createShopOrderShowTbHeadHtmlFn(tab){
    var availabledeposit=$("#availabledeposit").val();
    var headHtml="";
    var showDataTbId="showOutAckShopOrder";
    if(0 == tab){
        showDataTbId="showAllShopOrder";
        headHtml="<tr>";
        headHtml+="<th colspan=\"2\"><button type=\"button\" onclick=\"addOutStoreFn(this)\" tableId=\""+showDataTbId+"\">出&nbsp;&nbsp;库</button></th>";
        headHtml+="<th colspan=\"8\">";
        headHtml+="<span>商户余额:&nbsp;&nbsp;￥&nbsp;&nbsp;<label class=\"priceLabel\">"+availabledeposit+"</label>&nbsp;&nbsp;元</span>";
        headHtml+=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        headHtml+="<span>订单佣金总额:&nbsp;&nbsp;￥&nbsp;&nbsp;<label name=\"outStoreCommissionLabel\" class=\"priceLabel\">0.00</label>&nbsp;&nbsp;元</span>";
        headHtml+="</th>";
        headHtml+="</tr>";
    }else if(1 == tab){
        showDataTbId="showWaitingOutShopOrder";
        headHtml="<tr>";
        headHtml+="<th colspan=\"2\"><button type=\"button\" onclick=\"addOutStoreFn(this)\" tableId=\""+showDataTbId+"\">出&nbsp;&nbsp;库</button></th>";
        headHtml+="<th colspan=\"8\">";
        headHtml+="<span>商户余额:&nbsp;&nbsp;￥&nbsp;&nbsp;<label class=\"priceLabel\">"+availabledeposit+"</label>&nbsp;&nbsp;元</span>";
        headHtml+=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        headHtml+="<span>订单佣金总额:&nbsp;&nbsp;￥&nbsp;&nbsp;<label name=\"outStoreCommissionLabel\" class=\"priceLabel\">0.00</label>&nbsp;&nbsp;元</span>";
        headHtml+="</th>";
        headHtml+="</tr>";
    }
    headHtml+="<tr>";
    headHtml+="<th style='width: 22px;'><input type=\"checkbox\" class='checkbox_input' onchange=\"checkAllBoxFn(this)\" parantTbId=\""+showDataTbId+"\" /></th>";
    headHtml+="<th style='width: 45px;'>&nbsp;</th>";
    headHtml+="<th style='width:20%'>商品信息</th>";
    headHtml+="<th style='width:10%'>单价/数量</th>";
    headHtml+="<th style='width:10%'>货款金额</th>";
    headHtml+="<th style='width:10%'>下单帐号</th>";
    headHtml+="<th style='width:10%'>物流信息</th>";
    headHtml+="<th style='width:12%'>出库佣金</th>";
    headHtml+="<th style='width:10%'>订单状态</th>";
    headHtml+="<th style='width:60px'>操作</th>";
    headHtml+="</tr>";
    $("#"+showDataTbId+" thead").html(headHtml);
}


/**
 * 查询商户订单信息
 *
 */
function createShopOrderShowHtmlFn(tab,dataArrays) {
    var showDataTbId="showAllShopOrder";
    if(1 == tab){
        showDataTbId="showWaitingOutShopOrder";
    }else if(2 == tab){
        showDataTbId="showOutAckShopOrder";
    }
    var showHtml = "";
    for (var idx = 0; idx < dataArrays.length; idx++) {
        var dataObj = dataArrays[idx];
        var headTitleHtml=createHeadTitleFn(dataObj.shopOrderShowTitle,showDataTbId);
        var dataBodyHtml=createDataBodyFn(dataObj.shopOrderShowTitle,dataObj.shopOrderShowDataList);
        showHtml += headTitleHtml+dataBodyHtml;
    }
    $("#"+showDataTbId+" tbody").html(showHtml);
}

/**
 * 生成一行数据的头部信息
 *
 * @param headTitleObj
 * @returns {string}
 */
function createHeadTitleFn(headTitleObj,parantTbId){
    var headTitleHtml = "<tr class=\"head_tr\">";
    headTitleHtml += "<td style='width: 22px;text-align: center'><input type=\"checkbox\" class='checkbox_input' onchange='checkSingleBoxFn(this)' parantTbId='"+parantTbId+"' jdOrderNo='"+headTitleObj.jdOrderNo+"' outStoreCommissionPrice='"+headTitleObj.outStoreCommissionPrice+"' /></td>";
    headTitleHtml += "<td colspan=\"9\" class=\"orderTitle\">";
    headTitleHtml += "<span>京东单号:"+headTitleObj.jdOrderNo+"</span>";
    headTitleHtml += "<span>买手任务号:"+headTitleObj.buyerTaskNo+"</span>";
    headTitleHtml += "<span>下单时间:"+headTitleObj.orderDtimeFormat+"</span>";
    headTitleHtml += "<span>付款时间:"+headTitleObj.orderPayTimeFormat+"</span>";
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
        dataBodyHtml += "<td colspan=\"2\" style='width: 75px;'><img title='"+detailObj.goodsName+"' src=\""+detailObj.goodsImgUrl+"\"></td>";
        dataBodyHtml += "<td class='goodsInfoTd'><div class='goodsInfo' title='"+detailObj.goodsName+"'><a href=''>"+detailObj.goodsNameShort+"</a></div><div class='goodsInfo'>规格:"+detailObj.goodsSpecDesc+"</div></td>";
        dataBodyHtml += "<td class=\"price_num_td\"><div>￥"+detailObj.goodsPrice+"元</div><div>x"+detailObj.goodsNum+"</div></td>";
        if (idx == 0) {
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\" style='text-align: center'>￥&nbsp;"+dataHeadObj.orderTotalMoney+"&nbsp;元</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\" style='text-align: center'>"+dataHeadObj.buyerJdLoginNo+"</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\">"+dataHeadObj.logisticsInfo+"</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\" style='text-align: center'>￥&nbsp;"+dataHeadObj.outStoreCommissionPrice+"&nbsp;元</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\" style='text-align: center'>"+dataHeadObj.orderStatusName+"</td>";
            dataBodyHtml += "<td rowspan=\"" + count + "\" valign=\"top\" style='text-align: center'>";
            dataBodyHtml += "<div><a href='javascript:void(0)' onclick='showOrderInfoFn(\""+dataHeadObj.jdOrderNo+"\")'>详&nbsp;&nbsp;情</a></div>";
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
    var pageHtml="<a href='javascript:void(0)' onclick='gotoPageFn(1)'>首&nbsp;页</a>&nbsp;&nbsp;";
    pageHtml+="<a href='javascript:void(0)' onclick='gotoPageFn(2)'>上一页</a>&nbsp;&nbsp;";
    pageHtml+="<a href='javascript:void(0)' onclick='gotoPageFn(3)'>下一页</a>&nbsp;&nbsp;";
    pageHtml+="<a href='javascript:void(0)' onclick='gotoPageFn(4)'>尾&nbsp;页</a>";
    pageHtml+="&nbsp;&nbsp;第&nbsp;<span id='currentPage'>"+curentPage+"</span>&nbsp;/&nbsp;<span id='totalPage'>"+totalPage+"</span>&nbsp;页";
    pageHtml+="&nbsp;&nbsp;共&nbsp;<span id='totalRecords'>"+totalRecord+"</span>&nbsp;记录";
    $("#orderPageDiv").html(pageHtml);
    $("#logisticsQueryShopOrderForm input[name=totalPage]").val(totalPage);
}

/**
 * 全选/反选复选框
 *
 * @param obj
 */
function checkAllBoxFn(obj){
    var parantTbId=$(obj).attr("parantTbId");
    var totalOutCommissionMoney=0;
    $("#"+parantTbId).find(" tbody input[type=checkbox][parantTbId="+parantTbId+"]").each(function(){
        $(this).attr("checked",$(obj).attr("checked"));
        totalOutCommissionMoney=parseFloat(totalOutCommissionMoney)+parseFloat($(this).attr("outStoreCommissionPrice"));
    });
    $("#shopOrderTabs label[name=outStoreCommissionLabel]").html(totalOutCommissionMoney);
}

/**
 * 单个checkbox选中方法
 *
 * @param obj
 */
function checkSingleBoxFn(obj){
    var totalOutCommissionMoney=0;
    var parantTbId=$(obj).attr("parantTbId");
    $("#"+parantTbId).find(" tbody input[type=checkbox][parantTbId="+parantTbId+"]:checked").each(function(){
        totalOutCommissionMoney=parseFloat(totalOutCommissionMoney)+parseFloat($(this).attr("outStoreCommissionPrice"));
    });
    $("#shopOrderTabs label[name=outStoreCommissionLabel]").html(totalOutCommissionMoney);
}

/**
 * 增加订单出库操作
 *
 */
function addOutStoreFn(obj){
    var basePath=$("#queryBasePath").val();
    var parantTbId=$(obj).attr("tableId");
    var jdOrderNo="";
    $("#"+parantTbId).find(" tbody input[type=checkbox]:checked").each(function(){
        jdOrderNo=jdOrderNo+","+($(this).attr("jdOrderNo"));
    });
    var param={
        jdOrderNo:jdOrderNo
    };
    var url =basePath+ '/logisticsOrder/logistics/addOutStoreOrder';
    $.ajax({
        type: "POST",
        url: url,
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(param),
        dataType: "json",
        success: function (data) {
            alert(data.msg);
        },
        error: function (data) {
            console.log("addOutStoreFn=>" + data);
        }
    });
}

/**
 * 翻页查询
 *
 * @param gotoPageType:1首页2上一页3下一页4尾页
 * @param page
 */
function gotoPageFn(gotoPageType){
    var currentPage=1;
    if(2 == gotoPageType){
        var currentPageTemp=$("#logisticsQueryShopOrderForm input[name=currentPage]").val();
        currentPageTemp=parseInt(currentPageTemp)-1;
        currentPage=(currentPageTemp<1)?1:currentPageTemp;
    }else if(3 == gotoPageType){
        var currentPageTemp=$("#logisticsQueryShopOrderForm input[name=currentPage]").val();
        var totalPageTemp=$("#logisticsQueryShopOrderForm input[name=totalPage]").val();
        currentPageTemp=parseInt(currentPageTemp)+1;
        currentPage=(currentPageTemp<totalPageTemp)?currentPageTemp:totalPageTemp;
    }else if(4 == gotoPageType){
        currentPage=$("#logisticsQueryShopOrderForm input[name=totalPage]").val();
    }
    $("#logisticsQueryShopOrderForm input[name=currentPage]").val(currentPage);
    var activeIdx=$( "#shopOrderTabs" ).tabs( "option", "active" );
    chooseTabFn(activeIdx);
}

/**
 * 显示订单详情信息
 *
 * @param jdOrderNo
 */
function showOrderInfoFn(jdOrderNo){

}
