--[1] ī�װ�
--drop table Category cascade constraint;

CREATE TABLE Category
(
    categoryNo		number	NOT NULL Primary Key, --ī�װ� �Ϸù�ȣ
    categoryName		VarChar2(50),                 --ī�װ���
    categoryOrder		number	Default 0             --ī�װ��������¼���
);

--drop sequence category_seq;
create sequence category_seq
increment by 1
start with 1
nocache;

Insert Into Category Values(category_seq.nextval, '�����', 0);
Insert Into Category Values(category_seq.nextval,'�Ʒθ�����', 1);
Insert Into Category Values(category_seq.nextval,'�������', 2);


/*Insert Into Category Values(1, '�����', 0);
Insert Into Category Values(2,'�Ʒθ�����', 1);
Insert Into Category Values(3,'�������', 2);
*/

Select * From Category Order By CategoryNo Desc;


--[2] ��ǰ
--drop TABLE Products cascade constraint;
CREATE TABLE Products
(
  productNo	number	NOT NULL	Primary Key,	--��ǰ��ȣ
  categoryNo	number	references category(categoryNo) NOT NULL,	--ī�װ���ȣ
  productName	VarChar2(50),                           --��ǰ��
  sellPrice		number Null,                            --�ǸŰ�
  company		VarChar2(50),                           --����ȸ��
  --eventName	VarChar2(50) default 'GEN'  Null,
	--�Ż�ǰ(NEW), ����Ʈ(BEST), MD��õ(MD), �Ϲ�(GEN), ��������(NONE)
  imageURL		VarChar2(50),                           --��ǰ�̹���
  explains		VarChar2(400),                          --��༳��
  description	varchar2(4000) Null,                    --�󼼼���
  regDate		Date Default sysdate,			--��ǰ�����
  mileage		number Default 0                            --���ϸ���(������)
);

--drop sequence products_seq;
create sequence products_seq
increment by 1
start with 1
nocache;


insert into products(ProductNo, categoryNo, productname, sellprice, company,
imageurl, explains, description, Mileage)
values(products_seq.nextval, 1, '�󺥴�',  7000, '��곪��', 'lavender.jpg','������ : ����
���� : �󺥴� 99%, �����
', '��Ư�� ���� �� ������ ���θ��ô���� ��� �� ���̽Ĺ��� �θ� �̿�Ǿ�� ��� �θ��ε��� �󺥴� ���̳� ���� ��幰�� ��� �ַ� �̿� ���丮, �, ����丮, �ҽ� � �־� �Ա⵵ �ϸ� �󺥴� ������ ȭ��ǰ, ���, ������ε� �̿�ȴ�.
', 70);

insert into products(ProductNo, categoryNo, productname, sellprice, company,
imageurl, explains, description, Mileage)
values(products_seq.nextval,1, '�����',  8000, '��곪��', 'rosemary.jpg','������ : ����
���� : �����99%, ĳ����', '��ƾ���� �ٴ��� �̽��̶�� ������, ������ ������,�׸�����,����Ʈ��,�θ��ο��� �������� ���� ���� �Ǿ� ������ ��� �丮�� ���� ���δ�.
', 80);

insert into products(ProductNo, categoryNo, productname, sellprice, company,
imageurl, explains, description, Mileage)
values(products_seq.nextval,1, '�𽺹�', 9000, '��곪��', 'jasmine.jpg','������ : ����
���� : �𽺹� 99%, ĳ����',
'�ڽ����� ���̷Ӱ� �̱����� ���� ���� ���� �ɸ��δ� "���������"�̶� ���� �� ���� ������, ������ ������ ������ ��¡���� �Ҹ����Դ�. �������� �߱������� �� �� ���� �̿�Ǿ� ������, ���� �����Ͽ� ���� ���Ŵ�.
',90);


insert into products(ProductNo, categoryNo, productname, sellprice, company,
imageurl, explains, description, Mileage)
values(products_seq.nextval,1, '�󺥴������',  7000, '��곪��', 'lavenderherb.jpg','������ : ����
���� : �󺥴� 99%, �����
', '��Ư�� ���� �� ������ ���θ��ô���� ��� �� ���̽Ĺ��� �θ� �̿�Ǿ�� ��� �θ��ε��� �󺥴� ���̳� ���� ��幰�� ��� �ַ� �̿� ���丮, �, ����丮, �ҽ� � �־� �Ա⵵ �ϸ� �󺥴� ������ ȭ��ǰ, ���, ������ε� �̿�ȴ�.
', 70);

insert into products(ProductNo, categoryNo, productname, sellprice, company,
imageurl, explains, description, Mileage)
values(products_seq.nextval,1, '����������',  8000, '��곪��', 'rosemaryherb.jpg','������ : ����
���� : �����99%, ĳ����', '��ƾ���� �ٴ��� �̽��̶�� ������, ������ ������,�׸�����,����Ʈ��,�θ��ο��� �������� ���� ���� �Ǿ� ������ ��� �丮�� ���� ���δ�.
', 80);

insert into products(ProductNo, categoryNo, productname, sellprice, company,
imageurl, explains, description, Mileage)
values(products_seq.nextval,1, '�𽺹������',  9000, '��곪��', 'jasmineherb.jpg','������ : ����
���� : �𽺹� 99%, ĳ����',
'�ڽ����� ���̷Ӱ� �̱����� ���� ���� ���� �ɸ��δ� "���������"�̶� ���� �� ���� ������, ������ ������ ������ ��¡���� �Ҹ����Դ�. �������� �߱������� �� �� ���� �̿�Ǿ� ������, ���� �����Ͽ� ���� ���Ŵ�.
',90);


--[3] �̺�Ʈ ��ǰ ��� ���̺�
--drop table eventproduct cascade constraint;
create table eventproduct(
  eventProductNo	number primary key, --�⺻Ű, �Ϸù�ȣ
  productNo	number references products(productNo) on delete cascade not null, --��ǰ��ȣ
  eventName	VarChar2(30) --NEW, BEST, MD
);

--drop sequence eventproduct_seq;
create sequence eventproduct_seq
increment by 1
start with 1
nocache;
 
insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 2, 'NEW');
insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 3, 'NEW');
insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 4, 'NEW');
insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 5, 'NEW');
insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 6, 'NEW');

 
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 1, 'BEST');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 2, 'BEST');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 3, 'BEST');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 4, 'BEST');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 5, 'BEST');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 6, 'BEST');


 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 3, 'MD');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 4, 'MD');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 5, 'MD');
 insert into eventproduct(eventProductNo, productNo, eventName)
values(eventproduct_seq.nextval, 6, 'MD');


commit;

Select * From Products;
Select * From eventproduct;
Select * From Category;

select * from products p, eventproduct e
where p.productNo = e.productNo
and e.eventName = 'MD';

--------------------------------
--�� ����
create or replace view productEventView
  as
  select p.*, e.eventName
     from products p, eventProduct e
     where p.productno=e.productno;
     
  select * from productEventView
  where eventName='NEW';
