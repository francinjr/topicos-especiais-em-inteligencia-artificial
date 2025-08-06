package com.francinjr.rentalbusiness.components.food.infrastructure.persistence.apachejena;

import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Component;

@Component
public class FoodJenaRepository {

    public List<String> findAllAllergenFoodsByPatientName(String patientName) {
        Model baseModel = FileManager.get().loadModel("ontologia-alergia-alimentar.ttl");

        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, baseModel);

        String sparqlQueryString =
                "PREFIX : <http://www.semanticweb.org/ontologia-alergias#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "SELECT ?alimento WHERE { " +
                        "  { " +
                        "    SELECT ?nomePessoa ?alimento WHERE { " +
                        "        ?alimento a :Alimento ; " +
                        "                  :possuiSequencia ?sequencia . " +
                        "        ?sequencia :geraReacaoEmPessoaComProblema ?pessoa . " +
                        "        ?pessoa rdfs:label ?nomePessoa . " +
                        "    } " +
                        "  } " +
                        "  FILTER(?nomePessoa = ?nomeParam) " +
                        "}";

        ParameterizedSparqlString queryStr = new ParameterizedSparqlString(sparqlQueryString);
        queryStr.setLiteral("nomeParam", patientName);

        List<String> alimentosSeguros = new ArrayList<>();
        Query query = QueryFactory.create(queryStr.toString());
        try (QueryExecution qexec = QueryExecutionFactory.create(query, infModel)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();

                RDFNode alimentoNode = soln.get("alimento");

                if (alimentoNode.isResource()) {
                    Resource alimentoResource = alimentoNode.asResource();
                    String localName = alimentoResource.getLocalName();
                    alimentosSeguros.add(localName);
                }
            }
        }

        return alimentosSeguros;
    }


    public List<String> findAllNonAllergenFoodsByPatientName(String nomePessoa) {
        Model baseModel = FileManager.get().loadModel("ontologia-alergia-alimentar.ttl");
        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, baseModel);

        String sparqlQueryString =
                "PREFIX : <http://www.semanticweb.org/ontologia-alergias#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "SELECT ?alimento_seguro WHERE { " +
                        "    ?alimento_seguro a :Alimento . " +
                        "    MINUS { " +
                        "        ?alimento_seguro :possuiSequencia ?sequencia . " +
                        "        ?sequencia :geraReacaoEmPessoaComProblema ?pessoa . " +
                        "        ?pessoa rdfs:label ?nomePessoa . " +
                        "        FILTER(?nomePessoa = ?nomeParam) " +
                        "    } " +
                        "}";

        ParameterizedSparqlString queryStr = new ParameterizedSparqlString(sparqlQueryString);
        queryStr.setLiteral("nomeParam", nomePessoa);

        List<String> alimentosSeguros = new ArrayList<>();
        Query query = QueryFactory.create(queryStr.toString());
        try (QueryExecution qexec = QueryExecutionFactory.create(query, infModel)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                RDFNode alimentoNode = soln.get("alimento_seguro");
                if (alimentoNode.isResource()) {
                    Resource alimentoResource = alimentoNode.asResource();
                    String localName = alimentoResource.getLocalName();
                    alimentosSeguros.add(localName);
                }
            }
        }

        return alimentosSeguros;
    }

    public List<String> findAllFoods() {
        List<String> alimentos = new ArrayList<>();

        try {
            Model baseModel = FileManager.get().loadModel("ontologia-alergia-alimentar.ttl");

            Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
            InfModel infModel = ModelFactory.createInfModel(reasoner, baseModel);

            String sparqlQueryString =
                    "PREFIX : <http://www.semanticweb.org/ontologia-alergias#> " +
                            "SELECT ?alimento WHERE { " +
                            "    ?alimento a :Alimento . " +
                            "}";

            Query query = QueryFactory.create(sparqlQueryString);
            try (QueryExecution qexec = QueryExecutionFactory.create(query, infModel)) {
                ResultSet results = qexec.execSelect();
                while (results.hasNext()) {
                    QuerySolution soln = results.nextSolution();
                    RDFNode alimentoNode = soln.get("alimento");

                    if (alimentoNode != null && alimentoNode.isResource()) {
                        Resource alimentoResource = alimentoNode.asResource();
                        String localName = alimentoResource.getLocalName();
                        alimentos.add(localName);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return alimentos;
    }

}
