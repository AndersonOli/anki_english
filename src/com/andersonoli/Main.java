package com.andersonoli;

import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        ApiAnki api = new ApiAnki(3);
        FilesManipulator filesManipulator = new FilesManipulator();

        ArrayList<String> words = filesManipulator.readWords();

        for (String word : words) {
            try {
                for (String phrase : api.getPhrase(word)) {
                    filesManipulator.saveData(phrase);
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.toString());
            }

            filesManipulator.saveData("\n" + word + ": " + api.getTranslation(word) + "\n");
        }

        System.out.println("Done!");
    }
}
