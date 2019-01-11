package cn.jeeweb.web.ebp.shop.mapper;

import cn.jeeweb.web.ebp.shop.entity.Tsequence;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TsequenceMapper extends BaseMapper<Tsequence> {

    Tsequence getNkeyOne(@Param("nkey") String nkey);
}