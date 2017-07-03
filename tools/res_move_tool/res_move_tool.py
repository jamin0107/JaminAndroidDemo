# -*- coding:utf-8 -*-

import os
import subprocess
import shutil
import sys

#  使用说明
#  半自动.
#   1.先设置src,dst的res路径(APP_RES_PATH , BASE_RES_PATH )
#   2.支持两种命令模式:
#   将图片复制一份到指定目录 python res_move_tool.py cp
#   将图片移动到指定目录 python res_move_tool.py mv
#   3.TODO:移动XML Shape或者Selector里对应的 drawable
#   4.TODO:移动Color,String(多语言)


# CORE_RES_PATH = ""
APP_RES_PATH = "../../app/src/main/res/"
BASE_RES_PATH = "../../framework/src/main/res/"
GIT_MODE = True  # 使用git command移动,确保git可以追踪到移动的文件

src_dir_base = APP_RES_PATH
dst_dir_base = BASE_RES_PATH

move_file_list = "move_list.txt"

# find_dir = []
find_drawable_dir = ["drawable/", "drawable-hdpi/", "drawable-xhdpi/", "drawable-xxhdpi/",
                     "drawable-xxxhdpi/", "drawable-ldpi/", "drawable-mdpi/"]

find_mipmap_dir = ["mipmap/", "mipmap-hdpi/", "mipmap-xhdpi/", "mipmap-xxhdpi/", "mipmap-ldpi/",
                   "mipmap-mdpi/"]

suffixs = [".9.png", ".png", ".jpg", ".xml"]


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
        # 源文件不存在则跳过

        if not os.path.exists(src_file):
            continue

        # 如果文件存在则移动文件
        dst_file = dst_file_path + res_name + suffix
        # 目录不存在则创建目录
        if not os.path.exists(dst_file_path):
            os.makedirs(dst_file_path)

        if os.path.exists(dst_file):
            print dst_file + " is exist"
            continue

        # 根据命令执行,cp或者mv操作
        if sys.argv[1] == "cp":
            shutil.copy(src_file, dst_file)
            if GIT_MODE:
                subprocess.call(['git', "add", dst_file])
            print "****CP****" + src_file + "--->" + dst_file
        elif sys.argv[1] == "mv":
            if GIT_MODE:
                subprocess.call(['git', "mv", src_file, dst_file])
            else:
                shutil.move(src_file, dst_file)
            print "****MV****" + src_file + "--->" + dst_file


move_res()
