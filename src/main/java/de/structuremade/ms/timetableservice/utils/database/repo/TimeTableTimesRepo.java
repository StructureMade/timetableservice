package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.School;
import de.structuremade.ms.timetableservice.utils.database.entity.TimeTableTimes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableTimesRepo extends JpaRepository<TimeTableTimes, String> {
    Iterable<? extends TimeTableTimes> findAllBySchool(School school);
}
