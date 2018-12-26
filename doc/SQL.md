#会员
```sql
-- 用户信息

create table `t_user`(
  `id` VARCHAR(64) NOT NULL,
  `username` VARCHAR(32)   COMMENT '用户名',
  `password` VARCHAR(32)  COMMENT '密码',
  `email`  VARCHAR(32) COMMENT '邮箱',
  `cell_phone`  VARCHAR(32) COMMENT '电话',
  `question`  VARCHAR(32) COMMENT '问题',
  `answer`  VARCHAR(32) COMMENT '答案',
  `role`  VARCHAR(32) COMMENT '角色',
  `invite_code` VARCHAR(32) COMMENT '邀请码',
  `leader_id`  VARCHAR(64) COMMENT '上级id',
  `total_account` DECIMAL (7,2) DEFAULT 0 COMMENT '消费总金额',
  `total_rebate` DECIMAL (7,2) DEFAULT 0 COMMENT '消费总返利',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
      PRIMARY KEY (`id`)
);
   
  


   
-- 会员消费记录表
CREATE TABLE  `t_purchase_history`(
    `id` VARCHAR(64) NOT NULL,
    `user_id` VARCHAR (64) NOT NULL COMMENT '消费者ID',
    `account` DECIMAL (7,2) COMMENT '消费金额',
    `rebate` DECIMAL (7,2) COMMENT '消费返利',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
        PRIMARY KEY (`id`)
  );
 