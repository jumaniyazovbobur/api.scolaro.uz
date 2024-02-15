CREATE
OR REPLACE FUNCTION get_admin_dashboard_data ()
    returns text
    language plpgsql
AS $$
declare
student_count bigint;
    consulting_count bigint;
    trail_count bigint;
    started_count bigint;
    finished_count bigint;
    cancelled_count bigint;
    today_payment_count bigint;
    today_payment_amount bigint;
    month_payment_count bigint;
    month_payment_amount bigint;
    year_payment_count bigint;
    year_payment_amount bigint;
    total_payment_count bigint;
    total_payment_amount bigint;
    response text;
begin
    -- student count
select count(*) into student_count
from profile as p
         inner join person_role pr on pr.person_id = p.id
where role = 'ROLE_STUDENT' and p.visible = true and pr.visible=true;
-- consulting count
select count(*) into consulting_count from consulting where visible = true;
-- application count by status
select
    sum( CASE WHEN status = 'TRAIL'  THEN 1 else 0 END ) as trail_count_c,
    sum( CASE WHEN status = 'STARTED'  THEN 1 else 0 END ) as started_count_c,
    sum( CASE WHEN status = 'FINISHED'  THEN 1 else 0 END ) as finished_count_c,
    sum( CASE  WHEN status = 'CANCELED'  THEN 1 else 0  END ) as cancelled_count_c
into trail_count, started_count, finished_count, cancelled_count
from app_application where visible  = true;
-- payment today
select count(*), COALESCE (sum(amount),0) into  today_payment_count,today_payment_amount
from transactions where payment_type = 'PAYME' and DATE(created_date) = CURRENT_DATE;
-- payment this month
select count(*), COALESCE (sum(amount),0) into month_payment_count, month_payment_amount
from transactions where payment_type = 'PAYME'
                    and to_char(created_date, 'MONTH YYYY') = to_char(CURRENT_DATE, 'MONTH YYYY');
-- payment this year
select count(*), COALESCE (sum(amount),0) into year_payment_count,year_payment_amount
from transactions where payment_type = 'PAYME'
                    and to_char(created_date, 'YYYY') = to_char(CURRENT_DATE, 'YYYY');
-- payment total
select count(*), COALESCE (sum(amount),0) into total_payment_count, total_payment_amount
from transactions where payment_type = 'PAYME';
-- return result
response = '{ "studentCount":' || student_count || ',' ||
                  '"consultingCount":' || consulting_count || ',' ||
                  '"trailCount":' || trail_count || ',' ||
                  '"startedCount":' || started_count || ',' ||
                  '"finishedCount":' || finished_count || ',' ||
                  '"cancelledCount":' || cancelled_count || ',' ||
                  '"todayPaymentCount":' || today_payment_count || ',' ||
                  '"todayPaymentAmount":' || today_payment_amount || ',' ||
                  '"monthPaymentCount":' || month_payment_count || ',' ||
                  '"monthPaymentAmount":' || month_payment_amount || ',' ||
                  '"yearPaymentCount":' || year_payment_count || ',' ||
                  '"yearPaymentAmount":' || year_payment_amount || ',' ||
                  '"totalPaymentCount":' || total_payment_count || ',' ||
                  '"totalPaymentAmount":' || total_payment_amount ||
                '}';
return response;
end;$$
