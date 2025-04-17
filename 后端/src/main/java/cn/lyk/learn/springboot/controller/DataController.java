package cn.lyk.learn.springboot.controller;


import cn.hutool.core.collection.CollUtil;
import cn.lyk.learn.springboot.common.Result;
import cn.lyk.learn.springboot.config.AuthAccess;
import cn.lyk.learn.springboot.entity.*;
import cn.lyk.learn.springboot.service.*;
import cn.lyk.learn.springboot.utils.MqttClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 *
 */

@RestController
@RequestMapping("data")
public class DataController implements ApplicationListener<ContextRefreshedEvent>   {

    public static String RFID = null;
    public static String DATA = null;
    public static String I1 = null;
    public static String I3 = null;

    public static String I2 = null;
    @Resource
    private IWsdService wsdService;
    @Resource
    private IWdService wdService;
    @Resource
    private IFireService fireService;
    @Resource
    private IGzService gzService;
    @Resource
    private IDictService dictService;

    public static int DEVICE_MODE = 0; //硬件模式

    @RequestMapping("/rfid")
    @AuthAccess
    public Map<String,Object> warn() {

        String s= null;
        if(RFID !=null){
            s = RFID;
            RFID = null;
        }
        Map<String,Object> res = new HashMap<>();
        res.put("data",s);
        return res;
    }

    @RequestMapping("/getData")
    @AuthAccess
    public Map<String,Object> getData() {
        Map<String,Object> data = new HashMap<>();
        data.put("code",200);
        LambdaQueryWrapper<Wd> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Wd::getSjVal,"0")
                .ne(Wd::getDyVal,"0")
                .orderByDesc(Wd::getCreateTime);
        Page<Wd> wdPages =wdService.page(new Page<>(1, 1), queryWrapper);
        if (CollUtil.isNotEmpty(wdPages.getRecords())){
            Wd wd =wdPages.getRecords().get(0);
            data.put("dl",wd.getSjVal());
            data.put("dy",wd.getDyVal());
        }
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Wd::getKmh,"0")
                .orderByDesc(Wd::getCreateTime);
        wdPages =wdService.page(new Page<>(1, 1), queryWrapper);
        if (CollUtil.isNotEmpty(wdPages.getRecords())){
            Wd wd =wdPages.getRecords().get(0);
            data.put("kwh",wd.getKmh());
        }
        LambdaQueryWrapper<Wsd> wsdqueryWrapper = new LambdaQueryWrapper<>();
        wsdqueryWrapper.ne(Wsd::getSjVal,"0")
                        .orderByDesc(Wsd::getCreateTime);
        Page<Wsd> pages =wsdService.page(new Page<>(1, 1), wsdqueryWrapper);


        if (CollUtil.isNotEmpty(pages.getRecords())){
             Wsd wsd = pages.getRecords().get(0);
             data.put("sl",wsd.getSjVal());
        }
        return data;
    }

    public static int MODE = 0;
    @AuthAccess
    @RequestMapping("/mode/{state}")
    public Map<String,Object> mode(@PathVariable("state") Integer state) {
        MODE = state;
        Map<String,Object> res = new HashMap<>();
        res.put("code",200);
        res.put("success",true);
        return res;
    }


    public static String code = "home022599_post";

    public static String ctrl_code="home022599_set";

    public static void send(String cmd) throws InterruptedException {
        try {
            MqttClient.publish(ctrl_code, cmd);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 硬件控制
     * @param state
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/control/{state}")
    @AuthAccess
    public Map<String,Object> info(@PathVariable("state") String state) throws InterruptedException {
        Map<String,Object> res = new HashMap<>();
        //设置为自动模式
        JSONObject data =  new JSONObject();
        if ("OPEN".equals(state)){
            res.put("auto",1);
            data.put("setrelay",1);
        }
        if("CLOSE".equals(state)){
            res.put("auto",0);
            data.put("setrelay",0);
        }
        send(data.toJSONString());
        res.put("code",200);
        res.put("msg","操作成功");
        return res;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MqttClient.subscribe(code, new MqttCallback() {

                        @Override
                        public void connectionLost(Throwable arg0) {

                        }

                        @Override
                        public void messageArrived(String topic, MqttMessage message) throws Exception {
                            System.out.println("<--------------->");
                            System.out.println("硬件通讯数据接收成功  : " + message.toString());
                            System.out.println("<--------------->");
                            try {
                                boolean f = false;
                                JSONObject json = JSON.parseObject(message.toString());

                                 if(json.getInteger("volt")!=null || json.getBigDecimal("curr")!=null){
                                     Wd wd = new Wd();
                                     if(json.getBigDecimal("curr").compareTo(new BigDecimal(0))>0){
                                         wd.setDyVal(json.getBigDecimal("curr").toString());
                                     }else{
                                         wd.setDyVal("0");
                                     }
                                     if(json.getInteger("volt")!=null && json.getInteger("volt")>0){
                                         wd.setSjVal(json.getInteger("volt")+"");
                                     }else{
                                         wd.setSjVal("0");

                                     }
                                     if(json.getBigDecimal("kwh")!=null){
                                         wd.setKmh(json.getBigDecimal("kwh").toString());
                                     }else{
                                         wd.setKmh("0");
                                     }
                                     wd.setCreateTime(LocalDateTime.now());
                                     wdService.save(wd);
                                 }
                                 if(json.getBigDecimal("water")!=null){
                                     Wsd wsd = new Wsd();
                                     wsd.setSjVal(json.getBigDecimal("water").toString());
                                     wsd.setCreateTime(LocalDateTime.now());
                                     wsdService.save(wsd);
                                 }

                                System.out.println("硬件数据保存到MYSQL数据库成功!");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();

    }


    public static void error() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {  DataController.send("ERROR");
                    Thread.sleep(800);
                    send("OPEN-BUZZER");
                    Thread.sleep(500);
                    send("CLOSE-BUZZER");
                    Thread.sleep(500);
                    send("OPEN-BUZZER");
                    Thread.sleep(500);
                    send("CLOSE-BUZZER");
                    Thread.sleep(500);
                    send("OPEN-BUZZER");
                    Thread.sleep(500);
                    send("CLOSE-BUZZER");
                    Thread.sleep(500);
                    send("CLOSE-BUZZER");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
        thread.start();
    }


}

