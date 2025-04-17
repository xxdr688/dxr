package cn.lyk.learn.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.lyk.learn.springboot.common.Constants;
import cn.lyk.learn.springboot.common.Result;
import cn.lyk.learn.springboot.entity.Fire;
import cn.lyk.learn.springboot.entity.Wd;
import cn.lyk.learn.springboot.entity.Wsd;
import cn.lyk.learn.springboot.entity.Wzsl;
import cn.lyk.learn.springboot.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private IWzslService wzslService;

    @Resource
    private IUserService userService;
    @Resource
    private IWsdService wsdService;
    @Resource
    private IWdService wdService;
    @Resource
    private IFireService fireService;

    @GetMapping("/countUsers")
    public Result countUser(){
        long count = userService.count();//用户总数
        long countMedal = wzslService.countMedal();//申领总量
        long countMoney = wzslService.countMoney();//发放总量
        long countShop = wzslService.countShop();//入库总量
        Map<String,Object> data = new HashMap<>();
        data.put("counts",count);
        data.put("countMedals",countMedal);
        data.put("countMoney",countMoney);
        data.put("countShop",countShop);
        return Result.success(data);
    }

    //获取最新温湿度
    @GetMapping("/news")
    public Result wsdNew(){
        //温度
        LambdaQueryWrapper<Wd> wdLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wdLambdaQueryWrapper.orderByDesc(Wd::getCreateTime);
        List<Wd> wds = wdService.list(wdLambdaQueryWrapper);
        //湿度
        LambdaQueryWrapper<Wsd> wsdLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wsdLambdaQueryWrapper.orderByDesc(Wsd::getCreateTime);
        List<Wsd> wsds = wsdService.list(wsdLambdaQueryWrapper);
        //火焰
        LambdaQueryWrapper<Fire> fireLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fireLambdaQueryWrapper.orderByDesc(Fire::getCreateTime);
        List<Fire> fires = fireService.list(fireLambdaQueryWrapper);
        Map<String,Object> res = new HashMap<>();
        String wdVal = "0";
        String sdVal = "0";
        String fireVal = "0";
        if(CollUtil.isNotEmpty(wds)){
            Wd wd = wds.get(0);
            wdVal = wd.getSjVal();
        }

        if(CollUtil.isNotEmpty(wsds)){
            Wsd wsd = wsds.get(0);
            sdVal = wsd.getSjVal();
        }
        if(CollUtil.isNotEmpty(fires)){
            Fire fire = fires.get(0);
            fireVal = fire.getSjVal();
        }
        res.put("wd",wdVal);
        res.put("sd",sdVal);
        res.put("fire",fireVal);
        return Result.success(res);
    }

    @GetMapping("/members")
    public Result members() {
        List<Wzsl> list = wzslService.list();
        int q1 = 0; // 第一月
        int q2 = 0; // 第二月
        int q3 = 0; // 第三月
        int q4 = 0; // 第四月
        for (Wzsl li : list) {
            LocalDateTime crTime = li.getWztime();
            LocalDate localDate = crTime.toLocalDate();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Quarter quarter = DateUtil.quarterEnum(date);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        List<Integer> userList = CollUtil.newArrayList(q1, q2, q3, q4);
        return new Result(Constants.CODE_200, "成功", userList);
    }

}
