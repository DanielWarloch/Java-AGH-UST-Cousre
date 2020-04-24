package kolokwium2020;

import java.io.IOException;
import java.util.stream.Collectors;


public class Zad1 {
    @org.junit.Test
    public void Test1() throws IOException {

        /* Zadanie 1
    W pliku wplatomaty-euronet.csv zebrano informacje o lokalizacji i godzinach funkcjonowania wpłatomatów. Zadeklaruj klasę ATM, która posłuży do przechowywania danych wpłatomatu z polami odpowiadającymi kolumnom tabeli i wczytaj te dane do pamięci.

    a) wypisz wszystkie wpłatomaty w województwie małopolskim, które są czynne całą dobę

    b) wypisz wszystkie wpłatomaty umieszczone poza bankami (w nazwie nie ma słowa tekstu Bank) które zostały otwarte przed 2017 rokiem (pole LiveDate)

    c) wypisz wszystkie placówki bankowe (ale tylko jeden raz)

    d) wypisz wszystkie adresy wpłatomatów (ulica, kod, miasto) posortowane według miast i ulic*/



        var ATMs = new ATMLIST();
        ATMs.read("test/kolokwium2020/wplatomaty-euronet.csv");

        ATMs.Lista.stream().filter(x -> x.getClient_access_hours().equals("00:00 - 23:59")).filter(x -> x.getDistrict().equals("Małopolskie")).forEach(System.out::println);

        ATMs.Lista.stream().filter(x -> Integer.parseInt(x.getLive_Date().substring(x.getLive_Date().length() - 4)) < 2017).filter(x -> !x.getName().toLowerCase().contains("bank")).forEach(System.out::println);

        System.out.println(ATMs.Lista.stream().filter(x-> x.getName().toLowerCase().contains("bank")).collect(Collectors.groupingBy(ATM::getName)));

        ATMs.sortInPlaceByExtractor(ATM::getAddress).sortInPlaceByExtractor(ATM::getCity).Lista.stream().limit(10).forEach(System.out::println);

    }
}
