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

-- 用户
create table bulk_user
(
    user_id     bigint      not null auto_increment comment '用户ID,自动生成',
    username    varchar(32) not null comment '用户名',
    password    varchar(32) not null comment '密码',
    email       varchar(64) comment '邮箱账号',
    phone       varchar(20) comment '电话号码',
    avatar      varchar(1024) comment '头像地址',
    address     varchar(128) comment '住址',
    create_time timestamp   not null default current_timestamp comment '创建时间',
    modify_time timestamp   not null default current_timestamp on update current_timestamp comment '修改时间',
    primary key (user_id),
    unique key (username),
    unique key (email)
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