package com.halildev.cafeManagement.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CafeUtils {

    private CafeUtils() {

    }


    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage + "\"}", httpStatus);
    }


    public static <T> ResponseEntity<List<T>> getResponseEntityAsList(List<T> list, HttpStatus httpStatus) {
        return new ResponseEntity<>(list, httpStatus);
    }

    public static String getUUID() {

        Date data = new Date();


        long time = data.getTime();

        return "BILL-" + time;
    }

    public static JSONArray getJsonArrayFromString(String data) throws JSONException {

        JSONArray jsonArray = new JSONArray(data);

        return jsonArray;
    }


    public static Map<String, Object> getMapFromJson(String data) {
        if (!Strings.isEmpty(data) && !Strings.isBlank(data)) {

            return new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {

            }.getType());
        }
        return new HashMap<>();
    }
}
