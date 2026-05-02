import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {
    private List<Student> students;

    public DataProcessor() {
        this.students = new ArrayList<>();
    }

    public void loadFromCSV(String filePath) throws IOException {
        students.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] values = line.split(",");
                if (values.length >= 2) {
                    String name = values[0].trim();
                    try {
                        double marks = Double.parseDouble(values[1].trim());
                        students.add(new Student(name, marks));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
        }
    }

    public void loadFromData(String csvData) {
        students.clear();
        String[] lines = csvData.split("\n");
        boolean firstLine = true;
        for (String line : lines) {
            if (firstLine) {
                firstLine = false;
                continue;
            }
            String[] values = line.split(",");
            if (values.length >= 2) {
                String name = values[0].trim();
                try {
                    double marks = Double.parseDouble(values[1].trim());
                    students.add(new Student(name, marks));
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public double calculateAverage() {
        if (students.isEmpty()) return 0;
        double sum = 0;
        for (Student s : students) {
            sum += s.getMarks();
        }
        return sum / students.size();
    }

    public Student getTopPerformer() {
        if (students.isEmpty()) return null;
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getMarks() > top.getMarks()) {
                top = s;
            }
        }
        return top;
    }

    public int getTotalStudents() {
        return students.size();
    }

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"totalStudents\":").append(getTotalStudents()).append(",");
        sb.append("\"averageMarks\":").append(String.format("%.2f", calculateAverage())).append(",");
        Student topper = getTopPerformer();
        sb.append("\"topper\":").append(topper != null ? topper.toString() : "null").append(",");
        sb.append("\"students\":[");
        for (int i = 0; i < students.size(); i++) {
            sb.append(students.get(i).toString());
            if (i < students.size() - 1) sb.append(",");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}
