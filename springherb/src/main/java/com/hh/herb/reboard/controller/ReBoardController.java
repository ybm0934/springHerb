package com.hh.herb.reboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hh.herb.common.FileUploadUtil;
import com.hh.herb.common.PaginationInfo;
import com.hh.herb.common.SearchVO;
import com.hh.herb.common.Utility;
import com.hh.herb.reboard.model.ReBoardService;
import com.hh.herb.reboard.model.ReBoardVO;

@Controller
@RequestMapping("/reBoard")
public class ReBoardController {
	private static final Logger logger
	=LoggerFactory.getLogger(ReBoardController.class);

	@Autowired private ReBoardService reBoardService;
	@Autowired private FileUploadUtil fileUploadUtil;
	
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write_get() {
		//handler
		logger.info("글쓰기 화면");
		
		return "reBoard/write";  //논리적 뷰이름 리턴
	}

	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String write_post(@ModelAttribute ReBoardVO reBoardVo,
			HttpServletRequest request) {
		logger.info("글쓰기 처리, 파라미터 vo={}", reBoardVo);
		
		//파일 업로드 처리
		String fileName="", originalFileName="";
		long fileSize=0;
		try {
			List<Map<String, Object>> fileList
				= fileUploadUtil.fileUpload(request, 
						fileUploadUtil.PATH_FLAG_PDS);
			for(Map<String, Object> map : fileList) {
				fileName=(String) map.get("fileName");
				originalFileName=(String) map.get("originalFileName");
				fileSize=(long) map.get("fileSize");				
			}
			reBoardVo.setFileName(fileName);
			reBoardVo.setFileSize(fileSize);
			reBoardVo.setOriginalFileName(originalFileName);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("2M 이상의 파일은 업로드 불가!");
			e.printStackTrace();
		}
		
		int cnt=reBoardService.insertReBoard(reBoardVo);
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		return "redirect:/reBoard/list.do";
	}
	
	@RequestMapping("/list.do")
	public String list(@ModelAttribute SearchVO searchVo,  Model model) {
		logger.info("글 목록, 파라미터 searchVo={}", searchVo);
		
		//[1] PaginationInfo 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		//[2] SearchVO 에 값 셋팅
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT_PER_PAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		logger.info("setting 후 searchVo={}", searchVo);
		
		List<ReBoardVO> list =reBoardService.selectAll(searchVo);
		logger.info("글 목록 조회 결과, list.size={}", list.size());
		
		//전체 레코드 개수 조회
		int totalRecord=reBoardService.getTotalRecord(searchVo);
		pagingInfo.setTotalRecord(totalRecord);
		logger.info("전체 레코드 개수={}", totalRecord);
		
		model.addAttribute("list", list);
		model.addAttribute("pageVo", pagingInfo);
		
		return "reBoard/list";
	}
	
	@RequestMapping("/countUpdate.do")
	public String countUpdate(@RequestParam(defaultValue="0") int no, 
			ModelMap model) {
		logger.info("조회수 증가, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		int cnt=reBoardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);
		
		return "redirect:/reBoard/detail.do?no="+no;
	}
	
	@RequestMapping("/detail.do")
	public String detail(@RequestParam(defaultValue="0") int no, 
			HttpServletRequest request, Model model) {
		logger.info("상세보기 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("상세보기 결과, vo={}", vo);
		
		model.addAttribute("fileInfo", 
		Utility.getFileInfo(vo.getOriginalFileName(), vo.getFileSize(), request));
		model.addAttribute("downInfo", vo.getDownCount());
		model.addAttribute("vo", vo);
		
		return "reBoard/detail";
	}
	
	@RequestMapping(value="/edit.do", method=RequestMethod.GET)
	public String edit_get(@RequestParam(defaultValue="0") int no, 
			HttpServletRequest request, Model model) {
		logger.info("수정화면 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("수정화면 상세보기 결과, vo={}", vo);
		
		String fileInfo=Utility.getFileInfo(vo.getOriginalFileName(), vo.getFileSize(), request);
		
		model.addAttribute("vo", vo);
		model.addAttribute("fileInfo", fileInfo);
		
		return "reBoard/edit";
	}
	
	@RequestMapping(value="/edit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute ReBoardVO reBoardVo, 
			@RequestParam String oldFileName, HttpServletRequest request,
			Model model) {
		logger.info("글수정 처리, 파라미터 vo={}, oldFileName={}",
				reBoardVo, oldFileName);
		
		String msg="", url="/reBoard/edit.do?no="+reBoardVo.getNo();
		if(reBoardService.checkPwd(reBoardVo.getNo(), reBoardVo.getPwd())) {
			//파일 업로드 처리
			String fileName="", originalFileName="";
			long fileSize=0;
			try {
				List<Map<String, Object>> fileList
					=fileUploadUtil.fileUpload(request,fileUploadUtil.PATH_FLAG_PDS);
				
				for(Map<String, Object> map : fileList) {
					fileName=(String) map.get("fileName");
					originalFileName=(String) map.get("originalFileName");
					fileSize=(long) map.get("fileSize");
				}
				
				//업로드 파일의 정보 셋팅
				reBoardVo.setFileName(fileName);
				reBoardVo.setOriginalFileName(originalFileName);
				reBoardVo.setFileSize(fileSize);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			
			int cnt=reBoardService.updateReBoard(reBoardVo);
			logger.info("글수정 결과, cnt={}", cnt);
			if(cnt>0) {
				msg="글 수정 성공";			
				url="/reBoard/detail.do?no="+reBoardVo.getNo();
			}else {
				msg="글 수정 실패";
			}
			
			//새로 업로드하는 파일이 있으면 기존 파일 삭제
			if(fileName!=null && !fileName.isEmpty()) {
				File oldFile 
				= new File(fileUploadUtil.getUploadPath(request,fileUploadUtil.PATH_FLAG_PDS), oldFileName);
				if(oldFile.exists()) {
					boolean bool=oldFile.delete();
					logger.info("기존 파일 삭제 여부:{}", bool);
				}
			}//if
		}else {
			msg="비밀번호가 일치하지 않습니다.";			
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
	public String delete(@RequestParam(defaultValue="0") int no,
			Model model) {
		logger.info("글삭제 화면, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg","잘못된 URL 입니다.");
			model.addAttribute("url","/reBoard/list.do");
			return "common/message";
		}
		
		return "reBoard/delete";
	}
	
	@RequestMapping(value="/delete.do", method=RequestMethod.POST)
	public String delete_post(@ModelAttribute ReBoardVO reBoardVo, 
			HttpServletRequest request, Model model) {
		logger.info("글삭제 처리, 파라미터 vo={}", reBoardVo);
		
		String msg="", url="/reBoard/delete.do?no="+reBoardVo.getNo()
			+"&fileName="+reBoardVo.getFileName();
		if(reBoardService.checkPwd(reBoardVo.getNo(), reBoardVo.getPwd())) {
			Map<String, String> map = new HashMap<>();
			map.put("no", reBoardVo.getNo()+"");
			
			reBoardService.deleteReBoard(map);			
			msg="글 삭제 성공";
			url="/reBoard/list.do";	
			
			//파일이 첨부된 경우에는 파일도 삭제처리
			if(reBoardVo.getFileName()!=null && !reBoardVo.getFileName().isEmpty()) {
				File file 
	= new File(fileUploadUtil.getUploadPath(request,fileUploadUtil.PATH_FLAG_PDS), reBoardVo.getFileName());
				if(file.exists()) {
					boolean bool =file.delete();
					logger.info("파일 삭제 여부 : {}", bool);
				}
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		return "common/message";
	}
	
	@RequestMapping("/deleteMulti.do")
	public String deleteMulti(@RequestParam String[] chk, Model model) {
		logger.info("여러 글 삭제");
		
		//String[] chkArr = request.getParameterValues("chk");
		if(chk!=null) {
			int i=0;
			for(String no : chk) {
				logger.info("{} : 파라미터 => {}", i++, no);
			}
		}//if
		
		Map<String, String[]> map = new HashMap<>();
		map.put("nos", chk);
		int cnt=reBoardService.deleteMulti(map);
		logger.info("여러 글 삭제 결과, cnt={}", cnt);
		
		String msg="", url="/reBoard/list.do";
		if(cnt>0) {
			msg="여러 글 삭제 성공";
		}else {
			msg="여러 글 삭제 실패";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/download.do")
	public ModelAndView download(@RequestParam(defaultValue="0") int no,
			@RequestParam String fileName, HttpServletRequest request) {
		logger.info("다운로드 수 증가, 파라미터 no={}, fileName={}", no, fileName);
		
		int cnt = reBoardService.updateDowncount(no);
		logger.info("다운로드 수 증가 결과, cnt={}", cnt);
		
		//파일 다운로드 처리를 위한 뷰로 forward
		File file = new File(fileUploadUtil.getUploadPath(request,fileUploadUtil.PATH_FLAG_PDS), fileName);
		Map<String, Object> map = new HashMap<>();
		map.put("file", file);
		
		ModelAndView mav = new ModelAndView("downloadView", map);
		//ModelAndView(String viewName, Map<String, ?> model)
		
		return mav;
	}
	
	@RequestMapping(value="/reply.do", method=RequestMethod.GET)
	public String reply(@RequestParam(defaultValue="0") int no, Model model) {
		logger.info("답변하기 화면, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("답변하기 - 조회 결과, vo={}", vo);
		
		model.addAttribute("vo", vo);
		
		return "reBoard/reply";
	}
	
	@RequestMapping(value="/reply.do", method=RequestMethod.POST)
	public String reply_post(@ModelAttribute ReBoardVO vo) {
		logger.info("답변하기 처리, 파라미터 vo={}", vo);
		
		int cnt=reBoardService.reply(vo);
		logger.info("답변처리 결과, cnt={}", cnt);
		
		return "redirect:/reBoard/list.do";
	}
	
	
}











