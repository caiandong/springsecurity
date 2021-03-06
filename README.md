# springAdmin
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

- #### 在@ResponseBody后返回的字符串即使是json字符串，但它并不会被解析成application/json类型。

  - 因为它没有被json对象序列化器解析，所以ajax请求的回调方法无法收到string。除非手动设置其content-type，利用`@PostMapping(path = "/pets", consumes = "application/json") `和`@GetMapping(path = "/pets/{petId}", produces = "application/json;charset=UTF-8")`

- #### 根据框架中的静态资源处理，抄出来一个处理视频播放的Controller.这是为了防止直接输入资源位置播放，而绕过权限。

- #### 把目录操作抽取出来，封装成线程安全的类。

  - 返回可获取的文件名(与页面被访问次数正比，多次被调用)
  - 增加新的文件的目录(调用次数不固定，少数管理员会并发调用)
  - 根据文件名找出对应播放路径(与播放`请求`次数成正比，非常多次)

- 

- 获取上传视频的时长.网上搜的

  ```html
  <!DOCTYPE html>  
  <html>  
  	<head>  
  	    <meta charset="UTF-8">  
  	    <title>js获取上传视频的时长</title>  
  	</head>  
  	<body>  
  	   <video style="display:none;" controls="controls" id="aa" oncanplaythrough="myFunction(this)">
  	   	
  	   </video>
  	   <input type="file" onchange="changeFile(this)" />
  	   <br />
  	   <span id="getDuration"></span>
  	</body> 
  	<script type="text/javascript">  
  		function myFunction(ele) {
  			var hour = parseInt((ele.duration)/3600);
  			var minute = parseInt((ele.duration%3600)/60);
  			var second = Math.ceil(ele.duration%60);
  			//console.log(Math.floor(ele.duration));
  			//document.write("这段视频的时长为："+hour+"小时，"+minute+"分，"+second+"秒");
  			document.getElementById("getDuration").innerHTML="这段视频的时长为："+hour+"小时，"+minute+"分，"+second+"秒";
  		}  
  	      
  		function changeFile(ele){  
  		    var video = ele.files[0];  
  		    var url = URL.createObjectURL(video);  
  		    console.log(url);  
  		    document.getElementById("aa").src = url;  
  		}  
  	</script>  
   
  </html> 
  ```

  

- 在上传前获取视频文件截图作为封面一起上传

  - 文章太大，贴上连接，但愿不会丢失[解决方案](https://segmentfault.com/a/1190000010910097)

- 

- 

- 

- 

- 

- 权限表示

  - 网上所说，有url，菜单与按钮，以及操作。我觉得分为对用户可见性的按钮菜单以及其他的东西，然后是对用户不可见的url。增加与权限相关的两个表，做权限操作(包含资源的访问，事实上对用户可见的按钮等，都不是真正的权限，所有的权限都被抽象成一个个ulr访问的资源或操作)的ulr表，控制用户菜单按钮可见性的Visible表。

- 设计权限继承

  - 由于网上找不到合适的资料，rbac官网英文不理解。自己思考了一种方案。
  - 首先在角色表的基础上，再建立一张与之关联的表述继承关系的表。事实上由于数据库很难描述树行关系，或者说图关系。这张表是这样考虑的
  - 我们设立一个序列递增。只要一个角色继承了某个角色，它就在自己想要集成的父序列值的基础上增加1，这样我们可以认为2继承自1，同理3继承自2。如果其他也继承自1，再同样设立自己的序列为2。这样，只需要查出来比自己序列小的都是自己的祖先，并且按照顺序，分别是父，祖，曾祖。。。
  - 现在解决另一个问题，如果有两个体系，那么就会乱套。然而解决方法很容易实现，只需要预估继承层次的最大值，也就是说，先预留0到99的序列，作为第一个继承体系。从100到199作为第二个继承体系。完全够用了，因为在一个64位的int值上几乎每个继承体系间隔1万，也用不完64位int。并且如果不够大，只需要重新复制到下一个更大的序列。
  - 还有一个问题暂时没想到解决方案，我连问题都忘了，我尼玛。。。

- 权限设计

  ```text
  权限：系统管理 用户管理 查看用户 新增用户 修改用户 删除用户
  角色：系统管理员、管理员、用户、访客
  用户：他的权限集是自身具有的权限、所属的各角色具有的权限、所属的各组具有的权限的合集
  ```

  