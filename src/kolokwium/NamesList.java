package kolokwium;

import lab6.CsvReader.CsvReader;
import lombok.NonNull;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.sql.Types.NULL;

public class NamesList {

    @NonNull
    public CsvReader csvReader;
    public List<Name> Lista = new ArrayList<>();


    public NamesList() {
    }

    long a = NULL;

    private NamesList(List<Name> Lista) {
        this.Lista.addAll(Lista);
    }

    public static void main(String[] args) {

    }


    public Map<String, Double> popularityListBySexAndYear(String sex, Integer year){
        var tmp = Lista.stream().filter(x -> x.getSex().equals(sex) && x.getYear().equals(year)).collect(Collectors.toMap(Name::getFirstName, z -> Double.parseDouble(z.getCount().toString())));
        var sum = tmp.values().stream().mapToDouble(i -> i).sum();
        tmp.replaceAll((k,v) -> v/sum);
        return tmp;
    }



    public Name tryReadNames() {
        var name = new Name(csvReader.getInt(0), csvReader.get(1), csvReader.get(3));
        name.setCount(csvReader.getInt(2));
        return name;
    }

    public void read(String filename, String Regex) throws IOException {
        csvReader = new CsvReader(filename, ";", true);
        csvReader.setRegex(Regex);
        while (csvReader.next()) {
            var Name = tryReadNames();
            Lista.add(Name);
        }
    }

    public void read(String filename) throws IOException {
        csvReader = new CsvReader(filename, ";", true);
        while (csvReader.next()) {
            var Name = tryReadNames();
            Lista.add(Name);
        }
    }

    public void list(PrintStream out, int offset, int limit) {
        this.Lista.stream()
                .skip(offset)
                .limit(limit)
                .forEach(out::println);
    }

    public void list(PrintStream out) {
        this.Lista.forEach(out::println);
    }

    public <K> Map<K, Long> countDistinctValue(Function<Name, K> getExtractor) {
        return Lista.stream().collect(Collectors.groupingBy(getExtractor, Collectors.counting()));
    }

    public <K> Map<K, Integer> sumBirthValue(Function<Name, K> nameExtractor) {
        return Lista.stream().collect(Collectors.groupingBy(nameExtractor, Collectors.summingInt(Name::getCount)));
    }

    public Double averageByGetter(Function<Name, Double> getExtractor) {
        return (sumByGetter(getExtractor) / this.Lista.size());
    }

    public Double sumByGetter(Function<Name, ?> getExtractor) {
        var sum = 0.0;

//        var sum1 = (Double) this.Lista.stream().mapToDouble((ToDoubleFunction<? super Name>) getExtractor).sum();


        for (Name o : this.Lista) {
            sum += Double.parseDouble(getExtractor.apply(o).toString());
        }
        return sum;
    }

    public NamesList sortInplace(Comparator<Name> comparator) {
        this.Lista.sort(comparator);
        return this;
    }

    public NamesList sort(Comparator<Name> comparator) {
        var result = new NamesList(this.Lista);
        result.Lista.sort(comparator);
        return this;
    }

    NamesList filter(Predicate<Name> pred) {
        return new NamesList(this.Lista
                .stream()
                .filter(pred)
                .collect(Collectors.toList()));
    }

    NamesList filter(Predicate<Name> pred, int limit) {
        return new NamesList(this.Lista
                .stream()
                .filter(pred)
                .limit(limit)
                .collect(Collectors.toList()));
    }

    NamesList filter(Predicate<Name> pred, int offset, int limit) {
        return new NamesList(this.Lista
                .stream()
                .filter(pred)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }


}