package sourceCode;

public class Commision {
    private String PresetName;
    private double percentage;

    public String getPresetName() {
        return PresetName;
    }

    public void setPresetName(String PresetName) {
        this.PresetName = PresetName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public Commision(String PresetName, double percentage) {
        this.PresetName = PresetName;
        this.percentage = percentage;
    }
    
    
}
