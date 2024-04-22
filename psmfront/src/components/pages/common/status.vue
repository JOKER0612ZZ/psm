<template>
    <div :class="`select${idSelect}`">
        <el-select v-model="status" :style="{ width: `${statusWidth}` }" filterable allow-create @change="handleChange">
            <el-option v-for="item in option" :key="item.status" :label="item.label" :value="item.status"
                :style="item.style" />
        </el-select>
        
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted,nextTick } from 'vue';
const props = defineProps({
    status: {
        type: String,
        required: true
    },
    statusWidth: {
        type: String,
        default: '200px'
    },
    idSelect: {
        type: String,
        default: '0'
    }
})
let select: HTMLElement | null;
let selectPlaceholder: HTMLElement | null
onMounted(() => {
    nextTick(()=>{
        select = document.querySelector(`.select${props.idSelect}`) as HTMLElement;
        selectPlaceholder=select.querySelector(`.el-select__placeholder`)
        cssSelect(props.status)
    })
})
const status = ref<string>(props.status)
const emit = defineEmits()
const handleChange = (value: any) => {
    cssSelect(value)
    emit('changed',value)
}
const cssSelect = (status: string) => {
    switch (status) {
        case '未分配':
            if (selectPlaceholder) {
                selectPlaceholder.style.backgroundColor = '#909399'
            }
            break;
        case '进行中':
            if (selectPlaceholder) {
                selectPlaceholder.style.backgroundColor = '#409EFF'
            }
            break;
        case '审核中':
            if (selectPlaceholder) {
                selectPlaceholder.style.backgroundColor = '#E6A23C'
            }
            break;
        case '已完成':
            if (selectPlaceholder) {
                selectPlaceholder.style.backgroundColor = '#67C23A'
            }
            break;
        case '已延期':
            if (selectPlaceholder) {
                selectPlaceholder.style.backgroundColor = '#F56C6C'
            }
            break;
        case '已截止':
        if (selectPlaceholder) {
                selectPlaceholder.style.backgroundColor = '#c45656'
            }
            break;
        default:
            break;
    }
}
const option = [
    {
        status: '未分配',
        label: '未分配',
        style: {
            backgroundColor: '#909399',
            color: '#fff',
            borderRadius: '10px',
            margin: '5px',
            width: '150px'
        }
    },
    {
        status: '进行中',
        label: '进行中',
        style: {
            backgroundColor: '#409EFF',
            color: '#fff',
            borderRadius: '10px',
            margin: '5px',
            width: '150px'
        }
    },
    {
        status: '审核中',
        label: '审核中',
        style: {
            backgroundColor: '#E6A23C',
            color: '#fff',
            borderRadius: '10px',
            margin: '5px',
            width: '150px'
        }
    },
    {
        status: '已完成',
        label: '已完成',
        style: {
            backgroundColor: '#67C23A',
            color: '#fff',
            borderRadius: '10px',
            margin: '5px',
            width: '150px'
        }
    },
    {
        status: '已延期',
        label: '已延期',
        style: {
            backgroundColor: '#F56C6C',
            color: '#fff',
            borderRadius: '10px',
            margin: '5px',
            width: '150px'
        }
    },
    {
        status: '已截止',
        label: '已截止',
        style: {
            backgroundColor: '#c45656',
            color: '#fff',
            borderRadius: '10px',
            margin: '5px',
            width: '150px'
        }
    }
]
</script>

<style scoped lang="scss">
:deep(.el-select__placeholder) {
    color: #fff;
    text-align: center;
    width: 120%;
    left: -10px;
    background-color: #409EFF;
    border-radius: 10px 10px 10px 10px;
}
</style>