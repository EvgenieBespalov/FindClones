package DataBase.Models;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CascadeType;

@Entity
@Table (name = "variants_table")
public class Variants implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long variant;

    @OneToMany(mappedBy = "variant", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL})
    private List<Programs> programs;

    public Variants(){}

    public Variants(long variant){
        this.variant = variant;
    }

    public Variants(Variants variant){
        this.id = variant.id;
        this.variant = variant.variant;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getVariant(){ return this.variant; }
    public void setVariant(long variant){ this.variant = variant; }

    public List<Programs> getPrograms() { return programs; }

    public void setPrograms(List<Programs> programs) {
        this.programs = programs;
    }

    @Override
    public String toString(){
        return "Вариант: " + this.variant /*+ " " + this.programs*/;
    }

    @Override
    public boolean equals(Object object){
        return equality((Variants)object);
    }

    public boolean equality(Variants variants){
        if (this.id == variants.id)
            return true;
        else
            return false;
    }
}
