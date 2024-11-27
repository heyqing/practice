// 获取当前日期时间作为用户访问时间
function getCurrentTime() {
  const now = new Date();
  return now.toLocaleString();
}

// 获取或设置 Cookies
function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

// 设置 Cookies
function setCookie(name, value, days) {
  const d = new Date();
  d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
  const expires = `expires=${d.toUTCString()}`;
  document.cookie = `${name}=${value}; ${expires}; path=/`;
}

// 设置会话存储
function setSessionStorage(key, value) {
  sessionStorage.setItem(key, value);
}

// 页面加载时追踪用户信息
window.onload = function () {
  let visitCount = parseInt(getCookie('visitCount')) || 0;
  visitCount += 1;
  setCookie('visitCount', visitCount, 7);  // Cookie保存7天

  // 设置当前访问时间到会话存储
  const visitTime = getCurrentTime();
  setSessionStorage('lastVisitTime', visitTime);

  // 显示用户访问信息
  const visitorInfo = document.getElementById('visitor-info');
  visitorInfo.innerHTML = `
    <p>You have visited this site ${visitCount} time(s).</p>
    <p>Your last visit was at: ${sessionStorage.getItem('lastVisitTime')}</p>
  `;
};
