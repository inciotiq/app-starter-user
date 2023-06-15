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
        JsonNode role = jsonParser.getCodec().readTree(jsonParser);
//        JsonNode phaseNode = node.get("role");

        if (role == null) {
            return null;
        }

        String phase = role.textValue();

        for (RoleConverter converter : converters) {
            Role convert = converter.convert(phase);

            if (convert != null) {
                return convert;
            }
        }

        return null;
//        String attribute = MessageFormat.format(MachinePhase.TEMPLATE, phase, status);
//        return machinePhaseConverter.convertToEntityAttribute(attribute);
    }
}

//
//public class MachinePhaseDeserializer extends StdDeserializer<MachinePhase> {
//
//    private final MachinePhaseConverter machinePhaseConverter = new MachinePhaseConverter();
//
//    public MachinePhaseDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    // do not remove, required for request deserialization
//    public MachinePhaseDeserializer() {
//        this(null);
//    }
//
//    @Override
//    public MachinePhase deserialize(JsonParser p, DeserializationContext context) throws IOException {
//        JsonNode node = p.getCodec().readTree(p);
//        JsonNode phaseNode = node.get("phase");
//        JsonNode statusNode = node.get("status");
//
//        if (phaseNode == null || statusNode == null) {
//            return null;
//        }
//
//        String phase = phaseNode.textValue();
//        String status = statusNode.textValue();
//
//        String attribute = MessageFormat.format(MachinePhase.TEMPLATE, phase, status);
//        return machinePhaseConverter.convertToEntityAttribute(attribute);
//    }
//}
