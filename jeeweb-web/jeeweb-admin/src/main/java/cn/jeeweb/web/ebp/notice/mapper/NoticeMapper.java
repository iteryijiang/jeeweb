package cn.jeeweb.web.ebp.notice.mapper;

import cn.jeeweb.web.ebp.notice.entity.NoticeInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0.1
 * @Author ytj
 * @Date 2019/5/14
 * @Description
 */
@Mapper
public interface NoticeMapper extends BaseMapper<NoticeInfo> {

  /***
   * 分页查询消息通知
   *
   * @param page
   * @param wrapper
   * @return
   */
  List<NoticeInfo> selectNoticePageList(Pagination page, @Param("ew") Wrapper<NoticeInfo> wrapper);

  /***
   * 更改任务状态
   *
   * @param map
   * @return
   */
  int updateNoticeStatus(@SuppressWarnings("rawtypes") @Param("map") Map map);
}
