insert into attach(id, created_date, visible, extension, origen_name, path, size)
values('a2863bb9-75a8-4bbd-bd00-a21791da9c41',now(),true,'jpg','oxford_grant','2024/1/1',0),
      ('3e5c09da-d5f7-4442-b77a-a9142ef417c3',now(),true,'jpg','manchester_grant','2024/1/1',0),
      ('5d8c640c-9240-408b-88c0-63f5a6dc62c9',now(),true,'jpg','california_grant','2024/1/1',0),
      ('72e3c5e5-ec1e-43a9-8ecc-d18df3ac6698',now(),true,'jpg','china_grant','2024/1/1',0),
      ('47d394cc-2c6c-45cc-a36e-577aad5d89f1',now(),true,'jpg','usa_grant','2024/1/1',0),
      ('017d2b23-e6b9-4925-a72e-d4788de2206b',now(),true,'jpg','dp_grant','2024/1/1',0),
      ('da510cb9-698e-4ea8-810c-557cfebcad16',now(),true,'jpg','german_grant','2024/1/1',0),
      ('2380e8fd-2a07-439a-a048-ac7d2104c542',now(),true,'jpg','cambridge_grant','2024/1/1',0),
      ('11a54f01-a917-4594-98d6-7aee903871b3',now(),true,'jpg','leiden_grant','2024/1/1',0),
      ('068d9e8c-6bd7-4b99-9c1a-c5d6fac047f3',now(),true,'png','egei_grant','2024/1/1',0)
    ON CONFLICT (id) DO NOTHING;
