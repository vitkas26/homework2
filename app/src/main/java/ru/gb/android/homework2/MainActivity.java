package ru.gb.android.homework2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "$$$";

    private TextView resultTv;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonPlus;
    private Button buttonMinus;
    private Button buttonMultiply;
    private Button buttonDivide;
    private Button buttonEqual;
    private Button buttonDel;
    private Button buttonDot;
    private Button buttonDelAll;
    private int counterOfDots = 0;
    private String BUNDLE_KEY;
    String[] numbersToAdd;
    String[] operators;
    List<String> operatorsNoEmptyElements = new ArrayList();
    List<String> numbersToAddArrayList = new ArrayList();
    TextResult textToSave = new TextResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_KEY)) {
            textToSave = savedInstanceState.getParcelable(BUNDLE_KEY);
            resultTv.setText(String.valueOf(textToSave.value));
        }
        setupListeners();
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        textToSave.value = String.valueOf(resultTv.getText());
        outState.putParcelable(BUNDLE_KEY, textToSave);
        Log.d(TAG, "onSaveInstanceState() called with: outState = [" + outState + "]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    private void initViews() {
        resultTv = findViewById(R.id.result_text_view);
        button0 = findViewById(R.id.button_0);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonPlus = findViewById(R.id.button_plus);
        buttonMinus = findViewById(R.id.button_minus);
        buttonMultiply = findViewById(R.id.button_multiply);
        buttonDivide = findViewById(R.id.button_divide);
        buttonEqual = findViewById(R.id.button_equals);
        buttonDot = findViewById(R.id.button_dot);
        buttonDel = findViewById(R.id.button_del);
        buttonDelAll = findViewById(R.id.button_del_all);
    }

    private void setupListeners() {
        button0.setOnClickListener(this::onButtonClick);
        button1.setOnClickListener(this::onButtonClick);
        button2.setOnClickListener(this::onButtonClick);
        button3.setOnClickListener(this::onButtonClick);
        button4.setOnClickListener(this::onButtonClick);
        button5.setOnClickListener(this::onButtonClick);
        button6.setOnClickListener(this::onButtonClick);
        button7.setOnClickListener(this::onButtonClick);
        button8.setOnClickListener(this::onButtonClick);
        button9.setOnClickListener(this::onButtonClick);
        buttonDot.setOnClickListener(this::onButtonClick);
        buttonPlus.setOnClickListener(this::onActionButtonClick);
        buttonMinus.setOnClickListener(this::onActionButtonClick);
        buttonMultiply.setOnClickListener(this::onActionButtonClick);
        buttonDivide.setOnClickListener(this::onActionButtonClick);
        buttonEqual.setOnClickListener(this::onActionButtonClick);
        buttonDel.setOnClickListener(this::onButtonClick);
        buttonDelAll.setOnClickListener(this::onButtonClick);
    }

    private void onButtonClick(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        switch (b.getId()) {
            case R.id.button_del:
                deletePrevious();
                break;
            case R.id.button_dot:
                checkExtraDots(buttonText);
                break;
            case R.id.button_del_all:
                resultTv.setText("");
                break;
            default:
                resultTv.append(buttonText);
        }
    }

    private void onActionButtonClick(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        String resultTvString = resultTv.getText().toString();
        String lastCharacterOfResultTv = "";

        try {
            lastCharacterOfResultTv = resultTvString.substring(resultTvString.length() - 1);
        } catch (Exception e) {
            Log.d(TAG, "Empty resultTextView");
        }

        if (lastCharacterOfResultTv.equals("+") || lastCharacterOfResultTv.equals("-") || lastCharacterOfResultTv.equals("*")
                || lastCharacterOfResultTv.equals("/") || lastCharacterOfResultTv.equals("=")
                || lastCharacterOfResultTv.equals(".")) {
            Toast.makeText(this, "Impossible arithmetical operation", Toast.LENGTH_SHORT).show();
        } else {
            if (b.getId() != R.id.button_equals) {
                resultTv.append(buttonText);
                allowAnotherDot();
            } else {
                findNumbers();
            }
        }
    }

    private void checkExtraDots(String buttonText) {
        String lastCharacterOfResultTv = "";
        try {
            lastCharacterOfResultTv = resultTv.getText().toString().substring(resultTv.getText().toString().length() - 1);
        } catch (Exception e) {
            Log.d(TAG, "Empty resultTextView");
        }

        if (buttonText.equals(".") && counterOfDots < 1 && !lastCharacterOfResultTv.equals("+")
                && !lastCharacterOfResultTv.equals("-") && !lastCharacterOfResultTv.equals("*")
                && !lastCharacterOfResultTv.equals("/")) {
            resultTv.append(buttonText);
            counterOfDots++;
        } else {
            Toast.makeText(this, "Dot is exist", Toast.LENGTH_SHORT).show();
        }
        if (resultTv.getText().toString().equals(".")) {
            resultTv.setText("0.");
        }
    }

    private void deletePrevious() {
        String toRemove = resultTv.getText().toString();
        try {
            String editedText = toRemove.substring(0, toRemove.length() - 1);
            resultTv.setText(editedText);
        } catch (Exception e) {
            Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void allowAnotherDot() {
        counterOfDots = 0;
    }

    private void findNumbers() {
        String resultText = resultTv.getText().toString();
        numbersToAdd = resultText.split("[^\\d^.]");
        operators = resultText.split("[^+\\-*/]");


        for (String operator : operators) {
            if (!operator.equals("")) {
                operatorsNoEmptyElements.add(operator);
            }
        }

        for (String number : numbersToAdd) {
            if (!number.equals("")) {
                numbersToAddArrayList.add(number);
            }
        }
        calculateNumbers();
    }

    private void calculateNumbers() {
        divideNumbers();
        multiplyNumbers();
        subtractNumbers();
        addNumbers();
        resultTv.setText(numbersToAddArrayList.get(0));
        emptyOperatorsList();
        emptyNumbersList();
        counterOfDots++;
    }

    private void emptyNumbersList() {
        numbersToAddArrayList.clear();
    }

    private void emptyOperatorsList() {
        operatorsNoEmptyElements.clear();
    }

    private void divideNumbers() {
        int firstNumber;
        int secondNumber;
        for (int i = 0; i < operatorsNoEmptyElements.size(); i++) {
            firstNumber = i;
            secondNumber = i;
            if ((operatorsNoEmptyElements.get(i).equals("/"))) {

                if (i > 0) {
                    while (numbersToAddArrayList.get(firstNumber).equals("")) {
                        firstNumber--;
                    }
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            / Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                } else {
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            / Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                }
            }
        }
    }

    private void multiplyNumbers() {
        int firstNumber;
        int secondNumber;
        for (int i = 0; i < operatorsNoEmptyElements.size(); i++) {
            firstNumber = i;
            secondNumber = i;
            if ((operatorsNoEmptyElements.get(i).equals("*"))) {

                if (i > 0) {
                    while (numbersToAddArrayList.get(firstNumber).equals("")) {
                        firstNumber--;
                    }
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            * Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                } else {
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            * Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                }
            }
        }
    }

    private void subtractNumbers() {
        int firstNumber;
        int secondNumber;
        for (int i = 0; i < operatorsNoEmptyElements.size(); i++) {
            firstNumber = i;
            secondNumber = i;
            if ((operatorsNoEmptyElements.get(i).equals("-"))) {

                if (i > 0) {
                    while (numbersToAddArrayList.get(firstNumber).equals("")) {
                        firstNumber--;
                    }
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            - Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                } else {
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            - Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                }
            }
        }
    }

    private void addNumbers() {
        int firstNumber;
        int secondNumber;
        for (int i = 0; i < operatorsNoEmptyElements.size(); i++) {
            firstNumber = i;
            secondNumber = i;
            if ((operatorsNoEmptyElements.get(i).equals("+"))) {

                if (i > 0) {
                    while (numbersToAddArrayList.get(firstNumber).equals("")) {
                        firstNumber--;
                    }
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            + Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                } else {
                    numbersToAddArrayList.set(firstNumber, String.valueOf(Double.parseDouble(numbersToAddArrayList.get(firstNumber))
                            + Double.parseDouble(numbersToAddArrayList.get(secondNumber + 1))));
                    numbersToAddArrayList.set(i + 1, "");
                    operatorsNoEmptyElements.set(i, "");
                }
            }
        }
    }
}
