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
                               'subFaculty', get_sub_faculty(f.id,lang),
                               'universityCount', get_faculty_university_count(f.id))::text as body
      from faculty as f
      where parent_id = parent_faculty_id) as temp_table;
return result_json;
end;
$$;