<template>
    <el-main>
        <div class="login-info">
            <div class="logo">
                <h2>PSM SYSTEM</h2>
            </div>
            <div class="info-img">
                <img src="../assets/images/info1.png" alt="">
            </div>
        </div>
        <div class="login-content">
            <div class="login-content-header">
                <h2>登录</h2>
                <span>通过账号密码登录</span>
            </div>
            <el-form ref="LoginFormRef" :model="loginForm" status-icon :rules="rules" size="large">
                <span>账号</span>
                <el-form-item prop="userName">
                    <el-input v-model="loginForm.userName" type="text" autocomplete="off" />
                </el-form-item>
                <span>密码</span>
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" type="password" autocomplete="off" show-password/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="goLogin()">登录</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="resetForm(LoginFormRef)">Reset</el-button>

                </el-form-item>
                <p class="left-text">或者<a @click="goRegister">注册账号</a></p>

            </el-form>
        </div>
    </el-main>
    <el-footer> © PSM</el-footer>
</template>
  
<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { login } from '@/api/login'
import { useRouter } from "vue-router"
import { ElMessage } from 'element-plus'
const LoginFormRef = ref<FormInstance>()
    const router = useRouter()
const goRegister = () => {
    router.replace('/register')
}
onMounted(() => {
    document.body.style.backgroundColor = '#fafafa'
    const token  = sessionStorage.getItem('token')
    if(token!==null){
        router.replace('/home')
    }
})
const validatePass = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else {
        callback()
    }
}
const validateName = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入账号'))
    } else {
        callback()
    }
}

const loginForm = reactive({
    userName: '',
    password: ''

})

const rules = reactive<FormRules<typeof loginForm>>({
    userName: [{ validator: validateName, trigger: 'blur' }],
    password: [{ validator: validatePass, trigger: 'blur' }],
})

const goLogin = () => {
    if (loginForm.password !== '' && loginForm.userName !== '') {
        login(loginForm)
    } else {
        ElMessage.error("账号和密码不能为空");
    }

}

const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
}

</script>
<style scoped lang="scss">
.el-main {
    min-height: 600px;
    padding: 40px 0;
    width: 1000px;
    display: flex;
    margin: 0 auto;
    flex-direction: row;
    background: #fff;
    box-shadow: 0 2px 30px #5f6f7c17;
}

.el-footer {
    margin-top: 16px;
    width: 1000px;
    color: #aaa;
    text-align: center;
    line-height: 25px;
    font-size: 14px;
    padding: 0 40px;
}

.el-button {

    width: 100%;
    height: 48px;
}

.login-info {
    display: flex;
    width: 500px;
    height: 520px;
    padding: 0 20px;
    flex: 1;
    flex-direction: column;
    border-right: 1px solid #eee;
}

.login-info .logo {
    height: 40px;
}

.login-info .logo h2 {
    height: 100%;
    width: 100%;
    text-align: left;
}

.info-img {
    display: flex;
    width: 100%;
    flex: 1;
    justify-content: center;
    align-items: center;
}

.info-img img {
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 450px;
}

.login-content {
    width: 500px;
    height: 520px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.login-content-header {
    height: 40px;
}

.login-content-header h2 {
    height: 20px;
    width: 80%;
    padding-left: 50px;
    text-align: left;
}

.login-content-header span {
    display: block;
    color: #999;
    width: 80%;
    padding-left: 50px;

    font: {
        size: 15px;
    }

    ;
    text-align: left;
}

.el-form {
    margin-top: 30px;
    padding-top: 40px;
    box-sizing: border-box;
    flex: 1;
    overflow: hidden;
    display: block;
    position: relative;

}

.el-input {
    width: 100%;
    height: 48px;
    font-size: 16px;
    line-height: 1.5715;

    color: #333;
    background-color: #fff;
    background-clip: padding-box;
    border: 1px solid #eee;
    border-radius: .25rem;
}

.el-form span {
    display: block;
    font-size: 17px;
    color: #666;
    padding-left: 60px;
    margin-top: 10px;
    margin-bottom: 5px;
    text-align: left;
}

.el-form-item {
    width: 80%;
    margin-left: 50px;
}

.left-text {
    text-align: left;
    padding-left: 50px;
    font-size: 14px;

    a {
        color: #6698ff;

        &:hover {
            text-decoration: underline;
            cursor: pointer;
        }
    }
}
</style>