package com.example.logrecords;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CallLogFragment extends Fragment {

    private RecyclerView callLogRecyclerView;
    private LogAdapter callLogAdapter;
    private List<LogData> callLogDataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);

        callLogRecyclerView = view.findViewById(R.id.callLogRecyclerView);
        callLogDataList = new ArrayList<>();
        callLogAdapter = new LogAdapter(callLogDataList);
        callLogRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        callLogRecyclerView.setAdapter(callLogAdapter);

        // Fetch call logs and update the UI
        fetchCallLogs();

        return view;
    }

    private void fetchCallLogs() {
        // Query the call log
        Cursor cursor = requireActivity().getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                CallLog.Calls.DATE + " DESC"
        );

        // Process the call log data and add it to callLogDataList
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract relevant information from the cursor
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));

                // Create a LogData object and add it to the list
                LogData logData = new LogData(number, type, date);
                callLogDataList.add(logData);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        // Notify the adapter that data has changed
        callLogAdapter.notifyDataSetChanged();
    }
}
