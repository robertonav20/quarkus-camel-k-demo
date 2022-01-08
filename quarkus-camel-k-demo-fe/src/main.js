import {createApp} from 'vue'
import App from '@/App.vue'
import router from "@/router";

import BalmUI from 'balm-ui'; // Official Google Material Components
import BalmUIPlus from 'balm-ui-plus'; // BalmJS Team Material Components
import 'balm-ui-css';

createApp(App)
    .use(router)
    .use(BalmUI, {
        $theme: {
            primary: '#326ce5',
            secondary: '#11cc4b'
        }
    })
    .use(BalmUIPlus)
    .mount('#app');
