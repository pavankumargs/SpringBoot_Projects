package com.journal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.journal.entity.JournalEntry;
import com.journal.repository.JournalEntryRepository;

@Service
public class JournalEntryService {
	
	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	public JournalEntry saveEntry(JournalEntry journalEntry) {
		journalEntry.setLocalDateTime(LocalDateTime.now());
		return journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry> getAll(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> findById(Long id){
		return journalEntryRepository.findById(id);
	}
	
	public void deleteById(Long id) {
		journalEntryRepository.deleteById(id);
	}
}
