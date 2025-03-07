package com.parking.entities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.parking.exception.InvalidInputException;

import java.io.IOException;

//To handle the conversion of input strings to VehicleType enum values.
public class VehicleTypeDeserializer extends JsonDeserializer<VehicleType> {
    @Override
    public VehicleType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText().toUpperCase();
        try {
            return VehicleType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid vehicle type: " + value);
        }
    }
}