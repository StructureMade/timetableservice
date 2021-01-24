package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.LessonSubstitutes;
import de.structuremade.ms.timetableservice.utils.database.entity.Lessonsettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonSettingsRepo extends JpaRepository<Lessonsettings, String> {
}
