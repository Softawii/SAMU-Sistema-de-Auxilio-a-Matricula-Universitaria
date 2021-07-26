# **Contratos**

Abaixo será possível observar todos os contratos relacionados.

## Realizar Matrícula:

### Operações:

### **1.** iniciarProcessoMatrícula (aluno:Aluno)
-----------------------

### **Responsabilidades**:

- Iniciar processo de matrícula de um aluno

### **Referências Cruzadas**:

- **Caso de Uso:** "Realizar Matrícula"

### **Pré-Condições**:

- Aluno que deseja fazer a matrícula;
- Estar em período de matrícula.

### **Pós-Condições**:

- Lista de ofertas de disciplinas é instanciada.

<!-- Pular Linha -->
<br></br>

### **2.** escolherOfertaDisciplina (oferta:Oferta)
-----------------------

### **Responsabilidades**:

- Adicionar oferta de disciplina ao conjunto de disciplinas escolhida pelo aluno.

### **Referências Cruzadas**:

- **Caso de Uso:** "Realizar Matrícula"

### **Pré-Condições**:

- Ter selecionado 1 oferta de disciplina da lista.

### **Pós-Condições**:

- A oferta de disciplina selecionado pelo aluno é inserida em uma lista de disciplinas escolhidas por cada aluno.


<!-- Pular Linha -->
<br></br>


### **3.** confirmarEscolhasOfertasDisciplina (aluno:Aluno)
-----------------------

### **Responsabilidades**:

- Encerrar processo de matrícula de um aluno

### **Referências Cruzadas**:

- **Caso de Uso:** "Realizar Matrícula"

### **Pré-Condições**:

- Pelo menos uma oferta de disciplina ter sido escolhida.

### **Pós-Condições**:

- Uma nova solicitação de matrícula é registrada no sistema.

<!-- Pular Linha -->
<br></br>

### 4. **Operação**: cancelarProcessoMatrícula (aluno:Aluno)
-----------------------

### **Responsabilidades**:

- Cancelar o processo de matrícula sem realizar alterações no sistema.

### **Referências Cruzadas**:

- **Caso de Uso:** "Realizar Matrícula"

### **Pré-Condições**:

- Nenhuma.

### **Pós-Condições**:

- O processo de matrícula do aluno é cancelado e os objetos instanciados são destruídos.

<!-- Pular Linha -->
<br></br>

----
## Avaliar Experiência:

### Operações:

### 1. **Operação**: iniciarProcessoDeAvaliação (aluno:Aluno)
-----------------------

### **Responsabilidades**:

- Iniciar processo de avaliação de uma turma.

### **Referências Cruzadas**:

- **Caso de Uso:** "Avaliar experiência (UC09)"

### **Pré-Condições**:

- Ter uma turma e estar em período de avaliação de disciplina.

### **Pós-Condições**:

- Lista de turmas para serem avaliadas é instanciada.

<!-- Pular Linha -->
<br></br>

### 2. **Operação**: selecionarTurmaParaAvaliar (turma:Turma)
-----------------------

### **Responsabilidades**:

- Selecionar turma que será avaliada.

### **Referências Cruzadas**:

- **Caso de Uso:** "Avaliar experiência (UC09)"

### **Pré-Condições**:

- Ter a lista de turmas disponível.

### **Pós-Condições**:

- Objeto turma é selecionado da lista e armazenado outra variável.

<!-- Pular Linha -->
<br></br>

### 3. **Operação**: fazerAvaliação (turma:Turma, avaliação:Avaliação)
-----------------------

### **Responsabilidades**:

- Fazer avaliação da turma selecionada.

### **Referências Cruzadas**:

- **Caso de Uso:** "Avaliar experiência (UC09)"

### **Pré-Condições**:

- Ter uma turma selecionada e avaliação realizada.

### **Pós-Condições**:

- Nenhuma.

<!-- Pular Linha -->
<br></br>

### 4. **Operação**: cancelarProcessoAvaliação ()
-----------------------

### **Responsabilidades**:

- Cancelar o processo de avaliação sem realizar alterações no sistema.

### **Referências Cruzadas**:

- **Caso de Uso:** "Avaliar experiência (UC09)"

### **Pré-Condições**:

- Nenhuma.

### **Pós-Condições**:

- O processo de avaliação de turma é cancelado e os objetos instanciados são destruídos.

<!-- Pular Linha -->
<br></br>