CREATE OR REPLACE FUNCTION get_university_sub_faculty(parent_faculty_id varchar, lang varchar, university_id_in bigint)
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
                               'isSelected', (select exists(select true from university_faculty
                                                            where university_id = university_id_in and faculty_id = f.id and visible = true )),
                               'subFaculty', get_university_sub_faculty(f.id,lang, university_id_in))::text as body
      from faculty as f
      where parent_id = parent_faculty_id and visible=true) as temp_table;
return result_json;
end;
$$;