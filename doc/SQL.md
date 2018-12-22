#会员
```sql
-- 用户信息

create table `t_user`(
  `id` varchar(64) not null,
  `username` varchar(32)   comment '用户名',
  `password` varchar(32)  comment '密码',
  `email`  varchar(32) comment '邮箱',
  `cell_phone`  varchar(32) comment '电话',
  `question`  varchar(32) comment '问题',
  `answer`  varchar(32) comment '答案',
  `role`  varchar(32) comment '角色',
  `invite_code` varchar(32) comment '邀请码',
  `leader_id`  varchar(64) comment '上级id',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
      primary key (`id`)
);
   
  
 