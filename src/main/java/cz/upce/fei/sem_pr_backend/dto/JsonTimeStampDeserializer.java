package cz.upce.fei.sem_pr_backend.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JsonTimeStampDeserializer extends JsonDeserializer<Timestamp> {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss z");

    @Override
    public Timestamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return null;
//        String date = p.getText();
//        try {
//            return dateFormat.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
