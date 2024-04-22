<template>
    <el-container class="personal">
        <el-header> 
            <span>
                <svg t="1710423340042" class="icon" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="4582" width="30" height="30">
                    <path
                        d="M896.3584 512.3584c0-23.9104-2.56-41.3184-2.56-41.3184a46.2848 46.2848 0 0 1 20.992-43.1616l52.224-30.208a29.0816 29.0816 0 0 0 10.6496-39.5776l-99.1232-171.52a29.1328 29.1328 0 0 0-39.6288-10.5984l-52.224 30.1056a46.7456 46.7456 0 0 1-48.0256-3.328s-13.9264-10.8032-34.2016-22.528c-20.3776-11.776-37.4272-18.688-37.4272-18.688a46.7456 46.7456 0 0 1-26.88-39.936V61.2864a29.0816 29.0816 0 0 0-28.9792-29.0304H412.9792a29.0816 29.0816 0 0 0-29.0304 29.0304v60.3136a46.7456 46.7456 0 0 1-26.8288 39.936s-17.0496 6.9632-37.376 18.6368c-20.3264 11.776-34.304 22.5792-34.304 22.5792a46.7456 46.7456 0 0 1-48.0256 3.328l-52.224-30.1568a29.0816 29.0816 0 0 0-39.5776 10.5984L46.4896 358.0928a29.0816 29.0816 0 0 0 10.6496 39.6288l52.224 30.1056c13.824 7.9872 23.2448 27.4432 20.9408 43.2128 0 0-2.56 17.408-2.56 41.3184 0 24.0128 2.56 41.3696 2.56 41.3696a46.2848 46.2848 0 0 1-20.992 43.2128l-52.1728 30.1056a29.0816 29.0816 0 0 0-10.5984 39.5776l99.1232 171.52c7.9872 13.824 25.8048 18.6368 39.6288 10.6496l52.224-30.1056a46.592 46.592 0 0 1 47.9744 3.328s12.8 9.984 32.4096 21.504c20.8384 12.1856 39.2192 19.6608 39.2192 19.6608a46.7456 46.7456 0 0 1 26.8288 39.936v60.3136c0 15.9744 13.056 29.0304 29.0304 29.0304h198.144a29.0816 29.0816 0 0 0 29.0304-29.0304V903.168c0-15.9232 12.0832-33.8944 26.88-39.936 0 0 16.896-6.8608 37.12-18.4832 20.3776-11.776 34.4576-22.7328 34.4576-22.7328a46.6944 46.6944 0 0 1 48.0256-3.2768l52.224 30.1056c13.824 7.9872 31.744 3.2256 39.68-10.5984l99.072-171.52a29.0816 29.0816 0 0 0-10.5984-39.6288l-52.224-30.1056a46.2336 46.2336 0 0 1-20.992-43.2128s2.56-17.3568 2.56-41.3696z m-384.3072 160.0512a160.0512 160.0512 0 1 1 0-320.0512 160.0512 160.0512 0 1 1 0 320z"
                        fill="#d81e06" p-id="4583"></path>
                </svg>
                账号设置
            </span>
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
                            <!-- <el-form-item label="用户名" class="account_item_info">
                                <p>{{ userInfo.userName }}</p>
                            </el-form-item> -->
                            <!-- <el-form-item label="" class="account_change">
                                <el-input v-model="changeForm.userName"></el-input>
                                <el-button type="primary" @click="updateUserName(changeForm.userName)">修改</el-button>
                            </el-form-item> -->
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
import { updateEmailById, updateNickNameAndGenderById, updatePassWordById } from '@/api/user';
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

// const updateUserName = async (userName: string) => {
//     userInfo.value = await updateUserNameById(userName)
//     eventBus.emit('userInfo', userInfo.value);
// }
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
    display: flex;
    height: 7%;
    box-shadow: 0 4px 8px #00000008;
    align-items: center;
    position: relative;
    z-index: 2;
    border-bottom: 1px solid #eee;
    padding: 0;
    span {
        display: flex;
        flex-direction: row;
        align-items: center;
        font-size: 17px;
        margin-left: 25px;
        svg{
            margin-right: 10px;
        }
    }
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

            span {
                display: block;
                text-align: left;
                margin: 0 10px;
                margin-top: 10px;
                font-size: 12px;
                color: #999;
                line-height: 40px;
            }

            ;
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

.aside-menu {
    margin: 0 10px;
    display: flex;
    flex-direction: column;

    a {
        display: block;
        color: #333;
        padding: 10px;
        font-size: 15px;
        text-align: left;

        &:hover {
            color: #6698ff;
            background-color: #6698ff1a;
        }
    }
}
</style>