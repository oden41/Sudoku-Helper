package com.example.shumpmita.sudokuhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Vector;


public class MainActivity extends Activity {
    //選択したTextViewのIDを記憶する
    Vector id;
    //数独の情報を格納する
    final static int SIDE = 9;
    int[][] board = new int[SIDE][SIDE];

    int [][] sample1 = {
            {0, 0, 5,  3, 0, 0,  0, 0, 0},
            {8, 0, 0,  0, 0, 0,  0, 2, 0},
            {0, 7, 0,  0, 1, 0,  5, 0, 0},

            {4, 0, 0,  0, 0, 5,  3, 0, 0},
            {0, 1, 0,  0, 7, 0,  0, 0, 6},
            {0, 0, 3,  2, 0, 0,  0, 8, 0},

            {0, 6, 0,  5, 0, 0,  0, 0, 9},
            {0, 0, 4,  0, 0, 0,  0, 3, 0},
            {0, 0, 0,  0, 0, 9,  7, 0, 0},
            };

    int[][] sample2 = {
            {0,8,0,7,0,1,0,5,0},   //1
            {0,0,2,0,4,0,9,0,0},   //2
            {9,3,0,0,0,0,0,7,4},   //3
            {8,0,0,1,0,4,0,0,6},   //4
            {0,0,6,0,0,0,1,0,0},   //5
            {7,0,0,9,0,3,0,0,8},   //6
            {2,4,0,0,0,0,0,1,5},   //7
            {0,0,7,0,5,0,3,0,0},   //8
            {0,5,0,8,0,7,0,4,0},}; //9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = new Vector();
        for(int i=0;i<81;i++) {
            setBackground(R.id.textView + i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sample1) {
            int textview = R.id.textView;
            for(int i=0;i<SIDE;i++){
                for(int j=0;j<SIDE;j++){
                    TextView tx = (TextView) findViewById(textview);
                    if(sample1[i][j] != 0) {
                        //数字があったらそれで埋める
                        tx.setText(String.valueOf(sample1[i][j]));
                        board[i][j] = sample1[i][j];
                    }
                    else{
                        //0の時は空白
                        board[i][j] = 0;
                        tx.setText("");
                    }
                    textview++;
                }
            }
            return true;
        }
        else if(id == R.id.sample2){
            int textview = R.id.textView;
            for(int i=0;i<SIDE;i++){
                for(int j=0;j<SIDE;j++){
                    TextView tx = (TextView) findViewById(textview);
                    if(sample2[i][j] != 0) {
                        tx.setText(String.valueOf(sample2[i][j]));
                        board[i][j] = sample2[i][j];
                    }
                    else{
                        board[i][j] = 0;
                        tx.setText("");
                    }
                    textview++;
                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onChangeColor(View view){

        //nullだと強制終了
        TextView tx = (TextView)findViewById(view.getId());

        //TODO:背景の変更、backgroundcolorの取得方法
        if(id.lastIndexOf(view.getId()) == -1) {
            id.add(view.getId());
            tx.setBackgroundColor(Color.parseColor("#DDDDDD"));

        }
        else{
            id.remove((Object)view.getId());
            //TODO:背景設定
            setBackground(view.getId());
        }
    }

    public void onNumClick(View view){
        Button button = (Button)findViewById(view.getId());
        int num = Integer.parseInt(button.getText().toString());

        for(Object id_ : id){
            int textid = (int)id_;
            TextView tv = (TextView)findViewById(textid);
            tv.setText(String.valueOf(num));
            //TODO:背景設定
            setBackground(textid);

            //idからboardに数字を入れる
            int diff = textid - R.id.textView;
            int row = diff/SIDE;
            int col = diff%SIDE;
            board[row][col] = num;
        }
        id.clear();
    }

    public  void onReleaseClick(View view){
        for(Object id_ : id){
            int textid = (int)id_;
            TextView tv = (TextView)findViewById(textid);
            //TODO:背景設定
            setBackground(textid);
        }
        //idは空に
        id.clear();
    }

    public void onDeleteClick(View view){
        for(Object id_ : id){
            int textid = (int)id_;
            TextView tv = (TextView)findViewById(textid);
            //TODO:背景設定
            tv.setText("");
            setBackground(textid);
            //idからboardに0を入れる
            int diff = textid - R.id.textView;
            int row = diff/SIDE;
            int col = diff%SIDE;
            board[row][col] = 0;
        }
        //idは空に
        id.clear();
    }

    public void onAllDeleteClick(View view){
        //すべてのTextViewを空白に
        for(int i=0;i<81;i++) {
            TextView tv = (TextView) findViewById(R.id.textView + i);
            tv.setText("");
        }
        //選択されていたら背景を戻し、idを空に
        for(Object id_ : id){
            int textid = (int)id_;
            TextView tv = (TextView)findViewById(textid);
            //TODO:背景設定
            tv.setText("");
            setBackground(textid);
        }
        id.clear();
        //0に初期化
        for(int i=0;i<SIDE;i++){
            Arrays.fill(board[i],0);
        }
    }

    public void onHelpClick(View view){
        //選択されていたら背景を戻し、idを空に
        for(Object id_ : id){
            int textid = (int)id_;
            TextView tv = (TextView)findViewById(textid);
            //TODO:背景設定
            tv.setText("");
            setBackground(textid);
        }
        id.clear();

        if(isCorrect() && solve(0,0)){
            //数独を埋める
            int textview = R.id.textView;
            for(int i=0;i<SIDE;i++){
                for(int j=0;j<SIDE;j++){
                    TextView tx = (TextView) findViewById(textview);
                    tx.setText(String.valueOf(board[i][j]));
                    textview++;
                }
            }
        }
        else{
            //何らかの問題があり解けなかった
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("うまく解けませんでした。\n情報が間違ってないかお確かめ下さい。")
                    .setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
        }
    }

    private boolean solve(int row, int col) {
        // 最終地点まで行ったら、メソッドを全て閉じていく。
        if (row == 9)
            return true;

        // もしこのマスが固定されているなら、スキップして次のマスへ。
        if (board[row][col] != 0) {
            if (solve(col == 8? (row + 1): row, (col + 1) % 9))
                return true;
        } else {
            // ランダムの数字 1 - 9
            Integer[] randoms = generateRandomNumbers();
            for (int i = 0; i < 9; i++) {

                // この行・列・３ｘ３ブロックにこの数字が重複しなければ、このマスにrandoms[i]を代入します。
                if (!containedInRowCol(row, col, randoms[i]) &&
                        !containedIn3x3Box(row, col, randoms[i])) {
                    board[row][col] = randoms[i];
                    // 次のマス（左から右へ、上から下へという順）に進む。
                    if (solve(col == 8? (row + 1) : row, (col + 1) % 9))
                        return true;
                    else { // 次のマスに入る数字がなく、このマスに別の数字を入れないといけないので、一度入れた数字を初期化する。
                        board[row][col] = 0;
                    }
                }
            }
        }

        return false;
    }

    private boolean containedIn3x3Box(int row, int col, int value) {
        // このマスの位置から、３ｘ３ブロックの一番左上のインデックスを計算。
        int startRow = row / 3 * 3;
        int startCol = col / 3 * 3;

        // ３ｘ３ブロックの中のこのマス以外を調べる。
        for (int i = startRow; i < startRow + 3; i++)
            for (int j = startCol; j < startCol + 3; j++) {
                if (!(i == row && j == col)) {
                    if (board[i][j] == value){
                        return true;
                    }
                }
            }

        return false;
    }

    private boolean containedInRowCol(int row, int col, int value) {
        for (int i = 0; i < 9; i++) {
            // 同じマスは調べない。
            if (i != col)
                if (board[row][i] == value)
                    return true;
            if (i != row)
                if (board[i][col] == value)
                    return true;
        }

        return false;
    }

    private Integer[] generateRandomNumbers() {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++)
            randoms.add(i + 1);
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[9]);
    }

    private void setBackground(int id){
        TextView tv = (TextView)findViewById(id);
        int diff = id - R.id.textView;
        if(diff == 20 || diff == 23 || diff == 47 || diff == 50){
            tv.setBackgroundResource(R.drawable.bottom_right_bold);
        }
        else if(diff == 21 || diff == 24 || diff == 48 || diff == 51){
            tv.setBackgroundResource(R.drawable.bottom_left_bold);
        }
        else if(diff == 29 || diff == 32 || diff == 56 || diff == 59){
            tv.setBackgroundResource(R.drawable.top_right_bold);
        }
        else if(diff == 30 || diff == 33 || diff == 57 || diff == 60){
            tv.setBackgroundResource(R.drawable.top_left_bold);
        }
        else if(diff % 9 == 2){
            tv.setBackgroundResource(R.drawable.right_bold);
        }
        else if(diff % 9 == 3){
            tv.setBackgroundResource(R.drawable.left_bold);
        }
        else if(diff % 9 == 5){
            tv.setBackgroundResource(R.drawable.right_bold);
        }
        else if(diff % 9 == 6){
            tv.setBackgroundResource(R.drawable.left_bold);
        }
        else if(diff / 9 == 2){
            tv.setBackgroundResource(R.drawable.bottom_bold);
        }
        else if(diff / 9 == 3){
            tv.setBackgroundResource(R.drawable.top_bold);
        }
        else if(diff / 9 == 5){
            tv.setBackgroundResource(R.drawable.bottom_bold);
        }
        else if(diff / 9 == 6){
            tv.setBackgroundResource(R.drawable.top_bold);
        }
        else{
            tv.setBackgroundResource(R.drawable.test);
        }
    }

    private boolean isCorrect(){
        //boardから行・列・ブロックに数字の重複がないか確かめる
        boolean[] isNum = {false,false,false,false,false,false,false,false,false};

        //行
        for(int i=0;i<SIDE;i++){
            for(int j=0;j<SIDE;j++){
                if(board[i][j] != 0){
                    if(isNum[board[i][j] - 1]){
                        return false;
                    }
                    else{
                        isNum[board[i][j] - 1] = true;
                    }
                }
            }

            //1行終わったのでリセット
            for(int k=0;k<SIDE;k++){
                isNum[k] = false;
            }
        }

        //列
        for(int i=0;i<SIDE;i++){
            for(int j=0;j<SIDE;j++){
                if(board[j][i] != 0){
                    if(isNum[board[j][i] - 1]){
                        return false;
                    }
                    else{
                        isNum[board[j][i] - 1] = true;
                    }
                }
            }

            //1列終わったのでリセット
            for(int k=0;k<SIDE;k++){
                isNum[k] = false;
            }
        }

        //ブロック
        for(int i=0;i<SIDE; i += 3){
            for(int j=0;j<SIDE; j+= 3){

                for(int k=0;k<3;k++){
                    for(int l=0;l<3; l++){
                        if(board[i + k][j + l] != 0){
                            if(isNum[board[i + k][j + l] - 1]){
                                return false;
                            }
                            else{
                                isNum[board[i + k][j + l] - 1] = true;
                            }
                        }
                    }
                }

                //1ブロック終わったのでリセット
                for(int k=0;k<SIDE;k++){
                    isNum[k] = false;
                }

            }
        }

        return  true;
    }
}
