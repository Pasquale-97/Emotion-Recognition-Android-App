package com.example.smartwatch_app;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import libsvm.*;
import android.content.Context;
import android.content.res.AssetManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartwatch_app.database_classes.DatabaseOpenHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class EmotionRecognition extends Fragment {

    DatabaseOpenHelper myDb = DatabaseOpenHelper.getInstance(getActivity());
    Button emotionRecognition;
    Button correctButton;
    Button incorrectButton;

    int subListHappy = 0;
    int subListNeutral = 0;
    int subListSad = 0;
    private String Tag;

    int intEmotion = 0;
    String strEmotion = "";

    List<String> l = new ArrayList<>();

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    String dateString = dateFormat.format(date);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.emotion_recognition, container, false);
        final TextView textView = (TextView)v.findViewById(R.id.ml_emotion_text);
        final TextView textView2 = (TextView)v.findViewById(R.id.textView3);
        final TextView emotion_text = v.findViewById(R.id.emotion_prediction);

//        final ProgressBar progressBar = v.findViewById(R.id.progressBar);


//        TextView titleString = v.findViewById(R.id.title_string);
//        TextView happyString = v.findViewById(R.id.happy_string);
//        TextView excitedString = v.findViewById(R.id.excited_string);
//        TextView sadString = v.findViewById(R.id.sad_string);
//        TextView angryString = v.findViewById(R.id.angry_string);

//        DatabaseOpenHelper db = new DatabaseOpenHelper(getActivity());
//        int excitedData = db.getAllExcitedData();
//        int happyData = db.getAllHappyData();
//        int sadData = db.getAllSadData();
//        int angryData = db.getAllAngryData();

        correctButton = v.findViewById(R.id.correct_button);
        incorrectButton = v.findViewById(R.id.incorrect_button);

        emotionRecognition = v.findViewById(R.id.emotion_recognition);
        emotionRecognition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int predict_probability = 0;

                AssetManager am = getContext().getAssets();
                emotionRecognition.setVisibility(View.INVISIBLE);

                 try {

                     InputStream is = am.open("data.txt");

                     BufferedReader input = new BufferedReader(new InputStreamReader(is));


                     OutputStreamWriter output = new OutputStreamWriter(getContext().openFileOutput("prediction.txt", Context.MODE_PRIVATE));

                     InputStream fIn = am.open("emotion.model");
                     BufferedReader bufferedReader_model = new BufferedReader(new InputStreamReader(fIn));


                     svm_model model = svm.svm_load_model(bufferedReader_model);

                    if (model == null) {
                        Log.e(TAG, "Can't open model file EmotionRecognition" + "\n");
                        System.exit(1);
                    }

                    predict(input, output, model, predict_probability);
                    input.close();
                    output.close();

                }

                catch (IOException e){
                    Log.e(TAG, "onCreateView: " + e);
                }

                 finally {

                     try {
                         InputStream inputStream = getContext().openFileInput("prediction.txt");

                         if (inputStream != null) {
                             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                             String receiveString = "";
                             LinkedList<String> predictedList = new LinkedList<>();

                             while ((receiveString = bufferedReader.readLine()) != null) {
                                 predictedList.add("\n" + receiveString);
                             }

                             List<String> predictedSubList = predictedList.subList(predictedList.size() - 5, predictedList.size());

                             for(int i = 0; i < predictedSubList.size(); i++){
                                 double value = Double.valueOf(predictedSubList.get(i));

                                 if(value == 1.0)
                                     subListSad += 1;


                                 else if(value == 2.0)
                                     subListNeutral += 1;

                                 else
                                     subListHappy += 1;

                             }

                             textView.setText("Is the prediction correct?");
                             correctButton.setVisibility(View.VISIBLE);
                             incorrectButton.setVisibility(View.VISIBLE);


                             if(subListSad > subListHappy && subListSad > subListNeutral){
                                 textView.setTextSize(getResources().getDimension(R.dimen.textsize));
                                 emotion_text.setVisibility(View.VISIBLE);
                                 emotion_text.setTextColor(ContextCompat.getColor(getContext(), R.color.sad_colour));
                                 emotion_text.setText("Sad");
                                 intEmotion = 4;
                                 strEmotion = "sad";


                             }

                             if(subListNeutral > subListSad && subListNeutral > subListHappy){
                                 textView.setTextSize(getResources().getDimension(R.dimen.textsize));
                                 emotion_text.setVisibility(View.VISIBLE);
                                 emotion_text.setTextColor(ContextCompat.getColor(getContext(), R.color.calm_colour));
                                 emotion_text.setText("Calm");
                                 intEmotion = 3;
                                 strEmotion = "calm";
                             }

                             if(subListHappy > subListSad && subListHappy > subListNeutral){
                                 textView.setTextSize(getResources().getDimension(R.dimen.textsize));
                                 emotion_text.setVisibility(View.VISIBLE);
                                 emotion_text.setTextColor(ContextCompat.getColor(getContext(), R.color.happy_colour));
                                 emotion_text.setText("Happy");
                                 intEmotion = 2;
                                 strEmotion = "happy";
                             }


                             Log.e(TAG, "onClick: " + predictedSubList);
                             Log.e(TAG, "Sad: " + subListSad);
                             Log.e(TAG, "Neutral: " + subListNeutral);
                             Log.e(TAG, "Happy: " + subListHappy);

                             subListSad = 0;
                             subListNeutral = 0;
                             subListHappy = 0;

                             String getHr = l.get(l.size() - 2);
                             String subStringHr = getHr.substring(4, 6);
                             textView2.setText(subStringHr);

                             Log.e(TAG, "onClick: " + subStringHr );

                         }
                     } catch (FileNotFoundException e) {
                         Log.e(TAG, "FileNotFoundException: " + e );

                     }

                     catch (IOException e){
                         Log.e(TAG, "IOException: " + e );
                     }
                 }
            }
        });

        myDb = new DatabaseOpenHelper(getActivity());
        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = myDb.insertData(strEmotion, intEmotion, dateString);
                if (isInserted) {
                    Toast.makeText(getActivity(),"Emotion Added To Database", Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(getActivity(), "Unable To Add Emotion To Database", Toast.LENGTH_LONG).show();
                }
            }

        });

        incorrectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ListOfEmotions();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return v;
    }


    private static svm_print_interface svm_print_null = new svm_print_interface()
    {
        public void print(String s) {}
    };

    private static svm_print_interface svm_print_stdout = new svm_print_interface()
    {
        public void print(String s)
        {
            Log.d(TAG, "SVM: " + s);
        }
    };

    private static svm_print_interface svm_print_string = svm_print_stdout;

    static void info(String s)
    {
        svm_print_string.print(s);
    }

    private static double atof(String s)
    {
        return Double.valueOf(s).doubleValue();
    }

    private static int atoi(String s)
    {
        return Integer.parseInt(s);
    }

    int sad = 0;
    int happy = 0;
    int neutral = 0;

    public void predict(BufferedReader input, OutputStreamWriter output, svm_model model, int predict_probability) throws IOException
    {
        int correct = 0;
        int total = 0;
        double error = 0;
        double sump = 0, sumt = 0, sumpp = 0, sumtt = 0, sumpt = 0;


        int svm_type = svm.svm_get_svm_type(model);
        int nr_class = svm.svm_get_nr_class(model);
        double[] prob_estimates=null;

        if(predict_probability == 1)
        {
            if(svm_type == svm_parameter.EPSILON_SVR ||
                    svm_type == svm_parameter.NU_SVR)
            {
                Log.d(Tag, "Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
            }

            else
            {
                int[] labels = new int[nr_class];
                svm.svm_get_labels(model,labels);
                prob_estimates = new double[nr_class];
                output.write("labels");

                for(int j = 0; j < nr_class; j++)
                    output.write(" " + labels[j]);
                output.write("\n");
            }
        }

        while(true)
        {
            String line = input.readLine();
            l.add(line);

            if(line == null) {
                break;
            }


            StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

            double target_label = atof(st.nextToken());
            int m = st.countTokens()/2;

            svm_node[] x = new svm_node[m];
            for(int j = 0; j < m; j++)
            {
                x[j] = new svm_node();
                x[j].index = atoi(st.nextToken());
                x[j].value = atof(st.nextToken());
            }

            double predict_label;
            if (predict_probability == 1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
            {
                predict_label = svm.svm_predict_probability(model, x, prob_estimates);
                output.write(predict_label + " ");

                for(int j = 0; j < nr_class; j++)
                    output.write(prob_estimates[j] + " ");
                output.write("\n");
            }
            else
            {
                predict_label = svm.svm_predict(model, x);

                if(predict_label == 1.0)
                    sad += 1;


                if(predict_label == 2.0)
                    neutral += 1;


                if(predict_label == 3.0)
                    happy += 1;

                output.write(predict_label + "\n");
            }

            if(predict_label == target_label)
                ++correct;

            error += (predict_label-target_label)*(predict_label-target_label);
            sump += predict_label;
            sumt += target_label;
            sumpp += predict_label*predict_label;
            sumtt += target_label*target_label;
            sumpt += predict_label*target_label;

            ++total;
        }


        if(svm_type == svm_parameter.EPSILON_SVR ||
                svm_type == svm_parameter.NU_SVR)
        {
            EmotionRecognition.info("Mean squared error = "+error/total+" (regression)\n");
            EmotionRecognition.info("Squared correlation coefficient = "+
                    ((total*sumpt-sump*sumt)*(total*sumpt-sump*sumt))/
                            ((total*sumpp-sump*sump)*(total*sumtt-sumt*sumt))+
                    " (regression)\n");
        }
        else
            EmotionRecognition.info("Accuracy = "+(double)correct/total*100+
                    "% ("+correct+"/"+total+") (classification)\n");
            EmotionRecognition.info("Sad: " + sad + " Neutral: " + neutral + " Happy: " + happy );
    }

//    private static void exit_with_help()
//    {
//        System.err.print("usage: svm_predict [options] test_file model_file output_file\n"
//                +"options:\n"
//                +"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n"
//                +"-q : quiet mode (no outputs)\n");
//        System.exit(1);
//    }

}


