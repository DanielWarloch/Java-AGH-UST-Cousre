package lab7;

import lombok.*;

import java.util.*;

@ToString
@Data
@RequiredArgsConstructor
public class AdminUnit {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude

    private AdminUnit parent;

    @NonNull
    private final String name;
    private Double area;
    private Integer adminLevel;
    private Long population;
    private Double density;
    private BoundingBox bbox;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HashSet<AdminUnit> children = new HashSet<>();

    public void addChild(AdminUnit unit){
        children.add(unit);
        unit.setParent(this);
    }

    public void fixMissingValues() {
        if (parent == null || this.density != null) {
            return;
        }
        var parentDensity = parent.getDensity();

        if (parentDensity == null)
            parent.fixMissingValues();

        parentDensity = parent.getDensity();

        if (parentDensity == null)
            return;

        this.setDensity(parentDensity);

        if (this.area != null)
            this.setPopulation(Math.round(this.area * this.density));
    }

    public boolean hasBoundingBox() {
            return this.bbox != null;
    }
}