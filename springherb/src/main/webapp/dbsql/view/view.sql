--view.sql

create or replace view eventProductView
as
select p.*, e.EVENTNAME
from products p join eventproduct e
on p.productno=e.productno;

-----
create or replace view CartView
as
select c.*, p.PRODUCTNAME, p.IMAGEURL, p.SELLPRICE
from cart c join products p
on c.productno=p.productno;

select * from cartview
where customerid='hong';

------
create or replace view orderDetailsView
as
select o.*, p.productname, p.imageurl,
p.sellprice 
from orderDetails o join products p
on o.productno=p.productno;


create or replace view ordersView
as
select o.*, m.name as sender
from orders o join member2 m
on o.customerid=m.userid;


select * from orderDetailsView
where orderno=8;

select * from ordersView
where orderno=8;










