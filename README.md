# SAM-Sistema-de-Auxilio-a-Matricula
<!-- Output copied to clipboard! -->

<!-----
NEW: Check the "Suppress top comment" option to remove this info from the output.

Conversion time: 0.664 seconds.


Using this Markdown file:

1. Paste this output into your source file.
2. See the notes and action items below regarding this conversion run.
3. Check the rendered output (headings, lists, code blocks, tables) for proper
   formatting and use a linkchecker before you publish this page.

Conversion notes:

* Docs to Markdown version 1.0β30
* Mon Jul 05 2021 15:44:31 GMT-0700 (PDT)
* Source doc: SAM Alpha - v1.2.3
* Tables are currently converted to HTML tables.
----->


Documento de Requisitos

<p style="text-align: right">
<strong> </strong></p>


<p style="text-align: right">
<strong>Sistema de Auxílio à Matrícula (SAM)</strong></p>


<p style="text-align: right">
<strong>Natália Schots</strong></p>


<p style="text-align: right">
<strong>Projeto: Nº1</strong></p>


<p style="text-align: right">
<strong>Versão 1.2.3</strong></p>


**Histórico de Alterações**


<table>
  <tr>
   <td><strong>Data</strong>
   </td>
   <td>
    <strong>Versão</strong>
   </td>
   <td>
    <strong>Descrição</strong>
   </td>
   <td>
    <strong>Autor</strong>
   </td>
  </tr>
  <tr>
   <td>18/10/2019
   </td>
   <td>
    1.0
   </td>
   <td>Início da modelagem
   </td>
   <td>
    DevTeam
   </td>
  </tr>
  <tr>
   <td>31/10/2019
   </td>
   <td>
    1.1
   </td>
   <td>Correção dos erros encontrados
   </td>
   <td>
    DevTeam
   </td>
  </tr>
  <tr>
   <td>02/11/2019
   </td>
   <td>
    1.2
   </td>
   <td>Correção de requisitos e elicitação
   </td>
   <td>
    Edu, Yan, Mateus
   </td>
  </tr>
  <tr>
   <td>03/11/2019
   </td>
   <td>
    1.2.1
   </td>
   <td>
    Revisão da correção feita
   </td>
   <td>
    Romulo
   </td>
  </tr>
  <tr>
   <td>04/11/2019
   </td>
   <td>
    1.2.2
   </td>
   <td>
    Correções nos RNF e RN
   </td>
   <td>
    Mateus
   </td>
  </tr>
  <tr>
   <td>30/06/2021
   </td>
   <td>
    1.2.3
   </td>
   <td>
    Nova regra de negócio e correções
   </td>
   <td>DevTeam
   </td>
  </tr>
</table>


**Conteúdo**


[TOC]




1. 
 Introdução
Este documento tem por objetivo apresentar os requisitos que o sistema, feito para a Instituição de Ensino Natália Schots, deve atender em diferentes níveis de detalhamento. Dessa forma, serve como acordo entre as partes envolvidas – cliente e analista/desenvolvedor.



2. 
 Escopo
O software visa informatizar o procedimento de matrícula em disciplinas a cada semestre. O novo sistema se chama Sistema de Auxílio à Matrícula (SAM), e busca automatizar a procura de disciplinas conforme o período atual e perfil do aluno. 

A partir de sua implementação o sistema irá transferir todas as informações dos alunos atuais para o SAM, e os novos cadastros serão feitos no SAM.

O cadastro em disciplinas verifica todas as disciplinas e apresenta ao aluno apenas as que está apto a se matricular, baseado nas disciplinas concluídas e com pré-requisitos concluídos e sugerir novas disciplinas optativas em que pode se matricular, que serão selecionadas a partir do perfil do aluno.

A automatização de grande parte dos processos é vista de forma benéfica, pois assim os resultados são mais precisos, padronizados e rápidos. A automação não só facilita o processo para as matrículas dos alunos como também otimiza recomendações de disciplinas e retira gastos desnecessários para a equipe.

Apesar de tudo, o software não funcionará com o objetivo de automatizar o recebimento de notas, nem contará com um sistema para procura de estágios, etc.



3. 
 Definições, siglas e abreviações
User-friendly - Sistema intuitivo e de fácil utilização pelo usuário;

SAM - Sistema de Auxílio à Matrícula;

RF - Requisito Funcional;

RNF - Requisito não-funcional;

RN - Regra de Negócio.



4. 
 Referências
Mini-mundo - Matrícula de Alunos - Origem: SIGAA.

Google Material Design - Origem: [https://material.io/design/](https://material.io/design/)



5. 
 Técnicas de Elicitação de Requisitos
Entrevista - Foi feita uma rápida entrevista com o cliente para ter um feedback se o sistema se alinhava com o que havia sido depreendido do Minimundo;

Brainstorming entre os desenvolvedores - Foi considerada uma forma eficiente e rápida de identificar todas as necessidades e descrevê-las, assim como resolver potenciais problemas. A equipe se uniu para agilizar a descrição do sistema e definir a melhor solução.

A partir das informações adquiridas pelo mini-mundo e pela entrevista foi decidido que o sistema deve ter um design que demonstra modernidade, para isso foi discutido que seguir as diretrizes do Google Material seria a melhor opção, pela sua flexibilidade e adaptabilidade, vale ressaltar que será feito em uma linguagem orientada a objetos com suporte a interface gráfica, como Java ou C#, para maior portabilidade e compatibilidade.

Foi discutido também como o sistema deve definir o perfil dos alunos para otimização de suas matrículas e recomendações. E foi adotado um sistema de recomendação de optativas a partir de avaliação de experiência em disciplinas, onde novas serão recomendadas olhando para suas avaliações anteriores;

Prototipação evolutiva - Foi usado especialmente pelo feedback, para confirmar se tudo que havia sido colocado no escopo e funcionalidades correspondia com a expectativa do cliente antes de prosseguir com o projeto.



6. 
 Requisitos Específicos


    1. 
 Requisitos Funcionais
RF01 - O sistema deve permitir cadastrar usuários; (RN01, RN11)

RF02 - O sistema deve permitir cadastrar cursos; (RN02)

RF03 - O sistema deve permitir cadastrar disciplinas; (RN03)

RF04 - O sistema deve permitir que os alunos possam se matricular nas disciplinas; (RN04)

RF05 - O sistema deve permitir que o aluno avalie sua experiência nas disciplinas;

RF06 - O sistema deve gerar um perfil de cada aluno para inferir suas preferências; (RN08)

RF07 - O sistema deve permitir que o coordenador realize a confirmação da matrícula dos alunos de seu curso;

RF08 - O sistema deve permitir que o usuário acesse a plataforma a partir do nome de usuário e senha cadastrados; (RNF04)

RF09 - O sistema deve permitir que o coordenador do curso gere relatórios; (RN06, RN07)

RF10 - O sistema deve recomendar disciplinas optativas baseado no perfil do aluno. (RF06, RN05)



    2. 
Regras de Negócio
RN01 - Cada aluno terá as seguintes informações: matrícula, nome, senha, endereço, curso;

RN02 - Cada curso terá as seguintes informações: ID, nome do curso, disciplinas obrigatórias, disciplinas optativas por período;

RN03 - Cada disciplina terá as seguintes informações: ID, descrição, plano de aula, alocação de sala, disciplinas pré-requisitos, relatório, professor e o seu período relativo;

RN04 - O sistema deve permitir que os alunos possam somente realizar a matrícula nas disciplinas em que os pré-requisitos forem atendidos;

RN05 - O sistema deve recomendar as disciplinas optativas conforme o perfil do aluno e as possíveis restrições de pré-requisito;

RN06 - O sistema deve permitir que o Relatório de Alunos informe se o aluno realizou a matrícula em suas disciplinas obrigatórias e/ou se sua carga horária está adequada;

RN07 - O sistema deve permitir que o Relatório das Disciplinas informe se o número de alunos está adequado e se há alguma sala disponível que comporte o número de alunos matriculados;

RN08 - O perfil dos alunos é definido a partir das suas avaliações em disciplinas cursadas. Baseado nessas avaliações, serão definidas as áreas favoritas e indesejadas pelo aluno.       Ex.: uma disciplina com avaliação baixa deve fazer com que seja evitada a recomendação de disciplinas similares ou a própria disciplina avaliada pelo aluno, caso as disciplinas não sejam obrigatórias;

RN09 - Cada curso e cada disciplina terá um identificador único (ID);

RN10 - Para se matricular em disciplinas o aluno deve fornecer seu perfil, que consiste em informações básicas como renda familiar, endereço e número de telefone para contato;

RN11 - Cada coordenador terá as seguintes informações: nome, senha, endereço, curso que coordena;

RN12 - O sistema irá criar um usuário administrador de sistema padrão na primeira inicialização do mesmo.



    3. 
 Requisitos Não-Funcionais
_Confiabilidade:_

RNF01 - O sistema deverá realizar a transferência do sistema anterior sem haver perda de informações no processo;

RNF02 - O sistema terá no mínimo 40% de capacidade de processamento reservada para atender nos momentos de pico de uso;

RNF03 - O sistema armazenará todas as informações de cada usuário de forma segura e livre de falhas, contra-ataques e invasões.

_Usabilidade:_

RNF04 - O sistema terá uma interface intuitiva e simplificada, para que qualquer usuário leigo seja capaz de usá-lo sem dificuldades;

RNF05 - O sistema deve seguir as diretrizes do Design Material do Google como identidade visual, de modo a desenvolver um produto único que será adaptável a computadores e smartphones e terá a experiência do usuário como prioridade.

_Manutenibilidade:_

RNF06 - O sistema permitirá que futuras atualizações possam ser realizadas e implementadas através de uma implementação de software proprietária que realiza as atualizações de modo automático.

_Portabilidade:_

RNF07 - O sistema deve ser desenvolvido para a plataforma desktop, sendo compatível com todos os sistemas operacionais do mercado
