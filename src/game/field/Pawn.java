package game.field;

import game.utils.GameConstValue;

public class Pawn{
    private static int staticWhitePawnId = 1;
    private static int staticBlackPawnId = 1;

    private String color;
    private int pawnId;
    private int fieldId;

    public Pawn(GameConstValue gameConstValue, int fieldId) {
       switch (gameConstValue) {
           case pawnWhite:
               this.color = "W";
               this.pawnId = Pawn.staticWhitePawnId;
               staticWhitePawnId++;
               break;
           case pawnBlack:
               this.color = "B";
               this.pawnId = Pawn.staticBlackPawnId;
               staticBlackPawnId++;
               break;
           default:
               System.out.println("Incorect type of pawn");
       }
       this.fieldId = fieldId;
    }
    public int getPawnId() {return this.pawnId;}
    public int getFieldId() {return this.fieldId;}
    public void setFieldId(int fieldId) { this.fieldId = fieldId;}
    public String getColor() {return this.color;}

    @Override
    public String toString() {
        return String.valueOf("Pawn: " + this.color + " " + this.pawnId + " " + fieldId);
    }
}
