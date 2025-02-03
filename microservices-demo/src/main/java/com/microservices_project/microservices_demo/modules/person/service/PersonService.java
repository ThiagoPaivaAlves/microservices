package com.microservices_project.microservices_demo.modules.person.service;

import com.microservices_project.microservices_demo.exceptions.NotFoundException;
import com.microservices_project.microservices_demo.modules.person.controllers.PersonController;
import com.microservices_project.microservices_demo.modules.person.models.PersonDto;
import com.microservices_project.microservices_demo.modules.person.models.entities.Person;
import com.microservices_project.microservices_demo.modules.person.repositories.PersonRepository;
import com.microservices_project.microservices_demo.util.Mapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class PersonService {
    
    @Autowired
    PersonRepository repository;
    
    @Autowired
    PagedResourcesAssembler<PersonDto> assembler;
    
    public PersonDto findById(Long id) {
        log.info("returning person");
        PersonDto dto = Mapper.personMapper(repository.findById(id).orElseThrow(
                () -> new NotFoundException("tem nada nao")));
        dto.add(linkTo(methodOn(PersonController.class).findPerson(id)).withSelfRel());
        return dto;
    }
    
    //    public Page<PersonDto> findAll(Pageable pageable) {
    public PagedModel<EntityModel<PersonDto>> findAll(Pageable pageable) {
        log.info("returning all");
        return assembler.toModel(repository.findAll(pageable).map(entity -> {
            PersonDto dto = Mapper.personMapper(entity);
            dto.add(linkTo(methodOn(PersonController.class).findPerson(dto.getKey())).withSelfRel());
            return dto;
        }), linkTo(methodOn(PersonController.class).findAllPerson(pageable.getPageNumber(), pageable.getPageSize(),
                                                                  "ASC")).withSelfRel());
    }
    
    public PagedModel<EntityModel<PersonDto>> findPersonByName(String name, Pageable pageable) {
        log.info("find person by name");
        return assembler.toModel(repository.findByFirstNameIgnoreCaseContaining(name, pageable).map(entity -> {
            PersonDto dto = Mapper.personMapper(entity);
            dto.add(linkTo(methodOn(PersonController.class).findPerson(dto.getKey())).withSelfRel());
            return dto;
        }), linkTo(methodOn(PersonController.class).findAllPersonByName(name, pageable.getPageNumber(),
                                                                        pageable.getPageSize(),
                                                                        "ASC")).withSelfRel());
    }
    
    @SneakyThrows
    public PersonDto create(PersonDto personDto) {
        log.info("creating person");
        
        if(personDto == null) {
            throw new BadRequestException("You need to provide person data for person creation");
        }
        
        PersonDto dto = Mapper.personMapper(repository.save(Mapper.personDtoMapper(personDto)));
        dto.add(linkTo(methodOn(PersonController.class).createPerson(personDto)).withSelfRel());
        return dto;
    }
    
    @SneakyThrows
    public PersonDto update(Long id, PersonDto personDto) {
        log.info("updating person");
        
        repository.findById(id).orElseThrow( () -> new NotFoundException("tem nada nao"));
        
        if(personDto == null) {
            throw new BadRequestException("You need to provide Person for updating");
        }
        
        Person entity = Mapper.personDtoMapper(personDto);
        entity.setId(id);
        PersonDto dto = Mapper.personMapper(repository.save(entity));
        dto.add(linkTo(methodOn(PersonController.class).updatePerson(id, personDto)).withSelfRel());
        return dto;
    }
    
    @Transactional
    public PersonDto disablePerson(Long id) {
        log.info("Disabling a single person");
        repository.disablePerson(id);
        PersonDto dto = Mapper.personMapper(repository.findById(id).orElseThrow(
                () -> new NotFoundException("tem nada nao")));
        dto.add(linkTo(methodOn(PersonController.class).findPerson(id)).withSelfRel());
        return dto;
    }
    
    public void delete(Long id) {
        log.info("deleting person");
        
        repository.findById(id).orElseThrow( () -> new NotFoundException("tem nada nao"));
        repository.deleteById(id);
        
        log.info("Person {} deleted", id);
    }
}
