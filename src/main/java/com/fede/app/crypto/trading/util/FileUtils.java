package com.fede.app.crypto.trading.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by f.barbano on 31/01/2016.
 */
public class FileUtils {

	private static final String NEWLINE = "\n";
	private static final String TEMP_FILE = "generic.file.util.temp";

	public static final Charset CHARSET_ISO = Charset.forName("ISO-8859-1");
	public static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	public static final Charset CHARSET_UTF16 = Charset.forName("UTF-16BE");


	/* WRITING METHODS */
	public static void appendToFile(Path outputPath, String data) throws IOException {
		appendToFile(outputPath, data, false);
	}
	public static void appendToFile(Path outputPath, String data, boolean finalNewline) throws IOException {
		appendToFile(outputPath, Collections.singletonList(data), null, finalNewline);
	}
	public static void appendToFile(Path outputPath, String data, Charset encoding, boolean finalNewline) throws IOException {
		appendToFile(outputPath, Collections.singletonList(data), encoding, finalNewline);
	}
	public static void appendToFile(Path outputPath, List<String> lines) throws IOException {
		appendToFile(outputPath, lines, null);
	}
	public static void appendToFile(Path outputPath, List<String> lines, Charset encoding) throws IOException {
		appendToFile(outputPath, lines, encoding, true);
	}
	private static void appendToFile(Path outputPath, List<String> lines, Charset encoding, boolean finalNewline) throws IOException {
		Files.createDirectories(outputPath.toAbsolutePath().getParent());
		BufferedWriter writer = null;

		try {
			if(encoding == null) {
				writer = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} else {
				writer = Files.newBufferedWriter(outputPath, encoding, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			}

			for(int i = 0 ; i < lines.size(); i++) {
				if(!finalNewline && i > 0) 	writer.write(NEWLINE);
				writer.write(lines.get(i));
				if(finalNewline)	writer.write(NEWLINE);
			}
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
	}

	public static void writeFile(Path outputPath, String content, boolean overwrite) throws IOException {
		writeFile(outputPath, content, null, overwrite);
	}
	public static void writeFile(Path outputPath, List<String> lines, boolean overwrite) throws IOException {
		writeFile(outputPath, lines, null, overwrite);
	}
	public static void writeFile(Path outputPath, String content, Charset encoding, boolean overwrite) throws IOException {
		writeFile(outputPath, Arrays.asList(content), encoding, overwrite, false);
	}
	public static void writeFile(Path outputPath, String content, Charset encoding, boolean overwrite, boolean finalNewLine) throws IOException {
		writeFile(outputPath, Arrays.asList(content), encoding, overwrite, finalNewLine);
	}
	public static void writeFile(Path outputPath, List<String> lines, Charset encoding, boolean overwrite) throws IOException {
		writeFile(outputPath, lines, encoding, overwrite, true);
	}
	public static void writeFile(Path outputPath, List<String> lines, Charset encoding, boolean overwrite, boolean finalNewline) throws IOException {
		if(Files.exists(outputPath) && !overwrite) {
			throw new IOException("File [" + outputPath.normalize().toString() + "] already exists");
		}
		Files.deleteIfExists(outputPath);
		appendToFile(outputPath, lines, encoding, finalNewline);
	}
	public static void writeFile(Path outputPath, byte[] bytes, boolean overwrite) throws IOException {
		if(Files.exists(outputPath) && !overwrite) {
			throw new IOException("File [" + outputPath.normalize().toString() + "] already exists");
		}

		Files.createDirectories(outputPath.toAbsolutePath().getParent());
		Files.deleteIfExists(outputPath);
		Files.createFile(outputPath);

		try (OutputStream writer = new FileOutputStream(outputPath.toFile())) {
			writer.write(bytes);
		}
	}

	public static void insertFirstToFile(Path outputPath, List<String> lines) throws IOException {
		insertFirstToFile(outputPath, lines, null);
	}
	public static void insertFirstToFile(Path outputPath, List<String> lines, Charset encoding) throws IOException {
		if(!Files.exists(outputPath)) {
			writeFile(outputPath, lines, encoding, false);
		} else {
			Path tempFile = Paths.get(TEMP_FILE);
			writeFile(tempFile, lines, encoding, true);
			appendToFile(tempFile, Files.readAllLines(outputPath, encoding));
			copyAttributes(outputPath, tempFile);
			Files.delete(outputPath);
			Files.move(tempFile, outputPath);
		}
	}


	/* READING METHODS */
	public static byte[] getBytes(Path path) throws IOException {
		return getBytes(path, 0, -1);
	}
	public static byte[] getBytes(Path path, int start, int end) throws IOException {
		try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "r")) {
			if(end == -1) {
				end = (int) raf.length();
			}

			byte[] bytes = new byte[end - start];
			raf.seek(start);
			raf.read(bytes);
			return bytes;
		}
	}

	public static List<String> readAllLines(InputStream is) throws IOException {
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);

		List<String> lines = new ArrayList<>();
		String line;
		while((line = reader.readLine()) != null) {
			lines.add(line);
		}

		return lines;
	}


	/* REMOVE METHODS */
	public static void removeDirectory(Path folderToDel) throws IOException {
		if(!Files.exists(folderToDel)) {
			throw new FileNotFoundException("[" + folderToDel + "] does not exists");
		}

		if(!Files.isDirectory(folderToDel)) {
			throw new IllegalArgumentException("[" + folderToDel + "] is not a directory");
		}

		removeDirContent(folderToDel);
		Files.delete(folderToDel);
	}
	public static void removeDirectoryIfExists(Path folderToDel) throws IOException {
		if(Files.isDirectory(folderToDel)) {
			removeDirectory(folderToDel);
		}
	}
	private static void removeDirContent(Path folder) throws IOException {
		// remove all files
		List<Path> files = Files.find(folder, 1, (p, a) -> !Files.isDirectory(p)).collect(Collectors.toList());
		for(Path f : files)	Files.delete(f);

		// delete recursively the content of dirs
		List<Path> dirs = Files.find(folder, 1, (p, a) -> Files.isDirectory(p)).filter(p -> !p.equals(folder)).collect(Collectors.toList());
		for(Path d : dirs) {
			removeDirContent(d);
			Files.delete(d);
		}
	}


	/* MISCELLANEOUS METHODS */
	public static void copyFile(Path sourcePath, Path targetPath, boolean overwrite) throws IOException {
		copyFile(sourcePath, targetPath, overwrite, false);
	}
	public static void copyFile(Path sourcePath, Path targetPath, boolean overwrite, boolean holdAttributes) throws IOException {
		if(!Files.exists(sourcePath) || !Files.isRegularFile(sourcePath))  {
			throw new FileNotFoundException("Source file [%s] not exists or not a regular file!");
		}
		if(Files.exists(targetPath) && !Files.isRegularFile(targetPath)) {
			throw new FileAlreadyExistsException(String.format("Unable to copy [%s] to [%s]: target path is a folder", sourcePath.toAbsolutePath(), targetPath.toAbsolutePath()));
		}
		if(!overwrite && Files.exists(targetPath)) {
			throw new FileAlreadyExistsException(String.format("Unable to copy [%s] to [%s]: target path already exists", sourcePath.toAbsolutePath(), targetPath.toAbsolutePath()));
		}

		Files.deleteIfExists(targetPath);
		Files.createDirectories(targetPath.toAbsolutePath().getParent());
		Files.copy(sourcePath, targetPath);
		if(holdAttributes) {
			copyAttributes(sourcePath, targetPath);
		}
	}
	public static Path copyFileSafely(Path sourcePath, Path targetPath) throws IOException {
		return copyFileSafely(sourcePath, targetPath, false);
	}
	public static Path copyFileSafely(Path sourcePath, Path targetPath, boolean holdAttributes) throws IOException {
		Path newPath = computeSafelyPath(targetPath);
		copyFile(sourcePath, newPath, false, holdAttributes);
		return newPath;
	}

	public static void moveFile(Path sourcePath, Path targetPath, boolean overwrite) throws IOException {
		if(!Files.exists(sourcePath) || !Files.isRegularFile(sourcePath))  {
			throw new FileNotFoundException("Source file [%s] not exists or not a regular file!");
		}
		if(Files.exists(targetPath) && !Files.isRegularFile(targetPath)) {
			throw new FileAlreadyExistsException(String.format("Unable to move [%s] to [%s]: target path is a folder", sourcePath.toAbsolutePath(), targetPath.toAbsolutePath()));
		}
		if(!overwrite && Files.exists(targetPath)) {
			throw new FileAlreadyExistsException(String.format("Unable to move [%s] to [%s]: target path already exists", sourcePath.toAbsolutePath(), targetPath.toAbsolutePath()));
		}

		Files.deleteIfExists(targetPath);
		Files.createDirectories(targetPath.toAbsolutePath().getParent());
		Files.move(sourcePath, targetPath);
	}
	public static Path moveFileSafely(Path sourcePath, Path targetPath) throws IOException {
		Path newPath = computeSafelyPath(targetPath);
		moveFile(sourcePath, newPath, false);
		return newPath;
	}

	public static boolean pathEquals(Path p1, Path p2) {
		return p1.toAbsolutePath().equals(p2.toAbsolutePath());
	}
	public static boolean containsPath(List<Path> source, Path toFind) {
		for(Path p : source) {
			if(pathEquals(p, toFind)) {
				return true;
			}
		}
		return false;
	}

	public static void copyAttributes(Path sourcePath, Path targetPath) throws IOException {
		FileTime lastMod = Files.getLastModifiedTime(sourcePath);
		UserPrincipal owner = Files.getOwner(sourcePath);
		Files.setLastModifiedTime(targetPath, lastMod);
		Files.setOwner(targetPath, owner);
	}

	public static String getExtension(Path path) {
		return getExtension(path.getFileName().toString());
	}
	public static String getExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		return index < 0 ? "" : fileName.substring(index+1);
	}

	public static Path computeSafelyPath(String targetPath) {
		return computeSafelyPath(Paths.get(targetPath));
	}
	public static Path computeSafelyPath(Path targetPath) {
		Path newPath = targetPath;

		if(Files.exists(targetPath)) {
			String origOutPath = targetPath.getFileName().toString();
			String extension = getExtension(origOutPath);

			String newFileName;
			if(StringUtils.isBlank(extension)) {
				newFileName = origOutPath;
			} else {
				int endIdx = origOutPath.length() - 1 - extension.length();
				newFileName = origOutPath.substring(0, endIdx);
				extension = "." + extension;
			}

			int counter = 1;
			newPath = targetPath.getParent().resolve(newFileName + "." + counter + extension);
			while(Files.exists(newPath)) {
				counter++;
				newPath = targetPath.getParent().resolve(newFileName + "." + counter + extension);
			}
		}

		return newPath;
	}

	public static Path backupIfExists(Path targetPath) throws IOException {
		if(!Files.exists(targetPath)) {
			return null;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		String dtStr = LocalDateTime.now().format(formatter);

		String ext = getExtension(targetPath);

		String origPath = targetPath.toString();
		String newPath;

		if(ext.isEmpty()) {
			newPath = String.format("%s.%s", origPath, dtStr);
		} else {
			newPath = origPath.replaceAll(ext + "$", String.format("%s.%s", dtStr, ext));
		}

		Path outPath = Paths.get(newPath);
		moveFile(targetPath, outPath, false);
		return outPath;
	}

	public static Path getLauncherPath(Class<?> clazz) {
		try {
			String path = clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			return Paths.get(path.startsWith("/") ? path.substring(1) : path);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
