package sourceCode;

public class Commision {
    private String PresetName;
    private double percentage;

    public String getPresetName() {
        return PresetName;
    }

    public double getPercentage() {
        return percentage;
    }

    public Commision(String PresetName, double percentage) {
        this.PresetName = PresetName;
        this.percentage = percentage;
    }
    
    
}
