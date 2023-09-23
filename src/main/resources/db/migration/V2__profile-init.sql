-- default password 123456
INSERT INTO users (id, name, surname, password,status, phone,visible,created_date)
VALUES ('909133fe-81ad-4aa1-b5fe-30d42d75bb3d', 'Adminjon', 'Adminov', 'e10adc3949ba59abbe56e057f20f883e','ACTIVE',
        '998938080831', true, now()) ON CONFLICT (id) DO NOTHING;

-- Insert role
INSERT INTO person_role(id, person_id, role, created_date, visible)
values ('89563bcd-565e-45f7-ace7-3077fffb3153', '909133fe-81ad-4aa1-b5fe-30d42d75bb3d', 'ROLE_ADMIN', now(), true)ON CONFLICT (id) DO NOTHING;