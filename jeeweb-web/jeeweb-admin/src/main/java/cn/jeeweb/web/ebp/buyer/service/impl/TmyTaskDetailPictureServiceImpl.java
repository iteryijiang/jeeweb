package cn.jeeweb.web.ebp.buyer.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.buyer.entity.TmyTaskDetailPicture;
import cn.jeeweb.web.ebp.buyer.mapper.TmyTaskDetailPictureMapper;
import cn.jeeweb.web.ebp.buyer.service.TmyTaskDetailPictureService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TmyTaskDetailPictureService")
public class TmyTaskDetailPictureServiceImpl extends CommonServiceImpl<TmyTaskDetailPictureMapper, TmyTaskDetailPicture> implements TmyTaskDetailPictureService {

    @Override
    public Page<TmyTaskDetailPicture> selectPage(Page<TmyTaskDetailPicture> page, Wrapper<TmyTaskDetailPicture> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selecTmyTaskDetailPictureList(page, wrapper));
        return page;
    }


    public List<TmyTaskDetailPicture> selectPictureList(Map map) {
        return baseMapper.selectPictureList(map);
    }

    public List<TmyTaskDetailPicture> listMytaskPic(Map map) {
        return baseMapper.selectPictureList(map);
    }
}
