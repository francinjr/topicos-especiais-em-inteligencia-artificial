# Ontologia de Alergia Alimentar (OAA)

![Licença](https://img.shields.io/badge/license-CC--BY--4.0-blue.svg)

Bem-vindo(a) ao repositório da Ontologia de Alergia Alimentar. Este projeto visa criar um modelo de conhecimento formal e estruturado sobre alergias alimentares para apoiar profissionais da saúde, pesquisadores e pacientes.

## 1. Qual o objetivo deste projeto?

Alergias alimentares são condições complexas. O diagnóstico e o manejo exigem a conexão de diversas informações: o que o paciente comeu, quais sintomas apresentou, quais são os componentes moleculares dos alimentos e como eles podem interagir.

Este projeto busca organizar esse conhecimento de forma lógica e sem ambiguidades. O resultado é uma **ontologia**: um "mapa conceitual" inteligente que não apenas armazena os dados, mas também **entende as relações entre eles para realizar inferências automáticas**.

O objetivo final é criar uma base de conhecimento para:
* Apoiar profissionais da saúde na identificação de alimentos alergênicos e reações adversas.
* Auxiliar na recomendação de alternativas alimentares seguras.
* Servir como base para sistemas que realizem análises baseadas no perfil dos pacientes.

## 2. O Processo de Desenvolvimento: Do Conceito à Prática

O desenvolvimento desta ontologia seguiu um fluxo de trabalho estruturado para garantir tanto o rigor conceitual quanto a aplicabilidade computacional.

### 2.1. O Modelo Conceitual (OntoUML)

A base do projeto é um modelo conceitual criado na linguagem **OntoUML**, que permite uma representação rica e precisa da realidade. Este modelo funciona como a **planta baixa** da nossa base de conhecimento, detalhando os conceitos e suas relações fundamentais.

**Visualize o diagrama completo em `ontologia-alergia-alimentar.png`.**

#### Blocos de Construção do Modelo:
* **Paciente e Contexto Clínico 🩺**: Centrado na **Pessoa** e seus papéis (`Paciente`, `Médico`).
* **Alimentos e Componentes Alergênicos 🍲**: Detalha o `Alimento`, suas `Substâncias Alergênicas` (ex: caseína) e a `Sequência de Aminoácidos` que as define.
* **Reação Alérgica e Reatividade Cruzada 💥**: Modela a `Reação Alérgica` como um evento que envolve um `Paciente`, uma `Substância Alergênica` e um `Anticorpo`.
* **Diagnóstico 📝**: Representa o `Diagnóstico` como uma conclusão formal resultante de um `Procedimento` médico.

### 2.2. A Serialização para OWL2

Para que o modelo conceitual se torne operacional, ele precisa ser "traduzido" para uma linguagem que os computadores entendam. Este processo é chamado de **serialização**.

> A ontologia especificada em OntoUML foi **serializada para OWL2**, convertendo a "planta baixa" visual em um "edifício" computacional (`.ttl`), pronto para ser processado.

Este passo foi realizado com o auxílio de plugins e depois o resultado foi refinado no ambiente **Protégé** para adicionar a lógica complexa que permite as inferências.

## 3. Funcionalidades e Inferências Automáticas

A versão em OWL2 não é apenas um repositório de dados; é um sistema inteligente que deduz novos fatos. As principais funcionalidades são:

#### 1. Descoberta de Alergia Específica (Pessoa-Alimento)
A ontologia responde à pergunta: "**Esta pessoa específica é alérgica a este alimento específico?**".
* **Como?** Através de uma cadeia de propriedades (`propertyChainAxiom`), o sistema conecta a pessoa à sequência de aminoácidos que lhe causa reação, e essa sequência ao alimento que a contém.
* **Exemplo Prático:** O sistema infere que `joao é alérgico a amendoim`, mas **não** infere que `ana é alérgica a amendoim`, pois a reação dela é específica para a sequência do leite.

#### 2. Classificação Automática de Alimentos
A ontologia classifica os alimentos em duas categorias distintas e mutuamente exclusivas:
* **`AlimentoAlergenico`**: Um alimento recebe esta classificação se for comprovado que ele causa alergia em **pelo menos uma pessoa** no sistema. É um rótulo de "potencialmente perigoso".
* **`AlimentoNaoAlergenico`**: Um alimento sobre o qual declaramos explicitamente que não possui sequências alergênicas conhecidas.

* **Exemplo Prático:** Como João é alérgico a amendoim, o alimento `:amendoim` é classificado como `AlimentoAlergenico`. Já o `:ovo`, para o qual declaramos não haver sequências, é classificado como `AlimentoNaoAlergenico`.

## 4. Padrões de Projeto de Ontologia (ODP) Utilizados

Para simplificar o modelo e garantir a reutilização, foram aplicados Padrões de Projeto de Ontologia (ODPs):

* **Agent Role (Agente-Papel)**: A classe `Pessoa` funciona como o agente, que pode assumir diferentes papéis (`Role`) no contexto da ontologia, como `:Paciente` ou `:Medico`. Isso torna o modelo flexível e extensível.
* **Object Property Chain (Cadeia de Propriedades de Objeto)**: Este padrão foi implementado diretamente em OWL para criar a propriedade `:eAlergicoA`. Ele permite inferir uma relação direta (a alergia) a partir de uma cadeia de relações indiretas (Pessoa -> Sequência -> Alimento).

## 5. Navegando no Repositório

* `ontologia-alergia-alimentar.ttl`: **Arquivo principal da ontologia** em formato Turtle (OWL2). Este arquivo deve ser aberto no Protégé para exploração e testes (também tem no formato .owx).
* `Especificação de Requisitos da Ontologia (ORSD).pdf`: Contém o Documento de Requisitos da Ontologia, com a lista de perguntas que o modelo deve responder.
* `ontologia-alergia-alimentar.png`: Imagem do diagrama conceitual para fácil visualização.
* `ontologia-alergia-alimentar.vpp`: Arquivo do projeto do **Visual Paradigm**, para quem deseja editar ou explorar o modelo OntoUML.

## 6. Como Testar a Ontologia

1.  Instale o [Protégé Desktop](https://protege.stanford.edu/).
2.  Abra o arquivo `ontologia-alergias.ttl` no Protégé.
3.  Inicie um reasoner: vá ao menu `Reasoner` e clique em `Start reasoner` (recomenda-se HermiT ou Pellet).
4.  Navegue pelas classes e indivíduos na aba `Entities` para ver as novas classificações e relações inferidas (geralmente destacadas em amarelo).

## 7. Consultas SPARQL

### CQ-1: A quais alimentos o paciente X tem alergia?
```sparql
PREFIX : <http://www.semanticweb.org/ontologia-alergias#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?alimento
WHERE {
  {
    SELECT ?nomePessoa ?alimento WHERE {
        ?alimento a :Alimento ;
                  :possuiSequencia ?sequencia .
        ?sequencia :geraReacaoEmPessoaComProblema ?pessoa .
        ?pessoa rdfs:label ?nomePessoa .
    }
  }
  FILTER(?nomePessoa = "João Silva")
}
```

### CQ-2: Quais são as alternativas alimentares seguras, considerando as alergias do paciente X?
```sparql
PREFIX : <http://www.semanticweb.org/ontologia-alergias#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?alimento_seguro WHERE {
    ?alimento_seguro a :Alimento .
    MINUS { 
        ?alimento_seguro :possuiSequencia ?sequencia .
        ?sequencia :geraReacaoEmPessoaComProblema ?pessoa .
        ?pessoa rdfs:label ?nomePessoa .
        FILTER(?nomePessoa = "João Silva")
    }
}
```

### CQ-3: Quais os diagnósticos do paciente?
```sparql
PREFIX : <http://www.semanticweb.org/ontologia-alergias#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?diagnostico ?problema
WHERE {
    ?paciente a :Pessoa ;
              rdfs:label "João Silva" ;
              :temDiagnostico ?diagnostico .
    ?diagnostico :indica ?problema .
}
```

### Consulta Extra: Pegar todos os pacientes
```sparql
PREFIX : <http://www.semanticweb.org/ontologia-alergias#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?paciente ?nome
WHERE {
    ?paciente a :Pessoa ;
              rdfs:label ?nome .
}
```

### Consulta Extra: Pegar todos os alimentos
```sparql
PREFIX : <http://www.semanticweb.org/ontologia-alergias#>

SELECT ?alimento
WHERE {
    ?alimento a :Alimento .
}
```
## 8. Aplicação Web
A aplicação web consiste em uma interface gráfica para melhor visualização das instâncias da ontologia e dos dados.
O Frontend foi construido com Next.js e o Backend foi feito com Java + Spring Boot + Apache Jena para rodar a ontologia, reasoner e para realização das consultas SPARQL.

## 9. Como Rodar a Aplicação Web

### 🔧 Backend (Spring Boot + Apache Jena)

O backend é uma API REST construída em **Spring Boot 3.x** utilizando **Java 17+** e **Apache Jena** para consultas SPARQL sobre a ontologia OWL2.

### Pré-requisitos:
- **Java 17+** (JDK 17 ou superior)
- **Maven 3.8+**
- **Protégé** (opcional, para visualizar a ontologia `.ttl`)
- Arquivo da ontologia: `ontologia-alergia-alimentar.ttl` no diretório configurado no projeto (ex.: `src/main/resources/ontologia-alergia-alimentar.ttl/` ou caminho absoluto).

### Como Rodar o Backend:
1. **Clone o projeto:**

2. **Compile e rode a aplicação:**
    ```bash
    mvn clean spring-boot:run
    ```

3. **Acesse a API REST:**
    - Exemplos de endpoints:
        - `GET http://localhost:8080/api/foods/non-allergen?patientName=João Silva`
        - `GET http://localhost:8080/api/patients`
        - `GET http://localhost:8080/api/patients/João Silva/diagnostics`

---

### 🌐 Frontend (Next.js)

O frontend foi construído com **Next.js (React + Server Side Rendering)**, consumindo as APIs REST do backend.

### Pré-requisitos:
- **Node.js 18+**
- **NPM** (ou **Yarn** se preferir)

### Como Rodar o Frontend:
1. **Navegue até a pasta do frontend:**
    ```bash
    cd ../frontend
    ```

2. **Instale as dependências:**
    ```bash
    npm install
    ```

3. **Rode o servidor de desenvolvimento:**
    ```bash
    npm run dev
    ```

4. **Acesse no navegador:**
    ```text
    http://localhost:3000
    ```

---

### 🌐 Comunicação entre Frontend e Backend
- O **frontend (Next.js)** irá fazer chamadas HTTP (REST API) para o **backend Spring Boot**.
- As consultas SPARQL são encapsuladas em endpoints REST no backend.
- Exemplos de rotas no frontend:
    - Página de alimentos não-alergênicos: `/foods/non-allergen?patientName=João Silva`
    - Página de diagnósticos de um paciente: `/patients/João Silva/diagnostics`

---

### ⚠️ Observações Importantes:
- Certifique-se de que o **backend esteja rodando em `localhost:8080`** antes de acessar o frontend.
- Caso o frontend rode em outra porta (ex.: 3000) e precise fazer requisições, configure corretamente o **proxy** ou utilize variáveis de ambiente (`NEXT_PUBLIC_API_URL`).
- Verifique se o arquivo `.ttl` da ontologia está acessível e no caminho correto configurado no backend.


## 10. Autores e Contato

* **Francinaldo Manoel dos Anjos Junior** - *Modelagem Conceitual, Implementação OWL e desenvolvimento da aplicação web*

## 11. Licença

Este projeto está licenciado sob a licença Creative Commons Atribuição 4.0 Internacional (CC-BY 4.0).






