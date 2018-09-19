package info.mart.vocabquiz;

/**
 * Created by Tejash on 25-09-2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private List<QuizWrapper> quizList;
    public CustomAdapter(Context context, List<QuizWrapper> quizList) {
        this.context = context;
        this.quizList = quizList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        QuizWrapper quizWrapper = quizList.get(position);
        // set the data in items
        holder.questionId.setText(String.valueOf(quizWrapper.getId())+".");
        holder.questions.setText(quizWrapper.getQuestion());
        holder.options.setText("Options: "+quizWrapper.getAnswers());
        holder.answers.setText("Ans: "+quizWrapper.getCorrectAnswer());
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                //Toast.makeText(context, quizWrapper.getQuestion(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return quizList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questionId,questions,answers,options;// init the item view's
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            questionId = (TextView) itemView.findViewById(R.id.question_id);
            questions = (TextView) itemView.findViewById(R.id.quiz_question);
            answers = (TextView) itemView.findViewById(R.id.answers);
            options = (TextView) itemView.findViewById(R.id.options);
        }
    }
}