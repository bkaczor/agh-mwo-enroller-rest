package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

	@Autowired
	MeetingService meetingService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
	     Meeting meeting = meetingService.findById(id);
	     if (id <= 0) {
	         return new ResponseEntity(HttpStatus.NOT_FOUND);
	     }
	     return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	 }
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	 public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting) {
		Meeting foundMeeting = meetingService.findById(meeting.getId());
		if (foundMeeting != null) {
		
			return new ResponseEntity("Unable to create. A meeting with id " + meeting.getId() + " already exist.", HttpStatus.CONFLICT);
			}
		meetingService.add(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deleteMeeting(@PathVariable("id")long id) {
		Meeting meeting= meetingService.findById(id);
		if (meeting == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		meetingService.delete(meeting);
		return new ResponseEntity<Meeting>(HttpStatus.NO_CONTENT);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	 public ResponseEntity<?> deleteMeeting(@PathVariable("id")long id,
			 @RequestBody Meeting updatedParticipant) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		meeting.setDescription(updatedParticipant.getDescription());
		meetingService.update(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
		
	}
	
}
