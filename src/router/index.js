import {createRouter,createWebHistory} from "vue-router"
//导入组件
import Login from "@/views/Login.vue";
import layout from "@/views/Layout.vue"
import ArticleCategory from "@/views/article/ArticleCategory.vue";
import ArticleManage from "@/views/article/ArticleManage.vue";
import UserAvatar from "@/views/user/UserAvatar.vue";
import UserInfo from "@/views/user/UserInfo.vue";
import UserResetPassword from "@/views/user/UserResetPassword.vue";
//定义路由关系
const routes =[
    {
        path:"/",component:layout,redirect:"/article/manage",children:[
            {path:"/article/category",component:ArticleCategory},
            {path:"/article/manage",component:ArticleManage},
            {path: "/user/info",component:UserInfo},
            {path: "user/avatar",component:UserAvatar},
            {path:"user/resetPassword",component:UserResetPassword},
        ]
    },
    {path:"/login",component:Login}
]
//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes:routes
})
//导出路由器
export default router;