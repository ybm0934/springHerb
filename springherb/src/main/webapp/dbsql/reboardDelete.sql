--reboardDelete.sql
create or replace procedure reboardDelete --프로시저 이름 
(
  --매개변수
  p_no  number,
  p_groupNo number,
  p_step    number  
)
is
--변수선언부
    cnt number;
begin
--처리할 내용
    --원본글인 경우
    if p_step=0 then
        select count(*) into cnt from reboard
        where groupno=p_groupno;
        
        --[1] 답변달린 원본글인 경우
        if cnt>1 then
            update reboard
            set delflag='Y'
            where no=p_no;
        else
        --[2] 답변없는 원본글인 경우
            delete from reboard where no=p_no;        
        end if;
    else  --답변글인 경우 - delete
        delete from reboard where no=p_no;
    end if;
    
    commit;

EXCEPTION
    WHEN OTHERS THEN
        raise_application_error(-20001, ' 실패!');
        ROLLBACK;
end;

/*
프로시저 실행
exec reboardDelete(11,11,0);
*/