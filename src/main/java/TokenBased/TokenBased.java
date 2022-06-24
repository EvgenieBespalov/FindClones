package TokenBased;

import Lexers.ITokenType;
import Lexers.RegexTokenizer;
import Lexers.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

public class TokenBased {

    public static final String ANSI_RESET = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";

    public class CodeOfProgram{
        public ArrayList<Integer> uniqlueGrams,
                placeTokens = new ArrayList<>(),            //расположения токенов в коде
                allTokens = new ArrayList<>();              //все добавленные токены
        public ArrayList<Token> program = new ArrayList<>();//текст программы

        CodeOfProgram(String codeOfProgram){
            RegexTokenizer tokenizer = new RegexTokenizer(codeOfProgram, tokenTypes);
            int order = 0;

            while(tokenizer.hasMoreElements()) {
                Token token = tokenizer.nextElement();
                token.setStart(order);
                program.add(token);

                if(token.getType() == tokenTypes[0]){//keyword
                    allTokens.add(1);
                    placeTokens.add(order);

                }
                else
                    if(token.getType() == tokenTypes[1]){//type
                        allTokens.add(1);
                        placeTokens.add(order);
                    }
                else
                if(token.getType() == tokenTypes[2]){//id
                    allTokens.add(3);
                    placeTokens.add(order);
                }
                else
                if(token.getType() == tokenTypes[3] || token.getType() == tokenTypes[4] || token.getType() == tokenTypes[5] || token.getType() == tokenTypes[6]) {//int
                    allTokens.add(4);
                    placeTokens.add(order);
                }
                else
                if(token.getType() == tokenTypes[7] || token.getType() == tokenTypes[8] || token.getType() == tokenTypes[9] || token.getType() == tokenTypes[10]){ //float
                    allTokens.add(5);
                    placeTokens.add(order);
                }
                else
                if(token.getType() == tokenTypes[11]) {//logic
                    allTokens.add(6);
                    placeTokens.add(order);
                }
                else
                if(token.getType() == tokenTypes[12]) {// string
                    allTokens.add(7);
                    placeTokens.add(order);
                }
                else
                if(token.getType() == tokenTypes[13]) {//char
                    allTokens.add(8);
                    placeTokens.add(order);
                }
                else
                if(token.getType() == tokenTypes[16] || token.getType() == tokenTypes[17] || token.getType() == tokenTypes[18]) {//operators
                    allTokens.add(9);
                    placeTokens.add(order);
                }
                order++;
            }

            LinkedHashSet<Integer> programGrams = new LinkedHashSet();
            int bit = 1000, //размер N-граммы, 1000 = 4 символам
                    gram = 0;
            for (int i = 0; i < allTokens.size(); i++){
                gram = gram + allTokens.get(i)*bit;
                bit/=10;
                if(bit == 0){
                    programGrams.add(gram);
                    bit = 1000;
                    gram = 0;
                    i -= 3;
                }
            }
            uniqlueGrams = new ArrayList(programGrams);
        }
    }

    CodeOfProgram newProgram, expectedClone;
    private final ITokenType[] tokenTypes;

    public TokenBased(ITokenType[] tokenTypes){
        this.tokenTypes = tokenTypes;
    }

    ArrayList<Integer> clonesFromOneProgram = new ArrayList(), clonesFromTwoProgram = new ArrayList();
    private void searchClonedAreas(){
        ArrayList<Integer> oneProgram = new ArrayList<>(newProgram.allTokens), twoProgram = new ArrayList<>(expectedClone.allTokens);
        int sizeString = 4, max, endI = 0, endJ = 0;
        int[][] table = new int[oneProgram.size()+1][twoProgram.size()+1];

        for(int i = 0; i <= oneProgram.size(); i++){
            table[i][0] = 0;
        }
        for(int i = 0; i <= twoProgram.size(); i++){
            table[0][i] = 0;
        }

        clonesFromOneProgram.add(0);
        clonesFromTwoProgram.add(0);
        do{
            for(int i = 1; i <= oneProgram.size(); i++){
                for(int j = 1; j <= twoProgram.size(); j++){
                    table[i][j] = 0;
                }
            }

            for(int i=1; i <= oneProgram.size(); i++){
                for(int j = 1; j <= twoProgram.size(); j++){
                    if(oneProgram.get(i-1) != -1 && twoProgram.get(j-1) != -1 && oneProgram.get(i-1) == twoProgram.get(j-1)){
                        table[i][j] = table[i-1][j-1]+1;
                    }
                    else {
                        table[i][j] = 0;
                    }
                }
            }

            max = sizeString;
            for(int i=1; i <= oneProgram.size(); i++){
                for(int j = 1; j <= twoProgram.size(); j++){
                    if(table[i][j]>max) {
                        max = table[i][j];
                        endI = i;
                        endJ = j;
                    }
                }
            }

            if(max>sizeString){
                for(int i = endI-max; i<endI; i++)
                    oneProgram.set(i, -1);
                for(int j = endJ-max; j<endJ; j++)
                    twoProgram.set(j, -1);

                clonesFromOneProgram.add(newProgram.placeTokens.get(endI-max));
                clonesFromOneProgram.add(newProgram.placeTokens.get(endI-1));
                clonesFromTwoProgram.add(expectedClone.placeTokens.get(endJ-max));
                clonesFromTwoProgram.add(expectedClone.placeTokens.get(endJ-1));
            }
        }while(max>sizeString);
        clonesFromOneProgram.add(newProgram.program.size()-2);
        clonesFromTwoProgram.add(expectedClone.program.size()-2);

        Collections.sort(clonesFromOneProgram);
        Collections.sort(clonesFromTwoProgram);
    }

    public void printFirstProgram(){
        searchClonedAreas();
        ArrayList<Integer> clonesFromProgram = clonesFromOneProgram;
        Boolean black = true;
        for(int n = 0, i = 0; n < clonesFromProgram.size()-1; n++){
            for(; clonesFromProgram.get(n) <= newProgram.program.get(i).getStart() && newProgram.program.get(i).getStart() <= clonesFromProgram.get(n+1); i++){
                if(black == false) {
                    System.out.print(ANSI_RED + newProgram.program.get(i).getText());
                }
                else {
                    System.out.print(ANSI_RESET + newProgram.program.get(i).getText());
                }
            }

            if(black == false) {
                black = true;
            }
            else {
                black = false;
            }
        }
    }
    public void printSecondProgram(){
        ArrayList<Integer> clonesFromProgram = clonesFromTwoProgram;
        Boolean black = true;
        for(int n = 0, i = 0; n < clonesFromProgram.size()-1; n++){
            for(; clonesFromProgram.get(n) <= expectedClone.program.get(i).getStart() && expectedClone.program.get(i).getStart() <= clonesFromProgram.get(n+1); i++){
                if(black == false) {
                    System.out.print(ANSI_RED + expectedClone.program.get(i).getText() + ANSI_RESET);
                }
                else {
                    System.out.print(expectedClone.program.get(i).getText());
                }
            }

            if(black == false) {
                black = true;
            }
            else {
                black = false;
            }
        }
    }

    public Boolean searchClones(){
        float intersection = 0, unity = newProgram.uniqlueGrams.size()+expectedClone.uniqlueGrams.size();
        for(int i=0; i<newProgram.uniqlueGrams.size(); i++){
            for(int j=0; j<expectedClone.uniqlueGrams.size(); j++){
                if(newProgram.uniqlueGrams.get(i).equals(expectedClone.uniqlueGrams.get(j))){
                    intersection++;
                    unity--;
                }
            }
        }

        //searchClonedAreas();

        if(intersection/unity > 0.80)
            return true;
        else
            return false;
    }

    public CodeOfProgram getNewProgram() {
        return newProgram;
    }
    public CodeOfProgram getExpectedClone() {
        return expectedClone;
    }

    public void setNewProgram(String newProgram) {
        this.newProgram = new CodeOfProgram(newProgram);
    }
    public void setExpectedClone(String expectedClone) {
        this.expectedClone = new CodeOfProgram(expectedClone);
    }
}
