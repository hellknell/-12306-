import {createRouter, createWebHistory} from 'vue-router'
// import {notification} from "ant-design-vue";
// import store from "@/store";


const routes = [
    {
        path: '/',
        name: 'Index',
        redirect: '/welcome',
        meta: {requiresAuth: true}, // 添加该字段，表示需要认证
        component: () => import('../views/MainView.vue'),
        children: [
            {
                path: '/welcome',
                name: 'Welcome',
                component: () => import('../views/main/WelComeView.vue'),
                meta: {requiresAuth: true}
            },
            {
                path: '/station',
                name: 'Station',
                component: () => import('../views/main/StationView.vue')
            },

            {
                path: '/train',
                name: 'Train',
                component: () => import('../views/main/TrainView.vue')
            },
            {
                path: '/train-station',
                name: 'TrainStation',
                component: () => import('../views/main/TrainStationView.vue')
            },
            {
                path: '/train-carriage',
                name: 'TrainCarriage',
                component: () => import('../views/main/TrainCarriageView.vue')
            },
            {
                path: '/train-seat',
                name: 'TrainSeat',
                component: () => import('../views/main/TrainSeatView.vue')
            }
        ]
    }]
const router = createRouter({
    history: createWebHistory(),
    routes
})
// router.beforeEach((to, from, next) => {
//     if (to.matched.some((item) => {
//         return item.meta?.requiresAuth;
//     })) {
//         let token = JSON.parse(localStorage.getItem("member"))?.token
//         if (!token) {
//             notification.error({description: "未登录或token已过期，请重新登录"})
//             next('/login');
//         } else {
//             next()
//         }
//     } else {
//         next();
//     }
// })


export default router
