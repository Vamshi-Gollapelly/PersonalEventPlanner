package com.vamshigollapelly.personaleventplanner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vamshigollapelly.personaleventplanner.R;
import com.vamshigollapelly.personaleventplanner.data.Event;
import com.vamshigollapelly.personaleventplanner.viewmodel.EventViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditEventFragment extends Fragment {

    private EditText editTitle, editCategory, editLocation;
    private TextView textSelectedDateTime;
    private long selectedDateTime = -1;
    private int eventId = -1;
    private boolean isEditMode = false;
    private EventViewModel eventViewModel;

    public AddEditEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_edit_event, container, false);

        editTitle = view.findViewById(R.id.editTitle);
        editCategory = view.findViewById(R.id.editCategory);
        editLocation = view.findViewById(R.id.editLocation);
        textSelectedDateTime = view.findViewById(R.id.textSelectedDateTime);
        Button btnPickDateTime = view.findViewById(R.id.btnPickDateTime);
        Button btnSaveEvent = view.findViewById(R.id.btnSaveEvent);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);

        if (getArguments() != null) {
            isEditMode = true;

            eventId = getArguments().getInt("id", -1);
            editTitle.setText(getArguments().getString("title", ""));
            editCategory.setText(getArguments().getString("category", ""));
            editLocation.setText(getArguments().getString("location", ""));
            selectedDateTime = getArguments().getLong("dateTime", -1);

            if (selectedDateTime != -1) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                textSelectedDateTime.setText(sdf.format(new Date(selectedDateTime)));
            }

            btnSaveEvent.setText("Update Event");
        }

        btnPickDateTime.setOnClickListener(v -> pickDateTime());
        btnSaveEvent.setOnClickListener(v -> saveEvent());

        return view;
    }

    private void pickDateTime() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (datePicker, year, month, dayOfMonth) -> {

                    TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                            (timePicker, hourOfDay, minute) -> {
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth, hourOfDay, minute, 0);
                                selectedCalendar.set(Calendar.MILLISECOND, 0);

                                selectedDateTime = selectedCalendar.getTimeInMillis();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
                                textSelectedDateTime.setText(sdf.format(selectedCalendar.getTime()));
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false);

                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void saveEvent() {
        String title = editTitle.getText().toString().trim();
        String category = editCategory.getText().toString().trim();
        String location = editLocation.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(getContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDateTime == -1) {
            Toast.makeText(getContext(), "Please select a date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedDateTime < System.currentTimeMillis()) {
            Toast.makeText(getContext(), "Past dates are not allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event(title, category, location, selectedDateTime);

        if (isEditMode) {
            event.setId(eventId);
            eventViewModel.update(event);
            Toast.makeText(getContext(), "Event updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            eventViewModel.insert(event);
            Toast.makeText(getContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }
}