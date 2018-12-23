package GeneticTSP;

import java.util.Random;

/**
 * Description: 个体节点：基因序列、路径长度、适应度等
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class SpeciesNode {
    String[] genes;         // 基因序列
    float distance;         // 路程
    float fitness;          // 适应度
    SpeciesNode next;
    float rate;             // 轮盘赌概率

    SpeciesNode() {
        //初始化
        this.genes = new String[Constant.CITY_NUM];
        this.fitness = 0.0f;
        this.distance = 0.0f;
        this.next = null;
        rate = 0.0f;
    }

    //随机初始化个体的基因序列
    void createByRandomGenes() {
        //顺序初始化基因序列
        for (int i = 0; i < genes.length; i++) {
            genes[i] = Integer.toString(i + 1);
        }

        //获取随机种子
        Random rand = new Random();

        for (int j = 0; j < genes.length; j++) {
            int num = j + rand.nextInt(genes.length - j);

            //交换
            String tmp;
            tmp = genes[num];
            genes[num] = genes[j];
            genes[j] = tmp;
        }
    }

    //初始物种基因(贪婪)
    void createByGreedyGenes() {
        Random rand = new Random();
        int i = rand.nextInt(Constant.CITY_NUM); //随机产生一个城市作为起点
        genes[0] = Integer.toString(i + 1);
        int j;//终点
        int cityNum = 0;
        do {
            cityNum++;

            //选出单源最短城市
            float minDis = Integer.MAX_VALUE;
            int minCity = 0;
            for (j = 0; j < Constant.CITY_NUM; j++) {
                if (j != i) {
                    //判是否和已有重复
                    boolean repeat = false;
                    for (int n = 0; n < cityNum; n++) {
                        if (Integer.parseInt(genes[n]) == j + 1) {
                            repeat = true;//重了
                            break;
                        }
                    }
                    if (repeat == false)//没重
                    {
                        //判长度
                        if (Constant.disMap[i][j] < minDis) {
                            minDis = Constant.disMap[i][j];
                            minCity = j;
                        }
                    }
                }
            }

            //加入到染色体
            genes[cityNum] = Integer.toString(minCity + 1);
            i = minCity;
        } while (cityNum < Constant.CITY_NUM - 1);
    }

    //计算物种适应度
    void calFitness() {
        float totalDis = 0.0f;
        for (int i = 0; i < Constant.CITY_NUM; i++) {
            int curCity = Integer.parseInt(this.genes[i]) - 1;
            int nextCity = Integer.parseInt(this.genes[(i + 1) % Constant.CITY_NUM]) - 1;

            totalDis += Constant.disMap[curCity][nextCity];
        }

        this.distance = totalDis;
        this.fitness = 1.0f / totalDis;
    }

    //深拷贝
    public SpeciesNode clone() {
        SpeciesNode species = new SpeciesNode();

        //复制值
        for (int i = 0; i < this.genes.length; i++)
            species.genes[i] = this.genes[i];
        species.distance = this.distance;
        species.fitness = this.fitness;

        return species;
    }

    //打印路径
    void printRate() {
        System.out.print("Shortest path: ");
        for (int i = 0; i < genes.length; i++)
            System.out.print(genes[i] + "->");
        System.out.print(genes[0] + "\n");
        System.out.println("Shortest distance: " + distance);
    }
}
