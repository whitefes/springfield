package com.u2ware.springfield.view.multipart;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.u2ware.springfield.support.multipart.MultipartFileHandler;
import com.u2ware.springfield.view.ModelFilter;

public class MultipartFileBeanView extends AbstractView{

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required=false)
	private MultipartFileHandler multipartFileHandler;
	private ModelFilter modelFilter;
	private boolean isDownload = false;
	
	public void setMultipartFileHandler(MultipartFileHandler multipartFileHandler) {
		this.multipartFileHandler = multipartFileHandler;
	}
	public void setModelFilter(ModelFilter modelFilter) {
		this.modelFilter = modelFilter;
	}
	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}
	
	protected boolean generatesDownloadContent() {
		return isDownload;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(generatesDownloadContent()){
			download(request, response, model);
		}else{
			stream(request, response, model);
		}
		
	}	
	
	private MultipartFileBean filterModel(Map<String, Object> model)throws Exception {
		try{
			return (MultipartFileBean)modelFilter.extractOutputModel(model);
		}catch(Exception e){
			throw new Exception("Downloading is not found in model");
		}
	}	
	
	protected void download(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception{
		
		MultipartFileBean bean = filterModel(model);
		
		String contentFile = bean.getContentFile();
		String contentName = bean.getContentName();
		String contentType = bean.getContentType();
		long contentSize = bean.getContentSize();
		
		File file = multipartFileHandler.findFile(contentFile);
		logger.debug("contentFile : "+contentFile);
		logger.debug("contentName : "+contentName);
		logger.debug("contentType : "+contentType);
		logger.debug("contentSize : "+contentSize);
		logger.debug("file : "+file);

		String filename = new String(contentName.getBytes("euc-kr") , "8859_1"); 

		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		if(contentSize > 0){
			response.setHeader("Content-Length", ""+contentSize);
			response.setContentLength((int)contentSize);
		}
		if(contentType != null){
			response.setContentType(contentType);
		}
		
		this.copy(file, response.getOutputStream());
	}
	
	protected void stream(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception{
		
		MultipartFileBean bean = filterModel(model);

		String contentFile = bean.getContentFile();
		String contentName = bean.getContentName();
		String contentType = bean.getContentType();
		long contentSize = bean.getContentSize();
		
		File file = multipartFileHandler.findFile(contentFile);
		logger.debug("contentFile : "+contentFile);
		logger.debug("contentName : "+contentName);
		logger.debug("contentType : "+contentType);
		logger.debug("contentSize : "+contentSize);
		logger.debug("file : "+file);

		if(contentType != null){
			response.setContentType(contentType);
		}
		response.setContentLength((int)contentSize);
		copy(file, response.getOutputStream());
	}
	
	private void copy(File file, OutputStream out) throws Exception{
		FileCopyUtils.copy(new FileInputStream(file), out);
	}
}
