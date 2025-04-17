package cn.lyk.learn.springboot.service;

import cn.lyk.learn.springboot.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 云海
 * @since 2023-09-22
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
