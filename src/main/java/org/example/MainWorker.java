package org.example;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/** Сейчас между методами вводы/вывода ходят неструктурированны данные (JsonObject,[]string), хотя этим методам
  * необязательно знать как парсить json. Если заменить это на модель - то эти методы станут независимыми от способа их
  * получения(например если надо будет читать из csv, или поле в json переименуется) **/

public class MainWorker {
    String[] args;
    Integer factsNumber;
    String outputMethod;
    CatFactsCollection factsCollection;

    public MainWorker(String[] args){
        this.args = args;
    }
    private void parseArgs(){
        this.checkArgs();
        this.factsNumber = Integer.valueOf(this.args[0]);
        this.outputMethod = this.args[1];
    }

    private void checkArgs(){
        if (this.args.length < 2) {
            System.out.println("No enough arguments passed");
            System.exit(0);
        }
        if (!args[1].equals("file") && !args[1].equals("stdout")){
            System.out.println("Unclear how to process output");
            System.exit(0);
        }
    }

    public void process() throws IOException {
        this.parseArgs();
        HttpURLConnection con = new ConnectionBuilder(this.factsNumber).buildConnection();
        JsonObject data = fetchData(con);
        this.getCatKoans(data);
        con.disconnect();
        this.printKoans();
    }

    private void getCatKoans(JsonObject data) {
        for (JsonValue item : data.get("data").asJsonArray()) {
            processItem(item);
        }
    }

    private JsonObject fetchData(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        JsonReader jsonReader = Json.createReader(in);
        return jsonReader.readObject();
    }
    private void processItem(JsonValue item) {
        if(this.factsCollection == null){
            factsCollection = new CatFactsCollection();
            factsCollection.facts = new ArrayList<>();
        }
        factsCollection.facts.add(new CatFact(item));
    }

     private void printKoans() throws IOException {
         OutputManager outputManager = new OutputManager(this.outputMethod, this.factsCollection);
         outputManager.writeFacts();
     }
}
