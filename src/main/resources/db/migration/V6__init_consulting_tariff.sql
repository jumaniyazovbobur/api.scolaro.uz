INSERT INTO consulting_tariff(id, description_uz,description_en,description_ru,tariff_type,status,
                              price,order_number,name,consulting_id,visible,created_date)
VALUES ('c0e0008b-ef79-4363-bcd0-a734e14dbfd9','description1Uz','description1En','description1Ru','TEMPLATE','ACTIVE',
        5600,1,'Birinchi Consulting Tarif',null,true,now()),
       ('d95288ae-45c6-4061-87ae-0a800f0da67c','description2Uz','description2En','description2Ru','TEMPLATE','ACTIVE',
        5600,5,'Ikkinchi Consulting Tarif',null,true,now()),
       ('a36265d0-2ca3-41ae-b963-b64f1e6645b1','description3Uz','description3En','description3Ru','TEMPLATE','ACTIVE',
        5600,4,'Uchinchi Consulting Tarif',null,true,now()),
       ('9d09ff8b-5d9b-4f78-bf11-d9992fc543de','description4Uz','description4En','description4Ru','TEMPLATE','ACTIVE',
        5600,2,'To''rtinchi Consulting Tarif',null,true,now()),
       ('d9b743f6-2c96-498e-afad-3c84ce1801ca','description5Uz','description5Uz','description5Ru','TEMPLATE','ACTIVE',
        5600,3,'Beshinchi Consulting Tarif',null,true,now()) ON CONFLICT (id) DO NOTHING;
