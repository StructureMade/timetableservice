package de.structuremade.ms.timetableservice.api.json.answer.Arrays;

import de.structuremade.ms.timetableservice.utils.database.entity.Lessons;
import de.structuremade.ms.timetableservice.utils.database.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LessonDays {
    private String id;
    private String name;
    private int state;
    private String teacher;
    private String room;
    private int settings;
    private List<String> times;


    public LessonDays(Lessons lesson, String name, User teacher, List<String> times, int settings) {
        this.id = lesson.getId();
        this.name = name;
        this.state = lesson.getState();
        this.room = lesson.getRoom();
        this.teacher = teacher.getAbbreviation();
        this.settings = settings;
        this.times = new ArrayList<>();
        this.times.addAll(times);


    }
}
