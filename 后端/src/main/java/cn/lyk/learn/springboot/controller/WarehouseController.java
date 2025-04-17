package cn.lyk.learn.springboot.controller;

import cn.lyk.learn.springboot.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IWarehouseService;
import cn.lyk.learn.springboot.entity.Warehouse;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 云海
 * @since 2023-09-25
 */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Resource
    private IWarehouseService warehouseService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(warehouseService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Warehouse warehouse) {
        warehouseService.saveOrUpdate(warehouse);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        warehouseService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        warehouseService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(warehouseService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String type) {
        QueryWrapper<Warehouse> queryWrapper = new QueryWrapper<>();
        if (!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if (!"".equals(type)){
            queryWrapper.like("type",type);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(warehouseService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

