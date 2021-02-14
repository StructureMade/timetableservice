package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.Class;
import de.structuremade.ms.timetableservice.utils.database.entity.School;
import de.structuremade.ms.timetableservice.utils.database.entity.Schoolsettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolsettingsRepo extends JpaRepository<Schoolsettings, String> {
    Schoolsettings findBySchool(School school);
}
