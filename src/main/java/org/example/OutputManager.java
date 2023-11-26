package org.example;

import jakarta.json.JsonValue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class OutputManager {
    String outputMethod;
    CatFactsCollection collection;

    public OutputManager (String outputMethod, CatFactsCollection collection){
        this.outputMethod = outputMethod;
        this.collection = collection;
    }

    public void writeFacts() throws IOException {
        int counter=0;
        for (CatFact fact: collection.facts) {
            this.writeFact(fact,counter);
            counter++;
        }
    }

    private void writeFact(CatFact fact, int counter) throws IOException {
        if (Objects.equals(outputMethod, "stdout")) {
            writeToStdout(fact);
        } else if (Objects.equals(outputMethod, "file")) {
            writeToFile(counter, fact);
        } else {
            System.out.println("unknown format");
            System.exit(0);
        }
    }

    private void writeToStdout(CatFact fact) {
        System.out.println(fact.fact);
    }

    private void writeToFile(int counter, CatFact fact) throws IOException {
        FileWriter file = new FileWriter(String.format("%d.txt", counter));
        file.write(fact.fact);
        file.close();
    }
}