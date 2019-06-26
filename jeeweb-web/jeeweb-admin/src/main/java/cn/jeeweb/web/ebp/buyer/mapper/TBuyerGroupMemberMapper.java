package cn.jeeweb.web.ebp.buyer.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.jeeweb.web.ebp.buyer.entity.TBuyerGroupMember;

/**
 * 买手分组成员
 * 问题=>一个买手组长有没有可能在多个组承担组长职位
 * 
 * @author ytj
 *
 */
@Mapper
public interface TBuyerGroupMemberMapper extends BaseMapper<TBuyerGroupMember> {
}