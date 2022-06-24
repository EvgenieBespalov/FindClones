package DataBase.Models;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.CascadeType;

@Entity
@Table (name = "labworks_table")
public class LabWorks implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long labWork;

    @OneToMany(mappedBy = "labWork", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL})
    private List<Programs> programs;

    public LabWorks(){}

    public LabWorks(long labWork){
        this.labWork = labWork;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getLabwork() {
        return labWork;
    }
    public void setLabwork(long labWork) {
        this.labWork = labWork;
    }

    public List<Programs> getPrograms() { return programs; }
    public void setPrograms(List<Programs> programs) { this.programs = programs; }

    @Override
    public String toString() {
        return "Работа: " + labWork;
    }

    @Override
    public boolean equals(Object object){
        return equality((LabWorks)object);
    }

    public boolean equality(LabWorks labWorks){
        if (this.id == labWorks.id)
            return true;
        else
            return false;
    }
}
