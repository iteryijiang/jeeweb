package cn.jeeweb.web.ebp.shop.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

@TableName("t_task_base")
@SuppressWarnings("serial")
public class TtaskBase extends DataEntity<String> {

	/** id */
	@TableId(value = "id", type = IdType.UUID)
	private String id;
	private String tType;//	varchar	32	0	-1	0	0	0	0		0	任务类型	utf8	utf8_general_ci		0	0
	private String shopid;//	varchar	32	0	-1	0	0	0	0		0	商家id	utf8	utf8_general_ci		0	0
	private String tUrl;//	varchar	300	0	-1	0	0	0	0		0	商品链接	utf8	utf8_general_ci		0	0
	private String tTitle;//	varchar	300	0	-1	0	0	0	0		0	商品标题	utf8	utf8_general_ci		0	0
	private Double tPrice;//	double	10	2	-1	0	0	0	0		0	单品价格				0	0
	private Long tNum;//	int	8	0	-1	0	0	0	0		0	每单几件				0	0
	private Double totalPrice;//	double	10	2	-1	0	0	0	0		0	下单总金额				0	0
	private Double searchPrice;//	double	10	2	-1	0	0	0	0		0	搜索页面展示价格				0	0
	private String isPcView;//	varchar	32	0	-1	0	0	0	0		0	是否 淘宝搜索框	utf8	utf8_general_ci		0	0
	private String keyword;//	varchar	200	0	-1	0	0	0	0		0	搜索关键字	utf8	utf8_general_ci		0	0
	private String spec1;//	varchar	50	0	-1	0	0	0	0		0	规格1	utf8	utf8_general_ci		0	0
	private String spec2;//	varchar	50	0	-1	0	0	0	0		0	规格2	utf8	utf8_general_ci		0	0
	private String classify1;//	varchar	50	0	-1	0	0	0	0		0	分类1	utf8	utf8_general_ci		0	0
	private String classify2;//	varchar	50	0	-1	0	0	0	0		0	分类2	utf8	utf8_general_ci		0	0
	private String classify3;//	varchar	50	0	-1	0	0	0	0		0	分类3	utf8	utf8_general_ci		0	0
	private String classify4;//	varchar	50	0	-1	0	0	0	0		0	分类4	utf8	utf8_general_ci		0	0
	private String isMobileView;//	varchar	32	0	-1	0	0	0	0		0	是否 手机淘宝	utf8	utf8_general_ci		0	0
	private String expressWay;//	varchar	32	0	-1	0	0	0	0		0	运费收取方式	utf8	utf8_general_ci		0	0
	private Long taskNum;//	int	8	0	-1	0	0	0	0		0	任务单数				0	0
	private Date createDate;//	datetime	0	0	-1	0	0	0	0		0	创建时间				0	0
	private String createUser;//	varchar	32	0	-1	0	0	0	0		0	创建人	utf8	utf8_general_ci		0	0


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String gettType() {
		return tType;
	}

	public void settType(String tType) {
		this.tType = tType;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String gettUrl() {
		return tUrl;
	}

	public void settUrl(String tUrl) {
		this.tUrl = tUrl;
	}

	public String gettTitle() {
		return tTitle;
	}

	public void settTitle(String tTitle) {
		this.tTitle = tTitle;
	}

	public Double gettPrice() {
		return tPrice;
	}

	public void settPrice(Double tPrice) {
		this.tPrice = tPrice;
	}

	public Long gettNum() {
		return tNum;
	}

	public void settNum(Long tNum) {
		this.tNum = tNum;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getSearchPrice() {
		return searchPrice;
	}

	public void setSearchPrice(Double searchPrice) {
		this.searchPrice = searchPrice;
	}

	public String getIsPcView() {
		return isPcView;
	}

	public void setIsPcView(String isPcView) {
		this.isPcView = isPcView;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSpec1() {
		return spec1;
	}

	public void setSpec1(String spec1) {
		this.spec1 = spec1;
	}

	public String getSpec2() {
		return spec2;
	}

	public void setSpec2(String spec2) {
		this.spec2 = spec2;
	}

	public String getClassify1() {
		return classify1;
	}

	public void setClassify1(String classify1) {
		this.classify1 = classify1;
	}

	public String getClassify2() {
		return classify2;
	}

	public void setClassify2(String classify2) {
		this.classify2 = classify2;
	}

	public String getClassify3() {
		return classify3;
	}

	public void setClassify3(String classify3) {
		this.classify3 = classify3;
	}

	public String getClassify4() {
		return classify4;
	}

	public void setClassify4(String classify4) {
		this.classify4 = classify4;
	}

	public String getIsMobileView() {
		return isMobileView;
	}

	public void setIsMobileView(String isMobileView) {
		this.isMobileView = isMobileView;
	}

	public String getExpressWay() {
		return expressWay;
	}

	public void setExpressWay(String expressWay) {
		this.expressWay = expressWay;
	}

	public Long getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Long taskNum) {
		this.taskNum = taskNum;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}
