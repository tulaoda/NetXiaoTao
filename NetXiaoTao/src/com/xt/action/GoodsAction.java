package com.xt.action;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xt.base.BaseAction;
import com.xt.entity.Goods;
import com.xt.service.GoodsItemService;

@Namespace("/goods")
@ParentPackage("json-default")
@Controller
public class GoodsAction extends BaseAction{
	
	private File file;
	private String fileFileName;
	private String fileContentType;

	private String code;
	private List<Goods> data;
	private int pageSize;
	private int page;
	public Goods goodsItem;
	@Autowired
	private GoodsItemService goodsItemService;
	
	
	@Action(value="findAllGoodsItem",results={
			@Result(name="success",type="json")
	})
	public String findAllGoodsItem(){
		data=goodsItemService.findGoodsItemForPage(pageSize,page);
		if(data!=null){
			code="1";
		}else{
			code="0";
		}
		return SUCCESS;
	}
	@Action(value="findAllGoodsItemByL_class",results={
			@Result(name="success",type="json")
	})
	
	public String findAllGoodsItemByL_class(){
		data=goodsItemService.findGoodsItemByL_classForPage(goodsItem.getL_class(),pageSize,page);
		if(data!=null){
			code="1";
		}else{
			code="0";
		}
		return SUCCESS;
	}
	@Action(value="removeGoodsItem",results={
			@Result(name="success",type="json")
	})
	public String removeGoodsItem(){
		boolean flag=goodsItemService.removeGoodsItem(goodsItem);
		if(flag){
			code="1";
		}else{
			code="0";
		}
		return SUCCESS;
	}
	
	/*@Action(value="addNewGoodsItem",results={
			@Result(name="success",type="json")
	})
	public String addNewGoodsItem(){
		System.out.println("==========file============="+file==null);
		System.out.println("============fileFileName==============="+fileFileName);
		System.out.println("=================fileContentType==============="+fileContentType);
		boolean flag=goodsItemService.addNewGoodsItem(goodsItem,file,fileFileName);
		if(flag){
			code="1";
			data=new ArrayList<>();
			data.add(goodsItemService.findMaxIdGoodsItem());
		}else{
			code="0";
		}
		return SUCCESS;
	}
*/
	public String getCode() {
		return code;
	}


	public List<Goods> getData() {
		return data;
	}

	public void setGoodsItem(Goods goodsItem) {
		this.goodsItem = goodsItem;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public GoodsItemService getGoodsItemService() {
		return goodsItemService;
	}
	public void setGoodsItemService(GoodsItemService goodsItemService) {
		this.goodsItemService = goodsItemService;
	}
	public File getFile() {
		return file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public Goods getGoodsItem() {
		return goodsItem;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setData(List<Goods> data) {
		this.data = data;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	
}