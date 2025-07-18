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

* `ontologia-alergias.ttl`: **Arquivo principal da ontologia** em formato Turtle (OWL2). Este arquivo deve ser aberto no Protégé para exploração e testes.
* `Especificação de Requisitos da Ontologia (ORSD).pdf`: Contém o Documento de Requisitos da Ontologia, com a lista de perguntas que o modelo deve responder.
* `ontologia-alergia-alimentar.png`: Imagem do diagrama conceitual para fácil visualização.
* `ontologia-alergia-alimentar.vpp`: Arquivo do projeto do **Visual Paradigm**, para quem deseja editar ou explorar o modelo OntoUML.

## 6. Como Testar a Ontologia

1.  Instale o [Protégé Desktop](https://protege.stanford.edu/).
2.  Abra o arquivo `ontologia-alergias.ttl` no Protégé.
3.  Inicie um reasoner: vá ao menu `Reasoner` e clique em `Start reasoner` (recomenda-se HermiT ou Pellet).
4.  Navegue pelas classes e indivíduos na aba `Entities` para ver as novas classificações e relações inferidas (geralmente destacadas em amarelo).

## 7. Autores e Contato

* **Francinaldo Manoel** - *Modelagem Conceitual e Implementação OWL*

## 8. Licença

Este projeto está licenciado sob a licença Creative Commons Atribuição 4.0 Internacional (CC-BY 4.0).
