package com.andersonoli;

import java.io.IOException;
import java.net.URI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApiAnki {
    public int showMaxPhrases;
    private final HttpClient client;

    public ApiAnki(int showMaxPhrases) {
        this.showMaxPhrases = showMaxPhrases;
        this.client = HttpClient.newHttpClient();
    }

    // function to request fraze.it and return a response
    private HttpResponse<String> makeApiRequest(String pathApi) throws Exception {
        final String API_URL = "https://fraze.it/api";
        final String API_KEY = "422744f7-d611-4444-865c-75c8cae49f46";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + pathApi + API_KEY))
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());

        //Daily quota exceeded (you have reached your 250 daily requests allowed)

        if(response.body().contains("Daily quota exceeded")){
            throw new Exception("Limite excedido!");
        }

        return response;
    }

    public ArrayList<String> getPhrase(String word) throws Exception {
        ArrayList<String> phrases = new ArrayList<>();
        String path = "/phrase/" + word + "/en/1/yes/";

        HttpResponse<String> response = makeApiRequest(path);

        String totalResults = (new JSONObject(response.body())).get("total").toString();
        JSONArray results = new JSONObject(response.body()).getJSONArray("results");

        int maxPhrases = Math.min(Integer.parseInt(totalResults), this.showMaxPhrases);

        for (int i = 0; i < maxPhrases; i++) {
            JSONObject jsonPhrases = new JSONObject(results.get(i).toString());
            phrases.add(jsonPhrases.get("phrase").toString());
        }

        return phrases;
    }

    public String getTranslation(String word) throws Exception {
        String path;

        if(word.contains("%")){
            path = "/translator/" + word.substring(0, word.indexOf("%")) + "/en/pt/";
        } else {
            path = "/translator/" + word + "/en/pt/";
        }

        HttpResponse<String> response = makeApiRequest(path);

        JSONObject responseResult = null;
        
        try {
            responseResult = new JSONObject(response.body());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        assert responseResult != null;
        return responseResult.get("result").toString();
    }
}
