package de.structuremade.ms.timetableservice.api.json.answer.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Holidays {
    private String name;
    private String start;
    private String end;
}
