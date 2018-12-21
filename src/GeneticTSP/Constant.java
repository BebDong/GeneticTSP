package GeneticTSP;

import java.util.ArrayList;

/**
 * Description: 初始化操作
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class Constant {

    static int CITY_NUM;                                // 城市数
    static final float[][] disMap;                      // 地图数据
    static final int EPOCHS = 1;                        // 程序运行次数，取平均

    static final String dataFile = ".\\data\\kroA100.txt";  //数据集
    static final String paramFile = ".\\data\\param.txt";    //运行参数文件

    static {
        //城市坐标集合
        ArrayList<Position> cityPosition = DataReader.readData(dataFile);
        //路径集合
        CITY_NUM = cityPosition.size();
        disMap = new float[CITY_NUM][CITY_NUM];
        for (int i = 0; i < CITY_NUM; i++) {
            for (int j = i; j < CITY_NUM; j++) {
                Position thisPos = cityPosition.get(i);
                Position nextPos = cityPosition.get(j);
                float dis = (float) Math.sqrt(Math.pow(thisPos.getX() - nextPos.getX(), 2) +
                        Math.pow(thisPos.getY() - nextPos.getY(), 2));
                disMap[i][j] = dis;
                disMap[j][i] = disMap[i][j];
            }
        }
    }
}
