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
