CREATE OR REPLACE FUNCTION get_faculty_parent_list(parent_id_in varchar)
    returns varchar
    language plpgsql
AS
$$
declare
parentId varchar;
begin
select parent_id into parentId from faculty where id = parent_id_in;
if parentId is not null Then
        return parentId || ',' || get_faculty_parent_list(parentId);
end if;
return '';
end;
$$;