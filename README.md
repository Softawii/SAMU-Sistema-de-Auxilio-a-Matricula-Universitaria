<div>

<span class="c6"></span>

</div>

# <span class="c30 c15 c44">Documento de Requisitos</span>

<span class="c30 c35 c15"></span>

<span class="c42 c30 c15">Sistema de Auxílio à Matrícula Universitária
(SAMU)</span>

<span class="c42">Filipe Braida do Carmo</span>

<span class="c30 c15 c49"></span>

<span class="c0"></span>

<span class="c30 c15 c35">Projeto: Nº1</span>

<span class="c28 c30 c15">Versão
</span><span class="c28">1.2.</span><span class="c28">4</span>

<span class="c0"></span>

<span class="c0"></span>

<span class="c28 c30 c15">Histórico de Alterações</span>

<span class="c28 c30 c15"></span>

<span id="t.eff74fa2bb5b270dc9fa411243a83b4af6debdea"></span><span id="t.0"></span>

|                                    |                               |                                                             |                                          |
| ---------------------------------- | ----------------------------- | ----------------------------------------------------------- | ---------------------------------------- |
| <span class="c9">18/10/2019</span> | <span class="c9">1.0</span>   | <span class="c9">Início da modelagem</span>                 | <span class="c9">DevTeam</span>          |
| <span class="c9">31/10/2019</span> | <span class="c9">1.1</span>   | <span class="c9">Correção dos erros encontrados</span>      | <span class="c9">DevTeam</span>          |
| <span class="c9">02/11/2019</span> | <span class="c9">1.2</span>   | <span class="c9">Correção de requisitos e elicitação</span> | <span class="c9">Edu, Yan, Mateus</span> |
| <span class="c9">03/11/2019</span> | <span class="c9">1.2.1</span> | <span class="c6">Revisão da correção feita</span>           | <span class="c9">Romulo</span>           |
| <span class="c6">04/11/2019</span> | <span class="c6">1.2.2</span> | <span class="c9">Correções nos RNF e RN</span>              | <span class="c6">Mateus</span>           |
| <span class="c6">30/06/2021</span> | <span class="c6">1.2.3</span> | <span class="c6">Nova regra de negócio e correções</span>   | <span class="c6">DevTeam</span>          |
| <span class="c6">07/07/2021</span> | <span class="c6">1.2.4</span> | <span class="c6">Troca do nome</span>                       | <span class="c6">DevTeam</span>          |

<span class="c0"></span>

# <span class="c28 c30 c15">Conteúdo</span>

<span class="c5"></span>

<span class="c29 c15">1. INTRODUÇÃO        </span>

<span class="c29 c15">2. ESCOPO        </span>

<span class="c29 c15">3. DEFINIÇÕES, SIGLAS E ABREVIAÇÕES        </span>

<span class="c15 c29">4. REFERÊNCIAS        </span>

<span class="c29 c15">5. TÉCNICAS DE ELICITAÇÃO DE
REQUISITOS        </span>

<span class="c29 c15">6. REQUISITOS ESPECÍFICOS        </span>

<span class="c30 c47 c15">6.1 Requisitos Funcionais        </span>

<span class="c30 c47 c15">6.2 Regras de Negócio        </span>

<span class="c30 c15 c47">6.3 Requisitos Não-Funcionais        </span>

<span class="c0"></span>

1.  # <span class="c28 c30 c15">Introdução</span>

<span class="c0"></span>

<span class="c15">Este documento tem por objetivo apresentar os
requisitos que o sistema,</span><span> feito para a Instituição de
Ensino Natália Schots,</span><span class="c0"> deve atender em
diferentes níveis de detalhamento. Dessa forma, serve como acordo entre
as partes envolvidas – cliente e analista/desenvolvedor.</span>

<span class="c0"></span>

2.  # <span class="c28 c30 c15">Escopo</span>

<span class="c0"></span>

<span class="c0">O software visa informatizar o procedimento de
matrícula em disciplinas a cada semestre. O novo sistema se chama
Sistema de Auxílio à Matrícula Universitária (SAMU), e busca automatizar
a procura de disciplinas conforme o período atual e perfil do aluno.
</span>

<span>A partir de sua implementação o sistema irá transferir todas as
informações dos alunos atuais para o SAMU, e os novos cadastros serão
feitos no SAMU.</span>

<span class="c0">O cadastro em disciplinas verifica todas as disciplinas
e apresenta ao aluno apenas as que está apto a se matricular, baseado
nas disciplinas concluídas e com pré-requisitos concluídos e sugerir
novas disciplinas optativas em que pode se matricular, que serão
selecionadas a partir do perfil do aluno.</span>

<span class="c0">A automatização de grande parte dos processos é vista
de forma benéfica, pois assim os resultados são mais precisos,
padronizados e rápidos. A automação não só facilita o processo para as
matrículas dos alunos como também otimiza recomendações de disciplinas e
retira gastos desnecessários para a equipe.</span>

<span class="c0">Apesar de tudo, o software não funcionará com o
objetivo de automatizar o recebimento de notas, nem contará com um
sistema para procura de estágios, etc.</span>

<span class="c0"></span>

3.  # <span class="c28 c30 c15">Definições, siglas e abreviações</span>

<span class="c0"></span>

<span>User-friendly - Sistema intuitivo e de fácil utilização pelo
usuário;</span>

<span class="c0">SAMU - Sistema de Auxílio à Matrícula
Universitária;</span>

<span class="c0">RF - Requisito Funcional;</span>

<span class="c0">RNF - Requisito não-funcional;</span>

<span class="c0">RN - Regra de Negócio.</span>

<span class="c0"></span>

4.  # <span class="c15">Referências</span>

<span class="c0"></span>

<span class="c0">Mini-mundo - Matrícula de Alunos - Origem:
SIGAA.</span>

<span>Google Material Design - Origem:
</span><span class="c52">[https://material.io/design/](https://www.google.com/url?q=https://material.io/design/&sa=D&source=editors&ust=1626110809198000&usg=AOvVaw3jsz6Zk0APWo2wqTi-3QDX)</span>

5.  # <span class="c28 c15 c30">Técnicas de Elicitação de Requisitos</span>

<span class="c0"></span>

<span>Entrevista - </span><span class="c0">Foi feita uma rápida
entrevista com o cliente para ter um feedback se o sistema se alinhava
com o que havia sido depreendido do Minimundo;</span>

<span>Brainstorming entre os desenvolvedores - Foi considerada uma forma
eficiente e rápida de identificar todas as necessidades e descrevê-las,
assim como resolver potenciais problemas. A equipe se uniu para agilizar
a descrição do sistema e definir a melhor solução.</span>

<span class="c0">A partir das informações adquiridas pelo mini-mundo e
pela entrevista foi decidido que o sistema deve ter um design que
demonstra modernidade, para isso foi discutido que seguir as diretrizes
do Google Material seria a melhor opção, pela sua flexibilidade e
adaptabilidade, vale ressaltar que será feito em uma linguagem orientada
a objetos com suporte a interface gráfica, como Java ou C\#, para maior
portabilidade e compatibilidade.</span>

<span class="c0">Foi discutido também como o sistema deve definir o
perfil dos alunos para otimização de suas matrículas e recomendações. E
foi adotado um sistema de recomendação de optativas a partir de
avaliação de experiência em disciplinas, onde novas serão recomendadas
olhando para suas avaliações anteriores;</span>

<span>Prototipação evolutiva - Foi usado especialmente pelo feedback,
para confirmar se tudo que havia sido colocado no escopo e
funcionalidades correspondia com a expectativa do cliente antes de
prosseguir com o projeto.</span>

<span class="c0"></span>

6.  # <span class="c15">Requisitos Específicos</span>

<span class="c0"></span>

1.  ## <span class="c15"> Requisitos Funcionais</span>

<span class="c0">RF01 - O sistema deve permitir cadastrar usuários;
(RN01, RN11)</span>

<span class="c0">RF02 - O sistema deve permitir cadastrar cursos;
(RN02)</span>

<span class="c0">RF03 - O sistema deve permitir cadastrar disciplinas;
(RN03)</span>

<span class="c0">RF04 - O sistema deve permitir que os alunos possam se
matricular nas disciplinas; (RN04)</span>

<span class="c0">RF05 - O sistema deve permitir que o aluno avalie sua
experiência nas disciplinas;</span>

<span class="c0">RF06 - O sistema deve gerar um perfil de cada aluno
para inferir suas preferências; (RN08)</span>

<span class="c0">RF07 - O sistema deve permitir que o coordenador
realize a confirmação da matrícula dos alunos de seu curso;</span>

<span>RF08 - O sistema deve permitir que o usuário acesse a plataforma a
partir do nome de usuário e senha cadastrados;
</span><span>(RNF04)</span>

<span class="c0">RF09 - O sistema deve permitir que o coordenador do
curso gere relatórios; (RN06, RN07)</span>

<span class="c0">RF10 - O sistema deve recomendar disciplinas optativas
baseado no perfil do aluno. (RF06, RN05)</span>

2.  ## <span class="c15">Regras de Negócio</span>

<span>RN01 - Cada aluno terá as seguintes informações: matrícula,
nome,</span><span> senha</span><span class="c0">, endereço,
curso;</span>

<span class="c0">RN02 - Cada curso terá as seguintes informações: ID,
nome do curso, disciplinas obrigatórias, disciplinas optativas por
período;</span>

<span class="c0">RN03 - Cada disciplina terá as seguintes informações:
ID, nome, descrição, plano de aula, alocação de sala, disciplinas
pré-requisitadas, relatórios, professor e período letivo;</span>

<span class="c0">RN04 - O sistema deve permitir que os alunos possam
somente realizar a matrícula nas disciplinas em que os pré-requisitos
forem atendidos;</span>

<span>RN05 - O sistema deve recomendar as disciplinas optativas
</span><span>conforme o</span><span class="c0"> perfil do aluno e as
possíveis restrições de pré-requisito;</span>

<span class="c0">RN06 - O sistema deve permitir que o Relatório de
Alunos informe se o aluno realizou a matrícula em suas disciplinas
obrigatórias e/ou se sua carga horária está adequada;</span>

<span class="c0">RN07 - O sistema deve permitir que o Relatório das
Disciplinas informe se o número de alunos está adequado e se há alguma
sala disponível que comporte o número de alunos matriculados;</span>

<span>RN08 - </span><span>O perfil dos alunos é definido a partir das
suas avaliações em disciplinas cursadas. Baseado nessas avaliações,
serão definidas as áreas favoritas e indesejadas pelo aluno.       Ex.:
uma disciplina com avaliação baixa deve fazer com que seja evitada a
recomendação de disciplinas similares ou a própria disciplina avaliada
pelo</span><span> </span><span>aluno, caso as disciplinas não sejam
obrigatórias</span><span>;</span>

<span class="c0">RN09 - Cada curso e cada disciplina terá um
identificador único (ID);</span>

<span>RN10 - Para se matricular em disciplinas o aluno deve fornecer seu
perfil, que </span><span>consiste em</span><span> informações básicas
como renda familiar, endereço e número de telefone para contato;</span>

<span>RN11 - Cada coordenador terá as seguintes informações:
nome,</span><span> senha</span><span class="c0">, endereço, curso que
coordena;</span>

<span class="c0">RN12 - O sistema irá criar um usuário administrador de
sistema padrão na primeira inicialização do mesmo.</span>

<span class="c0"></span>

3.  ## <span class="c38 c30 c15"> Requisitos Não-Funcionais</span>

<span class="c3">Confiabilidade:</span>

<span class="c0">RNF01 - O sistema deverá realizar a transferência do
sistema anterior sem haver perda de informações no processo;</span>

<span class="c0">RNF02 - O sistema terá no mínimo 40% de capacidade de
processamento reservada para atender nos momentos de pico de uso;</span>

<span>RNF03 - O sistema armazenará todas as informações de cada usuário
de forma segura e livre de falhas, contra-ataques e invasões.</span>

<span class="c3"></span>

<span class="c3"></span>

<span class="c3">Usabilidade:</span>

<span class="c0">RNF04 - O sistema terá uma interface intuitiva e
simplificada, para que qualquer usuário leigo seja capaz de usá-lo sem
dificuldades;</span>

<span class="c0">RNF05 - O sistema deve seguir as diretrizes do Design
Material do Google como identidade visual, de modo a desenvolver um
produto único que será adaptável a computadores e smartphones e terá a
experiência do usuário como prioridade.</span>

<span class="c0"></span>

<span class="c3">Manutenibilidade:</span>

<span class="c0">RNF06 - O sistema permitirá que futuras atualizações
possam ser realizadas e implementadas através de uma implementação de
software proprietária que realiza as atualizações de modo
automático.</span>

<span class="c0"></span>

<span class="c3">Portabilidade:</span>

<span>RNF07 - O sistema deve ser desenvolvido para a plataforma desktop,
</span><span>sendo compatível com todos os sistemas operacionais do
mercado</span>

<div>

<span class="c0"></span>

</div>
