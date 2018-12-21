package GeneticTSP;

/**
 * Description: 主函数类
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class Main {
    public static void main(String[] args) {

        //开始时间
        long startTime=System.currentTimeMillis();

        //创建遗传算法驱动对象
        GeneticAlgorithm GA = new GeneticAlgorithm();

        //创建初始种群
        SpeciesList speciesList = new SpeciesList();

        //开始遗传算法（选择算子、交叉算子、变异算子）
        SpeciesNode bestRate = GA.run(speciesList);

        //打印路径与最短距离
        System.out.println("GA Completed: ");
        bestRate.printRate();

        //结束时间
        long endTime=System.currentTimeMillis();
        System.out.println("Time cost: "+ (endTime-startTime)/1000.0);
    }
}
