package cn.lyk.learn.springboot.controller;

import cn.lyk.learn.springboot.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IRoleMenuService;
import cn.lyk.learn.springboot.entity.RoleMenu;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色菜单关系表 前端控制器
 * </p>
 *
 * @author 云海
 * @since 2023-09-22
 */
@RestController
@RequestMapping("/role-menu")
public class RoleMenuController {

    @Resource
    private IRoleMenuService roleMenuService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(roleMenuService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody RoleMenu roleMenu) {
        roleMenuService.saveOrUpdate(roleMenu);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        roleMenuService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        roleMenuService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(roleMenuService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(roleMenuService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

