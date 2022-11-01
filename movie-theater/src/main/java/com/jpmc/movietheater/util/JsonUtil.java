package com.jpmc.movietheater.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jpmc.movietheater.enums.Enums;
import com.jpmc.movietheater.exception.JSONParseException;
import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.service.impl.MovieServiceImpl;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JsonUtil {

    @Autowired
    MovieServiceImpl movieService;

    private JsonUtil() {
    }

    //handle double time to human-readable time
    public static String humanReadableFormat(double duration) {
        return DurationFormatUtils.formatDurationWords((long) duration * 1000, true, false).replaceAll(Enums.REMOVE_SECONDS_ENUM.getConstant(), "").replaceAll(Enums.REMOVE_MINUTES_ENUM.getConstant(), "");
    }

    //returns JSONArray object
    public static JSONArray getJSONArray(List<Showing> schedule) throws JSONParseException {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_DATES_WITH_ZONE_ID, false)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule());
        JSONArray jsonArr;

        try {
            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schedule);
            jsonArr = (JSONArray) new JSONParser().parse(jsonStr);
            return jsonArr;
        } catch (JsonProcessingException | ParseException e) {
            throw new JSONParseException();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object> getJsonObjectList(final List<Showing> schedule) throws JSONParseException {
        List<Showing> sortedSchedule = schedule.stream()
                .peek(x -> x.getMovie().setTicketPrice(movieService.getMovieFee(x)))
                .collect(Collectors.toList());

        return (List<Object>) JsonUtil.getJSONArray(sortedSchedule).stream()
                .map(x -> {
                    JSONObject jsonObj = (JSONObject) x;
                    JSONObject movieObj = (JSONObject) jsonObj.get("movie");
                    movieObj.replace("runningTime", JsonUtil.humanReadableFormat((double) movieObj.get("runningTime")));
                    return jsonObj;
                })
                .collect(Collectors.toList());
    }
}
