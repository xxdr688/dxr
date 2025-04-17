package cn.lyk.learn.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.lyk.learn.springboot.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IMaterialListService;
import cn.lyk.learn.springboot.entity.MaterialList;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物资表 前端控制器
 * </p>
 *
 * @author 云海
 * @since 2023-09-24
 */
@RestController
@RequestMapping("/material")
public class MaterialListController {

    @Resource
    private IMaterialListService materialListService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(materialListService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody MaterialList materialList) {
        materialListService.saveOrUpdate(materialList);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        materialListService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        materialListService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(materialListService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String materialBh,
                           @RequestParam(defaultValue = "") String materialName) {
        QueryWrapper<MaterialList> queryWrapper = new QueryWrapper<>();
        if (!"".equals(materialBh)) {
            queryWrapper.like("material_bh", materialBh);
        }
        if (!"".equals(materialName)) {
            queryWrapper.like("material_name", materialName);
        }
        return Result.success(materialListService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

