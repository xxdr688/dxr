package cn.lyk.learn.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.lyk.learn.springboot.common.Constants;
import cn.lyk.learn.springboot.common.Result;
import cn.lyk.learn.springboot.entity.User;
import cn.lyk.learn.springboot.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Resource
    private IUserService userService;

    @GetMapping("/example")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return new Result(Constants.CODE_200,"成功",map);
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (User user : list) {
            LocalDateTime createTime = user.getCreateTime();
            LocalDate localDate = createTime.toLocalDate();
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
        return new Result(Constants.CODE_200,"成功",userList);
    }

}