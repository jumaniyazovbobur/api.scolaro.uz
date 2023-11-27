CREATE OR REPLACE FUNCTION get_faculty_university_count(p_faculty_id varchar)
    returns integer
    language plpgsql
AS
$$
declare
parent_f_u_count integer;
    child_f_u_sum    integer;
begin
select sum(temp_t.count)
into parent_f_u_count
from (select count(*) from university_faculty where faculty_id = p_faculty_id and visible=true) as temp_t;

if parent_f_u_count is null then
        parent_f_u_count = 0;
end if;

select sum(temp_t.univeristy_count)  into child_f_u_sum
from (select get_faculty_university_count(id) as univeristy_count
      from faculty
      where parent_id = p_faculty_id and visible=true) as temp_t;

if child_f_u_sum is null then
        child_f_u_sum = 0;
end if;

return parent_f_u_count + child_f_u_sum;
end;
$$;
