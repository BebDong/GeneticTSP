package GeneticTSP;

/**
 * Description: 种群链表，链接个体形成种群
 * Author: Quan Tang
 * Date: 2018/12/20
 **/
public class SpeciesList {
    SpeciesNode head;//头结点
    int speciesNum;//种群大小

    SpeciesList() {
        head = new SpeciesNode();
        speciesNum = Constant.SPECIES_NUM;
    }

    //添加物种
    void add(SpeciesNode species) {
        SpeciesNode point = head;//游标
        while (point.next != null)//寻找表尾结点
            point = point.next;
        point.next = species;
    }
}
