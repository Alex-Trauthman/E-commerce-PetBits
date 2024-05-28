insert into sabor (nome, intensidade) values('peixe', 'leve');
insert into sabor (nome, intensidade) values('frango', 'forte');
INSERT INTO sabor (nome, intensidade) VALUES ('carne', 'média');
INSERT INTO sabor (nome, intensidade) VALUES ('vegetais', 'suave');
INSERT INTO sabor (nome, intensidade) VALUES ('frutos do mar', 'alta');


insert into marca (nome, emailContato, cnpj) values ('Pedigree','pedigree@gmail.com','19923189');
INSERT INTO marca (nome, emailContato, cnpj) VALUES ('Whiskas', 'contato@whiskas.com', '987654321');
INSERT INTO marca (nome, emailContato, cnpj) VALUES ('Hills', 'info@hills.com', '123456789');
INSERT INTO marca (nome, emailContato, cnpj) VALUES ('Frolic', 'frolic@info.com', '135792468');


INSERT INTO brinquedo (nome, preco, descricao, id_marca, animal, material, tipoBrinquedo) VALUES ('Bolinha de borracha', 15.99, 'Bolinha para cães', 1, 'cão', 'borracha', 1);
INSERT INTO brinquedo (nome, preco, descricao, id_marca, animal, material, tipoBrinquedo) VALUES ('Corda de algodão', 9.99, 'Corda para brincadeiras', 2, 'cão', 'algodão', 3);
INSERT INTO brinquedo (nome, preco, descricao, id_marca, animal, material, tipoBrinquedo) VALUES ('Pelúcia divertida', 12.50, 'Brinquedo de pelúcia para gatos', 1, 'gato', 'pelúcia', 4);
insert into brinquedo (nome, preco, descricao,id_marca,animal,material,tipoBrinquedo) values('arranhador cilindríco 1,2x3', 121.8,'arranhador para gatos cilindrico', 1,'gato', 'tecido de sisal e papelão',8);

INSERT INTO remedio (nome, preco, descricao, quantidade, idade, pesoAnimal, id_marca, animal) VALUES ('Anti-pulgas', 25.00, 'Remédio para controle de pulgas e carrapatos', '1 comprimido', 2, 2, 1, 'cão');
INSERT INTO remedio (nome, preco, descricao, quantidade, idade, pesoAnimal, id_marca, animal) VALUES ('Vermífugo', 18.50, 'Vermífugo para combate de vermes intestinais', '10 mL', 5, 3, 2, 'cão');
INSERT INTO remedio (nome, preco, descricao, quantidade, idade, pesoAnimal, id_marca, animal) VALUES ('Antistress', 30.80, 'Remédio para redução de estresse e ansiedade', '20 comprimidos', 4, 1, 3, 'gato');

INSERT INTO petisco (nome, preco, descricao, unidades, pesoProduto, id_marca, animal, id_sabor) VALUES ('Palito de carne', 7.25, 'Palitos mastigáveis sabor carne para cães', 10, 1, 3, 'cão', 1);
INSERT INTO petisco (nome, preco, descricao, unidades, pesoProduto, id_marca, animal, id_sabor) VALUES ('Dentinhos de peixe', 4.99, 'Snack dental sabor peixe para gatos', 12, 2, 4, 'gato', 3);

INSERT INTO racao (nome, preco, descricao, idade, pesoProduto, id_marca, id_sabor, animal) VALUES ('Premium para filhotes', 35.99, 'Ração balanceada para filhotes', 2, 4, 1, 2,'cães e gatos');
INSERT INTO racao (nome, preco, descricao, idade, pesoProduto, id_marca, id_sabor, animal) VALUES ('Natural para gatos', 28.75, 'Ração natural com ingredientes selecionados para gatos', 3,4, 3, 1,'gato');

INSERT INTO usuario(username, senha) values ('jonata', 'PIEH9CgpvP+YoLEem5fmoM3Jm4j3T6ItKZTZ1AgrV88=');
INSERT INTO usuario(username,senha) values ('cleiton', '00QUydFSCxRZUL4yGYTi181zKihS7v6mifvvGIElga4=');

INSERT INTO cliente(id_usuario,email,nome,cidade,cpf,cep,rua,estado) values(2,'cleito@gmail.com','cleiton da silva','Palmas','289123','2131','203 sul','Tocantins');


