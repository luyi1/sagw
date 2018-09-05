package com.ge.digital.schedule.excelutil;

import java.util.List;

import com.ge.digital.schedule.excelutil.ExcelUploadSupport;


public interface MasterDataValidatorI<T extends ExcelUploadSupport> {
	
	public List<? extends ExcelUploadSupport> validateUpload(List<? extends ExcelUploadSupport> list);
	List<? extends ExcelUploadSupport> validateUploadDelete(List<? extends ExcelUploadSupport> list);
}
