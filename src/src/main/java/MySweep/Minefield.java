package MySweep;
import java.util.TimerTask;
import java.util.Timer;

class Minefield{//a data class for Grid. contains and manages all mutable game-state variables, it is more disposable than grid to allow easy reset of timer and board
    private boolean[][] cell, chkd, mrk, exp, qstn;    //initialization of variables
    private int[][] adj;
    private int Fieldx, Fieldy, bombCount;
    private int totalExploded = 0;
    private int totalChecked = 0;
    private int totalMarked = 0;
    private boolean firstClick = true;
    private boolean GameOver = false;
    private long time;  // Create Timer, which is started in doFirstClick();
    private long startTime;
    private final Timer currentTimeTimer = new Timer();
    private TimerTask timeTask = new TimerTask() {
        public void run() {
            time = System.currentTimeMillis()-startTime;//dont add a time format here. This gets saved to scores file
        }//                                                                           It may not compare for highscore correctly after doing that
    };
    //----------Constructor---------------------------------
    Minefield(int w, int h, int bombCount){
        Fieldx=w; 
        Fieldy=h; 
        this.bombCount=bombCount;
        cell = new boolean[Fieldx][Fieldy];
        adj = new int[Fieldx][Fieldy];
        chkd  = new boolean[Fieldx][Fieldy];
        mrk  = new boolean[Fieldx][Fieldy];
        exp  = new boolean[Fieldx][Fieldy];
        qstn = new boolean[Fieldx][Fieldy];
    }//----------Reset Function-----------MANDATORY---------------Reset Function------------------Reset Function-------------
    void reset(int a, int b){//THIS MUST BE CALLED BEFORE REFERENCING MINEFIELD CLASS IF YOU WANT BOMBS OR ADJ COUNTS
        //This cannot place a bomb that would cause AdjCount(a,b) to become >0 unless that is impossible
        totalMarked = 0;//initialize variables
        totalChecked = 0;
        totalExploded = 0;
        GameOver = false;
        for(int j=0;j<Fieldx;j++)for(int k=0;k<Fieldy;k++){
            chkd[j][k]=false;
            mrk[j][k]=false;
            exp[j][k]=false;
            cell[j][k]=false;
            qstn[j][k]=false;
        }
        if(Fieldx*Fieldy<=bombCount){//bombs>=cells. set all as bombs then proceed with the inevitable.
            for(int j=0;j<Fieldx;j++)for(int k=0;k<Fieldy;k++){
                cell[j][k]=true;
            }
        } else {//if not dead on arrival and
            int i=0;
            if(((Fieldx*Fieldy-bombCount)>8)||//field has 9 non-bomb cells,
            ((5<(Fieldx*Fieldy-bombCount))&&(a==0||a==(Fieldx-1)||b==0||b==(Fieldy-1)))||//or clicked on an edge and 6 non-bomb cells,
            ((3<(Fieldx*Fieldy-bombCount))&&((a==0 && b==(Fieldy-1))||(b==0 && a==(Fieldx-1))||(a==0 && b==0)||(b==(Fieldy-1) && a==(Fieldx-1))))){
                while(i<bombCount){                                                                    //^^or clicked a corner and 4 non-bomb cells
                    int Randx=(int)Math.round(Math.random()*(Fieldx-1));//place a bomb in a random cell unless occupied or is too close 
                    int Randy=(int)Math.round(Math.random()*(Fieldy-1));//repeatedly until no bombs are left to distribute
                    if((cell[Randx][Randy]==false)&&(((Randx <(a-1))||(Randx >(a+1)))||((Randy <(b-1))||(Randy >(b+1))))){
                        cell[Randx][Randy]=true;
                        i++;
                    }
                }
            }else {//not enough room for adjc==0. square just can't be a bomb.
                while(i<bombCount){
                    int Randx=(int)Math.round(Math.random()*(Fieldx-1));//place a bomb in a random cell unless occupied or is our cell 
                    int Randy=(int)Math.round(Math.random()*(Fieldy-1));//repeatedly until no bombs are left to distribute
                    if((cell[Randx][Randy]==false)&&(Randx!=a && Randy!=b)){//reset() but with an extra condition
                        cell[Randx][Randy]=true;
                        i++;
                    }
                }
            }
        }
        for(int j=0;j<Fieldx;j++)for(int k=0;k<Fieldy;k++)adj[j][k]=adjc(j,k);
    }
    private int adjc(int a, int b){//when called, initialize adjacent counts for 1 cell. Helper for reset.
        int adjCount = 0;
        for(int i=a-1;i<=a+1;i++){
            for(int j=b-1;j<=b+1;j++){
                if(i<0||j<0||i>=Fieldx||j>=Fieldy||(i==a && j==b)) continue;
                if(cell[i][j]) adjCount++;
            }
        }
        return adjCount;
    }
    //-----------------------Data Access functions------------------------------------------------------------------------------
    boolean isBomb(int a, int b){return cell[a][b];}

    int adjCount(int a, int b){return adj[a][b];}

    //exploding
    void explode(int a, int b){
        if(!exp[a][b])totalExploded++;
        exp[a][b]=true;
    }
    boolean exploded(int a, int b){return exp[a][b];}
    int cellsExploded(){return totalExploded;}

    //marking
    void mark(int a, int b){
        if(!mrk[a][b])totalMarked++;
        mrk[a][b]=true;
    }
    void unmark(int a, int b){
        if(mrk[a][b])totalMarked--;
        mrk[a][b]=false;
    }
    boolean marked(int a, int b){return mrk[a][b];}
    int cellsMarked(){return totalMarked;}

    //questioning
    void question(int a, int b){qstn[a][b]=true;}
    void clearSuspicion(int a, int b){qstn[a][b]=false;}
    boolean isQuestionable(int a, int b){return qstn[a][b];}

    //checking
    void check(int a, int b){
        if(!chkd[a][b])totalChecked++;
        chkd[a][b]=true;
    }
    boolean checked(int a, int b){return chkd[a][b];}
    int cellsChecked(){return totalChecked;}

    //first click & start timer
    void doFirstClick(){
        if(firstClick){
            firstClick=false;
            startTime = System.currentTimeMillis();
            currentTimeTimer.scheduleAtFixedRate(timeTask, 0, 200);//<-- change timer precision here
        }
    }
    boolean isFirstClick(){return firstClick;}

    long getTime(){return time;}//get time

    //GameOver & stop timer (which then cannot be started again)
    void setGameOver(){
        if(!GameOver){
            GameOver=true;
            timeTask.cancel();
        }
    }
    boolean isGameOver(){return GameOver;}
}