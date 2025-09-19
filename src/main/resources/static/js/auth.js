(async function validateToken() {
    const token = localStorage.getItem('token');
    if (!token) {
        // 如果没有 token，跳转到登录页面
        console.warn('No token found. Redirecting to login...');
        window.location.href = '/index.html';
        return;
    }

    try {
        const response = await fetch(`/auth/validate?token=${token}`);
        if (!response.ok) {
            // 如果验证失败，跳转到登录页面
            console.warn('Invalid token. Redirecting to login...');
            window.location.href = '/index.html';
        } else {
            console.log('Token is valid.');
        }
    } catch (error) {
        // 处理网络错误或其他异常
        console.error('Error during token validation:', error);
        window.location.href = '/index.html';
    }
})();