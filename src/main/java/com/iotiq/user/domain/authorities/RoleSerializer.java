package com.iotiq.user.domain.authorities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RoleSerializer extends JsonSerializer<Role> {
    @Override
    public void serialize(Role role, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        jsonGenerator.writeStartObject();
//        jsonGenerator.writeStringField("role", role.getClass().getSimpleName()); // fixme
//        jsonGenerator.writeEndObject();
        jsonGenerator.writeString(role.name());
    }
}


//public class MachinePhaseSerializer extends JsonSerializer<MachinePhase> {
//    @Override
//    public void serialize(MachinePhase value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeStartObject();
//        gen.writeStringField("phase", value.getClass().getSimpleName());
//        gen.writeStringField("status", value.name());
//        gen.writeEndObject();
//    }
//}
