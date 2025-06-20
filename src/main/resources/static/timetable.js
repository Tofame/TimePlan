new Darkmode().showWidget();

const hours = Array.from({ length: 11 }, (_, i) => 8 + i); // 08:00–18:00

// Start with current week (Monday)
let currentWeekMonday = getMonday(new Date());

function getMonday(d) {
    const date = new Date(d);
    const day = date.getDay();
    const diff = (day === 0 ? -6 : 1) - day; // adjust if Sunday (0) to Monday
    date.setDate(date.getDate() + diff);
    date.setHours(0, 0, 0, 0);
    return date;
}

function formatDate(date) {
    return date.toISOString().slice(0, 10);
}

function updateWeekLabel() {
    const mondayStr = formatDate(currentWeekMonday);
    const friday = new Date(currentWeekMonday);
    friday.setDate(friday.getDate() + 4);
    const fridayStr = formatDate(friday);
    document.getElementById('currentWeekLabel').textContent = `${mondayStr} - ${fridayStr}`;

    updateWeekDaysHeader();
}

function createEmptyGrid() {
    const tbody = document.getElementById("timetable-body");
    tbody.innerHTML = ""; // Clear previous grid

    hours.forEach(hour => {
        const tr = document.createElement("tr");
        const timeCell = document.createElement("td");
        timeCell.textContent = `${hour}:00`;
        tr.appendChild(timeCell);

        for (let i = 0; i < 5; i++) {
            const td = document.createElement("td");
            td.dataset.day = i;
            td.dataset.hour = hour;
            tr.appendChild(td);
        }

        tbody.appendChild(tr);
    });
}

async function loadActivities() {
    const token = localStorage.getItem("jwtToken");
    let studentIndex = null;

    if (token) {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            studentIndex = payload.studentIndex;
        } catch (e) {
            console.error("Invalid JWT Token");
            return;
        }
    }

    if (!studentIndex) {
        console.error("Student index not found.");
        return;
    }

    try {
        const groupRes = await fetch(`/data/students/${studentIndex}/groups`);
        if (!groupRes.ok) {
            console.error("Failed to fetch student groups");
            return;
        }

        const groups = await groupRes.json();
        const { groupLectureId, groupLessonId } = groups;

        const [lectureRes, lessonRes] = await Promise.all([
            fetch(`/data/activities/lectures/${groupLectureId}`),
            fetch(`/data/activities/lessons/${groupLessonId}`)
        ]);

        if (!lectureRes.ok || !lessonRes.ok) {
            console.error("Failed to fetch activities");
            return;
        }

        const lectures = await lectureRes.json();
        const lessons = await lessonRes.json();
        const activities = [...lectures, ...lessons];

        // Clear previous activities from grid
        document.querySelectorAll('.activity').forEach(el => el.remove());

        for (const activity of activities) {
            const start = new Date(activity.startTime);

            if (!isInCurrentWeek(start)) continue;

            const day = start.getDay(); // 1 (Mon) to 5 (Fri)
            const hour = start.getHours();

            if (day < 1 || day > 5) continue;

            const selector = `td[data-day="${day - 1}"][data-hour="${hour}"]`;
            const cell = document.querySelector(selector);

            if (cell) {
                const div = document.createElement("div");
                div.className = "activity";
                div.dataset.activityId = activity.id;
                div.dataset.groupId = activity.groupId;

                doEasterEgg(activity, div);

                div.innerHTML = `
                <strong>${activity.subject?.name || 'N/A'}</strong><br>
                ${activity.teacher?.name || ''}<br>
                Room: ${activity.classroom?.address || 'N/A'}
                <div class="tooltip">
                    Teacher: ${activity.teacher?.title || ''} ${activity.teacher?.name || ''} (Age: ${calculateAge(activity.teacher?.birthDate) || 'N/A'})<br>
                    Room: ${activity.classroom?.address || 'N/A'}<br>
                    Subject Code: ${activity.subject?.codeName || 'N/A'}<br>
                    ETCS: ${activity.subject?.etcs || 'N/A'}<br>
                    Students: <span class="student-count">Loading...</span><br>
                    Type: <span class="activity-type-element">Loading...</span><br>
                    Group: <span class="activity-group-element">Loading...</span><br>
                    Start: ${start.toLocaleString()}<br>
                    End: ${calculateEndTime(activity.startTime, activity.duration || 0).toLocaleTimeString()}<br>
                    Duration: ${formatDuration(activity.duration || 0)}
                </div>
            `;
                cell.appendChild(div);
            }
        }
    } catch (error) {
        console.error("Error loading activities:", error);
    }
}

function isInCurrentWeek(date) {
    const monday = currentWeekMonday;
    const friday = new Date(monday);
    friday.setDate(friday.getDate() + 4);

    // Compare only date part ignoring time
    const d = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    return d >= monday && d <= friday;
}

function updateWeekDaysHeader() {
    const dayNames = ['Pon.', 'Wt.', 'Śr.', 'Czw.', 'Pt.'];
    const thead = document.querySelector('#timetable thead tr');

    for(let i = 0; i < 5; i++) {
        const th = thead.children[i + 1];
        const date = new Date(currentWeekMonday);
        date.setDate(date.getDate() + i);
        th.innerHTML = `${dayNames[i]}<br>${date.getDate()}`;
    }
}

function formatDuration(minutes) {
    const h = Math.floor(minutes / 60);
    const m = minutes % 60;
    let result = '';
    if (h > 0) {
        result += `${h}h `;
    }
    if (m > 0 || h === 0) {
        result += `${m} min`;
    }
    return result.trim();
}

function calculateEndTime(startTime, durationMinutes) {
    const startDate = new Date(startTime);
    const endDate = new Date(startDate.getTime() + durationMinutes * 60000); // 60000 ms = 1 minute
    return endDate;
}

function calculateAge(birthDateStr) {
    if (!birthDateStr) return 'N/A';

    const birthDate = new Date(birthDateStr);
    const today = new Date();

    let age = today.getFullYear() - birthDate.getFullYear();
    const m = today.getMonth() - birthDate.getMonth();

    // If birth month/day hasn't occurred yet this year, subtract 1
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    return age;
}

function doEasterEgg(activity, div) {
    const startDate = new Date(activity.startTime);
    // Warning, they count months from 0, so may is actually 4th
    const isTheDate = startDate.getMonth() === 4 && startDate.getDate() === 27;
    const isTPO = activity.subject?.codeName === "TPO";

    if (isTheDate && isTPO) {
        div.classList.add("rainbow");
    }
}

// Handlers for buttons
document.getElementById('prevWeek').addEventListener('click', () => {
    currentWeekMonday.setDate(currentWeekMonday.getDate() - 7);
    updateWeekLabel();
    createEmptyGrid();
    loadActivities();
});

document.getElementById('nextWeek').addEventListener('click', () => {
    currentWeekMonday.setDate(currentWeekMonday.getDate() + 7);
    updateWeekLabel();
    createEmptyGrid();
    loadActivities();
});

window.onload = () => {
    updateWeekLabel();
    createEmptyGrid();
    loadActivities();
};

// Handle mouseover fetches with caching
document.addEventListener('mouseover', async (e) => {
    const activityEl = e.target.closest('.activity');
    if (
        activityEl &&
        activityEl.dataset.activityId &&
        !activityEl.dataset.fetching &&
        !activityEl.dataset.fetched
    ) {
        const span = activityEl.querySelector('.student-count');
        const activityTypeSpan = activityEl.querySelector('.activity-type-element');
        const activityGroupSpan = activityEl.querySelector('.activity-group-element');
        activityEl.dataset.fetching = "true"; // Prevent duplicates

        try {
            const groupInfoRes = await fetch(`/data/activitiesGroups/${activityEl.dataset.groupId}`)
            const groupInfo = await groupInfoRes.json();
            activityTypeSpan.textContent = groupInfo.type || 'N/A';
            activityGroupSpan.textContent = groupInfo.groupNumber || 'N/A';

            const res = await fetch(`/data/activities/${activityEl.dataset.activityId}/studentCount`);
            const count = await res.json();
            span.textContent = count || 'N/A';
            activityEl.dataset.fetched = "true"; // Mark as fetched
        } catch (err) {
            span.textContent = 'Error';
        } finally {
            delete activityEl.dataset.fetching; // Clean up either way
        }
    }
});