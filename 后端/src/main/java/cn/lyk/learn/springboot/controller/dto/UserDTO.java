package cn.lyk.learn.springboot.controller.dto;

import cn.lyk.learn.springboot.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 接受前端登录请求的参数
 */
@Data
public class UserDTO {

    private Integer id;//id
    private String username;//用户名
    private String password;//密码
    private String nickname;//昵称
    private String avatarUrl;//图片路径
    private String token;//token
    private String role;//角色

    private List<Menu> menus;//菜单

}
