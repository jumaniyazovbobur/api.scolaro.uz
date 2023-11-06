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
