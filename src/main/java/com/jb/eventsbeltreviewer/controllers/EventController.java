package com.jb.eventsbeltreviewer.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jb.eventsbeltreviewer.models.Event;
import com.jb.eventsbeltreviewer.models.Message;
import com.jb.eventsbeltreviewer.models.User;
import com.jb.eventsbeltreviewer.services.EventService;
import com.jb.eventsbeltreviewer.services.UserService;

@Controller
public class EventController {
	private final EventService eventService;
	private final UserService userService;
	public EventController(EventService eventService, UserService userService) {
		this.eventService = eventService;
		this.userService = userService;
	}

	@RequestMapping(value="/events/create", method=RequestMethod.POST)
    public String createEvent(@Valid @ModelAttribute("newEvent") Event event, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
        	Long id = (Long) session.getAttribute("userId");
    		User user = userService.findUserById(id);
    		model.addAttribute("user", user);
    		String state = user.getState();
    		List<Event> inStateEvents = eventService.findEventsByState(state);
    		List<Event> outStateEvents = eventService.findEventsNotInState(state);
    		model.addAttribute("inStateEvents", inStateEvents);
    		model.addAttribute("outStateEvents", outStateEvents);
            return "home.jsp";
        }
        else {
            eventService.create(event);
            return "redirect:/home";
        }
    }
	
	@RequestMapping(value="/events/join", method=RequestMethod.POST)
	public String joinEvent(@RequestParam("eventId") Long id, HttpSession session) {
		Long userIdNum = (Long) session.getAttribute("userId");
		User user = userService.findUserById(userIdNum);
		Event event = eventService.findEvent(id);
		List<User> participants = event.getParticipants();
		participants.add(user);
		event.setParticipants(participants);
		eventService.save(event);
		return "redirect:/home";
	}
	
	@RequestMapping(value="/events/cancel", method=RequestMethod.POST)
	public String leaveEvent(@RequestParam("eventId") Long id, HttpSession session) {
		Long userIdNum = (Long) session.getAttribute("userId");
		User user = userService.findUserById(userIdNum);
		Event event = eventService.findEvent(id);
		List<User> participants = event.getParticipants();
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i) == user) {
				participants.remove(participants.get(i));
			}
		}
		eventService.save(event);
		return "redirect:/home";
	}
	
	@RequestMapping(value="/events/delete", method=RequestMethod.DELETE)
	public String deleteEvent(@RequestParam("eventId") Long eventId) {
		eventService.deleteEvent(eventId);
		return "redirect:/home";
	}
	
	@RequestMapping("/events/edit/{id}")
	public String editEvent(@PathVariable("id") Long eventId, Model model, HttpSession session) {
		Event event = eventService.findEvent(eventId);
		model.addAttribute("updateEvent", event);
		Long id = (Long) session.getAttribute("userId");
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
		return "updateEvent.jsp";
	}
	
	@RequestMapping(value="/events/{id}", method=RequestMethod.PUT)
	public String updateEvent(@Valid @ModelAttribute("updateEvent") Event updateEvent, BindingResult result, @PathVariable("id") Long eventId, Model model, HttpSession session) {
		if (result.hasErrors()) {
			Event event = eventService.findEvent(eventId);
			model.addAttribute("updateEvent", event);
			Long id = (Long) session.getAttribute("userId");
			User user = userService.findUserById(id);
			model.addAttribute("user", user);
			return "updateEvent.jsp";
		}
		else {
			Event event = eventService.findEvent(eventId);
			List<User> participants =  event.getParticipants();
			List<Message> messages = event.getMessages();
			updateEvent.setMessages(messages);
			updateEvent.setParticipants(participants);
			eventService.updateEvent(updateEvent);
			return "redirect:/home";
		}
	}
	
	@RequestMapping("/events/{id}")
	public String showEvent(@PathVariable("id") Long eventId, Model model, HttpSession session) {
		Event event = eventService.findEvent(eventId);
		model.addAttribute("event", event);
		Long id = (Long) session.getAttribute("userId");
		User user = userService.findUserById(id);
		model.addAttribute("user", user);
//		model.addAttribute("newMessage", new Message());
		List<User> participants = event.getParticipants();
		List<Message> messages = eventService.findAllMessages();
		model.addAttribute("messages", messages);
		Integer count = eventService.countParticipants(participants);
		model.addAttribute("count", count);
		return "event.jsp";
	}
	
	@RequestMapping(value="/messages/create/{id}", method=RequestMethod.POST)
	public String createMessage(@RequestParam("user") Long userId, @RequestParam("text") String text, @PathVariable("id") Long eventId, Model model, HttpSession session) {
//		if (result.hasErrors()) {
//			Event event = eventService.findEvent(eventId);
//			model.addAttribute("event", event);
//			Long id = (Long) session.getAttribute("userId");
//			User user = userService.findUserById(id);
//			model.addAttribute("user", user);
//			model.addAttribute("newMessage", new Message());
//			List<User> participants = event.getParticipants();
//			Integer count = eventService.countParticipants(participants);
//			model.addAttribute("count", count);
//			return "event.jsp";
//		}
		User u = userService.findUserById(userId);
		Event event = eventService.findEvent(eventId);
		Message message = new Message(text, u, event);
		eventService.createMessage(message);
		String redirect = String.format("redirect:/events/%d", eventId);
		return redirect;
	}
}
