import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import request from "@/utils/request";

Vue.use(ElementUI, { size: "mini" });

Vue.config.productionTip = false

Vue.prototype.request = request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
