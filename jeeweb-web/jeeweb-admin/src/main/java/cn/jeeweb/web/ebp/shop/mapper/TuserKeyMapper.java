package cn.jeeweb.web.ebp.shop.mapper;

import cn.jeeweb.web.ebp.shop.entity.TuserKey;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TuserKeyMapper extends BaseMapper<TuserKey> {

	List<TuserKey> selectUserList(Pagination page, @Param("ew") Wrapper<TuserKey> wrapper);

	Integer sumTuserKey(@Param("map") Map map);

}