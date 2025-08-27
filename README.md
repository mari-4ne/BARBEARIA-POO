# Sistema de agendamento para barbearia
Para nosso projeto idealizamos um sistema de agendamento e aplicamos numa barbearia, onde foram utilizados os padrões de projeto MVC e Template Method com o objetivo obter melhor dinamicidade na construção e manutenção do código.

## Padrão de projeto
No desenvolvimento deste projeto julgamos pertinente o uso do padrão de projeto Template Method, visto que assemelha-se ao conceito de interface associado aos pilares da orientação a objetos. Aplicamos o padrão nas
classes do tipo Service(subclasses) e na classe que contem a lógica principal da persistência(superclasse), com o intuito de possibilitar a persistência de dados de maneira segura.
## Teste Junit
Para as classes de teste julgamos pertinente utilizar nas classes pertencentes ao Service, para garantir o funcionamento da persistencia de arquivos e as funcionalidades principais que dão sentido a execução do projeto.
## Funcionalidades
Agendamento de Horários: Para o usuário do tipo cliente é possível realizar a escolha do profissional, dia, horário e corte;  
Disponibilizar e Indisponibilar Horários: Para o usuário colaborador é possível deixar disponível ou indisponível para agendamento qualquer horário que não tenha sido anteriormente agendado;  
Vizializar Horários: Para o usuário colaborador é possível vizualizar horários agendados, disponíveis e indisponíveis uma vizualização do tipo agenda;  
