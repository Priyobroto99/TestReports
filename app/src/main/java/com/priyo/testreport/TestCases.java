package com.priyo.testreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.priyo.testreport.R;
import com.priyo.testreport.databinding.ActivityTestCasesBinding;
import com.priyo.testreport.models.TestCase_Item;
import com.priyo.testreport.models.TestCasesModel;
import com.priyo.testreport.models.TestStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class TestCases extends AppCompatActivity implements TestCaseAdapter.TestCaseClickhandler {
    final String TAG = "TestCasesTag";
    ArrayList<TestCase_Item> testcases = new ArrayList<>();
    private TestCaseAdapter adapter;
    private RecyclerView recyclerView;
    private ActivityTestCasesBinding testCasesBinding;
    SwipeRefreshLayout swipeRefreshLayout;
    private Dialog stepsDialog;
    ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cases);

        Intent i = getIntent();
        final String from = i.getStringExtra("from");
        final String to = i.getStringExtra("to");
        testCasesBinding= DataBindingUtil.setContentView(this, R.layout.activity_test_cases);
        swipeRefreshLayout = testCasesBinding.swiperefresh;
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_background);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata(from,to);
            }
        });
        getdata(from,to);


    }

    private void getdata(String from,String to){
        TestCasesModel tc = ViewModelProviders.of(this).get(TestCasesModel.class);
        LiveData data = tc.getTestcasesbyRange(from,to);
        data.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    showLoader(true);
                    loadTestCases(s);
                    showRecyclerView();
                    showLoader(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stepsDialog = new Dialog(this);
    }

    private void showRecyclerView() {
        recyclerView = testCasesBinding.recyclerview;
        adapter = new TestCaseAdapter(this,testcases,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void loadTestCases(String s) throws JSONException, ParseException {
        testcases.clear();

        JSONObject ob = new JSONObject(s);
        Log.i(TAG, ob.toString());
        JSONArray arr = (JSONArray) ob.get("testcases");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject tc = arr.getJSONObject(i);
            TestCase_Item testCase_item = new TestCase_Item();
            testCase_item.setTcid(tc.getString("tc_id"));
            testCase_item.setTcname(tc.getString("tc_name"));
            testCase_item.setTcstatus(tc.getString("tc_status"));
            testCase_item.setDate(tc.getString("tc_executed"));
            testCase_item.setSteps(tc.getJSONArray("steps"));

            testcases.add(testCase_item);
        }
    }

    @Override
    public void ontestCaseClicked(int position) {
        TestCase_Item testCase_item = testcases.get(position);

        JSONArray steps = testCase_item.getSteps();

        List<TestStep> steplist = new ArrayList<>();

        for (int i = 0; i < steps.length(); i++) {
            try {
                int counter = i+1;
                JSONObject step = (JSONObject) steps.get(i);

                if(step.keys().hasNext()){
                 String stepName = step.keys().next();
                   String status =  step.get(stepName).toString();

                    steplist.add(new TestStep(counter+"",stepName,status));
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initTable(steplist);
    }

    public void initTable(List<TestStep> steplist) {
        stepsDialog.setContentView(R.layout.steps);
        TableLayout stepsTable;

        stepsTable = stepsDialog.findViewById(R.id.table_main);

        TableRow headerRow = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("step #".toUpperCase());
        tv0.setGravity(Gravity.CENTER);
        tv0.setPadding(5,5,5,5);
        headerRow.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" step name ".toUpperCase());
        tv1.setGravity(Gravity.CENTER);
        tv1.setPadding(5,5,5,5);
        headerRow.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Status ");
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(5,5,5,5);
        headerRow.addView(tv2);
        stepsTable.addView(headerRow);


        int stepslength = steplist.size();
        for (int i = 0; i < stepslength; i++) {
            TableRow tbrow = new TableRow(this);
            TextView count = new TextView(this);
            count.setText(steplist.get(i).getStepNo());
            count.setGravity(Gravity.CENTER);
            count.setPadding(5,5,5,5);
            tbrow.addView(count);
            TextView name = new TextView(this);
            name.setText(steplist.get(i).getStepName());
            name.setGravity(Gravity.CENTER);
            name.setPadding(5,5,5,5);
            tbrow.addView(name);
            TextView status = new TextView(this);
            status.setText(steplist.get(i).getStepStatus());
            status.setGravity(Gravity.CENTER);
            status.setPadding(5,5,5,5);
            tbrow.addView(status);
            stepsTable.addView(tbrow);
        }

        stepsDialog.show();

    }

    void showLoader(boolean b){

        if(dialog==null) {
            dialog = dialog = new ProgressDialog(this);
            ;
        }
        if (b) {
                dialog.setMessage("Loading ...");
                dialog.show();
            } else {
                dialog.dismiss();
            }

    }
}
