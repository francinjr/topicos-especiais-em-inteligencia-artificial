# Ontologia de Alergia Alimentar (OAA)

![Licença](https://img.shields.io/badge/license-CC--BY--4.0-blue.svg)

Bem-vindo(a) ao repositório da Ontologia de Alergia Alimentar. Este projeto visa criar um modelo de conhecimento formal e estruturado sobre alergias alimentares para apoiar profissionais da saúde, pesquisadores e pacientes.

## 1. Qual o objetivo deste projeto?

Alergias alimentares são condições complexas. O diagnóstico e o manejo exigem a conexão de diversas informações: o que o paciente comeu, quais sintomas apresentou, quais são os componentes moleculares dos alimentos e como eles podem interagir.

Este projeto busca organizar esse conhecimento de forma lógica e sem ambiguidades. O resultado é uma **ontologia**: um "mapa conceitual" inteligente que não apenas armazena os dados, mas também entende as relações entre eles.

O objetivo final é criar uma base de conhecimento para:
* Apoiar profissionais da saúde (nutricionistas, alergologistas, etc.) na identificação de alimentos alergênicos e reações adversas.
* Auxiliar na recomendação de alternativas alimentares seguras.
* Servir como base para sistemas que realizem análises baseadas no perfil dos pacientes.

## 2. Entendendo o Modelo Conceitual

Para que a ontologia seja útil, ela precisa refletir a realidade clínica. Abaixo, descrevemos os principais "blocos de construção" do nosso modelo de forma acessível.

**Visualize o diagrama completo em `ontologia-alergia-alimentar.png`.

#### Bloco 1: O Paciente e o Contexto Clínico 🩺

O centro de tudo é a **Pessoa**, que, no contexto da saúde, pode assumir papéis específicos. Os dois papéis principais são:
* **Paciente**: Uma pessoa que sobre ou está em risco de sofrer uma reação alérgica.
* **Médico**: O profissional de saúde que realiza o diagnóstico e acompanha o paciente.

#### Bloco 2: Alimentos e seus Componentes Alergênicos 🍲

* **Alimento**: É a entidade que um paciente consome. Cada alimento (`Leite`, `Ovo`) é composto por nutrientes, como **Proteínas** e **Carboidratos**.
* **Substância Alergênica**: Dentro de um alimento, existem componentes específicos que são os verdadeiros gatilhos das alergias. Chamamos essas moléculas de `Substância Alergênica`. Elas são a "parte" do alimento que o sistema imunológico reconhece como uma ameaça.
    * Por exemplo, no leite, a **caseína** é uma substância alergênica. No amendoim, o **Ara h 2** é outra.
* **Sequência de Aminoácidos**: Para identificar essas substâncias com precisão, o modelo reconhece que elas são definidas por sua `Sequência de Aminoácidos`.

#### Bloco 3: A Reação Alérgica e a Reatividade Cruzada 💥

* **Reação Alérgica**: A reação é um **acontecimento**. Ela é desencadeada quando uma `Substância Alergênica` se liga a um **Anticorpo** específico (como o **IgE**). O `Paciente` é quem "sofre" essa reação.
* **Reatividade Cruzada**: O modelo também representa o fenômeno da reatividade cruzada. Isso acontece quando a sensibilização a uma proteína causa uma reação a outra proteína similar em um alimento diferente.

#### Bloco 4: O Diagnóstico (A Investigação e a Conclusão) 📝

* **A Ação**: Para confirmar uma alergia, um `Médico` realiza um **Procedimento de Diagnóstico**. No nosso modelo, isso também é um **acontecimento**.
* **O Resultado**: O resultado desse procedimento é o **Diagnóstico**. Ele não é um objeto físico, mas sim uma **conclusão formal** que descreve a condição do paciente (ex: "Alergia à Proteína do Leite de Vaca Mediada por IgE").

## 3. Navegando no Repositório

* `Especificação de Requisitos da Ontologia (ORSD).pdf`: Contém o **Documento de Requisitos da Ontologia**, com a lista de perguntas que o modelo deve responder.
* `ontologia-alergia-alimentar.png`: Imagem do diagrama completo para fácil visualização.
* `ontologia-alergia-alimentar.vpp`: Arquivo do projeto do **Visual Paradigm**, para quem deseja editar ou explorar o modelo OntoUML.

## 4. Autores e Contato

* **Francinaldo Manoel** - *Modelagem Conceitual* 

## 5. Licença

Este projeto está licenciado sob a licença Creative Commons Atribuição 4.0 Internacional (CC-BY 4.0).
