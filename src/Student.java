public class Student {
    private String name;
    private double marks;
    private String category;

    public Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
        this.category = calculateCategory(marks);
    }

    private String calculateCategory(double marks) {
        if (marks > 80) return "Excellent";
        if (marks >= 60) return "Good";
        return "Needs Improvement";
    }

    // Getters
    public String getName() { return name; }
    public double getMarks() { return marks; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("{\"name\":\"%s\", \"marks\":%.2f, \"category\":\"%s\"}", name, marks, category);
    }
}
