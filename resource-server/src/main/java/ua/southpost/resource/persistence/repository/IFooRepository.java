package ua.southpost.resource.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.southpost.resource.persistence.model.Foo;

public interface IFooRepository extends PagingAndSortingRepository<Foo, Long> {
}
