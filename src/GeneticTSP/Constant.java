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

    //若连续DISTURB_DEVELOP次进化最优解每次变化不超过DISTURB_THRESHOLD，进行2-opt扰动
    static final int DISTURB_DEVELOP=100;
    static final float DISTURB_THRESHOLD=10.0f;

    static final boolean OPT_ON=false;       // 2-opt开关，默认关闭
    static final boolean RANDOM_INIT_POPULATION=true;     //初始化种群时随机或贪婪，默认随机

    static final String dataFile = ".\\data\\kroA100.txt";   //数据集
    static final String paramFile = ".\\data\\param.txt";    //运行参数文件
    static final String avgResultsFile = ".\\data\\avg_results.txt";   //保存每组参数运行的平均结果

    static {
        //城市坐标集合
        ArrayList<Position> cityPosition = Utils.readData(dataFile);
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
