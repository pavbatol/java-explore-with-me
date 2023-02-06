create table if not exists users
(
    user_id     bigint generated by default as identity not null,
    user_name   varchar(255)                            not null,
    email       varchar(255)                            not null,
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

CREATE TABLE if not exists events
(
    event_id           BIGINT GENERATED BY DEFAULT AS IDENTITY   NOT NULL,
    annotation         VARCHAR(2000)                             NOT NULL,
    cat_id             BIGINT                                    NOT NULL,
    confirmed_requests BIGINT                      DEFAULT 0     NOT NULL,
    created_on         TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    description        VARCHAR(7000)                             NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE               NOT NULL,
    initiator_id       BIGINT                                    NOT NULL,
    latitude           DOUBLE PRECISION                          NOT NULL,
    longitude          DOUBLE PRECISION                          NOT NULL,
    paid               BOOLEAN                                   NOT NULL,
    participant_limit  INTEGER                     DEFAULT 0     NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                     DEFAULT TRUE  NOT NULL,
    state              VARCHAR(255)                              NOT NULL,
    title              VARCHAR(120)                              NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (event_id),
    CONSTRAINT FK_EVENTS_ON_CAT FOREIGN KEY (cat_id) REFERENCES categories (cat_id),
    CONSTRAINT FK_EVENTS_ON_INITIATOR FOREIGN KEY (initiator_id) REFERENCES users (user_id)
);

CREATE TABLE if not exists requests
(
    request_id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    request_date TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    user_id      BIGINT                                  NOT NULL,
    status       VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_requests PRIMARY KEY (request_id),
    CONSTRAINT FK_REQUESTS_ON_EVENT FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT FK_REQUESTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id)
);
