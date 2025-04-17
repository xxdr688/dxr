package cn.lyk.learn.springboot.mapper;

import cn.lyk.learn.springboot.controller.dto.UserPasswordDTO;
import cn.lyk.learn.springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 云海
 * @since 2023-09-14
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> page, @Param("username") String username, @Param("email") String email, @Param("address") String address);

    int updatePassword(UserPasswordDTO userPasswordDTO);
}
