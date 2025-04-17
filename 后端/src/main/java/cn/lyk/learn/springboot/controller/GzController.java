package cn.lyk.learn.springboot.controller;

import cn.lyk.learn.springboot.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IGzService;
import cn.lyk.learn.springboot.entity.Gz;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @since 2024-04-08
 */
@RestController
@RequestMapping("/gz")
public class GzController {

    @Resource
    private IGzService gzService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(gzService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Gz gz) {
        gzService.saveOrUpdate(gz);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        gzService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        gzService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(gzService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Gz> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(gzService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

