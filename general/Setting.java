package general;

public class Setting {
    private Long id;
    private String name;
    private Boolean value;


    public Setting(Long id, String name, Boolean value){
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String toString(){
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

        return value ? name + ": on" : RED + name + ": off" + RESET;
    }
}
