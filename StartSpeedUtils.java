package reggie.android.performance.scripts.startSpeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created on 2015/12/23
 * 
 * @author Reggie
 *
 */

public class StartSpeedUtils {
	static StartSpeedUtils ss = new StartSpeedUtils();
	final static String pressHome = "adb shell input keyevent KEYCODE_HOME";

	/**
	 * @param activityName
	 * @return 首次启动时间
	 */
	public String getThisTime1(String activityName) {
		String command = "adb shell am start -S -W -n " + activityName;
		String result = ss.execute1(command, "ThisTime");
		ss.execute1(pressHome, "");
		String[] result1 = result.split(":");
		return result1[1].trim();
	}

	/**
	 * 
	 * @param activityName
	 * @return 二次启动时间
	 */
	public String getThisTime2(String activityName) {
		String command = "adb shell am start -W -n " + activityName;
		String result = ss.execute1(command, "ThisTime");
		ss.execute1(pressHome, "");
		System.out.println(result);
		String[] result1 = result.split(":");
		return result1[1].trim();
	}
	
	public String getDeviceId(){
		String command="adb devices";
		String[] result=ss.execute1(command, "").split("\t");
		String[] mResult=result[0].split(" ");
		return mResult[mResult.length-1].trim();
	}

	public String getDeviceName() {
		String command = "adb shell getprop ro.product.device";
		String result = ss.execute1(command, "");
		return result.trim();// IUNI_N1
	}
	
	public String getPhoneModel() {
		String command = "adb shell getprop ro.product.model";
		String result = ss.execute1(command, "");
		System.out.println(result);
		return result.trim();// IUNI_N1
	}
	
	public static void main(String[] args) {
		ss.getPhoneModel();
	}

	public String getSoftwareVersion1() {
		String command = "adb shell getprop ro.gn.iuniznvernumber";
		String result = ss.execute1(command, "");
		System.out.println("软件版本号:"+result);
		return result.trim();// IUNI-N1-AlphaV3.0-201512170137
	}

	public String getSoftwareVersion2() {
		String command = "adb shell getprop ro.gn.gnznvernumber";
		String result = ss.execute1(command, "");
		System.out.println("软件版本号:"+result);
		return result.trim();// IUNI-N1-AlphaV3.0-201512170137
	}
	
	public String getCurrentTime1() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public String getCurrentTime2() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hhmmss");
		return sdf.format(date);
	}
	
	public File getDirectoryPath() {
		String directoryPath = System.getProperty("user.dir") + "\\repository\\results";
		File mDirectoryPath = new File(directoryPath);
		if (!mDirectoryPath.exists()) {
			mDirectoryPath.mkdirs();
		}
		return mDirectoryPath;
	}
	
	// 数组求平均值
	public int getArraysAVG(int[] a) {
		int sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum = sum + a[i];
		}
		int average = sum / a.length;
		return average;
	}

	// 将String[]转换成int[]
	public int[] arrayTransform_StringToInt(String[] str) {
		int[] arr = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			arr[i] = Integer.parseInt(str[i]);
		}
		return arr;
	}

	// List转换为数组
	public String[] listTransformArray(List<String> list) {
		int size = list.size();
		String[] all = list.toArray(new String[size]);
		return all;
	}

	public String execute1(String command, String filter) {
		String commandResult = null;
		BufferedReader in = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			Process proc = Runtime.getRuntime().exec(command);
			proc.waitFor();
			in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (line.contains(filter)) {
					// stringBuffer.append(line + "\n");
					stringBuffer.append(line);
				}
			}
			commandResult = stringBuffer.toString();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return commandResult;
	}
}
