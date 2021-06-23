package game;

import game.field.Field;
import game.field.Pawn;
import game.utils.GameConstValue;
import game.utils.GameError;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Field[][] chessBoard = new Field[GameConstValue.numberOfFields.getValue()][GameConstValue.numberOfFields.getValue()];
    private ArrayList<Pawn> whitePawns;
    private ArrayList<Pawn> blackPawns;
    private ArrayList<Integer> fieldsChessboard;
    private ArrayList<Integer> freeFieldForPawnList;

    private Scanner scanner;
    private String input;
    private int options;
    private Pawn actualPawn;
    private GameConstValue actualTurn;

    //private GameConstValue actualPlayerActivity;

    public Game() {
        scanner = new Scanner(System.in);
        whitePawns = new ArrayList<>();
        blackPawns = new ArrayList<>();
        fieldsChessboard = new ArrayList<>();
        freeFieldForPawnList = new ArrayList<Integer>();
        actualTurn = GameConstValue.whiteTurn;
        actualPawn = null;

        this.initialChessBoard();
    }

    private void addPawnToList(GameConstValue gameConstValue, Pawn pawn) {
        switch (gameConstValue) {
            case pawnBlack:
                blackPawns.add(pawn);
                break;
            case pawnWhite:
                whitePawns.add(pawn);
                break;
            default:
                this.writeMessage(GameConstValue.errorAddPawnToList);
        }
    }
    private void deletePawnFromList(GameConstValue gameConstValue, int idPawn) {
        switch (gameConstValue) {
            case pawnBlack:
                blackPawns.remove(idPawn);
                break;
            case pawnWhite:
                whitePawns.remove(idPawn);
                break;
            default:
                this.writeMessage(GameConstValue.errorRemovePawnFromList);
        }
    }

    private void movePawn() {
        System.out.println(options);
        int oldField = actualPawn.getFieldId();
        for (int i = 0; i< chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                if (chessBoard[i][j].getId() == options) {
                    actualPawn.setFieldId(options);
                    chessBoard[i][j].setPawn(actualPawn);

                }
                if (chessBoard[i][j].getId() == oldField) {
                    chessBoard[i][j].setPawn(null);
                }
            }
        }
        actualPawn = null;
        freeFieldForPawnList.clear();
    }

    private void validateValueOptionsofField() {
        System.out.println(freeFieldForPawnList);

        if (freeFieldForPawnList.contains(options)) {
            this.movePawn();
        } else {
            writeMessage(GameConstValue.errorChooseFreeField);
        }
    }
    private void isEmptyField() {
        for(Field row[]: this.chessBoard) {
            for(Field f: row) {
                if (freeFieldForPawnList.contains(f.getId()) && f.getPawn() != null) {
                    freeFieldForPawnList.remove(freeFieldForPawnList.indexOf(f.getId()));
                }
            }
        }
    }
    private void setFreeFieldForPawn() {
        int row = (actualPawn.getFieldId()/10);
        int col = actualPawn.getFieldId() % 10;
        int id;
        Integer nextRow;

        switch (actualTurn) {
            case whiteTurn:
                    nextRow = (row > 0) ? (--row)*10 : null;
                    if (col >= 1 && col <= 8) {
                        freeFieldForPawnList.add(nextRow + (col-1));
                        freeFieldForPawnList.add(nextRow + (col+1));
                    } else {
                        freeFieldForPawnList.add(nextRow + ((col < 0) ? --col : ++col));
                    }
                    isEmptyField();
                break;
            case blackTurn:
                break;
            default:
                writeMessage(GameConstValue.errorTurnOrChoosePawn);
        }
    }

    public void runGame() {
        this.writeMessage(actualTurn);
        this.writeMessage(GameConstValue.pawnSelection);
        this.inputGameControl(GameConstValue.pawnSelection);

        if (actualPawn != null) {
            this.writeMessage(GameConstValue.writeActualPawn);

            this.setFreeFieldForPawn();

            this.drawChessBoard();
            this.writeMessage(GameConstValue.fieldSelection);
            this.inputGameControl(GameConstValue.fieldSelection);
            this.changeTurn();
        }
    }

    private void changeTurn() {
        if (actualTurn == GameConstValue.whiteTurn) {
            actualTurn = GameConstValue.blackTurn;
        } else {
            actualTurn = GameConstValue.whiteTurn;
        }
    }

    private void initalPawns(int id, int i, int j) {
        Pawn pawn;
        if (id < 20) {
            pawn = new Pawn(GameConstValue.pawnBlack, id);
            this.addPawnToList(GameConstValue.pawnBlack, pawn);
            chessBoard[i][j].setPawn(pawn);
        }
        if (id >= 80 && id < 100) {
            pawn = new Pawn(GameConstValue.pawnWhite, id);
            this.addPawnToList(GameConstValue.pawnWhite, pawn);
            chessBoard[i][j].setPawn(pawn);
        }
    }

    //sprawdzanie typu
    private void validateTypeOptions() {
        try {
            this.options = Integer.parseInt(this.input);
        } catch (NumberFormatException e) {
            System.out.println("Error - enter an integer value.");
        }
    }

    //Zwraca aktualną grupę pionków, wedle tury
    private ArrayList<Pawn> returnActualSetPawns() {
        ArrayList<Pawn> tmpPawnList = null;
        switch (actualTurn) {
            case blackTurn:
                tmpPawnList = blackPawns;
                break;
            case whiteTurn:
                tmpPawnList = whitePawns;
                break;
            default:
                writeMessage(GameConstValue.errorTurnOrChoosePawn);
        }
        return tmpPawnList;
    }

    //"""Funkcja sprawdza czy podana wartość/wybrany przez gracza pionek
    // znajduje się w liście/rejestrze."""
    private boolean validateValueOptionsofPawnId(int id) {
        ArrayList<Pawn> actualPawnList = returnActualSetPawns();

        for(Pawn p: actualPawnList) {
            if (p.getPawnId() == id) {
                return true;
            }
        }
        return false;
    }

    //po wybraniu dostępnego pionka, jego instancja jest wiązana z zmienną actualPawn
    private void setActualPawn(int pawnId) {
        ArrayList<Pawn> actualPawnList = returnActualSetPawns();

        for(Pawn p: actualPawnList) {
            if (p.getPawnId() == pawnId) {
                actualPawn = p;
            }
        }
    }

    private void validateValueOptionsofPawn() {
        if ( validateValueOptionsofPawnId(this.options)) {
            GameError.errorOfValue = true;
            this.setActualPawn(this.options);
        } else {
            System.out.println("Error - you choose unidentfied pawn");
            GameError.errorOfValue = false;
        }
    }

    private void inputGameControl(GameConstValue gameConstValue) {
        switch (gameConstValue) {
            case pawnSelection:
                input = scanner.nextLine();
                this.validateTypeOptions();
                this.validateValueOptionsofPawn();
                break;
            case fieldSelection:
                input = scanner.nextLine();
                this.validateTypeOptions();
                this.validateValueOptionsofField();
            default:
                System.out.println("Incorect input");
        }
    }
    public void initialChessBoard() {
        int id = 0;
        int offSet = 0;

        for (int i = 0; i< chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                chessBoard[i][j] = new Field(id);
                id++;
            }
        }
        id = 0;
        for (int i = 0; i < this.chessBoard.length; i++) {
            for (int j = 0; j < this.chessBoard[i].length; j++) {
                if ((id + offSet) % 2 == 0) {
                    chessBoard[i][j].setField("-------");
                    this.initalPawns(id, i, j);
                    this.fieldsChessboard.add(id);
                } else {
                    chessBoard[i][j].setField("#######");
                }
                id++;
            }
            offSet++;
        }
    }

    private String actualTurnPawnId(Field field) {
        Pawn pawn = field.getPawn();

        if (actualTurn==GameConstValue.whiteTurn && pawn.getColor().equals("W")) {
            return String.valueOf(field.getPawnId());
        } else if (actualTurn==GameConstValue.blackTurn && pawn.getColor().equals("B")) {
            return String.valueOf(field.getPawnId());
        } else {
            return "--";
        }
    }

    public void drawChessBoard() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int q = 0; q<3; q++) {
                for (int j = 0; j < chessBoard[i].length; j++) {
                    Pawn pawn = chessBoard[i][j].getPawn();
                    if (pawn != null) {
                        if ( pawn == actualPawn) {
                            if (q == 0 || q == 2) {
                                System.out.printf("-*****-");
                            } else {
                                System.out.printf("-*" + "%s" + "%2s" + "*-",this.chessBoard[i][j].getPawnColor(),this.actualTurnPawnId(this.chessBoard[i][j]));
                            }
                        } else {
                            if ( q == 1) {
                                System.out.printf("--" + "%s" + "%2s" + "--",this.chessBoard[i][j].getPawnColor(),this.actualTurnPawnId(this.chessBoard[i][j]));
                            } else {
                                System.out.printf("%5s", this.chessBoard[i][j].getField());
                            }
                        }
                    } else {
                        if (q == 1 && freeFieldForPawnList.contains(chessBoard[i][j].getId())){
                            System.out.printf("---%2d--", this.chessBoard[i][j].getId());
                        } else {
                            System.out.printf("%5s", this.chessBoard[i][j].getField());
                        }

                    }
                }System.out.println();
            }
        }
    }

    private void writeMessage(GameConstValue gameConstValue) {
        switch(gameConstValue) {
            case pawnSelection:
                System.out.print("Wyznacz figurę: ");
                break;
            case fieldSelection:
                System.out.print("Wyznacz pole dla figury: ");
                break;
            case errorAddPawnToList:
                System.out.println("Błąd dodania pionka do listy: ");
                break;
            case errorRemovePawnFromList:
                System.out.println("Błąd usunięcia pionka z listy: ");
                break;
            case errorTurnOrChoosePawn:
                System.out.println("Błąd wyznaczenia pionka ");
                break;
            case errorChooseFreeField:
                System.out.println("Błąd wyboru wolnego pola! ");
                break;
            case writePawnsList:
                System.out.println("Black Pawns on board: " + blackPawns);
                System.out.println("White Pawns on board: " + whitePawns);
                break;
            case writeFieldList:
                System.out.println("Field list: " + fieldsChessboard);
                break;
            case writeActualPawn:
                System.out.println("Actual pawn: " + actualPawn);
                break;
            case whiteTurn:
                System.out.println("Actual turn: WHITE");
                break;
            case blackTurn:
                System.out.println("Actual turn: BLACK");
                break;
            default:
                System.out.println("Incorect enum");
        }
    }

}
