package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.Holidays;
import de.structuremade.ms.timetableservice.utils.database.entity.Lessons;
import de.structuremade.ms.timetableservice.utils.database.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepo extends JpaRepository<Holidays, String> {
    List<Holidays> findAllBySchool(School school);
}
