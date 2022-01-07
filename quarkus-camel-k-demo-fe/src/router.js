import {createRouter, createWebHistory} from 'vue-router'
import MongoDB from "@/components/MongoDB";
import Kubernetes from "@/components/Kubernetes";

const routes = [
    {
        path: '/mongo',
        name: 'Mongo',
        component: MongoDB,
        meta: {
            icon: 'description'
        }
    },
    {
        path: '/kubernetes',
        name: 'Kubernetes',
        component: Kubernetes,
        meta: {
            icon: 'description'
        }
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
