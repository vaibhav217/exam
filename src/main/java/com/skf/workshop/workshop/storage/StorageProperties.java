package com.skf.workshop.workshop.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String companyUpload = "./uploads";
	private String imagesUpload = "./images";


	public String getCompanyUpload() {
		return companyUpload;
	}

	public void setCompanyUpload(String companyUpload) {
		this.companyUpload = companyUpload;
	}


	public String getImagesUpload() {
		return imagesUpload;
	}

	public void setImagesUpload(String imagesUpload) {
		this.imagesUpload = imagesUpload;
	}

}