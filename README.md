# Student Performance Analytics Dashboard

A simple, beginner-to-intermediate project demonstrating Java OOP concepts, CSV data handling, and a clean web-based dashboard.

## Features
- **CSV Data Parsing**: Loads student data (Name, Marks) from a CSV file using Java.
- **Analytics Logic**: 
  - Calculates average marks.
  - Identifies the top performer.
  - Categorizes students:
    - Excellent (> 80)
    - Good (60 - 80)
    - Needs Improvement (< 60)
- **Interactive Dashboard**:
  - Summary cards for key metrics.
  - Data table with hover effects.
  - Filters for category and marks range.
  - Sorting by marks (Ascending/Descending).
- **File Upload**: Upload your own CSV file to see real-time updates.

## Tech Stack
- **Backend**: Pure Java (No frameworks like Spring Boot).
- **Frontend**: HTML5, CSS3, JavaScript (ES6).
- **Communication**: Simple Java HTTP Server (`com.sun.net.httpserver`).

## Folder Structure
```
student-analytics-dashboard/
├── data/
│   └── students.csv       # Sample data file
├── src/
│   ├── Student.java       # Model class
│   ├── DataProcessor.java # Logic & CSV processing
│   └── Main.java          # HTTP Server & API
├── web/
│   ├── index.html         # Frontend structure
│   ├── style.css          # Styling
│   └── script.js          # Frontend logic & API calls
└── README.md              # Project documentation
```

## How to Run

### Prerequisites
- Java Development Kit (JDK 8 or higher) installed.

### Steps
1. **Open Terminal/Command Prompt** in the project root directory.
2. **Compile the Java files**:
   ```bash
   javac src/*.java -d .
   ```
3. **Run the Server**:
   ```bash
   java Main
   ```
4. **Access the Dashboard**:
   Open your browser and go to: `http://localhost:8081`

## Interview Focus Points
- **OOP Concepts**: Usage of classes, objects, and private fields with getters.
- **Collections**: Storing data in `ArrayList`.
- **File I/O**: Reading CSV data using `BufferedReader`.
- **Clean Code**: Separation of concerns between Model (`Student`), Logic (`DataProcessor`), and Server (`Main`).
