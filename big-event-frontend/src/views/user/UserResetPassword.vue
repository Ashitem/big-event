<script setup>
//定义数据模型
import {ref} from "vue";
import {userUpdatePwdService} from "@/api/user.js";
import {ElMessage} from "element-plus";
import {useTokenStore} from "@/stores/token.js";
import {useRouter} from "vue-router";
import {useUserInfoStore} from "@/stores/userInfo.js";

const passwordData=ref({
  old_pwd: '',
  new_pwd: '',
  re_pwd: ''
})
//定义密码函数
let checkRePassword=function(rule, value, callback){
  if(value===''){
    callback(new Error('请输入确认密码'))

  }else if(value!==passwordData.value.new_pwd){
    callback(new Error('两次输入的密码不一致'))
  }else {
    callback()
  }
}
const rules = {
  old_pwd: [
    {required:true , message:'请输入原密码',trigger:'blur'},
    {min:5,max:16,message: '长度为5-16位非空字符',trigger:'blur'}
  ],
  new_pwd: [
    {required:true , message:'请输入新密码',trigger:'blur'},
    {min:5,max:16,message: '长度为5-16位非空字符',trigger:'blur'}
  ],
  re_pwd: [
    {required:true , message:'请输入新密码',trigger:'blur'},
    {validator:checkRePassword,trigger:'blur'},
  ]
}
//修改密码
const tokenStore = useTokenStore();
const router=useRouter();
const userInfoStore=useUserInfoStore();
const updatePwd=async()=>{
  console.log(passwordData.value);
  //调用接口
  await userUpdatePwdService(passwordData.value)
  //清空pinia中的token和个人信息
  tokenStore.removeToken();
  userInfoStore.removeInfo();
  //跳转到登录页
  await router.push('/login')
  ElMessage.success('修改密码成功，请重新登录')
}
//重置密码
const resetPwd=()=>{
  passwordData.value.new_pwd = '';
  passwordData.value.re_pwd = '';
  passwordData.value.old_pwd = '';
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>重置密码</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="passwordData" :rules="rules" label-width="100px" size="large">
          <el-form-item label="原密码"  prop="old_pwd">
            <el-input type="password" placeholder="请输入原密码" v-model="passwordData.old_pwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码"  prop="new_pwd">
            <el-input type="password" placeholder="请输入新密码" v-model="passwordData.new_pwd"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码"  prop="re_pwd">
            <el-input type="password" placeholder="请确认新密码" v-model="passwordData.re_pwd" ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updatePwd">提交修改</el-button>
            <el-button @click="resetPwd">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>