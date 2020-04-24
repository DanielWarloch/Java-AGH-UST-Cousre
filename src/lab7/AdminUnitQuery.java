package lab7;

import java.util.Comparator;
import java.util.function.Predicate;

public class AdminUnitQuery {

    private AdminUnitList source;
    private Predicate<AdminUnit> predicate = (x)->true;
    private Comparator<AdminUnit> comparator;
    private int limit = Integer.MAX_VALUE;
    private int offset = 0;
    AdminUnitQuery selectFrom(AdminUnitList src){
        this.source = src;
        return this;
    }
    AdminUnitQuery where(Predicate<AdminUnit>pred){
        this.predicate = pred;
        return this;
    }

    AdminUnitQuery and(Predicate<AdminUnit>pred){
        pred = this.predicate.and(pred);
        return this;
    }

    AdminUnitQuery or(Predicate<AdminUnit>pred){
        pred = this.predicate.or(pred);
        return this;
    }

    AdminUnitQuery sort(Comparator<AdminUnit>cmp){
        comparator = cmp;
        return this;
    }

    AdminUnitQuery limit(int limit){
        this.limit = limit;
        return this;
    }
    AdminUnitQuery offset(int offset){
        this.offset = offset;
        return this;
    }

    AdminUnitList execute(){
        var result = this.source
                .filter(this.predicate,offset,limit);
        if(this.comparator != null)
            result.sort(comparator);

        return result;
    }
}
