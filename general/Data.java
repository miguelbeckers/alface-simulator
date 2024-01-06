package general;

public class Data {
    private double intensity;
    private double lane;
    private double position;

    public Data(double intensity, double lane, double position){
        this.intensity = intensity;
        this.lane = lane;
        this.position = position;
    }

    public double getIntensity(){
        return intensity;
    }

    public double getLane(){
        return lane;
    }

    public double getPosition(){
        return position;
    }
}
