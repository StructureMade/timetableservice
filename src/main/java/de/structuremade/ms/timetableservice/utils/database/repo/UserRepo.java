package de.structuremade.ms.timetableservice.utils.database.repo;

import de.structuremade.ms.timetableservice.utils.database.entity.School;
import de.structuremade.ms.timetableservice.utils.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
    User findByParentsAndSchools(User user, School school);
}
