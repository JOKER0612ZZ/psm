<template>
    <div class="member">
        <el-main>
            <div class="member-content">
                <div class="member-content-header">
                    <span>成员列表</span>
                    <el-button plain type="primary" @click="visible = true">邀请成员</el-button>
                    <el-dialog v-model="visible" title="邀请成员" width="250" @close="close">
                        <div class="invite-header">
                            <el-input v-model="condition" class="invite-input"></el-input>
                            <el-icon size="25" class="invite-search" @click="search">
                                <Search />
                            </el-icon>
                        </div>
                        <invite :users="users" @messages="sendMessage" v-if="inviteVisible"></invite>
                    </el-dialog>
                </div>
                <el-table :data="member" :row-style="rowStyle" @row-click="showDetails">
                    <el-table-column prop="memberName" width="200" label="姓名"></el-table-column>
                    <el-table-column prop="roleName" label="职位">
                        <template #default="{ row }">
                            <span v-if="row.roleName === 'manager'">项目经理</span>
                            <span v-if="row.roleName === 'member'">成员</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template #default="{row}">
                            <el-button @click.stop="remove(row.teamId,row.memberId)" type="danger" v-if="row.roleName!=='manager'&&(project?.creatorId===userInfo.userId)">移出团队</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <el-dialog v-model="detailsVisible" class="details">
                <member-details :member="details!"></member-details>
            </el-dialog>
            <div class="member-center">
                <span class="title">活跃成员</span>
                <div class="frist">
                    <div>
                        <span>今日活跃人数</span>
                        <span class="big">{{ people }}<div class="suffix">人</div></span>
                    </div>
                    <div>
                        <span>今日活跃率</span>
                        <span class="big">{{ percent }}<div class="suffix">%</div></span>

                    </div>
                </div>
                <div class="member-active" ref="memberActive">

                </div>
            </div>
        </el-main>
    </div>
</template>


<script setup lang="ts">
import { Members } from '@/api/interface';
import { queryProjectMembers,removeMember } from '@/api/team';
import { useProjectStore } from '@/store/project';
import { onBeforeMount, ref, onMounted } from 'vue';
import echarts, { ECOption } from '@/utils/echarts';
import { getActive } from '@/api/project';
import { getCurrentDateForMat } from '@/utils/dateUtils';
import invite from './invite.vue';
import { searchUser } from '@/api/user';
import { ElMessage } from 'element-plus';
import { useWebSocket } from '@/store/websocket';
import { useUserStore } from '@/store/user';
import memberDetails from '../pages/team/memberDetails.vue';
const project = useProjectStore().projectInfo
const userInfo =useUserStore().userInfo
const member = ref<Members[]>([])
const memberActive = ref<HTMLElement | null>()
let keys: string[] = [];
let datas: number[] = [];
let percents: number[] = [];
let active: Object;
const people = ref<number>(0)
const percent = ref<number>(0)
const date = getCurrentDateForMat()
const visible = ref(false)
const condition = ref<string>()
const users = ref<any>()
const inviteVisible = ref(false)
const socket = useWebSocket().getSocket()
const invites = (messages: []) => {
    if (messages.length !== 0) {
        if (socket) {
            socket.send(JSON.stringify(messages));
        }
    }
}
onMounted(async () => {
    active = await getActive(project?.projectId!);
    loadEchartData()
    initEcharts()
})
const remove=(teamId:string,memberId:string)=>{
    removeMember(teamId,memberId).then(async()=>{
        member.value = await queryProjectMembers(project?.projectId!)
    })
}
const loadEchartData = async () => {
    if (typeof active === 'object' && active !== null) {
        Object.keys(active).forEach((key) => {
            const value = active[key];
            if (value) {
                keys.push(key);
                datas.push(value.set.length);
                percents.push(value.percent)
                if (key == date) {
                    people.value = value.set.length
                    percent.value = value.percent
                } // 如果是 Set，使用 value.size
            }
        });
    }
}
onBeforeMount(async () => {
    member.value = await queryProjectMembers(project?.projectId!)
})
const rowStyle = (_row: any, _index: number) => {
    return {
        height: '40px', // 设置行高

    };
};

const initEcharts = () => {
    const option: ECOption = {
        backgroundColor: '#fff',
        tooltip: {
            trigger: 'axis',
        },
        legend: {
            data: ['活跃人数', '活跃率'],
        },
        xAxis: {
            data: keys,
        },
        yAxis: [
            {
                type: 'value',
                name: '活跃人数',
                splitLine: { show: false },
            },
            {
                type: 'value',
                name: '活跃率',
                splitLine: { show: false },
            }
        ],
        series: [
            {
                name: '活跃率',
                type: 'line',
                yAxisIndex: 1, // 将此系列与第二个y轴关联（活跃率）
                smooth: true,
                showAllSymbol: true,
                symbol: 'emptyCircle',
                symbolSize: 15,
                data: percents
            },
            {
                name: '活跃人数',
                type: 'bar',
                yAxisIndex: 0, // 将此系列与第一个y轴关联（活跃人数）
                barWidth: 30,
                itemStyle: {
                    borderRadius: 5,
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        { offset: 0, color: '#14c8d4' },
                        { offset: 1, color: '#43eec6' }
                    ])
                },
                data: datas
            },
        ]
    };
    const lineChart = echarts.init(memberActive.value)
    lineChart.setOption(option)
}
const search = async () => {
    users.value = await searchUser(condition.value!)
    console.log('users.value: ', users.value);
    inviteVisible.value = true
}
const close = () => {
    users.value = ''
    inviteVisible.value = false
    condition.value = ''
}
const sendMessage = (data: any) => {
    console.log('data: ', data);
    let memberName
    let flag= member.value.some((memberItem: any) => {
        return data.some((d: any) => {
            if (memberItem.memberId === d.receiverId) {
                memberName = memberItem.memberName;
                return true
            }
        });
    });
    if (!flag) {
        invites(data)
    } else {
        ElMessage.error(`${memberName}已经参与了项目`)
    }
    inviteVisible.value = false
    visible.value = false
}
const detailsVisible = ref(false)
const details= ref<Members>()
const showDetails=(row:any)=>{
    details.value=row
    detailsVisible.value=true
}
</script>

<style lang="scss" scoped>
.member {
    background-color: #fafafa;
    height: 100%;
    width: 100%;

    .el-main {

        height: 100%;
        padding: none;
        outline: 1px solid #eee;
        z-index: 1;
        background-color: #fff;

        span {
            display: inline-block;
            width: 100%;
            text-align: left;
        }

        .member-content {
            outline: 1px solid #eee;
            padding: 20px;
        }
    }
}

.member-active {
    margin-top: 20px;
    width: 100%;
    height: 400px;
}

.member-center {
    padding: 10px;
    outline: 1px solid #eee;
    margin-top: 20px;

    .title {
        display: block;
        width: 100%;
        margin-top: 10px;
        margin-bottom: 20px;
    }

    .frist {
        display: flex;
        flex-direction: row;
        margin-left: 50px;
        margin-top: 20px;

        .big {
            font-size: 48px;
            font-family: digiter;
            margin-left: 30px;

            .suffix {
                display: inline-block;
                font-size: 14px;

            }
        }

    }
}

.member-content-header {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
}

.invite-input {
    width: 200px;
}

:deep(.el-dialog) {
    width: 400px !important;
    padding: 20px;
    border-radius: 20px;
}
:deep(.details){
    width:600px !important;
    padding: 20px;
}
.invite-search {
    z-index: 3;
    position: relative;
    right: -80px;
    top: -27px;
}

.invite-header {
    width: 200px;
    margin: 0 auto;
    margin-top: 5px;
    height: 40px;
}
</style>