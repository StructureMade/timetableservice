package de.structuremade.ms.timetableservice.utils.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "schools", schema = "services", indexes = {
        @Index(name = "id_schoolid", columnList = "id", unique = true)
})
@Getter
@Setter
public class School {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column
    private String name;

    @Column
    private String email;

    @OneToMany(targetEntity = Role.class, orphanRemoval = true)
    @JoinColumn(name = "school", foreignKey = @ForeignKey(name = "fk_school"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(targetEntity = de.structuremade.ms.initializer.database.entity.LessonSubstitutes.class, orphanRemoval = true)
    @JoinColumn(name = "school", foreignKey = @ForeignKey(name = "fk_school"))
    private List<de.structuremade.ms.initializer.database.entity.LessonSubstitutes> lessons = new ArrayList<>();

    @OneToMany(targetEntity = de.structuremade.ms.initializer.database.entity.TimeTableTimes.class, orphanRemoval = true)
    @JoinColumn(name = "school", foreignKey = @ForeignKey(name = "fk_school"))
    private List<de.structuremade.ms.initializer.database.entity.TimeTableTimes> times = new ArrayList<>();

    @OneToMany(targetEntity = Holidays.class)
    @JoinColumn(name = "school")
    private List<Holidays> holidays;

}
