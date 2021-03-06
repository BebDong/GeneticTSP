package GeneticTSP;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Description: 工具类
 * Author: Quan Tang
 * Date: 2018/12/23
 **/
public class Utils {
    //读取数据文件
    public static ArrayList<Position> readData(String filePath) {
        ArrayList<Position> cityPosition = new ArrayList<>();
        // 读取文件并初始化cityPosition
        File dataFile = new File(filePath);
        if (dataFile.isFile() && dataFile.exists()) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(dataFile));
                BufferedReader bufferedReader = new BufferedReader(reader);
                String singleLine = null;
                while ((singleLine = bufferedReader.readLine()) != null) {
                    // 解析这行数据
                    cityPosition.add(parseDataLine(singleLine));
                }
                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cityPosition;
    }

    // 解析数据集一行数据
    private static Position parseDataLine(String line) {
        Position position = new Position();
        String temp[] = line.split(" ");
        position.setX(Integer.parseInt(temp[1]));
        position.setY(Integer.parseInt(temp[2]));
        return position;
    }

    //读取参数文件
    public static ArrayList<Parameter> readParameter(String filePath) {
        ArrayList<Parameter> parameters = new ArrayList<>();
        File paramFile = new File(filePath);
        if (paramFile.isFile() && paramFile.exists()) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(paramFile));
                BufferedReader bufferedReader = new BufferedReader(reader);
                String singleLine = null;
                while ((singleLine = bufferedReader.readLine()) != null) {
                    // 解析这行数据
                    parameters.add(parseParamLine(singleLine));
                }
                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return parameters;
    }

    // 解析参数文件一行数据
    private static Parameter parseParamLine(String line) {
        Parameter parameter = new Parameter();
        String[] temp = line.split(" ");
        parameter.setSerialNumber(temp[0]);
        parameter.setSpeciesNum(Integer.parseInt(temp[1]));
        parameter.setDevelopNum(Integer.parseInt(temp[2]));
        parameter.setPc(Float.parseFloat(temp[3]));
        parameter.setPm(Float.parseFloat(temp[4]));
        parameter.setTalentReserveRate(Float.parseFloat(temp[5]));
        return parameter;
    }

    //生成指定范围内的随机数
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    //获取一个基因对应的路径
    public static String getPath(String[] genes){
        String path="";
        for (int i = 0; i < genes.length; i++)
            path += (genes[i] + "->");
        path +=(genes[0] + "\n");
        return path;
    }
}
