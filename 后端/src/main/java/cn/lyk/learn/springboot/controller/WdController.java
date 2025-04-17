package cn.lyk.learn.springboot.controller;

import cn.lyk.learn.springboot.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IWdService;
import cn.lyk.learn.springboot.entity.Wd;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 云海
 * @since 2024-04-03
 */
@RestController
@RequestMapping("/wd")
public class WdController {

    @Resource
    private IWdService wdService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(wdService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Wd wd) {
        wdService.saveOrUpdate(wd);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        wdService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        wdService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(wdService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Wd> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(wdService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

