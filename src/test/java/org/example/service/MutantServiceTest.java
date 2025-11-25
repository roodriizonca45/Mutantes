package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MutantServiceTest {

    private final MutantDetector detector = mock(MutantDetector.class);
    private final DnaRecordRepository repository = mock(DnaRecordRepository.class);

    private final MutantService service =
            new MutantService(detector, repository);

    @Test
    void testMutantCachedResult() {
        String[] dna = {"AAAA", "TTTT", "CCCC", "GGGG"};
        DnaRecord record = new DnaRecord();
        record.setMutant(true);

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.of(record));

        assertTrue(service.analyzeDna(dna));
    }

    @Test
    void testMutantNewRecord() {
        String[] dna = {"AAAA", "TTTT", "CCCC", "GGGG"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());

        when(detector.isMutant(dna))
                .thenReturn(true);

        assertTrue(service.analyzeDna(dna));

        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testHumanNewRecord() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};

        when(repository.findByDnaHash(anyString()))
                .thenReturn(Optional.empty());

        when(detector.isMutant(dna))
                .thenReturn(false);

        assertFalse(service.analyzeDna(dna));

        verify(repository, times(1)).save(any(DnaRecord.class));
    }
}

