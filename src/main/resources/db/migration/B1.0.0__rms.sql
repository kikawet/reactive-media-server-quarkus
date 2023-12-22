
create table USERS (
    login varchar(255) not null,
    primary key (login)
);

create table UserView (
    timestamp timestamp(6) not null,
    completionPercentage float4,
    source varchar(255) check (source in ('User','System')),
    user_login varchar(255) not null,
    video_title varchar(255) not null,
    primary key (timestamp, user_login, video_title)
);

create table Video (
    title varchar(255) not null,
    duration numeric(21,0),
    isPrivate boolean not null,
    url varchar(255),
    primary key (title)
);

alter table if exists UserView 
    add constraint FK_UserView_UserLogin 
    foreign key (user_login) 
    references USERS;

alter table if exists UserView 
    add constraint FK_UserView_VideoTitle 
    foreign key (video_title) 
    references Video;
