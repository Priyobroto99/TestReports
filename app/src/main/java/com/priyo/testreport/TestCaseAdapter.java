package com.priyo.testreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.priyo.testreport.databinding.TestcaseItemBinding;
import com.priyo.testreport.models.TestCase_Item;
import java.util.*;
public class TestCaseAdapter extends RecyclerView.Adapter<TestCaseAdapter.TestCaseViewHolder> {

    private Context context;
    private  ArrayList<TestCase_Item> testcases;
    TestCaseClickhandler itemClickHander;

    public TestCaseAdapter(Context context, ArrayList<TestCase_Item> testcases, TestCaseClickhandler itemClickHander) {
        this.context = context;
        this.testcases = testcases;
        this.itemClickHander = itemClickHander;
    }

    @NonNull
    @Override
    public TestCaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TestcaseItemBinding testcaseItemBinding = DataBindingUtil.inflate(LayoutInflater
                .from(parent.getContext() )  ,R.layout.testcase_item,parent,false);
        TestCaseViewHolder testCaseViewHolder = new TestCaseViewHolder( testcaseItemBinding,itemClickHander);
        return testCaseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestCaseViewHolder holder, int position) {

        TestCase_Item tc = testcases.get(position);
        holder.testcaseItemBinding.setTestcase(tc);
    }

    @Override
    public int getItemCount() {
        return testcases.size();
    }	

    public class TestCaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TestcaseItemBinding testcaseItemBinding;
        TestCaseClickhandler clickhandler;

        public TestCaseViewHolder(@NonNull TestcaseItemBinding testcaseItemBinding,TestCaseClickhandler clickhandler) {

            super(testcaseItemBinding.getRoot());
            this.testcaseItemBinding = testcaseItemBinding;
            this.clickhandler = clickhandler;

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            clickhandler.ontestCaseClicked(getAdapterPosition());
        }
    }

    public interface TestCaseClickhandler {
        void ontestCaseClicked(int position);
    }
}
