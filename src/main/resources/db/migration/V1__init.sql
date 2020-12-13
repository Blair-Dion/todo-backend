drop table if exists board;

drop table if exists card;

drop table if exists list;

drop table if exists log;

drop table if exists user;

create table board
(
    id               bigint auto_increment primary key,
    name             varchar(255),
    created_datetime datetime(6),
    created_by       bigint,
    updated_datetime datetime(6),
    updated_by       bigint
) engine = InnoDB;

create table card
(
    id                bigint auto_increment primary key,
    title             varchar(255),
    contents          varchar(500),
    pos               integer not null,
    is_archived       bit     not null,
    archived_datetime datetime(6),
    list_id           bigint,
    user_id           bigint,
    created_datetime  datetime(6),
    created_by        bigint,
    updated_datetime  datetime(6),
    updated_by        bigint
) engine = InnoDB;

create table list
(
    id                bigint auto_increment primary key,
    name              varchar(255),
    is_archived       bit not null,
    archived_datetime datetime(6),
    board_id          bigint,
    created_datetime  datetime(6),
    created_by        bigint,
    updated_datetime  datetime(6),
    updated_by        bigint
) engine = InnoDB;

create table log
(
    id               bigint auto_increment primary key,
    type             varchar(255),
    before_title     varchar(255),
    after_title      varchar(255),
    before_contents  varchar(255),
    after_contents   varchar(255),
    from_list_id     bigint,
    to_list_id       bigint,
    board_id         bigint,
    created_datetime datetime(6),
    created_by       bigint,
    updated_datetime datetime(6),
    updated_by       bigint
) engine = InnoDB;

create table user
(
    id                bigint auto_increment primary key,
    user_id           varchar(255),
    user_nickname     varchar(255),
    profile_image_url varchar(255),
    github_token      varchar(255),
    created_datetime  datetime(6),
    updated_datetime  datetime(6)
) engine = InnoDB;

alter table card
    add constraint card_ref_list
        foreign key (list_id)
            references list (id);

alter table card
    add constraint card_ref_user
        foreign key (user_id)
            references user (id);

alter table list
    add constraint list_ref_board
        foreign key (board_id)
            references board (id);

alter table log
    add constraint log_ref_board
        foreign key (board_id)
            references board (id);
