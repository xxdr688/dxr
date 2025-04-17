package cn.lyk.learn.springboot.service.impl;

import cn.lyk.learn.springboot.entity.Fire;
import cn.lyk.learn.springboot.mapper.FireMapper;
import cn.lyk.learn.springboot.service.IFireService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 云海
 * @since 2024-04-08
 */
@Service
public class FireServiceImpl extends ServiceImpl<FireMapper, Fire> implements IFireService {

}
