# Java Weekend Project Challenge

Four console-based Java applications built to demonstrate practical use of
**Exception Handling**, **Strings**, **File Handling & I/O Streams**, and the
**Java Collection Framework**.

> Built individually as part of the Semicolon Africa Java Weekend Project Challenge.

---

## 👤 Author

**Sofuyi David**
- GitHub: [@DavidSofuyi](https://github.com/bigdev14bit)
- Email: dayodavid54@gmail.com

---

##  Projects

| # | Project | Focus | Description |
|---|---------|-------|-------------|
| 01 | [**SafeBank**](./01-safe-bank) | Exception Handling | Banking simulator with custom exceptions and protected balances. |
| 02 | [**TextLab**](./02-text-lab) | Strings | Text analysis & cleanup: counts, search, replace, palindromes. |
| 03 | [**StudyVault**](./03-study-vault) | File I/O | Persistent notes manager using BufferedReader/Writer & streams. |
| 04 | [**CohortHub**](./04-cohort-hub) | Collections | Learners, study groups, and a mentor-support queue. |

Each project folder contains its own `README.md`, source code, JUnit tests, and notes on design decisions.

---

##  Reflection

### 1. Which project was most challenging, and why?
**StudyVault** was the most challenging project. Managing the actual file system requires a lot of defensive programming. I had to ensure that filenames were strictly validated to prevent directory traversal attacks (like `../`), handle UTF-8 encoding correctly so emojis didn't break, and manage low-level byte copying with `FileInputStream` and `FileOutputStream` without relying on easy shortcuts like `Files.copy()`. Ensuring files were closed properly using `try-with-resources` was also a crucial learning curve.

### 2. Where did you use concepts from another topic?
I used **Collections** in the SafeBank project by storing the transaction history in an `ArrayList`, and I used **Strings** formatting to display the timestamped transactions. Additionally, in StudyVault, I used **Exception Handling** extensively to catch `IOException` and custom validation exceptions (like invalid filenames) to prevent the application from crashing when a user made a mistake.

### 3. What would you improve with more time?
If I had more time, I would:
1. Add a formal testing framework setup in Maven/Gradle so that JUnit runs automatically on every push via GitHub Actions (CI/CD).
2. Expand the StudyVault stretch goals to include the file size, line count, and last-modified timestamp for every note.
3. Build a shared "Menu" class using an interface to standardize the console UI across all four projects, reducing code duplication.

---

##  How to Run

### Prerequisites
- JDK 21+ (`java -version` to check)
- IntelliJ IDEA (or any Java IDE)
- JUnit 5 for running tests

### Running a project
1. Open the project folder in IntelliJ.
2. Navigate to the `Main.java` file inside the `src` folder.
3. Right-click and select **Run 'Main.main()'**.

---

##  License
MIT — see [LICENSE](./LICENSE).
