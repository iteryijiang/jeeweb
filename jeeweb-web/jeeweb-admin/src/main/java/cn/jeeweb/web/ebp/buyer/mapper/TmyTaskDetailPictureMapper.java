package cn.jeeweb.web.ebp.buyer.mapper;

import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailPicture;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TmyTaskDetailPictureMapper extends BaseMapper<TmyTaskDetailPicture> {

    List<TmyTaskDetailPicture> selecTmyTaskDetailPictureList(Pagination page, @Param("ew") Wrapper<TmyTaskDetailPicture> wrapper);

    List<TmyTaskDetailPicture>  selectPictureList(@Param("map") Map map);

    List<TmyTaskDetailPicture>  listMytaskPic(@Param("map") Map map);

}