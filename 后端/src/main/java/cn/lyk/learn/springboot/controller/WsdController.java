package cn.lyk.learn.springboot.controller;

import cn.lyk.learn.springboot.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IWsdService;
import cn.lyk.learn.springboot.entity.Wsd;

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
@RequestMapping("/wsd")
public class WsdController {

    @Resource
    private IWsdService wsdService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(wsdService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Wsd wsd) {
        wsdService.saveOrUpdate(wsd);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        wsdService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        wsdService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(wsdService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Wsd> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(wsdService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

