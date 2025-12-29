Student Grade Management System – Lab 3 
Overview

This project is a console-based Student Grade Management System developed in Java.
It implements advanced features across multiple user stories (US‑1 to US‑10), including:

Student management with regex validation

Multi-format file operations using NIO.2

Concurrent batch report generation

Real-time statistics dashboard

Advanced search and query features

Scheduled tasks, caching, and audit trail logging

The system is designed with modular architecture, Git workflow best practices.

* Features Implemented (User Stories)
US‑1: LinkedList Grade History
Each student maintains a LinkedList of grades.

Supports efficient grade insertion and history traversal.

US‑2: Multi-Format File Operations with NIO.2
Export/import in CSV, JSON, Binary formats.

Uses java.nio.file.Files and Path for all operations.

Streaming for large CSV files (line-by-line, not full memory load).

UTF‑8 encoding enforced.

Separate directories:

Code
./data/csv
./data/json
./data/binary
./data/import
./data/cache
./data/audit
./reports/csv
./reports/json
./reports/binary

WatchService detects new files in ./data/import.

US‑3: Regex Input Validation
Validates Student ID, Name, Email, Phone, Date with precompiled regex patterns.

Clear error messages with examples.

Integrated into AddStudentConsole.

US‑4: Concurrent Batch Report Generation
Uses FixedThreadPool for parallel report generation.

Outputs timing and throughput metrics.

Thread-safe caching of reports.

US‑5: Advanced Stream Analytics
Parallel streams for statistics (average GPA, grade distribution).

Performance benchmarks included.

US‑6: Pattern-Based Search
Regex-based search for students and grades.

Supports flexible queries (e.g., names, course codes).

US‑7: Scheduled Tasks
Automated tasks (e.g., nightly report generation).

Uses ScheduledExecutorService.

US‑8: Caching System & Audit Trail
Cache layer for frequently accessed reports.

Audit trail logs stored in ./data/audit.

US-9: Concurrent Audit Trail

US-10:Stream-Based Data processing


 How to Run
Compile the project  
In IntelliJ: Build → Build Project.

Run Main  
Right‑click Main.java → Run.

Main Menu  
You’ll see:

Code
STUDENT GRADE MANAGEMENT - MAIN MENU

...
1. Add Student (with validation)
2. View Students
...
5. Export Grade Report (CSV/JSON/Binary)
6. Import Data (Multi-format support) [ENHANCED]
...
10. Real-Time Statistics Dashboard [NEW]
...
18. Audit Trail Viewer [NEW]
19. Exit
Test features

Add students with valid/invalid inputs (US‑3).

Export/import reports (US‑2).

Generate batch reports (US‑4).

Run analytics and dashboard (US‑5).

Search with regex (US‑6).

Schedule tasks (US‑7).

View cache and audit logs (US‑8).

 Testing & Verification
Validation: Enter invalid emails, phone numbers, IDs → error messages with examples.

File Ops: Export/import CSV/JSON/Binary → check file size and time metrics.

Concurrency: Generate batch reports → verify parallel execution and timing.

WatchService: Drop a file into ./data/import → auto-detection and import.

Audit Trail: Check ./data/audit for logs.

Cache: Verify cached GPA reports.

* Git Workflow Requirements
Branch strategy:

main (protected)

develop

feature/* branches for each user story (e.g., feature/nio2-file-operations).

Commit requirements:

≥30 meaningful commits.

Conventional commit format:

feat(nio2): add streaming CSV reader

fix(regex): correct phone pattern

test(concurrent): add thread-safety tests

Workflow steps:

Create feature branch from develop.

Implement feature with ≥3 commits.

Merge develop into feature branch regularly.

Open PR → review → merge into develop.

Tag release after merging into main:

Code
git commit -m "Lab 3: Advanced Features & Concurrency"
git push origin 

Screenshots Checklist
Main Menu

Add Student (valid + invalid)

View Students

Export CSV/JSON/Binary (with size/time)

Import CSV/JSON/Binary (with size/time)

Bulk Import Grades

GPA calculation

Class statistics

Real-time dashboard

Batch reports

Advanced search

Pattern-based search

Query grade history

Scheduled tasks

System performance

Cache management

Audit trail viewer

Exit




<img width="1919" height="941" alt="Screenshot 2025-12-29 192609" src="https://github.com/user-attachments/assets/ac9b4ca6-1b38-403e-96c4-0ce12cf64147" />
<img width="1913" height="893" alt="Screenshot 2025-12-29 192454" src="https://github.com/user-attachments/assets/7adb0c94-ab67-428d-9950-4bbc57fd632f" />
<img width="1862" height="860" alt="Screenshot 2025-12-29 192319" src="https://github.com/user-attachments/assets/6eeebd04-5fbb-46a8-bc59-ee1758288a5e" />
<img width="1919" height="943" alt="Screenshot 2025-12-29 192233" src="https://github.com/user-attachments/assets/1897d435-5219-4125-aa13-194fef82bc44" />
<img width="1822" height="892" alt="Screenshot 2025-12-29 192046" src="https://github.com/user-attachments/assets/b86bcdd0-77f0-4070-a40a-e5c8ebbdfc1d" />
<img width="1919" height="1001" alt="Screenshot 2025-12-29 191613" src="https://github.com/user-attachments/assets/f97cab07-6122-411d-abbf-28e875371fd2" />
<img width="979" height="891" alt="Screenshot 2025-12-29 191503" src="https://github.com/user-attachments/assets/60786010-f841-4e67-b13b-e7e72336d0f6" />
<img width="1919" height="882" alt="Screenshot 2025-12-29 191115" src="https://github.com/user-attachments/assets/8d77435e-6789-439b-8519-ea6b1191faa9" />
<img width="1916" height="880" alt="Screenshot 2025-12-29 191045" src="https://github.com/user-attachments/assets/ee5e9aa8-43d3-439c-a1ff-22b7ad199e14" />



<img width="1664" height="460" alt="Screenshot 2025-12-18 191113" src="https://github.com/user-attachments/assets/6a74831e-4a94-4f8d-8f12-39aa9d1f3d47" />
<img width="737" height="516" alt="Screenshot 2025-12-18 190516" src="https://github.com/user-attachments/assets/7a471ae2-a1b6-473e-b538-6723e694b077" />
<img width="900" height="728" alt="Screenshot 2025-12-18 190453" src="https://github.com/user-attachments/assets/476669f8-efa0-4603-bca1-c5cbc532beb7" />
<img width="692" height="766" alt="Screenshot 2025-12-18 190951" src="https://github.com/user-attachments/assets/3d95c890-00e4-44bf-bc5d-cf663e749509" />
<img width="789" height="690" alt="Screenshot 2025-12-18 192940" src="https://github.com/user-attachments/assets/e2ab1371-562f-4ec6-8d14-07d2320f15be" />
<img width="1270" height="542" alt="Screenshot 2025-12-18 191307" src="https://github.com/user-attachments/assets/bb214532-d780-47ac-a806-c7e8ee389056" />
<img width="924" height="756" alt="Screenshot 2025-12-18 191531" src="https://github.com/user-attachments/assets/3c20254c-4e17-4300-bac6-6f24716fd96a" />
<img width="1442" height="554" alt="Screenshot 2025-12-18 192348" src="https://github.com/user-attachments/assets/8a3c3fdd-cc0d-4ead-8432-b4c729efd90f" />
<img width="1218" height="735" alt="Screenshot 2025-12-18 194008" src="https://github.com/user-attachments/assets/d0ab1a95-39f2-4c9c-b172-93e174b6eb9c" />
<img width="713" height="693" alt="Screenshot 2025-12-18 193542" src="https://github.com/user-attachments/assets/2b51e725-cc40-430e-8990-a503af7f5bf8" />
<img width="814" height="531" alt="Screenshot 2025-12-18 193316" src="https://github.com/user-attachments/assets/c67bac60-9042-499f-85a3-c0d12f921a03" />
<img width="912" height="741" alt="Screenshot 2025-12-18 192701" src="https://github.com/user-attachments/assets/65d295a3-a508-4e83-8543-210851d23360" />
<img width="1408" height="225" alt="Screenshot 2025-12-18 192443" src="https://github.com/user-attachments/assets/99705266-6f7b-4675-a76c-5b6379b55b4e" />
<img width="1399" height="555" alt="Screenshot 2025-12-18 192230" src="https://github.com/user-attachments/assets/c9dc01f9-7bfc-45a1-a4f5-9073cd50b091" />
<img width="1506" height="474" alt="Screenshot 2025-12-18 192135" src="https://github.com/user-attachments/assets/758e5e65-c88d-40de-80e4-89c548fc5793" />
<img width="1293" height="768" alt="Screenshot 2025-12-18 194313" src="https://github.com/user-attachments/assets/01f09e90-70dc-410b-ae31-06dfb34d252c" />






