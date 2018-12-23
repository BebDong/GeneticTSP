package GeneticTSP;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Description: 主函数类
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class Main {
    public static void main(String[] args) {

        //开始时间
        long startTime = System.currentTimeMillis();

        //从参数文件读入参数
        ArrayList<Parameter> parameters = Utils.readParameter(Constant.paramFile);

        //记录每组参数运行epoch次的平均值结果
        ArrayList<String> allAvgResults = new ArrayList<>();

        //每一种参数配置执行遗传算法
        Iterator<Parameter> iterator = parameters.iterator();
        while (iterator.hasNext()) {
            //本次的参数配置
            Parameter thisParam = iterator.next();

            //记录本次参数
            ArrayList<String> outData = new ArrayList<>();
            outData.add("SPECIES_NUM: " + thisParam.getSpeciesNum());
            outData.add("DEVELOP_NUM: " + thisParam.getDevelopNum());
            outData.add("pm: " + thisParam.getPm());
            outData.add("pc: " + thisParam.getPc());
            outData.add("TALENT_RESERVE_RATE: " + thisParam.getTalentReserveRate());
            outData.add("EPOCHS: " + Constant.EPOCHS);

            //记录每次运行的最优解，最后取平均
            ArrayList<Float> results = new ArrayList<>();

            // 同一参数运行多次求平均值
            for (int epoch = 0; epoch < Constant.EPOCHS; epoch++) {

                System.out.println("# Epoch: " + (epoch + 1));

                //创建遗传算法驱动对象
                GeneticAlgorithm GA = new GeneticAlgorithm(thisParam);

                //创建初始种群
                SpeciesList speciesList = new SpeciesList(thisParam.getSpeciesNum());

                //开始遗传算法（选择算子、交叉算子、变异算子）
                SpeciesNode bestRate = GA.run(speciesList, outData);

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
            float avg = sum / results.size();
            System.out.println("# Average shortest distance: ");
            System.out.println(avg);
            outData.add("avg: " + avg);
            //记录本组参数的均值
            allAvgResults.add(avg + "");

            //结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("# Time cost: " + (endTime - startTime) / 1000.0);
            outData.add("time: " + (endTime - startTime) / 1000.0);

            //写入文件
            Path outFile = Paths.get(".\\data\\" + thisParam.getSerialNumber() + ".txt");
            try {
                Files.write(outFile, outData, Charset.forName("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将所有组参数运行的平均结果保存到文件
        Path outFile = Paths.get(Constant.avgResultsFile);
        try {
            Files.write(outFile, allAvgResults, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
