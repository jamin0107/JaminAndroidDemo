# -*- coding:utf-8 -*-

import os
import subprocess
import shutil

# CORE_RES_PATH = ""
APP_RES_PATH = "../../app/src/main/res/"
BASE_RES_PATH = "../../framework/src/main/res/"

src_dir_base = APP_RES_PATH
dst_dir_base = BASE_RES_PATH

move_file_list = "move_list.txt"

# find_dir = []
find_drawable_dir = ["drawable/", "drawable-hdpi/", "drawable-xhdpi/", "drawable-xxhdpi/",
                     "drawable-xxxhdpi/", "drawable-ldpi/", "drawable-mdpi/"]

find_mipmap_dir = ["mipmap/", "mipmap-hdpi/", "mipmap-xhdpi/", "mipmap-xxhdpi/", "mipmap-ldpi/",
                   "mipmap-mdpi/"]

suffixs = [".9.png", ".png", ".jpg", ".xml"]


# 使用git命令移动
def git_mv(src, dst):
    subprocess.call(['git', 'mv', src, dst])


# main
def move_res():
    f = open("move_list.txt")
    for res in f:
        # drawable的key名字
        if str(res).startswith('@drawable/'):
            res_name = res[len('@drawable/'):].strip('\n')
            for drawable_dir in find_drawable_dir:
                move_image(drawable_dir, res_name)
        elif str(res).startswith('@mipmap/'):
            res_name = res[len('@mipmap/'):].strip('\n')
            for mipmap_dir in find_mipmap_dir:
                move_image(mipmap_dir, res_name)


# 寻找图片,并移动
def move_image(drawable_dir, res_name):
    src_file_path = src_dir_base + drawable_dir
    dst_file_path = dst_dir_base + drawable_dir
    for suffix in suffixs:
        # 根据扩展名.在对应资源目录下面寻找资源文件
        src_file = src_file_path + res_name + suffix
        if os.path.exists(src_file):
            # 如果文件存在则移动文件
            dst_file = dst_file_path + res_name + suffix
            print src_file + "--->" + dst_file
            # 目录不存在则创建目录
            if not os.path.exists(dst_file_path):
                os.makedirs(dst_file_path)
                
            # 移动文件
            # shutil.move(src_file, dst_file)
            # 执行 git mv
            git_mv(src_file, dst_file)


move_res()
