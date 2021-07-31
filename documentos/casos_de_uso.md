![diagrama_casos_de_uso](/diagramas/Casos%20de%20Uso/Diagrama%20de%20Caso%20de%20Uso%200.png)


# 1. Caso de uso: Cadastrar aluno (UC01).
- Ator: Coordenador.
- Visão geral: Permite cadastrar um aluno em um curso.
- Referências cruzadas:
    - Requisitos: RF01.
    - Caso relacionado: Fazer login (UC08).
- Pré-condição: Coordenador ter feito login no sistema (RF08).
- Pós-condições: O sistema irá gerar um novo aluno.
- Fluxo principal:
    1.  O sistema solicita o nome, data de nascimento e endereço do
        aluno;
    2.  O coordenador informa o nome, data de nascimento e endereço do
        aluno (FE-01, FE-02, FE-03);
    3.  O sistema informa a lista de cursos ao qual o aluno
        pode cursar;
    4.  O coordenador seleciona um curso na lista;
    5.  O sistema gera um número de matrícula e uma senha
        padrão e exibe as informações para o coordenador confirmar;
    6.  O coordenador confirma as informações;
    7.  Fim do caso de uso.
- Fluxo alternativo:
    - Não há
- Fluxo de exceção:
    - FE-01 - Aluno já cadastrado:
        1.  O sistema informa que o aluno já foi cadastrado;
        2.  Fim do caso de uso.
    - FE-02 - CEP inválido:
        1.  O sistema informa que o CEP é inválido;
        2.  O caso de uso retorna ao passo 1 do fluxo principal.
    - FE-03 - Data de nascimento inválida:
        1. O sistema informa que a data de nascemento é inválida;
        2. O caso de uso retorna ao passo 1 do fluxo principal.

# 2. Caso de uso: Cadastrar coordenador (UC02).

- Ator: Administrador do sistema.
- Visão geral: Permite cadastrar o coordenador de um curso específico.
- Referências cruzadas:
    - Requisitos: RF01.
    - Caso relacionado: Fazer login (UC08).
- Pré-condição: Administrador do sistema ter feito login no sistema. (RF08)
- Pós-condições: O sistema irá gerar um novo coordenador.
- Fluxo principal:
    1.  O sistema exibe uma lista de professores do curso e solicita que escolha um professor  (FA-01);
    2.  O administrador do sistema seleciona um professor da lista (FE-01);
    3.  O sistema sistema exibe as informações do professor selecionado para o administrador do sistema confirmar;
    4.  O administrador do sistema confirma a escolha;
    5. Fim do caso de uso.
- Fluxos Alternativos:
    - FA-01 - Professor não cadastrado:
        1.  O caso de uso é redirecionado para o passo 1 de (UC10);
        2.  O caso de uso retorna ao passo 1 do fluxo principal.
    - Fluxo de exceção:
        - FE-01 - Professor já está cadastrado como coordenador:
            1.  O sistema informa que o professor já foi cadastrado como coordenador;
            2.  Fim do caso de uso.

# 3. Caso de uso: Cadastrar curso (UC03).
- Ator: Coordenador.
- Visão geral: Permite cadastrar um novo curso.
- Referências cruzadas:
    - Requisitos: RF02.
    - Caso relacionado: Fazer login (UC08).
- Pré-condição: Coordenador ter feito login no sistema (RF08).
- Pós-condições: O sistema irá gerar um novo curso.
- Fluxo principal:
    1.  O sistema solicita o nome do curso;
    2.  O coordenador informa o nome do curso (FE-01);
    3.  O sistema exibe uma lista de disciplinas obrigatórias e optativas;
    4.  O sistema solicita as disciplinas obrigatórias e optativas;
    5.  O coordenador seleciona as disciplinas obrigatórias e optativas da lista (FA-01);
    6.  O sistema gera um ID e exibe as informações para o coordenador confirmar;
    7.  O coordenador confirma as informações;
    8.  Fim do caso de uso.
- Fluxo Alternativo:
    - FA-01 - Disciplina não cadastrada
        1.  O caso de uso é redirecionado para o passo 1 de (UC04);
        2.  O caso de uso retorna ao passo 5 ou 8 do fluxo principal, se foi cadastrada uma disciplina obrigatória ou optativa, respectivamente.
  - Fluxo de exceção:
    - FE-01 - Curso já cadastrado:
        1.  O sistema informa que o curso já foi cadastrado;
        2.  Fim do caso de uso.

# 4. Caso de uso: Cadastrar disciplina (UC04).
- Ator: Coordenador ou professor.
- Visão geral: Permite criar uma nova disciplina.
- Referências cruzadas:
    - Requisitos: RF03.
    - Caso relacionado: Fazer login (UC08).
- Pré-condição: Coordenador ou professor ter feito login no sistema. (RF08).
- Pós-condições: O sistema irá gerar uma nova disciplina.
- Fluxo principal:
    1.  O sistema solicita o nome e descrição da disciplina;
    2.  O coordenador ou professor informa o nome da disciplina (FE-01);
    3.  O sistema solicita o plano de aula;
    4.  O coordenador ou professor informa o plano de aula;
    5.  O sistema exibe uma lista de disciplinas;
    6.  O sistema solicita as disciplinas pré-requisitadas;
    7.  O coordenador ou professor informa as disciplinas pré-requisitadas.(FA-01);
    8.  O sistema solicita o professor da disciplina;
    9.  O coordenador ou professor informa o professor da disciplina;
    10. O sistema solicita o período relativo da disciplina;
    11. O coordenador ou professor informa o período relativo da disciplina;
    12. O sistema gera um ID e procura uma sala para alocar a disciplina e exibe as informações para o coordenador ou professor confirmar;
    13. O coordenador ou professor confirma as informações;
    14. Fim do caso de uso.
- Fluxo Alternativo:
    - FA-01 - Disciplina não cadastrada
        1.  O caso de uso é redirecionado para o passo 1 de UC04;
        2.  O caso de uso retorna ao passo 7 do fluxo principal.
- Fluxo de exceção:
    - FE-01 - Disciplina já cadastrada
        1.  O sistema informa que a disciplina já foi cadastrada;
        2.  Fim do caso de uso.


# 5. Caso de uso: Matricular aluno em disciplina (UC05).
- Ator: Aluno.
- Visão Geral: Permite que o aluno se matricule em uma ou mais ofertas de disciplinas disponíveis.
- Referência Cruzada:
    - Requisitos: RF04.
    - Caso relacionado: Fazer login(UC08).
- Pré Requisitos: Aluno ter feito login.
- Pós-Condição: O sistema irá matricular o aluno na(s) oferta(s) disciplina(s) selecionada.
- Fluxo Principal:
    1.  O sistema oferece a lista de ofertas de disciplinas disponíveis para o aluno de acordo com perfil, curso e se atende os pré-requisitos das ofertas;
    2.  O sistema pede para o aluno escolher uma ou mais ofertas de disciplinas;
    3.  O aluno seleciona uma ou mais ofertas de disciplinas da lista;
    4.  O sistema pede para o aluno confirmar se deseja prosseguir (FE-01);
    5.  O aluno decide confirmar a escolha (FA-01);
    6.  O sistema envia uma confirmação do registro do aluno na oferta para o coordenador;
    7.  Fim do caso de uso.
- Fluxo Alternativo:
    - FA-01 - Se o aluno decide não confirmar a escolha;
        1.  O sistema cancela a operação;
        2.  Fim do caso de uso.
- Fluxo de exceção: 
    - FE-01 Caso ocorra choque de horário.
        1.  O sistema informa que há choque de horário entre as disciplinas;
        2.  O caso de uso retorna ao passo 1 do fluxo principal.


# 6. Caso de uso: Confirmar matrícula em disciplina (UC06).
- Ator: Coordenador.
- Visão Geral: Permite confirmar a matrícula de alunos em disciplinas.
- Referência Cruzada:
    - Requisitos: RF07.
    - Caso relacionado: Fazer login (UC08).
- Pré Requisitos: Aluno ter feito login.
- Pós-Condição: O sistema irá confirmar ou não a matrícula do aluno na disciplina requerida.
- Fluxo Principal:
    1.  O sistema fornece a lista de requisição de matrícula em disciplinas do curso do Coordenador;
    2.  O sistema pede para o Coordenador confirmar os pedidos (FA-01);
    3.  O coordenador confirma o pedido;
    4.  O sistema registra o aluno na disciplina;
    5.  Fim do caso de uso.
- Fluxo Alternativo:
    - FA-01 - O coordenador nega o pedido.
        1.  O sistema não registra o aluno na disciplina;
        2.  O sistema finaliza a operação com sucesso;
        3.  Fim do caso de uso.
- Fluxo de exceção:
    - Não há.

# 7. Caso de uso: Gerar relatório (UC 07).
- Ator: Coordenador.
- Visão geral: Permite que o coordenador do curso gere relatórios.
- Referências cruzadas:
    - Requisitos: RF-09.
    - Outros casos de uso relacionados: Fazer login (UC08).
- Pré-condições: Coordenador ter feito login no sistema (RF08).
- Pós-condições: O relatório será exibido.
- Fluxo principal:
    1.  O coordenador escolhe o Relatório de Alunos a ser gerado (FA-01);
    2.  O sistema retorna o Relatório de Alunos;
    3.  O caso de uso é encerrado.
- Fluxo alternativo:
    - FA-01 - O usuário escolhe o Relatório de Disciplina
        1.  O sistema retorna o Relatório de Disciplina;
        2.  O caso de uso é encerrado.
- Fluxo de exceção:
    - Não há.

# 8. Caso de uso: Fazer login (UC08).
- Ator: Usuário.
- Visão geral: Permite que o usuário acesse a plataforma a partir do usuário e senha cadastrados.
- Referências cruzadas:
    - Requisitos: RF08.
    - Outros casos de uso relacionados: Cadastrar usuário.
- Pré-condições: O usuário deverá estar registrado.
- Pós-condições: O usuário é autenticado e tem acesso à plataforma.
- Fluxo principal:
    1.  O sistema solicita o nome de usuário e senha do usuário;
    2.  O usuário informa seu nome de usuário e senha;
    3.  O sistema verifica o nome de usuário (FE-01);
    4.  O sistema verifica o nome de usuário e senha; (FE-01,FE-02,FE-03)
    5.  O login é feito.
    6.  O caso de uso é encerrado.
- Fluxos de exceção:
    - FE-01 - Usuário inválido:
        1.  O sistema informa que o apelido de usuário inserido é inválido;
        2.  O caso de uso retorna para o passo 1 do fluxo principal.
    - FE-02 - Senha inválida:
        1.  O sistema informa que a senha inserida é inválida;
        2.  O caso de uso retorna para o passo 4 do fluxo principal.
    - FE-03 - Senha inválida \> 3 vezes:
        1.  O sistema informa que o acesso à conta está temporariamente bloqueado e pede que o usuário recadastre a senha através de um link que será enviado para o seu email;
        2.  O caso de uso é encerrado.


# 9. Caso de uso: Avaliar experiência (UC09).
- Ator: Aluno.
- Visão geral: Permite que o aluno avalie sua experiência em uma ou mais turmas.
- Referências cruzadas:
    - Requisitos: RF05.
    - Outros casos de uso relacionados: Fazer login (UC08).
- Pré-condições: O aluno deverá ter feito login e deve ter finalizado a(s) disciplina(s).
- Pós-condições: O usuário terá sua avaliação enviada.
- Fluxo principal:
    1.  O sistema exibe a lista de turmas disponíveis para avaliação;
    2.  O aluno seleciona uma ou mais turma que deseja avaliar;
    3.  O sistema solicita a quantidade de estrelas ao aluno;
    4.  O aluno seleciona a quantidade de estrelas e confirma a avaliação; (FA-01)
    5.  O sistema analisa e valida a avaliação; (FE-01)
    6.  O caso de uso é encerrado.
- Fluxo alternativo:
    - FA-01 - Cancelar processo de avaliação
        1. O caso de uso é encerrado e nenhum processo é salvo.
- Fluxo de exceção:
    - FE-01 - Nenhuma estrela colocada
        1.  O sistema informa que não foi colocada nenhuma estrela;
        2.  O caso de uso retorna ao passo 1 do fluxo principal.

# 10. Caso de uso: Cadastrar professor (UC10).

- Ator: Administrador do sistema ou coordenador.
- Visão geral: Permite cadastrar o professor em um curso.
- Referências cruzadas:
    - Requisitos: RF01.
    - Caso relacionado: Fazer login (UC08).
- Pré-condição: Administrador do sistema ou coordenador ter feito login no sistema. (RF08)
- Pós-condições: O sistema irá gerar um novo professor.
- Fluxo principal:
    1.  O sistema solicita o nome, data de nascimento e endereço do professor;
    2.  O administrador do sistema ou coordenador informa o nome, data de nascimento e endereço do novo professor (FE-01, FE-02, FE-03);
    3.  O sistema informa a lista de cursos que o professor pode lecionar;
    4.  O administrador do sistema ou coordenador seleciona um curso na lista (FA-01);
    5.  O sistema gera uma senha padrão e exibe as informações para o administrador do sistema ou coordenador confirmar;
    6.  O administrador do sistema ou coordenador confirma as informações;
    7.  Fim do caso de uso.
- Fluxos Alternativos:
    - FA-01 - Cadastrar novo curso:
        1.  O caso de uso é redirecionado para o passo 1 de (UC03);
        2.  O caso de uso retorna ao passo 4 do fluxo principal.
    - Fluxo de exceção:
        - FE-01 - Professor já cadastrado:
            1.  O sistema informa que o professor já foi cadastrado;
            2.  Fim do caso de uso.
    - FE-02 - CEP inválido:
        1.  O sistema informa que o CEP é inválido;
        2.  O caso de uso retorna ao passo 1 do fluxo principal.
    - FE-03 - Data de nascimento inválida:
        1. O sistema informa que a data de nascemento é inválida;
        2. O caso de uso retorna ao passo 1 do fluxo principal.
