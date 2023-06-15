package com.iotiq.user.domain.authorities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.List;

@JsonComponent
public class RoleDeserializer extends JsonDeserializer<Role> {

    private final List<RoleConverter> converters;

    public RoleDeserializer(List<RoleConverter> converters) {
        this.converters = converters;
    }

    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);

        if (jsonNode == null) {
            return null;
        }

        String roleString = jsonNode.textValue();

        for (RoleConverter converter : converters) {
            Role role = converter.convert(roleString);

            if (role != null) {
                return role;
            }
        }

        return null;
    }
}