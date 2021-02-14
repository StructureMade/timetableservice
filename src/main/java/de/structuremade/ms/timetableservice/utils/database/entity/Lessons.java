package de.structuremade.ms.timetableservice.utils.database.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Lessons")
@Getter
@Setter
public class Lessons {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column
    private String day;

    @Column
    private String room;

    @Column
    private int state;

    @ManyToOne(targetEntity = Lessonsettings.class)
    @JoinColumn(name = "settings", foreignKey = @ForeignKey(name = "fk_lessonsettings"))
    private Lessonsettings settings;

    @ManyToOne(targetEntity = LessonRoles.class)
    @JoinColumn(name = "lessonrole", foreignKey = @ForeignKey(name = "fk_lessonrole"))
    private LessonRoles lessonRoles;

    @ManyToMany(targetEntity = TimeTableTimes.class, fetch = FetchType.LAZY)
    @JoinTable(name = "lessontimes", schema = "services", joinColumns = @JoinColumn(name = "lesson", foreignKey = @ForeignKey(name = "fk_user"))
            , inverseJoinColumns = @JoinColumn(name = "time", foreignKey = @ForeignKey(name = "fk_time")))
    private List<TimeTableTimes> times;

    @OneToMany(targetEntity = LessonSubstitutes.class)
    @JoinColumn(name = "lesson")
    private List<LessonSubstitutes> lesson;

}
