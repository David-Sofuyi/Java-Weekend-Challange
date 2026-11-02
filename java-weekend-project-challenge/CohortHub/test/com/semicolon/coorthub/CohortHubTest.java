
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

    // Test duplicate IDs and emails
    @Test
    void testRejectDuplicateIdsAndEmails() {
        manager.registerLearner("L01", "David", "david@example.com");

        assertThrows(IllegalArgumentException.class, () ->
                manager.registerLearner("L01", "Dav", "dav@example.com")
        );

        assertThrows(IllegalArgumentException.class, () ->
                manager.registerLearner("L02", "Freshie", "david@example.com")
        );
    }

    // Test that a learner cannot be added twice to a group
    @Test
    void testNoDuplicateGroupMembers() {
        manager.registerLearner("L01", "David", "david@example.com");
        manager.createGroup("Coding Study Group");

        manager.addLearnerToGroup("L01", "Coding Study Group");
        manager.addLearnerToGroup("L01", "Coding Study Group");

        Set<Learner> members =
                manager.getGroupMembers("Coding Study Group");

        assertEquals(1, members.size());
    }

    // Test support queue follows FIFO order
    @Test
    void testSupportQueueFIFO() {
        manager.registerLearner("L01", "David", "david@example.com");
        manager.registerLearner("L02", "Dav", "dav@example.com");

        manager.addToSupportQueue("L02");
        manager.addToSupportQueue("L01");

        assertEquals(2, manager.getWaitingCount());

        assertEquals("L02", manager.attendNextInQueue().getId());
        assertEquals("L01", manager.attendNextInQueue().getId());

        assertEquals(0, manager.getWaitingCount());
    }

    // Test removing a learner from all groups and queues
    @Test
    void testCleanUpOnRemove() {
        manager.registerLearner("L01", "David", "david@example.com");

        manager.createGroup("Group A");
        manager.addLearnerToGroup("L01", "Group A");
        manager.addToSupportQueue("L01");

        manager.removeLearner("L01");

        assertNull(manager.findLearner("L01"));
        assertTrue(manager.getGroupMembers("Group A").isEmpty());
        assertEquals(0, manager.getWaitingCount());
    }

    // Test sorting learners by name without affecting lookup
    @Test
    void testSortingDoesNotCorruptLookup() {
        manager.registerLearner("L03", "Freshie", "fresh@example.com");
        manager.registerLearner("L01", "David", "david@example.com");
        manager.registerLearner("L02", "dav", "dav@example.com");

        List<Learner> sorted = manager.getSortedLearners();

        assertEquals("L01", sorted.get(0).getId());
        assertEquals("L02", sorted.get(1).getId());
        assertEquals("L03", sorted.get(2).getId());

        Learner learner = manager.findLearner("L01");

        assertNotNull(learner);
        assertEquals("David", learner.getName());
    }
}
