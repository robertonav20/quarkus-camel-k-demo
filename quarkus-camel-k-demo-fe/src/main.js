import {createApp} from 'vue'
import {createRouter, createWebHistory} from 'vue-router';
import App from '@/App.vue'
import Main from "@/components/Main";
import Kubernetes from "@/components/Kubernetes";

import vuetify from './plugins/vuetify'
import {loadFonts} from './plugins/webfontloader'

const routes = [
    {
        path: '/',
        name: 'root',
        component: Main
    },
    {
        path: '/main',
        name: 'main',
        component: Main
    },
    {
        path: '/kubernetes',
        name: 'kubernetes',
        component: Kubernetes
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

loadFonts()

createApp(App)
    .use(vuetify)
    .use(router)
    .mount('#app')
