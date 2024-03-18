import { Router } from 'vue-router';
const checkLogined=(): boolean=> {
    const token: string | null = sessionStorage.getItem('token');
    if (token !== null) {
        return true;
    }
    return false;
}
export const intercept=(router:Router) =>{
    router.beforeEach(async(to, _from, next) => {
        const isLogin = checkLogined();
        if (to.name === 'login' || to.name === 'register') {
            next();
        } else {
            if (isLogin) {
                next();
            } else {
                next({ name: 'login' });
            }
        }
    });
}
