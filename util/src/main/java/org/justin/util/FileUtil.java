package org.justin.util;

import java.io.File;

public final class FileUtil {
	private FileUtil() {
		// do nothing, hide constructor
	}

	public static boolean delete(String filepath) throws Exception {
        File path = new File(filepath);
        if (path.isDirectory()) {
            File[] fileList = path.listFiles();
            if (null == fileList) {
            	return false;
            }
            if (fileList.length==0) {
                path.delete();	// delete empty directory
            } else {
                for (File file : fileList) {
                    FileUtil.delete(file.getCanonicalPath());	// recursively delete directory or file
                }
                fileList = path.listFiles();	// re-read directory contents
                if (fileList.length==0) {
                    path.delete();	// delete empty directory
                }
            }
            return true;
        }
        return path.delete();
    }
}
