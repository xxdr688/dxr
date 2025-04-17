<template>
    <div class="wrapper">
      <div style="margin: 200px auto; background-color: rgb(169 233 220 / 65%); width: 350px; height: 300px; padding: 20px;">
        <div style="margin: 20px 0; text-align: center; font-size: 24px"><b style="color: rgb(55 60 190);">智慧租房管理系统</b></div>
        <el-form :model="user" :rules="rules" ref="userForm">
          <el-form-item prop="username">
            <el-input size="medium" placeholder="请输入账号" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input size="medium" placeholder="请输入密码" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item style="margin: 10px 0;text-align: right;" label="温馨提示：忘记密码？联系管理员！">
                <el-button type="primary" autocomplete="off" @click="login">登录</el-button>
            <!-- <el-button type="warning" autocomplete="off">注册</el-button> -->
          </el-form-item>
          
        </el-form>
      </div>
    </div>
  </template>
  
  <script>
import {setRoutes} from "@/router";

  export default {
    name: "Login",
    data() {
      return {
        user: {},
        rules: {
          username: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      login() {
        this.$refs['userForm'].validate((valid) => {
          if (valid) {  // 表单校验合法
            this.request.post("/login", this.user).then(res => {
              if(res.code === '200') {
                localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
                localStorage.setItem("menus", JSON.stringify(res.data.menus))  // 存储用户信息到浏览器
                // 动态设置当前用户的路由
                setRoutes()
                this.$router.push("/")
                this.$message.success("登录成功")
              } else {
                this.$message.error("账号或密码错误")
              }
            })
          } else {
            return false;
          }
        });
      }
    }
  }
  </script>
  
  <style>
    .wrapper {
      height: 100vh;
      background-image: url('../assets/background.jpg');
      overflow: hidden;
    }
  </style>