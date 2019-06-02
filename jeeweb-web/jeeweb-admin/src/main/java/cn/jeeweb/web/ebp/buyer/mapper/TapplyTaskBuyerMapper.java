package cn.jeeweb.web.ebp.buyer.mapper;


import cn.jeeweb.web.ebp.buyer.entity.TapplyTaskBuyer;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TapplyTaskBuyerMapper extends BaseMapper<TapplyTaskBuyer> {

    List<TapplyTaskBuyer> selectApplyPageList(Pagination page, @Param("ew") Wrapper<TapplyTaskBuyer> wrapper);

    /**
     * 主键ID查询数据
     *
     * @param applyId
     * @return
     */
    TapplyTaskBuyer selectApplyTaskById(@Param("applyId") String applyId);

    /**
     * 更改申请状态
     *
     * @param obj
     * @return
     */
    int updateTapplyTaskBuyerStatus(TapplyTaskBuyer obj);
}
