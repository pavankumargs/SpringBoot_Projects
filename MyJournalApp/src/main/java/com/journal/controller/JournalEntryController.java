package com.journal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.entity.JournalEntry;
import com.journal.service.JournalEntryService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

	@Autowired
	private JournalEntryService journalEntryService;

	@PostMapping
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
		try {
			journalEntryService.saveEntry(journalEntry);
			return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<JournalEntry> all = journalEntryService.getAll();
		if (all != null && !all.isEmpty()) {
			return new ResponseEntity<>(all, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<?> getJournalEntryById(@PathVariable("id") Long id) {
		Optional<JournalEntry> jouranlEntry = journalEntryService.findById(id);
		if (jouranlEntry.isPresent()) {
			return new ResponseEntity<>(jouranlEntry, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable("id") Long id) {
		journalEntryService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<?> updateJournalEntryById(@PathVariable Long id, @RequestBody JournalEntry journalEntry) {
		JournalEntry old = journalEntryService.findById(id).orElse(null);
		if (old != null) {
			old.setTitle(
					journalEntry.getTitle() != null && !journalEntry.getTitle().equals("") ? journalEntry.getTitle()
							: old.getTitle());
			old.setContent(journalEntry.getContent() != null && journalEntry.equals("") ? journalEntry.getContent()
					: old.getContent());
			journalEntryService.saveEntry(old);
			return new ResponseEntity<>(old, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
