package cn.lyk.learn.springboot.controller;

import cn.hutool.crypto.SecureUtil;
import cn.lyk.learn.springboot.common.Constants;
import cn.lyk.learn.springboot.common.Result;
import cn.lyk.learn.springboot.controller.dto.UserPasswordDTO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IUserService;
import cn.lyk.learn.springboot.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 云海
 * @since 2023-09-14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody User user) {
        if (user.getId() == null && user.getPassword() == null) {  // 新增用户默认密码
            user.setPassword( SecureUtil.md5("123"));
        }
        return Result.success(userService.saveOrUpdate(user));
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        User user = userService.getById(id);
        //如果传过来的id查出来权限字段是超级管理员，则返回error
        if ("ROLE_ADMIN".equals(user.getRole()) && "admin".equals(user.getUsername())){
            return Result.error(Constants.CODE_400,"超级管理员不能删除!");
        }
        return Result.success(userService.removeById(id));
    }

    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Result.success(userService.getOne(queryWrapper));
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        List<User> userList = userService.listByIds(ids);
        for (User user : userList) {
            //如果传过来的id查出来权限字段是超级管理员，则返回error
            if ("ROLE_ADMIN".equals(user.getRole()) && "admin".equals(user.getUsername())){
                return Result.error(Constants.CODE_400,"超级管理员不能删除!");
            }
        }
        return Result.success(userService.removeByIds(ids));
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/role/{role}")
    public Result findUsersByRole(@PathVariable String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role", role);
        List<User> list = userService.list(queryWrapper);
        return Result.success(list);
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
        return Result.success(userService.findPage(new Page<>(pageNum, pageSize), username, email, address));
    }

    /**
     * 修改密码
     * @param userPasswordDTO
     * @return
     */
    @PostMapping("/password")
    public Result password(@RequestBody UserPasswordDTO userPasswordDTO) {
        userPasswordDTO.setPassword(SecureUtil.md5(userPasswordDTO.getPassword()));
        userPasswordDTO.setNewPassword(SecureUtil.md5(userPasswordDTO.getNewPassword()));
        userService.updatePassword(userPasswordDTO);
        return Result.success();
    }

}

