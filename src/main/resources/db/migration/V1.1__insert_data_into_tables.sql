insert into department (name, description, location) values
('HR', 'Human Resource', 'Room 100, 999 Washington Ave. Falls Church, VA'),
('R&D', 'Research and Development', 'Room 101, 999 Washington Ave. Falls Church, VA'),
('Tech', 'Technical Support', 'Room 102, 999 Washington Ave. Falls Church, VA'),
('CS', 'Custom Service', 'Room 103, 999 Washington Ave. Falls Church, VA')
;
commit;

insert into employee (name, first_name, last_name, email, address, department_id) values
('dwang', 'David', 'Wang', 'david.wang@training.liwei.org', '999 Washington Ave, Fairfax, VA 22030', 2),
('rhang', 'Ryo', 'Hang', 'ryo.hang@training.liwei.org', '998 Washington Ave, Fairfax, VA 22030', 2),
('gzhang', 'Gloria', 'Zhang', 'gloria.zhang@training.liwei.org', '997 Washington Ave, Fairfax, VA 22030', 1),
('xhuang', 'Xingyue', 'Huang', 'xingyue.Hang@training.liwei.org', '996 Washington Ave, Fairfax, VA 22030', 3)
;
commit;

insert into account (account_type, balance, employee_id) values
('checking', 199999.59, 1),
('saving', 199999.59, 2),
('checking', 199999.59, 3),
('saving', 199999.59, 4)
;
commit;
