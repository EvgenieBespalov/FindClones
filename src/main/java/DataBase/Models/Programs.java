package DataBase.Models;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import org.hibernate.annotations.CascadeType;

@Entity
@Table (name = "programs_table")
public class Programs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String program;

    @ManyToOne
    @Cascade({CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private Variants variant;

    @ManyToOne
    @Cascade({CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private Subjects subject;

    @ManyToOne
    @Cascade({CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private Students student;

    @ManyToOne
    @Cascade({CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    private LabWorks labWork;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name="findclones_table",
            joinColumns = @JoinColumn(name="clone1_id"),
            inverseJoinColumns=@JoinColumn(name="clone2_id"))
    private Set<Programs> clonesProgram = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name="findclones_table",
            joinColumns = @JoinColumn(name="clone2_id"),
            inverseJoinColumns=@JoinColumn(name="clone1_id"))
    private Set<Programs> programClones = new HashSet<>();

    public Programs(){}

    public Programs(String program, Subjects subject, LabWorks labWork, Variants variant, Students student){
        this.program = program;
        this.variant = variant;
        this.subject = subject;
        this.student = student;
        this.labWork = labWork;
    }

    public void addClone(Programs clone){
        this.programClones.add(clone);
        clone.getProgramClones().add(this);
    }

    public Set<Programs> getProgramClones() {
        return programClones;
    }

    public Set<Programs> getClonesProgram() {
        return clonesProgram;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getProgram() { return program; }
    public void setProgram(String program) { this.program = program; }

    public Variants getVariant() { return variant; }
    public void setVariant(Variants variants) { this.variant = variants; }

    public Subjects getSubject() { return subject; }
    public void setSubject(Subjects subject) { this.subject = subject; }

    public Students getStudent() { return student; }
    public void setStudent(Students student) { this.student = student; }

    public LabWorks getLabwork() { return labWork; }
    public void setLabwork(LabWorks labWork) { this.labWork = labWork; }

    @Override
    public String toString() {
        return "Номер программы: " + id + " ";
    }

    public void printAllClones(){
        for(Object object: clonesProgram){
            System.out.println(object);
        }
    }
}
