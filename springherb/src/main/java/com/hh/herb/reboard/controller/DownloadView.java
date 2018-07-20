package com.hh.herb.reboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class DownloadView extends AbstractView {
	private static final Logger logger
	=LoggerFactory.getLogger(DownloadView.class);
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, 
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		File file =(File) model.get("file");
		logger.info("다운로드 처리를 위한 뷰, file명={}", file.getName());
		
		if(!file.exists() || !file.canRead()) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out =response.getWriter();
			out.println("<script type='text/javascript'>");
			out.print("alert('파일이 존재하지 않거나 손상된 파일입니다.');");
			out.println("history.back();");
			out.println("</script>");
			return;
		}
		
		//다운로드 창 띄우기
		response.setContentType("application/octet-stream");
		String str = new String(file.getName().getBytes("euc-kr"),"8859_1");
		response.setHeader("Content-disposition", "attachment;filename="+str);
		
		OutputStream os =response.getOutputStream();
		FileInputStream fis = new FileInputStream(file);
		
		FileCopyUtils.copy(fis, os);
		
		fis.close();
		os.close();
	}

}





