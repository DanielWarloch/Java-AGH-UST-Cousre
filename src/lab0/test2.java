package lab0;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringJoiner;

class test {
    public static void main(String[] args) throws IOException {
        test t = new test();
        t.blog();
    }
    void post(Map<String,String> argsBlog) throws IOException {
        Random rand = new Random(System.currentTimeMillis());
        for (int j = 0; j < 3000; j++) {
            URL url = new URL("http://student.uci.agh.edu.pl/~msawczuk/public_html/wpis.php");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            Map<String,String> argsPost = new HashMap<>();
            StringJoiner sjPost = new StringJoiner("&");
            argsPost.put("nazwa_uzytkownika", argsBlog.get("nazwa_uzytkownika"));
            argsPost.put("haslo", argsBlog.get("haslo"));
            argsPost.put("post", String.valueOf(rand.nextInt(2100000000)));
            argsPost.put("date", "2019-12-09");
            argsPost.put("time", "20:17");
            for(Map.Entry<String,String> entry : argsPost.entrySet())
                sjPost.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] outPost = sjPost.toString().getBytes(StandardCharsets.UTF_8);
            int lengthPost = outPost.length;

                http.setFixedLengthStreamingMode(lengthPost);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(outPost);
            }
        }
    }
    void blog() throws IOException {
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < 3; i++) {
            URL url = new URL("http://student.uci.agh.edu.pl/~msawczuk/public_html/nowy.php");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
            Map<String,String> argsBlog = new HashMap<>();
            argsBlog.put("nazwa_bloga", String.valueOf(rand.nextInt(2100000000)));
            argsBlog.put("nazwa_uzytkownika", String.valueOf(rand.nextInt(2100000000))); // This is a fake password obviously
            argsBlog.put("haslo", String.valueOf(rand.nextInt(2100000000))); // This is a fake password obviously
            argsBlog.put("opis", String.valueOf(rand.nextInt(2100000000))); // This is a fake password obviously
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : argsBlog.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                        + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }
            post(argsBlog);
        }
    }
}
