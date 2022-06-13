import os
import cv2
import sys
import argparse
from paddleocr import PPStructure,draw_structure_result,save_structure_res

def parseArgument():
	parser = argparse.ArgumentParser(description='表格OCR')
	#type是要传入的参数的数据类型  help是该参数的提示信息
	parser.add_argument('--img_path', type=str, help='表格图片文件',required=True)
	parser.add_argument('--save_folder', type=str, help='输出路径',default="/Users/andrew_asa/temp/ocr/out")
	args = parser.parse_args()
	return args.img_path,args.save_folder

def table_to_ocr(img_path,save_folder):

	table_engine = PPStructure(show_log=True)
	img = cv2.imread(img_path)
	result = table_engine(img)
	save_structure_res(result, save_folder,os.path.basename(img_path).split('.')[0])


if __name__=="__main__":
	img_path,save_folder = parseArgument()
	print("img_path="+img_path)
	print("save_folder="+save_folder)
	table_to_ocr(img_path,save_folder)