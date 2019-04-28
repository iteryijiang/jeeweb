package cn.jeeweb.web.ebp.shop.spider;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.common.utils.FastJsonUtils;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import jd.union.open.coupon.importation.request.CouponReq;
import jd.union.open.coupon.importation.request.UnionOpenCouponImportationRequest;
import jd.union.open.coupon.importation.response.UnionOpenCouponImportationResponse;
import jd.union.open.coupon.query.request.UnionOpenCouponQueryRequest;
import jd.union.open.coupon.query.response.CouponResp;
import jd.union.open.coupon.query.response.UnionOpenCouponQueryResponse;
import jd.union.open.promotion.bysubunionid.get.request.UnionOpenPromotionBysubunionidGetRequest;
import jd.union.open.promotion.bysubunionid.get.response.UnionOpenPromotionBysubunionidGetResponse;

/**
 * Created by YIJIANG on 2019/4/16.
 */
public class JDUnionApi {


    public void couponImport() throws JdException {

        String SERVER_URL = "https://router.jd.com/api";
        String appKey = "your appkey";
        String appSecret ="your secret";
        String accessToken = "";
        JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);
        UnionOpenCouponImportationRequest request = new UnionOpenCouponImportationRequest();
        CouponReq couponReq = new CouponReq();
        couponReq.setSkuId(43563168740L);
        couponReq.setCouponLink("http://coupon.m.jd.com/coupons/show.action?key=2572412b7bfb4390896774156bee3c5e&roleId=18983871&to=mall.jd.com/index-908143.html");
        request.setCouponReq(couponReq);
        UnionOpenCouponImportationResponse response = client.execute(request);
        System.out.println(response.getCode());

    }
    public void couponQuery()throws JdException{
        String SERVER_URL = "https://router.jd.com/api";
        String appKey = "your appkey";
        String appSecret ="your secret";
        String accessToken = "";
        JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);
        UnionOpenCouponQueryRequest request = new UnionOpenCouponQueryRequest();
        request.setCouponUrls(new String[]{"http://coupon.jd.com/ilink/couponActiveFront/front_index.action?key=61c19ff15ceb450c8d801c50f729069d&roleId=7290729&to=jyhbj.jd.hk"});
        UnionOpenCouponQueryResponse response = client.execute(request);
        response.getData().toString();
    }

    /*
        获取推广链接API>通过subUnionId获取推广链接【申请】
        jd.union.open.promotion.bysubunionid.get 通过subUnionId获取推广链接【申请】
        版本1.0基础通过商品链接、领券链接、活动链接获取普通推广链接或优惠券二合一推广链接，
        支持传入subunionid参数，可用于区分媒体自身的用户ID。需向cps-qxsq@jd.com申请权限。
        功能同宙斯接口的优惠券,商品二合一转接API-通过subUnionId获取推广链接、联盟微信手q通过subUnionId获取推广链接。
        https://union.jd.com/openplatform/api/634
     */
    public String getCouponURL(String skuid,String couponurl)throws JdException{
        System.out.println("========1=======");
        //联盟ID   1001489696
        String SERVER_URL = "https://router.jd.com/api";
        String appKey = "5e13fa6becc380b136749e57e2d52934";
        String appSecret ="ffaf8f1b12384d9c89b964f08ff29b36";
        String accessToken = "";
        JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);
        UnionOpenPromotionBysubunionidGetRequest request = new UnionOpenPromotionBysubunionidGetRequest();
        jd.union.open.promotion.bysubunionid.get.request.PromotionCodeReq promotionCodeReq = new jd.union.open.promotion.bysubunionid.get.request.PromotionCodeReq();
        promotionCodeReq.setMaterialId("https://item.jd.com/"+skuid+".html");
        promotionCodeReq.setSubUnionId("1001489696");
        //http://coupon.m.jd.com/coupons/show.action?key=2572412b7bfb4390896774156bee3c5e&roleId=18983871&to=mall.jd.com/index-908143.html
        System.out.println("========2=======");
        promotionCodeReq.setCouponUrl(couponurl);
        request.setPromotionCodeReq(promotionCodeReq);
        UnionOpenPromotionBysubunionidGetResponse response = client.execute(request);
        int code = response.getCode();
        String message = "";
        if(code==200){
            // 200为 生成成功
        }else{
            // 失败,返回失败消息
            message = response.getMessage();
        }
        String shortURL = response.getData().getShortURL();
        System.out.println("生成的短链接为："+shortURL);
        return shortURL;
    }

    /*
        https://union.jd.com/openplatform/api/627
        通过领券链接查询优惠券的平台、面额、期限、可用状态、剩余数量等详细信息，通常用于和商品信息一起展示优惠券券信息。需向cps-qxsq@jd.com申请权限。


        通过接口 获取优惠券 最新可用状态
        可用数量低于1000个限制发布
        有效期低于生效时间后72小时的  限制发布
     */
    public String getCouponInfoByJDAPI(String couponurl)throws JdException{
        String SERVER_URL = "https://router.jd.com/api";
        String appKey = "5e13fa6becc380b136749e57e2d52934";
        String appSecret ="ffaf8f1b12384d9c89b964f08ff29b36";
        String accessToken = "";

        JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);

        UnionOpenCouponQueryRequest request = new UnionOpenCouponQueryRequest();
        request.setCouponUrls(new String[]{couponurl});
        UnionOpenCouponQueryResponse response = client.execute(request);
        int code = response.getCode();
        String message = "";
        System.out.println("data length::::"+response.getData().length);
        CouponResp[] datas = response.getData();
        CouponResp data = datas[0];
        String yxzt =  data.getYn(); //有效状态
        long remainNum = data.getRemainNum(); //优惠奍剩余张数
        long takeBeginTime = data.getTakeBeginTime(); //开始有效时间
        long takeEndTime = data.getTakeEndTime(); //到期 有效时间
        double discount = data.getDiscount(); // 奍面额
        System.out.println("remainNum:"+remainNum);
        System.out.println("takeBeginTime:"+takeBeginTime);
        System.out.println("takeEndTime:"+takeEndTime);
        System.out.println("discount:"+discount);
        if(code==200){
            // 200为 生成成功
        }else{
            // 失败,返回失败消息
            message = response.getMessage();
        }
        System.out.println(response.getData().toString());
        return response.getData().toString();
    }



    public static void main(String agrs[]){
        JDUnionApi JDUnionApi = new JDUnionApi();
        //JDUnionApi.couponImport();
        try {
            JDUnionApi.getCouponInfoByJDAPI("http://coupon.m.jd.com/coupons/show.action?key=203d6fb476074ecab137df29da8903ab&roleId=18950122&to=mall.jd.com/index-910797.html");
            //41011489021
            //String shorturl = JDUnionApi.getCouponURL("41011489021","http://coupon.m.jd.com/coupons/show.action?key=203d6fb476074ecab137df29da8903ab&roleId=18950122&to=mall.jd.com/index-910797.html");
            // shorturl 不为空  才去生成二维码
            //QRCodeUtil.getInstance().genQrCodeImg(null, 300, 300, "d:\\", "qrcode4.jpg", shorturl);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
