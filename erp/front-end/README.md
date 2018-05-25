## Front-end
1. 利用Redis缓存session,前端通过cookie,保存sessionId
2. 登录拦截：利用LoginFilter,同时排除拦截的一些URL(在配置文件中设置)，判断Session是否存在。
3. LogFilter:访问日志输出，排除静态文件
4. CsrfFilter:csrf_token检查
5. ResubmitFilter:防止重复提交检查
6. 尽量不处理业务逻辑控制，仅将后端数据返回到页面输出
