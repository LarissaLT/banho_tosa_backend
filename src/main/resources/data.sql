-- Inserção de dados
INSERT INTO Tutor (nome, telefone, email, endereco, senha, role) VALUES ('Maria', '(21)91234-6789', 'maria@example.com', 'Rua A, 123', '$2a$10$3Kooh4.8Rxvu/fJzpzWViuPhPZVqSbA6MKR1Pg03KVmZn.Y5MOg4O','USER'),
                                                           ('Ana', '(21)95478-8963', 'ana@example.com', 'Rua B, 456', '$2a$10$3Kooh4.8Rxvu/fJzpzWViuPhPZVqSbA6MKR1Pg03KVmZn.Y5MOg4O','ADMIN');

INSERT INTO Cachorro (nome, raca, idade, porte, genero, tutor_id, deleted_at) VALUES ('Rex', 'Labrador', 3, 'Grande', 'Macho', 1,null),
                                                                        ('Arya', 'American Staffordshire Terrier', 7, 'Grande', 'Fêmea', 1,null),
                                                                        ('Juma', 'Vira-lata', 7, 'Medio', 'Fêmea', 1,null);

INSERT INTO Funcionario (nome) VALUES ('João'),
                                      ('Pedro');

INSERT INTO Pagamento (valor, data) VALUES(100.0, CURRENT_DATE),
                                          (75.0, CURRENT_DATE);

INSERT INTO Servico (nome, preco) VALUES ('Banho', 50.0),
                                         ('Banho e Tosa', 80.0);


# INSERT INTO Agendamento (data, funcionario_id, cachorro_id, pagamento_id) VALUES ('2023-06-17', 1, 1, 1);


