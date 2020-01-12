insert into property_exclusion (class_name, excluded_properties, role_id) values
('Department', 'location', 1),
('Department', 'location, description', 2),
('Department', 'name, location, description', 3),

('Employee', 'email, lastName', 2),
('Employee', 'email, lastName, firstName', 3),

('Account', 'balance', 2),
('Account', 'accountType, balance', 3)
;


commit;