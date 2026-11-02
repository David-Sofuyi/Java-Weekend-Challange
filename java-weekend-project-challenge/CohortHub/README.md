# 04 CohortHub | Learners & Study Groups

## Focus: Java Collection Framework
A management system for a cohort coordinator to handle learner records, study group memberships, and mentor support queues using the right Collections.

## Features
- Register learners with unique IDs and emails (rejects duplicates).
- View learners sorted alphabetically by name, then by ID.
- Create study groups and assign learners (prevents duplicate memberships).
- Add learners to a mentor-support queue and attend to them in First-In-First-Out (FIFO) order.
- Display live statistics (total learners, groups, waiting count).
- **Stretch Goal:** Show intersection/union of learners in multiple groups.

## Key Design Decisions
- **Map (`HashMap`):** Used for O(1) lookup of learners by their unique ID.
- **Set (`HashSet`):** Used for study group memberships to automatically prevent duplicate learners in the same group.
- **Queue (`LinkedList`):** Used for the mentor-support queue to enforce strict FIFO arrival order.
- **List (`ArrayList`):** Used to create a sorted view of learners without corrupting the underlying `Map` lookup.
- **Immutability:** The `Learner` class implements `equals()` and `hashCode()` using the immutable `id` field so it behaves correctly inside Sets and Maps.

## How to Run
Compile and run `Main.java` inside `src/com/semicolon/coorthub`.