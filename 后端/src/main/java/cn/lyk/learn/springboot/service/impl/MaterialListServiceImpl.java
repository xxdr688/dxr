package cn.lyk.learn.springboot.service.impl;

import cn.lyk.learn.springboot.entity.MaterialList;
import cn.lyk.learn.springboot.mapper.MaterialListMapper;
import cn.lyk.learn.springboot.service.IMaterialListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 物资表 服务实现类
 * </p>
 *
 * @author 云海
 * @since 2023-09-24
 */
@Service
public class MaterialListServiceImpl extends ServiceImpl<MaterialListMapper, MaterialList> implements IMaterialListService {

}
