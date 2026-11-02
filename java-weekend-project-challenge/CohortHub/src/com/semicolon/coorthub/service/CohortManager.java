package com.semicolon.coorthub.service;

import com.semicolon.coorthub.model.Learner;

import java.util.*;

public class CohortManager {
    private final Map<String, Learner> learnersById;

    private final Map<String, Set<Learner>> studyGroups;

    private final Queue<Learner> supportQueue;

    public CohortManager() {
        this.learnersById = new HashMap<>();
        this.studyGroups = new HashMap<>();
        this.supportQueue = new LinkedList<>();
    }

    public void registerLearner(String id, String name, String email) {
        if (learnersById.containsKey(id)) {
            throw new IllegalArgumentException("Learner with ID " + id + " already exists.");
        }
        for (Learner learner : learnersById.values()) {
            if (learner.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email " + email + " is already registered.");
            }
        }
        Learner learner = new Learner(id, name, email);
        learnersById.put(id, learner);
    }

    public Learner findLearner(String id) {
        return learnersById.get(id);
    }

    public List<Learner> getSortedLearners() {
        List<Learner> sortedList = new ArrayList<>(learnersById.values());

        sortedList.sort(new Comparator<Learner>() {
            @Override
            public int compare(Learner l1, Learner l2) {
                int nameCompare = l1.getName().compareToIgnoreCase(l2.getName());
                if (nameCompare != 0) {
                    return nameCompare;
                }
                return l1.getId().compareTo(l2.getId());
            }
        });
        return sortedList;
    }

    public void createGroup(String groupName) {
        studyGroups.putIfAbsent(groupName, new HashSet<>());
    }

    public void addLearnerToGroup(String learnerId, String groupName) {
        Learner learner = learnersById.get(learnerId);
        if (learner == null) throw new IllegalArgumentException("Learner not found.");

        Set<Learner> group = studyGroups.get(groupName);
        if (group == null) throw new IllegalArgumentException("Group not found.");

        group.add(learner);
    }

    public void addToSupportQueue(String learnerId) {
        Learner learner = learnersById.get(learnerId);
        if (learner == null) throw new IllegalArgumentException("Learner not found.");

        if (!supportQueue.contains(learner)) {
            supportQueue.add(learner);
        }
    }

    public Learner attendNextInQueue() {
        return supportQueue.poll();
    }

    public int getWaitingCount() {
        return supportQueue.size();
    }

    public void removeLearner(String learnerId) {
        Learner learner = learnersById.remove(learnerId);
        if (learner != null) {
            for (Set<Learner> group : studyGroups.values()) {
                group.remove(learner);
            }
            supportQueue.remove(learner);
        }
    }

    public int getTotalLearners() { return learnersById.size(); }
    public int getGroupCount() { return studyGroups.size(); }
    public Set<Learner> getGroupMembers(String groupName) {
        return studyGroups.getOrDefault(groupName, new HashSet<>());
    }
}
