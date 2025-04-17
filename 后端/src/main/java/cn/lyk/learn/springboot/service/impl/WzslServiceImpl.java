package cn.lyk.learn.springboot.service.impl;

import cn.lyk.learn.springboot.entity.Wzsl;
import cn.lyk.learn.springboot.mapper.WzslMapper;
import cn.lyk.learn.springboot.service.IWzslService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 云海
 * @since 2023-09-25
 */
@Service
public class WzslServiceImpl extends ServiceImpl<WzslMapper, Wzsl> implements IWzslService {

    @Resource
    public WzslMapper wzslMapper;

    @Override
    public long countMedal() {
        return wzslMapper.countMedal();
    }

    @Override
    public long countMoney() {
        return wzslMapper.countMoney();
    }

    @Override
    public long countShop() {
        return wzslMapper.countShop();
    }
}
