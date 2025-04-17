package cn.lyk.learn.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.lyk.learn.springboot.common.Result;
import cn.lyk.learn.springboot.entity.MaterialList;
import cn.lyk.learn.springboot.service.IMaterialListService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.lyk.learn.springboot.service.IWzslService;
import cn.lyk.learn.springboot.entity.Wzsl;

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
@RequestMapping("/wzsl")
public class WzslController {

    @Resource
    private IMaterialListService materialListService;

    @Resource
    private IWzslService wzslService;

    // 查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(wzslService.list());
    }

    //根据ID进行发放更新物资
    @PostMapping("/{id}")
    public Result updateById(@PathVariable Integer id){
        Wzsl wzsl = new Wzsl();
        wzsl.setId(id);
        wzsl.setFlag("1");
        wzslService.updateById(wzsl);
        return Result.success();
    }

    //根据ID进行入库更新物资
    @PutMapping("/{id}")
    public Result updateRkById(@PathVariable Integer id){
        Wzsl list = wzslService.getById(id);
        QueryWrapper<MaterialList> qw = new QueryWrapper<>();
        qw.eq("material_name",list.getWzname());
        MaterialList maList = materialListService.getOne(qw);

        Integer.valueOf(maList.getMaterialKc());
        int countSq = Integer.valueOf(maList.getMaterialSq()) - Integer.valueOf(list.getWznumber());
        int countKc = Integer.valueOf(maList.getMaterialKc()) + Integer.valueOf(list.getWznumber());
        maList.setMaterialKc(String.valueOf(countKc));//库存数量
        maList.setMaterialSq(String.valueOf(countSq));//已申领数量
        materialListService.updateById(maList);

        list.setFlagnumber(1);
        wzslService.updateById(list);
        return Result.success();
    }
    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Wzsl wzsl) {
        QueryWrapper<MaterialList> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("material_name",wzsl.getWzname());
        MaterialList materialList = materialListService.getOne(queryWrapper);

        Integer.valueOf(materialList.getMaterialKc());
        int countSq = Integer.valueOf(materialList.getMaterialSq()) + Integer.valueOf(wzsl.getWznumber());
        int countKc = Integer.valueOf(materialList.getMaterialKc()) - Integer.valueOf(wzsl.getWznumber());
        materialList.setMaterialKc(String.valueOf(countKc));//库存数量
        materialList.setMaterialSq(String.valueOf(countSq));//已申领数量
        materialListService.updateById(materialList);
        //设置物资申领表归还状态为0
        wzsl.setFlag("0");
        //设置物资申领表入库状态为0
        wzsl.setFlagnumber(0);
        wzsl.setRfid(materialList.getMaterialBh());
        wzslService.saveOrUpdate(wzsl);
        return Result.success();
    }

    //根据id进行删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        wzslService.removeById(id);
        return Result.success();
    }

    //根据list ids批量删除方法
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        wzslService.removeByIds(ids);
        return Result.success();
    }

    //根据id查询一条数据
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(wzslService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                           @RequestParam(defaultValue = "") String wzname,
                           @RequestParam(defaultValue = "") String rfid
                           ) {
        QueryWrapper<Wzsl> queryWrapper = new QueryWrapper<>();
        if ("".equals(name)){
            queryWrapper.like("name",name);
        }
        if ("".equals(wzname)){
            queryWrapper.like("wzname",wzname);
        }
        if (!"".equals(rfid) || StrUtil.isNotBlank(rfid)) {
            queryWrapper.eq("rfid",rfid);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(wzslService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

