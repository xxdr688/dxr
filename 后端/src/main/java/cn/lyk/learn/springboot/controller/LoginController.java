package cn.lyk.learn.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.lyk.learn.springboot.common.Constants;
import cn.lyk.learn.springboot.common.Result;
import cn.lyk.learn.springboot.config.AuthAccess;
import cn.lyk.learn.springboot.controller.dto.UserDTO;
import cn.lyk.learn.springboot.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 云海
 * @since 2023-09-14
 */
@RestController
public class LoginController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    @AuthAccess
    public Result login(@RequestBody UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);
    }
}
