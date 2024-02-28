CREATE
OR REPLACE FUNCTION get_consulting_dashboard_data (in_consulting_id varchar)
    returns text
    language plpgsql
AS $$
declare
student_count bigint;
    total_count bigint;
    trail_count bigint;
    started_count bigint;
    finished_count bigint;
    cancelled_count bigint;
    response text;
begin
    -- student count
select count(*) into student_count from app_application where consulting_id = in_consulting_id
group by student_id;
-- consulting count
select count(*) into total_count from app_application where consulting_id = in_consulting_id;
-- application count by status
select
    sum( CASE WHEN status = 'TRAIL'  THEN 1 else 0 END ) as trail_count_c,
    sum( CASE WHEN status = 'STARTED'  THEN 1 else 0 END ) as started_count_c,
    sum( CASE WHEN status = 'FINISHED'  THEN 1 else 0 END ) as finished_count_c,
    sum( CASE  WHEN status = 'CANCELED'  THEN 1 else 0  END ) as cancelled_count_c
into trail_count, started_count, finished_count, cancelled_count
from app_application where visible  = true and consulting_id = in_consulting_id;
-- return result
response = '{ "studentCount":' || student_count || ',' ||
               '"total_count":' || total_count || ',' ||
               '"trailCount":' || trail_count || ',' ||
               '"startedCount":' || started_count || ',' ||
               '"finishedCount":' || finished_count || ',' ||
               '"cancelledCount":' || cancelled_count || ','
               '}';
return response;
end;$$