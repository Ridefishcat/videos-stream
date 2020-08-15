package com.zhangzhe.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {

	private String ffmpegEXE;

	public MergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	//ffmpeg -i 20181225_231045_9714.mp4 -i Houche.mp3 -filter_complex [1:a]aloop=loop=-1:size=2e+09[out];[out][0:a]amix -t 10 -y out1.mp4
	public void convertor(String videoInputPath, String mp3InputPath,
						   double seconds, String videoOutputPath) throws Exception {

		List<String> command = new ArrayList<>();
		command.add(ffmpegEXE);

		command.add("-i");
		command.add(videoInputPath);

		command.add("-i");
		command.add(mp3InputPath);

		command.add("-filter_complex");
		command.add("[1:a]aloop=loop=-1:size=2e+09[out];[out][0:a]amix");

		command.add("-t");
		command.add(String.valueOf(seconds));

		command.add("-y");
		command.add(videoOutputPath);


		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();

		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);

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
}
