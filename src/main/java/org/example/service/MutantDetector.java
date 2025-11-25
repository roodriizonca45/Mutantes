package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MutantDetector {

    private static final int SEQ = 4;
    private static final Set<Character> VALID = Set.of('A', 'T', 'C', 'G');

    public boolean isMutant(String[] dna) {
        if (!isValid(dna)) return false;

        int n = dna.length;
        char[][] m = new char[n][];
        for (int i = 0; i < n; i++) m[i] = dna[i].toCharArray();

        int count = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {

                if (c <= n - SEQ && checkH(m, r, c) && ++count > 1) return true;
                if (r <= n - SEQ && checkV(m, r, c) && ++count > 1) return true;
                if (r <= n - SEQ && c <= n - SEQ && checkD(m, r, c) && ++count > 1) return true;
                if (r <= n - SEQ && c >= SEQ - 1 && checkDR(m, r, c) && ++count > 1) return true;
            }
        }
        return false;
    }

    private boolean isValid(String[] dna) {
        if (dna == null || dna.length == 0) return false;
        int n = dna.length;
        for (String row : dna) {
            if (row.length() != n) return false;
            for (char c : row.toCharArray())
                if (!VALID.contains(c)) return false;
        }
        return true;
    }

    private boolean checkH(char[][] m, int r, int c) {
        char b = m[r][c];
        return m[r][c+1]==b && m[r][c+2]==b && m[r][c+3]==b;
    }

    private boolean checkV(char[][] m, int r, int c) {
        char b = m[r][c];
        return m[r+1][c]==b && m[r+2][c]==b && m[r+3][c]==b;
    }

    private boolean checkD(char[][] m, int r, int c) {
        char b = m[r][c];
        return m[r+1][c+1]==b && m[r+2][c+2]==b && m[r+3][c+3]==b;
    }

    private boolean checkDR(char[][] m, int r, int c) {
        char b = m[r][c];
        return m[r+1][c-1]==b && m[r+2][c-2]==b && m[r+3][c-3]==b;
    }
}

