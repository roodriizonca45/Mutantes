package org.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void testMutantHorizontal() {
        String[] dna = {
                "AAAA",
                "CGTG",
                "TTAT",
                "AGAC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantVertical() {
        String[] dna = {
                "ATGCGA",
                "AAGTGC",
                "ATATGT",
                "AGTAGG",
                "ATCCTA",
                "TTACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalRight() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTATTA",
                "AGATGG",
                "CCCATA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalLeft() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTCTGT",
                "AGACTG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testHumanNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testHumanOneSequenceOnly() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidCharacters() {
        String[] dna = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    void testEmptyDna() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidNonSquare() {
        String[] dna = {
                "ATGCA",
                "CAGTG",
                "TTATG"
        };
        assertFalse(detector.isMutant(dna));
    }
}
