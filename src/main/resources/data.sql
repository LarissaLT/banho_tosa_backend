-- Inserção de dados
INSERT INTO Tutor (nome, telefone, email, endereco, senha, role) VALUES ('ADMIN', '', 'admin@admin.com', '', '$2a$10$3Kooh4.8Rxvu/fJzpzWViuPhPZVqSbA6MKR1Pg03KVmZn.Y5MOg4O','ADMIN'),
                                                           ('Maria Julia do Nascimento Santos', '(21)95478-8963', 'maria@example.com', 'Rua B, 456', '$2a$10$3Kooh4.8Rxvu/fJzpzWViuPhPZVqSbA6MKR1Pg03KVmZn.Y5MOg4O','USER');

INSERT INTO Cachorro (nome, raca, idade, porte, genero, tutor_id, deleted_at) VALUES ('Rex', 'Labrador', 3, 'Grande', 'Macho', 2,null),
                                                                        ('Arya', 'American Staffordshire Terrier', 7, 'Grande', 'Fêmea', 2,null),
                                                                        ('Juma', 'Vira-lata', 7, 'Medio', 'Fêmea', 2,null);

INSERT INTO Funcionario (nome) VALUES ('João Pedro'),
                                      ('Paulo');

INSERT INTO Servico (nome) VALUES ('Banho'),
                                  ('Banho e Tosa');


# INSERT INTO Agendamento (data, funcionario_id, cachorro_id, pagamento_id) VALUES ('2023-06-17', 1, 1, 1);


