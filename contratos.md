# **Contratos**

Abaixo será possível observar todos os contratos relacionados.

## Realizar matrícula:

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

- Adicionar oferta de disciplina ao conjunto de disciplinas escolhida pelo aluno

### **Referências Cruzadas**:

- **Caso de Uso:** "Realizar Matrícula"

### **Pré-Condições**:

- Ter selecionado 1 oferta de disciplina da lista.

### **Pós-Condições**:

- A oferta de disciplina é inserida lista de ofertas de disciplina.

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
