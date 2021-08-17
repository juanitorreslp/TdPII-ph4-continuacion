package org.tdp2.ph4;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import org.tdp2.ph4.ValuesMPU;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ValuesMPURepository extends CrudRepository<ValuesMPU, Integer> {

    List<ValuesMPU> findAll();

}