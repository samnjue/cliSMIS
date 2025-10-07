# CLI Student Management Information System (SMIS)

#### Overview
The CLI Student Management Information System (SMIS) is a command-line application designed to manage student records. I built it incrementally to demonstrate proficiency in core Java concepts, object-oriented programming (OOP) principles, and modern Spring Boot practices.

The application allows users to perform CRUD (Create, Read, Update, Delete) operations on student records, manage course enrollments and grades, and generate reports using a non-blocking, multithreaded process. Data is persisted to a local CSV file using Java's built-in I/O streams.

### Core Features & Demonstrated Concepts
| Concept | Implementation Details|
| :---: | :---: |
| Object-Oriented Programming (OOP) | Inheritance (`Student` extends `Person`), Abstraction (`Person` abstract class, `DataRepository` interface), and Encapsulation (private fields with getters/setters). |
| Spring Boot / DI | Uses Spring Boot for application context management and Dependency Injection (DI) for managing `StudentService` and `FileRepository` components. |
| Data Structures | Uses `HashMap` for fast, O(1) student ID lookups and duplicate checks, and `Queue` (`LinkedList`) to simulate an enrollment queue. |
| File I/O & Persistence | Data is persisted to a `students.csv` file using low-level Java I/O Streams (`BufferedReader`/`BufferedWriter`). |
| Java 8+ Features | Streams and Lambdas for filtering (`searchByName`), sorting (`sortStudentsByName`), and data aggregation (report generation). `Optional` is used for safe, null-free searching (`findById`). |
| Multithreading | Implemented a `ReportGenerator` using a background `ExecutorService` (thread pool) to run long-running report tasks without blocking the main CLI thread. |
| Exception Handling | Custom exceptions (e.g., `DuplicateStudentException`) for business rule validation and robust `try-catch` blocks for I/O errors and invalid user input. |

### Getting Started
#### Prerequisites
· Java Development Kit (JDK) 17+

· Apache Maven (for dependency management and running the application)
#### Build and Run
1. Clone the repository:
```bash
git clone https://github.com/samnjue/cliSMIS
cd cli-smis
```
2. Run the application:
```bash
./mvnw spring-boot:run
```
3. The application will start, load any existing data from `students.csv`, and present the main menu in the console.

### CLI Usage

| Concept | Implementation Details| Concept Demonstrated |
| :---: | :---: | :---: |
| 1. Add Student | Collects input for Name, Age, and ID. Uses `HashMap` to check for ID uniqueness. | Exception Handling |
| 2. Find Student by ID | Searches the repository by ID. Uses `Optional.ifPresentOrElse()` for safe display. | Java 8 `Optional` |
| 3. Search Students by Name | Searches for partial name matches. Uses Java Streams and a Lambda filter. | Java 8 Streams |
| 5. Enroll Student in Course | Enrolls an existing student in a new course. | OOP (Encapsulation) |
| 7. Generate Grade Report | Kicks off a background thread to calculate average enrollment. | Multithreading (`ExecutorService`) |
| 8. Exit and Save | Saves all current data to `students.csv` and gracefully shuts down the thread pool. | File I/O, Concurrency |

## Author
· Sammy Njue

· Built as a guided learning project focusing on Core Java and OOP principles.
