"""
@FileName：movie.py
@Description：

@Author：HeYiQing
@Time：2024/8/3 22:29
"""
import requests
from selenium import webdriver
from selenium.webdriver.common.by import By
import base64

from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


def download_blob_video(url, video_selector, filename):
    options = webdriver.ChromeOptions()
    options.add_argument('--no-sandbox')
    driver = webdriver.Chrome(options=options)

    driver.get(url)
    video_element = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.CSS_SELECTOR, video_selector))
    )
    # 正确获取blob URL
    blob_url = video_element.get_attribute('src')

    download_blob_video_by_request(blob_url[5:], 'request.mp4')

    print('blob_url:', blob_url)

    # 使用Fetch API读取blob数据
    blob_data = driver.execute_async_script("""
        var uri = arguments;
        var callback = arguments[1];

        fetch(uri)
            .then(function(response) {
                return response.blob();
            })
            .then(function(blob) {
                var reader = new FileReader();
                reader.onloadend = function() {
                    var base64data = reader.result.split(',')[1];
                    // 通过callback返回base64编码的数据
                    callback(base64data);
                };
                reader.readAsDataURL(blob);
            });
        """, blob_url)
    # 将base64编码的字符串转换为字节数据，并保存到文件
    binary_data = base64.b64decode(blob_data)
    with open(filename, 'wb') as file:
        file.write(binary_data)

    driver.quit()


def download_blob_video_by_request(blob_url, filename):
    print(blob_url)
    # 使用requests库来下载blob URL指向的资源
    response = requests.get(blob_url, stream=True)

    # 检查请求是否成功
    if response.status_code == 200:
        # 将视频流写入文件
        with open(filename, 'wb') as file:
            for chunk in response.iter_content(chunk_size=1024):
                if chunk:
                    file.write(chunk)
    else:
        print("请求失败，状态码：", response.status_code)
