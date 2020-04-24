package lab6.CsvReader;

import lab6.Utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

public class CsvReader{
    private final BufferedReader reader;

    private final String delimiter;
    private final boolean hasHeader;
    String groupCharacter = "\"";
    String regex = "";
    private final List<String> columnLabels = new ArrayList<>();
    private final Map<String,Integer> columnLabelsToInt = new HashMap<>();

    private List<String> currentRow = new ArrayList<>();

    public CsvReader(String filename, String delimiter, boolean hasHeader) throws IOException {
        this(Files.newBufferedReader(Paths.get(filename)),delimiter,hasHeader);
    }
    public CsvReader(String filename,String delimiter) throws IOException {
        this(filename,delimiter,false);
    }
    public CsvReader(String filename) throws IOException {
        this(filename,",",false);
    }
    public CsvReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
        this.reader = reader instanceof BufferedReader? (BufferedReader) reader :  new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        this.groupCharacter = "\"";
        this.regex = String.format("%s(?=(?:[^\\%s]*\\%s[^\\%s]*\\%s)*[^\\%s]*$)",delimiter,groupCharacter,
                groupCharacter,groupCharacter,groupCharacter,groupCharacter);
        if(hasHeader)
            parseHeader();
    }
    public boolean next() throws IOException {
        this.currentRow.clear();
        String line = this.reader.readLine();
        if(line == null)
            return false;
        currentRow.clear();
        currentRow.addAll(Arrays.asList(split(line)));
        return true;
    }

    public List<String> getColumnLabels(){

        return Collections.unmodifiableList(this.columnLabels);
    }

    public int getRecordLength(){
        return this.currentRow.size();
    }

    public boolean isMissing(int columnIndex){
        if(columnIndex < 0 || columnIndex >= this.currentRow.size())
            return true;
        return StringUtils.isNullOrWhitespace(this.currentRow.get(columnIndex));
    }
    public  boolean isMissing(String columnLabel){
        Integer index = this.columnLabelsToInt.get(columnLabel);
        if(index == null)
            return true;
        return isMissing(index);
    }

    public String get(int columnIndex){
        return this.currentRow.get(columnIndex);
    }

    public int getInt(int columnIndex){
        return Integer.parseInt(get(columnIndex));
    }

    public long getLong(int columnIndex){
        return Long.parseLong(get(columnIndex));
    }

    public double getDouble(int columnIndex){
        return Double.parseDouble(get(columnIndex));
    }

    private void parseHeader() throws IOException {
        String firstLine = this.reader.readLine();
        this.columnLabels.addAll(Arrays.asList(split(firstLine)));
        for (int i = 0; i < columnLabels.size(); i++) {
            this.columnLabelsToInt.put(columnLabels.get(i),i);
        }
    }

    public LocalTime getTime(int columnIndex,String format) {
        return LocalTime.parse(get(columnIndex),DateTimeFormatter.ofPattern(format));
    }

    public LocalDate getDate(int columnIndex, String format) {
        return LocalDate.parse(get(columnIndex),DateTimeFormatter.ofPattern(format));
    }

    public <T> T getOrNull(Function<Integer,T>func,int index){
        if(isMissing(index))
            return null;
        return func.apply(index);
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    private String[] split(String line) {
        String groupCharacter = "\"";
        String regex = String.format("%s(?=(?:[^\\%s]*\\%s[^\\%s]*\\%s)*[^\\%s]*$)",delimiter,groupCharacter,
                groupCharacter,groupCharacter,groupCharacter,groupCharacter);
        return line.trim().split(regex);
    }
}
