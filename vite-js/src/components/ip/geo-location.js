// 使用 ip-api 获取用户的 IP 地址和位置信息
function getGeoLocation() {
  fetch('http://ip-api.com/json')
    .then(response => response.json())
    .then(data => {
      if (data.status === 'fail') {
        alert('无法获取位置信息');
      } else {
        const newIp = data.query;
        const newLocation = `${data.city}, ${data.regionName}, ${data.country}`;

        // 更新 Vue 组件中的响应式数据
        if (window.updateGeoLocation) {
          window.updateGeoLocation(newIp, newLocation);
        }
      }
    })
    .catch(error => {
      console.error('获取位置时出错:', error);
      alert('无法获取位置信息');
    });
}

// 页面加载时调用函数获取地理位置
window.onload = getGeoLocation;
