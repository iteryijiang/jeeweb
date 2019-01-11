package cn.jeeweb.web.ebp.shop.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.web.ebp.shop.entity.Tsequence;
import cn.jeeweb.web.ebp.shop.mapper.TsequenceMapper;
import cn.jeeweb.web.ebp.shop.service.TsequenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("TsequenceService")
public class TsequenceServiceImpl extends CommonServiceImpl<TsequenceMapper, Tsequence> implements TsequenceService {

    public long nextNum(String bhkey) throws Exception {
        synchronized(this) {
            long var;
            try {
                Tsequence a = baseMapper.getNkeyOne(bhkey);
                if (a == null) {
                    a = new Tsequence();
                    a.setNkey(bhkey);
                    a.setNnum(1L);
                    super.insert(a);
                    return 1L;
                }

                long rn = a.getNnum() + 1L;
                a.setNnum(rn);
                super.insertOrUpdate(a);
                var = rn;
            } catch (Exception var6) {
                var6.printStackTrace();
                throw new Exception("获取流水号失败!");
            }

            return var;
        }
    }
}
