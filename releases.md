# Etapa 1 - v1.2.4v
## Alterações: 
- Novas regra de negócio
    - RN12 - O sistema irá criar um usuário administrador de sistema padrão na primeira inicialização do mesmo.
- Correções gramaticais
- Melhor descrição do escopo
- Refactoring dos Requisitos Não-Funcionais
- Troca de nome do projeto. SAM -> SAMU

# Etapa 2 - v2.0
## Alterações
- Arquivo releases.md reunindo todas as releases
- Diagrama de Classes de Análise
- Dois Diagramas de Sequência de Sistema
- Contratos dos DSS
- Administrador removido
- Documentos(minimundo, casos de uso, documento de requisitos) convertidos para Markdown
- Alteração de alguns casos de uso por necessidade dos diagramas
- Primeiros passos da aplicação gráfica
- Imagens dos diagramas são geradas por um executável, em vez de printscreen

# Etapa 3 - v3.0

## Correções/alterações
- Minimundo
- Casos de uso
- Requisitos funcionais
- Regras de negócios

### Alterações no minimundo
- Aluno agora também tem data de nascimento
- Cada curso tem um professor que deve ser cadastrado com seu nome, data de nascimento, endereço e curso. 
- Professores podem ser coordenadores de um curso.

### Alterações nos casos de usos
- Novo ator: Professor
- Professor agora cadastra plano de aula, antes era o Coordenador
- Administrador do Sistema agora cadastra cursos, antes era o Coordenador
- Novos casos de usos: cadastrar professor e turma

### Adição de requisitos funcionais

- RF11 - O sistema deve permitir cadastrar plano de aula em uma turma;
- RF12 - O sistema deve permitir cadastrar turmas; (RN11)

### Adição de regras de negócio

- RN10 - Cada professor terá as seguintes informações: nome, usuário, senha, data de nascimento, endereço, turmas que leciona.
- RN11 - Cada Turma terá as seguintes informações: disciplina, professor, aluno, período letivo, alocação de sala, plano de aula e relatório;