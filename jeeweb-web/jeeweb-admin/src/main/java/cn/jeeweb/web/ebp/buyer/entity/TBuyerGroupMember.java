package cn.jeeweb.web.ebp.buyer.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import cn.jeeweb.web.common.entity.DataEntity;

/**
 * 买手分组成员
 * 问题=>一个买手组长有没有可能在多个组承担组长职位
 * 
 * @author ytj
 *
 */
@TableName("t_buyer_group_member")
@SuppressWarnings("serial")
public class TBuyerGroupMember extends DataEntity<Long> {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	
	/**
	 * 所属分组
	 */
	private long groupId;
	
	/**
	 * 组员买手ID
	 */
	private String buyerId;
	
	/**
	 * 类型0组员1组长
	 */
	private int memType;

	/**
	 * 加入分组时间
	 */
	private Date joinTime;
	
	/**
	 * 加入分组时买手等级
	 */
	private long buyerLevelOnJoin;
	
	/**
	 * 加入分组时买手等级名称
	 */
	private String buyerLevelNameOnJoin;
	
	/**
	 * 加入分组时操作人id
	 */
	private String joinCreateBy;
	/**
	 * 加入分组时操作人
	 */
	private String joinCreateName;
	
	/**
	 * 退出时间(退出使用删除标记表示)
	 */
	private Date quitTime;
	/**
	 * 买手退出分组时等级ID
	 */
	private long buyerLevelOnQuit;
	/**
	 * 买手退出分组时等级名称
	 */
	private String buyerLevelNameOnQuit;
	
	/**
	 * 买手退出分组时操作人ID
	 */
	private String quitCreateBy;
	
	/**
	 * 买手退出分组时操作人
	 */
	private String quitCreateName;
	
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public int getMemType() {
		return memType;
	}

	public void setMemType(int memType) {
		this.memType = memType;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public long getBuyerLevelOnJoin() {
		return buyerLevelOnJoin;
	}

	public void setBuyerLevelOnJoin(long buyerLevelOnJoin) {
		this.buyerLevelOnJoin = buyerLevelOnJoin;
	}

	public String getBuyerLevelNameOnJoin() {
		return buyerLevelNameOnJoin;
	}

	public void setBuyerLevelNameOnJoin(String buyerLevelNameOnJoin) {
		this.buyerLevelNameOnJoin = buyerLevelNameOnJoin;
	}

	public String getJoinCreateBy() {
		return joinCreateBy;
	}

	public void setJoinCreateBy(String joinCreateBy) {
		this.joinCreateBy = joinCreateBy;
	}

	public String getJoinCreateName() {
		return joinCreateName;
	}

	public void setJoinCreateName(String joinCreateName) {
		this.joinCreateName = joinCreateName;
	}

	public Date getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
	}

	public long getBuyerLevelOnQuit() {
		return buyerLevelOnQuit;
	}

	public void setBuyerLevelOnQuit(long buyerLevelOnQuit) {
		this.buyerLevelOnQuit = buyerLevelOnQuit;
	}

	public String getBuyerLevelNameOnQuit() {
		return buyerLevelNameOnQuit;
	}

	public void setBuyerLevelNameOnQuit(String buyerLevelNameOnQuit) {
		this.buyerLevelNameOnQuit = buyerLevelNameOnQuit;
	}

	public String getQuitCreateBy() {
		return quitCreateBy;
	}

	public void setQuitCreateBy(String quitCreateBy) {
		this.quitCreateBy = quitCreateBy;
	}

	public String getQuitCreateName() {
		return quitCreateName;
	}

	public void setQuitCreateName(String quitCreateName) {
		this.quitCreateName = quitCreateName;
	}
}
