package de.structuremade.ms.timetableservice.utils.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "class")
@Getter
@Setter
public class Class {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column
    private String name;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "classteacher", foreignKey = @ForeignKey(name = "fk_classteacher"))
    private User classTeacher;

    @ManyToOne(targetEntity = School.class)
    @JoinColumn(name = "school", foreignKey = @ForeignKey(name = "fk_school"))
    private School school;

    @ManyToMany(targetEntity = LessonRoles.class)
    @JoinTable(name = "classlessons", schema = "services", joinColumns = @JoinColumn(name = "class", foreignKey = @ForeignKey(name = "fk_class"))
            , inverseJoinColumns = @JoinColumn(name = "lessonrole", foreignKey = @ForeignKey(name = "fk_lessonrole")))
    private List<LessonRoles> lessons;

    @OneToMany(targetEntity = User.class)
    @JoinColumn(name = "class")
    private List<User> students;

}
