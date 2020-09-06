package com.andersonoli;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FilesManipulator {
    final String readFile = "C:\\Users\\Zurg\\Desktop\\my-words.txt";
    final String saveFile = "C:\\Users\\Zurg\\Desktop\\words.txt";

    public FilesManipulator(){
        this.deleteFile();
    }

    public ArrayList<String> readWords(){
        ArrayList<String> words = new ArrayList<>();

        try {
            File targedFile = new File(this.readFile);
            Scanner reader = new Scanner(targedFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine().replace(" ", "%20");
                words.add(line);
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler o arquivo!");
        }

        return words;
    }

    public void saveData(String str) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.saveFile, true));
            writer.append("\n").append(str);

            writer.close();
        } catch (Exception e) {
            System.out.println("Erro ao salvar no arquivo!");
        }
    }

    public void deleteFile(){
        try {
            File targedFile = new File(this.saveFile);
            if(targedFile.delete()){
                System.out.println("Deletado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar o arquivo!");
        }
    }
}
