/*
 * Copyright 2016 Shif Ben Avraham
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.shif.mazing;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public enum Color {
        WHITE, GRAY, BLACK;
    }
    public MazeView mMazeView;
    public FingerLine mFingerLine;
    static ImageView strawberry;
    ImageView arrow;
    public int mazeSize;
    public FingerLine line;
    public Toolbar mazingToolBar;
    DisplayMetrics displaymetrics = new DisplayMetrics();
    FrameLayout mFrameLayout;
    public final int PADDING = 64;
    public final int FAT_FINGERS_MARGIN = 25;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        mazingToolBar = (Toolbar) findViewById(R.id.mazing_toolbar);
        setSupportActionBar(mazingToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mToolBarText=(TextView)findViewById(R.id.toolbar_title);
        Typeface typeFace=Typeface.createFromAsset(getAssets(), "fonts/Schoolbell.ttf");
        mToolBarText.setTypeface(typeFace);

        mFrameLayout = (FrameLayout)findViewById(R.id.mazeWrapper);
        ViewGroup.LayoutParams params = mFrameLayout.getLayoutParams();
        params.height = (int) Math.floor(displaymetrics.heightPixels * 0.7);
        mFrameLayout.setLayoutParams(params);

        FloatingActionButton newMazeButton = (FloatingActionButton) findViewById(R.id.newMazeButton);
        newMazeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createMaze();
            }
        });
        newMazeButton.performClick();

    }

    public void createMaze() {
        // First remove any existing MazeView and FingerLine
        if (mMazeView != null) {
            ((ViewGroup) mMazeView.getParent()).removeView(mMazeView);
        }
        if (mFingerLine != null) {
            ((ViewGroup) mFingerLine.getParent()).removeView(mFingerLine);
        }
        mazeSize = 10;
        mMazeView = new MazeView(this, mazeSize);

        // Trace the path from start to farthestVertex using the line of predecessors,
        // apply this information to form an array of rectangles
        // which will be passed on to fingerLine view
        // where the line has to pass.
        // The array be checked against the drawn line in FingerLine.

        int[][] solutionAreas = new int[mMazeView.lengthOfSolutionPath][4];

        int currentVertexKey;
        int totalMazeWidth = displaymetrics.widthPixels - PADDING;
        // int totalMazeHeight = totalMazeWidth;
        int cellSide = totalMazeWidth / mazeSize;
        int row, column;
        int topLeftX, topLeftY, bottomRightX, bottomRightY;

        for (int i = 0; i < mMazeView.lengthOfSolutionPath; i++) {

            currentVertexKey = mMazeView.listOfSolutionVertecesKeys[i];

            // Translate vertex key to location on screen
            row = (currentVertexKey) / mazeSize;
            column = (currentVertexKey) % mazeSize;
            topLeftX = (PADDING / 2) + (column * cellSide) - FAT_FINGERS_MARGIN;
            topLeftY = (PADDING / 2) + (row * cellSide) - FAT_FINGERS_MARGIN;

            bottomRightX = (PADDING / 2) + ((column + 1) * cellSide) + FAT_FINGERS_MARGIN;
            bottomRightY = (PADDING / 2) + ((row + 1) * cellSide) + FAT_FINGERS_MARGIN;
            solutionAreas[i] = new int[]{ topLeftX, topLeftY, bottomRightX, bottomRightY };
        }

        mFrameLayout.addView(mMazeView);
        mFingerLine = new FingerLine(this, null, solutionAreas);
        mFrameLayout.addView(mFingerLine);

        // Add start arrow pic
        int startCellArrowX = solutionAreas[mMazeView.lengthOfSolutionPath - 1][0] + 12 + FAT_FINGERS_MARGIN;
        int startCellArrowY = solutionAreas[mMazeView.lengthOfSolutionPath - 1][1] + 100 + FAT_FINGERS_MARGIN;
        arrow = (ImageView) findViewById(R.id.arrow);
        arrow.setX(startCellArrowX);
        arrow.setY(startCellArrowY);
        arrow.setVisibility(View.VISIBLE);

        // Add strawberry pic
        int endCellStrawberryX = solutionAreas[0][0] + 8 + FAT_FINGERS_MARGIN;
        int endCellStrawberryY = solutionAreas[0][1] + 10 + FAT_FINGERS_MARGIN;
        strawberry = (ImageView) findViewById(R.id.strawberry);
        strawberry.setX(endCellStrawberryX);
        strawberry.setY(endCellStrawberryY);
        strawberry.setVisibility(View.VISIBLE);;
    }


    // Function to remove a specific Integer from Integer array
    // and return a new Integer array without the specified value
    public static Integer[] removeValueFromArray(Integer[] array, Integer value) {

        ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(array));
        arrayList.remove(arrayList.indexOf(value));

        Integer[] newArray = new Integer[arrayList.size()];
        arrayList.toArray(newArray);

        return newArray;
    }

    public static void startGameSolvedAnimation() {
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(700);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        strawberry.startAnimation(animation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_level:
                // User chose the "Level" item, show the level settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
