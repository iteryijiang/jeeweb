package cn.jeeweb.web.ebp.shop.mapper;

import cn.jeeweb.web.ebp.shop.entity.TtaskPictureComment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TtaskPictureCommentMapper extends BaseMapper<TtaskPictureComment> {

    List<TtaskPictureComment> selectTaskPictureCommentList(Pagination page, @Param("ew") Wrapper<TtaskPictureComment> wrapper);

    List<TtaskPictureComment>  selectPictureList(@Param("map") Map map);

    List<TtaskPictureComment>  listMytaskPic(@Param("map") Map map);

}