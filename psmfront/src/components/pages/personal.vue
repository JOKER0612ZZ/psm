<template>
    <el-container class="personal">
        <el-header>

        </el-header>
        <div class="personal_body">
            <div class="personal_body_head">
                <h2>个人资料</h2>
            </div>
            <div class="personal_body_main">
                <div class="aside">
                    <span>访问</span>
                    <div class="aside-menu">
                        <a href="#basic_info">基础信息</a>
                        <a href="#account_info">账号信息</a>
                        <a href="#change_password">修改密码</a>
                    </div>
                </div>
                <div class="main">
                    <div id="basic_info">
                        <span>基础信息</span>
                        <el-form size="large" label-position="right" label-width="60px" :model="userForm">
                            <el-form-item label="昵称">
                                <el-input v-model="userForm.nickname"></el-input>
                            </el-form-item>
                            <el-form-item label="性别">
                                <el-select v-model="userForm.gender" placeholder="Select" size="large">
                                    <el-option v-for="item in genderOptions" :key="item.value" :label="item.label"
                                        :value="item.value" />
                                </el-select>
                            </el-form-item>
                            <el-form-item label="">
                                <el-button type="primary"
                                    @click="updateNickNameAndGender(userForm.nickname, userForm.gender)">确认</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div id="account_info">
                        <span>账号信息</span>
                        <el-form label-position="right" label-width="60px" :model="changeForm">
                            <el-form-item label="用户名" class="account_item_info">
                                <p>{{ userInfo.userName }}</p>
                            </el-form-item>
                            <el-form-item label="" class="account_change">
                                <el-input v-model="changeForm.userName"></el-input>
                                <el-button type="primary" @click="updateUserName(changeForm.userName)">修改</el-button>
                            </el-form-item>
                            <el-form-item label="邮箱" class="account_item_info">
                                <p>{{ userInfo.email }}</p>
                            </el-form-item>
                            <el-form-item label="" class="account_change">
                                <el-input v-model="changeForm.email"></el-input>
                                <el-button type="primary" @click="updateEmail(changeForm.email)">重新绑定</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div id="change_password">
                        <span>修改密码</span>
                        <el-form label-position="right" label-width="90px" size="large" ref="passwordFormRef"
                            :model="passwordForm" :rules="passwordRule">
                            <el-form-item label="旧密码" prop="oldPass">
                                <el-input v-model="passwordForm.oldPass" type="password" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="新密码" prop="newPass">
                                <el-input v-model="passwordForm.newPass" type="password" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="确认新密码" prop="checkPass">
                                <el-input v-model="passwordForm.checkPass" type="password" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="">
                                <el-button type="primary"
                                    @click="updatePass(passwordForm.oldPass, passwordForm.newPass)">确定</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>

            </div>

        </div>

    </el-container>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, reactive } from 'vue'
import { useUserStore } from '@/store/user';
import type { FormInstance, FormRules } from 'element-plus'
import { updateEmailById, updateNickNameAndGenderById, updateUserNameById, updatePassWordById } from '@/api/user';
import eventBus from '@/utils/event';
const store = useUserStore();

onBeforeMount(() => {
    userInfo.value = store.getUserInfo()
    userForm.value = JSON.parse(JSON.stringify(store.getUserInfo()))
})
let userForm: any = ref({
    nickname: '',
    gender: ''
})
let userInfo: any = ref({
    userName: '',
    email: ''
})
const passwordForm = reactive({
    oldPass: '',
    newPass: '',
    checkPass: ''
})
const validateOldPass = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入旧密码'))
    } else {
        callback()
    }
}
const validateNewPass = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请输入新密码'))
    } else if (value === passwordForm.oldPass) {
        callback(new Error('新密码不能和旧密码一致'))
    } else {
        if (passwordForm.checkPass !== '') {
            if (!passwordFormRef.value) return
            passwordFormRef.value.validateField('checkPass', () => null)
        }
        callback()
    }
}
const validateCheckPass = (_rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('请再次输入新密码'))
    } else if (value !== passwordForm.newPass) {
        callback(new Error("两次输入不一致"))
    } else {
        callback()
    }
}

const passwordRule = reactive<FormRules<typeof passwordForm>>({
    oldPass: [{ validator: validateOldPass, trigger: 'blur' }],
    newPass: [{ validator: validateNewPass, trigger: 'blur' }],
    checkPass: [{ validator: validateCheckPass, trigger: 'blur' }]
})

const updatePass = (oldPass: string, newPass: string) => {
    passwordFormRef.value?.validate((valid) => {
        if (valid) {
            updatePassWordById(oldPass, newPass, passwordFormRef.value);
        }
    });

}
const passwordFormRef = ref<FormInstance>()

const updateUserName = async (userName: string) => {
    userInfo.value = await updateUserNameById(userName)
    eventBus.emit('userInfo', userInfo.value);
}
const updateEmail = async (email: string) => {
    userInfo.value = await updateEmailById(email)
    eventBus.emit('userInfo', userInfo.value);
}
const updateNickNameAndGender = async (nickname: string, gender: string) => {
    userForm.value = await updateNickNameAndGenderById(nickname, gender)
    eventBus.emit('userInfo', userInfo.value);
}
const changeForm = reactive({
    userName: '',
    email: ''
})
const genderOptions = [
    {
        value: '男',
        label: '男'
    },
    {
        value: '女',
        label: '女'
    },
    {
        value: '保密',
        label: '保密'
    }
]
</script>

<style scoped lang="scss">
.el-header {
    height: 7%;
    box-shadow: 0 4px 8px #00000008;
    position: relative;
    z-index: 2;
    border-bottom: 1px solid #eee;
}

.personal {
    overflow: hidden;
    height: 100%;
}

.personal_body {
    display: flex;
    flex-direction: column;
    height: 93%;

    .personal_body_main {
        display: flex;
        flex-direction: row;
        height: 90%;

        .aside {
            width: 20%;
            display: flex;
            flex-direction: column;
            border-right: 2px solid #eee;
            span{
                display: block;
                text-align: left;
                margin: 0 10px;
                margin-top: 10px;
                font-size: 12px;
                color: #999;
                line-height:40px;
            };
        }

        .main {
            display: flex;
            flex-direction: column;
            position: relative;
            overflow-y: scroll;
            width: 80%;

            span {
                display: block;
                position: relative;
                text-align: left;
                width: 60%;
                padding-top: 20px;
                margin-left: 100px;
                padding-bottom: 10px;
                font-size: 20px;
                font-weight: bold;
                border-bottom: 1px solid #eee;
            }
        }
    }

    .personal_body_head {
        align-items: center;
        height: 10%;
        border-bottom: 1px solid #eee;
    }

    .el-aside {
        background-color: #fff;

        span {
            display: block;
            position: relative;
            text-align: left;
            padding-top: 15px;
            padding-left: 15px;
        }
    }
}



.el-form {
    display: block;
    width: 40%;
    margin: 0 auto;
    margin-top: 40px;
    margin-bottom: 40px;
    p {
        display: inline;
        align-items: center;
        margin: 0px;

    }
}

.account_change {
    display: flex;
    flex-direction: row;

    .el-input {
        width: 70%;
    }

    .el-button {
        margin-left: 5%;
        width: 25%;
    }
}

.account_item_info {
    margin-bottom: 5px;
    margin-top: 5px;
}
.aside-menu{
    margin: 0 10px;
    display: flex;
    flex-direction: column;
    a{
        display: block;
        color: #333;
        padding: 10px;
        font-size: 15px;
        text-align: left;
        &:hover{
            color: #6698ff;
            background-color: #6698ff1a;
        }
    }
}
</style>