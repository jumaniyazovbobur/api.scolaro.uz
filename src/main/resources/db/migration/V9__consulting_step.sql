INSERT INTO consulting_step(id, description,step_type,
                              order_number,name,consulting_id,visible,created_date)
VALUES ('ed289dd9-1f76-4a9f-a428-05c4a7965e14','description1','TEMPLATE',1,'Birinchi Consulting step',null,true,now()),
 ('cffc1ee2-a393-487a-a248-d05a07a9cb96','description2','TEMPLATE',5,'Ikkinchi Consulting step',null,true,now()),
 ('4a947534-10b1-426c-9f70-75f4a8589d12','description3','TEMPLATE',2,'Uchinchi Consulting step',null,true,now()),
 ('37aeeb65-9d51-4131-acaa-6e787fc48978','description4','TEMPLATE',3,'To''rtinchi Consulting step',null,true,now()),
 ('f2985750-657a-45de-b7fc-c2c6cc9c8b6c','description5','TEMPLATE',4,'Beshinchi Consulting step',null,true,now()) ON CONFLICT (id) DO NOTHING;
