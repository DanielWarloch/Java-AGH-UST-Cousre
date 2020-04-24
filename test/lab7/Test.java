package lab7;
import java.io.IOException;

public class Test {
    @org.junit.Test
    public void Test1() throws IOException {
        var adminUnitList = new AdminUnitList();
        adminUnitList.read("test/lab7/admin-units.csv");
        adminUnitList.list(System.out,5000,10);
//        adminUnitList.selectByName(".*[kK]rak.*",true).list(System.out);
    }
    public  void Test2() throws IOException {
        var adminUnitList = new AdminUnitList();
        adminUnitList.read("test/lab7/admin-units.csv");



//        adminUnitList.getNeighbors2();
    }
}
