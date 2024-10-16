const scheduleForm = document.getElementById('schedule-form');
const editScheduleForm = document.getElementById('edit-schedule-form');
const commentForm = document.getElementById('comment-form');

const schedulesList = document.getElementById('schedules');

// 일정 추가
scheduleForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const title = document.getElementById('schedule-title').value;
    const content = document.getElementById('schedule-content').value;

    const response = await fetch('/api/schedules/1', { // 예시로 userId 1
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, content })
    });

    if (response.ok) {
        await loadSchedules();
        scheduleForm.reset();
    }
});

// 일정 목록 로드
async function loadSchedules() {
    const response = await fetch('/api/schedules');
    const schedules = await response.json();
    schedulesList.innerHTML = '';

    for (const schedule of schedules) {
        const li = document.createElement('li');
        li.innerHTML = `
            <strong>${schedule.title}</strong>
            <p>${schedule.content}</p>
            <button onclick="editSchedule(${schedule.id}, '${schedule.title}', '${schedule.content}')">수정</button>
            <button onclick="deleteSchedule(${schedule.id})">삭제</button>
            <button onclick="loadComments(${schedule.id})">댓글 보기</button>
            <div id="comments-container-${schedule.id}" class="comments-container" style="display: none;">
                <h4>댓글</h4>
                <ul id="comments-${schedule.id}"></ul>
            </div>
        `;
        schedulesList.appendChild(li);
    }
}

// 일정 수정
function editSchedule(id, title, content) {
    document.getElementById('edit-schedule-id').value = id;
    document.getElementById('edit-schedule-title').value = title;
    document.getElementById('edit-schedule-content').value = content;
    document.getElementById('schedule-edit').style.display = 'block';
}

// 수정한 일정 저장
editScheduleForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = document.getElementById('edit-schedule-id').value;
    const title = document.getElementById('edit-schedule-title').value;
    const content = document.getElementById('edit-schedule-content').value;

    await fetch(`/api/schedules/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, content })
    });

    await loadSchedules();
    editScheduleForm.reset();
    document.getElementById('schedule-edit').style.display = 'none';
});

// 일정 삭제
async function deleteSchedule(id) {
    await fetch(`/api/schedules/${id}`, { method: 'DELETE' });
    await loadSchedules();
}

// 댓글 추가
commentForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const scheduleId = document.getElementById('comment-schedule-id').value;
    const content = document.getElementById('comment-content').value;

    await fetch(`/api/comments/${scheduleId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content })
    });

    await loadComments(scheduleId);
});

// 댓글 로드
async function loadComments(scheduleId) {
    const response = await fetch(`/api/comments/${scheduleId}`);
    const comments = await response.json();

    const commentsList = document.getElementById(`comments-${scheduleId}`);
    commentsList.innerHTML = '';

    comments.forEach(comment => {
        const li = document.createElement('li');
        li.textContent = comment.content;
        commentsList.appendChild(li);
    });

    const commentsContainer = document.getElementById(`comments-container-${scheduleId}`);
    commentsContainer.style.display = 'block';
    document.getElementById('comment-schedule-id').value = scheduleId;
    document.getElementById('comment-section').style.display = 'block';
}

// 초기 로드
loadSchedules();
