"""
@FileName：enter.py
@Description：

@Author：HeYiQing
@Time：2024/8/4 0:18
"""
from func import download_blob_video

url = "https://kk20002.vip/vod/player.html?cate_id=223&id=539658&type_id=185"
# video_selector = "#J_prismPlayer video"
video_selector = "body .homepage .main .content .module .module-main .player-box .player-box-main .MacPlayer #J_prismPlayer video"
filename = r"res/我想吃掉你的胰脏.mp4"
if __name__ == '__main__':
    download_blob_video(url, video_selector, filename)
    print("Video download completed.")