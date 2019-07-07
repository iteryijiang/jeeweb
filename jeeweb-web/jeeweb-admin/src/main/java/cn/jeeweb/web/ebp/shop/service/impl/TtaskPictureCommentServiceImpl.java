package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.TtaskPictureComment;
import cn.jeeweb.web.ebp.shop.mapper.TtaskPictureCommentMapper;
import cn.jeeweb.web.ebp.shop.service.TtaskPictureCommentService;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("TtaskPictureCommentService")
public class TtaskPictureCommentServiceImpl extends CommonServiceImpl<TtaskPictureCommentMapper, TtaskPictureComment> implements TtaskPictureCommentService {

    @Override
    public Page<TtaskPictureComment> selectPage(Page<TtaskPictureComment> page, Wrapper<TtaskPictureComment> wrapper) {
        wrapper.eq("1", "1");
        page.setRecords(baseMapper.selectTaskPictureCommentList(page, wrapper));
        return page;
    }


    public List<TtaskPictureComment> selectPictureList(Map map) {
        return baseMapper.selectPictureList(map);
    }

    public List<TtaskPictureComment> listMytaskPic(Map map) {
        return baseMapper.listMytaskPic(map);
    }
}
