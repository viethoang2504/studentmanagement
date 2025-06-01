package com.example.studentmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Student> list;

    public StudentAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override public int getCount() { return list.size(); }
    @Override public Object getItem(int position) { return list.get(position); }
    @Override public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);

        TextView name = convertView.findViewById(R.id.textName);
        TextView mssv = convertView.findViewById(R.id.textMSSV);

        Student student = list.get(position);
        name.setText(student.getName());
        mssv.setText("MSSV: " + student.getMssv());

        return convertView;
    }
}
