package datadog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ComplementingSetTest {

    private ComplementingSet complementingSet;
    private final String[][] oriStrings = new String[5][];
    private String[] flatOriStrings;
    private String[] filterStrings1 = new String[1];
    private String[] filterStrings2 = new String[2];

    @BeforeEach
    void populateData() {
        oriStrings[0] = new String[]{"apple", "facebook", "google"};
        oriStrings[1] = new String[]{"banana", "facebook"};
        oriStrings[2] = new String[]{"facebook", "google", "tesla", "apple"};
        oriStrings[3] = new String[]{"facebook", "google", "banana", "apple", "google", "banana"};
        oriStrings[4] = new String[]{"intuit", "google", "facebook"};
        flatOriStrings = Arrays.stream(oriStrings).flatMap(Arrays::stream).toArray(String[]::new);
        filterStrings1 = new String[]{"apple"};
        filterStrings2 = new String[]{"facebook", "google"};

        complementingSet = new ComplementingSet();
        // Generate inverted index map
        complementingSet.generateInvertedIndexMap(oriStrings);
    }

    @Test
    void when_passNullOriginalString_then_throwRuntimeException() {
        assertThrows(RuntimeException.class, () -> complementingSet.findComplementingSet(null, filterStrings1));
    }

    @Test
    void when_passEmpty_then_returnOneDimensionalOriginalString() {
        assertArrayEquals(flatOriStrings, complementingSet.findComplementingSet(oriStrings, null));
        assertArrayEquals(flatOriStrings, complementingSet.findComplementingSet(oriStrings, new String[]{}));
    }

    @Test
    void when_filterPracticalString_then_verifyResult() {
        assertArrayEquals(new String[]{"banana", "tesla", "facebook", "google"}, complementingSet.findComplementingSet(oriStrings, filterStrings1));
        assertArrayEquals(new String[]{"banana", "intuit", "apple", "tesla"}, complementingSet.findComplementingSet(oriStrings, filterStrings2));
    }
}