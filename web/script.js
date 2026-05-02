let studentData = [];

document.addEventListener('DOMContentLoaded', () => {
    fetchData();

    document.getElementById('csvFileInput').addEventListener('change', handleFileUpload);
    document.getElementById('categoryFilter').addEventListener('change', renderTable);
    document.getElementById('minMarks').addEventListener('input', renderTable);
    document.getElementById('sortOrder').addEventListener('change', renderTable);
});

async function fetchData() {
    try {
        const response = await fetch('/api/data');
        const data = await response.json();
        updateDashboard(data);
        studentData = data.students;
        renderTable();
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function handleFileUpload(event) {
    const file = event.target.files[0];
    if (!file) return;

    document.getElementById('fileName').textContent = file.name;
    const reader = new FileReader();
    reader.onload = async (e) => {
        const content = e.target.result;
        try {
            const response = await fetch('/api/upload', {
                method: 'POST',
                body: content
            });
            if (response.ok) {
                fetchData();
            }
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    };
    reader.readAsText(file);
}

function updateDashboard(data) {
    document.getElementById('totalStudents').textContent = data.totalStudents;
    document.getElementById('averageMarks').textContent = data.averageMarks;
    document.getElementById('topPerformer').textContent = data.topper ? `${data.topper.name} (${data.topper.marks})` : 'N/A';
}

function renderTable() {
    const categoryFilter = document.getElementById('categoryFilter').value;
    const minMarks = parseFloat(document.getElementById('minMarks').value) || 0;
    const sortOrder = document.getElementById('sortOrder').value;

    let filteredData = [...studentData].filter(s => {
        const matchesCategory = categoryFilter === 'All' || s.category === categoryFilter;
        const matchesMarks = s.marks >= minMarks;
        return matchesCategory && matchesMarks;
    });

    if (sortOrder === 'asc') {
        filteredData.sort((a, b) => a.marks - b.marks);
    } else if (sortOrder === 'desc') {
        filteredData.sort((a, b) => b.marks - a.marks);
    }

    const tbody = document.getElementById('studentTableBody');
    tbody.innerHTML = '';

    filteredData.forEach(s => {
        const row = document.createElement('tr');
        const categoryClass = s.category.toLowerCase().split(' ').join('-');
        row.innerHTML = `
            <td>${s.name}</td>
            <td>${s.marks}</td>
            <td class="category-${categoryClass}">${s.category}</td>
        `;
        tbody.appendChild(row);
    });
}
