package GeneticTSP;

import java.io.*;
import java.util.ArrayList;

/**
 * Description: 读取数据集文件
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class DataReader {
    public static ArrayList<Position> readData() {
        ArrayList<Position> cityPosition = new ArrayList<>();
        // 读取文件并初始化cityPosition
        File dataFile = new File(".\\data\\kroA100.txt");
//        File dataFile = new File(".\\data\\city42.txt");
        if (dataFile.isFile() && dataFile.exists()) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(dataFile));
                BufferedReader bufferedReader = new BufferedReader(reader);
                String singleLine = null;
                while ((singleLine=bufferedReader.readLine())!=null){
                    // 解析这行数据
                    cityPosition.add(parseLine(singleLine));
                }
                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cityPosition;
    }

    // 解析一行数据
    private static Position parseLine(String line){
        Position position=new Position();
        String temp[]=line.split(" ");
        position.setX(Integer.parseInt(temp[1]));
        position.setY(Integer.parseInt(temp[2]));
        return position;
    }

}
