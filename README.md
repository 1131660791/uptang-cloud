### 工程结构
```
uptang-cloud
├─uptang-cloud-api  -- WEB API / Feign 远程调用接口
│  ├─uptang-cloud-base-parent     -- 基础数据服务
│  │  ├─uptang-cloud-base         -- 基础数据服务 - Controller/Service/Repository
│  │  ├─uptang-cloud-base-common  -- 基础数据服务 - 公用代码(model/util/ ...)   
│  │  └─uptang-cloud-base-feign   -- 基础数据服务 - 远程调用接口契约
│  │                                          
│  └─uptang-cloud-sequence-parent    -- 全局序号
│      ├─uptang-cloud-sequence       -- 全局序号 - Controller/Util 
│      └─uptang-cloud-sequence-feign -- 全局序号 - 远程调用接口契约
│                                              
├─uptang-cloud-common  -- 稳定后不需要经常改动下面的代码
│  ├─uptang-cloud-bom             -- 依赖JAR文件版本的统一定义
│  ├─uptang-cloud-config          -- 主要用于管理Consul中的配置文件
│  ├─uptang-cloud-core            -- 全局工具类/异常定义
│  ├─uptang-cloud-pojo            -- 公共Pojo(微服务的pojo需要放模块下对包)
│  ├─uptang-cloud-starter-common  -- SpringBoot增强，会增加依赖 uptang-cloud-core/pojo
│  ├─uptang-cloud-starter-data    -- SpringBoot增强，会增加依赖 MySQL/Redis
│  ├─uptang-cloud-starter-task    -- SpringBoot增强，会增加依赖 Spring-Task
│  └─uptang-cloud-starter-web     -- SpringBoot增强，会增加依赖 Spring-Web
│                          
└─uptang-cloud-task  -- 批处理/离线统计
    └─uptang-cloud-task-paper-image -- 图片裁切，会依赖 uptang-cloud-base-common
```

### 代码结构说明
1. 对外提供服务的接口模块需要放在 uptang-cloud-api 下
2. 每个微服务下需要有目录 uptang-cloud-xxx-parent 目录
3. uptang-cloud-xxx-parent 目录下需要有 uptang-cloud-xxx 目录
4. uptang-cloud-xxx-common 可选，主要目的是为了工具或POJO可以独立被依赖，例如task-paper-image里有图片裁切的功能与接口的图片裁切是相同的逻辑，这样独立出来后代码就只有一份。
5. uptang-cloud-xxx-feign  可选，只有当微服务中的接口会被其它服务依赖调用，就需要把内部服务的契约定义出来。

### 代码运行方式
1. 运行环境分为 DEV / FAT / UAT / PRO
2. 所有启动配置信息保存在配置中心（Consul）, 命名规则为：模块名称 + '-' + profile + '.yml' 标识，例如： uptang-cloud-base-DEV.yml

### 数据库中业务表需要定义的5个字段
1. id bigint(20) UNSIGNED 主键,  // 单表时自增步长为1, 核心业务使用全局ID生成器
2. creator_id bigint(20) UNSIGNED DEFAULT NULL COMMENT '创建人',
3. created_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
4. modifier_id bigint(20) UNSIGNED DEFAULT NULL COMMENT '修改人',
5. modified_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'

### 数据库中状态类字段命名
1. [xxx_]state tinyint(1) tinyint(1) UNSIGNED NOT NULL COMMENT 'xxx状态'
