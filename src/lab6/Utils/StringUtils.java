package lab6.Utils;

public class StringUtils {
    public static boolean isNullOrWhitespace(String string){
        return string == null || string.trim().isEmpty();
    }
}
