package ua.southpost.resource.backup.service;

import java.util.Optional;

import ua.southpost.resource.persistence.model.Foo;


public interface IFooService {
    Optional<Foo> findById(Long id);

    Foo save(Foo foo);
    
    Iterable<Foo> findAll();

}
