Student Grade Management System — Lab 3

Overview
This project is a Java console application for managing students and grades.
In Lab 3, I extended the system with advanced features to improve performance, reliability, and usability.

Features Implemented
1. Optimized Collections
HashMap for fast student lookups by ID (O(1) access).

TreeMap for sorted GPA rankings and grade reports.

HashSet to track unique courses.

ArrayList for ordered student lists.

LinkedList for grade history where frequent insertions/deletions are needed.

ConcurrentHashMap for thread-safe caching and statistics.

PriorityQueue for scheduling tasks by priority.

This ensures better performance compared to basic structures used in earlier labs.

2. Multi-Format File I/O (NIO.2)
Import and export data in CSV, JSON, and Binary formats.

Large CSV files are processed line-by-line using Files.lines() streaming (no full file load).

Proper resource management with try-with-resources.

Export directories organized by format (./reports/csv/, ./reports/json/, ./reports/binary/).

File size, read/write times, and compression ratios are displayed after operations.

3. Regex-Based Validation
Validates all user inputs with clear error messages.

Patterns include:

Student ID: STU### (e.g., STU123)

Email: username@domain.extension

Phone: multiple formats like (123) 456-7890, +1-123-456-7890

Name: letters, spaces, hyphens, apostrophes only

Date: YYYY-MM-DD

Course Code: ABC123

Grade: 0–100

This ensures data integrity across the system.

4. Concurrent Operations
Batch report generation using thread pools (2–8 threads).

Real-time statistics dashboard with a background thread refreshing every 5 seconds.

Scheduled tasks for daily GPA recalculation, weekly reports, and hourly cache refresh.

Thread-safe file writing and proper shutdown of executors.

5. Real-Time Statistics Dashboard
Continuously updates class statistics in the background.

Displays grade distribution, averages, median, standard deviation, and top performers.

Shows thread pool status, cache hit rate, and memory usage.

Manual controls: refresh, pause, resume.

6. Scheduled Automated Tasks
Configurable recurring tasks:

Daily GPA recalculation

Weekly grade report generation

Hourly cache refresh

Daily database backup

Each task shows next run time, last run status, and execution duration.

Notifications simulated via console output and log entries.

7. Pattern-Based Search
Search students using regex patterns:

By email domain (e.g., @university.edu)

By phone area code

By student ID wildcard (e.g., STU0**)

By name substring or regex

Custom regex input

Displays matched results, statistics, and distribution breakdowns.

8. Thread-Safe Caching System
Implemented with ConcurrentHashMap.

Stores frequently accessed data (students, reports, statistics).

LRU eviction policy with max 150 entries.

Displays hit/miss rates, average access times, memory usage, and eviction count.

Cache warming on startup and background refresh of stale entries.

9. Audit Trail
Logs all operations (add student, record grade, generate report, search, etc.).

Each entry includes timestamp, thread ID, operation type, execution time, and status.

Logs written asynchronously to avoid blocking.

Log rotation (daily or when file exceeds 10MB).

Viewer supports filtering by date, operation type, or thread ID.

10. Stream-Based Data Processing
Used Java Streams for modern, readable data handling.

Examples implemented:

Find honors students with GPA > 3.5

Group students by grade range (A/B/C/D/F)

Calculate average grade per subject

Extract unique course codes

Find top 5 students by average grade

Compare sequential vs parallel stream performance

Operations timed and logged for performance comparison.


console output pictures:

<img width="1293" height="768" alt="Screenshot 2025-12-18 194313" src="https://github.com/user-attachments/assets/b6262cd3-2e03-45d6-958e-1b1f680a3b69" />
<img width="1218" height="735" alt="Screenshot 2025-12-18 194008" src="https://github.com/user-attachments/assets/46e56ab3-f01e-4336-b853-706187be3954" />
<img width="713" height="693" alt="Screenshot 2025-12-18 193542" src="https://github.com/user-attachments/assets/627e9064-a85e-48e1-8d50-2d86749a31a1" />
<img width="814" height="531" alt="Screenshot 2025-12-18 193316" src="https://github.com/user-attachments/assets/e157f96e-f0a6-4587-aff6-3ff815dc35ce" />
<img width="789" height="690" alt="Screenshot 2025-12-18 192940" src="https://github.com/user-attachments/assets/c52b7c90-2e72-4a99-bc2b-293cce537ec4" />
<img width="912" height="741" alt="Screenshot 2025-12-18 192701" src="https://github.com/user-attachments/assets/e756f172-b130-4a76-b165-8d44d6d536ab" />
<img width="1408" height="225" alt="Screenshot 2025-12-18 192443" src="https://github.com/user-attachments/assets/a6820210-f2fc-4507-9cae-6706017552a7" />
<img width="1442" height="554" alt="Screenshot 2025-12-18 192348" src="https://github.com/user-attachments/assets/9b1bacdb-5232-454b-9a6c-d29217dc2fd2" />
<img width="1399" height="555" alt="Screenshot 2025-12-18 192230" src="https://github.com/user-attachments/assets/ed54e536-2666-4148-9874-5214df329f5a" />
<img width="1506" height="474" alt="Screenshot 2025-12-18 192135" src="https://github.com/user-attachments/assets/9990e6e1-c817-4b58-abbe-641e00fb0b9c" />
<img width="924" height="756" alt="Screenshot 2025-12-18 191531" src="https://github.com/user-attachments/assets/62b11590-b4ae-44a2-a3c1-4b53c97c3afd" />
<img width="1270" height="542" alt="Screenshot 2025-12-18 191307" src="https://github.com/user-attachments/assets/71df60da-991c-46d7-a9eb-cba9b5d2dae2" />
<img width="1664" height="460" alt="Screenshot 2025-12-18 191113" src="https://github.com/user-attachments/assets/4e5670fa-315b-4f41-a645-e3879441671a" />
<img width="692" height="766" alt="Screenshot 2025-12-18 190951" src="https://github.com/user-attachments/assets/73e30e6b-e696-4154-94c8-63c284e08c43" />
<img width="737" height="516" alt="Screenshot 2025-12-18 190516" src="https://github.com/user-attachments/assets/3795e3bc-062d-4bd1-8569-abb0bda9fdac" />
<img width="900" height="728" alt="Screenshot 2025-12-18 190453" src="https://github.com/user-attachments/assets/afb4fbdd-d7aa-41e9-982e-7192ff7c0030" />
