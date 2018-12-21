package GeneticTSP;

/**
 * Description: 参数类
 * Author: Quan Tang
 * Date: 2018/12/21
 **/
public class Parameter {
    // 序列号
    private String serialNumber;
    // 种群大小
    private int speciesNum;
    // 进化代数
    private int developNum;
    // 交叉概率
    private float pc;
    // 变异概率
    private float pm;
    //精英复制的个数占种群数量的比例
    private float talentReserveRate;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getSpeciesNum() {
        return speciesNum;
    }

    public void setSpeciesNum(int speciesNum) {
        this.speciesNum = speciesNum;
    }

    public int getDevelopNum() {
        return developNum;
    }

    public void setDevelopNum(int developNUm) {
        this.developNum = developNUm;
    }

    public float getPc() {
        return pc;
    }

    public void setPc(float pc) {
        this.pc = pc;
    }

    public float getPm() {
        return pm;
    }

    public void setPm(float pm) {
        this.pm = pm;
    }

    public float getTalentReserveRate() {
        return talentReserveRate;
    }

    public void setTalentReserveRate(float talentReserveRate) {
        this.talentReserveRate = talentReserveRate;
    }
}
