package com.yoti.api.client.docs.session.retrieve.configuration.capture;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestTestTest {

    @Test
    public void test() throws IOException {
        final Bulldog bulldog = new Bulldog();
        bulldog.setSpecies("Dog");
        bulldog.setName("name is irrelvant actually");
        bulldog.setBreed("Bulldog");
        //        bulldog.setType("so is type");

        final ObjectMapper om = new ObjectMapper();
        AnnotationIntrospector dis1 = AnnotationIntrospectorPair.pair(new JacksonAnnotationIntrospector(), new YotiAnnotationIntrospector());
        om.setAnnotationIntrospectors(new JacksonAnnotationIntrospector(), dis1);

        final String json = om.writeValueAsString(bulldog);
        System.out.println(json);

        final Animal animal = om.readValue(json, Animal.class);
        assertTrue(animal instanceof Bulldog);
        Bulldog deserializedBulldog = (Bulldog) animal;
    }

    //    @JsonTypeInfo(use = DEDUCTION, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "species", visible = true)
    @JsonTypeInfo(use = NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "species", visible = true)
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
    @JsonSubTypes({
            @JsonSubTypes.Type(name = "Labrador", value = Labrador.class),
            @JsonSubTypes.Type(name = "Bulldog", value = Bulldog.class)
    })
    public static abstract class Dog extends Animal {

        private String breed;

        //
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

        //        private String breed;

        //                public String getBreed() {
        //                    return "Bulldog";
        //                }

        //                public void setBreed(final String breed) {
        //                    this.breed = breed;
        //                }

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

}
