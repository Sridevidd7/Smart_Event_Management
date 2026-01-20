package com.college.eventclub.api;

import com.college.eventclub.Event;
import com.college.eventclub.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Event Management API Controller
 */
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventDAO eventDAO;

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        try {
            List<Event> events = eventDAO.getAllEvents();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", events,
                    "count", events.size()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to fetch events: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id) {
        try {
            Event event = eventDAO.getEventById(id);
            if (event == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Event not found"));
            }
            return ResponseEntity.ok(Map.of("success", true, "data", event));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to fetch event: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody Event event) {
        try {
            eventDAO.addEvent(event);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Event created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to create event: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody Event event) {
        try {
            event.setEventId(id);
            eventDAO.updateEvent(event);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Event updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to update event: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id) {
        try {
            eventDAO.deleteEvent(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Event deleted successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to delete event: " + e.getMessage()));
        }
    }
}
