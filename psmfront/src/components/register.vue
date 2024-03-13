<template>
    <div id="register">
        <el-main>
            <p>输入信息</p>
            <p>开启PSM之旅</p>
            <el-form ref="registerFormRef" :model="registerForm" status-icon :rules="rules" label-width="auto"
                label-position="left" size="large">
                <el-form-item label="账号" prop="userName">
                    <el-input v-model="registerForm.userName" type="text"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" type="password" autocomplete="off" show-password/>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass">
                    <el-input v-model="registerForm.checkPass" type="password" autocomplete="off" show-password/>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="registerForm.email" type="text"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="doRegister()">注册</el-button>
                </el-form-item>
                <span>已有账号<a @click="goLogin">去登录</a></span>
            </el-form>
        </el-main>
    </div>

</template>

<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus'
import { register } from '@/api/login';


onMounted(() => {
    document.body.style.backgroundColor = '#fafafa'
})
const router = useRouter()
const goLogin = () => {
    router.replace('/login')
}
const registerFormRef = ref<FormInstance>()

const registerForm = reactive({
    userName: '',
    password: '',
    email: '',
    checkPass: ''
})

const validateName = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入账号'))
    } else {
        callback()
    }
}
const validatePass = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入密码'))
    } else {
        if (registerForm.checkPass !== '') {
            if (!registerFormRef.value) return
            registerFormRef.value.validateField('checkPass', () => null)
        }
        callback()
    }
}
const validatePass2 = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请再次输入密码'))
    } else if (value !== registerForm.password) {
        callback(new Error("密码不匹配"))
    } else {
        callback()
    }
}
const validateEmail = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入邮箱号'))
    } else {
        callback()
    }
}
const rules = reactive<FormRules<typeof registerForm>>({
    userName: [{ validator: validateName, trigger: 'blur' }],
    password: [{ validator: validatePass, trigger: 'blur' }],
    checkPass: [{ validator: validatePass2, trigger: 'blur' }],
    email: [{ validator: validateEmail, trigger: 'blur' }]
})

const hasNull = (e: any): boolean => {
    for (var i in e) {
        if (e[i] === '') {
            return true
        }
    }
    return false
}
const doRegister = () => {
    if (hasNull(registerForm)) {
        ElMessage.error("有未填写的信息")
    }
    else {
        register(registerForm)
        goLogin()
    }

}


</script>

<style scoped lang="scss">
#register {
    width: 100vw;
    height: 100vh;
    position: absolute;
    left: 0;
    top: 0;
    background: {
        image: url('../assets/images/register.jpg');
        repeat: no-repeat;
        size: 100% 100%;
    }
    
}

.el-main {
    min-height: 500px;
    padding: 40px 0;
    width: 600px;
    display: flex;
    margin: 0 auto;
    margin-top: 120px;
    flex-direction: column;
    background: #fff;
    box-shadow: 0 2px 30px #5f6f7c17;
    border-radius: 1.75em;

    p {
        font: {
            size: 20px;
            weight: bold;
        }

        line-height: 20px;
        margin: 5px auto;
    }

    a {
        color: #6698ff;

        &:hover {
            text-decoration: underline;
            cursor: pointer;
        }
    }
}

.el-form {
    display: flex;
    flex-direction: column;
    width: 80%;
    margin: 0 auto;
    margin-top: 30px;
}

.el-button {
    width: 80%;
    margin: 5px auto;
}
</style>