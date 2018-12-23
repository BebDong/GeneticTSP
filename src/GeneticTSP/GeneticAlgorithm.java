package GeneticTSP;

import java.util.ArrayList;
import java.util.Random;

/**
 * Description: 遗传算法类
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class GeneticAlgorithm {
    //遗传算法参数
    Parameter parameter;

    //构造器
    public GeneticAlgorithm(Parameter parameter) {
        this.parameter = parameter;
    }

    //开始遗传
    SpeciesNode run(SpeciesList list, ArrayList<String> outdata) {
        //创建初始种群
        createBeginningSpecies(list);

        //记录前一个种群最优值
        float oldBest = 9999999.0f;
        //记录迭代次数判断是否2-opt扰动
        int epochNumForOpt = 0;

        for (int i = 1; i <= this.parameter.getDevelopNum(); i++) {
            //选择
            select(list);

            //交叉
            crossover(list);

            //变异
            mutate(list);

            //打印本次迭代信息
            System.out.println("* Iterating " + i + " times: ");
            SpeciesNode thisBest = getBest(list);
            thisBest.printRate();

            //保存本次迭代信息到文件
            outdata.add(i + " " + thisBest.distance);

            //判断是否进行2-opt扰动
            if (Math.abs(oldBest-thisBest.distance)<Constant.DISTURB_THRESHOLD){
                epochNumForOpt++;
                if (epochNumForOpt>Constant.DISTURB_DEVELOP){
                    //进行扰动
                    SpeciesNode point = list.head;
                    while (point.next != null){
                        point.twoOpt(randInt(0,Constant.CITY_NUM-1),randInt(0,Constant.CITY_NUM-1));
                        point = point.next;
                    }
                }
            }else{
                epochNumForOpt = 0;
            }
            oldBest = thisBest.distance;
        }

        return getBest(list);
    }

    //生成指定范围内的随机数
    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    //随机创建初始种群
    void createBeginningSpecies(SpeciesList list) {
        int randomNum = this.parameter.getSpeciesNum();
        for (int i = 1; i <= randomNum; i++) {
            SpeciesNode species = new SpeciesNode();//创建结点
            species.createByRandomGenes();//初始种群基因
//            species.createByGreedyGenes();

            list.add(species);//添加物种
        }
    }

    //计算每一物种被选中的概率
    void calRate(SpeciesList list) {
        //计算总适应度
        float totalFitness = 0.0f;
        list.speciesNum = 0;
        SpeciesNode point = list.head.next;//游标
        while (point != null)//寻找表尾结点
        {
            point.calFitness();//计算适应度

            totalFitness += point.fitness;
            list.speciesNum++;

            point = point.next;
        }

        //计算选中概率
        point = list.head.next;//游标
        while (point != null)//寻找表尾结点
        {
            point.rate = point.fitness / totalFitness;
            point = point.next;
        }
    }

    //选择优秀物种（轮盘赌）
    void select(SpeciesList list) {
        //计算适应度
        calRate(list);

        //找出最大适应度物种
        float talentDis = Float.MAX_VALUE;
        SpeciesNode talentSpecies = null;
        SpeciesNode point = list.head.next;//游标

        while (point != null) {
            if (talentDis > point.distance) {
                talentDis = point.distance;
                talentSpecies = point;
            }
            point = point.next;
        }

        //将最大适应度物种复制talentNum个
        SpeciesList newSpeciesList = new SpeciesList(this.parameter.getSpeciesNum());
        int talentNum = (int) (list.speciesNum * this.parameter.getTalentReserveRate());
        for (int i = 1; i <= talentNum; i++) {
            //复制物种至新表
            SpeciesNode newSpecies = talentSpecies.clone();
            newSpeciesList.add(newSpecies);
        }

        //轮盘赌list.speciesNum-talentNum次
        int roundNum = list.speciesNum - talentNum;
        for (int i = 1; i <= roundNum; i++) {
            //产生0-1的概率
            float rate = (float) Math.random();

            SpeciesNode oldPoint = list.head.next;//游标
            while (oldPoint != null && oldPoint != talentSpecies)//寻找表尾结点
            {
                if (rate <= oldPoint.rate) {
                    SpeciesNode newSpecies = oldPoint.clone();
                    newSpeciesList.add(newSpecies);

                    break;
                } else {
                    rate = rate - oldPoint.rate;
                }
                oldPoint = oldPoint.next;
            }
            if (oldPoint == null || oldPoint == talentSpecies) {
                //复制最后一个
                point = list.head;//游标
                while (point.next != null)//寻找表尾结点
                    point = point.next;
                SpeciesNode newSpecies = point.clone();
                newSpeciesList.add(newSpecies);
            }

        }
        list.head = newSpeciesList.head;
    }

    //交叉操作
    void crossover(SpeciesList list) {
        //以概率pcl~pch进行
        float rate = (float) Math.random();
        if (rate <= this.parameter.getPc()) {
            SpeciesNode point = list.head.next;//游标
            Random rand = new Random();
            int find = rand.nextInt(list.speciesNum);
            while (point != null && find != 0)//寻找表尾结点
            {
                point = point.next;
                find--;
            }

            if (point.next != null) {
                int begin = rand.nextInt(Constant.CITY_NUM);

                //取point和point.next进行交叉，形成新的两个染色体
                for (int i = begin; i < Constant.CITY_NUM; i++) {
                    //找出point.genes中与point.next.genes[i]相等的位置fir
                    //找出point.next.genes中与point.genes[i]相等的位置sec
                    int fir, sec;
                    for (fir = 0; !point.genes[fir].equals(point.next.genes[i]); fir++) ;
                    for (sec = 0; !point.next.genes[sec].equals(point.genes[i]); sec++) ;
                    //两个基因互换
                    String tmp;
                    tmp = point.genes[i];
                    point.genes[i] = point.next.genes[i];
                    point.next.genes[i] = tmp;

                    //消去互换后重复的那个基因
                    point.genes[fir] = point.next.genes[i];
                    point.next.genes[sec] = point.genes[i];

                }
            }
        }
    }

    //变异操作
    void mutate(SpeciesList list) {
        //每一物种均有变异的机会,以概率pm进行
        SpeciesNode point = list.head.next;
        while (point != null) {
            float rate = (float) Math.random();
            if (rate < this.parameter.getPm()) {
                //寻找逆转左右端点
                Random rand = new Random();
                int left = rand.nextInt(Constant.CITY_NUM);
                int right = rand.nextInt(Constant.CITY_NUM);
                if (left > right) {
                    int tmp;
                    tmp = left;
                    left = right;
                    right = tmp;
                }

                //逆转left-right下标元素
                while (left < right) {
                    String tmp;
                    tmp = point.genes[left];
                    point.genes[left] = point.genes[right];
                    point.genes[right] = tmp;

                    left++;
                    right--;
                }
            }
            point = point.next;
        }
    }

    //获得适应度最大的物种
    SpeciesNode getBest(SpeciesList list) {
        float distance = Float.MAX_VALUE;
        SpeciesNode bestSpecies = null;
        SpeciesNode point = list.head.next;//游标
        while (point != null)//寻找表尾结点
        {
            if (distance > point.distance) {
                bestSpecies = point;
                distance = point.distance;
            }

            point = point.next;
        }

        return bestSpecies;
    }
}
