package com.jb.eventsbeltreviewer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jb.eventsbeltreviewer.models.Event;
import com.jb.eventsbeltreviewer.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findAll();
	Optional<Message> findById(Long id);
	void deleteById(Long id);
	List<Message> findByEvent(Event event);
}
