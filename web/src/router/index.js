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
                path: '/ticket',
                name: 'Ticket',
                component: () => import('../views/main/member//TicketView.vue')
            },
            {
                path: '/welcome',
                name: 'Welcome',
                component: () => import('../views/main/WelComeView.vue'),
                meta: {requiresAuth: true}
            },
            {
                path: 'base',
                name: 'Base',
                children: [
                    {
                        path: 'order'
                        , name: 'Order'
                        , component: () => import('../views/main/base/ConfirmOrderView.vue')
                    },
                    {
                        path: 'station',
                        name: 'Station',
                        component: () => import('../views/main/base/StationView.vue')
                    },

                    {
                        path: 'train',
                        name: 'Train',
                        component: () => import('../views/main/base/TrainView.vue')
                    },
                    {
                        path: 'train-station',
                        name: 'TrainStation',
                        component: () => import('../views/main/base/TrainStationView.vue')
                    },
                    {
                        path: 'train-carriage',
                        name: 'TrainCarriage',
                        component: () => import('../views/main/base/TrainCarriageView.vue')
                    },
                    {
                        path: 'train-seat',
                        name: 'TrainSeat',
                        component: () => import('../views/main/base/TrainSeatView.vue')
                    }

                ]
            },
            {
                path: '/daily',
                name: 'Daily',
                children: [

                    {
                        path: 'train-carriage'
                        , name: 'DailyTrainCarriage'
                        , component: () => import('../views/main/daily/DailyTrainCarriageView.vue')
                    },
                    {
                        path: 'train-seat'
                        , name: 'DailyTrainSeat'
                        , component: () => import('../views/main/daily/DailyTrainSeatView.vue')
                    },
                    {
                        path: 'train-station'
                        , name: 'DailyTrainStation'
                        , component: () => import('../views/main/daily/DailyTrainStationView.vue')
                    },
                    {
                        path: 'train'
                        , name: 'DailyTrain'
                        , component: () => import('../views/main/daily/DailyTrainView.vue')
                    },
                    {
                        path: 'train-ticket',
                        name: 'DailyTrainTicket',
                        component: () => import('../views/main/daily/DailyTrainTicketView.vue')
                    }
                ]
            }
            ,
            {
                path: '/batch',
                name: 'Batch',
                children: [

                    {
                        path: 'job'
                        , name: 'Job'
                        , component: () => import('../views/main/batch/JobView.vue')
                    }
                ]
            }

        ]
    },

]
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
