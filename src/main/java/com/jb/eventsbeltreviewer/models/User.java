package com.jb.eventsbeltreviewer.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	@NotNull(message = "First name cannot be empty")
	@Size(min = 2, max = 25, message = "First name must be between 2 and 25 characters long")
	private String firstName;
	@NotNull(message = "Last name cannot be empty")
	@Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters long")
	private String lastName;
	@NotNull(message = "Email cannot be empty")
	@Size(min = 5, max = 180, message = "Email must be between 5 and 180 characters long")
	@Email(message = "Email must be valid")
	@Column(unique=true)
    private String email;
	@NotNull(message = "Location cannot be blank")
	@Size(min = 2, message = "Location must be at least 2 characters long")
	private String location;
	@NotNull(message = "State cannot be blank")
	private String state;
    @NotNull(message = "Password cannot be empty")
	@Size(min = 6, message = "Password must least 6 characters long")
    private String password;
    @Transient
    private String passwordConfirmation;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "participants_events", 
        joinColumns = @JoinColumn(name = "participant_id"), 
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;
    @OneToMany(mappedBy="creator", fetch = FetchType.LAZY)
    private List<Event> createdEvents;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "messages", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Message> messages;
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    public User() {
    }

	public User(
			String firstName, String lastName, String email, String location, String state, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.location = location;
		this.state = state;
		this.password = password;
	}

	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Event> getCreatedEvents() {
		return createdEvents;
	}

	public void setCreatedEvents(List<Event> createdEvents) {
		this.createdEvents = createdEvents;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
