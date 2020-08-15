package com.zhangzhe.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 
 * @Description: 获取视频的信息
 */
public class FetchVideoCover {
	// 视频路径
	private String ffmpegEXE;

	public void getCover(String videoInputPath, String coverOutputPath) throws IOException {
//		ffmpeg.exe -ss 00:00:01 -i spring.mp4 -vframes 1 bb.jpg
		List<String> command = new java.util.ArrayList<>();
		command.add(ffmpegEXE);
		
		// 指定截取第1秒
		command.add("-ss");
		command.add("00:00:00");
		command.add("-i");
		//输入路径
		command.add(videoInputPath);
		command.add("-vframes");
		command.add("1");
		//输出路径
		command.add(coverOutputPath);
		
		for (String c : command) {
			System.out.print(c + " ");
		}
		
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		
		String line = "";
		while ( (line = br.readLine()) != null ) {
		}
		
		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}
	}

	public String getFfmpegEXE() {
		return ffmpegEXE;
	}

	public void setFfmpegEXE(String ffmpegEXE) {
		this.ffmpegEXE = ffmpegEXE;
	}

	public FetchVideoCover() {
		super();
	}

	public FetchVideoCover(String ffmpegEXE) {
		this.ffmpegEXE = ffmpegEXE;
	}
	
	public static void main(String[] args) {
		// 获取视频信息。
		FetchVideoCover videoInfo = new FetchVideoCover("D:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			videoInfo.getCover("C:\\imooc_videos_dev\\2004197S0S2S94M8\\video\\328f4b89-52ca-4859-8b68-3e0b09a8f50d.mp4","C:\\imooc_videos_dev\\2004197S0S2S94M8\\photo\\37a2694e46c8.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}