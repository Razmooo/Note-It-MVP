package com.example.adria.myappmvp.Task;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskFragment extends Fragment implements TaskContract.View {

    private ListView mTaskList;
    private LinearLayout mTaskLayout;

    private TextView mNoTaskTextView;
    private LinearLayout mNoTaskLayout;

    private TaskAdapter mTaskAdapter;

    public TaskFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskAdapter = new TaskAdapter(getContext(),new ArrayList<Task>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.task_frag, container, false);
        mTaskLayout = (LinearLayout) root.findViewById(R.id.TaskLayout);
        mTaskList = (ListView)root.findViewById(R.id.TaskListView);

        mTaskList.setAdapter(mTaskAdapter);


        mNoTaskTextView = (TextView) root.findViewById(R.id.NoTaskTextView);
        mNoTaskLayout = (LinearLayout) root.findViewById(R.id.NoTaskLayout);
        showNoTaskMenu(false);


        return root;

    }

    @Override
    public void updateTaskList(List<Task> taskList)
    {
        mTaskAdapter.setList(taskList);
    }

    @Override
    public void addTask(Task task) {
        mTaskAdapter.addTask(task);
    }

    @Override
    public void showNoTaskMenu(boolean show)
    {
        if(show)
        {
            mTaskList.setVisibility(View.GONE);
            mTaskLayout.setVisibility(View.GONE);

            mNoTaskTextView.setVisibility(View.VISIBLE);
            mNoTaskLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mTaskList.setVisibility(View.VISIBLE);
            mTaskLayout.setVisibility(View.VISIBLE);

            mNoTaskTextView.setVisibility(View.GONE);
            mNoTaskLayout.setVisibility(View.GONE);
        }

    }
}
