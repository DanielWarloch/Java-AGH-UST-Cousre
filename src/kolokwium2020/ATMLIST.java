package kolokwium2020;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.sql.Types.NULL;
public class ATMLIST {




   /* Zadanie 1
    W pliku wplatomaty-euronet.csv zebrano informacje o lokalizacji i godzinach funkcjonowania wpłatomatów. Zadeklaruj klasę ATM, która posłuży do przechowywania danych wpłatomatu z polami odpowiadającymi kolumnom tabeli i wczytaj te dane do pamięci.

    a) wypisz wszystkie wpłatomaty w województwie małopolskim, które są czynne całą dobę

    b) wypisz wszystkie wpłatomaty umieszczone poza bankami (w nazwie nie ma słowa tekstu Bank) które zostały otwarte przed 2017 rokiem (pole LiveDate)

    c) wypisz wszystkie placówki bankowe (ale tylko jeden raz)

    d) wypisz wszystkie adresy wpłatomatów (ulica, kod, miasto) posortowane według miast i ulic*/



    @NonNull
    public CsvReader csvReader;
    public List<ATM> Lista = new ArrayList<>();



    Pattern p = Pattern.compile("^([0-9]+)([a-z]?)$");

    /**
     * Converts a string to a sortable integer, ensuring proper ordering:
     * "1" becomes 256
     * "1a" becomes 353
     * "1b" becomes 354
     * "2" becomes 512
     * "100" becomes 25600
     * "100a" becomes 25697
     */
    int getSortOrder(String s) {
        Matcher m = p.matcher(s);
        if(!m.matches()) return 0;
        int major = Integer.parseInt(m.group(1));
        int minor = m.group(2).isEmpty() ? 0 : m.group(2).charAt(0);
        return (major << 8) | minor;
    }




    public ATMLIST() {
    }

    long a = NULL;

    private ATMLIST(List<ATM> Lista) {
        this.Lista.addAll(Lista);
    }

    public static void main(String[] args) {

    }

    public ATM tryReadATM() {
        var atm = new ATM(csvReader.get(0));
        atm.setName(csvReader.getOrNull(csvReader::get, 1));
        atm.setCity(csvReader.getOrNull(csvReader::get, 2));
        atm.setAddress(csvReader.getOrNull(csvReader::get, 3));
        atm.setPost_code(csvReader.getOrNull(csvReader::get, 4));
        atm.setDistrict(csvReader.getOrNull(csvReader::get, 5));
        atm.setLive_Date(csvReader.getOrNull(csvReader::get, 6));
        atm.setClient_access_hours(csvReader.getOrNull(csvReader::get, 7));

        return atm;
    }

    public void read(String filename,String Regex) throws IOException {
        csvReader = new CsvReader(filename, ";", true);
        csvReader.setRegex(Regex);
        while (csvReader.next()) {
            var atm = tryReadATM();
            Lista.add(atm);
        }
    }
    public void read(String filename) throws IOException {
        csvReader = new CsvReader(filename, ";", true);

        while (csvReader.next()) {
            var atm = tryReadATM();
            Lista.add(atm);
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

    public <K> Map<K, Long> countDistinctValue(Function<ATM, K> getExtractor) {
        return Lista.stream().collect(Collectors.groupingBy(getExtractor, Collectors.counting()));
    }

    public ATMLIST sortInplace(Comparator<ATM> comparator) {
        this.Lista.sort(comparator);
        return this;
    }

    public ATMLIST sortInPlaceByExtractor(Function<ATM, String> getExtractor){
        class CustomComparator implements Comparator<ATM>
        {
            @Override
            public int compare(ATM left, ATM right) {
                return getExtractor.apply(left).compareTo(getExtractor.apply(right));
            }
        }
        this.Lista.sort(new CustomComparator());
        return this;
    }

    public ATMLIST sort(Comparator<ATM> comparator) {
        var result = new ATMLIST(this.Lista);
        result.Lista.sort(comparator);
        return this;
    }

    ATMLIST filter(Predicate<ATM> pred) {
        return new ATMLIST(this.Lista
                .stream()
                .filter(pred)
                .collect(Collectors.toList()));
    }

    ATMLIST filter(Predicate<ATM> pred, int limit) {
        return new ATMLIST(this.Lista
                .stream()
                .filter(pred)
                .limit(limit)
                .collect(Collectors.toList()));
    }

    ATMLIST filter(Predicate<ATM> pred, int offset, int limit) {
        return new ATMLIST(this.Lista
                .stream()
                .filter(pred)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }


}









