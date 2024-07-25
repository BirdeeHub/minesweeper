package MySweep;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

class ScoresFileIO{//reads from file, creates scoreEntry instances based on the file contents. ScoresWindow uses read and delete, maingame uses update. write is private
    private static final String scoresFileNameWindows = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "minesweeperScores" + File.separator + "MineSweeperScores.txt";
    private static final String scoresFileNameOther = System.getProperty("user.home") + File.separator + ".local" + File.separator + "share" + File.separator + "minesweeper" + File.separator + "MineSweeperScores.txt";
    private static final String scoresFileName = (System.getProperty("os.name").toLowerCase().contains("win"))?scoresFileNameWindows:scoresFileNameOther;
    
    
    //----------------------------------WRITE------------------------------------------------------WRITE-------------------------------------------
    private static void writeLeaderboard(ScoreEntry[] allEntries, boolean append){
        StringBuilder scoresFileString = new StringBuilder();
        if(append)scoresFileString.append(" ");
        for(int i = 0; i < allEntries.length; i++){
            scoresFileString.append(allEntries[i].toString()).append(" ");
        }
        try {
            Files.createDirectories(Path.of(scoresFileName).getParent());
        } catch (IOException e) {e.printStackTrace();}
        try{
            Files.createFile(Path.of(scoresFileName));
        }catch(IOException e){
            if(!(e instanceof FileAlreadyExistsException)){
                e.printStackTrace();
            }
        }
        try (FileWriter out2 = new FileWriter(scoresFileName, append)) {
            out2.write(scoresFileString.toString());
        }catch(IOException e){e.printStackTrace();}
    }
    //-----------------------------------READ-------------------------------------READ----------------------------------------------------------------
    public static ScoreEntry[] readLeaderboard(){
        ArrayList<ScoreEntry> fileEntriesBuilder = new ArrayList<>();
        ScoreEntry[] fileEntries;
        try(Scanner in = new Scanner(new File(scoresFileName))) {
            while (in.hasNext()) {
                ScoreEntry currentEntry = new ScoreEntry(in.next());
                if(currentEntry.isValid()){
                    fileEntriesBuilder.add(currentEntry);
                }
            }
            fileEntries = fileEntriesBuilder.toArray(new ScoreEntry[0]);
        }catch(FileNotFoundException e){
            fileEntries=null; 
            e.printStackTrace();
        }
        return fileEntries;
    }
    //---------------------------------Everything below here uses only ScoreEntries to do its work---and uses read and write---------------
    public static void deleteScoreEntry(ScoreEntry thisEntry){
        ScoreEntry[] deletries = readLeaderboard();
        ArrayList<ScoreEntry> newFileBuilder = new ArrayList<>();
        if(deletries!=null){
            int c = 0;
            while(c<deletries.length){
                if(thisEntry.isValid()){//<-- only write back valid entries
                    if(!(deletries[c].equals(thisEntry) && thisEntry.getRemainingLives()==deletries[c].getRemainingLives() && thisEntry.getTime()==deletries[c].getTime())){
                        newFileBuilder.add(deletries[c]);//only add back entries that arent the exact entry in thisEntry (equals() only finds if same board)
                    }
                }
                c++;
            }
            deletries = newFileBuilder.toArray(new ScoreEntry[0]);
            writeLeaderboard(deletries, false);// <-- overwrite with new
        }
    }//-------------------------------------------------------------------------update score entry-------------------------------------------
    public static int updateScoreEntry(boolean won, long time, int cellsExploded, int Fieldx, int Fieldy, int bombCount, int lives){
        //Writes new scores to score file, returns highscore/new_board/normal index for assigning win/loss message
        int RemainingLives= Math.max(0, lives-cellsExploded);
        ScoreEntry thisEntry = new ScoreEntry(Fieldx,Fieldy,bombCount,lives,RemainingLives,time);
        if(thisEntry.isValid()){
            ScoreEntry[] entries = readLeaderboard();//<-- get our score entries
            if(entries == null){//<-- file not found
                entries = new ScoreEntry[1];
                entries[0] = thisEntry;
                writeLeaderboard(entries, false);
                return 1;//<-- score not found index
            }else{
                if(0==entries.length){//<-- file found but empty. Writing.
                    entries = new ScoreEntry[1];
                    entries[0] = thisEntry;
                    writeLeaderboard(entries, false);
                    return 1;
                }else{//<---------------------- file found and not empty
                    boolean thisScoreFound=false;
                    boolean isHighscore=false;
                    for(int c = 0;c<entries.length;c++){//loop through entries in file
                        if(entries[c].isValid() && entries[c].equals(thisEntry)){//<-- board identifier matches
                            thisScoreFound=true;
                            if(won && entries[c].getTime()>time){
                                entries[c]=thisEntry;//                         ^did you beat the time?
                                isHighscore=true;
                            }else if(won && entries[c].getRemainingLives()>RemainingLives && entries[c].getTime()==time){
                                entries[c]=thisEntry;//                         ^is it same time but more lives?
                                isHighscore=true;
                            }else if(won && entries[c].getRemainingLives()<1){//was the entry created by dying on a new board configuration?
                                entries[c]=thisEntry;
                                isHighscore=true;
                            }
                        }
                    }
                    if(!thisScoreFound){//none were a match. New Board Size
                        ScoreEntry[] newEntries = new ScoreEntry[1];
                        newEntries[0] = thisEntry;
                        writeLeaderboard(newEntries, true);
                        return 1;
                    }
                    if(isHighscore){//Was a high score! save edited version of file
                        writeLeaderboard(entries, false);
                        return 2;
                    }
                }
            }
        }
        return 0;//<-- board size was found, score was not better.
    }
}
