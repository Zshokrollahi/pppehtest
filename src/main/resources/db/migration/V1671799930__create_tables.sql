create table if not exists admins
(
    id            bigserial
        primary key,
    enabled       boolean,
    first_name    varchar(52) not null,
    last_name     varchar(52) not null,
    national_code varchar(10),
    password      varchar(72) not null,
    username      varchar(52) not null
        unique,
    phone_number  varchar(11) not null,
    created_at    timestamp,
    created_by    bigint
        references admins
);

create table if not exists otps
(
    id           bigserial
        primary key,
    phone_number varchar(11)                     not null,
    send_at      timestamp                       not null,
    expired_at   timestamp                       not null,
    realm        text                            not null,
    kind         text                            not null,
    consumed     boolean default false           not null,
    password     text    default 'not set'::text not null,
    unique (phone_number, realm, kind)
);

create table if not exists files
(
    id          bigserial
        primary key,
    object_name text        not null,
    bucket_name text        not null,
    file_name   text        not null,
    size        text,
    media_type  varchar(24) not null,
    uploaded_by bigint
        references admins,
    uploaded_at timestamp,
    unique (object_name, bucket_name)
);

alter table if exists admins
    add column if not exists avatar_id bigint references files;

create index if not exists index_admins_avatar_id on admins (avatar_id);

create table if not exists roles
(
    id    uuid default gen_random_uuid() not null
        primary key,
    name  text                           not null,
    realm text                           not null,
    title varchar(200),
    unique (name, realm)
);

create table if not exists permissions
(
    id        uuid default gen_random_uuid() not null
        primary key,
    name      text                           not null,
    realm     text                           not null,
    title     varchar(200),
    tag_name  varchar(200)                   not null,
    tag_title varchar(200),
    unique
        (name, realm)
);

create index if not exists index_permissions_tag_name on permissions (tag_name);

create table if not exists roles_permissions_mapping
(
    role_id       uuid not null
        references roles,
    permission_id uuid not null
        references permissions,
    primary key (role_id, permission_id)
);

create table if not exists admins_roles_mapping
(
    admin_id bigint not null
        references admins,
    role_id  uuid   not null
        references roles,
    primary key (admin_id, role_id)
);

create table if not exists categories
(
    id         bigserial
        primary key,
    slug       text unique,
    title      varchar(350) not null,
    is_root    boolean default true,
    created_at timestamp,
    created_by bigint
        references admins,
    parent_id  bigint
        references categories,
    icon       bigint references files
);


create table if not exists provinces
(
    id   bigserial
        primary key,
    name varchar(72) not null,
    code integer default '-1'::integer
);

create table if not exists adjacent_provinces
(
    province_id          bigint not null
        constraint province_fk
            references provinces,
    adjacent_province_id bigint not null
        references provinces,
    primary key (province_id, adjacent_province_id)
);

create table if not exists cities
(
    id           bigserial
        primary key,
    name         varchar(72),
    province_id  bigint not null
        references provinces,
    code         integer default '-1'::integer,
    thumbnail_id bigint references files
);

create table if not exists users
(
    id            bigserial
        primary key,
    first_name    varchar(52),
    last_name     varchar(52),
    phone_number  varchar(11)                               not null
        unique,
    username      varchar(72) default ''::character varying not null
        unique,
    password      varchar(72) default ''::character varying not null,
    signup_at     timestamp,
    enabled       boolean     default true,
    national_code varchar(10)
        unique,
    city_id       bigint
        constraint users_cities_fk
            references cities
);

create table if not exists tour_leaders
(
    id            bigserial
        primary key,
    first_name    varchar(52),
    last_name     varchar(52),
    phone_number  varchar(11)                               not null
        unique,
    username      varchar(72) default ''::character varying not null
        unique,
    password      varchar(72) default ''::character varying not null,
    signup_at     timestamp,
    created_by    bigint references admins,
    enabled       boolean     default true,
    national_code varchar(10)
        unique,
    city_id       bigint
        constraint users_cities_fk
            references cities,
    avatar_id     bigint references files
);

create table if not exists tour_classifications
(
    id          bigserial
        primary key,
    name        varchar(100) not null,
    slug        varchar(100) unique,
    description text         not null,
    created_at  timestamp,
    icon        bigint references files,
    created_by  bigint references admins
);

create table if not exists contacts_us
(
    id                bigserial primary key,
    title             varchar(200) not null,
    description       text         not null,
    first_name        varchar(72),
    last_name         varchar(72),
    email             varchar(300),
    phone_number      varchar(11),
    status            varchar(52)  not null check ( status in ('AWAITING_FOR_REVIEW', 'REVIEWED') ),
    answer            text         not null,
    send_at           timestamp,
    changed_status_at timestamp,
    changed_status_by bigint references admins
);

create table if not exists tours
(
    id                     bigserial primary key,
    title                  varchar(500) not null,
    created_at             timestamp,
    created_by_admin       bigint references admins,
    created_by_tour_leader bigint references tour_leaders,
    thumbnail_id           bigint references files,
    tour_classification_id bigint references tour_classifications
);

create table if not exists tour_services
(
    id      bigserial primary key,
    title   varchar(500) not null,
    kind    varchar(52)  not null check ( kind in ('POSITIVE', 'NEGATIVE', 'NEUTRAL') ),
    tour_id bigint references tours
);

create table if not exists tour_spatial_coordinates
(
    id                       bigserial primary key,
    origin_address_text      text,
    destination_address_text text,
    origin_city_id           bigint references cities,
    destination_city_id      bigint references provinces,
    origin_longitude         text,
    origin_latitude          text,
    destination_longitude    text,
    destination_latitude     text,
    tour_id                  bigint references tours
);

create table if not exists tour_periods
(
    id                  bigserial primary key,
    start_date          date    not null,
    end_date            date    not null,
    start_time          timestamp,
    end_time            timestamp,
    enabled             boolean not null default false,
    tour_id             bigint references tours,
    price               decimal not null,
    discounted_price    decimal,
    discount_activation boolean          default false,
    capacity            int     not null
);

--blog tables
create table if not exists posts
(
    id                  bigserial primary key,
    title               varchar(300) not null,
    short_description   varchar(800),
    cornerstone_content jsonb,
    type                varchar(300) not null,
    thumbnail_id        bigint references files,
    created_by          bigint references admins,
    created_at          timestamp,
    published           boolean default false,
    published_at        date         not null
);
create index index_posts_type on posts (type);
create index index_posts_published_published_at on posts (published, published_at);

create table if not exists post_seo_optimizations
(
    id               bigserial primary key,
    meta_title       text,
    meta_description text,
    meta_robots      text,
    meta_keywords    text,
    open_graph       jsonb,
    post_id          bigint references posts,
    created_by       bigint references admins,
    created_at       timestamp
);
alter table if exists posts
    add column if not exists post_seo_id bigint references post_seo_optimizations;
create index index_post_seo_optimizations_post_id on post_seo_optimizations (post_id);

create table if not exists post_visits
(
    id      uuid primary key,
    ip      text,
    time    timestamp,
    post_id bigint references posts
);
create index index_post_visits_post_id on post_seo_optimizations (post_id);

create table if not exists post_sections
(
    id         uuid primary key,
    priority   int   not null,
    content    jsonb not null,
    created_at timestamp,
    created_by bigint references admins,
    post_id    bigint references posts
);
create index index_post_sections_post_id on post_seo_optimizations (post_id);

create table if not exists tags
(
    id         bigserial primary key,
    name       varchar(500) not null unique,
    created_at timestamp,
    created_by bigint references admins
);

create table if not exists tags_posts
(
    post_id bigint not null references posts,
    tag_id  bigint not null references tags,
    primary key (post_id, tag_id)
);

create table if not exists post_categories
(
    id         bigserial
        primary key,
    title      varchar(350) not null,
    slug       text unique,
    root       boolean default true,
    created_at timestamp,
    created_by bigint references admins,
    parent_id  bigint
        references post_categories,
    icon_id    bigint references files,
    image_id   bigint references files
);
create index index_post_categories_parent_id on post_categories (parent_id);
create index index_post_categories_is_root on post_categories (root);

create table if not exists post_active_categories
(
    post_id          bigint references posts,
    post_category_id bigint references post_categories,
    primary key (post_id, post_category_id)
);

create table if not exists post_writers
(
    id         bigserial primary key,
    first_name varchar(52) not null,
    last_name  varchar(52) not null,
    phone_number varchar(11) not null unique ,
    signup_at timestamp,
    created_by bigint references admins,
    enabled boolean default true,
    national_code varchar(10) unique ,
    avatar_id bigint references files,
    bio text
);
