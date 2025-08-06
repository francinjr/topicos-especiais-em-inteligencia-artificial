package com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.apachejena;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.FileManager;
import org.springframework.stereotype.Component;

@Component
public class CustomerJenaRepository {
    public List<String> findAllPatients() {
        List<String> pacientes = new ArrayList<>();

        Model baseModel = FileManager.get().loadModel("ontologia-alergia-alimentar.ttl");
        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
        InfModel infModel = ModelFactory.createInfModel(reasoner, baseModel);

        String sparqlQueryString =
                "PREFIX : <http://www.semanticweb.org/ontologia-alergias#> " +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                        "SELECT ?pessoa ?nomePessoa WHERE { " +
                        "    ?pessoa a :Pessoa ; " +
                        "            rdfs:label ?nomePessoa . " +
                        "}";

        Query query = QueryFactory.create(sparqlQueryString);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, infModel)) {
            ResultSet results = qexec.execSelect();
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution();

                RDFNode nomeNode = soln.get("nomePessoa");

                if (nomeNode != null && nomeNode.isLiteral()) {
                    String nome = nomeNode.asLiteral().getString();
                    pacientes.add(nome);
                }
            }
        }

        return pacientes;
    }

}
