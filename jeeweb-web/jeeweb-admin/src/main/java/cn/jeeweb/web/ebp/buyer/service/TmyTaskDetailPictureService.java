package cn.jeeweb.web.ebp.buyer.service;

import cn.jeeweb.common.mybatis.mvc.service.ICommonService;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailPicture;

import java.util.List;
import java.util.Map;

public interface TmyTaskDetailPictureService extends ICommonService<TmyTaskDetailPicture> {

    public List<TmyTaskDetailPicture> selectPictureList(Map map);
    public List<TmyTaskDetailPicture> listMytaskPic(Map map);
}
