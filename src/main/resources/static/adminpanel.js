new Darkmode().showWidget();
document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('.section-header').forEach(header => {
        header.addEventListener('click', () => {
            const section = header.parentElement;
            section.classList.toggle('active');
        });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    flatpickr("#startTime", {
        enableTime: true,
        dateFormat: "Y-m-d\\TH:i",
        time_24hr: true
    });
});

// Load Data
window.onload = async () => {
    await Promise.all([
        loadTeachers(),
        loadStudents(),
        loadSubjects(),
        loadClassrooms(),
        loadActivities(),
        loadActivitiesGroups(),
        loadActivityDropdowns()
    ]);
};

// Groups
const groupForm = document.getElementById("add-group-form");
const groupsList = document.getElementById("groups-list");
const groupSubmitBtn = document.getElementById("group-submit-btn");
const groupCancelBtn = document.getElementById("group-cancel-btn");

// Load groups
async function loadActivitiesGroups() {
    const res = await fetch("/data/activitiesGroups");
    const groups = await res.json();

    groupsList.innerHTML = groups.map(g =>
        `<li>Type: ${g.type} — Group Number: ${g.groupNumber}
            <button class="edit-btn" data-id="${g.id}" data-type="${g.type}" data-groupnumber="${g.groupNumber}">Edit</button>
            <button class="delete-btn" data-id="${g.id}" data-type="activitiesGroups">Delete</button>
        </li>`
    ).join("");
}

function resetGroupForm() {
    groupForm.reset();
    groupForm.id.value = "";
    groupSubmitBtn.textContent = "Add Group";
    groupCancelBtn.style.display = "none";
}

// Submit handler for add/edit
groupForm.onsubmit = async e => {
    e.preventDefault();

    const formData = new FormData(groupForm);
    const id = formData.get("id");
    const data = {
        type: Number(formData.get("type")),
        groupNumber: Number(formData.get("groupNumber"))
    };

    if (id) {
        // Edit
        await fetch(`/data/activitiesGroups/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    } else {
        // Add
        await fetch("/data/activitiesGroups", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    }

    resetGroupForm();
    loadActivitiesGroups();

    // TO-DO reloadDropdown(type) instead of reloading all, for better performance
    loadActivityDropdowns();
};

groupCancelBtn.onclick = () => resetGroupForm();

// Click handlers on groups list for Edit/Delete buttons
groupsList.onclick = async e => {
    if (e.target.classList.contains("edit-btn")) {
        const groupNumber = e.target.dataset.groupnumber;
        const type = e.target.dataset.type;
        const res = await fetch("/data/activitiesGroups");
        const groups = await res.json();
        const group = groups.find(g => g.groupNumber == groupNumber && g.type == type);

        const ActivityTypeMap = {
            LESSON: 0,
            LECTURE: 1
        };

        if (group) {
            groupForm.querySelector('input[name="id"]').value = group.id;
            groupForm.querySelector('input[name="type"]').value = ActivityTypeMap[group.type];
            groupForm.querySelector('input[name="groupNumber"]').value = group.groupNumber;
            groupSubmitBtn.textContent = "Update Group";
            groupCancelBtn.style.display = "inline-block";
        } else {
            console.log("GROUP NOT FOUND " + type + " number: " + groupNumber)
        }
    }
};

// Teacher
const teacherForm = document.getElementById("add-teacher-form");
const teachersList = document.getElementById("teachers-list");
const teacherSubmitBtn = document.getElementById("teacher-submit-btn");
const teacherCancelBtn = document.getElementById("teacher-cancel-btn");

async function loadTeachers() {
    const res = await fetch("/data/teachers");
    const list = await res.json();

    teachersList.innerHTML = list.map(t =>
        `<li>${t.title || ''} ${t.name} (Age: ${calculateAge(t.birthDate)})
            <button class="edit-btn" data-id="${t.id}">Edit</button>
            <button class="delete-btn" data-id="${t.id}" data-type="teachers">Delete</button></li>`
    ).join("");
}

function calculateAge(birthDateStr) {
    if (!birthDateStr) return '?';

    const birthDate = new Date(birthDateStr);
    const today = new Date();

    let age = today.getFullYear() - birthDate.getFullYear();
    const m = today.getMonth() - birthDate.getMonth();

    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    return age;
}

function resetTeacherForm() {
    teacherForm.reset();
    teacherForm.id.value = "";
    teacherSubmitBtn.textContent = "Add Teacher";
    teacherCancelBtn.style.display = "none";
}

teacherForm.onsubmit = async e => {
    e.preventDefault();
    const formData = new FormData(teacherForm);
    const id = formData.get("id");
    const data = {
        name: formData.get("name"),
        title: formData.get("title"),
        birthDate: formData.get("birthDate") || null,
    };

    if (id) {
        // Edit
        await fetch(`/data/teachers/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    } else {
        // Add
        await fetch("/data/teachers", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    }

    resetTeacherForm();
    loadTeachers();

    // TO-DO reloadDropdown(type) instead of reloading all, for better performance
    loadActivityDropdowns();
};

teacherCancelBtn.onclick = () => resetTeacherForm();

teachersList.onclick = async e => {
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.dataset.id;
        // Fetch the single teacher data or from loaded list
        const res = await fetch("/data/teachers");
        const teachers = await res.json();
        const teacher = teachers.find(s => s.id == id);
        if (teacher) {
            teacherForm.id.value = teacher.id;
            teacherForm.name.value = teacher.name;
            teacherForm.title.value = teacher.title || "";
            teacherForm.birthDate.value = teacher.birthDate;
            teacherSubmitBtn.textContent = "Update Teacher";
            teacherCancelBtn.style.display = "inline-block";
        }
    }
};

// Student
const studentForm = document.getElementById("add-student-form");
const studentsList = document.getElementById("students-list");
const studentSubmitBtn = document.getElementById("student-submit-btn");
const studentCancelBtn = document.getElementById("student-cancel-btn");

async function loadStudents() {
    const res = await fetch("/data/students");
    const list = await res.json();
    const ul = document.getElementById("students-list");

    // Fetch all activity groups, we will be doing group id -> group number
    const resGroups = await fetch("/data/activitiesGroups");
    const groups = await resGroups.json();
    const groupMap = {};
    groups.forEach(g => {
        groupMap[g.id] = g.groupNumber;
    });

    ul.innerHTML = list.map(s =>
        `<li>${s.name} (Password: ${s.password}, Index: ${s.studentIndex}) — Lesson Group: ${groupMap[s.groupLessonId] ?? 'N/A'}, Lecture Group: ${groupMap[s.groupLectureId] ?? 'N/A'}
            <button class="edit-btn" data-id="${s.id}">Edit</button>
            <button class="delete-btn" data-id="${s.id}" data-type="students">Delete</button></li>`
    ).join("");
}

function resetStudentForm() {
    studentForm.reset();
    studentForm.id.value = "";
    studentSubmitBtn.textContent = "Add Student";
    studentCancelBtn.style.display = "none";
}

studentForm.onsubmit = async e => {
    e.preventDefault();
    const formData = new FormData(studentForm);
    const id = formData.get("id");
    const data = {
        name: formData.get("name"),
        studentIndex: formData.get("studentIndex"),
        password: formData.get("studentPassword"),
        groupLessonId: formData.get("groupLessonId") ? Number(formData.get("groupLessonId")) : null,
        groupLectureId: formData.get("groupLectureId") ? Number(formData.get("groupLectureId")) : null,
    };

    console.log("Data being sent:", data);

    if (id) {
        // Edit student
        await fetch(`/data/students/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    } else {
        // Add student
        await fetch("/data/students", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    }

    resetStudentForm();
    loadStudents();

    // TO-DO reloadDropdown(type) instead of reloading all, for better performance
    loadActivityDropdowns();
};

studentCancelBtn.onclick = () => resetStudentForm();

studentsList.onclick = async e => {
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.dataset.id;
        // Fetch the single student data or from loaded list
        const res = await fetch("/data/students");
        const students = await res.json();
        const student = students.find(s => s.id == id);
        if (student) {
            studentForm.id.value = student.id;
            studentForm.name.value = student.name;
            studentForm.studentIndex.value = student.studentIndex;
            studentForm.groupLessonId.value = student.groupLessonId || "";
            studentForm.groupLectureId.value = student.groupLectureId || "";
            studentForm.studentPassword.value = student.password || "";
            studentSubmitBtn.textContent = "Update Student";
            studentCancelBtn.style.display = "inline-block";
        }
    }
};

// Subject
const subjectForm = document.getElementById("add-subject-form");
const subjectsList = document.getElementById("subjects-list");
const subjectSubmitBtn = document.getElementById("subject-submit-btn");
const subjectCancelBtn = document.getElementById("subject-cancel-btn");

async function loadSubjects() {
    const res = await fetch("/data/subjects");
    const list = await res.json();
    subjectsList.innerHTML = list.map(s =>
        `<li>
            ${s.name} (${s.codeName}), ETCS: ${s.etcs}
            <button class="edit-btn" data-id="${s.id}">Edit</button>
            <button class="delete-btn" data-id="${s.id}" data-type="subjects">Delete</button>
            </li>`
    ).join("");
}

function resetSubjectForm() {
    subjectForm.reset();
    subjectForm.dataset.id = "";
    subjectSubmitBtn.textContent = "Add Subject";
    subjectCancelBtn.style.display = "none";
}

subjectCancelBtn.onclick = () => resetSubjectForm();

subjectForm.onsubmit = async e => {
    e.preventDefault();
    const formData = new FormData(subjectForm);
    const id = subjectForm.dataset.id;
    const data = {
        name: formData.get("name"),
        codeName: formData.get("codeName"),
        etcs: formData.get("etcs") ? Number(formData.get("etcs")) : 0
    };

    if (id) {
        // Edit
        await fetch(`/data/subjects/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    } else {
        // Add
        await fetch("/data/subjects", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    }

    resetSubjectForm();
    loadSubjects();

    // TO-DO reloadDropdown(type) instead of reloading all, for better performance
    loadActivityDropdowns();
};

subjectsList.onclick = async e => {
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.dataset.id;
        const res = await fetch("/data/subjects");
        const subjects = await res.json();
        const subject = subjects.find(s => s.id == id);
        if (subject) {
            subjectForm.name.value = subject.name;
            subjectForm.codeName.value = subject.codeName;
            subjectForm.etcs.value = subject.etcs || "";
            subjectForm.dataset.id = id;
            subjectSubmitBtn.textContent = "Update Subject";
            subjectCancelBtn.style.display = "inline-block";
        }
    }
};

// Classroom
const classroomForm = document.getElementById("add-classroom-form");
const classroomsList = document.getElementById("classrooms-list");
const classroomSubmitBtn = document.getElementById("classroom-submit-btn");
const classroomCancelBtn = document.getElementById("classroom-cancel-btn");

async function loadClassrooms() {
    const res = await fetch("/data/classrooms");
    const list = await res.json();
    classroomsList.innerHTML = list.map(c =>
        `<li>${c.address}
        <button class="edit-btn" data-id="${c.id}">Edit</button>
        <button class="delete-btn" data-id="${c.id}" data-type="classrooms">Delete</button></li>`
    ).join("");
}

function resetClassroomForm() {
    classroomForm.reset();
    classroomForm.dataset.id = "";
    classroomSubmitBtn.textContent = "Add Classroom";
    classroomCancelBtn.style.display = "none";
}

classroomCancelBtn.onclick = () => resetClassroomForm();

classroomForm.onsubmit = async e => {
    e.preventDefault();
    const formData = new FormData(classroomForm);
    const id = classroomForm.dataset.id;
    const data = {
        address: formData.get("address"),
    };

    if (id) {
        // Edit
        await fetch(`/data/classrooms/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    } else {
        // Add
        await fetch("/data/classrooms", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    }

    resetClassroomForm();
    loadClassrooms();

    // TO-DO reloadDropdown(type) instead of reloading all, for better performance
    loadActivityDropdowns();
};

classroomsList.onclick = async e => {
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.dataset.id;
        const res = await fetch("/data/classrooms");
        const classrooms = await res.json();
        const classroom = classrooms.find(c => c.id == id);
        if (classroom) {
            classroomForm.address.value = classroom.address;
            classroomForm.dataset.id = id;
            classroomSubmitBtn.textContent = "Update Classroom";
            classroomCancelBtn.style.display = "inline-block";
        }
    }
};

// Activities
const activityForm = document.getElementById("add-activity-form");
const activitySubmitBtn = document.getElementById("activity-submit-btn");
const activityCancelBtn = document.getElementById("activity-cancel-btn");

function resetActivityForm() {
    activityForm.reset();
    activityForm.id.value = "";
    activitySubmitBtn.textContent = "Add Activity";
    activityCancelBtn.style.display = "none";
}

activityCancelBtn.onclick = () => resetActivityForm();

document.getElementById("activities-list").onclick = async e => {
    if (e.target.classList.contains("edit-btn")) {
        const id = e.target.dataset.id;
        const activity = allActivities.find(a => a.id == id);

        if (activity) {
            //console.log(activity.subject.id + " data classroom " + activity.duration)
            activityForm.id.value = id;
            activityForm.querySelector('select[name="subject_id"]').value = activity.subject.id || "";
            activityForm.querySelector('select[name="teacher_id"]').value = activity.teacher.id || "";
            activityForm.querySelector('select[name="classroom_id"]').value = activity.classroom.id || "";
            document.getElementById("startTime").value = activity.startTime;
            activityForm.querySelector('input[name="duration"]').value = activity.duration || "";
            activityForm.querySelector('select[name="groupIdSelector"]').value = activity.groupId || "";

            activityForm.subject_id.value = activity.subject.id;
            activityForm.teacher_id.value = activity.teacher.id;
            activityForm.classroom_id.value = activity.classroom.id;

            activitySubmitBtn.textContent = "Update Activity";
            activityCancelBtn.style.display = "inline-block";
        }
    }
};

activityForm.onsubmit = async e => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const id = formData.get("id");
    const data = {
        subjectId: formData.get("subject_id") ? Number(formData.get("subject_id")) : null,
        teacherId: formData.get("teacher_id") ? Number(formData.get("teacher_id")) : null,
        classroomId: formData.get("classroom_id") ? Number(formData.get("classroom_id")) : null,
        startTime: formData.get("startTime"),
        duration: formData.get("duration") ? Number(formData.get("duration")) : null,
        groupId: formData.get("groupIdSelector") ? Number(formData.get("groupIdSelector")) : null,
    };

    // DEBUG print to see the content sent
    //console.log("Data sent ", data)

    if (id) {
        // Edit
        await fetch(`/data/activities/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    } else {
        // Add
        await fetch("/data/activities", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });
    }

    e.target.reset();

    resetActivityForm();
    loadActivities();

    // TO-DO reloadDropdown(type) instead of reloading all, for better performance
    loadActivityDropdowns();
};

let allActivities = [];
let sortField = 'startTime';
let sortAsc = true;

async function loadActivities() {
    const res = await fetch("/data/activities");
    allActivities = await res.json();
    renderActivities(allActivities);
}

function applyFilters() {
    const date = document.getElementById('filter-date').value;
    const code = document.getElementById('filter-code').value.toLowerCase();
    let filtered = [...allActivities];

    if (date) {
        filtered = filtered.filter(a => a.startTime.startsWith(date));
    }

    if (code) {
        filtered = filtered.filter(a => (a.subject?.codeName || '').toLowerCase().includes(code));
    }

    renderActivities(filtered);
}

function clearFilters() {
    document.getElementById('filter-date').value = '';
    document.getElementById('filter-code').value = '';
    renderActivities(allActivities);
}

function sortActivities(field) {
    sortField = field;
    renderActivities(allActivities);
}

function toggleSortDirection() {
    sortAsc = !sortAsc;
    renderActivities(allActivities);
}

function resolveField(obj, path) {
    return path.split('.').reduce((o, key) => (o ? o[key] : ''), obj) || '';
}

function renderActivities(list) {
    const listEl = document.getElementById("activities-list");
    listEl.innerHTML = '';

    const sorted = [...list].sort((a, b) => {
        const valA = resolveField(a, sortField);
        const valB = resolveField(b, sortField);
        if (valA < valB) return sortAsc ? -1 : 1;
        if (valA > valB) return sortAsc ? 1 : -1;
        return 0;
    });

    sorted.forEach(a => {
        const li = document.createElement('li');
        li.innerHTML = `
          <strong>${a.subject?.name || 'No subject'}</strong>
          (${a.subject?.codeName || '-'}) — ${new Date(a.startTime).toLocaleString()}
          <br>
          <small>${a.type || ''} with ${a.teacher?.name || 'N/A'} in ${a.classroom?.address || 'N/A'} (${a.duration || 0} mins)</small>
          <button class="edit-btn" data-id="${a.id}">Edit</button>
          <button class="delete-btn" data-id="${a.id}" data-type="activities">Delete</button>
        `;
        listEl.appendChild(li);
    });
}

async function loadActivityDropdowns() {
    const [teachersRes, subjectsRes, classroomsRes, activityTypesRes, activitiesGroupsRes] = await Promise.all([
        fetch('/data/teachers'),
        fetch('/data/subjects'),
        fetch('/data/classrooms'),
        fetch('/api/enums/activity-types'),
        fetch('/data/activitiesGroups')
    ]);
    const teachers = await teachersRes.json();
    const subjects = await subjectsRes.json();
    const classrooms = await classroomsRes.json();
    const activityTypes = await activityTypesRes.json();
    const activitiesGroups = await activitiesGroupsRes.json();

    // Populate subjects dropdown
    const subjectSelect = document.querySelector('select[name="subject_id"]');
    subjectSelect.innerHTML = '<option value="">Select Subject</option>' + subjects.map(s =>
        `<option value="${s.id}">${s.name} (${s.codeName})</option>`
    ).join('');

    // Populate teachers dropdown
    const teacherSelect = document.querySelector('select[name="teacher_id"]');
    teacherSelect.innerHTML = `<option value="">Select Teacher</option>` + teachers.map(t =>
        `<option value="${t.id}">${t.title ? t.title + ' ' : ''}${t.name}</option>`
    ).join('');

    // Populate classrooms dropdown
    const classroomSelect = document.querySelector('select[name="classroom_id"]');
    classroomSelect.innerHTML = '<option value="">Select Classroom</option>' + classrooms.map(c =>
        `<option value="${c.id}">${c.address}</option>`
    ).join('');

    // Populate groupLessonId and groupLectureId dropdowns
    const groupLessonSelect = document.getElementById('group-lesson-select');
    const groupLectureSelect = document.getElementById('group-lecture-select');
    const groupIdSelector = document.getElementById('group-id-selector');

    groupLessonSelect.innerHTML = '<option value="">Select Lesson Group</option>';
    groupLectureSelect.innerHTML = '<option value="">Select Lecture Group</option>';
    groupIdSelector.innerHTML = '<option value="">Select Group</option>';

    activitiesGroups.forEach(group => {
        let typeWord = "N/A";

        const option = `<option value="${group.id}">Group ${group.groupNumber}</option>`;
        if (group.type === "LESSON") {
            typeWord = "LESSON"
            groupLessonSelect.innerHTML += option;
        } else if (group.type === "LECTURE") {
            typeWord = "LECTURE"
            groupLectureSelect.innerHTML += option;
        }

        const generalOption = `<option value="${group.id}">Group ${typeWord} ${group.groupNumber}</option>`;
        groupIdSelector.innerHTML += generalOption;
    });
}

// Delete handler
document.addEventListener("click", async e => {
    if (e.target.classList.contains("delete-btn")) {
        const id = e.target.dataset.id;
        const type = e.target.dataset.type;
        await fetch(`/data/${type}/${id}`, { method: "DELETE" });
        if (type === "teachers") loadTeachers();
        else if (type === "subjects") loadSubjects();
        else if (type === "classrooms") loadClassrooms();
        else if (type === "activities") loadActivities();
        else if (type === "students") loadStudents();
        else if (type === "activitiesGroups") loadActivitiesGroups();

        // TO-DO reloadDropdown(type) instead of reloading all, for better performance
        loadActivityDropdowns();
    }
});