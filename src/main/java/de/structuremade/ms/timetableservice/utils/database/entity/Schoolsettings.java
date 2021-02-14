package de.structuremade.ms.timetableservice.utils.database.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "schoolsettings")
@Getter
@Setter
public class Schoolsettings {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    @Column
    private Date dateOfBegin;

    @Column
    private Date dateOfEnd;

    @OneToOne(targetEntity = School.class)
    @JoinColumn(name = "school", foreignKey = @ForeignKey(name = "fk_school"))
    private School school;
}
