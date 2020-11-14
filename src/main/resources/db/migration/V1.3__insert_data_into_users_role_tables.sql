insert into role (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) values
('Admin', '/', 'Y', 'Y', 'Y', 'Y'),
('Manager', '/depts,/departments,/employees,/ems,/acnts,/accounts', 'Y', 'Y', 'Y', 'N'),
('user', '/employees,/ems,/acnts,/accounts', 'Y', 'N', 'N', 'N')
;
commit;

insert into users (name, password, first_name, last_name, email) values
('dwang', '25f9e794323b453885f5181f1b624d0b', 'David', 'Wang', 'dwang@training.liwei.org'),
('rhang', '25f9e794323b453885f5181f1b624d0b', 'Ryo', 'Hang', 'rhang@training.liwei.org'),
('xyhuang', '25f9e794323b453885f5181f1b624d0b', 'Xinyue', 'Huang', 'xyhuang@training.liwei.org')
;
commit;

insert into users_role values
(1, 1),
(2, 2),
(3, 3),
(1, 2),
(1, 3)
;
commit;