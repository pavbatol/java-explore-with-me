create table if not exists users
(
    user_id     bigint generated by default as identity not null,
    user_name   varchar(255)                            not null,
    email       varchar(255)                            not null,
    observable BOOLEAN                                  NOT NULL,
    constraint pk_users primary key (user_id),
    constraint uc_users_email unique (email)
);

create table if not exists categories
(
    cat_id   bigint generated by default as identity not null,
    cat_name varchar(255)                            not null,
    constraint pk_categories primary key (cat_id),
    constraint uc_categories_cat_name unique (cat_name)
);

create table if not exists events
(
    event_id           bigint generated by default as identity   not null,
    annotation         varchar(2000)                             not null,
    cat_id             bigint                                    not null,
    confirmed_requests bigint                      default 0     not null,
    created_on         timestamp without time zone default now() not null,
    description        varchar(7000)                             not null,
    event_date         timestamp without time zone               not null,
    initiator_id       bigint                                    not null,
    latitude           double precision                          not null,
    longitude          double precision                          not null,
    paid               boolean                                   not null,
    participant_limit  integer                     default 0     not null,
    published_on       timestamp without time zone,
    request_moderation boolean                     default true  not null,
    state              varchar(255)                              not null,
    title              varchar(120)                              not null,
    constraint pk_events primary key (event_id),
    constraint fk_events_on_cat foreign key (cat_id) references categories (cat_id),
    constraint fk_events_on_initiator foreign key (initiator_id) references users (user_id)
);

create table if not exists requests
(
    request_id   bigint generated by default as identity not null,
    request_date timestamp without time zone             not null,
    event_id     bigint                                  not null,
    user_id      bigint                                  not null,
    status       varchar(255)                            not null,
    constraint pk_requests primary key (request_id),
    constraint fk_requests_on_event foreign key (event_id) references events (event_id),
    constraint fk_requests_on_user foreign key (user_id) references users (user_id)
);

create table if not exists compilations
(
    comp_id bigint generated by default as identity not null,
    pinned  boolean                   default false not null,
    title   varchar(255)                            not null,
    constraint pk_compilations primary key (comp_id)
);

create table if not exists compilations_events
(
    comp_id  bigint not null,
    event_id bigint not null,
    constraint pk_compilations_events primary key (comp_id, event_id),
    constraint fk_comeve_on_compilation foreign key (comp_id) references compilations (comp_id),
    constraint fk_comeve_on_event foreign key (event_id) references events (event_id)
);

CREATE TABLE if not exists subscriptions
(
    sbr_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    owner_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_subscriptions PRIMARY KEY (sbr_id),
    CONSTRAINT FK_SUBSCRIPTIONS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (user_id)
);

CREATE TABLE if not exists subscriptions_favorites
(
    sbr_id BIGINT NOT NULL,
    fav_id BIGINT NOT NULL,
    CONSTRAINT pk_subscriptions_favorites PRIMARY KEY (sbr_id, fav_id),
    CONSTRAINT fk_subfav_on_subscription FOREIGN KEY (sbr_id) REFERENCES subscriptions (sbr_id),
    CONSTRAINT fk_subfav_on_user FOREIGN KEY (fav_id) REFERENCES users (user_id)
);


/*
CREATE TABLE if not exists subscriptions
(
    sbr_id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    subscriber_id BIGINT                                  NOT NULL,
    favorite_id   BIGINT                                  NOT NULL,
    state         VARCHAR(255),
    CONSTRAINT pk_subscriptions PRIMARY KEY (sbr_id),
    CONSTRAINT FK_SUBSCRIPTIONS_ON_FAVORITE FOREIGN KEY (favorite_id) REFERENCES users (user_id),
    CONSTRAINT FK_SUBSCRIPTIONS_ON_SUBSCRIBER FOREIGN KEY (subscriber_id) REFERENCES users (user_id)
);
*/
