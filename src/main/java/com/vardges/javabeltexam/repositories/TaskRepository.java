package com.vardges.javabeltexam.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.vardges.javabeltexam.models.Task;
import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>{
    List<Task> findAll();
}
