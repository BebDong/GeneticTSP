package GeneticTSP;

import java.util.ArrayList;

/**
 * Description: 参数定义
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class Constant {

    static int CITY_NUM;                                // 城市数
    static final float[][] disMap;                      // 地图数据

    static final int SPECIES_NUM = 200;                 // 种群大小
    static final int DEVELOP_NUM = 2000;                 // 进化代数
    static final float pcl = 0.6f, pch = 0.95f;         // 交叉概率
    static final float pm = 0.4f;                       // 变异概率
    static final float TALENT_RESERVE_RATE = 0.25f;     //精英复制的个数占种群数量的比例

    static final int EPOCHS = 5;                        // 程序运行次数，取平均

    static final String OUT_FILE = ".\\data\\1.txt";     //输出文件路径

    static {
        //城市坐标集合
        ArrayList<Position> cityPosition = DataReader.readData();

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
