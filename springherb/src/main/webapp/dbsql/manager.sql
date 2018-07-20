--���� ���̺�
--drop table authority  cascade constraint;
create table authority
(
	authCode           varchar2(30)  primary key ,  --�����ڵ�
	authName          varchar2(100)  not null , --���Ѹ�
	authDesc           varchar2(200)  null , --���Ѽ���
    authLevel       number(3),   --���� ����  --1(ADMIN), 2(ASSISTANT), 3(STAFF)     
	regdate		date  default sysdate
);

--INSERT INTO authority VALUES ('USER','�Ϲ� �����'  , '', 0, SYSDATE);
INSERT INTO authority VALUES ('ADMIN','������', 'manager',1, SYSDATE);
INSERT INTO authority VALUES ('ASSISTANT','�ΰ�����', 'assistant manager',2, SYSDATE);
INSERT INTO authority VALUES ('STAFF','������', 'staff',3, SYSDATE);

--drop table managers   cascade constraint;
create table managers
(
    no        number        primary key,
    userid     varchar2(20)   unique not null,
    name        varchar2(20)    not null,
    pwd        varchar2(15)    not null,
    authCode	 varchar2(30)  not null,  --�����ڵ� ADMIN, ASSISTANT,STAFF
    regdate     date        default sysdate,
    constraint managers_fk1 foreign key(authCode) references authority(authCode)
);

--drop sequence managers_seq;
create sequence managers_seq
increment by 1
start with 1
nocache;

INSERT INTO managers VALUES (managers_seq.nextval,'admin','�����','1', 
'ADMIN', SYSDATE);

select * from managers;

--

select * from  authority;
