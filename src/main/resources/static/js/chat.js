// Set initial message timestamp
document.getElementById('initTimestamp').textContent = formatTime(new Date());

// Allow Enter key to send message
document.getElementById('userInput').addEventListener('keydown', function (e) {
    if (e.key === 'Enter') sendMessage();
});

/**
 * Sends the user's message to the /api/chat endpoint and displays the response.
 */
async function sendMessage() {
    const input = document.getElementById('userInput');
    const text = input.value.trim();
    if (!text) return;

    appendMessage('user', text);
    input.value = '';

    try {
        const response = await fetch('/api/chat', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ message: text })
        });

        if (!response.ok) throw new Error('Server error: ' + response.status);

        const data = await response.json();
        appendMessage('bot', data.content);
    } catch (err) {
        appendMessage('bot', 'Sorry, something went wrong. Please try again.');
        console.error(err);
    }
}

/**
 * Appends a message bubble to the chat window.
 * @param {string} sender - 'user' or 'bot'
 * @param {string} text   - message content
 */
function appendMessage(sender, text) {
    const container = document.getElementById('chatMessages');

    const wrapper = document.createElement('div');
    wrapper.className = 'message ' + sender;

    const bubble = document.createElement('span');
    bubble.className = 'bubble';
    bubble.textContent = text;

    const ts = document.createElement('span');
    ts.className = 'timestamp';
    ts.textContent = formatTime(new Date());

    wrapper.appendChild(bubble);
    wrapper.appendChild(ts);
    container.appendChild(wrapper);

    // Auto-scroll to latest message
    container.scrollTop = container.scrollHeight;
}

/**
 * Formats a Date object as HH:MM.
 * @param {Date} date
 * @returns {string}
 */
function formatTime(date) {
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}
