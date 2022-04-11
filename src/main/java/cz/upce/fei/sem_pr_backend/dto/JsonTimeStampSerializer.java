package cz.upce.fei.sem_pr_backend.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class JsonTimeStampSerializer extends JsonSerializer<Timestamp> {
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss z");

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formattedDate = dateFormat.format(value);
        gen.writeString(formattedDate);
    }
}
