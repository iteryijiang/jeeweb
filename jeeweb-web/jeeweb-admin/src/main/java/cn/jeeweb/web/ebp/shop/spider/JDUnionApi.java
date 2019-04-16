package cn.jeeweb.web.ebp.shop.spider;

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

    public void getCouponURL()throws JdException{
        String SERVER_URL = "https://router.jd.com/api";
        String appKey = "your appkey";
        String appSecret ="your secret";
        String accessToken = "";
        JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);
        UnionOpenPromotionBysubunionidGetRequest request = new UnionOpenPromotionBysubunionidGetRequest();
        jd.union.open.promotion.bysubunionid.get.request.PromotionCodeReq promotionCodeReq = new jd.union.open.promotion.bysubunionid.get.request.PromotionCodeReq();
        promotionCodeReq.setMaterialId("https://item.jd.com/23484023378.html");
        promotionCodeReq.setSubUnionId("1000054104");
        request.setPromotionCodeReq(promotionCodeReq);
        UnionOpenPromotionBysubunionidGetResponse response = client.execute(request);



    }



    public static void main(String agrs[]){
        JDUnionApi JDUnionApi = new JDUnionApi();
        try {
            JDUnionApi.couponImport();
        } catch (JdException e) {
            e.printStackTrace();
        }
    }
}
