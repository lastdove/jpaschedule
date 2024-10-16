document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('register-form');
    const loginForm = document.getElementById('login-form');
    const scheduleForm = document.getElementById('schedule-form');
    const schedulesList = document.getElementById('schedules');
    const loginStatus = document.getElementById('login-status');
    const scheduleCreationSection = document.getElementById('schedule-creation');
    let loggedInUserId = null;

    // 회원가입 처리
    registerForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const username = document.getElementById('username').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/api/users', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, email, password}),
            });

            if (response.ok) {
                alert('회원가입 성공!');
            } else {
                const errorMessage = await response.text();
                console.error('Error:', errorMessage); // 에러 메시지 로그
                alert('회원가입 실패!');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    // 로그인 처리
    loginForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const username = document.getElementById('login-username').value;
        const password = document.getElementById('login-password').value; // 비밀번호 추가

        try {
            const response = await fetch(`/api/users/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password }) // 사용자명과 비밀번호 전송
            });
            if (response.ok) {
                const user = await response.json();
                loggedInUserId = user.id;
                loginStatus.textContent = `${user.username}님, 환영합니다!`;
                scheduleCreationSection.style.display = 'block';
            } else {
                loginStatus.textContent = '로그인 실패: 사용자명과 비밀번호를 확인하세요.';
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });


    // 일정 추가 처리
    scheduleForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        if (!loggedInUserId) {
            alert('로그인이 필요합니다.');
            return;
        }

        const title = document.getElementById('schedule-title').value;
        const content = document.getElementById('schedule-content').value;

        try {
            const response = await fetch(`/api/schedules/${loggedInUserId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ title, content }),
            });

            if (response.ok) {
                const schedule = await response.json();
                addScheduleToList(schedule);
                alert('일정 추가 성공!');
            } else {
                alert('일정 추가 실패!');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    // 일정 목록 로드
    async function loadSchedules() {
        try {
            const response = await fetch('/api/schedules');
            const schedules = await response.json();
            schedules.forEach(addScheduleToList);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    // 일정 목록에 추가
    function addScheduleToList(schedule) {
        const li = document.createElement('li');
        li.textContent = `${schedule.title} - ${schedule.content}`;
        schedulesList.appendChild(li);
    }

    loadSchedules();
});
