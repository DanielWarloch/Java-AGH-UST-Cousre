package kolokwium;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

/*      Zadanie 1
         a) Wczytaj dane z pliku do pamieci

         b) Podaj ile ksiazek wydano w kolejnych latach (policz ksiazki z kolejnych lat i wypisz)

         c) Wypisz wszystkie ksiazki wydane przez Wydawnictwo Naukowe PWN

         d) Podaj ile ksiazek nalezy do kazdej z kategorii

         Ibuk ID;    Tytuł;    Autor;    ISBN;    Wydawnictwo;    Rok wydania;    Kategoria;    Podkategoria;    Link do książki

*/


public class Test {
    @org.junit.Test
    public void Test1() throws IOException {
        var books = new Zad1();
        books.read("test/kolokwium/ibuk_wykaz_pozycji.csv");
        System.out.print(books.countDistinctValue(Obiekt::getRok_wydania) + "\n\r");
        books.Lista.stream().filter(p -> p.getWydawnictwo().equals("Wydawnictwo Naukowe PWN")).limit(10).forEach(System.out::println);
        System.out.print(books.countDistinctValue(Obiekt::getKategoria) + "\n\r");
    }
    @org.junit.Test
    public void Test2() throws Exception {
        var words = new Zad2();

        words.read("test/kolokwium/w-pustyni.txt", Charset.forName("cp1250"));
        words.splitByRegex("[\\s|\\r|\\,|\\.|\\-|\\!|\\—|\\?|\\;|\\:|\\”|\\„|\\…]+");
        words.printMax();
    }
    @org.junit.Test
    public void Test3() throws Exception {
        var namesList = new NamesList();
        namesList.read("test/kolokwium/imiona-2000-2016.csv");
        System.out.println("Sum of births by years");
        System.out.print(namesList.countDistinctValue(Name::getYear) + "\n");
        System.out.println("Sum of all births");
        System.out.print(namesList.sumByGetter(Name::getCount) + "\n");


        System.out.println("Most popular name in 2000-2016");

        System.out.print(namesList.filter(x -> x.getSex().equals("M")).sumBirthValue(Name::getFirstName).entrySet().stream().max(Map.Entry.comparingByValue()) + "\n");
        System.out.print(namesList.filter(x -> x.getSex().equals("K")).sumBirthValue(Name::getFirstName).entrySet().stream().max(Map.Entry.comparingByValue()) + "\n");


        System.out.println("PopularityListBySexAndYear");

        var LM2000 = namesList.popularityListBySexAndYear("M",2000);
        var LK2000 = namesList.popularityListBySexAndYear("K",2000);
        var LM2016 = namesList.popularityListBySexAndYear("M",2016);
        var LK2016 = namesList.popularityListBySexAndYear("K",2016);
/*        System.out.print(LM2000 + "\n\r");
        System.out.print(LK2000 + "\n\r");
        System.out.print(LM2016 + "\n\r");
        System.out.print(LK2016 + "\n\r");
        LM2000.replaceAll((k,v) -> v * -1.0);
        LK2000.replaceAll((k,v) -> v * -1.0);
        var LM = Stream.of(LM2000, LM2016).flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::sum));
        var LK = Stream.of(LK2000, LK2016).flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::sum));*/

        var LM = Stream.of(LM2000, LM2016).flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (s, b) -> b / s));
        var LK = Stream.of(LK2000, LK2016).flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (s, b) -> b / s));

        LM.entrySet().forEach((t) -> {if(!LM2000.containsKey(t.getKey())){t.setValue(POSITIVE_INFINITY);}});
        LK.entrySet().forEach((t) -> {if(!LK2000.containsKey(t.getKey())){t.setValue(POSITIVE_INFINITY);}});
        LM.entrySet().forEach((t) -> {if(!LM2016.containsKey(t.getKey())){t.setValue(NEGATIVE_INFINITY);}});
        LK.entrySet().forEach((t) -> {if(!LK2016.containsKey(t.getKey())){t.setValue(NEGATIVE_INFINITY);}});

        var LM_f = LM.entrySet().stream().filter(x -> !x.getValue().isInfinite()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        var LK_f = LK.entrySet().stream().filter(x -> !x.getValue().isInfinite()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



        System.out.println("Names popularity change 2016/2000 without INFINITY and - INFINITY in %");
        System.out.print(LK_f.entrySet().stream().min(Map.Entry.comparingByValue()) + "\n");
        System.out.print(LK_f.entrySet().stream().max(Map.Entry.comparingByValue()) + "\n");
        System.out.print(LM_f.entrySet().stream().min(Map.Entry.comparingByValue()) + "\n");
        System.out.print(LM_f.entrySet().stream().max(Map.Entry.comparingByValue()) + "\n");




        System.out.println("Names popularity change 2016/2000 with INFINITY and - INFINITY in %");
        System.out.print(LK.entrySet().stream().min(Map.Entry.comparingByValue()) + "\n");
        System.out.print(LK.entrySet().stream().max(Map.Entry.comparingByValue()) + "\n");
        System.out.print(LM.entrySet().stream().min(Map.Entry.comparingByValue()) + "\n");
        System.out.print(LM.entrySet().stream().max(Map.Entry.comparingByValue()) + "\n");


    }
}
