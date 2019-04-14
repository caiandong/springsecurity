# springsecurity
## 遇到的问题

- #### 权限的同步问题

  采用了权限从数据库动态查询的原因是权限本身是动态变化的。springsecurity文档中表示不推荐这种做法。最直观的想法是把这个查询通过springcache抽象来提高性能。在用redis作为缓存后备时使用了json序列化器(这里的序列化器的坑也有)，因为原生jdk序列化也不被推荐并且占用内存多点，总之最后在json反序列化出错。这使我意识到这个查询操作可能会根据权限需求而变化，而且还有一个本质问题，线程安全。事实上另一个严重问题是，我在查询权限的类中放入了共享对象作为对每个请求调用查询的优化(又是万恶的过早优化性能)，我又想把它放在springcache抽象中，导致了设计逻辑混乱。最终我选择重写这个查询逻辑，利用了双重加锁保护共享对象，并且去除此查询的springcache。

- #### 在没有登录时操作，遇到需要权限时引导用户登录。那么采用新页面登录，原页面如何刷新？

  这里有搜索到的解决方案[oauth授权登入在授权成功后如何自动关闭窗口并刷新源网页？]( https://segmentfault.com/q/1010000008980773/a-1020000008982156 )

  ```
  LZ主要想问前端怎么做吧？作为一个写后台的，对后台的处理你应该比较熟悉了，我就不多说了。
  
  前端主要在于两点：
  
  要保证打开的窗口可控（可以关闭）
  
  要能收到授权成功的通知/事件
  
  第一点而言，很简单 window.open() 打开窗口的时候函数返回值保留下来。请参考 window.open on MDN.
  
  而第二点，就需要前后台合作了，一般可以通过轮询、长连接拉取、EventSource甚至WebSocket等方式来搞。由于登录功能不是一个长时间跑的业务，一般轮询就可以了。
  
  前端伪代码：
  
  $('#QQ登录按钮ID').on('click', function(){
      // 打开小窗，让用户登录，并保留窗口对象以备关闭
      var authWin = window.open('你的授权的页面URL', '_blank', 'width=600,height=400,menubar=no,toolbar=no,location=no')
      
      // 轮询是否授权成功，授权成功后关闭小窗，并刷新页面
      var timerId = setInterval(function(){
          $.getJSON("你的授权是否成功的轮询地址?相关参数", function(response){
              if (response.授权成功){
                  clearInterval(timerId)
                  authWin.close()
                  window.location.reload()
              }
          })
      }, 500)
  })
  ```

- 在@ResponseBody后返回的字符串并不是json对象，它并不会被解析成application/json类型。因为它没有被json对象序列化器解析。所以ajax请求的回调方法无法收到string。

- 下载和浏览是两种东西，但浏览器都把它们看待成资源，

- 

- 

- 

- 

- 

- 

- 

- 

- 

- 

- 

- 权限设计

  ```text
  权限：系统管理 用户管理 查看用户 新增用户 修改用户 删除用户
  角色：系统管理员、管理员、用户、访客
  用户：他的权限集是自身具有的权限、所属的各角色具有的权限、所属的各组具有的权限的合集
  ```

  
# admins

#### 介绍
{**以下是码云平台说明，您可以替换此简介**
码云是开源中国推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用码云实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 软件架构
软件架构说明


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
