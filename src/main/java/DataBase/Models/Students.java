package DataBase.Models;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import org.hibernate.annotations.CascadeType;

@Entity
@Table (name = "students_table")
public class Students implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long student;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @Cascade({ CascadeType.ALL})
    private List<Programs> programs;

    public Students(){}

    public Students(long stugent){
        this.student = stugent;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getStudent() {
        return student;
    }
    public void setStudent(long student) {
        this.student = student;
    }

    public List<Programs> getPrograms() { return programs; }
    public void setPrograms(List<Programs> programs) { this.programs = programs; }

    @Override
    public String toString() {
        return "Студент: " + student;
    }

    @Override
    public boolean equals(Object object){
        return equality((Students)object);
    }

    public boolean equality(Students students){
        if (this.id == students.id)
            return true;
        else
            return false;
    }
}
