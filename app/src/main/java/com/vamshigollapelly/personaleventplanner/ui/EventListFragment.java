package com.vamshigollapelly.personaleventplanner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.vamshigollapelly.personaleventplanner.R;
import com.vamshigollapelly.personaleventplanner.data.Event;
import com.vamshigollapelly.personaleventplanner.viewmodel.EventViewModel;

public class EventListFragment extends Fragment {

    private EventViewModel eventViewModel;
    private EventAdapter adapter;

    public EventListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new EventAdapter(new EventAdapter.OnEventActionListener() {
            @Override
            public void onDeleteClick(Event event) {
                eventViewModel.delete(event);
                Snackbar.make(view, "Event deleted successfully", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onEditClick(Event event) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", event.getId());
                bundle.putString("title", event.getTitle());
                bundle.putString("category", event.getCategory());
                bundle.putString("location", event.getLocation());
                bundle.putLong("dateTime", event.getDateTime());

                AddEditEventFragment fragment = new AddEditEventFragment();
                fragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> adapter.setEvents(events));

        return view;
    }
}