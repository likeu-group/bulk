-- ============================= RBAC START (RBAC0) ============================= --
-- ---------------
-- 用户表
-- ---------------
create table bulk_user
(
    user_id     bigint      not null auto_increment comment '用户ID,自动生成',
    username    varchar(32) not null comment '用户名',
    password    varchar(32) not null comment '密码',
    sex         tinyint     not null comment '性别',
    status      tinyint     not null default 0 comment '用户状态',
    email       varchar(64)          default null comment '邮箱账号',
    phone       varchar(20)          default null comment '电话号码',
    avatar      varchar(1024)        default null comment '头像地址',
    address     varchar(128)         default null comment '住址',
    create_time timestamp   not null default current_timestamp comment '创建时间',
    modify_time timestamp   not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (user_id),
    unique key (username),
    unique key (email)
) engine = innodb
  default charset = utf8mb4 comment '用户表';

-- ---------------
-- 角色表
-- ---------------
create table bulk_role
(
    role_id       bigint       not null auto_increment comment '角色ID',
    role_name     varchar(32)  not null comment '角色名称:别名',
    role_entity   varchar(32)  not null comment '角色实体',
    role_describe varchar(128) not null comment '角色控制项描述',
    create_time   timestamp    not null default current_timestamp comment '创建时间',
    modify_time   timestamp    not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (role_id),
    unique key (role_entity)
) engine = innodb
  default charset = utf8mb4 comment '角色表 RBAC0';

-- ---------------
-- 用户与角色映射表
-- ---------------
create table bulk_user_role_relation
(
    id          bigint    not null auto_increment comment '用户与角色映射ID',
    user_id     bigint    not null comment '用户ID',
    role_id     bigint    not null comment '角色ID',
    create_time timestamp not null default current_timestamp comment '创建时间',
    modify_time timestamp not null default current_timestamp on update current_timestamp comment '创建时间',
    primary key (id),
    unique key (user_id, role_id)
) engine = innodb
  default charset = utf8mb4 comment '角色与权限关系映射表';

-- ---------------
-- 权限表
-- ---------------
create table bulk_permission
(
    perms_id       bigint       not null auto_increment comment '权限ID',
    perms_name     varchar(32)  not null comment '',
    perms_entity   varchar(32)  not null comment '',
    perms_describe varchar(128) not null comment '',
    create_time    timestamp    not null default current_timestamp comment '创建时间',
    modify_time    timestamp    not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (perms_id),
    unique key (perms_entity)
) engine = innodb
  default charset = utf8mb4 comment '权限表';

-- ---------------
-- 角色与权限映射表
-- ---------------
create table bulk_role_perms_relation
(
    relation_id bigint    not null auto_increment comment 'ID',
    role_id     bigint    not null comment '角色ID',
    perms_id    bigint    not null comment '权限ID',
    create_time timestamp not null default current_timestamp comment '创建时间',
    modify_time timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (relation_id),
    unique key (role_id, perms_id)
) engine = innodb
  default charset = utf8mb4 comment '角色权限关联表';

-- ---------------
-- 菜单表
-- ---------------
create table bulk_menu
(
    menu_id     bigint      not null auto_increment comment '菜单ID',
    paren_id    bigint               default null comment '父级菜单ID',
    menu_icon   varchar(128)         default null comment '菜单图标',
    menu_name   varchar(64) not null comment '菜单名称',
    menu_url    varchar(128)         default null comment '菜单路径',
    menu_type   int(11)              default null comment '菜单类型 0:菜单，1:按钮',
    menu_order  int(11)              default null comment '菜单排序',
    menu_status int(11)              default null comment '是否禁用 0:正常，1:禁用',
    menu_method varchar(128)         default null comment '菜单http请求方法, 如: GET, POST',
    create_time timestamp   not null default current_timestamp comment '创建时间',
    modify_time timestamp   not null default current_timestamp on update current_timestamp comment '创建时间',
    primary key (menu_id)
) engine = innodb
  default charset = utf8mb4 comment '菜单表';

-- ---------------
-- 角色与菜单关联表
-- ---------------
create table bulk_role_menu_relation
(
    id          bigint    not null auto_increment comment '关联表ID',
    role_id     bigint    not null comment '角色ID',
    menu_id     bigint    not null comment '菜单表ID',
    create_time timestamp not null default current_timestamp comment '创建时间',
    modify_time timestamp not null default current_timestamp on update current_timestamp comment '创建时间',
    primary key (id),
    unique key (role_id, menu_id)
) engine = innodb
  default charset = utf8mb4 comment '角色与菜单关联表';
-- ============================= RBAC END ============================= --

-- 品牌
create table bulk_brand
(
    brand_id    bigint      not null auto_increment comment '品牌ID',
    brand_name  varchar(50) not null comment '品牌名称',
    create_time timestamp   not null default current_timestamp comment '创建时间',
    modify_time timestamp   not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (brand_id),
    unique key (brand_name)
) engine = innodb
  default charset = utf8 comment '商品品牌表';

-- 类别
create table bulk_category
(
    category_id   bigint      not null auto_increment comment '类别ID',
    category_name varchar(50) not null comment '类别名称',
    create_time   timestamp   not null default current_timestamp comment '创建时间',
    modify_time   timestamp   not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (category_id),
    unique key (category_name)
) engine = innodb
  default charset = utf8 comment '商品类别表';

-- 品牌与类别多对多的关系表
create table bulk_brand_category
(
    brand_category_id bigint not null auto_increment comment '关系表ID',
    brand_id          bigint not null comment '品牌ID',
    category_id       bigint not null comment '类别ID',
    primary key (brand_category_id),
    unique key (brand_id, category_id)
) engine = innodb
  default charset = utf8 comment '品牌与类别多对多的关系表';

-- 商品信息
create table bulk_commodity
(
    commodity_id   bigint       not null auto_increment comment '商品ID',
    commodity_name varchar(50)  not null comment '商品名称',
    commodity_desc varchar(256) not null comment '商品描述',
    purchase_price float        not null comment '采购价格',
    stock          int          not null comment '库存',
    image_url      varchar(256) comment '图片地址，用于展示商品的图片',
    brand_id       bigint       not null comment '关联品牌',
    category_id    bigint       not null comment '关联类别',
    create_time    timestamp    not null default current_timestamp comment '创建时间',
    modify_time    timestamp    not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (commodity_id),
    unique key (commodity_name, brand_id)
) engine = innodb
  default charset = utf8 comment '商品表';

-- 价格表
create table bulk_price
(
    price_id bigint not null auto_increment comment '价格ID',

);

-- 买家
create table buyer
(

);


-- 成本
create table cost
(

);

-- 订单
create table `order`
(

);

-- TODO 图片
create table bulk_images
(
    image_id   bigint comment '图片ID',
    image_type varchar(10) comment '图片类型',
    image_data mediumtext comment '数据',
    image_name varchar(64) comment '图片名称'
)
