package Oka.model;

public class Bamboo {
    //region==========ATTRIBUTES===========
    private Enums.Color color;
    //endregion

    //region==========CONSTRUCTORS=========
    public Bamboo(Enums.Color color) {
        this.color = color;
    }
    //endregion

    //region==========GETTER/SETTER========
    public Enums.Color getColor() {
        return color;
    }
    //endregion
}
