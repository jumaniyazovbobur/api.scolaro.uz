INSERT INTO consulting_tariff(id, name_uz,name_en,name_ru,tariff_type,status,
                              price,orders,description,consulting_id,visible,created_date)
VALUES ('c0e0008b-ef79-4363-bcd0-a734e14dbfd9','Name1Uz','Name1En','Name1Ru','TEMPLATE','ACTIVE',
        5600,1,'Birinchi Consulting Tarif',null,true,now()),
       ('d95288ae-45c6-4061-87ae-0a800f0da67c','Name2Uz','Name2En','Name2Ru','TEMPLATE','ACTIVE',
        5600,5,'Ikkinchi Consulting Tarif',null,true,now()),
       ('a36265d0-2ca3-41ae-b963-b64f1e6645b1','Name3Uz','Name3En','Name3Ru','TEMPLATE','ACTIVE',
        5600,4,'Uchinchi Consulting Tarif',null,true,now()),
       ('9d09ff8b-5d9b-4f78-bf11-d9992fc543de','Name4Uz','Name4En','Name4Ru','TEMPLATE','ACTIVE',
        5600,2,'To''rtinchi Consulting Tarif',null,true,now()),
       ('d9b743f6-2c96-498e-afad-3c84ce1801ca','Name5Uz','Name5En','Name5Ru','TEMPLATE','ACTIVE',
        5600,3,'Beshinchi Consulting Tarif',null,true,now()) ON CONFLICT (id) DO NOTHING;
