package com.microservices_project.microservices_demo.util;

import com.microservices_project.microservices_demo.modules.person.models.PersonDto;
import com.microservices_project.microservices_demo.modules.person.models.entities.Person;

public class Mapper {

    public static PersonDto personMapper(Person person) {
        return PersonDto.builder().key(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress())
                .gender(person.getGender())
                .enabled(person.getEnabled())
                .build();

    }

    public static Person personDtoMapper(PersonDto personDto) {
        return new Person(personDto.getKey(), personDto.getFirstName(), personDto.getLastName(), personDto.getAddress(),
                personDto.getGender(), personDto.getEnabled());
    }
    
    
}
