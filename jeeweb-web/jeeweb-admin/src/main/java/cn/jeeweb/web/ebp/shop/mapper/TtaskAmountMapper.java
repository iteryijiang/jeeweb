package cn.jeeweb.web.ebp.shop.mapper;

import cn.jeeweb.web.ebp.shop.entity.TtaskAmount;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TtaskAmountMapper extends BaseMapper<TtaskAmount> {

    List<TtaskAmount> selecTtaskAmountList(Pagination page, @Param("ew") Wrapper<TtaskAmount> wrapper);

    List<TtaskAmount>  selectPictureList(@Param("map") Map map);

}