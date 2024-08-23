"""
@FileName：xhs_note_release.py
@Description：

@Author：HeYiQing
@Time：2024/8/15 20:18
"""
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.edge.service import Service
from selenium.webdriver.edge.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

import time
import sys


def main(title, image):
    # 参数设置
    # title = sys.argv[1]
    # image = sys.argv[2]
    baseurl = 'https://creator.xiaohongshu.com/login?selfLogout=true'
    wait_time = 10
    wait_time_login = 240

    # 发布笔记按钮
    release_note_button = '#content-area > main > div.menu-container.menu-panel > div > div.publish-video > a'
    # 上传图文selector
    upload_image_button_selector = '#web > div > div > div > div.header > div:nth-child(2)'
    # 上传图片selector
    input_image = '#web > div > div > div > div.upload-content > div.upload-wrapper > div > input'
    # 填写标题
    # input_title = '//*[@id="el-id-6878-5"]'
    # 上传按钮
    release_button = '#web > div > div > div > div > div > div.content > div.submit > div > button.el-button.publishBtn > span'

    options = webdriver.EdgeOptions()
    # 指定msedgedriver的路径
    service = Service('msedgedriver.exe')
    # 初始化WebDriver
    we = webdriver.Edge(service=service, options=options)

    # 设置隐式等待时间和访问网站
    we.implicitly_wait(wait_time)
    we.get(baseurl)
    wait = WebDriverWait(we, wait_time_login)
    wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, release_note_button))).click()
    # we.find_element(By.CSS_SELECTOR, release_note_button).click()
    # 上传图文按钮
    we.find_element(By.CSS_SELECTOR, upload_image_button_selector).click()
    we.find_element(By.CSS_SELECTOR, input_image).send_keys(image)
    # 等待图片上传成功
    time.sleep(6)
    # 填写标题
    # we.find_element(By.XPATH, input_title).send_keys(title)
    # time.sleep(1.5)
    # 点击发布按钮
    we.find_element(By.CSS_SELECTOR, release_button).click()
    time.sleep(4)
    # 退出
    we.quit()


if __name__ == '__main__':
    main('test', 'E:\\1.jpg')
