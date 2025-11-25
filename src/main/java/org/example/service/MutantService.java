package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;

    public boolean analyzeDna(String[] dna) {
        String hash = generateHash(dna);

        Optional<DnaRecord> existing = repository.findByDnaHash(hash);
        if (existing.isPresent()) return existing.get().isMutant();

        boolean isMutant = mutantDetector.isMutant(dna);

        DnaRecord record = new DnaRecord();
        record.setDnaHash(hash);
        record.setMutant(isMutant);
        record.setCreatedAt(LocalDateTime.now());
        repository.save(record);

        return isMutant;
    }

    private String generateHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] b = digest.digest(String.join("", dna).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte x : b) {
                String h = Integer.toHexString(0xff & x);
                if (h.length()==1) sb.append('0');
                sb.append(h);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new DnaHashCalculationException("Error con hash", e);
        }
    }
}
