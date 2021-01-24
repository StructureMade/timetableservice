package de.structuremade.ms.timetableservice.api.json;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetLessonJson {
    @NotNull(message = "date is needed")
    private String date;
    @NotNull
    private boolean children;

    private String childrenId;
}
