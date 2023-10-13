package com.simple.SpringCRUD.MaskingInterface;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MaskingSerializer extends JsonSerializer {
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        String personalIdentifiableInformation = o.toString().length() > 3
                ? o.toString().replaceAll("\\w(?=\\w{4})", "X")
                : "X".repeat(o.toString().length());
        jsonGenerator.writeString(personalIdentifiableInformation);
    }
}
