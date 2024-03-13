import { EventEmitter } from 'events';

// 创建全局事件总线
const eventBus = new EventEmitter();

export default eventBus;