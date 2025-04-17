package cn.lyk.learn.springboot.mapper;

import cn.lyk.learn.springboot.entity.Wzsl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 云海
 * @since 2023-09-25
 */
public interface WzslMapper extends BaseMapper<Wzsl> {

    long countMedal();

    long countMoney();

    long countShop();
}
