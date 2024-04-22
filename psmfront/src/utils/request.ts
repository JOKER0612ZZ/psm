import router from '@/router';
import axios, { InternalAxiosRequestConfig, AxiosResponse } from 'axios';
import { ElMessage } from 'element-plus'
// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8080', // api的base_url
    // baseURL: 'http://120.78.5.157:8080', 
    timeout: 10000 // 请求超时时间
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
        if (token !== null) {
            config.headers['token'] = token;
        }
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
        const res = response.data;
        if(res.status === 1015){
            return res;
        }
        if(res.code === 1022){
            sessionStorage.clear()
            router.replace('/login')
        }
        if (res.code === 1007) {
            return res;
        }
        if(res === ''){
            return res;
        }
        if (response.status === 200 && res.success) {
            ElMessage.success(res.message);
        } else {
            ElMessage.error(res.message);
        }
        return res;
    },
    (error: any) => {
        // 检查错误对象是否包含响应信息
        if (error.response && error.response.status === 1015) {
            return Promise.resolve(error.response.data); // 在状态码为1015时，直接返回响应数据
        }
        ElMessage.error(error); // for debug
        return Promise.reject(error);
    }
)

export default {
      
    // get方法
    // get<T = any>(url: string, params: object = {}): Promise<T> {
    //     return service({
    //         url: url,
    //         method: 'get',
    //         params: params
    //     })
    // },
    get(url: string, params: object = {}): Promise<ResponseData> {
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
    download<T = any>(url: string, data: object = {}): Promise<T> {
        return service({
            url: url,
            method: 'post',
            data: data,  
            responseType:'blob',
        }).then(response => {
            return Promise.resolve(response); // 解决Promise
        })
        .catch(error => {
            if (error.response && error.response.status === 1015) {
                return Promise.resolve(error.response.data); // 状态码为1015时视为成功
            } else {
               return Promise.reject(error); // 其他错误情况
            }
        });
    },
    upload<T = any>(url: string,data: object = {}): Promise<T> {
        return service({
            url: url,
            method: 'post',
            data: data,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
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
