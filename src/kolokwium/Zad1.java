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

public class Zad1 {


    /*
         a) Wczytaj dane z pliku do pamieci

         b) Podaj ile ksiazek wydano w kolejnych latach (policz ksiazki z kolejnych lat i wypisz)

         c) Wypisz wszystkie ksiazki wydane przez Wydawnictwo Naukowe PWN

         d) Podaj ile ksiazek nalezy do kazdej z kategorii

         Ibuk ID;    Tytuł;    Autor;    ISBN;    Wydawnictwo;    Rok wydania;    Kategoria;    Podkategoria;    Link do książki

*/
    @NonNull
    public CsvReader csvReader;
    public List<Obiekt> Lista = new ArrayList<>();

    public Zad1() {
    }

    long a = NULL;

    private Zad1(List<Obiekt> Lista) {
        this.Lista.addAll(Lista);
    }

    public static void main(String[] args) {

    }

    public Obiekt tryReadBook() {
        var book = new Obiekt(csvReader.getInt(0));
        book.setTytul(csvReader.getOrNull(csvReader::get, 1));
        book.setAutor(csvReader.getOrNull(csvReader::get, 2));
        book.setISBN(csvReader.getOrNull(csvReader::get, 3));
        book.setWydawnictwo(csvReader.getOrNull(csvReader::get, 4));
        book.setRok_wydania(csvReader.getOrNull(csvReader::getInt, 5));
        book.setKategoria(csvReader.getOrNull(csvReader::get, 6));
        book.setPodkategoria(csvReader.getOrNull(csvReader::get, 7));
        book.setLink_do_książki(csvReader.getOrNull(csvReader::get, 8));

        return book;
    }

    public void read(String filename,String Regex) throws IOException {
        csvReader = new CsvReader(filename, ";", true);
        csvReader.setRegex(Regex);
        while (csvReader.next()) {
            var obiekt = tryReadBook();
            Lista.add(obiekt);
        }
    }
    public void read(String filename) throws IOException {
        csvReader = new CsvReader(filename, ";", true);
        while (csvReader.next()) {
            var obiekt = tryReadBook();
            Lista.add(obiekt);
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

    public <K> Map<K, Long> countDistinctValue(Function<Obiekt, K> getExtractor) {
        return Lista.stream().collect(Collectors.groupingBy(getExtractor, Collectors.counting()));
    }
    public Number countValue() {
        var sum = 0;
        for (Obiekt o: this.Lista) {
            sum += o.getRok_wydania();
        }
        return sum;
    }

    public Zad1 sortInplace(Comparator<Obiekt> comparator) {
        this.Lista.sort(comparator);
        return this;
    }

    public Zad1 sort(Comparator<Obiekt> comparator) {
        var result = new Zad1(this.Lista);
        result.Lista.sort(comparator);
        return this;
    }

    Zad1 filter(Predicate<Obiekt> pred) {
        return new Zad1(this.Lista
                .stream()
                .filter(pred)
                .collect(Collectors.toList()));
    }

    Zad1 filter(Predicate<Obiekt> pred, int limit) {
        return new Zad1(this.Lista
                .stream()
                .filter(pred)
                .limit(limit)
                .collect(Collectors.toList()));
    }

    Zad1 filter(Predicate<Obiekt> pred, int offset, int limit) {
        return new Zad1(this.Lista
                .stream()
                .filter(pred)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }


}

