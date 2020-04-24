package lab7;

import lab6.CsvReader.CsvReader;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.sql.Types.NULL;

public class AdminUnitList {
    private List<AdminUnit> units = new ArrayList<>();
    private Map<Long, AdminUnit> idToUnit = new HashMap<>();
    private Map<Long, Long> idToParent = new HashMap<>();
    private CsvReader csvReader;

    public AdminUnitList(){}
    long a = NULL;
    private AdminUnitList(List<AdminUnit>units)
    {
        this.units.addAll(units);
    }

    public void read(String filename) throws IOException {
        csvReader = new CsvReader(filename, ",", true);
        while (csvReader.next()) {
            var adminUnit = tryReadAdminUnit();
            var boundingBox = tryReadBoundingBox();
            if (boundingBox != null)
                adminUnit.setBbox(boundingBox);
            units.add(adminUnit);
            idToUnit.put(csvReader.getLong(0), adminUnit);
            idToParent.put(csvReader.getLong(0), csvReader.getOrNull(csvReader::getLong, 1));
        }

        idToUnit.forEach(this::bindRelationship);
//        idToUnit.clear();
//        idToParent.clear();

        units.forEach(AdminUnit::fixMissingValues);
    }

    public void list(PrintStream out) {
        this.units.forEach(out::println);
    }

    public void list(PrintStream out, int offset, int limit) {
        this.units.stream()
                .skip(offset)
                .limit(limit)
                .forEach(out::println);
    }

    public AdminUnitList selectByName(String pattern, boolean regex) {
        var list = new AdminUnitList();
        list.units.addAll(this.units.stream().filter(x -> {
            if (regex)
                return x.getName().matches(pattern);
            else
                return x.getName().contains(pattern);
        }).collect(Collectors.toList()));
        return list;
    }

    public AdminUnitList getNeighbors5(AdminUnit unit, double maxDistance) {
        if (!unit.hasBoundingBox())
            return new AdminUnitList();

        var fromTheSameHierarchy = this.units.stream().filter(
                x -> x.getAdminLevel() == unit.getAdminLevel()
                        && x.hasBoundingBox());

        var neighbors = fromTheSameHierarchy.filter(x -> {

            if (x.getAdminLevel()!=8){
                        return x.getBbox().intersects(unit.getBbox());
            } else {
                       return x.getBbox().distanceTo(unit.getBbox()) <= maxDistance;
            }
        });

        return new AdminUnitList(neighbors.collect(Collectors.toList()));
    }

//    public AdminUnitList getNeighbors(AdminUnit unit, double maxDistance){
//
//        AdminUnitList neighbourList = new AdminUnitList();
//        for(AdminUnit potentialNeighbour : units){
//
//            if(potentialNeighbour != unit && potentialNeighbour.getAdminLevel() == unit.getAdminLevel()) {
//                if(unit.getAdminLevel() == 8 && unit.getBbox().distanceTo(potentialNeighbour.getBbox()) < maxDistance){
//                    neighbourList.units.add(potentialNeighbour);
//                }else if(unit.getAdminLevel() != 8 && unit.getBbox().intersects(potentialNeighbour.getBbox())){
//                    neighbourList.units.add(potentialNeighbour);
//                }
//            }
//        }
//        return neighbourList;
//    }
//    public AdminUnitList RTree(AdminUnit unit, double maxDistance){
//
//        var parentNeighbors = new AdminUnitList();
//        if (unit.getParent() != null){
//            parentNeighbors = RTree(unit.getParent(), maxDistance);
//        }else {
//            parentNeighbors.units.addAll(getNeighbors(unit, maxDistance).units);
//            parentNeighbors.units.add(unit);
//            return parentNeighbors;
//        }
//
//        AdminUnitList neighbourList = new AdminUnitList();
//        for(AdminUnit potentialNeighbour : parentNeighbors.units){
//
//            if(potentialNeighbour != unit && potentialNeighbour.getAdminLevel() == unit.getAdminLevel()) {
//                if(unit.getAdminLevel() == 8 && unit.getBbox().distanceTo(potentialNeighbour.getBbox()) < maxDistance){
//                    neighbourList.units.add(potentialNeighbour);
//                }else if(unit.getAdminLevel() != 8 && unit.getBbox().intersects(potentialNeighbour.getBbox())){
//                    neighbourList.units.add(potentialNeighbour);
//                }
//            }
//        }
//        return neighbourList;
//    }


    public <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    public AdminUnitList RTree0(AdminUnit unit, double maxDistance) {
        AdminUnitList a = this.RTree1(unit,maxDistance);
        var fromTheSameHierarchy = a.units.stream().filter(
                x -> x.getAdminLevel() == unit.getAdminLevel()
                        && x.hasBoundingBox());

        var neighbors = fromTheSameHierarchy.filter(x -> {

            if (x.getAdminLevel()!=8){
                return x.getBbox().intersects(unit.getBbox());
            } else {
                return x.getBbox().distanceTo(unit.getBbox()) <= maxDistance;
            }
        });

        return new AdminUnitList(neighbors.collect(Collectors.toList()));
    }
    public AdminUnitList RTree1(AdminUnit unit, double maxDistance) {
        if (!unit.hasBoundingBox())
            return new AdminUnitList();
        if (unit.getParent() != null){
            AdminUnitList b = new AdminUnitList();
            b.units.addAll(RTree1(unit, maxDistance).units);
            return b;
        }else{
            return getNeighbors5(unit,maxDistance);
        }

    }


    public AdminUnitList sortInplaceByName(){
        class CustomComparator implements Comparator<AdminUnit>
        {
            @Override
            public int compare(AdminUnit left, AdminUnit right) {
                return left.getName().compareTo(right.getName());
            }
        }
        this.units.sort(new CustomComparator());
        return this;
    }

    public AdminUnitList sortInplaceByPopulation(){
        this.units.sort(Comparator.comparing(AdminUnit::getPopulation));
        return this;
    }

    public AdminUnitList sortInplace(Comparator<AdminUnit>comparator){
        this.units.sort(comparator);
        return this;
    }
    public AdminUnitList sort(Comparator<AdminUnit>comparator){
        var result = new AdminUnitList(this.units);
        result.units.sort(comparator);
        return this;
    }

    AdminUnitList filter(Predicate<AdminUnit> pred)
    {
        return new AdminUnitList(this.units
                .stream()
                .filter(pred)
                .collect(Collectors.toList()));
    }

    AdminUnitList filter(Predicate<AdminUnit> pred,int limit){

        return new AdminUnitList(this.units
                .stream()
                .filter(pred)
                .limit(limit)
                .collect(Collectors.toList()));
    }
    AdminUnitList filter(Predicate<AdminUnit> pred,int offset,int limit){

        return new AdminUnitList(this.units
                .stream()
                .filter(pred)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList()));
    }

    private AdminUnit tryReadAdminUnit() {
        var adminUnit = new AdminUnit(csvReader.get(2));
        var adminLevel = csvReader.getOrNull(csvReader::getInt, 3);
        var population = csvReader.getOrNull(csvReader::getLong, 4);
        var area = csvReader.getOrNull(csvReader::getDouble, 5);
        var density = csvReader.getOrNull(csvReader::getDouble, 6);

        if (adminLevel != null)
            adminUnit.setAdminLevel(adminLevel);
        if (population != null)
            adminUnit.setPopulation(population);
        if (density != null)
            adminUnit.setDensity(density);
        if (area != null)
            adminUnit.setArea(area);

        return adminUnit;
    }

    private BoundingBox tryReadBoundingBox() {
        var xList = readX();
        var yList = readY();
        if (xList.isEmpty() || yList.isEmpty())
            return null;
        return new BoundingBox(xList.stream().mapToDouble(x -> x).min().getAsDouble(),
                yList.stream().mapToDouble(y -> y).min().getAsDouble(),
                xList.stream().mapToDouble(x -> x).max().getAsDouble(),
                yList.stream().mapToDouble(y -> y).min().getAsDouble()
        );
    }

    private List<Double> readX() {
        List<Double> result = Arrays.asList(csvReader.getOrNull(csvReader::getDouble, 7),
                csvReader.getOrNull(csvReader::getDouble, 9),
                csvReader.getOrNull(csvReader::getDouble, 11),
                csvReader.getOrNull(csvReader::getDouble, 13));
        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<Double> readY() {
        List<Double> result = Arrays.asList(csvReader.getOrNull(csvReader::getDouble, 8),
                csvReader.getOrNull(csvReader::getDouble, 10),
                csvReader.getOrNull(csvReader::getDouble, 12),
                csvReader.getOrNull(csvReader::getDouble, 14));
        return result.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }


    private void bindRelationship(Long id, AdminUnit unit) {
        Long parentId = idToParent.get(id);
        if (parentId != null && unit.getParent() == null) {
            AdminUnit parent = idToUnit.get(parentId);
            parent.addChild(unit);
        }
    }


}



