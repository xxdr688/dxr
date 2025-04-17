package cn.lyk.learn.springboot.mapper;

import cn.lyk.learn.springboot.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 云海
 * @since 2023-09-22
 */
public interface RoleMapper extends BaseMapper<Role> {

    Integer selectRoleMenus(@Param("flag") String flag);
}
