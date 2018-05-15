## Front-end
1. 利用Redis缓存session,前端通过cookie,保存sessionId
2. 登录拦截：利用LoginFilter,同时排除拦截的一些URL(在配置文件中设置)，判断Session是否存在。
