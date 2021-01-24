package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.Lessons;
import de.structuremade.ms.timetableservice.utils.database.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permissions, String> {
}
