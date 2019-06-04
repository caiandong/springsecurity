package com.example.demo.controller;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.TestController.PictureJson;
import com.example.demo.model.FormData;
import com.example.demo.model.ResultJson;
import com.example.demo.mvc.MvcProperties;

@Controller
@RequestMapping("/test")
public class TestController {

	private final String ImgaePath;
	
	public TestController(MvcProperties mp) {
		String string = mp.getImageUpload();
		string=string.substring(string.indexOf(':')+1);
		if(string.charAt(string.length()-1)!='/')
			ImgaePath=string+'/';
		else
			ImgaePath=string;
		
	}
	@RequestMapping("/upload")
	@ResponseBody
	@CrossOrigin(origins="*")
	public ResultJson shangchuan(MultipartFile[] file,FormData fd) {
		System.out.println(file);
		System.out.println(fd);
		return new ResultJson();
	}
	@RequestMapping("/uploadp")
	@ResponseBody
	@CrossOrigin(origins="*")
	public PictureJson shangchuantupian(@RequestParam(name="editormd-image-file",required=false)MultipartFile[] files,
			String dialog_id) {
		System.out.println(files);
		PictureJson pictureJson = new PictureJson().setDialog_id(dialog_id);
		try {
			if(files.length==1) {
				MultipartFile file=files[0];
//				String filename = file.getOriginalFilename();
				String uuidname = UUID.randomUUID().toString();
				file.transferTo(new File(ImgaePath,uuidname));	
				pictureJson.setUrl("/imageupload/"+uuidname);
			}
		} catch (Exception e) {
			pictureJson.setSuccess(0).setMessage("上传出错");
		}
		
//		System.out.println(fd);
		
		return pictureJson;
	}
	@RequestMapping("/fuck")
	@ResponseBody
	public String fuck () {
		return "fucssssssssss";
	}
	public static class PictureJson{
		private int success;
		private String message;
		private String url;
		private String dialog_id;
		public String getDialog_id() {
			return dialog_id;
		}
		public PictureJson setDialog_id(String dialog_id) {
			this.dialog_id = dialog_id;
			return this;
		}
		public PictureJson() {
			success=1;
			message="好了";
			url="/fuck/asdasd";
		}
		public int getSuccess() {
			return success;
		}
		public PictureJson setSuccess(int success) {
			this.success = success;
			return this;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getUrl() {
			return url;
		}
		public PictureJson setUrl(String url) {
			this.url = url;
			return this;
		}
	}
}
