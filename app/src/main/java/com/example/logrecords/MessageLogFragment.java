package com.example.logrecords;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
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

public class MessageLogFragment extends Fragment {

    private RecyclerView messageLogRecyclerView;
    private LogAdapter messageLogAdapter;
    private List<LogData> messageLogDataList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_log, container, false);

        messageLogRecyclerView = view.findViewById(R.id.messageLogRecyclerView);
        messageLogDataList = new ArrayList<>();
        messageLogAdapter = new LogAdapter(messageLogDataList);
        messageLogRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        messageLogRecyclerView.setAdapter(messageLogAdapter);

        // Fetch message logs and update the UI
        fetchMessageLogs();

        return view;
    }

    private void fetchMessageLogs() {
        // Query the message log
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = requireActivity().getContentResolver().query(
                uri,
                null,
                null,
                null,
                Telephony.Sms.DEFAULT_SORT_ORDER
        );

        // Process the message log data and add it to messageLogDataList
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Extract relevant information from the cursor
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));

                // Create a LogData object and add it to the list
                LogData logData = new LogData(address, body, date);
                messageLogDataList.add(logData);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        // Notify the adapter that data has changed
        messageLogAdapter.notifyDataSetChanged();
    }
}
