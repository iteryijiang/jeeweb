package cn.jeeweb.web.ebp.shop.spider;

/**
 * Created by YIJIANG on 2018/12/22.
 * QQ号：1132017151
 * 京东 爬虫
 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class JdSpider {

    public static void main(String[] args)  {
        String url = "https://item.jd.com/37560650497.html";
        try {
           String title = getGoodInfos("36716998968");
           System.out.println("商品信息: " + title);
        }catch (Exception e){
            e.printStackTrace();
        }
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
    public  static String getGoodTitleByurl(String url) throws Exception{
        //根据url  获取html内容
        Document document = Jsoup.connect(url).maxBodySize(0).get();
        //select 获取标签
        String title = document.select("div[class='sku-name']").get(0).text();
        if(title!=null && title.length()>0){
            title = title.replaceAll("【图片 价格 品牌 报价】-京东","");
        }
        return title;
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
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(new ArrayList<>(), "utf-8");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
        if(response != null){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                result = EntityUtils.toString(resEntity, "utf-8");
            }
        }
        System.out.println(result);
        return result;
    }




}