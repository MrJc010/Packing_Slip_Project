package com.bizcom.excel;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class ApplicationPath {
	private String appPath = "";

	public ApplicationPath() {
		CodeSource codeSource = ApplicationPath.class.getProtectionDomain().getCodeSource();
		File jarFile = null;
		try {
			jarFile = new File(codeSource.getLocation().toURI().getPath());
		} catch (URISyntaxException e) {

			e.printStackTrace();
		}
		appPath = jarFile.getParentFile().getPath()+"\\";
	}

	public String getPath() {
		return this.appPath;
	}

	public void setPath(String newPath) {
		this.appPath = newPath;
	}
}
