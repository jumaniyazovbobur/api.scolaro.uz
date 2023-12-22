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
                                'deadline',ls.deadline,
                                'amount',ls.amount)::TEXT as body
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