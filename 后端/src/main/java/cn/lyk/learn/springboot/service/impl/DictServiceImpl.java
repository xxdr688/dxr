package cn.lyk.learn.springboot.service.impl;

import cn.lyk.learn.springboot.entity.Dict;
import cn.lyk.learn.springboot.mapper.DictMapper;
import cn.lyk.learn.springboot.service.IDictService;
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
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
