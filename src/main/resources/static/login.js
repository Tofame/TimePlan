document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const index = document.getElementById('index').value.trim();
    const password = document.getElementById('password').value.trim();
    const message = document.getElementById('message');

    message.style.display = 'none';
    message.textContent = '';

    if (!index || !password) {
        message.style.color = 'red';
        message.textContent = 'Both index and password are required.';
        message.style.display = 'block';
        return;
    }

    try {
        const response = await fetch('/data/students/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ studentIndex: index, password: password })
        });

        if (response.ok) {
            const data = await response.json();
            message.style.color = 'green';
            message.textContent = 'Login successful!';
            message.style.display = 'block';
            localStorage.setItem('jwtToken', data.token);
            window.location.href = '/timetable.html';
        } else if (response.status === 401) {
            message.style.color = 'red';
            message.textContent = 'Invalid index or password.';
            message.style.display = 'block';
        } else {
            message.style.color = 'red';
            message.textContent = 'Login failed. Please try again later.';
            message.style.display = 'block';
        }
    } catch (error) {
        message.style.color = 'red';
        message.textContent = 'Error connecting to server.';
        message.style.display = 'block';
        console.error('Login error:', error);
    }
});