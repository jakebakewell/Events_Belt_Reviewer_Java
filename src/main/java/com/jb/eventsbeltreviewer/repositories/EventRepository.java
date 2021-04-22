package com.jb.eventsbeltreviewer.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jb.eventsbeltreviewer.models.Event;
import com.jb.eventsbeltreviewer.models.User;

@Repository
public interface EventRepository  extends CrudRepository<Event, Long>{
	List<Event> findAll();
	Optional<Event> findById(Long id);
	void deleteById(Long id);
	List<Event> findByStateNotContains(String state);
	List<Event> findByStateContains(String state);
	void deleteUserInParticipantsById(Long id);
}
