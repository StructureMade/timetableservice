package de.structuremade.ms.timetableservice.api.json.answer.LessonDays;

import de.structuremade.ms.timetableservice.utils.database.entity.LessonSubstitutes;
import de.structuremade.ms.timetableservice.utils.database.entity.Lessons;
import de.structuremade.ms.timetableservice.utils.database.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String substituteTeacher;
    private String substituteRoom;
    private List<String> times;


    public LessonDays(Lessons lesson, LessonSubstitutes lessonSubstitutes, String name, User teacher, User substituteTeacher, List<String> times, int settings) {
        this.id = lesson.getId();
        this.name = name;
        this.state = lesson.getState();
        this.room = lesson.getRoom();
        this.teacher = teacher.getAbbreviation();
        this.substituteTeacher = substituteTeacher.getAbbreviation();
        this.substituteRoom = lessonSubstitutes.getSubstituteRoom();
        this.settings = settings;
        this.times.addAll(times);


    }
}
