INSERT INTO continent(id, name_uz, name_en, name_ru, order_number, created_date, visible)
values (1, 'MDH boyicha', 'CIS to according', 'СНГ по данным', 1, now(), true),
       (2, 'Osiyo', 'Asia', 'Азия', 2, now(), true),
       (3, 'Yevropa', 'Evropa', 'Европа', 3, now(), true),
       (4, 'Shimoliy Amerika', 'North America', 'Северная Америка', 4, now(), true),
       (5, 'Avstraliya & Okeaniya', 'Australia & Oceania', 'Австралия & Океания', 5, now(), true) ON CONFLICT (id) DO NOTHING;
SELECT setval('continent_id_seq', max(id))
FROM continent;