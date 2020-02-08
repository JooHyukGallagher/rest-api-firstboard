package me.weekbelt.restapifirstboard.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.BindingResult;

import java.io.IOException;

@JsonComponent
public class BindingResultSerializer extends JsonSerializer<BindingResult> {
    @Override
    public void serialize(BindingResult value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        value.getGlobalErrors().forEach(e -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("objectName", e.getObjectName());
                gen.writeStringField("code", e.getCode());
                gen.writeStringField("defaultMessage", e.getDefaultMessage());
                gen.writeEndObject();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        gen.writeEndArray();
    }
}
