import axios, { InternalAxiosRequestConfig, AxiosResponse } from 'axios';
import { ElMessage } from 'element-plus'
// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8080', // api的base_url
    timeout: 5000 // 请求超时时间
})

// 定义接口
export interface ResponseData<T = any> {
    code: number,
    data: T,
    message: string,
    success: boolean
}


// request拦截器
service.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const token = window.sessionStorage.getItem('token')
        if(token!==null){
            config.headers['token'] = token;
        }
        // if(store.userInfo.data.token!==''){
        //     console.log(store.userInfo.data.token)
        //     config.headers.set('token',store.userInfo.data.token)
        // }
        // 在这里可以做些什么，例如添加token到headers
        return config
    },
    (error: any) => {
        // 请求错误时做些事情
        ElMessage.error(error.message)
        return Promise.reject(error)
    }
)

// respone拦截器
service.interceptors.response.use(
    (response: AxiosResponse) => {
        /**
         * 在这里可以对响应数据做些什么
         * 如果你想获取http信息，如headers或status
         * 请返回response => response
        */
        const res = response.data;
        console.log(response)
        if (response.status == 200 && res.success) {
            ElMessage.success(res.message)
        }else {
            ElMessage.error(res.message)
        }
        return res;
    },
    (error: any) => {
        ElMessage.error(error) // for debug
        return Promise.reject(error)
    }
)

export default {
    // get方法
    get<T = any>(url: string, params: object = {}): Promise<T> {
        return service({
            url: url,
            method: 'get',
            params: params
        })
    },
    // post方法
    post<T = any>(url: string, data: object = {}): Promise<T> {
        return service({
            url: url,
            method: 'post',
            data: data
        })
    },
    // put方法
    put<T = any>(url: string, data: object = {}): Promise<T> {
        return service({
            url: url,
            method: 'put',
            data: data
        })
    },
    // delete方法
    delete<T = any>(url: string, params: object = {}): Promise<T> {
        return service({
            url: url,
            method: 'delete',
            params: params
        })
    }
}
