package cn.jeeweb.web.ebp.shop.spider;

/**
 * Created by YIJIANG on 2018/12/22.getGoodInfos
 * QQ号：1132017151
 * 京东 爬虫
 */
import cn.jeeweb.common.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JdSpider {

    public static void main(String[] args)  {
        String url = "https://item.jd.com/6837346.html";
        try {
            String goodis = getGoodId_ByURL(url);
//            String goodsrc = getGoodImgByurl(url);
//            System.out.println("商品src: " + goodsrc);
//            System.out.println("商品信息: " + goodis);
            if(!StringUtils.isEmpty(goodis)){
                System.out.println("获取商品店铺："+getGoodStorenameByurl(getDocumentUrl(url)));
//                getGoodList(url);
//                System.out.println("获取商品标题："+getGoodTitleByurl(url));//获取商品标题);
//                System.out.println("获取商品品牌："+getGoodBrandByurl(url));//获取商品标题);
//                String result = getGoodInfos(goodis);
//                System.out.println("商品信息: " + result);
//                String good_price  = getGoodPrice_ByResult(result);
//                System.out.println("商品价格: " + good_price);
            }
//            System.out.println("商品型号："+getGoodSpec1ByTitle(""));
//            System.out.println("商品规格："+getGoodSpec2ByTitle(""));


//            BigDecimal tprice = new BigDecimal(0.0);
//            BigDecimal a = new BigDecimal(10.1);
//            tprice = tprice.add(a);
//            System.out.println(tprice.setScale(2, BigDecimal.ROUND_HALF_UP));
//            System.out.println(a);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     *
     * @Title: getGoodId_ByURL
     * @Description: 根据商品URL  截取 goodID
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static String getGoodId_ByURL(String url) throws Exception{
        String goodId = "";
        if(url!=null && url.length()>0){
            int s = url.lastIndexOf("/");
            int e = url.lastIndexOf(".");
            if(s>0 && e>0){
                goodId = url.substring(s+1,e);
            }
        }
        return goodId;
    }

    /**
     *
     * @Title: getGoodPrice_ByResult
     * @Description: 根据爬虫结果  截取 商品价格
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static String getGoodPrice_ByResult(String result) throws Exception{
        String good_price = "";
        if(StringUtils.isNotEmpty(result)){
            int s = result.lastIndexOf("\"op\":\"");
            int e = result.lastIndexOf("\",\"m\"");
            if(s>0 && e>0){
                good_price = result.substring(s+6,e);
            }
        }
        return good_price;
    }

    /**
     *
     * @Title: getGoodList
     * @Description: 获取商品列表
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static void getGoodList(String url) throws Exception{
        //String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&psort=3&page=3";//第二页商品
        //String url = "https://search.jd.com/Search?keyword=%E8%89%BE%E5%8F%AF%E8%89%BE%E5%8F%AF&enc=utf-8&wq=%E8%89%BE%E5%8F%AF%E8%89%BE%E5%8F%AF&pvid=331cd5bb16484f9ab8a3b5f12c1ba905";
        //网址分析
        /*keyword:关键词（京东搜索框输入的信息）
         * enc：编码方式（可改动:默认UTF-8）
         * psort=3 //搜索方式  默认按综合查询 不给psort值
         * page=分业（不考虑动态加载时按照基数分业，每一页30条，这里就不演示动态加载）
         * 注意：受京东商品个性化影响，准确率无法保障
         * */
        Document doc = Jsoup.connect(url).maxBodySize(0).get();
        //doc获取整个页面的所有数据
        Elements ulList = doc.select("ul[class='gl-warp clearfix']");
        Elements liList = ulList.select("li[class='gl-item']");
        //循环liList的数据
        for (Element item : liList) {
            //排除广告位置
            if (!item.select("span[class='p-promo-flag']").text().trim().equals("广告")) {
                //如果向存到数据库和文件里请自行更改
                System.out.println(item.select("div[class='p-name p-name-type-2']").select("em").text());//打印商品标题到控制台
            }
        }
    }
    /**
     *
     * @Title: getGoodTitle_one
     * @Description: 获取 单 商品标题
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static String getGoodTitleByurl(Document document) throws Exception{
        //根据url  获取html内容
//        Document document = Jsoup.connect(url).maxBodySize(0).get();
        //select 获取标签
        String title = document.select("div[class='sku-name']").get(0).text();
        if(title!=null && title.length()>0){
            title = title.replaceAll("【图片 价格 品牌 报价】-京东","");
        }
        return title;
    }

    /**
     *
     * @Title: getGoodTitle_one
     * @Description: 获取 单 商品品牌
     * @param @param url
     * @return String 返回类型
     * @author  iter
     * @throws
     */
    public  static String getGoodBrandByurl(Document document) throws Exception{
        //根据url  获取html内容
//        Document document = Jsoup.connect(url).maxBodySize(0).get();
        //select 获取标签
        String title = document.select("#parameter-brand").get(0).text();
        if(title!=null && title.length()>0){
            title = title.replaceAll("品牌： ","");
        }
        return title;
    }
    /**
     *
     * @Title: getGoodTitle_one
     * @Description: 获取 单 商品品牌
     * @param @param url
     * @return String 返回类型
     * @author  iter
     * @throws
     */
    public  static String getGoodStorenameByurl(Document document) throws Exception{
        //根据url  获取html内容
//        Document document = Jsoup.connect(url).maxBodySize(0).get();
        //select 获取标签
        String title = document.select("div[class='name']").get(0).text();
        if(title!=null && title.length()>0){
            title = title.replaceAll("品牌： ","");
        }
        return title;
    }

    /**
     *
     * @Title: getGoodTitle_one
     * @Description: 获取 单 商品颜色规格
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static String getGoodSpec1ByTitle(String title) throws Exception{
        String spec1 = "";
        if(StringUtils.isNotEmpty(title)){
            spec1 = title.substring(0,title.lastIndexOf(" "));
            spec1 = spec1.substring(spec1.lastIndexOf(" ")+1);
        }
        return spec1;
    }

    /**
     *
     * @Title: getGoodTitle_one
     * @Description: 获取 单 商品型号规格
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static String getGoodSpec2ByTitle(String title) throws Exception{
        String spec2 = "";
        if(StringUtils.isNotEmpty(title)){
            spec2 = title.substring(title.lastIndexOf(" ")+1);
        }
        return spec2;
    }
    /**
     *
     * @Title: getDocumentUrl
     * @Description: 获取html内容对象
     * @param @param url
     * @return Document 返回类型
     * @author  iter
     * @throws
     */
    public  static Document getDocumentUrl(String url) throws Exception{
        //根据url  获取html内容
        Document document = Jsoup.connect(url).maxBodySize(0).get();
        return document;
    }
    /**
     *
     * @Title: getGoodTitle_one
     * @Description: 获取  商品图片
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static String getGoodImgByurl(Document document) throws Exception{
        //根据url  获取html内容
//        Document document = Jsoup.connect(url).maxBodySize(0).get();
        //select 获取标签
        String src = document.select("img[id='spec-img']").get(0).attr("data-origin");
        if(src!=null && src.length()>0){
            src = "https://"+src;
        }
        return src;
    }



    /**
     *
     * @Title: getGoodInfos
     * @Description: 获取 商品详细信息
     * @param @param url
     * @return void 返回类型
     * @author  iter
     * @date 2018-12-22 下午2:11:23
     * @throws
     */
    public  static String getGoodInfos(String skuId) throws Exception{
        //根据url  获取html内容  itemInfo-wrap 商品信息div
        //String skuId = "36326977521";  //36716998968
        String getPriceUrl = "https://c0.3.cn/stock?skuId="+skuId+"&cat=1315,1343,9719&venderId=600490&area=1_72_2799_0&buyNum=1&choseSuitSkuIds=&ch=1&fqsp=0&pduid=15323266581711238648276&pdpin=iteryijiang&callback=jQuery6319042";
        Map map = new HashMap<>();
        String result = "";
        HttpClient httpClient = new SSLClient();
        HttpPost httpPost  = new HttpPost(getPriceUrl);
        //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(new ArrayList<>(), "utf-8");
        //httpPost.setEntity(entity);
        httpPost.addHeader("Content-type","application/json;charset=utf-8");
        httpPost.addHeader("Accept","application/json");
        httpPost.setEntity(new StringEntity(new JSONObject().toString(), Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(httpPost);
        //URLDecoder.decode() response.getEntity().getContentEncoding().getValue().
        if(response != null){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                result = EntityUtils.toString(resEntity, "utf-8");
            }
        }
        return result;
    }




}