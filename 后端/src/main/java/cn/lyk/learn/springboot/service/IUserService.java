package cn.lyk.learn.springboot.service;

import cn.lyk.learn.springboot.common.Constants;
import cn.lyk.learn.springboot.controller.dto.UserDTO;
import cn.lyk.learn.springboot.controller.dto.UserPasswordDTO;
import cn.lyk.learn.springboot.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 云海
 * @since 2023-09-14
 */
public interface IUserService extends IService<User> {

   UserDTO login(UserDTO userDTO);

   Page<User> findPage(Page<User> objectPage, String username, String email, String address);

    void updatePassword(UserPasswordDTO userPasswordDTO);

}
