package de.structuremade.ms.timetableservice.api.routes;

import com.google.gson.Gson;
import de.structuremade.ms.timetableservice.api.json.GetLessonJson;
import de.structuremade.ms.timetableservice.api.json.answer.GetMyLessonsJson;
import de.structuremade.ms.timetableservice.api.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("service/timetable")
public class TimeTableRoutes {

    @Autowired
    TimeTableService service;

    @CrossOrigin
    @PostMapping("/getmy")
    public Object getAllMyLessons(@RequestBody GetLessonJson lessonJson, HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        GetMyLessonsJson glj = service.getUserLessons(lessonJson,request.getHeader("Authorization").substring(7));
        if (glj != null) {
            if (glj.getMonday() != null) {
                if (glj.getMonday().size() == 1){
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return null;
                }
                response.setStatus(HttpStatus.ACCEPTED.value());
                return gson.toJson(glj);
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                return null;
            }
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }

    }

}
