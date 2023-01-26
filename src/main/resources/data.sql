INSERT INTO person (name, address, phone, password, status, dtype) VALUES
  ('Jose Lema', 'Otavalo sn y principal', '098254785', '1234', true, 'Client'),
  ('Marianela Montalvo', 'Amazonas y NNUU', '097548965', '5678', true, 'Client'),
  ('Juan Osorio', '13 junio y Equinoccial', '098874587', '1245', true, 'Client');

INSERT INTO account (number, type, balance, status, client_id) VALUES
  ('478758', 'Ahorro', 2000, true, 1),
  ('225487', 'Corriente', 100, true, 2),
  ('495878', 'Ahorro', 0, true, 3),
  ('496825', 'Ahorro', 540, true, 2),
  ('585545', 'Corriente', 1000, true, 1);