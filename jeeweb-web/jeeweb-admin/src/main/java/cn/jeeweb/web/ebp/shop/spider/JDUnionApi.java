package cn.jeeweb.web.ebp.shop.spider;

import cn.jeeweb.beetl.tags.dict.DictUtils;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import jd.union.open.coupon.importation.request.CouponReq;
import jd.union.open.coupon.importation.request.UnionOpenCouponImportationRequest;
import jd.union.open.coupon.importation.response.UnionOpenCouponImportationResponse;
import jd.union.open.coupon.query.request.UnionOpenCouponQueryRequest;
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
        String shortURL = response.getData().getShortURL();
        System.out.println("生成的短链接为："+shortURL);
        return shortURL;
    }

    public static void main(String agrs[]){
        JDUnionApi JDUnionApi = new JDUnionApi();
        //JDUnionApi.couponImport();
        try {
            String shorturl = JDUnionApi.getCouponURL("41011489021","http://coupon.m.jd.com/coupons/show.action?key=203d6fb476074ecab137df29da8903ab&roleId=18950122&to=mall.jd.com/index-910797.html");
            QRCodeUtil.getInstance().genQrCodeImg(null, 300, 300, "d:\\", "qrcode4.jpg", shorturl);
        }catch (Exception e){

        }

    }
}
