package com.example.adria.myappmvp.TaskDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

/**
 * Created by adria on 10.05.2018.
 */

public class TaskDetailFragment extends Fragment implements TaskDetailContract.View
{

    private EditText mTitle;
    private EditText mDescription;

    private TaskDetailContract.Presenter mPresenter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Task task = mPresenter.getTaskFromIntent(getActivity().getIntent());
        mTitle.setText(task.getTitle());
        mDescription.setText(task.getDescription());

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_task_detail);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.taskdetail_frag, container, false);
        mTitle = (EditText) root.findViewById(R.id.title);
        mDescription = (EditText) root.findViewById(R.id.description);

        return root;
    }

    @Override
    public void setPresenter(TaskDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void updateTask()
    {

        mPresenter.updateTask(mTitle.getText().toString(), mDescription.getText().toString());
    }

    @Override
    public void closeTaskDetail()
    {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


}
