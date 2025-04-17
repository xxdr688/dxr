package cn.lyk.learn.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.lyk.learn.springboot.common.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IDictService;
import cn.lyk.learn.springboot.entity.Dict;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 云海
 * @since 2024-04-08
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private IDictService dictService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(dictService.list());
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Dict dict) {
        if(Objects.isNull(dict.getId())){
            LambdaQueryWrapper<Dict> dictLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dictLambdaQueryWrapper.orderByDesc(Dict::getId);
            List<Dict> dicts = dictService.list(dictLambdaQueryWrapper);
            if(CollUtil.isNotEmpty(dicts)){
                Dict exits = dicts.get(0);
                Long id = exits.getId()+1L;
                dict.setId(id);
            }
        }
        dictService.saveOrUpdate(dict);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        dictService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        dictService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Long id) {
        return Result.success(dictService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("type","icon");
        queryWrapper.orderByDesc("id");
        Page page = dictService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

}

