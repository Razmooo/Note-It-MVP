package com.example.adria.myappmvp.Task;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adria.myappmvp.AddTask.AddTaskActivity;
import com.example.adria.myappmvp.R;
import com.example.adria.myappmvp.TaskDetail.TaskDetailActivity;
import com.example.adria.myappmvp.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskFragment extends Fragment implements TaskContract.View {

    private final int ADD_TASK = 1;

    private GridView mTaskGridView;
    private LinearLayout mTaskLayout;

    private TextView mNoTaskTextView;
    private LinearLayout mNoTaskLayout;

    private TaskAdapter mTaskAdapter;
    private TaskContract.Presenter mPresenter;

    private FloatingActionButton mFabTaskDelete;


    public TaskFragment()
    {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
    }
    @Override
    public void setPresenter(TaskContract.Presenter presenter)
    {
        mPresenter = presenter;
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
        mTaskGridView = (GridView)root.findViewById(R.id.TaskListView);
        mTaskGridView.setAdapter(mTaskAdapter);
        mTaskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getTaskDetail(i);
            }
        }) ;
        mFabTaskDelete = (FloatingActionButton)root.findViewById(R.id.fab_deleteTask);
        mFabTaskDelete.setVisibility(View.GONE);
        mFabTaskDelete.setOnDragListener(new MyDragListener());

        mTaskGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                startDragAndDrop(i);
                return false;
            }
        });

        mNoTaskTextView = (TextView) root.findViewById(R.id.NoTaskTextView);
        mNoTaskLayout = (LinearLayout) root.findViewById(R.id.NoTaskLayout);
        showNoTaskMenu(false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab_task);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskStart();
            }
        });
    }

    @Override
    public void updateTaskList(List<Task> taskList)
    {
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
    }

    @Override
    public void clearTaskList()
    {
        mPresenter.clearTasks();
        mTaskAdapter.replaceTaskList(new ArrayList<Task>(0));
        mTaskAdapter.notifyDataSetChanged();
    }



    @Override
    public void addTaskStart()
    {
        Intent intent = new Intent(getActivity(),AddTaskActivity.class);
        startActivityForResult(intent,ADD_TASK);
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
    }

    @Override
    public void addTask(Task task)
    {
        mTaskAdapter.addTask(task);
    }

    @Override
    public void showNoTaskMenu(boolean show)
    {
        if(show)
        {
            mTaskGridView.setVisibility(View.GONE);
            mTaskLayout.setVisibility(View.GONE);

            mNoTaskTextView.setVisibility(View.VISIBLE);
            mNoTaskLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mTaskGridView.setVisibility(View.VISIBLE);
            mTaskLayout.setVisibility(View.VISIBLE);

            mNoTaskTextView.setVisibility(View.GONE);
            mNoTaskLayout.setVisibility(View.GONE);
        }

    }


    @Override
    public void getTaskDetail(int taskFromList)
    {
        Task task = mTaskAdapter.getItem(taskFromList);
        Intent intent = new Intent(getContext(), TaskDetailActivity.class);
        intent.putExtra("TASKID",task.getId());

        startActivity(intent);
    }

    void startDragAndDrop(int itemIndex)
    {
        mFabTaskDelete.setVisibility(View.VISIBLE);

        String id = mTaskAdapter.getItem(itemIndex).getId();
        CardView cardView = (CardView)getView().findViewById(R.id.taskCardView);
        ClipData.Item item = new ClipData.Item(id);
        ClipData dragData = new ClipData(id,new String[]{ ClipDescription.MIMETYPE_TEXT_PLAIN},item );
        View.DragShadowBuilder myShadow = new MyDragShadowBuilder(cardView);
        getView().startDrag(dragData, myShadow,null,0);
    }

    private class MyDragListener implements View.OnDragListener
    {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch(dragEvent.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item= dragEvent.getClipData().getItemAt(0);
                    mPresenter.deleteTask(item.getText().toString());
                    mTaskAdapter.replaceTaskList(mPresenter.GetAllTasks());
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    mFabTaskDelete.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    }
}


