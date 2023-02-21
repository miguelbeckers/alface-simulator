package algorithms.Immunological;

import java.util.List;
import java.util.ArrayList;

public class Antibody implements Comparable<Antibody>, Cloneable{
    private List<Integer> lightChain = new ArrayList<>();
    private Integer affinity;

    public Antibody(int arrayWidth){
        arrayWidth *= arrayWidth;

        for (int i = 0; i < arrayWidth; i++) {
            double randomValue = (double) Math.round(Math.random() * 100) / 100;
            lightChain.add(randomValue > 0.5 ? 1 : 0);
        }
    }

    public void print(int arrayWidth){
        int count = 0;
        for (Integer integer : lightChain) {
            System.out.print(integer == 0 ? ":" : "█");
            if (++count == arrayWidth) {
                System.out.print("\n");
                count = 0;
            }
        }
    }

    public void fitness(List<Integer> reference) {
        int sum = 0;
        for (int i = 0; i < reference.size(); i++) {
            if (reference.get(i) == lightChain.get(i)) {
                sum += 1;
            }
        }
        this.affinity = sum;
    }

    public void setLightChain(List<Integer> lightChain){
        this.lightChain = lightChain;
    }

    public Integer getAffinity(){
        return affinity;
    }

    public List<Integer> getLightChain(){
        return lightChain;
    }

    @Override
    public int compareTo(Antibody antibody) {
        return antibody.getAffinity().compareTo(this.getAffinity());
    }


    @Override
    public Antibody clone() {
        try {
            Antibody clone = (Antibody) super.clone();
            clone.setLightChain(new ArrayList<>(lightChain));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
