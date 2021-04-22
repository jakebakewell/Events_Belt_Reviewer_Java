package com.jb.eventsbeltreviewer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jb.eventsbeltreviewer.models.Event;
import com.jb.eventsbeltreviewer.models.Message;
import com.jb.eventsbeltreviewer.models.User;
import com.jb.eventsbeltreviewer.repositories.EventRepository;
import com.jb.eventsbeltreviewer.repositories.MessageRepository;
import com.jb.eventsbeltreviewer.repositories.UserRepository;


@Service
public class EventService {
	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final MessageRepository messageRepository;
	public EventService(UserRepository userRepository, EventRepository eventRepository, MessageRepository messageRepository) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
		this.messageRepository = messageRepository;
	}
	
	public List<Event> findEventsByState(String state) {
		return eventRepository.findByStateContains(state);
	}
	
	public List<Event> findEventsNotInState(String state) {
		return eventRepository.findByStateNotContains(state);
	}
	
	public Event create(Event event) {
		return eventRepository.save(event);
	}
	
	public Event findEvent(Long id) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            return null;
        }
    }
	
	public void deleteEvent(Long id) {
    	eventRepository.deleteById(id);
    }
	
	public List<Event> allEvents() {
        return eventRepository.findAll();
    }
	
	public void save(Event e) {
		eventRepository.save(e);
	}
	
	public Event updateEvent(Event event) {
		return eventRepository.save(event);
	}
	
	public Integer countParticipants(List<User> participants) {
		Integer count = 0;
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i) != null) {
				count += 1;
			}
		}
		return count;
	}
	
	public Message createMessage(Message message) {
		return messageRepository.save(message);
	}
	
//	public List<Message> findEventMessages(Long id) {
//		return messageRepository.findByEventId(id);
//	}
	
	public List<Message> findAllMessages() {
		return messageRepository.findAll();
	}
}
