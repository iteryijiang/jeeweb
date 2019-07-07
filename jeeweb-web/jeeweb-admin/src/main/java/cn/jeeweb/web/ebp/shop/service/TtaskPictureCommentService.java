package cn.jeeweb.web.ebp.shop.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.shop.entity.TtaskPictureComment;

import java.util.List;
import java.util.Map;

public interface TtaskPictureCommentService extends ICommonService<TtaskPictureComment> {

    public List<TtaskPictureComment> selectPictureList(Map map);
    public List<TtaskPictureComment> listMytaskPic(Map map);
}
