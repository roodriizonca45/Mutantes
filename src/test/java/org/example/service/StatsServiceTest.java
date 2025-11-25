package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceTest {

    private final DnaRecordRepository repository = mock(DnaRecordRepository.class);
    private final StatsService service = new StatsService(repository);

    @Test
    void testStatsValues() {
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(5L);

        StatsResponse stats = service.getStats();

        assertEquals(10, stats.getCount_mutant_dna());
        assertEquals(5, stats.getCount_human_dna());
        assertEquals(2.0, stats.getRatio());
    }

    @Test
    void testZeroHumansRatio() {
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = service.getStats();

        assertEquals(0, stats.getRatio());
    }
}
