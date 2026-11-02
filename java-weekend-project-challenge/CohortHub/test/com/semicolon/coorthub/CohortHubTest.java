package com.semicolon.coorthub;

import com.semicolon.coorthub.model.Learner;
import com.semicolon.coorthub.service.CohortManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CohortHubTest {

    private CohortManager manager;

    @BeforeEach
    void setUp() {
        manager = new CohortManager();
    }

    @Test
    void testRejectDuplicateIdsAndEmails() {
        manager.registerLearner("L01", "Alice", "alice@example.com");

        assertThrows(IllegalArgumentException.class, () -> {
            manager.registerLearner("L01", "Bob", "bob@example.com");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            manager.registerLearner("L02", "Charlie", "alice@example.com");
        });
    }

    @Test
    void testNoDuplicateGroupMembers() {
        manager.registerLearner("L01", "Alice", "alice@example.com");
        manager.createGroup("Java Study Group");

        manager.addLearnerToGroup("L01", "Java Study Group");
        manager.addLearnerToGroup("L01", "Java Study Group");

        Set<Learner> members = manager.getGroupMembers("Java Study Group");
        assertEquals(1, members.size());
    }

    @Test
    void testSupportQueueFIFO() {
        manager.registerLearner("L01", "Alice", "alice@example.com");
        manager.registerLearner("L02", "Bob", "bob@example.com");

        manager.addToSupportQueue("L02");
        manager.addToSupportQueue("L01");

        assertEquals(2, manager.getWaitingCount());

        Learner firstAttended = manager.attendNextInQueue();
        assertEquals("L02", firstAttended.getId());

        Learner secondAttended = manager.attendNextInQueue();
        assertEquals("L01", secondAttended.getId());

        assertEquals(0, manager.getWaitingCount());
    }

    @Test
    void testCleanUpOnRemove() {
        manager.registerLearner("L01", "Alice", "alice@example.com");
        manager.createGroup("Group A");
        manager.addLearnerToGroup("L01", "Group A");
        manager.addToSupportQueue("L01");

        manager.removeLearner("L01");

        assertNull(manager.findLearner("L01"));
        assertTrue(manager.getGroupMembers("Group A").isEmpty());
        assertEquals(0, manager.getWaitingCount());
    }

    @Test
    void testSortingDoesNotCorruptLookup() {
        manager.registerLearner("L03", "Charlie", "charlie@example.com");
        manager.registerLearner("L01", "Alice", "alice@example.com");
        manager.registerLearner("L02", "Bob", "bob@example.com");

        List<Learner> sorted = manager.getSortedLearners();

        assertEquals("L01", sorted.get(0).getId());
        assertEquals("L02", sorted.get(1).getId());
        assertEquals("L03", sorted.get(2).getId());

        Learner alice = manager.findLearner("L01");
        assertNotNull(alice);
        assertEquals("Alice", alice.getName());
    }
}
