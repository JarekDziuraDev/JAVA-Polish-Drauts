package game.field;

import game.utils.GameConstValue;

public class Field {

    private Pawn pawn;
    private String type;
    private int id;

    public Field(int id){
        this.id = id;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }
    public Pawn getPawn() {
        return this.pawn;
    }
    public Pawn getPawnWithId(int id) {
        if (this.pawn.getPawnId() == id) {
            return getPawn();
        } else {
            return null;
        }
    }
    public String getPawnColor() {
        return pawn.getColor();
    }
    public int getPawnId() {
        return pawn.getPawnId();
    }

    public String getField() {
        return this.type;
    }
    public void setField(String type) {
        this.type = type;
    }

    public int getId() {return this.id;}
    public String getStrId() {
        if ( this.type.equals("---") )
            return "";
        else
            return String.valueOf(this.id);

    }
    public void setId(int id) {this.id = id;}

}
