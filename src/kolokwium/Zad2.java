package kolokwium;

import lab6.CsvReader.CsvReader;
import lombok.NonNull;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Zad2 {


    @NonNull
    public CsvReader csvReader;
    public Map<String, Integer> words = new HashMap<>();
    String content;

    public Zad2() {
    }

    public static void main(String[] args) throws Exception {
    }

    public <K, V extends Comparable<V>> Map.Entry<K, V> maxMap(Map<K, V> map) {
        Optional<Map.Entry<K, V>> maxEntry = map.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue));
        return maxEntry.get();
    }

    public void printMax() {
        var max = maxMap(words);
        System.out.printf("Word = %s  Count = %d\n", max.getKey(), max.getValue());
    }

    public void list(PrintStream out, int offset, int limit) {
        this.words.entrySet().stream()
                .skip(offset)
                .limit(limit)
                .forEach(out::println);
    }

    public void list(PrintStream out) {
        this.words.entrySet().forEach(out::println);
    }

    public String convertStreamToString(InputStream is, Charset charset) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public void read(String fileName, Charset charset) throws Exception {
        content = getStringFromFile(fileName, charset).toLowerCase();
    }

    public String getStringFromFile(String filePath, Charset charset) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin, charset);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public void splitByRegex(String regex) {
        String[] splitted = content.split(regex);
        for (String word : splitted) {
            Integer wordNumber = words.get(word);
            if (wordNumber == null) {
                wordNumber = 1;
            } else {
                wordNumber++;
            }
            words.put(word, wordNumber);
        }
    }
}

