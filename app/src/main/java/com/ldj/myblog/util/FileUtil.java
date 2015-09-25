package com.ldj.myblog.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class FileUtil {
	private static final String TAG = FileUtil.class.getSimpleName();

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static File getExternalFilesDir(Context context) {
		return context.getExternalFilesDir(null);
	}

	/**
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static File getExternalFilesDir(Context context, String name) {
		return context.getExternalFilesDir(name);
	}

	/**
	 * 
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(File fromFile, String toFile)
			throws IOException {

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
		}
	}

	/**
	 * 
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(String fromFile, String toFile)
			throws IOException {

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					Log.e(TAG, "", e);
				}
		}
	}


	public static int copy(File fromFile, String toFile) throws IOException {
		File[] currentFiles;
		if (!fromFile.exists()) {
			return -1;
		}
		currentFiles = fromFile.listFiles();

		
		File targetDir = new File(toFile);
	
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

		
		for (int i = 0; i < currentFiles.length; i++) {
			if (currentFiles[i].isDirectory())
			{
				copy(currentFiles[i], toFile + currentFiles[i].getName() + "/");

			} else
			{
				copyFile(currentFiles[i], toFile + currentFiles[i].getName());
			}
		}
		return 0;
	}

	
	public static void renameTo(String filePath, String newPath) {
		File file = new File(filePath);
		if (file.exists()) {
			File file1 = new File(newPath);
			file.renameTo(file1);
		}
	}

	
	public static File createNewFile(File file) {

		try {

			if (file.exists()) {
				return file;
			}

			File dir = file.getParentFile();
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			Log.e(TAG, "", e);
			return null;
		}
		return file;
	}

	
	public static File createNewFile(String path) {
		File file = new File(path);
		return createNewFile(file);
	}

	
	public static void deleteFile(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
//				file.delete();
				return;
			}
			for (int i = 0; i < childFiles.length; i++) {

				deleteFile(childFiles[i]);
			}

			file.delete();
		}

	}

	
	public static void deleteFile(String path) {
		File file = new File(path);
		deleteFile(file);
	}

	/**
	 * write content
	 * 
	 * @param file
	 * @param content
	 * @return
	 */
	public static boolean write(String path, String content) {
		return write(path, content, false);
	}

	public static boolean write(String path, String content, boolean append) {
		return write(new File(path), content, append);
	}

	public static boolean write(File file, String content) {
		return write(file, content, false);
	}

	public static boolean write(File file, String content, boolean append) {
		if (file == null || TextUtils.isEmpty(content)) {
			return false;
		}
		if (!file.exists()) {
			file = createNewFile(file);
		}
		FileOutputStream ops = null;
		try {
			ops = new FileOutputStream(file, append);
			ops.write(content.getBytes());
		} catch (Exception e) {
			Log.e(TAG, "", e);
			return false;
		} finally {
			try {
				ops.close();
			} catch (IOException e) {
				Log.e(TAG, "", e);
			}
			ops = null;
		}

		return true;
	}

	/**
	 * get file name
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		File f = new File(path);
		String name = f.getName();
		f = null;
		return name;
	}

	/**
	 * 读取文件内容，从第startLine行开始，读取lineCount行
	 * 
	 * @param file
	 * @param startLine
	 * @param lineCount
	 * @return 读到文字的list,如果list.size<lineCount则说明读到文件末尾了
	 */
	public static List<String> readFile(File file, int startLine, int lineCount) {
		if (file == null || startLine < 1 || lineCount < 1) {
			return null;
		}
		if (!file.exists()) {
			return null;
		}
		FileReader fileReader = null;
		List<String> list = null;
		try {
			list = new ArrayList<String>();
			fileReader = new FileReader(file);
			LineNumberReader lnr = new LineNumberReader(fileReader);
			boolean end = false;
			for (int i = 1; i < startLine; i++) {
				if (lnr.readLine() == null) {
					end = true;
					break;
				}
			}
			if (end == false) {
				for (int i = startLine; i < startLine + lineCount; i++) {
					String line = lnr.readLine();
					if (line == null) {
						break;
					}
					list.add(line);

				}
			}
		} catch (Exception e) {
			Log.e(TAG, "read log error!", e);
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean createDir(File dir) {
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return true;
		} catch (Exception e) {
			Log.e(TAG, "create dir error", e);
			return false;
		}
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 */
	public static File creatSDDir(String dirName) {
		File dir = new File(dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * 判断SD卡上的文件是否存在
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	public static File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			creatSDDir(path);
			file = createNewFile(path + "/" + fileName);
			output = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int len = -1;
			while ((len = input.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	// 从文件中一行一行的读取文件
	public static String readFile(File file) {
		Reader read = null;
		String content = "";
		String string = "";
		BufferedReader br = null;
		try {
			read = new FileReader(file);
			br = new BufferedReader(read);
			while ((content = br.readLine().toString().trim()) != null) {
				string += content + "\r\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				read.close();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("string=" + string);
		return string.toString();
	}

}
