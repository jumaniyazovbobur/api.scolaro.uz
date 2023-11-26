----------------------- get application level status list by step level id

SELECT '[' || string_agg(jsonTable.body,',') || ']' as data
from( select json_build_object( 'id',ls.id,
                                'applicationStepLevelStatus', ls.application_step_level_status,
                                'createdDate',ls.created_date,
                                'description',ls.description,
                                'deadline',ls.deadline)::TEXT as body
      from app_application_level_status as ls where
              ls.consulting_step_level_id = '402880e48ba33c9b018ba33da92b0001'
                                                and visible = true) as jsonTable

-----
select get_application_level_status_list_by_step_level_id('402880e48ba33c9b018ba33da92b0001')

----- function

CREATE OR REPLACE FUNCTION get_application_level_status_list_by_step_level_id (in_consulting_step_level_id varchar)
    returns text
    language plpgsql
AS $$
declare
response text;
begin
SELECT string_agg(jsonTable.body,',') into response
from( select json_build_object( 'id',ls.id,
                                'applicationStepLevelStatus', ls.application_step_level_status,
                                'createdDate',ls.created_date,
                                'description',ls.description,
                                'deadline',ls.deadline)::TEXT as body
      from app_application_level_status as ls where
              ls.consulting_step_level_id = in_consulting_step_level_id
                                                and visible = true) as jsonTable;
if response is null THEN
  	response = '[]';
else
  	response = '[' || response || ']';
END IF;

return response;
end;$$

--------------------------- get  university list with consulting
select c.id,
       case when :lang = 'uz' then name_uz when :lang = 'en' then name_en else name_ru end as nama,
       (select json_agg(temp_t1)
        from (select u.id, u.name, cu.tariff_id
              from university u
                       left join consulting_university cu on u.id = cu.university_id
              where (cu.visible = true or cu is null)
                and u.visible = true
                and (consulting_id = '8080b7ef8ba46236018bae4b30940021' or consulting_id is null)
                and u.country_id = c.id)
                 as temp_t1)
from country as c
where c.visible = true;
-----
(select json_agg(temp_t1)
 from (select u.id, u.name, cu.tariff_id
       from university u
                left join consulting_university cu on u.id = cu.university_id
       where (cu.visible = true or cu is null)
         and u.visible = true
         and (consulting_id = '8080b7ef8ba46236018bae4b30940021' or consulting_id is null))
          as temp_t1);

----------------------- get application level attach list by step level id

SELECT  string_agg(jsonTable.body, ',') as data
from (select json_build_object('id',la.id,
                               'createdDate',la.created_date,
                               'attachId',la.attach_id,
                               'attachType',la.attach_type,
                               'consultingStepLevelId',la.consulting_step_level_id,
                               'extension',a.extension,
                               'origenName',a.origen_name)::TEXT  as body
      from app_application_level_attach as la
               inner join attach a on a.id = la.attach_id
      where consulting_step_level_id = '402880e48ba33c9b018ba33da92b0001') as jsonTable

-----
select get_application_level_attach_list_by_step_level_id('402880e48ba33c9b018ba33da92b0001')

----- function
CREATE OR REPLACE FUNCTION get_application_level_attach_list_by_step_level_id (in_consulting_step_level_id varchar)
    returns text
    language plpgsql
AS $$
declare
response text;
begin
SELECT  string_agg(jsonTable.body, ',') into response
from (select json_build_object('id',la.id,
                               'createdDate',la.created_date,
                               'attachId',la.attach_id,
                               'attachType',la.attach_type,
                               'consultingStepLevelId',la.consulting_step_level_id,
                               'extension',a.extension,
                               'origenName',a.origen_name)::TEXT  as body
      from app_application_level_attach as la
               inner join attach a on a.id = la.attach_id
      where consulting_step_level_id = in_consulting_step_level_id) as jsonTable;

if response is null THEN
  	response = '[]';
else
  	response = '[' || response || ']';
END IF;

return response;
end;$$


-------------------------- get faculty university count recursively
--- function
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
from (select count(*) from university_faculty where faculty_id = p_faculty_id) as temp_t;

if parent_f_u_count is null then
        parent_f_u_count = 0;
end if;

select sum(temp_t.univeristy_count)  into child_f_u_sum
from (select get_faculty_university_count(id) as univeristy_count
      from faculty
      where parent_id = p_faculty_id) as temp_t;

if child_f_u_sum is null then
        child_f_u_sum = 0;
end if;

return parent_f_u_count + child_f_u_sum;
end;
$$;

--- call
select get_faculty_university_count('402880ea8bf4eea3018bf4eec6960000');


----------------------- get faculty tree recursively

----- function
CREATE OR REPLACE FUNCTION get_sub_faculty(parent_faculty_id varchar, lang varchar)
    returns varchar
    language plpgsql
AS
$$
declare
result_json varchar;
begin
select '[' || string_agg(temp_table.body, ',') || ']'
into result_json
from (select json_build_object('id', f.id,
                               'name', CASE lang WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END,
                               'orderNumber', f.order_number,
                               'parentId', f.parent_id,
                               'subFaculty', get_sub_faculty(f.id),
                               'universityCount', get_faculty_university_count(f.id))::text as body
      from faculty as f
      where parent_id = parent_faculty_id) as temp_table;
return result_json;
end;
$$;

----- query
select f.id,
       CASE 'uz' WHEN 'uz' THEN f.name_uz WHEN 'en' THEN f.name_en else f.name_ru END as name,
       f.order_number,
       get_sub_faculty(f.id,'en') as subFaculty,
       get_faculty_university_count(f.id) as universityCount
from faculty as f
where parent_id isnull;