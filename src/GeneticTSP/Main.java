package GeneticTSP;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Description: 主函数类
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class Main {
    public static void main(String[] args) {

        //开始时间
        long startTime = System.currentTimeMillis();

        //结果输出至文件
        ArrayList<String> outdata = new ArrayList<>();
        outdata.add("SPECIES_NUM: " + Constant.SPECIES_NUM);
        outdata.add("DEVELOP_NUM: " + Constant.DEVELOP_NUM);
        outdata.add("pm: " + Constant.pm);
        outdata.add("pcl: " + Constant.pcl + " pch: " + Constant.pch);
        outdata.add("TALENT_RESERVE_RATE: " + Constant.TALENT_RESERVE_RATE);
        outdata.add("EPOCHS: " + Constant.EPOCHS);


        //记录每次运行的最优解，最后取平均
        ArrayList<Float> results = new ArrayList<>();

        for (int epoch = 0; epoch < Constant.EPOCHS; epoch++) {

            System.out.println("# Epoch: " + epoch);

            //创建遗传算法驱动对象
            GeneticAlgorithm GA = new GeneticAlgorithm();

            //创建初始种群
            SpeciesList speciesList = new SpeciesList();

            //开始遗传算法（选择算子、交叉算子、变异算子）
            SpeciesNode bestRate = GA.run(speciesList, outdata);

            //打印路径与最短距离
            System.out.println("* GA Completed: ");
            bestRate.printRate();

            //记录本次收敛结果
            results.add(bestRate.distance);
        }

        //求解均值
        float sum = 0;
        for (int i = 0; i < results.size(); i++) {
            sum += results.get(i);
            System.out.println("# Shortest distance in each epoch: ");
            System.out.println("In epoch " + i + ": " + results.get(i));
        }
        System.out.println("# Average shortest distance: ");
        System.out.println(sum / results.size());
        outdata.add("avg: " + sum / results.size());

        //写入文件
        Path outFile = Paths.get(Constant.OUT_FILE);
        try {
            Files.write(outFile, outdata, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("# Time cost: " + (endTime - startTime) / 1000.0);
    }
}
