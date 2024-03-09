<template>
    <el-container class="personal">
        <el-header>

        </el-header>
        <div class="personal_body">
            <div class="personal_body_head">
                <h2>个人资料</h2>
            </div>
            <div class="personal_body_main">
                <div class="basic_info">
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
                <div class="account_info">
                    <span>账号信息</span>
                    <el-form label-position="right" label-width="60px" :model="changeForm">
                        <el-form-item label="用户名" class="account_item_info">
                            <p v-if="userInfo">{{ userInfo.userName }}</p>
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
                <div class="change_password">
                    <span>修改密码</span>
                    <el-form label-position="right" label-width="90px" size="large">
                        <el-form-item label="旧密码">
                            <el-input></el-input>
                        </el-form-item>
                        <el-form-item label="新密码">
                            <el-input></el-input>
                        </el-form-item>
                        <el-form-item label="确认新密码">
                            <el-input></el-input>
                        </el-form-item>
                        <el-form-item label="">
                            <el-button type="primary">确定</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </div>

        </div>

    </el-container>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, reactive } from 'vue'
import { useUserStore } from '@/store/user';
import { updateEmailById, updateNickNameAndGenderById, updateUserNameById } from '@/api/user';
let userForm: any = ref({})
let userInfo: any = ref({})
const store = useUserStore();
onBeforeMount(() => {
    userInfo = store.getUserInfo()
    userForm.value = userForm.value = JSON.parse(JSON.stringify(store.getUserInfo()))
})
const updateUserName = async (userName: string) => {
    userInfo.value  = await updateUserNameById(userName)
    console.log(userInfo);
}
const updateEmail =async (email: string) => {
    userInfo = await updateEmailById(email)
}
const updateNickNameAndGender = async (nickname: string, gender: string) => {
    userInfo =await updateNickNameAndGenderById(nickname, gender)
    
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
    height: 52px;
    line-height: 52px;
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
    height: inherit;

    .personal_body_main {
        display: flex;
        flex-direction: column;
        overflow-y: auto;
        height: 80%;

        span {
            display: block;
            position: relative;
            text-align: left;
            width: 60%;
            padding-top: 20px;
            margin-left: 350px;
            padding-bottom: 10px;
            font-size: 20px;
            font-weight: bold;
            border-bottom: 1px solid #eee;
        }
    }

    .personal_body_head {
        align-items: center;
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
</style>