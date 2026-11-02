
package com.semicolon.coorthub.service;

import com.semicolon.coorthub.model.Learner;
import java.util.*;

public class CohortManager {

    private Map<String, Learner> learnersById = new HashMap<>();
    private Map<String, Set<Learner>> studyGroups = new HashMap<>();
    private Queue<Learner> supportQueue = new LinkedList<>();

    // Register a new learner
    public void registerLearner(String id, String name, String email) {
        if (learnersById.containsKey(id)) {
            throw new IllegalArgumentException("ID already exists.");
        }

        for (Learner learner : learnersById.values()) {
            if (learner.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email already exists.");
            }
        }

        Learner learner = new Learner(id, name, email);
        learnersById.put(id, learner);
    }

    // Find a learner
    public Learner findLearner(String id) {
        return learnersById.get(id);
    }

    // Get learners sorted by name

    public List<Learner> getSortedLearners() {
        List<Learner> learners =
                new ArrayList<>(learnersById.values());

        learners.sort(
                Comparator.comparing(
                        Learner::getName,
                        String.CASE_INSENSITIVE_ORDER
                ).thenComparing(Learner::getId)
        );

        return learners;
    }


    // Create a study group
    public void createGroup(String groupName) {
        studyGroups.putIfAbsent(groupName, new HashSet<>());
    }

    // Add learner to a study group
    public void addLearnerToGroup(String learnerId, String groupName) {
        Learner learner = learnersById.get(learnerId);

        if (learner == null) {
            throw new IllegalArgumentException("Learner not found.");
        }

        if (!studyGroups.containsKey(groupName)) {
            throw new IllegalArgumentException("Group not found.");
        }

        studyGroups.get(groupName).add(learner);
    }

    // Add learner to support queue
    public void addToSupportQueue(String learnerId) {
        Learner learner = learnersById.get(learnerId);

        if (learner == null) {
            throw new IllegalArgumentException("Learner not found.");
        }

        if (!supportQueue.contains(learner)) {
            supportQueue.add(learner);
        }
    }

    // Attend to the next learner
    public Learner attendNextInQueue() {
        return supportQueue.poll();
    }

    // Get number of learners waiting
    public int getWaitingCount() {
        return supportQueue.size();
    }

    // Remove a learner
    public void removeLearner(String learnerId) {
        Learner learner = learnersById.remove(learnerId);

        if (learner != null) {
            for (Set<Learner> group : studyGroups.values()) {
                group.remove(learner);
            }

            supportQueue.remove(learner);
        }
    }

    // Get total number of learners
    public int getTotalLearners() {
        return learnersById.size();
    }

    // Get total number of groups
    public int getGroupCount() {
        return studyGroups.size();
    }

    // Get members of a group
    public Set<Learner> getGroupMembers(String groupName) {
        return studyGroups.getOrDefault(groupName, new HashSet<>());
    }

}
