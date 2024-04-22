
import { Message } from "@/api/interface";
import eventBus from "@/utils/event";
import { defineStore } from "pinia";

export const useWebSocket = defineStore('socket', {
    state: () => ({
        socket: null as WebSocket | null,
    }),
    actions: {
        setSocket(socket: WebSocket) {
            this.socket = socket
            this.socket.onopen = () => {
                console.log('【websocket】连接成功');
            } 
            this.socket.onmessage = (message) => {
                const data: Message = JSON.parse(message.data)
                eventBus.emit('onmessage', data)
            }
            this.socket.onerror = (event) => {
                console.error('WebSocket Error:', event);
            }
            this.socket.onclose = (event) => {
                console.log('WebSocket Connection Closed:', event);
            }
        },
        getSocket() {
            return this.socket
        },

    },
    // persist: {
    //     key: 'socket', //存储名称
    //     storage: sessionStorage, // 存储方式
    // }
})