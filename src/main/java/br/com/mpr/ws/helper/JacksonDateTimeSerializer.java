package br.com.mpr.ws.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wagner on 28/10/15.
 */
public class JacksonDateTimeSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        jgen.writeString(df.format(value));
    }
}
