package Enum;

public enum ResultKey {

    CODE("code"),MSG("msg");

    private String str;

    ResultKey(String str){
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
