\1.       用户表

| 用户表（TUser） |                 |             |              |
| --------------- | --------------- | ----------- | ------------ |
| 字段名称        | 字段            | 类型        | 备注         |
| 记录标识        | tu_id           | bigint      | pk, not null |
| 所属组织        | to_id           | bigint      | fk, not null |
| 登录帐号        | login_name      | varchar(64) | not null     |
| 用户密码        | password        | varchar(64) | not null     |
| 用户姓名        | vsername        | varchar(64) | not null     |
| 手机号          | mobile          | varchar(20) |              |
| 电子邮箱        | email           | varchar(64) |              |
| 创建时间        | gen_time        | datetime    | not null     |
| 登录时间        | login_time      | datetime    |              |
| 上次登录时间    | last_login_time | datetime    |              |
| 登录次数        | count           | bigint      | not null     |

\2.       角色表

| 角色表（TRole） |              |              |              |
| --------------- | ------------ | ------------ | ------------ |
| 字段名称        | 字段         | 类型         | 备注         |
| 角色ID          | tr_id        | bigint       | pk, not null |
| 父级角色ID      | parent_tr_id | bigint       | not null     |
| 角色名称        | role_name    | varchar(64)  | not null     |
| 创建时间        | gen_time     | datetime     | not null     |
| 角色描述        | description  | varchar(200) |              |

\3.       权限表

| 权限表（TRight） |              |              |              |
| ---------------- | ------------ | ------------ | ------------ |
| 字段名称         | 字段         | 类型         | 备注         |
| 权限ID           | tr_id        | bigint       | pk, not null |
| 父权限           | parent_tr_id | bigint       | not null     |
| 权限名称         | right_name   | varchar(64)  | not null     |
| 权限描述         | description  | varchar(200) |              |

\4.       组表

| 组表（TGroup） |              |              |              |
| -------------- | ------------ | ------------ | ------------ |
| 字段名称       | 字段         | 类型         | 备注         |
| 组ID           | tg_id        | bigint       | pk, not null |
| 组名称         | group_name   | varchar(64)  | not null     |
| 父组           | parent_tg_id | bigint       | not null     |
| 创建时间       | gen_time     | datetime     | not null     |
| 组描述         | description  | varchar(200) |              |

\5.       角色权限表

| 角色权限表（TRoleRightRelation） |            |        |                                |
| -------------------------------- | ---------- | ------ | ------------------------------ |
| 字段名称                         | 字段       | 类型   | 备注                           |
| 记录标识                         | trr_id     | bigint | pk, not null                   |
| 角色                             | Role_id    | bigint | fk, not null                   |
| 权限                             | right_id   | bigint | fk, not null                   |
| 权限类型                         | right_type | int    | not null（0:可访问，1:可授权） |

\6.       组权限表

| 组权限表（TGroupRightRelation） |            |        |                                |
| ------------------------------- | ---------- | ------ | ------------------------------ |
| 字段名称                        | 字段       | 类型   | 备注                           |
| 记录标识                        | tgr_id     | bigint | pk, not null                   |
| 组                              | tg_id      | bigint | fk, not null                   |
| 权限                            | tr_id      | bigint | fk, not null                   |
| 权限类型                        | right_type | int    | not null（0:可访问，1:可授权） |

\7.       组角色表

| 组角色表（TGroupRoleRelation） |        |        |              |
| ------------------------------ | ------ | ------ | ------------ |
| 字段名称                       | 字段   | 类型   | 备注         |
| 记录标识                       | tgr_id | bigint | pk, not null |
| 组                             | tg_id  | bigint | fk, not null |
| 角色                           | tr_id  | bigint | pk, not null |

\8.       用户权限表

| 用户权限表（TUserRightRelation） |            |        |                                |
| -------------------------------- | ---------- | ------ | ------------------------------ |
| 字段名称                         | 字段       | 类型   | 备注                           |
| 记录标识                         | tur_id     | bigint | pk, not null                   |
| 用户                             | tu_id      | bigint | fk, not null                   |
| 权限                             | tr_id      | bigint | fk, not null                   |
| 权限类型                         | right_type | int    | not null（0:可访问，1:可授权） |

\9.       用户角色表

| 用户角色表（TUserRoleRelation） |        |        |              |
| ------------------------------- | ------ | ------ | ------------ |
| 字段名称                        | 字段   | 类型   | 备注         |
| 记录标识                        | tur_id | bigint | pk, not null |
| 用户                            | tu_id  | bigint | fk, not null |
| 角色                            | tr_id  | bigint | fk, not null |

\10.   用户组表

| 用户组表（TUserGroupRelation） |        |        |              |
| ------------------------------ | ------ | ------ | ------------ |
| 字段名称                       | 字段   | 类型   | 备注         |
| 记录标识                       | tug_id | bigint | pk, not null |
| 用户                           | tu_id  | bigint | fk, not null |
| 组                             | tg_id  | bigint | fk, not null |

\11.   组织表

| 组织表（TOrganization） |              |              |              |
| ----------------------- | ------------ | ------------ | ------------ |
| 字段名称                | 字段         | 类型         | 备注         |
| 组织id                  | to_id        | bigint       | pk, not null |
| 父组                    | parent_to_id | bigint       | not null     |
| 组织名称                | org_name     | varchar(64)  | not null     |
| 创建时间                | gen_time     | datetime     | not null     |
| 组织描述                | description  | varchar(200) |              |

\12.   操作日志表

| 操作日志表（TLog） |          |              |              |
| ------------------ | -------- | ------------ | ------------ |
| 字段名称           | 字段     | 类型         | 备注         |
| 日志ID             | log_id   | bigint       | pk, not null |
| 操作类型           | op_type  | int          | not null     |
| 操作内容           | content  | varchar(200) | not null     |
| 操作人             | tu_id    | bigint       | fk, not null |
| 操作时间           | gen_time | datetime     | not null     |

\4.      界面总体设计

本节将阐述用户界面的实现，在此之前对页面元素做如下约定：

| 序号 | 页面元素   | 约定                                   |
| ---- | ---------- | -------------------------------------- |
| 1    | 按钮       | 未选中时：[按钮名称]选中时：[按钮名称] |
| 2    | 单选框     | ○ 选项                                 |
| 3    | 复选框     | □ 选项                                 |
| 4    | 下拉框     | [选项,…,] ▽                            |
| 5    | 文本框     | \|________\|                           |
| 6    | TextArea   | \|…………\|                               |
| 7    | 页签       | 未选中时：选项名称 选中时：选项名称    |
| 8    | 未选中链接 | 链接文字                               |
| 9    | 选中链接   | 链接文字                               |
| 10   | 说明信息   | 说明信息                               |

 

4.1 组权限管理

4.1.1包含用户

| 组信息   组1       组11       组12       组…   组2       组21       组22       组… | 所选择组：组1[包含用户] [所属角色] [组权限] [总权限][修改]用户名   姓名     手机号   最近登录时间 登录次数阿蜜果 谢星星 13666666666 2007-10-8    66sterning xxx    13555555555 2007-10-8    10 …… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

当用户选择“修改”按钮时，弹出用户列表，操作人可以通过勾选或取消勾选来修改该组所包含的用户。

4.1.2所属角色

| 组信息   组1       组11       组12       组…   组2       组21       组22       组… | 所选择组：组1[包含用户] [所属角色] [组权限] [总权限][修改]角色ID   角色名称   角色描述1          访客       --   2         初级用户    -- |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

当用户选择“修改”按钮时，弹出角色树形结构，操作人可以通过勾选或取消勾选来修改该组所属的角色。

4.1.3组权限

| 组信息   组1       组11       组12       组…   组2       组21       组22       组… | 所选择组：组1[包含用户] [所属角色] [组权限] [总权限]                [保存] [取消] |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

4.1.4总权限

| 组信息   组1       组11       组12       组…   组2       组21       组22       组… | 所选择组：组1[包含用户] [所属角色] [组权限] [总权限]                [保存] [取消] |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

通过对已具有的权限取消勾选，或为某权限添加勾选，来修改组的权限信息，点击“保存”按钮保存修改信息。

4.1.5组管理

​       在下图中，选中组1的时候，右键点击可弹出组的操作列表，包括添加、删除和修改按钮，从而完成在该组下添加子组，删除该组以及修改该组的功能。

| 组信息   组1       组11       组12       组…   组2       组21       组22       组… | 所选择组：组1[包含用户] [所属角色] [组权限] [总权限][修改]用户名   姓名     手机号   最近登录时间 登录次数阿蜜果 谢星星 13666666666 2007-10-8    66sterning xxx    13555555555 2007-10-8    10 …… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

4.2 角色权限管理

4.2.1包含用户

| 角色信息   角色1       角色11       角色12       角色…   角色2       角色21       角色22       角色… | 所选择角色：角色1[包含用户] [包含组] [角色权限][修改]用户名   姓名     手机号   最近登录时间 登录次数阿蜜果 谢星星 13666666666 2007-10-8    66sterning xxx    13555555555 2007-10-8    10 …… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

当用户选择“修改”按钮时，弹出用户列表，操作人可以通过勾选或取消勾选来修改该角色所包含的用户。

4.2.2包含组

| 角色信息   角色1       角色11       角色12       角色…   角色2       角色21       角色22       角色… | 所选择角色：角色1[包含用户] [包含组] [角色权限][修改]组ID   组名称     组描述1      xxx1       --2       xxx2        -- …… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

当用户选择“修改”按钮时，弹出用户列表，操作人可以通过勾选或取消勾选来修改该角色所包含的组。

4.2.3角色权限

| 角色信息   角色1       角色11       角色12       角色…   角色2       角色21       角色22       角色… | 所选择角色：角色1[包含用户] [包含组] [角色权限]                                [保存] [取消] |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

通过对已具有的权限取消勾选，或为某权限添加勾选，来修改角色的权限信息，点击“保存”按钮保存修改信息。

4.2.4管理角色

​       在下图中，选中组1的时候，右键点击可弹出组的操作列表，包括添加、删除和修改按钮，从而完成在该组下添加子组，删除该组以及修改该组的功能。

| 角色信息   角色1       角色11       角色12       角色…   角色2       角色21       角色22       角色… | 所选择角色：角色1[包含用户] [包含组] [角色权限][修改]用户名   姓名     手机号   最近登录时间 登录次数阿蜜果 谢星星 13666666666 2007-10-8    66sterning xxx    13555555555 2007-10-8    10 …… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

4.3 用户权限管理

4.3.1所属角色

| 用户权限信息xx公司   广州分公司       阿蜜果       肖xx       yy…   北京分公司       zz1       zz2       zz3… | 所选择用户：阿蜜果[所属角色] [所属组] [用户权限] [总权限][修改]角色ID   角色名称   角色描述1          访客       --   2         初级用户    --… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

当用户选择“修改”按钮时，弹出角色树形结构，操作人可以通过勾选或取消勾选来修改该用户所属的角色。

4.3.2所属组

| 用户信息xx公司   广州分公司       阿蜜果       肖xx       yy…   北京分公司       zz1       zz2       zz3… | 所选择用户：阿蜜果[所属角色] [所属组] [用户权限] [总权限][修改]组ID   组名称     组描述1       组1         --   2       组2         --… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

当用户选择“修改”按钮时，弹出组的树形结构，操作人可以通过勾选或取消勾选来修改该用户所属的组。

4.3.3用户权限

| 用户信息xx公司   广州分公司       阿蜜果       肖xx       yy…   北京分公司       zz1       zz2       zz3… | 所选择用户：阿蜜果[所属角色] [所属组] [用户权限] [总权限]                                 [保存] [取消] |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

通过对已具有的权限取消勾选，或为某权限添加勾选，来修改用户的权限信息，点击“保存”按钮保存修改信息。

4.3.4总权限

| 用户信息xx公司   广州分公司       阿蜜果       肖xx       yy…   北京分公司       zz1       zz2       zz3… | 所选择用户：阿蜜果[所属角色] [所属组] [用户权限] [总权限]                                 [保存] [取消] |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

通过对已具有的权限取消勾选，或为某权限添加勾选，来修改用户的权限信息，点击“保存”按钮保存修改信息。

4.3.5用户管理

​       当选择了某用户时，点击右键，弹出菜单列表：修改、删除、取消，点击修改和删除按钮可以实现用户的删除和修改功能。

​       选择某个组织，例如下表中的“广州分公司”，弹出菜单列表：添加子组织、删除组织、修改组织、添加用户、取消，点击添加用户按钮可以实现用户的添加功能。

| 用户权限信息xx公司   广州分公司       阿蜜果       肖xx       yy…   北京分公司       zz1       zz2       zz3… | 所选择用户：阿蜜果[所属角色] [所属组] [用户权限] [总权限][修改]角色ID   角色名称   角色描述1          访客       --   2         初级用户    --… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

4.3.6组织管理

​       选择某个组织，例如下表中的“广州分公司”，弹出菜单列表：添加子组织、删除组织、修改组织、添加用户、取消，点击添加子组织、删除组织、修改组织按钮可以实现组织的添加、删除和修改功能。

| 用户权限信息xx公司   广州分公司       阿蜜果       肖xx       yy…   北京分公司       zz1       zz2       zz3… | 所选择用户：阿蜜果[所属角色] [所属组] [用户权限] [总权限][修改]角色ID   角色名称   角色描述1          访客       --   2         初级用户    --… |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
|                                                              |                                                              |

4.4 操作日志管理

4.4.1查询操作日志

操作名称：|________|  操作人：|________|操作时间从 |________| 到 |________| [查询] [重置] [删除]编号    操作名称    操作内容    操作人    操作时间1        xx1         --        Amigo    2007-10-82        xx2         --        xxyy     2007-10-8…

输入上图表单中的查询信息后，点击“查询”按钮，可查询出符合条件的信息。

4.4.2删除操作日志

操作名称：|________| 操作人：|________|操作时间从 |________| 到 |________| [查询] [重置] [删除]编号    操作名称    操作内容    操作人    操作时间1        xx1       --           Amigo      2007-10-82        xx2       --           xxyy       2007-10-8…

输入上图表单中的查询信息后，点击“查询”按钮，可查询出符合条件的信息。而后点击“删除”按钮，可删除符合查询条件的操作日志。 
