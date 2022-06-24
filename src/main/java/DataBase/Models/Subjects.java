package DataBase.Models;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.CascadeType;

@Entity
@Table (name = "subjects_table")
public class Subjects implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String subject;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL})
    private List<Programs> programs;

    public Subjects(){}

    public Subjects(String subject){ this.subject = subject; }

    public Subjects(Subjects subject){
        this.id = subject.id;
        this.subject = subject.subject;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Programs> getPrograms() { return programs; }
    public void setPrograms(List<Programs> programs) { this.programs = programs; }

    @Override
    public String toString(){
        return "Предмет: " + this.subject;
    }

    @Override
    public boolean equals(Object object){
        return equality((Subjects)object);
    }

    public boolean equality(Subjects subjects){
        if (this.id == subjects.id)
            return true;
        else
            return false;
    }
}
