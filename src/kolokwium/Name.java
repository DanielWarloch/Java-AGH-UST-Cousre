package kolokwium;


import lombok.Data;



@Data


public class Name {
    final Integer year;
    final String firstName;
    Integer count;
    final String sex;


    public Double countAsDouble(){
        return Double.parseDouble(count.toString());
    }
}
