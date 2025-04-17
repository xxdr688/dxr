package cn.lyk.learn.springboot.service.impl;

import cn.lyk.learn.springboot.entity.RoleMenu;
import cn.lyk.learn.springboot.mapper.RoleMenuMapper;
import cn.lyk.learn.springboot.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author 云海
 * @since 2023-09-22
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
