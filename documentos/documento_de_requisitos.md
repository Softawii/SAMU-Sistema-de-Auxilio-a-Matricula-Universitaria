<h1 align="center">Documento de Requisitos</h1>
<h2 align="center">Sistema de Auxílio à Matrícula Universitária (SAMU)</h2>
<h2 align="center">Filipe Braida do Carmo</h2>
<h3 align="center">Projeto: Nº1</h3>
<h3 align="center">Versão 1.2.4</h3>

----

## Histórico de Alterações

| Data       | Versão | Descrição                           | Autor            |
| ---------- | ------ | ----------------------------------- | ---------------- |
| 18/10/2019 | 1.0    | Início da modelagem                 | DevTeam          |
| 31/10/2019 | 1.1    | Correção dos erros encontrados      | DevTeam          |
| 02/11/2019 | 1.2    | Correção de requisitos e elicitação | Edu, Yan, Mateus |
| 03/11/2019 | 1.2.1  | Revisão da correção feita           | Romulo           |
| 04/11/2019 | 1.2.2  | Correções nos RNF e RN              | Mateus           |
| 30/06/2021 | 1.2.3  | Nova regra de negócio e correções   | DevTeam          |
| 07/07/2021 | 1.2.4  | Troca do nome                       | DevTeam          |

----

## Sumário

1. INTRODUÇÃO
2. ESCOPO
3. DEFINIÇÕES, SIGLAS E ABREVIAÇÕES
4. REFERÊNCIAS
5. TÉCNICAS DE ELICITAÇÃO DE REQUISITOS
6. REQUISITOS ESPECÍFICOS  
    6.1 Requisitos Funcionais  
    6.2 Regras de Negócio  
    6.3 Requisitos Não-Funcionais  

----
# 1. Introdução

Este documento tem por objetivo apresentar os requisitos que o sistema, feito para a Instituição de Ensino Natália Schots, deve atender em diferentes níveis de detalhamento. Dessa forma, serve como acordo entre as partes envolvidas – cliente e analista/desenvolvedor.

# 2. Escopo

O software visa informatizar o procedimento de matrícula em disciplinas a cada semestre. O novo sistema se chama Sistema de Auxílio à Matrícula Universitária (SAMU), e busca automatizar a procura de disciplinas conforme o período atual e perfil do aluno.

A partir de sua implementação o sistema irá transferir todas as informações dos alunos atuais para o SAMU, e os novos cadastros serão feitos no SAMU.

O cadastro em disciplinas verifica todas as disciplinas e apresenta ao aluno apenas as que está apto a se matricular, baseado nas disciplinas concluídas e com pré-requisitos concluídos e sugerir novas disciplinas optativas em que pode se matricular, que serão selecionadas a partir do perfil do aluno.

A automatização de grande parte dos processos é vista de forma benéfica, pois assim os resultados são mais precisos, padronizados e rápidos. A automação não só facilita o processo para as matrículas dos alunos como também otimiza recomendações de disciplinas e retira gastos desnecessários para a equipe.

Apesar de tudo, o software não funcionará com o objetivo de automatizar o recebimento de notas, nem contará com um sistema para procura de estágios, etc.

# 3. Definições, siglas e abreviações

User-friendly - Sistema intuitivo e de fácil utilização pelo usuário;
SAMU - Sistema de Auxílio à Matrícula Universitária;
RF - Requisito Funcional;
RNF - Requisito não-funcional;
RN - Regra de Negócio.

# 4. Referências

Mini-mundo - Matrícula de Alunos - Origem: SIGAA.

Google Material Design - Origem: [https://material.io/design/](https://material.io/design)

# 5. Técnicas de Elicitação de Requisitos

Entrevista - Foi feita uma rápida entrevista com o cliente para ter um feedback se o sistema se alinhava com o que havia sido depreendido do Minimundo;

Brainstorming entre os desenvolvedores - Foi considerada uma forma eficiente e rápida de identificar todas as necessidades e descrevê-las, assim como resolver potenciais problemas. A equipe se uniu para agilizar a descrição do sistema e definir a melhor solução.

A partir das informações adquiridas pelo mini-mundo e pela entrevista foi decidido que o sistema deve ter um design que demonstra modernidade, para isso foi discutido que seguir as diretrizes do Google Material seria a melhor opção, pela sua flexibilidade e adaptabilidade.

Foi discutido também como o sistema deve definir o perfil dos alunos para otimização de suas matrículas e recomendações. E foi adotado um sistema de recomendação de optativas a partir de avaliação de experiência em disciplinas, onde novas serão recomendadas olhando para suas avaliações anteriores;

Prototipação evolutiva - Foi usado especialmente pelo feedback, para confirmar se tudo que havia sido colocado no escopo e funcionalidades correspondia com a expectativa do cliente antes de prosseguir com o projeto.

# 6. Requisitos Específicos

## 1.  Requisitos Funcionais

RF01 - O sistema deve permitir cadastrar usuários; (RN01, RN09, RN10)

RF02 - O sistema deve permitir cadastrar cursos; (RN02)

RF03 - O sistema deve permitir cadastrar disciplinas e turmas; (RN03)

RF04 - O sistema deve permitir que os alunos possam se matricular nas turmas; (RN04)

RF05 - O sistema deve permitir que o aluno avalie sua experiência nas turmas;

RF06 - O sistema deve gerar um perfil de cada aluno para inferir suas preferências; (RN08)

RF07 - O sistema deve permitir que o coordenador realize a confirmação da matrícula dos alunos de seu curso;

RF08 - O sistema deve permitir que o usuário acesse a plataforma a partir do nome de usuário e senha cadastrados; (RNF04)

RF09 - O sistema deve permitir que o coordenador do curso gere relatórios; (RN06, RN07)

RF10 - O sistema deve recomendar disciplinas optativas baseado no perfil do aluno; (RF06, RN05)

RF11 - O sistema deve permitir cadastrar plano de aula em uma turma.

## 2. Regras de Negócio

RN01 - Cada aluno terá as seguintes informações: matrícula, nome, usuário, senha, data de nascimento, endereço, curso;

RN02 - Cada curso terá as seguintes informações: ID, nome do curso, disciplinas obrigatórias, disciplinas optativas por período;

RN03 - Cada disciplina terá as seguintes informações: ID, nome, descrição, plano de aula, alocação de sala, disciplinas pré-requisitadas, relatórios, professor e período letivo;

RN04 - O sistema deve permitir que os alunos possam somente realizar a matrícula nas disciplinas em que os pré-requisitos forem atendidos;

RN05 - O sistema deve recomendar as disciplinas optativas conforme o perfil do aluno e as possíveis restrições de pré-requisito;

RN06 - O sistema deve permitir que o Relatório de Alunos informe se o aluno realizou a matrícula em suas disciplinas obrigatórias e/ou se sua carga horária está adequada;

RN07 - O sistema deve permitir que o Relatório das Disciplinas informe se o número de alunos está adequado e se há alguma sala disponível que comporte o número de alunos matriculados;

RN08 - O perfil dos alunos é definido a partir das suas avaliações em disciplinas cursadas. Baseado nessas avaliações, serão definidas as áreas favoritas e indesejadas pelo aluno.  
Ex.: uma disciplina com avaliação baixa deve fazer com que seja evitada a recomendação de disciplinas similares ou a própria disciplina avaliada pelo aluno, caso as disciplinas não sejam obrigatórias;

RN09 - Cada coordenador terá as seguintes informações: nome, usuário, senha, data de nascimento, endereço, curso que coordena;

RN10 - Cada professor terá as seguintes informações: nome, usuário, senha, data de nascimento, endereço, turmas que leciona.

## 3. Requisitos Não-Funcionais

- Confiabilidade:  
    RNF01 - O sistema deverá realizar a transferência do sistema anterior sem haver perda de informações no processo;  
    RNF02 - O sistema terá no mínimo 40% de capacidade de processamento reservada para atender nos momentos de pico de uso;  
    RNF03 - O sistema armazenará todas as informações de cada usuário de forma segura e livre de falhas, contra-ataques e invasões.

- Usabilidade:  
    RNF04 - O sistema terá uma interface intuitiva e simplificada, para que qualquer usuário leigo seja capaz de usá-lo sem dificuldades;  
    RNF05 - O sistema deve seguir as diretrizes do Design Material do Google como identidade visual, de modo a desenvolver um produto único que será adaptável a computadores e smartphones e terá a experiência do usuário como prioridade.

- Manutenibilidade:  
    RNF06 - O sistema permitirá que futuras atualizações possam ser realizadas e implementadas através de uma implementação de software proprietária que realiza as atualizações de modo automático.

- Portabilidade:  
    RNF07 - O sistema deve ser desenvolvido para a plataforma desktop, sendo compatível com todos os sistemas operacionais do mercado
