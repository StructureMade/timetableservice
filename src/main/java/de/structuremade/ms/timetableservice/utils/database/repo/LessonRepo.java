package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepo extends JpaRepository<Lessons, String> {
}
