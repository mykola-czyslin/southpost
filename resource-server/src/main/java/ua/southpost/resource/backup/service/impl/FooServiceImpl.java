package ua.southpost.resource.backup.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.southpost.resource.persistence.model.Foo;
import ua.southpost.resource.persistence.repository.IFooRepository;
import ua.southpost.resource.backup.service.IFooService;

@Service
public class FooServiceImpl implements IFooService {

    private IFooRepository fooRepository;

    public FooServiceImpl(IFooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @Override
    public Optional<Foo> findById(Long id) {
        return fooRepository.findById(id);
    }

    @Override
    public Foo save(Foo foo) {
        return fooRepository.save(foo);
    }

    @Override
    public Iterable<Foo> findAll() {
        return fooRepository.findAll();
    }
}
