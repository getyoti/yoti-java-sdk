package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CUSTOM;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.fasterxml.jackson.databind.jsontype.impl.TypeNameIdResolver;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestTestTest2 {

    @Test
    public void test() throws IOException {
        final Bulldog bulldog = new Bulldog();
        bulldog.setSpecies("Dog");
        bulldog.setName("name is irrelvant actually");
        bulldog.setBreed("Bulldog");
//        bulldog.setType("so is type");

        final ObjectMapper om = new ObjectMapper();
        final String json = om.writeValueAsString(bulldog);
        System.out.println(json);

        final Animal animal = om.readValue(json, Animal.class);
        assertTrue(animal instanceof Bulldog);
        Bulldog deserializedBulldog = (Bulldog) animal;
    }


//    static class MyResolver extends TypeNameIdResolver {
//
//        protected MyResolver(MapperConfig<?> config,
//                JavaType baseType,
//                ConcurrentHashMap<String, String> typeToId, HashMap<String, JavaType> idToType) {
//            super(config, baseType, typeToId, idToType);
//        }
//    }

//    @JsonTypeInfo(use = DEDUCTION, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "species", visible = true)
    @JsonTypeInfo(use = NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "species", visible = true)
//    @JsonTypeInfo(use = CUSTOM, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "species", visible = true)
//    @JsonTypeIdResolver(MyResolver.class)
    @JsonSubTypes({
            @JsonSubTypes.Type(name = "Dog", value = Dog.class),
            @JsonSubTypes.Type(name = "Cat", value = Cat.class)
    })
    public static abstract class Animal {

        private String name;
        private String species;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "breed", visible = true)
//    @JsonTypeInfo(use = DEDUCTION, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "breed", visible = true)
//    @JsonTypeInfo(use = DEDUCTION, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "breed", visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(name = "Labrador", value = Labrador.class),
            @JsonSubTypes.Type(name = "Bulldog", value = Bulldog.class)
    })
//    @JsonTypeIdResolver()

    public static abstract class Dog extends Animal {

        private String breed;

        public String getBreed() {
            return breed;
        }

        public void setBreed(final String breed) {
            this.breed = breed;
        }
    }

    public static abstract class Cat {

        private String name;
    }

    public static class Labrador extends Dog {

        private String color;

        public String getColor() {
            return color;
        }

        public void setColor(final String color) {
            this.color = color;
        }
    }

    public static class Bulldog extends Dog {

//        private String type; // "frenchy", "english", etc..
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(final String type) {
//            this.type = type;
//        }
    }

    public static class MyResolver extends TypeIdResolverBase {

        @Override
        public String idFromValue(Object value) {
            return null;
        }

        @Override
        public String idFromValueAndType(Object value, Class<?> suggestedType) {
            return null;
        }

        @Override
        public JsonTypeInfo.Id getMechanism() {
            return null;
        }
    }

}
