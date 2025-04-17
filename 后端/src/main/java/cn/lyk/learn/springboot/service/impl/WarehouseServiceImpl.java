package cn.lyk.learn.springboot.service.impl;

import cn.lyk.learn.springboot.entity.Warehouse;
import cn.lyk.learn.springboot.mapper.WarehouseMapper;
import cn.lyk.learn.springboot.service.IWarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 云海
 * @since 2023-09-25
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {

}
