package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.LessonSubstitutes;
import de.structuremade.ms.timetableservice.utils.database.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Date;

public interface LessonSubstitutesRepo extends JpaRepository<LessonSubstitutes, String> {
    List<LessonSubstitutes> findAllByDateAndSchool(Date time, School school);
}
