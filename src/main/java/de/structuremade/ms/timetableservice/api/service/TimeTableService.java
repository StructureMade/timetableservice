package de.structuremade.ms.timetableservice.api.service;

import de.structuremade.ms.timetableservice.api.json.GetLessonJson;
import de.structuremade.ms.timetableservice.api.json.answer.Arrays.Holidays;
import de.structuremade.ms.timetableservice.api.json.answer.Arrays.LessonDays;
import de.structuremade.ms.timetableservice.api.json.answer.Arrays.Times;
import de.structuremade.ms.timetableservice.api.json.answer.GetMyLessonsJson;
import de.structuremade.ms.timetableservice.utils.JWTUtil;
import de.structuremade.ms.timetableservice.utils.database.entity.Class;
import de.structuremade.ms.timetableservice.utils.database.entity.*;
import de.structuremade.ms.timetableservice.utils.database.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TimeTableService {

    private final Logger LOGGER = LoggerFactory.getLogger(TimeTableService.class);
    @Autowired
    SchoolsettingsRepo schoolSettingsRepo;
    @Autowired
    SchoolRepo schoolRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    LessonSubstitutesRepo lessonSubstitutesRepo;
    @Autowired
    TimeTableTimesRepo timeRepo;
    @Autowired
    HolidayRepo holidayRepo;
    @Autowired
    ClassRepo classRepo;
    @Autowired
    JWTUtil jwtUtil;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdfHoliday = new SimpleDateFormat("dd.MM.YYYY");

    @Transactional
    public GetMyLessonsJson getUserLessons(GetLessonJson lessonJson, String jwt) {
        //int holidayCount = 0;
        List<Holidays> holidays = new ArrayList<>();
        List<Times> times = new ArrayList<>();
        List<LessonDays> monday = new ArrayList<>();
        List<LessonDays> tuesday = new ArrayList<>();
        List<LessonDays> wednesday = new ArrayList<>();
        List<LessonDays> thursday = new ArrayList<>();
        List<LessonDays> friday = new ArrayList<>();
        List<String> timeIds = new ArrayList<>();
        //List<LessonSubstitutes> lessonSubstitutesList = new ArrayList<>();
        List<LessonRoles> lrList;
        LessonDays lessonsJsonArray;
        //User substituteTeacher;
        User user;
        Class userClass;
        try {
            LOGGER.info("Check if Token is expired");
            if (jwtUtil.isTokenExpired(jwt)) {
                LOGGER.info("Token is expired");
                return new GetMyLessonsJson(new LessonDays());
            }
            LOGGER.info("Get User- and Schoolinformations");
            if (lessonJson.isChildren()) {
                user = userRepo.getOne(lessonJson.getChildrenId());
            } else {
                user = userRepo.getOne(jwtUtil.extractIdOrEmail(jwt));
            }

            if (user == null) return null;
            School school = schoolRepo.getOne(jwtUtil.extractSpecialClaim(jwt, "schoolid"));

            holidayRepo.findAll().forEach(holidayDate -> {
                holidays.add(new Holidays(holidayDate.getName(), sdfHoliday.format(holidayDate.getBeginofholidays()), sdfHoliday.format(holidayDate.getEndofholidays())));
            });


//            LOGGER.info("Format String to Calendar");
//            String[] splitDate = lessonJson.getDate().split("\\.");
//            Calendar calendar = Calendar.getInstance();
//            LOGGER.info("Check if Holiday is right now");
//            for (int i = 0; i < 5; i++) {
//                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splitDate[0]) + i);
//                calendar.set(Calendar.MONTH, Integer.parseInt(splitDate[1]));
//                calendar.set(Calendar.YEAR, Integer.parseInt(splitDate[2]));
//                if (this.isHoliday(calendar.getTime(), school)) {
//                    LOGGER.info("Holiday found");
//                    holidayCount++;
//                    switch (i) {
//                        case 0 -> holidayDays.add("monday");
//                        case 1 -> holidayDays.add("tuesday");
//                        case 2 -> holidayDays.add("wednesday");
//                        case 3 -> holidayDays.add("thursday");
//                        case 4 -> holidayDays.add("friday");
//                    }
//                } else {
//                    lessonSubstitutesList.addAll(lessonSubstitutesRepo.findAllByDateAndSchool(calendar.getTime(), school));
//                }
//              }
            LOGGER.info("Create Timetable");
            userClass = user.getUserClass();
            lrList = user.getLessonRoles();
            LOGGER.info(userClass.getId());
            LOGGER.info(String.valueOf(userClass.getLessons().size()));
            for (LessonRoles lrole : lrList) {
                for (Lessons lesson : lrole.getLessons()) {
                    //boolean substitute = false;
                    //LOGGER.info("Check if Lesson is in this week");
                    //if (this.isLessonInThisWeek(lessonJson.getDate(), school, lesson.getSettings())) {
                    LOGGER.info("Get times of Lesson");
                    for (TimeTableTimes ttt : lesson.getTimes()) {
                        timeIds.add(ttt.getId());
                    }
                    //LOGGER.info("Check for substitute");
//                        for (LessonSubstitutes lessonSubstitutes : lessonSubstitutesList) {
//                            if (lessonSubstitutes.getLesson().getId().equals(lesson.getId())) {
//                                LOGGER.info("Lesson have substitute");
//                                substituteTeacher = lessonSubstitutes.getSubstituteTeacher();
//                                lessonsJsonArray = new LessonDays(lesson, lessonSubstitutes, lrole.getName(), lrole.getTeacher(), substituteTeacher, timeIds, lesson.getSettings().getId());
//                                substitute = true;
//                                timeIds.clear();
//                            }
//                        }
                    //}
                    //if (!substitute) {
                    lessonsJsonArray = new LessonDays(lesson, lrole.getName(), lrole.getTeacher(), timeIds, lesson.getSettings().getId());
                    timeIds.clear();
                    //}
                    LOGGER.info("Add Entity to List");
                    switch (lesson.getDay().toLowerCase()) {
                        case "monday" -> monday.add(lessonsJsonArray);
                        case "tuesday" -> tuesday.add(lessonsJsonArray);
                        case "wednesday" -> wednesday.add(lessonsJsonArray);
                        case "thursday" -> thursday.add(lessonsJsonArray);
                        case "friday" -> friday.add(lessonsJsonArray);
                    }
                }
            }
            if (userClass != null && userClass.getLessons().size() > 0) {
                for (LessonRoles lr : userClass.getLessons()) {
                    for (Lessons lesson : lr.getLessons()) {
                        LOGGER.info("Get times of Lesson");
                        for (TimeTableTimes ttt : lesson.getTimes()) {
                            timeIds.add(ttt.getId());
                        }
                        lessonsJsonArray = new LessonDays(lesson, lr.getName(), lr.getTeacher(), timeIds, lesson.getSettings().getId());
                        timeIds.clear();
                        LOGGER.info("Add Entity to List");
                        switch (lesson.getDay().toLowerCase()) {
                            case "monday" -> monday.add(lessonsJsonArray);
                            case "tuesday" -> tuesday.add(lessonsJsonArray);
                            case "wednesday" -> wednesday.add(lessonsJsonArray);
                            case "thursday" -> thursday.add(lessonsJsonArray);
                            case "friday" -> friday.add(lessonsJsonArray);
                        }
                    }
                }
            }
            LOGGER.info("Get Times of Timetable ");
            for (TimeTableTimes ttt : timeRepo.findAllBySchool(school)) {
                Date start = new Date(ttt.getStarttime().getTime());
                Date end = new Date(ttt.getEndtime().getTime());
                times.add(new Times(ttt.getId(), sdf.format(start), sdf.format(end)));
            }
            return new GetMyLessonsJson(times, monday, tuesday, wednesday, thursday, friday, holidays);
        } catch (Exception e) {
            LOGGER.error("Couldn't get Lessons of User", e.fillInStackTrace());
            return null;
        }
    }

    //@Transactional
    //protected boolean isHoliday(Date date, School school) {
    //    boolean holidaysBool = false;
    //    LOGGER.info("Get all Holidays of this School");
    //    List<Holidays> holidays = holidayRepo.findAllBySchool(school);
    //    for (Holidays holiday : holidays) {
    //        if (date.after(holiday.getBeginofholidays()) && date.before(holiday.getEndofholidays())) {
    //            LOGGER.info("No Holiday was found");
    //            holidaysBool = false;
    //        } else {
    //            LOGGER.info("Holiday was found");
    //            holidaysBool = true;
    //            break;
    //        }
    //    }
    //    return holidaysBool;
    //}


    // @Transactional
    // protected boolean isLessonInThisWeek(String date, School school, Lessonsettings settings) {
    //     String[] splitDate = date.split("\\.");
    //     Calendar c = Calendar.getInstance();
    //     c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(splitDate[0]));
    //     c.set(Calendar.MONTH, Integer.parseInt(splitDate[1]) - 1);
    //     c.set(Calendar.YEAR, Integer.parseInt(splitDate[2]));
    //     int cw = c.get(Calendar.WEEK_OF_YEAR);
    //     Schoolsettings ss = schoolSettingsRepo.findBySchool(school);
    //     DateTime currentDate = new DateTime(c.getTime());
    //     DateTime start = new DateTime(ss.getDateOfBegin());
    //     DateTime end = new DateTime(ss.getDateOfEnd());
    //     int halfYearWeeks = Weeks.weeksBetween(start, end).getWeeks() / 2;
    //     switch (settings.getId()) {
    //         case 0:
    //             //Every Week
    //             return true;
    //         case 1:
    //             //Gerade Woche
    //             return cw % 2 == 0;
    //         case 2:
    //             //Ungerade Woche
    //             return cw % 2 != 0;
    //         case 3:
    //             //Erstes Halbjahr
    //             int week = Weeks.weeksBetween(start, currentDate).getWeeks();
    //             return week < halfYearWeeks;
    //         case 4:
    //             //Zweites Halbjahr
    //             int week1 = Weeks.weeksBetween(start, currentDate).getWeeks();
    //             return week1 > halfYearWeeks;
    //         case 5:
    //             //Ersten 2 Wochen
    //             return 2 >= c.get(Calendar.WEEK_OF_MONTH);
    //         case 6:
    //             //Letzten 2 Wochen
    //             return 2 <= c.get(Calendar.WEEK_OF_MONTH);
    //         default:
    //             return false;
    //     }
    // }
//
}
