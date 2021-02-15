package de.structuremade.ms.timetableservice.api.json.answer;

import de.structuremade.ms.timetableservice.api.json.answer.Arrays.Holidays;
import de.structuremade.ms.timetableservice.api.json.answer.Arrays.LessonDays;
import de.structuremade.ms.timetableservice.api.json.answer.Arrays.Times;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetMyLessonsJson {
    private List<Times> times;
    private List<LessonDays> monday;
    private List<LessonDays> tuesday;
    private List<LessonDays> wednesday;
    private List<LessonDays> thursday;
    private List<LessonDays> friday;
    private List<Holidays> holidays;

    public GetMyLessonsJson(LessonDays o){
        this.monday = new ArrayList<>();
        monday.add(o);
    }
}
