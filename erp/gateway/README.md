## GateWay
1. 登录请求业务：具体业务操作，登录成功后，将token返回给前端，并将token保存到redis中。
2. 请求前过滤：主要验证是否含有access-token或x-access-token,并校验资源权限。
3. 请求后过滤：转码过滤（判断后端API的数据格式，统一封装成ResponseResult对象）和头部过滤（统一添加头部过滤）
4. 异常过滤：将异常数据统一封装成ResponseResult.
5. 配置加密组件：可以根据不同的用途生成不同的加密组件且可以用不同的加密算法，密密钥通过配置。
   * 加密access-token